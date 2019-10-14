package me.hays.learn4j.jdk.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/***
 * 使用socket结合线程池的简单示例
 * 保持socketServer监听，有客户端连接进来就得到一个socket并丢进线程池进行处理
 * 服务端代码
 * @author hays
 */
public class SocketServerTest3 {

	public static void main(String[] args){
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			//指定监听端口8000
			serverSocket = new ServerSocket(8000);
			System.out.println("服务启动，等待连接...");
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
			while(true){//轮询
				//未获取连接时一直阻塞，知道accept
				socket = serverSocket.accept();
				System.out.println("获取socket，开始处理，正在运行线程数：" + threadPoolExecutor.getActiveCount());
				//获取连接就创建丢到线程池里处理
				DealSocketThread dealSocketThread = new SocketServerTest3().new DealSocketThread(socket);
				threadPoolExecutor.execute(dealSocketThread);
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null){
					serverSocket.close();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/***
	 * 专门处理socket交换数据的线程类
	 */
	class DealSocketThread extends Thread{
		private Socket socket;
		public DealSocketThread(Socket socket) {
			this.socket = socket;
		}
		@Override
		public void run() {
			InputStream in = null;
			OutputStream out = null;
			try {
				//从socket中获取交换数据使用的流
				in = socket.getInputStream();
				byte[] buffer = new byte[1024];
				int readed = in.read(buffer);
				String dataFromClient = new String(buffer, 0, readed);
				System.out.println("接收到客户端数据：" + dataFromClient);
				byte[] resp = "服务端响应数据：hello world".getBytes();
				out = socket.getOutputStream();
				out.write(resp);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(in != null){
							in.close();
					} 
					if(out != null){
						out.close();
					} 
					if(socket != null){
						socket.close();
					} 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
