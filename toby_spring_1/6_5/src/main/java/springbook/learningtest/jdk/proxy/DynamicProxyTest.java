package springbook.learningtest.jdk.proxy;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class DynamicProxyTest {
	@Test
	public void proxyFactoryBean(){
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		pfBean.addAdvice(new UppercaseAdvice());
		
		Hello proxiedHello = (Hello)pfBean.getObject();
		
		assertThat(proxiedHello.sayHello("Tello"), is("HELLO TELLO"));
		assertThat(proxiedHello.sayHi("Tello"), is("HI TELLO"));
		assertThat(proxiedHello.sayThankYou("Tello"), is("THANK YOU TELLO"));
	}
	
	@Test
	public void pointcutAdvisor(){
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(new HelloTarget());
		
		NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
		pointcut.setMappedName("sayH*");
		
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		
		Hello proxiedHello = (Hello)pfBean.getObject();
		
		assertThat(proxiedHello.sayHello("Tello"), is("HELLO TELLO"));
		assertThat(proxiedHello.sayHi("Tello"), is("HI TELLO"));
		assertThat(proxiedHello.sayThankYou("Tello"), is("Thank You Tello"));
	}	
	
	@Test
	public void classNamePointcutAdvisor(){
		NameMatchMethodPointcut classMethodPointcut = new NameMatchMethodPointcut(){
			public ClassFilter getClassFilter(){
				return new ClassFilter() {
					public boolean matches(Class<?> clazz) {
						return clazz.getSimpleName().startsWith("HelloT");
					}
				};
			}
		};
		classMethodPointcut.setMappedName("sayH*");
		
		checkAdviced(new HelloTarget(), classMethodPointcut, true);
		
		class HelloWorld extends HelloTarget{};
		checkAdviced(new HelloWorld(), classMethodPointcut, false);
		
		class HelloTello extends HelloTarget{};
		checkAdviced(new HelloTello(), classMethodPointcut, true);
		
		
	}
	
	static interface Hello {
		String sayHello(String name);
		String sayHi(String name);
		String sayThankYou(String name);
	}
	
	static class HelloTarget implements Hello {
		public String sayHello(String name) {	return "Hello " + name;}
		public String sayHi(String name) {	return "Hi " + name;}
		public String sayThankYou(String name) {return "Thank You " + name;}
	}
	
	static class UppercaseAdvice implements MethodInterceptor{
		public Object invoke(MethodInvocation invocation) throws Throwable{
			String ret = (String)invocation.proceed();
			return ret.toUpperCase();
		}
	}
	
	private void checkAdviced(Object target , Pointcut pointcut , boolean adviced){
		ProxyFactoryBean pfBean = new ProxyFactoryBean();
		pfBean.setTarget(target);
		pfBean.addAdvisor(new DefaultPointcutAdvisor(pointcut, new UppercaseAdvice()));
		Hello proxiedHello = (Hello)pfBean.getObject();
		
		if(adviced){
			assertThat(proxiedHello.sayHello("Tello"), is("HELLO TELLO"));
			assertThat(proxiedHello.sayHi("Tello"), is("HI TELLO"));
			assertThat(proxiedHello.sayThankYou("Tello"), is("Thank You Tello"));
		}
		else{
			assertThat(proxiedHello.sayHello("Tello"), is("Hello Tello"));
			assertThat(proxiedHello.sayHi("Tello"), is("Hi Tello"));
			assertThat(proxiedHello.sayThankYou("Tello"), is("Thank You Tello"));
		}
	}

}