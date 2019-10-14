/**  
* @Title: CountDownLatchTest.java
* @Description: 
* 可以实现类似计数器的功能。
* 
* 应用:
* 任务A要等待其他几个任务执行完毕之后才执行，
* 就可以利用CountDownLatch
* 
* @author hays  
* @date 2017年1月24日 上午11:31:48 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
	
	final static CountDownLatch countDownLatch = new CountDownLatch(2);
	public static void main(String[] args) {
		CountDownLatchTest countDownLatchTest = new CountDownLatchTest();
		MyThread thread1 = countDownLatchTest.new MyThread("子线程1");
		MyThread thread2 = countDownLatchTest.new MyThread("子线程2");
		thread1.start();
		thread2.start();
		
		try {
			System.out.println("等待子线程执行...");
			countDownLatch.await();
			System.out.println("子线程执行完毕，现在执行主程序");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	class MyThread extends Thread{
		private String name;
		public MyThread(String name) {
			this.name = name;
		}
		@Override
		public void run() {
			System.out.println("线程" + name + "正在运行...");
			try {
				Thread.sleep(1000);
				countDownLatch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("线程" + name + "执行完毕");
		}
	}
}
