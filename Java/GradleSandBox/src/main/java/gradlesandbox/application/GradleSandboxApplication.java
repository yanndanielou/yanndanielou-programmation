package gradlesandbox.application;

import org.joda.time.LocalTime;

import greeter.Greeter;

public class GradleSandboxApplication {
	public static void main(String[] args) {
		LocalTime currentTime = new LocalTime();
		System.out.println("The current local time is: " + currentTime);

		Greeter greeter = new Greeter();
		System.out.println(greeter.sayHello());
	}

}
