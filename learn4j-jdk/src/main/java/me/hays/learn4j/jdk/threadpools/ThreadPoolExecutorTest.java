/**  
* @Title: ThreadPoolExecutor.java
* @Description: 线程池
* @author hays  
* @date 2017年1月24日 上午9:45:35 
*/ 
package me.hays.learn4j.jdk.threadpools;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class ThreadPoolExecutorTest {
	
	//@Test
	//使用threadPoolExecutor类执行
	public void ThreadPoolExecutorTest1(){
		try {
			ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
					10,  //核心线程池大小
					15,  //最大线程池数量
					1,  //线程没有任务执行时最多保持多久时间会终止，当线程数大于核心线程数量时，线程存活等待的时间
					TimeUnit.SECONDS, //keepAliveTime的时间单位
					new LinkedBlockingQueue<Runnable>(10),  //阻塞队列
					//null,  //线程产生的工厂
					new ThreadPoolExecutor.AbortPolicy() //拒绝处理任务时的策略
					//ThreadPoolExecutor.AbortPolicy:丢弃任务并抛出RejectedExecutionException异常。 
					//ThreadPoolExecutor.DiscardPolicy：也是丢弃任务，但是不抛出异常。 
					//ThreadPoolExecutor.DiscardOldestPolicy：丢弃队列最前面的任务，然后重新尝试执行任务（重复此过程）
					//ThreadPoolExecutor.CallerRunsPolicy：由调用线程处理该任务 
					);
			execute(threadPoolExecutor);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	//在java doc中，并不提倡我们直接使用ThreadPoolExecutor
	//而是使用Executors类中提供的几个静态方法来创建线程池
	@Test
	public void ExecutorsStaticClassTest(){
		try {
			ThreadPoolExecutor newCachedThreadPoolExe = (ThreadPoolExecutor) Executors.newCachedThreadPool();        //创建一个缓冲池，缓冲池容量大小为Integer.MAX_VALUE
			//ExecutorService  newSingleThreadExecutorExe = (ExecutorService ) Executors.newSingleThreadExecutor();   //创建容量为1的缓冲池
			//ThreadPoolExecutor newFixedThreadPoolExe = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);    //创建固定容量大小的缓冲池
			
			/***
			 * public static ExecutorService newFixedThreadPool(int nThreads) {
				    return new ThreadPoolExecutor(nThreads, nThreads,
				                                  0L, TimeUnit.MILLISECONDS,
				                                  new LinkedBlockingQueue<Runnable>());
				}
				public static ExecutorService newSingleThreadExecutor() {
				    return new FinalizableDelegatedExecutorService
				        (new ThreadPoolExecutor(1, 1,
				                                0L, TimeUnit.MILLISECONDS,
				                                new LinkedBlockingQueue<Runnable>()));
				}
				public static ExecutorService newCachedThreadPool() {
				    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
				                                  60L, TimeUnit.SECONDS,
				                                  new SynchronousQueue<Runnable>());
				}
			 * 
			 */
			
			execute(newCachedThreadPoolExe);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	private void execute(ThreadPoolExecutor threadPoolExecutor) {
		try {
			ThreadPoolExecutorTest threadPoolExecutorTest = new ThreadPoolExecutorTest();
			for(int i = 0; i<200; i++){
				MyTask myTask = threadPoolExecutorTest.new MyTask("task" + i);
				threadPoolExecutor.execute(myTask);
				System.out.println("线程池中线程数目："+threadPoolExecutor.getPoolSize()+"，队列中等待执行的任务数目："+
						threadPoolExecutor.getQueue().size()+"，已执行玩别的任务数目："+threadPoolExecutor.getCompletedTaskCount());
			}
		} finally {
			threadPoolExecutor.shutdown();
		}
	}
	
	class MyTask extends Thread{
		private String name;
		public MyTask(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			System.out.println("正在执行线程》》》》" + name);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("线程执行完成》》》》" + name);
		}
	}
}
