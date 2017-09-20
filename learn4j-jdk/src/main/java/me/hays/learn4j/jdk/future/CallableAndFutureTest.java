/**  
* @Title: CallAndFutureTest.java
* @Description: 

callable和future示例
futureTask

* @author hays  
* @date 2017年1月25日 上午9:42:18 
*/ 
package me.hays.learn4j.jdk.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableAndFutureTest {

	/**
	* @Title: main
	* @Description: TODO
	* @param @param args    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void main(String[] args) {
		CallableAndFutureTest callAndFutureTest = new CallableAndFutureTest();
		
		ExecutorService executors = Executors.newCachedThreadPool();
		//1、
		//Future<String> result = executors.submit(callAndFutureTest.new MyTask());
		
		
		//2、
		MyTask myTask = callAndFutureTest.new MyTask();
		FutureTask<String> result = new FutureTask<String>(myTask); 
		executors.submit(result);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("主线程执行任务");
		try {
			//System.out.println("子线程执行结果" + result.get());
			try {
				System.out.println("子线程是否执行完成" + result.isDone());
				System.out.println("子线程执行结果" + result.get(1, TimeUnit.SECONDS));
			} catch (TimeoutException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		System.out.println("主线程执行完毕");
	}
	
	class MyTask implements Callable<String>{

		@Override
		public String call() throws Exception {
			System.out.println(Thread.currentThread().getName() + "正在执行call");
			Thread.sleep(4000);
			System.out.println(Thread.currentThread().getName() + "执行完成");
			return Thread.currentThread().getName();
		}
		
	}
	
}
