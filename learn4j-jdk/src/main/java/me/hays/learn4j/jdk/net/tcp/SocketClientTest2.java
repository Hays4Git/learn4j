package me.hays.learn4j.jdk.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/***
 * 使用同一个socket发送多组数据
 * 客户端代码使用for循环
 * 服务端也要相应的多次读取
 * @author hays
 */
public class SocketClientTest2 {

	public static void main(String[] args) {
		Socket socket = null;
		InputStream in = null;
		OutputStream out = null;
		try {
			//指定监听端口8000
			socket = new Socket("localhost", 8000);
			//从socket中获取交换数据使用的流
			out = socket.getOutputStream();
			in = socket.getInputStream();
			String[] data = new String[]{"test1", "test2", "test3", SocketServerTest2.ENDFLAG};
			for(int i = 0; i < data.length; i++){
				out.write(data[i].getBytes());
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
