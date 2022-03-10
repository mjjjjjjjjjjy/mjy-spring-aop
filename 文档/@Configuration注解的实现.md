
<div style="text-align: center;"><span style="font-size: xxx-large" >Spring @Configuration的实现原理
</span></div>


# 1. 前言

在应用使用Spring框架开发的时候，我们经常使用@Configuration标注一些配置类，被标注的类中@Bean的方法就可以被Spring处理成为一个Spring的bean。其中一个简单的例子如下：

maven工程先引入依赖包

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.3.15</version>
</dependency>
```
demo:
```java
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Map;

public class ConfigurationApplication {
    //1、先定义一个Bean
    static class ClassA {
        int a = 0;
        public ClassA(int a) {
            this.a = a;
        }
        public int getA() {
            return a;
        }
    }
    // 2. 定义一个配置类
    @Configuration
    static class Configutation{
        @Bean
        public ClassA classA(){
            System.out.println("配置classA到Spring");
            return new ClassA(1);
        }
    }
    // 3. 启动一个Spring容器
    public static void main(String[] args) {
        //打印代理类
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY,"./");
        System.out.println("开始。。。");
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Configutation.class);
        //配置成功，可以从容器中获取bean 成功
        ClassA bean = annotationConfigApplicationContext.getBean(ClassA.class);
        System.out.println("ClassA 的属性a="+bean.getA());
        //获取 Configutation 对象
        Configutation configutationBean = annotationConfigApplicationContext.getBean(Configutation.class);
        System.out.println("Configutation对象的Class名称："+configutationBean.getClass().getName());
        ClassA classA = configutationBean.classA();
        System.out.println("结束....");
        
        //输出：
        //开始。。。
        //配置classA到Spring
        //ClassA 的属性a=1
        //Configutation对象的Class名称：ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3
        //结束....
    }
}
```

以上代码举例了如何使用@Configuration注解和@Bean注解将一个Bean配置一个类到Spring容器中。

不知道注意了没有，在例子中，我特意打印了容器中Configutation对象的Class名称，打印结果为"ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3"，从名字中可以这是是一个被Spring cglib增强后的类。
另外：我在使用configutationBean.classA()的时候，理论上讲，会打印"配置classA到Spring"这个文本。单实际没有。这两个问题，就是我们今天的主题。

生成的代理类如下:
```java

import ConfigurationApplication.ClassA;
import ConfigurationApplication.Configutation;
import java.lang.reflect.Method;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cglib.core.ReflectUtils;
import org.springframework.cglib.core.Signature;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.annotation.ConfigurationClassEnhancer.EnhancedConfiguration;

public class ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3 extends Configutation implements EnhancedConfiguration {
    private boolean CGLIB$BOUND;
    public static Object CGLIB$FACTORY_DATA;
    private static final ThreadLocal CGLIB$THREAD_CALLBACKS;
    private static final Callback[] CGLIB$STATIC_CALLBACKS;
    //根据filter决定了哪个方法使用哪个MethodInterceptor
    private MethodInterceptor CGLIB$CALLBACK_0;
    private MethodInterceptor CGLIB$CALLBACK_1;
    private NoOp CGLIB$CALLBACK_2;
    private static Object CGLIB$CALLBACK_FILTER;
    private static final Method CGLIB$classA$0$Method;
    private static final MethodProxy CGLIB$classA$0$Proxy;
    private static final Object[] CGLIB$emptyArgs;
    private static final Method CGLIB$setBeanFactory$5$Method;
    private static final MethodProxy CGLIB$setBeanFactory$5$Proxy;
    public BeanFactory $$beanFactory;

    static void CGLIB$STATICHOOK1() {
        CGLIB$THREAD_CALLBACKS = new ThreadLocal();
        CGLIB$emptyArgs = new Object[0];
        Class var0 = Class.forName("ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3");
        Class var1;
        CGLIB$classA$0$Method = ReflectUtils.findMethods(new String[]{"classA", "()LConfigurationApplication$ClassA;"}, (var1 = Class.forName("ConfigurationApplication$Configutation")).getDeclaredMethods())[0];
        CGLIB$classA$0$Proxy = MethodProxy.create(var1, var0, "()LConfigurationApplication$ClassA;", "classA", "CGLIB$classA$0");
        CGLIB$setBeanFactory$5$Method = ReflectUtils.findMethods(new String[]{"setBeanFactory", "(Lorg/springframework/beans/factory/BeanFactory;)V"}, (var1 = Class.forName("org.springframework.beans.factory.BeanFactoryAware")).getDeclaredMethods())[0];
        CGLIB$setBeanFactory$5$Proxy = MethodProxy.create(var1, var0, "(Lorg/springframework/beans/factory/BeanFactory;)V", "setBeanFactory", "CGLIB$setBeanFactory$5");
    }

    final ClassA CGLIB$classA$0() {
        return super.classA();
    }

    public final ClassA classA() {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_0;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_0;
        }

        return var10000 != null ? (ClassA)var10000.intercept(this, CGLIB$classA$0$Method, CGLIB$emptyArgs, CGLIB$classA$0$Proxy) : super.classA();
    }

    final void CGLIB$setBeanFactory$5(BeanFactory var1) throws BeansException {
        super.setBeanFactory(var1);
    }

    public final void setBeanFactory(BeanFactory var1) throws BeansException {
        MethodInterceptor var10000 = this.CGLIB$CALLBACK_1;
        if (var10000 == null) {
            CGLIB$BIND_CALLBACKS(this);
            var10000 = this.CGLIB$CALLBACK_1;
        }

        if (var10000 != null) {
            var10000.intercept(this, CGLIB$setBeanFactory$5$Method, new Object[]{var1}, CGLIB$setBeanFactory$5$Proxy);
        } else {
            super.setBeanFactory(var1);
        }
    }

    public static MethodProxy CGLIB$findMethodProxy(Signature var0) {
        String var10000 = var0.toString();
        switch(var10000.hashCode()) {
        case 2075358314:
            if (var10000.equals("classA()LConfigurationApplication$ClassA;")) {
                return CGLIB$classA$0$Proxy;
            }
            break;
        case 2095635076:
            if (var10000.equals("setBeanFactory(Lorg/springframework/beans/factory/BeanFactory;)V")) {
                return CGLIB$setBeanFactory$5$Proxy;
            }
        }

        return null;
    }

    public ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3() {
        CGLIB$BIND_CALLBACKS(this);
    }

    public static void CGLIB$SET_THREAD_CALLBACKS(Callback[] var0) {
        CGLIB$THREAD_CALLBACKS.set(var0);
    }

    public static void CGLIB$SET_STATIC_CALLBACKS(Callback[] var0) {
        CGLIB$STATIC_CALLBACKS = var0;
    }

    private static final void CGLIB$BIND_CALLBACKS(Object var0) {
        ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3 var1 = (ConfigurationApplication$Configutation$$EnhancerBySpringCGLIB$$ebb398d3)var0;
        if (!var1.CGLIB$BOUND) {
            var1.CGLIB$BOUND = true;
            Object var10000 = CGLIB$THREAD_CALLBACKS.get();
            if (var10000 == null) {
                var10000 = CGLIB$STATIC_CALLBACKS;
                if (var10000 == null) {
                    return;
                }
            }

            Callback[] var10001 = (Callback[])var10000;
            var1.CGLIB$CALLBACK_2 = (NoOp)((Callback[])var10000)[2];
            var1.CGLIB$CALLBACK_1 = (MethodInterceptor)var10001[1];
            var1.CGLIB$CALLBACK_0 = (MethodInterceptor)var10001[0];
        }

    }

    static {
        CGLIB$STATICHOOK2();
        CGLIB$STATICHOOK1();
    }

    static void CGLIB$STATICHOOK2() {
    }
}

```

# 2 寻找学习入口

那@Configuration是具体如何发挥作用的呢？最简单就是跟进AnnotationConfigApplicationContext源码。

```java
public class AnnotationConfigApplicationContext extends GenericApplicationContext implements AnnotationConfigRegistry {
    public AnnotationConfigApplicationContext(Class<?>... componentClasses) {
        //调用了无参构造器
        this();
        register(componentClasses);
        refresh();
    }
    
    public AnnotationConfigApplicationContext() {
        StartupStep createAnnotatedBeanDefReader = this.getApplicationStartup().start("spring.context.annotated-bean-reader.create");
        //需要重点关注这个类。从名字里面可以看到，翻译一下名字就知道专门助力注解类型的bean定义。
        this.reader = new AnnotatedBeanDefinitionReader(this);
        createAnnotatedBeanDefReader.end();
        this.scanner = new ClassPathBeanDefinitionScanner(this);
    }
}
```
从源码中知道，AnnotatedBeanDefinitionReader从名字上看就知道是专门处理注解类型的bean定义。
```java
public class AnnotatedBeanDefinitionReader {
    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, getOrCreateEnvironment(registry));
    }

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry registry, Environment environment) {
        this.registry = registry;
        this.conditionEvaluator = new ConditionEvaluator(registry, environment, null);
        //注册了processor，方法名很明显
        AnnotationConfigUtils.registerAnnotationConfigProcessors(this.registry);
    }
}
```
从以上发现，AnnotationConfigUtils做了一件事情，就是将Processors注册到上下文中。
```java
public abstract class AnnotationConfigUtils {
    
    //静态方法
    public static Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
            BeanDefinitionRegistry registry, @Nullable Object source) {

        DefaultListableBeanFactory beanFactory = unwrapDefaultListableBeanFactory(registry);
        if (beanFactory != null) {
            if (!(beanFactory.getDependencyComparator() instanceof AnnotationAwareOrderComparator)) {
                beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
            }
            if (!(beanFactory.getAutowireCandidateResolver() instanceof ContextAnnotationAutowireCandidateResolver)) {
                beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
            }
        }

        Set<BeanDefinitionHolder> beanDefs = new LinkedHashSet<>(8);

        if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(ConfigurationClassPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(AutowiredAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        // Check for JSR-250 support, and if present add the CommonAnnotationBeanPostProcessor.
        if (jsr250Present && !registry.containsBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(CommonAnnotationBeanPostProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, COMMON_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        // Check for JPA support, and if present add the PersistenceAnnotationBeanPostProcessor.
        if (jpaPresent && !registry.containsBeanDefinition(PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition();
            try {
                def.setBeanClass(ClassUtils.forName(PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME,
                        AnnotationConfigUtils.class.getClassLoader()));
            }
            catch (ClassNotFoundException ex) {
                throw new IllegalStateException(
                        "Cannot load optional framework class: " + PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME, ex);
            }
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(EVENT_LISTENER_PROCESSOR_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(EventListenerMethodProcessor.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_PROCESSOR_BEAN_NAME));
        }

        if (!registry.containsBeanDefinition(EVENT_LISTENER_FACTORY_BEAN_NAME)) {
            RootBeanDefinition def = new RootBeanDefinition(DefaultEventListenerFactory.class);
            def.setSource(source);
            beanDefs.add(registerPostProcessor(registry, def, EVENT_LISTENER_FACTORY_BEAN_NAME));
        }

        return beanDefs;
    }
}
```

静态方法中Set<BeanDefinitionHolder> registerAnnotationConfigProcessors(
BeanDefinitionRegistry registry, @Nullable Object source)，定义了一系列默认的Processor，供给bean生命周期中调用。其中ConfigurationClassPostProcessor类就是专门处理@Configuration配置的。本文从ConfigurationClassPostProcessor类的源码剖析加载的过程。

# 3 ConfigurationClassPostProcessor 源码

来看一下核心类

ConfigurationClassPostProcessor 实现了 BeanFactoryPostProcessor，其方法void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)在容器标准初始化完成，但是创建实例之间触发，可以修改或者添加bean的定义。

```java

public class ConfigurationClassPostProcessor implements BeanDefinitionRegistryPostProcessor,
        PriorityOrdered, ResourceLoaderAware, ApplicationStartupAware, BeanClassLoaderAware, EnvironmentAware {

    //在beanFactory的标准初始化完成后触发。此时bean的定义已经加载完成，但是还没有进行初始化。
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        enhanceConfigurationClasses(beanFactory);
        beanFactory.addBeanPostProcessor(new ImportAwareBeanPostProcessor(beanFactory));
    }

    public void enhanceConfigurationClasses(ConfigurableListableBeanFactory beanFactory) {
        Map<String, AbstractBeanDefinition> configBeanDefs = new LinkedHashMap<>();
        //忽略获取@Configuration注解的类定义。
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
通过查看源码，发现ConfigurationClassPostProcessor主要做了两件事情:

1. 从BeanFactory获取所有@Configuration注解类的bean定义
2. 遍历所有的bean定义，通过ConfigurationClassEnhancer生成代理类的Class，并重新赋值给原来的BeanDefinition。

接下来看下创建代理的过程。

# 4 ConfigurationClassEnhancer源码

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
从以上代码看，创建代理类的时候，最重要的是两个CallBack类：

- new BeanMethodInterceptor()：对@Bean的方法进行增强
- new BeanFactoryAwareMethodInterceptor()：将 BeanFactory 注入到代理类中。

具体如何发挥作用的呢。
# 5 BeanFactoryAwareMethodInterceptor 
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
				return proxy.invokeSuper(obj, args);ConfigurationClassEnhancer
			}
			return null;
		}
	}
```

# 6 BeanMethodInterceptor 增强@Bean注解的方法
BeanMethodInterceptor类是 ConfigurationClassEnhancer 类的私有内部类。在调用@Bean方法时，将会被intercept先拦截，具体代码如下：
```java
private static class BeanMethodInterceptor implements MethodInterceptor, ConditionalCallback {

		/**
         * 对@Bean的方法进行增强。
		 */
		@Override
		@Nullable
		public Object intercept(Object enhancedConfigInstance, Method beanMethod, Object[] beanMethodArgs,
					MethodProxy cglibMethodProxy) throws Throwable {
            //直接通过反射获取beanFactory。
			ConfigurableBeanFactory beanFactory = getBeanFactory(enhancedConfigInstance);
			String beanName = BeanAnnotationHelper.determineBeanNameFor(beanMethod);

			// 判断是否指定了scoped-proxy。如果是会有一个前缀 "scopedTarget."
			if (BeanAnnotationHelper.isScopedProxy(beanMethod)) {
				String scopedBeanName = ScopedProxyCreator.getTargetBeanName(beanName);
				if (beanFactory.isCurrentlyInCreation(scopedBeanName)) {
					beanName = scopedBeanName;
				}
			}

			// To handle the case of an inter-bean method reference, we must explicitly check the
			// container for already cached instances.

		
			// 首选，检查目标bean是否是一个FactoryBean，如果是，就创建一个代理子类，获取并缓存一个bean的实例。使得@Bean获取的bean和XML配置的一致
			if (factoryContainsBean(beanFactory, BeanFactory.FACTORY_BEAN_PREFIX + beanName) &&
					factoryContainsBean(beanFactory, beanName)) {
				Object factoryBean = beanFactory.getBean(BeanFactory.FACTORY_BEAN_PREFIX + beanName);
				if (factoryBean instanceof ScopedProxyFactoryBean) {
					// Scoped proxy factory beans are a special case and should not be further proxied
				}
				else {
					// It is a candidate FactoryBean - go ahead with enhancement
					return enhanceFactoryBean(factoryBean, beanMethod.getReturnType(), beanFactory, beanName);
				}
			}

			if (isCurrentlyInvokedFactoryMethod(beanMethod)) {
				// The factory is calling the bean method in order to instantiate and register the bean
				// (i.e. via a getBean() call) -> invoke the super implementation of the method to actually
				// create the bean instance.
				if (logger.isInfoEnabled() &&
						BeanFactoryPostProcessor.class.isAssignableFrom(beanMethod.getReturnType())) {
					logger.info(String.format("@Bean method %s.%s is non-static and returns an object " +
									"assignable to Spring's BeanFactoryPostProcessor interface. This will " +
									"result in a failure to process annotations such as @Autowired, " +
									"@Resource and @PostConstruct within the method's declaring " +
									"@Configuration class. Add the 'static' modifier to this method to avoid " +
									"these container lifecycle issues; see @Bean javadoc for complete details.",
							beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName()));
				}
				return cglibMethodProxy.invokeSuper(enhancedConfigInstance, beanMethodArgs);
			}

			return resolveBeanReference(beanMethod, beanMethodArgs, beanFactory, beanName);
		}

		private Object resolveBeanReference(Method beanMethod, Object[] beanMethodArgs,
				ConfigurableBeanFactory beanFactory, String beanName) {

			// The user (i.e. not the factory) is requesting this bean through a call to
			// the bean method, direct or indirect. The bean may have already been marked
			// as 'in creation' in certain autowiring scenarios; if so, temporarily set
			// the in-creation status to false in order to avoid an exception.
			boolean alreadyInCreation = beanFactory.isCurrentlyInCreation(beanName);
			try {
				if (alreadyInCreation) {
					beanFactory.setCurrentlyInCreation(beanName, false);
				}
				boolean useArgs = !ObjectUtils.isEmpty(beanMethodArgs);
				if (useArgs && beanFactory.isSingleton(beanName)) {
					// Stubbed null arguments just for reference purposes,
					// expecting them to be autowired for regular singleton references?
					// A safe assumption since @Bean singleton arguments cannot be optional...
					for (Object arg : beanMethodArgs) {
						if (arg == null) {
							useArgs = false;
							break;
						}
					}
				}
				Object beanInstance = (useArgs ? beanFactory.getBean(beanName, beanMethodArgs) :
						beanFactory.getBean(beanName));
				if (!ClassUtils.isAssignableValue(beanMethod.getReturnType(), beanInstance)) {
					// Detect package-protected NullBean instance through equals(null) check
					if (beanInstance.equals(null)) {
						if (logger.isDebugEnabled()) {
							logger.debug(String.format("@Bean method %s.%s called as bean reference " +
									"for type [%s] returned null bean; resolving to null value.",
									beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName(),
									beanMethod.getReturnType().getName()));
						}
						beanInstance = null;
					}
					else {
						String msg = String.format("@Bean method %s.%s called as bean reference " +
								"for type [%s] but overridden by non-compatible bean instance of type [%s].",
								beanMethod.getDeclaringClass().getSimpleName(), beanMethod.getName(),
								beanMethod.getReturnType().getName(), beanInstance.getClass().getName());
						try {
							BeanDefinition beanDefinition = beanFactory.getMergedBeanDefinition(beanName);
							msg += " Overriding bean of same name declared in: " + beanDefinition.getResourceDescription();
						}
						catch (NoSuchBeanDefinitionException ex) {
							// Ignore - simply no detailed message then.
						}
						throw new IllegalStateException(msg);
					}
				}
				Method currentlyInvoked = SimpleInstantiationStrategy.getCurrentlyInvokedFactoryMethod();
				if (currentlyInvoked != null) {
					String outerBeanName = BeanAnnotationHelper.determineBeanNameFor(currentlyInvoked);
					beanFactory.registerDependentBean(beanName, outerBeanName);
				}
				return beanInstance;
			}
			finally {
				if (alreadyInCreation) {
					beanFactory.setCurrentlyInCreation(beanName, true);
				}
			}
		}

		@Override
		public boolean isMatch(Method candidateMethod) {
			return (candidateMethod.getDeclaringClass() != Object.class &&
					!BeanFactoryAwareMethodInterceptor.isSetBeanFactory(candidateMethod) &&
					BeanAnnotationHelper.isBeanAnnotated(candidateMethod));
		}

		private ConfigurableBeanFactory getBeanFactory(Object enhancedConfigInstance) {
			Field field = ReflectionUtils.findField(enhancedConfigInstance.getClass(), BEAN_FACTORY_FIELD);
			Assert.state(field != null, "Unable to find generated bean factory field");
			Object beanFactory = ReflectionUtils.getField(field, enhancedConfigInstance);
			Assert.state(beanFactory != null, "BeanFactory has not been injected into @Configuration class");
			Assert.state(beanFactory instanceof ConfigurableBeanFactory,
					"Injected BeanFactory is not a ConfigurableBeanFactory");
			return (ConfigurableBeanFactory) beanFactory;
		}

		/**
		 * Check the BeanFactory to see whether the bean named <var>beanName</var> already
		 * exists. Accounts for the fact that the requested bean may be "in creation", i.e.:
		 * we're in the middle of servicing the initial request for this bean. From an enhanced
		 * factory method's perspective, this means that the bean does not actually yet exist,
		 * and that it is now our job to create it for the first time by executing the logic
		 * in the corresponding factory method.
		 * <p>Said another way, this check repurposes
		 * {@link ConfigurableBeanFactory#isCurrentlyInCreation(String)} to determine whether
		 * the container is calling this method or the user is calling this method.
		 * @param beanName name of bean to check for
		 * @return whether <var>beanName</var> already exists in the factory
		 */
		private boolean factoryContainsBean(ConfigurableBeanFactory beanFactory, String beanName) {
			return (beanFactory.containsBean(beanName) && !beanFactory.isCurrentlyInCreation(beanName));
		}

		/**
		 * Check whether the given method corresponds to the container's currently invoked
		 * factory method. Compares method name and parameter types only in order to work
		 * around a potential problem with covariant return types (currently only known
		 * to happen on Groovy classes).
		 */
		private boolean isCurrentlyInvokedFactoryMethod(Method method) {
			Method currentlyInvoked = SimpleInstantiationStrategy.getCurrentlyInvokedFactoryMethod();
			return (currentlyInvoked != null && method.getName().equals(currentlyInvoked.getName()) &&
					Arrays.equals(method.getParameterTypes(), currentlyInvoked.getParameterTypes()));
		}

		/**
		 * Create a subclass proxy that intercepts calls to getObject(), delegating to the current BeanFactory
		 * instead of creating a new instance. These proxies are created only when calling a FactoryBean from
		 * within a Bean method, allowing for proper scoping semantics even when working against the FactoryBean
		 * instance directly. If a FactoryBean instance is fetched through the container via &-dereferencing,
		 * it will not be proxied. This too is aligned with the way XML configuration works.
		 */
		private Object enhanceFactoryBean(Object factoryBean, Class<?> exposedType,
				ConfigurableBeanFactory beanFactory, String beanName) {

			try {
				Class<?> clazz = factoryBean.getClass();
				boolean finalClass = Modifier.isFinal(clazz.getModifiers());
				boolean finalMethod = Modifier.isFinal(clazz.getMethod("getObject").getModifiers());
				if (finalClass || finalMethod) {
					if (exposedType.isInterface()) {
						if (logger.isTraceEnabled()) {
							logger.trace("Creating interface proxy for FactoryBean '" + beanName + "' of type [" +
									clazz.getName() + "] for use within another @Bean method because its " +
									(finalClass ? "implementation class" : "getObject() method") +
									" is final: Otherwise a getObject() call would not be routed to the factory.");
						}
						return createInterfaceProxyForFactoryBean(factoryBean, exposedType, beanFactory, beanName);
					}
					else {
						if (logger.isDebugEnabled()) {
							logger.debug("Unable to proxy FactoryBean '" + beanName + "' of type [" +
									clazz.getName() + "] for use within another @Bean method because its " +
									(finalClass ? "implementation class" : "getObject() method") +
									" is final: A getObject() call will NOT be routed to the factory. " +
									"Consider declaring the return type as a FactoryBean interface.");
						}
						return factoryBean;
					}
				}
			}
			catch (NoSuchMethodException ex) {
				// No getObject() method -> shouldn't happen, but as long as nobody is trying to call it...
			}

			return createCglibProxyForFactoryBean(factoryBean, beanFactory, beanName);
		}

		private Object createInterfaceProxyForFactoryBean(Object factoryBean, Class<?> interfaceType,
				ConfigurableBeanFactory beanFactory, String beanName) {

			return Proxy.newProxyInstance(
					factoryBean.getClass().getClassLoader(), new Class<?>[] {interfaceType},
					(proxy, method, args) -> {
						if (method.getName().equals("getObject") && args == null) {
							return beanFactory.getBean(beanName);
						}
						return ReflectionUtils.invokeMethod(method, factoryBean, args);
					});
		}

		private Object createCglibProxyForFactoryBean(Object factoryBean,
				ConfigurableBeanFactory beanFactory, String beanName) {

			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(factoryBean.getClass());
			enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
			enhancer.setCallbackType(MethodInterceptor.class);

			// Ideally create enhanced FactoryBean proxy without constructor side effects,
			// analogous to AOP proxy creation in ObjenesisCglibAopProxy...
			Class<?> fbClass = enhancer.createClass();
			Object fbProxy = null;

			if (objenesis.isWorthTrying()) {
				try {
					fbProxy = objenesis.newInstance(fbClass, enhancer.getUseCache());
				}
				catch (ObjenesisException ex) {
					logger.debug("Unable to instantiate enhanced FactoryBean using Objenesis, " +
							"falling back to regular construction", ex);
				}
			}

			if (fbProxy == null) {
				try {
					fbProxy = ReflectionUtils.accessibleConstructor(fbClass).newInstance();
				}
				catch (Throwable ex) {
					throw new IllegalStateException("Unable to instantiate enhanced FactoryBean using Objenesis, " +
							"and regular FactoryBean instantiation via default constructor fails as well", ex);
				}
			}

			((Factory) fbProxy).setCallback(0, (MethodInterceptor) (obj, method, args, proxy) -> {
				if (method.getName().equals("getObject") && args.length == 0) {
					return beanFactory.getBean(beanName);
				}
				return proxy.invoke(factoryBean, args);
			});

			return fbProxy;
		}
	}
```

# 参考
1. [CGLib中CallbackFilter介绍](https://blog.csdn.net/NEW_BUGGER/article/details/106350780)
2. [死磕cglib系列之一 cglib简介与callback解析](https://blog.csdn.net/zhang6622056/article/details/87286498)
3. [解析Spring中@Bean的实现原理](https://blog.csdn.net/CSDN_WYL2016/article/details/108223930)
4. [@Scope注解的作用详解](https://www.cnblogs.com/ziyue7575/p/c925cfe466df01c1d352f37da8823946.html)
5. [521我发誓读完本文，再也不会担心Spring配置类问题了](https://blog.csdn.net/f641385712/article/details/106175299)
6. [你自我介绍说很懂Spring配置类，那你怎么解释这个现象？](https://mp.weixin.qq.com/s/qKenyoydYm4q2yPnGSdMZw)
7. [Spring配置类深度剖析-总结篇(手绘流程图，可白嫖)](https://fangshixiang.blog.csdn.net/article/details/106294908?spm=1001.2014.3001.5502)