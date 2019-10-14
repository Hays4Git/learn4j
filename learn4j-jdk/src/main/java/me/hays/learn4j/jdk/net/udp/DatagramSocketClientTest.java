package me.hays.learn4j.jdk.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/***
 * 使用DatagramSocket发送和接收数据示例
 * 客户端代码，发送数据
 * @author hays
 */
public class DatagramSocketClientTest {

	public static void main(String[] args) {
		DatagramSocket datagramSocket = null;
		try {
			//提供4个构造方法//无参使用系统分配、指定port、指定port和InetAddress、传入SocketAddress
			datagramSocket = new DatagramSocket();
			//要发送的数据//数据必须发送byte[]//IO流不一定需要了
			byte[] data = "hellp DatagramSocket!".getBytes();
			//发送到的主机地址
			String host = "localhost";
			//发送到的端口
			int port = 8000;
			//封装成InetAddress
			InetAddress inetAddress = InetAddress.getByName(host);
			//使用UDP的方式发送数据是将数据和发送方和接收方相关信息使用DatagramPacket包装在一起
			DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length, inetAddress, port);
			//使用DatagramSocket实例对象发送//不保证数据一定被成功传输
			datagramSocket.send(datagramPacket);
			//接收响应信息
			byte[] recAllData = new byte[1024];
			DatagramPacket backDatagramPacket = new DatagramPacket(recAllData, 0, recAllData.length);
			//receive和Socket的accept方法一样也是阻塞方法，直到接收到数据
			datagramSocket.receive(backDatagramPacket);
			System.out.println("接收到服务端反馈：" + new String(backDatagramPacket.getData(), 0, backDatagramPacket.getLength()));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(datagramSocket != null){
				try {
					datagramSocket.close();
				} catch (Exception e2) {
				}
			}
		}
	}

}
