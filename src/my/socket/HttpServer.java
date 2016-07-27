package my.socket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import my.entity.Request;
import my.entity.Response;

public class HttpServer {

	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.await();
	}

	private void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try {
			serverSocket = new ServerSocket(port);
		}catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while (!shutdown) {
			Socket socket = null;
			InputStream is = null;
			OutputStream os = null;
			try {
				socket = serverSocket.accept();
				is = socket.getInputStream();
				os = socket.getOutputStream();
				Request req = new Request(is);
				req.parse();
				Response resp = new Response(os);
				resp.setRequest(req);
				resp.sendStaticResource();
				socket.close();
				shutdown = req.getUri().equals(SHUTDOWN_COMMAND);
			}catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
