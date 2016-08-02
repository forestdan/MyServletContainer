package my.chapter07.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.HttpRequest;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;
import org.apache.catalina.Wrapper;

/**
 * 用来执行context子容器（Wrapper）的阀
 * @author Administrator
 *
 */
public class SimpleContextValve implements Valve, Contained {

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
	public void invoke(Request request, Response response, ValveContext valuecontext)
			throws IOException, ServletException {
		if (!(request.getRequest() instanceof HttpServletRequest)
				|| !(request.getResponse() instanceof HttpServletResponse)) {
			return;
		}
		String requestUri = ((HttpRequest) request).getDecodedRequestURI();
		Context context = (Context) getContainer();
		Wrapper wrapper = null;
		try {
			wrapper = (Wrapper) context.map(request, true);
		} catch (IllegalArgumentException e) {
			badRequest(requestUri, (HttpServletResponse) response.getResponse());
		}
		if (wrapper == null) {
			notFound(requestUri, (HttpServletResponse) response.getResponse());
		}
		response.setContext(context);
		wrapper.invoke(request, response);
	}

	private void badRequest(String requestURI, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, requestURI);
		} catch (IllegalStateException e) {
			;
		} catch (IOException e) {
			;
		}
	}

	private void notFound(String requestURI, HttpServletResponse response) {
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, requestURI);
		} catch (IllegalStateException e) {
			;
		} catch (IOException e) {
			;
		}
	}
}
