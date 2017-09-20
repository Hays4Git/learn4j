package me.hays.learn4j.jdk.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/****
 * @author hays
 */
public class ServerSocketServerTest {

	public static void main(String[] args) throws IOException {
		createServer(8000);
	}

	public static void createServer(int port) throws IOException{
		//使用ServerSocketChannel的静态方法打开一个ServerSocket的通道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.bind(new InetSocketAddress(port));//监听端口
		serverSocketChannel.configureBlocking(false);//设置非阻塞
		Selector selector = Selector.open();//使用Selector
		//注册感兴趣的事件，当接收到请求时才进行处理，其他情况下阻塞
		//Selector一起使用时，Channel必须处于非阻塞模式下，所以不能将FileChannel与Selector一起使用
		//SelectionKey.OP_ACCEPT; SelectionKey.OP_CONNECT; SelectionKey.OP_READ; SelectionKey.OP_WRITE
		//一般ServerSocketChannel只注册accept事件，read和write事件是注册到accept的SocketChannel中
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		while(selector.select() > 0){//轮询//已更新其准备就绪操作集的键的数目，该数目可能为零 
			Iterator<SelectionKey> it = selector.selectedKeys().iterator();
			while (it.hasNext()) {
				SelectionKey selectionKey = (SelectionKey) it.next();
				it.remove();
				if(selectionKey.isAcceptable()){
					System.out.println("accept......");
					SocketChannel socketChannel = serverSocketChannel.accept();
					socketChannel.configureBlocking(false);
					ByteBuffer sendBuffer = ByteBuffer.wrap("向客户端发送消息：hello client!".getBytes("utf-8"));//将 byte 数组包装到缓冲区中
					socketChannel.write(sendBuffer);
					socketChannel.register(selector, SelectionKey.OP_READ);
					System.out.println("registe read and write......");
				}else if(selectionKey.isReadable()){
					System.out.println("read......");
					SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
					socketChannel.configureBlocking(false);
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					socketChannel.read(readBuffer);
					byte[] data = readBuffer.array();
					String msg = new String(data, "utf-8").trim();
					System.out.println("服务端接收到消息：" + msg);
				}else{
					System.out.println("????");
				}
			}
		}
	}
}
