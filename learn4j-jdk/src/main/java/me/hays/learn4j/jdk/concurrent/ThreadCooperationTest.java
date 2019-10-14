/**  
* @Title: ThreadCooperationTest.java
* @Description:
* 
* 线程协作的应用
* notify()
* wait()
* notifyAll()
* 
* @author hays  
* @date 2017年2月6日 下午2:53:43 
*/ 
package me.hays.learn4j.jdk.concurrent;

public class ThreadCooperationTest {

	public Object object = new Object();
	/**
	* @Title: main
	* @Description: TODO
	* @param @param args    设定文件
	* @return void    返回类型
	* @throws
	*/
	public static void main(String[] args) {
		ThreadCooperationTest threadCooperationTest = new ThreadCooperationTest();
		
		MyThread1 thread1 = threadCooperationTest.new MyThread1();
		MyThread2 thread2 = threadCooperationTest.new MyThread2();
		
		thread1.start();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		thread2.start();
	}

	class MyThread1 extends Thread{
		@Override
		public void run() {
			synchronized (object) {
				try {
					System.out.println("wait方法必须在synchronized方法或者synchronized代码块中调用。");
					System.out.println(String.format("线程%s调用object的wait方法",Thread.currentThread().getName()));
					object.wait();
					System.out.println(String.format("线程%s获得锁", Thread.currentThread().getName()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	} 
	
	class MyThread2 extends Thread{
		@Override
		public void run() {
			synchronized (object) {
				System.out.println(String.format("线程%s调用object的notify方法",Thread.currentThread().getName()));
				object.notify();
				System.out.println(String.format("线程%s在运行完synchronized才释放锁", Thread.currentThread().getName()));
			}
		}
	} 
}
