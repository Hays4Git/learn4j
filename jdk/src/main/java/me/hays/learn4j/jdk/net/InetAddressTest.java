package me.hays.learn4j.jdk.net;

import java.io.IOException;
import java.net.InetAddress;

public class InetAddressTest {

	public static void main(String[] args) throws IOException {
		/***
		 * InetAddress提供了5个静态方法来获取InetAddress相关实例
		 * getByAddress(byte[] addr)
		 * getByAddress(String host, byte[] addr)
		 * getByName(String host) 
		 * getLocalHost()
		 */
		 //使用域名创建对象
        InetAddress inet1 = InetAddress.getByName("127.0.0.1");
        System.out.println(inet1.getHostName());
        InetAddress inet2 = InetAddress.getByName("www.baidu.com");
        System.out.println(inet2.getHostAddress());
        InetAddress inet3 = InetAddress.getLocalHost();
        System.out.println(inet3.getHostAddress());
        //InetAddress实例提供了一些检测方法，一般比较少用到
        System.out.println("是否可达：" + inet3.isReachable(1000));
        System.out.println("是否是通配符地址的实用例行程序：" + inet3.isAnyLocalAddress());
	}

}
