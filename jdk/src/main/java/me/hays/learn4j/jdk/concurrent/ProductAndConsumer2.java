/**  
* @Title: ProductAndConsumer.java
* @Description: 并发，消费者和生产者模型，使用非阻塞队列
* @author hays  
* @date 2017年1月23日 下午2:54:28 
*/ 
package me.hays.learn4j.jdk.concurrent;

import java.util.PriorityQueue;

/**
 *
 * 1.非阻塞队列中的几个主要方法：

　　add(E e):将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则会抛出异常；

　　remove()：移除队首元素，若移除成功，则返回true；如果移除失败（队列为空），则会抛出异常；

　　offer(E e)：将元素e插入到队列末尾，如果插入成功，则返回true；如果插入失败（即队列已满），则返回false；

　　poll()：移除并获取队首元素，若成功，则返回队首元素；否则返回null；

　　peek()：获取队首元素，若成功，则返回队首元素；否则返回null

2.阻塞队列中的几个主要方法：
　　put(E e)

　　take()

　　offer(E e,long timeout, TimeUnit unit)

　　poll(long timeout, TimeUnit unit)
 *
 */
public class ProductAndConsumer2 {
	private int initSize = 10;
	private PriorityQueue<Integer> priorityQueue = new PriorityQueue<Integer>(initSize);
	
	public static void main(String[] args) {
		ProductAndConsumer2 productAndConsumer = new ProductAndConsumer2();
		Productor productor = productAndConsumer.new Productor();
		Consumer consumer = productAndConsumer.new Consumer();
		
		productor.start();
		consumer.start();
	}
	
	class Productor extends Thread{
		@Override
		public void run() {
			while(true){
				synchronized (priorityQueue) {
					if(priorityQueue.size() == initSize){
						try {
							System.out.println("队列已满，等待取出数据...");
							priorityQueue.wait();
						} catch (Exception e) {
							priorityQueue.notify();
							e.printStackTrace();
						}
					}
					priorityQueue.offer(1);
					System.out.println("从队列增加一个元素，队列剩余"+priorityQueue.size()+"个元素");
					priorityQueue.notify();
				}
			}
		}
	}
	
	class Consumer extends Thread{
		@Override
		public void run() {
			while(true){
				synchronized (priorityQueue) {
					if(priorityQueue.size() == 0){
						try {
							System.out.println("队列为空！等待数据...");
							priorityQueue.wait();
						} catch (InterruptedException e) {
							priorityQueue.notify();
							e.printStackTrace();
						}
					}
					priorityQueue.poll();
					System.out.println("从队列取走一个元素，队列剩余"+priorityQueue.size()+"个元素");
					priorityQueue.notify();
				}
			}
		}
	}
}	
