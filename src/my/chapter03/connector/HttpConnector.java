package my.chapter03.connector;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import my.chapter03.processor.HttpProcessor;

public class HttpConnector implements Runnable{

	//是否停止标志
	boolean stopped;
	
	//请求协议
	private String scheme = "http";
	
	public String getScheme() {
		return scheme;
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		while (!stopped) {
			Socket socket = null;
			try {
				//当请求到来时，获取一个socket实例
				socket = serverSocket.accept();
			}catch(IOException e) {
				e.printStackTrace();
				continue;
			}
			//socket交给processor处理
			HttpProcessor httpProcessor = new HttpProcessor(this);
			httpProcessor.process(socket);
		}
	}

	public void start() {
		Thread thread = new Thread(this);
		thread.start();
	}
}
