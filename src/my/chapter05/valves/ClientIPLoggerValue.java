package my.chapter05.valves;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;

import org.apache.catalina.Contained;
import org.apache.catalina.Container;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.ValveContext;

/**
 * 此阀用来将客户端的IP地址输出到控制台上
 * @author Administrator
 *
 */
public class ClientIPLoggerValue implements Valve, Contained{

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
		System.out.println("Client IP Logger Value");
		ServletRequest sreq = request.getRequest();
		System.out.println(sreq.getRemoteAddr());
		System.out.println("-----------------------------");
	}
	
}
