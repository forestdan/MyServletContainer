package my.chapter05.valves;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/**
 * 用于输出header信息
 * @author Administrator
 *
 */
public class HeaderLoggerValve implements Valve, Contained{

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
		
		context.invokeNext(request, response);
		System.out.println("Header logger value");
		ServletRequest sreq = request.getRequest();
		if (sreq instanceof HttpServletRequest) {
			HttpServletRequest hreq = (HttpServletRequest) sreq;
			Enumeration headerNames = hreq.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement().toString();
				String headerValue = hreq.getHeader(headerName);
				System.out.println(headerName + ":" + headerValue);
			}
		}else {
			System.out.println("It is not a HTTP request");
		}
		System.out.println("----------------");
	}

	
}
