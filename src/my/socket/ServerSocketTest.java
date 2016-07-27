package my.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerSocketTest
 * @author Administrator
 *
 */
public class ServerSocketTest {

	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(8080);
			Socket socket = null;
			while((socket = server.accept()) != null) {
				System.out.println("接收到了客户端的请求");
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				while((line = reader.readLine()) != null) {
					System.out.println("读到了的内容" + line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
