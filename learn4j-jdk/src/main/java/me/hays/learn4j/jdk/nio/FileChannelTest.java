package me.hays.learn4j.jdk.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
	public static final int BUFFER_SIZE = 2048;

	public static void main(String[] args) throws IOException {
		String fileName = "fileChannelTest.txt";
		String fileNameTo = "fileChannelTestDest.txt";
		writeFile(fileName);
		readFile(fileName);
		copyFile(fileName, fileNameTo);
	}

	/***
	 * 写文件*基本用法
	 * @param fileName 文件路径
	 * @throws IOException
	 * 使用buffer读写数据的一般套路：
	 * 1、写入数据到Buffer
	 * 2、调用flip()方法
	 * 3、从Buffer中读取数据
	 * 4、调用clear()方法或者compact()方法
	 * clear()方法会清空整个缓冲区。compact()方法只会清除已经读过的数据。
	 * 任何未读的数据都被移到缓冲区的起始处，新写入的数据将放到缓冲区未读数据的后面。
	 */
	public static void writeFile(String fileName) throws IOException {
		FileChannel wFileChannel = null;
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(fileName, "rw");
			wFileChannel = randomAccessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);// 分配新的字节缓存区
			buffer.put("hello world!!".getBytes());// 往缓冲器中写入数据// putChar,putDobule...
			buffer.flip();// 反转此缓冲区，因为刚刚是放入数据，现在要取出来
			while (buffer.hasRemaining()) {// 查看是否有元素，如有返回true
				wFileChannel.write(buffer);// write方法是FileChannel提供的
			}
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();//randomAccessFile关闭的时候，通道会自动关闭
			}
		}
	}
	
	/***
	 * 读文件，基本用法
	 * @param fileName 文件路径
	 * @throws IOException
	 */
	public static void readFile(String fileName) throws IOException{
		FileChannel rFileChannel = null;
		RandomAccessFile randomAccessFile = null;
		try {
			randomAccessFile = new RandomAccessFile(fileName, "r");
			rFileChannel = randomAccessFile.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			int readBytes = rFileChannel.read(buffer);
			while(readBytes != -1){//如果该通道已到达流的末尾，则返回 -1
				buffer.flip();
				while(buffer.hasRemaining()){
					System.out.print((char) buffer.get());
				}
				buffer.clear();//读完后清空，给下次继续读
				readBytes = rFileChannel.read(buffer);  
			}
		} finally {
			if (randomAccessFile != null) {
				randomAccessFile.close();//randomAccessFile关闭的时候，通道会自动关闭
			}
		}
	}

	/***
	 * 文件复制**transferTo和transferFrom的使用
	 * @param srcFileName 源文件
	 * @param destFileName 目标文件
	 * @throws IOException
	 */
	public static void copyFile(String srcFileName, String destFileName) throws IOException{
		RandomAccessFile srcR = null;
		RandomAccessFile destR = null;
		try {
			srcR = new RandomAccessFile(srcFileName, "r");
			FileChannel srcChannel = srcR.getChannel();
			destR = new RandomAccessFile(destFileName, "rw");
			FileChannel destChannel = destR.getChannel();
			srcChannel.transferTo(0, srcChannel.size(), destChannel);
			//destChannel.transferFrom(destChannel, 0, srcChannel.size());//和上面语句实现效果一致
			srcChannel.close();
			destChannel.close();
		} finally {
			if (srcR != null) {
				srcR.close();//randomAccessFile关闭的时候，通道会自动关闭
			}
			if (destR != null) {
				destR.close();
			}
		}
	}
	
	
	
}
