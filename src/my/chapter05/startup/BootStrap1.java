package my.chapter05.startup;

import org.apache.catalina.Loader;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;

import my.chapter05.core.SimpleLoader;
import my.chapter05.core.SimpleWrapper;
import my.chapter05.valves.ClientIPLoggerValue;
import my.chapter05.valves.HeaderLoggerValve;

public class BootStrap1 {

	public static void main(String[] args) {
		
		HttpConnector connector = new HttpConnector();
		Wrapper wrapper = new SimpleWrapper();
		wrapper.setServletClass("ModelServlet");
		
		Loader loader = new SimpleLoader();
		Valve valve1 = new ClientIPLoggerValue();
		Valve valve2 = new HeaderLoggerValve();
		wrapper.setLoader(loader);
		((Pipeline) wrapper).addValve(valve1);
		((Pipeline) wrapper).addValve(valve2);
		connector.setContainer(wrapper);
		try {
			connector.initialize();
			connector.start();
			System.in.read();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
