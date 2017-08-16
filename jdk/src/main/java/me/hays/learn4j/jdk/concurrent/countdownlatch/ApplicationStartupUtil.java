package me.hays.learn4j.jdk.concurrent.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApplicationStartupUtil {
	private static List<BaseHealthChecker> services;

	private static CountDownLatch countDownLatch;

	private ApplicationStartupUtil() {
	}

	private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

	public static ApplicationStartupUtil getInstance() {
		return INSTANCE;
	}

	public static boolean checkExternalServices() throws Exception {
		countDownLatch = new CountDownLatch(2);
		services = new ArrayList<>();
		services.add(new DatabaseHealthChecker(countDownLatch));
		services.add(new NetworkHealthChecker(countDownLatch));

		Executor executor = Executors.newFixedThreadPool(2);
		for (BaseHealthChecker checker : services) {
			executor.execute(checker);
		}

		countDownLatch.await();

		for (BaseHealthChecker checker : services) {
			if (!checker.isServiceUp()) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		boolean result = false;
		try {
			result = ApplicationStartupUtil.checkExternalServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("External services validation completed !! Result was :: " + result);
	}
}
