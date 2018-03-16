package springbook.learningtest.jdk;

public class HelloUppercase implements Hello {
	Hello hello;
	
	public HelloUppercase(Hello hello){
		this.hello = hello;
	}

	public String sayHello(String name) {
		return "Hello " + name;
	}

	public String sayHi(String name) {
		return "Hi " + name;
	}

	public String sayThankYou(String name) {
		return "Thank You " + name;
	}

}
