package my.chapter05.core;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/**
 * SimpleWrapper的基本阀
 * @author Administrator
 *
 */
public class SimpleWrapperValve implements Valve, Contained{

	protected Container container;
	
	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public void invoke(Request request, Response response, ValveContext context) throws IOException, ServletException {
		SimpleWrapper wrapper = (SimpleWrapper) getContainer();
		ServletRequest sreq = request.getRequest();
		ServletResponse sresp = response.getResponse();
		Servlet servlet = null;
		HttpServletRequest hsreq = null;
		if (sreq instanceof HttpServletRequest) {
			hsreq = (HttpServletRequest) sreq;
		}
		HttpServletResponse hresp = null;
		if (sresp instanceof HttpServletResponse) {
			hresp = (HttpServletResponse) sresp;
		}
		try {
			servlet = wrapper.allocate();
			if (hsreq != null && hresp != null) {
				servlet.service(hsreq, hresp);
			} else {
				servlet.service(sreq, sresp);
			}
		}catch(ServletException e) {
			e.printStackTrace();
		}
	}

}
