package my.chapter05.core;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Container;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Mapper;
import org.apache.catalina.Request;
import org.apache.catalina.Wrapper;

/**
 * 对应某种协议，对应的在该种协议下的映射方式,Mapper只是一个映射工具，
 * 其本身内部不存任何的映射内容，映射内容都是存储在其关联的Container中
 * 当使用Mapper进行映射的时候，所使用的数据都存在context中，映射的是
 * 一个请求对应的wrapper(一个wrapper中嵌一个servlet)
 * @author Administrator
 *
 */
public class SimpleContextMapper implements Mapper{

	protected SimpleContext context;
	protected String protocol;
	
	@Override
	public Container getContainer() {
		return context;
	}

	@Override
	public void setContainer(Container container) {
		if (!(container instanceof SimpleContext)) {
			throw new IllegalArgumentException("Illegal container type");
		}
		this.context = (SimpleContext)container;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public Container map(Request request, boolean update) {
		HttpServletRequest hreq = (HttpServletRequest) request.getRequest();
		String contextPath = hreq.getContextPath();
		String requestUri = ((HttpRequest) request).getDecodedRequestURI();
		String relativeUri = requestUri.substring(contextPath.length());
		Wrapper wrapper = null;
		String servletPath = relativeUri;
		String pathInfo = null;
		String name = context.findServletMapping(relativeUri);
		if (name != null) {
			wrapper = (Wrapper) context.findChild(name);
		}
		return wrapper;
	}

	
}
