package me.hays.learn4j.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
/****
 * @author hays
 * 客户端代码
 */
public class SocketClientTest {

	public static void main(String[] args) throws IOException {
		createClient("localhost", 8000);
	}
	
	public static void createClient(String hostname, int port) throws IOException{
		//套路1、获取通道2、设置非阻塞3、监听端口4、注册连接
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		socketChannel.connect(new InetSocketAddress(hostname, 8000));
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		//轮询处理感兴趣的事件
		while(selector.select() > 0){
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			if(it.hasNext()){
				SelectionKey selectionKey = it.next();
				it.remove();
				if(selectionKey.isConnectable()){
				    socketChannel = (SocketChannel) selectionKey.channel();
                    if(socketChannel.isConnectionPending()){  // 如果正在连接，则完成连接  //没有的话会报错
                    	socketChannel.finishConnect();  
                    }  
				    socketChannel.configureBlocking(false);
				    socketChannel.write(ByteBuffer.wrap(new String("向服务端发送信息：hello server").getBytes("utf-8")));
				    socketChannel.register(selector, SelectionKey.OP_READ);
				}else if(selectionKey.isReadable()){
					System.out.println("read......");
					socketChannel = (SocketChannel) selectionKey.channel();
					socketChannel.configureBlocking(false);
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					socketChannel.read(readBuffer);
					byte[] data = readBuffer.array();
					String msg = new String(data, "utf-8").trim();
					System.out.println("客户端接收到消息：" + msg);
				}else{
					System.out.println("????");
				}
			}
		}
	}
}
