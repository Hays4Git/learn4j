package me.hays.learn4j.jdk.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/***
 * 模拟多个客户端发送数据
 * @author hays
 */
public class SocketClientTest3 {

	public static void main(String[] args) {
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			for(int i = 0; i < 20; i++){
				//指定监听端口8000
				socket = new Socket("localhost", 8000);
				//从socket中获取交换数据使用的流
				byte[] req = ("第"+ i +"次向服务端发送数据：hello world").getBytes();
				out = socket.getOutputStream();
				out.write(req);
				in = socket.getInputStream();
				byte[] buffer = new byte[1024];
				int readed = in.read(buffer);
				String dataFromClient = new String(buffer, 0, readed);
				System.out.println("接收到服务端响应数据：" + dataFromClient);
			}
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
