package my.chapter03.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.util.ParameterMap;
import org.apache.catalina.util.RequestUtil;

import my.chapter03.connector.SocketInputStream;

public class HttpRequest implements HttpServletRequest {

	private int contentLength;
	private String contentType;

	private String requestURI;
	private String method;
	private String protocol;
	private String requestedSessionId;
	private boolean requestedSessionURL;
	private boolean requestedSessionCookie;

	/**
	 * 当前这个请求对象的参数是否已经解析标志
	 */
	protected boolean parsed = false;

	protected HashMap<Object, Object> headers = new HashMap<>();

	protected ArrayList<Cookie> cookies = new ArrayList<>();

	// 用于存放请求参数
	protected ParameterMap parameters;

	private String queryString;

	private SocketInputStream socketInputStream;

	public void addHeader(String name, String value) {
		name = name.toLowerCase();
		synchronized (headers) {
			ArrayList<String> values = (ArrayList<String>) headers.get(name);
			if (null == values) {
				values = new ArrayList<String>();
				headers.put(name, values);
			}
			values.add(value);
		}
	}

	public void addCookie(Cookie cookie) {
		synchronized (cookies) {
			cookies.add(cookie);
		}
	}

	protected void parseParameters() {
		if (parsed) {
			return;
		}
		ParameterMap results = parameters;
		if (null == results) {
			results = new ParameterMap();
		}
		results.setLocked(false);
		String encoding = getCharacterEncoding();
		if (encoding == null) {
			encoding = "UTF-8";
		}
		
		String queryString = getQueryString();
		//把queryString中的内容解析成参数 名/值对
		try {
			RequestUtil.parseParameters(results, queryString, encoding);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//获取请求中的contentType
		String contentType = getContentType();
		if (contentType == null) {
			contentType = "";
		}
		int semicolon = contentType.indexOf(';');
		if (semicolon >= 0) {
			contentType = contentType.substring(0, semicolon).trim();
		}else {
			contentType = contentType.trim();
		}
		
		//转换content里面的参数
		if ("POST".equals(getMethod()) && getContentLength() > 0 && "application/x-www-form-urlencoded".equals(contentType)) {
			try {
				int max = getContentLength();
				int len = 0;
				byte[] buff = new byte[getContentLength()];
				ServletInputStream is = getInputStream();
				while (len < max) {
					int next = is.read(buff, len, max - len);
					if (next < 0) {
						break;
					}
					len += next;
				}
				is.close();
				
				if (len < max) {
					throw new RuntimeException("Content length mismatch");
				}
				
				RequestUtil.parseParameters(results, buff, encoding);
			}catch(IOException e) {
				e.printStackTrace();
			}catch(RuntimeException e) {
				e.printStackTrace();
			}
			results.setLocked(true);
			parsed = true;
			parameters = results;
		}
	}

	public boolean isRequestedSessionCookie() {
		return requestedSessionCookie;
	}

	public void setRequestedSessionCookie(boolean requestedSessionCookie) {
		this.requestedSessionCookie = requestedSessionCookie;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public boolean isRequestedSessionURL() {
		return requestedSessionURL;
	}

	public void setRequestedSessionURL(boolean requestedSessionURL) {
		this.requestedSessionURL = requestedSessionURL;
	}

	public void setRequestedSessionId(String requestedSessionId) {
		this.requestedSessionId = requestedSessionId;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public HttpRequest(SocketInputStream socketInputStream) {
		this.socketInputStream = socketInputStream;
	}

	@Override
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public int getContentLength() {
		return contentLength;
	}


	@Override
	public String getContentType() {
		return contentType;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<Locale> getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getParameter(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProtocol() {
		return this.protocol;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRealPath(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration<String> getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMethod() {
		return this.method;
	}

	@Override
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getQueryString() {
		return this.queryString;
	}

	@Override
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestURI() {
		return this.requestURI;
	}

	@Override
	public StringBuffer getRequestURL() {
		return new StringBuffer(this.requestURI);
	}

	@Override
	public String getRequestedSessionId() {
		return this.requestedSessionId;
	}

	@Override
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
