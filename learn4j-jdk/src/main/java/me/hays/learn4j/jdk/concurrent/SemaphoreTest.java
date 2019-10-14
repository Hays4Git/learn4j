/**  
* @Title: SemaphoreTest.java
* @Description: 

	控制同时访问的线程个数

* @author hays  
* @date 2017年1月24日 下午2:45:34 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {

	/**
	* @Title: main
	* @Description: TODO
	* @param @param args    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(10);
		SemaphoreTest semaphoreTest = new SemaphoreTest();
		for (int i = 0; i < 20; i++) {
			MyThread myThread = semaphoreTest.new MyThread(semaphore);
			myThread.start();
		}
	}

	class MyThread extends Thread{
		
		private Semaphore semaphore;
		public MyThread(Semaphore semaphore) {
			this.semaphore = semaphore;
		}
		@Override
		public void run() {
			try {
				semaphore.acquire();//请求一个许可//阻塞方法，请求不到一直等待。。.tryAcquire()方法立即返回
				System.out.println("线程" + Thread.currentThread().getName() + "请求一个许可，剩下" 
						+ semaphore.availablePermits() + "个许可可以请求");
				Thread.sleep(4000);//线程处理工作
				semaphore.release();//释放一个许可
				System.out.println("线程" + Thread.currentThread().getName() + "释放一个许可，剩下" 
						+ semaphore.availablePermits() + "个许可可以请求");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
