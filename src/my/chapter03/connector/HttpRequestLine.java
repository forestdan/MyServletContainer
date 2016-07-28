package my.chapter03.connector;

/**
 * 请求第一行
 * 
 * @author Administrator
 *
 */
public final class HttpRequestLine {

	public static final int INITIAL_METHOD_SIZE = 8;
	public static final int INITIAL_URI_SIZE = 64;
	public static final int INITIAL_PROTOCOL_SIZE = 8;
	public static final int MAX_METHOD_SIZE = 1024;
	public static final int MAX_URI_SIZE = 32768;
	public static final int MAX_PROTOCOL_SIZE = 1024;

	public char[] method;
	public int methodEnd;
	public char[] uri;
	public int uriEnd;
	public char[] protocol;
	public int protocolEnd;

	public HttpRequestLine() {
		this(new char[INITIAL_METHOD_SIZE], 0, new char[INITIAL_URI_SIZE], 0, new char[INITIAL_PROTOCOL_SIZE], 0);
	}

	public HttpRequestLine(char[] method, int methodEnd, char[] uri, int uriEnd, char[] protocol, int protocolEnd) {
		this.method = method;
		this.methodEnd = methodEnd;
		this.uri = uri;
		this.uriEnd = uriEnd;
		this.protocol = protocol;
		this.protocolEnd = protocolEnd;
	}
	
	public int indexOf(char[] chars) {
		return indexOf(chars.toString());
	}
	
	public int indexOf(String str) {
		String uriStr = uri.toString();
		return uriStr.indexOf(str);
	}
	
	/**
    * Release all object references, and initialize instance variables, in
    * preparation for reuse of this object.
    * 回收内部数据
    */
   public void recycle() {

       methodEnd = 0;
       uriEnd = 0;
       protocolEnd = 0;

   }
}
