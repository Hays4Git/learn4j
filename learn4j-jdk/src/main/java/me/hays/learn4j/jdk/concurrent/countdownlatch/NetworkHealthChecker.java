package me.hays.learn4j.jdk.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

public class NetworkHealthChecker extends BaseHealthChecker {

	public NetworkHealthChecker(CountDownLatch countDownLatch) {
		super(countDownLatch, "Network Service");
	}

	@Override
	public void checkService() {
		System.out.println("Checking " + this.getServiceName());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(this.getServiceName() + " is UP");
	}

}
