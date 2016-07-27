package my.chapter03.connector;

/**
 * 请求第一行
 * @author Administrator
 *
 */
public class HttpRequestLine {

	//获取到的方法字节数组
	private byte[] method;
	//获取到的协议字节数组
	private byte[] protocol;
	//获取uri
	private byte[] uri;
	
	public byte[] getMethod() {
		return method;
	}
	
	public byte[] getProtocol() {
		return protocol;
	}
	
	public byte[] getUri() {
		return uri;
	}
	
	public int indexOf(String str) {
		for(int i = 0; i < str.length(); i++) {
			//此处为自己实现的HttpRequestLine中的indexOf方法，目前只支持查找单个字符
			if (uri[i] == str.getBytes()[0]) {
				return i;
			}
		}
		return -1;
	}
}
