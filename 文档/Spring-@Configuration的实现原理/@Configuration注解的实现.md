Spring @Configuration的实现原理


ConfigurationClassPostProcessor 类，是Spring中的一个默认的Processor。实现了 BeanFactoryPostProcessor，其方法void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)在容器标准初始化完成，但是创建实例之间触发，可以修改或者添加bean的定义。

类源码为

## ConfigurationClassPostProcessor源码

```java

public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor,
        PriorityOrdered, ResourceLoaderAware, ApplicationStartupAware, BeanClassLoaderAware, EnvironmentAware {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        enhanceConfigurationClasses(beanFactory);
        beanFactory.addBeanPostProcessor(new ImportAwareBeanPostProcessor(beanFactory));
    }

    public void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
        //对对配置类进行增强
        ConfigurationClassEnhancer enhancer = new ConfigurationClassEnhancer();
        for (Map.Entry<String, AbstractBeanDefinition> entry : configBeanDefs.entrySet()) {
            AbstractBeanDefinition beanDef = entry.getValue();
            // If a @Configuration class gets proxied, always proxy the target class
            beanDef.setAttribute(AutoProxyUtils.PRESERVE_TARGET_CLASS_ATTRIBUTE, Boolean.TRUE);
            // Set enhanced subclass of the user-specified bean class
            Class<?> configClass = beanDef.getBeanClass();
            Class<?> enhancedClass = enhancer.enhance(configClass, this.beanClassLoader);
            if (configClass != enhancedClass) {
                //注意这里，直接将beanDef改成了代理类。
                beanDef.setBeanClass(enhancedClass);
            }
        }
        enhanceConfigClasses.tag("classCount", () -> String.valueOf(configBeanDefs.keySet().size())).end();
    }
}
```

以上最重要的一行代码是 beanDef.setBeanClass(enhancedClass); beanDef为原来配置类的定义，enhancedClass为增强后的类，直接将原始类更换为了代理类的类定义，原始类不会在Spring中生成实例对象。

## ConfigurationClassEnhancer源码

```java
class ConfigurationClassEnhancer {
    
    private static final String BEAN_FACTORY_FIELD = "$$beanFactory";


    // The callbacks to use. Note that these callbacks must be stateless.
    //直接定义了一些callback
	private static final Callback[] CALLBACKS = new Callback[] {
			new BeanMethodInterceptor(),
			new BeanFactoryAwareMethodInterceptor(),
			NoOp.INSTANCE
	};
  
    public Class<?> enhance(Class<?> configClass, @Nullable ClassLoader classLoader) {
        if (EnhancedConfiguration.class.isAssignableFrom(configClass)) {
            //已经被代理过
            return configClass;
        }
        Class<?> enhancedClass = createClass(newEnhancer(configClass, classLoader));
        return enhancedClass;
    }
    
    //创建CGLIB代理
    private Enhancer newEnhancer(Class<?> configSuperClass, @Nullable ClassLoader classLoader) {
        Enhancer enhancer = new Enhancer();
        //配置代理类继承的类
        enhancer.setSuperclass(configSuperClass);
        //实现了BeanFactoryAware，另外可以用来标明已经被代理过了
        enhancer.setInterfaces(new Class<?>[] {EnhancedConfiguration.class});
        enhancer.setUseFactory(false);
        //命名规则
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        //指定了一个新的生成策略，继承了DefaultGeneratorStrategy，重写了一些方法。作用是将定义了一个类型为BeanFactory，名称为$$beanFactory的变量
        enhancer.setStrategy(new BeanFactoryAwareGeneratorStrategy(classLoader));
        //指定过滤器，为不同的method指定合适的Callback
        enhancer.setCallbackFilter(CALLBACK_FILTER);
        //当使用enhancer.createClass()时，setCallbackTypes用来代替createClass
        enhancer.setCallbackTypes(CALLBACK_FILTER.getCallbackTypes());
        return enhancer;
    }

    private Class<?> createClass(Enhancer enhancer) {
        Class<?> subclass = enhancer.createClass();
        // Registering callbacks statically (as opposed to thread-local)
        // is critical for usage in an OSGi environment (SPR-5932)...
        //多线程时使用
        Enhancer.registerStaticCallbacks(subclass, CALLBACKS);
        return subclass;
    }

    private static class BeanFactoryAwareGeneratorStrategy extends
            ClassLoaderAwareGeneratorStrategy {

        public BeanFactoryAwareGeneratorStrategy(@Nullable ClassLoader classLoader) {
            super(classLoader);
        }

        @Override
        protected ClassGenerator transform(ClassGenerator cg) throws Exception {
            ClassEmitterTransformer transformer = new ClassEmitterTransformer() {
                @Override
                public void end_class() {
                    declare_field(Constants.ACC_PUBLIC, BEAN_FACTORY_FIELD, Type.getType(BeanFactory.class), null);
                    super.end_class();
                }
            };
            return new TransformingClassGenerator(cg, transformer);
        }

    }

}
```
那真正发挥功能的就是两个Callback类了。
###为代理类设置beanFactory
回头看在设置生成策略的时候， 传入了BeanFactoryAwareGeneratorStrategy，作用是为代理类加了一个类型为BeanFactory，名称为$$beanFactory的属性。另外代理类的接口类为EnhancedConfiguration，继承了BeanFactoryAware，里面有一个方法setBeanFactory(BeanFactory beanFactory)。只是一个空的接口而已，如何将这个接口的beanFactory赋予$$beanFactory属性呢？具体实现是由BeanFactoryAwareMethodInterceptor来实现的。
```java
private static class BeanFactoryAwareMethodInterceptor implements MethodInterceptor, ConditionalCallback {
        @Override
        public boolean isMatch(Method candidateMethod) {
            return isSetBeanFactory(candidateMethod);
        }
    
        //先判定方法属于setBeanFactory才会被拦截
        public static boolean isSetBeanFactory(Method candidateMethod) {
            return (candidateMethod.getName().equals("setBeanFactory") &&
                    candidateMethod.getParameterCount() == 1 &&
                    BeanFactory.class == candidateMethod.getParameterTypes()[0] &&
                    BeanFactoryAware.class.isAssignableFrom(candidateMethod.getDeclaringClass()));
        }
        //将
		@Override
		@Nullable
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            //给$$beanFactory属性赋值
			Field field = ReflectionUtils.findField(obj.getClass(), BEAN_FACTORY_FIELD);
			Assert.state(field != null, "Unable to find generated BeanFactory field");
			field.set(obj, args[0]);
			// Does the actual (non-CGLIB) superclass implement BeanFactoryAware?
			// If so, call its setBeanFactory() method. If not, just exit.
			if (BeanFactoryAware.class.isAssignableFrom(ClassUtils.getUserClass(obj.getClass().getSuperclass()))) {
				return proxy.invokeSuper(obj, args);
			}
			return null;
		}
	}
```

### 为代理类设置beanFactory


# 参考
1. [CGLib中CallbackFilter介绍](https://blog.csdn.net/NEW_BUGGER/article/details/106350780)
2. [死磕cglib系列之一 cglib简介与callback解析](https://blog.csdn.net/zhang6622056/article/details/87286498)