/**  
* @Title: ProductAndConsumer.java
* @Description: 并发，消费者和生产者模型，使用阻塞队列
* @author hays  
* @date 2017年1月23日 下午2:54:28 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.concurrent.ArrayBlockingQueue;

public class ProductAndConsumer {
	private int initSize = 10;
	private ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<Integer>(initSize);
	
	public static void main(String[] args) {
		ProductAndConsumer productAndConsumer = new ProductAndConsumer();
		Productor productor = productAndConsumer.new Productor();
		Consumer consumer = productAndConsumer.new Consumer();
		
		productor.start();
		consumer.start();
	}
	
	class Productor extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					arrayBlockingQueue.put(1);
					System.out.println("从队列增加一个元素，队列剩余"+arrayBlockingQueue.size()+"个元素");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	class Consumer extends Thread{
		@Override
		public void run() {
			while(true){
				try {
					arrayBlockingQueue.take();
					System.out.println("从队列增加一个元素，队列剩余"+arrayBlockingQueue.size()+"个元素");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}	
