/**  
* @Title: ProductAndConsumer3_Condition.java
* @Description: 
* 
* 生产者消费者模型
* condition的实现
* Condition的await()和signal()方法必须在lock方法之内
* Conditon中的await()对应Object的wait()；
* Condition中的signal()对应Object的notify()；
* Condition中的signalAll()对应Object的notifyAll()。
* 
* @author hays  
* @date 2017年2月6日 下午3:13:03 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.PriorityQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class ProductAndConsumer3_Condition {
	private int initSize = 10;
	private PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(initSize);
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public static void main(String[] args) throws InterruptedException {
		ProductAndConsumer3_Condition productAndConsumer3_Condition = new ProductAndConsumer3_Condition();
		Productor productor = productAndConsumer3_Condition.new Productor();
		Consumer consumer = productAndConsumer3_Condition.new Consumer();
		Thread.sleep(1000);
		productor.start();
		consumer.start();
	}
	
	class Productor extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					lock.lock();
					if(priorityQueue.size() == initSize){
						try {
							System.out.println("队列已满，等待取出数据......");
							condition.await();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					priorityQueue.offer(1);
					condition.signal();
					System.out.println("增加一个数据，目前有" + priorityQueue.size() + "个数据。");
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
	class Consumer extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					lock.lock();
					if(priorityQueue.size() == 0){
						System.out.println("队列已空，等待加入数据......");
						condition.await();
					}
					priorityQueue.poll();
					condition.signal();
					System.out.println("取出一个数据，目前有" + priorityQueue.size() + "个数据。");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			}
		}
	}
	
}
