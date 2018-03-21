package springbook.learningtest.jdk;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.junit.Test;

public class ReflectionTest {	
	@Test
	public void invokeMethod() throws Exception{
		String name = "Spring";
		
		assertThat(name.length(), is(6));
		
		Method lengthMethod = String.class.getMethod("length");
		assertThat((Integer)lengthMethod.invoke(name), is(6));
		
		assertThat(name.charAt(0), is('S'));
		
		Method charAtMethod = String.class.getMethod("charAt", int.class);
		assertThat((Character)charAtMethod.invoke(name, 0),is('S'));
	}	
	
	@Test
	public void simpleProxy(){
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Tello"), is("Hello Tello"));
		assertThat(hello.sayHi("Tello"), is("Hi Tello"));
		assertThat(hello.sayThankYou("Tello"), is("Thank You Tello"));
		
		Hello proxiedHello2 = new HelloUppercase(new HelloTarget());
		assertThat(proxiedHello2.sayHello("Tello"), is("Hello Tello"));
		assertThat(proxiedHello2.sayHi("Tello"), is("Hi Tello"));
		assertThat(proxiedHello2.sayThankYou("Tello"), is("Thank You Tello"));
		
		Hello proxiedHello = (Hello)Proxy.newProxyInstance(getClass().getClassLoader(),new Class[] {Hello.class},new UppercaseHandler(new HelloTarget()));
		assertThat(proxiedHello.sayHello("Tello"), is("HELLO TELLO"));
		assertThat(proxiedHello.sayHi("Tello"), is("HI TELLO"));
		assertThat(proxiedHello.sayThankYou("Tello"), is("THANK YOU TELLO"));
	}
}