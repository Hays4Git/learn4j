package me.hays.learn4j.jdk.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/***
 * 使用DatagramSocket发送和接收数据示例
 * 服务端代码，接收数据，反馈数据
 * @author hays
 */
public class DatagramSocketServerTest {

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		try {
			//和TCP方式一样，服务端的DatagramSocket也需要监听端口
			datagramSocket = new DatagramSocket(8000);
			//使用DatagramPacket去接收数据
			//recAllData接收到整个数据包的数据，必须大于客户端发送的
			byte[] recAllData = new byte[1024];
			DatagramPacket datagramPacket = new DatagramPacket(recAllData, 0, recAllData.length);
			//receive和Socket的accept方法一样也是阻塞方法，直到接收到数据
			datagramSocket.receive(datagramPacket);
			//实际客户端发送的数据长度
			byte[] data = datagramPacket.getData();
			int len = datagramPacket.getLength();
			String recStr = new String(data, 0, len);
			System.out.println("服务端接收到数据：" + recStr);
			int clientPort = datagramPacket.getPort();
			System.out.println("客户端端口：" + clientPort);
			InetAddress clientAddress = datagramPacket.getAddress();
			System.out.println("客户端IP：" + clientAddress);
			//有了客户端的信息就可以构造反馈给客户端的数据包
			byte[] backData = "服务端响应".getBytes();
			//使用UDP的方式发送数据是将数据和发送方和接收方相关信息使用DatagramPacket包装在一起
			DatagramPacket backDatagramPacket = new DatagramPacket(backData, 0, backData.length, clientAddress, clientPort);
			//使用DatagramSocket实例对象发送//不保证数据一定被成功传输
			datagramSocket.send(backDatagramPacket);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(datagramSocket != null){
				datagramSocket.close();
			}
		}
	}

}
