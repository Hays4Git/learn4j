package me.hays.learn4j.jdk.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/***
 * http://www.importnew.com/15731.html
 * 这个类是一个Runnable，负责所有特定的外部服务健康的检测。
 */
public abstract class BaseHealthChecker implements Runnable{
	
	private CountDownLatch countDownLatch;
	private String serviceName;
	private boolean serviceUp;

	public BaseHealthChecker(CountDownLatch countDownLatch, String serviceName) {
		super();
		this.countDownLatch = countDownLatch;
		this.serviceName = serviceName;
		serviceUp = false;
	}

	@Override
	public void run() {
		try {
			checkService();
			serviceUp = true;
		} catch (Exception e) {
			serviceUp = false;
			e.printStackTrace();
		} finally {
			if(countDownLatch != null){
				countDownLatch.countDown();
			}
		}
	}

	public abstract void checkService();
	
	public String getServiceName() {
		return serviceName;
	}

	public boolean isServiceUp() {
		return serviceUp;
	}
	
}
