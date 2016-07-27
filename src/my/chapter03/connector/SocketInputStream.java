package my.chapter03.connector;

import java.io.BufferedInputStream;
import java.io.InputStream;

/**
 * 此类在tomcat4之后就不存在了，自己实现
 *
 */
public class SocketInputStream {

	private BufferedInputStream inputStream;
	
	public SocketInputStream(InputStream is, int bufferSize) {
		this.inputStream = new BufferedInputStream(is, bufferSize);
	}
	
	public HttpRequestLine readRequestLine(HttpRequestLine requestLine) {
		return null;
	}
	
	public HttpHeader readHead() {
		return null;
	}
	
}
