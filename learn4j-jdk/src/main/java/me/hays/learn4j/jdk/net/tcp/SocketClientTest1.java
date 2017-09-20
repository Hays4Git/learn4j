package me.hays.learn4j.jdk.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/***
 * 最简单的Socket使用示例
 * 客户端代码
 * @author hays
 */
public class SocketClientTest1 {

	public static void main(String[] args) {
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			//指定监听端口8000
			socket = new Socket("localhost", 8000);
			//从socket中获取交换数据使用的流
			byte[] req = "向服务端发送数据：hello world".getBytes();
			out = socket.getOutputStream();
			out.write(req);
			in = socket.getInputStream();
			byte[] buffer = new byte[1024];
			int readed = in.read(buffer);
			String dataFromClient = new String(buffer, 0, readed);
			System.out.println("接收到服务端响应数据：" + dataFromClient);
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			try {
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
