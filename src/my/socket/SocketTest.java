package my.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Socket测试
 * @author Administrator
 *
 */
public class SocketTest {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8080);
			OutputStream os = socket.getOutputStream();
//			BufferedReader reader = null;
			PrintWriter out = new PrintWriter(os);
//			StringBuffer buffer = null;
			while(true) {
				System.out.println("请输入：/t");
				String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
				System.out.println("客户端请求字符串" + s);
				out.println(s);
				out.flush();
//				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//				String line = "";
//				buffer = new StringBuffer();
//				while ((line = reader.readLine()) != null) {
//					buffer.append(line);
//				}
//				System.out.println(buffer.toString());
//				if (buffer.toString().equals("END")) {
//					break;
//				}
			}
//			reader.close();
//			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
