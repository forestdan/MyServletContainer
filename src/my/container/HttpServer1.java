package my.container;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import my.entity.Request;
import my.entity.Response;

public class HttpServer1 {

	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	private boolean shutdown = false;

	public static void main(String[] args) {
		HttpServer1 server = new HttpServer1();
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
				if(req.getUri().startsWith("/servlet/")) {
					ServletProcessor servletProcessor = new ServletProcessor();
					servletProcessor.process(req, resp);
				}else {
					StaticResourceProcessor staticResourceProcessor = new StaticResourceProcessor();
					staticResourceProcessor.process(req, resp);
				}
				socket.close();
				shutdown = req.getUri().equals(SHUTDOWN_COMMAND);
			}catch (IOException e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
