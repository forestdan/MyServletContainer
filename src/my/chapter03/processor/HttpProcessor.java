package my.chapter03.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;

import my.chapter03.connector.HttpConnector;
import my.chapter03.connector.HttpRequestLine;
import my.chapter03.connector.SocketInputStream;
import my.chapter03.entity.HttpRequest;
import my.chapter03.entity.HttpResponse;

public class HttpProcessor {

	private HttpConnector connector;

	private HttpRequest request;

	private HttpResponse response;

	private HttpRequestLine requestLine = new HttpRequestLine();

	public HttpProcessor(HttpConnector connector) {
		this.connector = connector;
	}

	public void process(Socket socket) {
		OutputStream os = null;
		SocketInputStream is = null;
		try {
			// 获取socket输出输入流
			os = socket.getOutputStream();
			is = new SocketInputStream(socket.getInputStream(), 2048);

			request = new HttpRequest(is);
			response = new HttpResponse(os);
			response.setRequest(request);
			response.setHeader("Server", "Pyrmont Servlet Container");
			parseRequest(is, os);
			parseHeaders(is);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private void parseHeaders(SocketInputStream is) {
		// TODO parseHeaders实现解析请求

	}

	private void parseRequest(SocketInputStream is, OutputStream os) throws IOException, ServletException{
		//把is中获取到的输出流解析到requestLine中去
		is.readRequestLine(requestLine);
		String method = new String(requestLine.getMethod(), 0, requestLine.getMethod().length);
		String uri = null;
		String protocol = new String(requestLine.getProtocol(), 0, requestLine.getProtocol().length);
		if (method.isEmpty()) {
			throw new ServletException("Missing HTTP request method");
		}else if (requestLine.getUri().length < 1) {
			throw new ServletException("Missing HTTP request URI");
		}
		
		//开始解析uri
		int questionIndex = requestLine.indexOf("?");
		if (questionIndex > -1) {
			byte[] temp = requestLine.getUri();
			//获取请求参数串
			request.setQueryString(new String(temp, questionIndex + 1, temp.length - questionIndex - 1));
			//获取不含有参数的uri
			uri = new String(temp, 0, questionIndex);
		}else {
			//获取请求参数串(无请求参数)
			request.setQueryString(null);
			//获取不含有参数的uri
			uri = new String(requestLine.getUri(), 0, questionIndex);
		}
		
		//检查当uri是绝对路径的时候的情况 也就是请求报文中直接就是http://xxxxxx.com类似的情况
		if (!uri.startsWith("/")) {
			int pos = uri.indexOf("://");
			if (pos != -1) {
				pos = uri.indexOf('/', pos + 3);
				if (pos == -1) {
					uri = "";
				}else {
					uri = uri.substring(pos);
				}
			}
		}
		
	}

}
