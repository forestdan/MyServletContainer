package my.chapter14.startup;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Stopper {
	
	public static void main(String[] args) {
		int port = 8005;
		try {
			Socket socket = new Socket("127.0.0.1", port);
			OutputStream output = socket.getOutputStream();
			String command = "SHUTDOWN";
			for (int i = 0; i < command.length(); i++) {
				output.write(command.charAt(i));
			}
			output.flush();
			output.close();
			socket.close();
			System.out.println("The server successfully shut down");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
