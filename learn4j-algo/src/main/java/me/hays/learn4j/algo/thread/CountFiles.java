package me.hays.learn4j.algo.thread;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

/****
 * 统计一个文件系统的下的文件数，
 * 在当前目录下进行遍历，如果遍历到它是一个目录，
 * 则另起一个线程，再统计该目录下所有文件（包括目录和文件）；
 * 如果是遍历到它是一个文件，则无需启线程，直接统计。
 * 最后主线程要统计所有线程得到的文件数的和。
 * 举例：
	/usr
		/local
			/a.txt
			/b.jpg
			/lib
		/bin
			/crypto.so
		/a.txt
		/b.txt
	说明：标“加粗”的为文件夹，其他为一个普通文件。
	输入：一个路径 /usr
	输出：该路径下（含输入路径），所有文件的总数。该举例中为：9个。
 * @author lhs
 */
public class CountFiles {

	private static final AtomicInteger count = new AtomicInteger(0);
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\lhsu2014\\Desktop\\aaa");
		if(file.exists()){
			CounterThread counterThread = new CounterThread(file);
			counterThread.start();
			try {
				counterThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("文件和文件夹总数：" + count);
	}
	
	public static class CounterThread extends Thread{
		private File file;
		public CounterThread(File file){
			this.file = file;
		}
		@Override
		public void run() {
			count.incrementAndGet();
			if(file != null && file.exists()){//文件存在
				if(file.isDirectory()){
					File[] files = file.listFiles();
					for(File fileTmp : files){
						if(fileTmp.isFile()){//文件直接计数加一
							count.incrementAndGet();
						}else{//文件夹
							CounterThread counterThread = new CounterThread(fileTmp);
							counterThread.start();
							try {
								counterThread.join();//并行变串行//起线程其实意义不大
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}
}
