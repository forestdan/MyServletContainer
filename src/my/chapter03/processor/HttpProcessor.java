package my.chapter03.processor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;

import my.chapter03.connector.HttpConnector;
import my.chapter03.connector.HttpHeader;
import my.chapter03.connector.HttpRequestLine;
import my.chapter03.connector.SocketInputStream;
import my.chapter03.entity.HttpRequest;
import my.chapter03.entity.HttpResponse;
import my.chapter03.util.RequestUtil;

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
			//check if this is a request for a servlet or a static resource
		      //a request for a servlet begins with "/servlet/"
		      if (request.getRequestURI().startsWith("/servlet/")) {
		        ServletProcessor processor = new ServletProcessor();
		        processor.process(request, response);
		      }
		      else {
		        StaticResourceProcessor processor = new StaticResourceProcessor();
		        processor.process(request, response);
		      }
		      // Close the socket
		      socket.close();
		      // no shutdown for this application
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

	private void parseHeaders(SocketInputStream input) throws ServletException, IOException {
		// 一个循环读取一个head value对
		while (true) {
			HttpHeader header = new HttpHeader();
			input.readHeader(header);
			if (header.nameEnd == 0) {
				if (header.valueEnd == 0) {
					return;
				} else {
					throw new ServletException("httpProcessor.parseHeaders.colon");
				}
			}

			String name = new String(header.name, 0, header.nameEnd);
			String value = new String(header.value, 0, header.valueEnd);

			request.addHeader(name, value);

			if (name.equals("cookie")) {
				Cookie[] cookies = RequestUtil.parseCookieHeader(value);
				for (int i = 0; i < cookies.length; i++) {
					if (cookies[i].getName().equals("jsessionid")) {
						if (!request.isRequestedSessionIdFromCookie()) {
							request.setRequestedSessionId(cookies[i].getValue());
							request.setRequestedSessionURL(false);
							request.setRequestedSessionCookie(true);
						}
					}
					request.addCookie(cookies[i]);
				}
			} else if (name.equals("content-length")) {
				int n = -1;
				try {
					n = Integer.parseInt(value);
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
				request.setContentLength(n);
			} else if (name.equals("content-type")) {
				request.setContentType(value);
			}
		}

	}

	private void parseRequest(SocketInputStream is, OutputStream os) throws IOException, ServletException {
		// 把is中获取到的输出流解析到requestLine中去
		is.readRequestLine(requestLine);
		String method = new String(requestLine.method, 0, requestLine.methodEnd);
		String uri = null;
		String protocol = new String(requestLine.protocol, 0, requestLine.protocolEnd);
		if (method.isEmpty()) {
			throw new ServletException("Missing HTTP request method");
		} else if (requestLine.uriEnd < 1) {
			throw new ServletException("Missing HTTP request URI");
		}

		// 开始解析uri
		int questionIndex = requestLine.indexOf("?");
		if (questionIndex > -1) {
			// 获取请求参数串
			request.setQueryString(
					new String(requestLine.uri, questionIndex + 1, requestLine.uriEnd - questionIndex - 1));
			// 获取不含有参数的uri
			uri = new String(requestLine.uri, 0, questionIndex);
		} else {
			// 获取请求参数串(无请求参数)
			request.setQueryString(null);
			// 获取不含有参数的uri
			uri = new String(requestLine.uri, 0, requestLine.uriEnd);
		}

		// 检查当uri是绝对路径的时候的情况 也就是请求报文中直接就是http://xxxxxx.com类似的情况
		if (!uri.startsWith("/")) {
			int pos = uri.indexOf("://");
			if (pos != -1) {
				pos = uri.indexOf('/', pos + 3);
				if (pos == -1) {
					uri = "";
				} else {
					uri = uri.substring(pos);
				}
			}
		}

		// 处理保存在url中的sessionid
		String match = ";jsessionid=";
		int semicolon = uri.indexOf(match);
		if (semicolon >= 0) {
			String rest = uri.substring(semicolon + match.length());
			int semicolon2 = rest.indexOf(';');
			if (semicolon2 >= 0) {
				request.setRequestedSessionId(rest.substring(0, semicolon2));
				rest = rest.substring(semicolon2);
			} else {
				request.setRequestedSessionId(rest);
				rest = "";
			}
			request.setRequestedSessionURL(true);
			uri = uri.substring(0, semicolon) + rest;
		} else {
			request.setRequestedSessionId(null);
			request.setRequestedSessionURL(false);
		}

		// 修正url中的不正确部分
		String normalizedUri = normalize(uri);
		// 把解析后的参数放入HttpRequest对象中
		((HttpRequest) request).setMethod(method);
		request.setProtocol(protocol);
		if (normalizedUri != null) {
			((HttpRequest) request).setRequestURI(normalizedUri);
		} else {
			((HttpRequest) request).setRequestURI(uri);
		}

		if (normalizedUri == null) {
			throw new ServletException("Invalid URI: " + uri + "'");
		}
	}

	/**
	 * Return a context-relative path, beginning with a "/", that represents the
	 * canonical version of the specified path after ".." and "." elements are
	 * resolved out. If the specified path attempts to go outside the boundaries
	 * of the current context (i.e. too many ".." path elements are present),
	 * return <code>null</code> instead.
	 *
	 * @param path
	 *            Path to be normalized
	 */
	protected String normalize(String path) {
		if (path == null)
			return null;
		// Create a place for the normalized path
		String normalized = path;

		// Normalize "/%7E" and "/%7e" at the beginning to "/~"
		if (normalized.startsWith("/%7E") || normalized.startsWith("/%7e"))
			normalized = "/~" + normalized.substring(4);

		// Prevent encoding '%', '/', '.' and '\', which are special reserved
		// characters
		if ((normalized.indexOf("%25") >= 0) || (normalized.indexOf("%2F") >= 0) || (normalized.indexOf("%2E") >= 0)
				|| (normalized.indexOf("%5C") >= 0) || (normalized.indexOf("%2f") >= 0)
				|| (normalized.indexOf("%2e") >= 0) || (normalized.indexOf("%5c") >= 0)) {
			return null;
		}

		if (normalized.equals("/."))
			return "/";

		// Normalize the slashes and add leading slash if necessary
		if (normalized.indexOf('\\') >= 0)
			normalized = normalized.replace('\\', '/');
		if (!normalized.startsWith("/"))
			normalized = "/" + normalized;

		// Resolve occurrences of "//" in the normalized path
		while (true) {
			int index = normalized.indexOf("//");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index) + normalized.substring(index + 1);
		}

		// Resolve occurrences of "/./" in the normalized path
		while (true) {
			int index = normalized.indexOf("/./");
			if (index < 0)
				break;
			normalized = normalized.substring(0, index) + normalized.substring(index + 2);
		}

		// Resolve occurrences of "/../" in the normalized path
		while (true) {
			int index = normalized.indexOf("/../");
			if (index < 0)
				break;
			if (index == 0)
				return (null); // Trying to go outside our context
			int index2 = normalized.lastIndexOf('/', index - 1);
			normalized = normalized.substring(0, index2) + normalized.substring(index + 3);
		}

		// Declare occurrences of "/..." (three or more dots) to be invalid
		// (on some Windows platforms this walks the directory tree!!!)
		if (normalized.indexOf("/...") >= 0)
			return (null);

		// Return the normalized path that we have completed
		return (normalized);

	}

}
