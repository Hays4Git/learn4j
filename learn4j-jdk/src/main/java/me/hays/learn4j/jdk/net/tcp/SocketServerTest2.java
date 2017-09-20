package me.hays.learn4j.jdk.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * 使用同一个socket发送多组数据
 * 客户端代码使用for循环
 * 服务端也要相应的多次读取
 * @author hays
 */
public class SocketServerTest2 {

	public static final String ENDFLAG = "==========end==========";//结束标记
	public static void main(String[] args){
		ServerSocket serverSocket = null;
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			//指定监听端口8000
			serverSocket = new ServerSocket(8000);
			//未获取连接时一直阻塞，知道accept
			socket = serverSocket.accept();
			//从socket中获取交换数据使用的流
			in = socket.getInputStream();
			out = socket.getOutputStream();
			String dataFromClient = null;
			while(!ENDFLAG.equals(dataFromClient)){
				byte[] buffer = new byte[1024];
				int readed = in.read(buffer);
				dataFromClient = new String(buffer, 0, readed);
				System.out.println("接收到客户端数据：" + dataFromClient);
				byte[] resp = "服务端响应数据：hello world".getBytes();
				out.write(resp);
			}
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
				if(serverSocket != null){
					serverSocket.close();
				} 
				if(socket != null){
					socket.close();
				} 
				if(in != null){
					in.close();
				} 
				if(out != null){
					out.close();
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
