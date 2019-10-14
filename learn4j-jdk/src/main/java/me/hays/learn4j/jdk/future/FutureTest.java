/**  
* @Title: FutureTest.java
* @Description: 
* 
* 
* 理解java中的future
* 是一个接口FutureTask
* 1）判断是否完成
* 2）中断任务
* 3）获取任务的执行结果
* 
* 
* @author hays  
* @date 2017年1月23日 下午4:42:17 
*/ 
package me.hays.learn4j.jdk.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {

	/**
	* @throws ExecutionException 
	* @throws InterruptedException 
	* @Title: main
	* @Description: TODO
	* @param @param args    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 100; i++) {
			Future<String> exeThreadNames = executorService.submit(new MyTask());
			System.out.println(exeThreadNames.get());//get方法是阻塞方法，直到返回结果
		}
	}

	public static class MyTask implements Callable<String>{

		@Override
		public String call() throws Exception {
			return "call...";
		}
		
	}
}
