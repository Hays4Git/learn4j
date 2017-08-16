/**  
* @Title: CyclicBarrierTest.java
* @Description: 
* 
* 回环栅栏
* 让一组线程等待至某个状态之后再全部同时执行
* 
* @author hays  
* @date 2017年1月24日 下午2:15:18 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CyclicBarrierTest {
	
	final static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, //让多少个线程等待至Barrier状态
			new Runnable() {//处理完5个线程后的处理任务，非必传参数
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "处理后续任务...");//执行完的线程中选择一个执行
				}
			}
	);//cyclicBarrier调用完成后可以重用
	
	public static void main(String[] args) {
		CyclicBarrierTest cyclicBarrierTest = new CyclicBarrierTest();
		for (int i = 0; i < 4; i++) {
			MyThread myThread = cyclicBarrierTest.new MyThread();
			myThread.start();
		}
		
		
		//以下测试cyclicBarrier.await(1, TimeUnit.SECONDS)
		///////start
		try {
			Thread.sleep(5000);
			MyThread myThread = cyclicBarrierTest.new MyThread();
			myThread.start();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		///////end
	}

	class MyThread extends Thread{
		@Override
		public void run() {
			System.out.println("开始执行线程" + Thread.currentThread().getName());
			try {
				Thread.sleep(2000);
				System.out.println("线程" + Thread.currentThread().getName() + "进行等待");
				//cyclicBarrier.await();
				cyclicBarrier.await(1, TimeUnit.SECONDS);//让这些线程等待至一定的时间，
														//如果还有线程没有到达barrier状态就直接让到达barrier的线程执行后续任务
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
			System.out.println("线程" + Thread.currentThread().getName() + "执行完毕");
		}
	}
}
