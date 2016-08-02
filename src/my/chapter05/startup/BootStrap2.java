package my.chapter05.startup;

import java.io.IOException;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Loader;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

import my.chapter05.core.SimpleContext;
import my.chapter05.core.SimpleContextMapper;
import my.chapter05.core.SimpleLoader;
import my.chapter05.core.SimpleWrapper;
import my.chapter05.valves.ClientIPLoggerValue;
import my.chapter05.valves.HeaderLoggerValve;

public class BootStrap2 {

	public static void main(String[] args) {
		
		HttpConnector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Context context = new SimpleContext();
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Valve valve1 = new ClientIPLoggerValue();
		Valve valve2 = new HeaderLoggerValve();
		
		((Pipeline) context).addValve(valve1);
		((Pipeline) context).addValve(valve2);
		
		Mapper mapper = new SimpleContextMapper();
		mapper.setProtocol("http");
		context.addMapper(mapper);
		Loader loader = new SimpleLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive", "Primitive");
		context.addServletMapping("/Modern", "Modern");
		
		connector.setContainer(context);
		try {
			connector.initialize();
			connector.start();
			System.in.read();
		}catch(LifecycleException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
