package my.chapter11.startup;

import java.io.IOException;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;

import my.chapter11.core.SimpleContextConfig;

public class BootStrap {

	public static void main(String[] args) {
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		
		Connector connector = new HttpConnector();
		Wrapper wrapper1 = new StandardWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new StandardWrapper();
		wrapper2.setName("Modern");
		wrapper2.setServletClass("ModernServlet");
		
		Context context = new StandardContext();
		context.setPath("/webroot");
		context.setDocBase("webroot");
		
		LifecycleListener listener = new SimpleContextConfig();
		((Lifecycle) context).addLifecycleListener(listener);
		
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Loader loader = new WebappLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/webroot/Primitive", "Primitive");
		context.addServletMapping("/webroot/Modern", "Modern");
		
		connector.setContainer(context);
		
		try {
			connector.initialize();
			((Lifecycle) connector).start();
			((Lifecycle) context).start();
			
			System.in.read();
			((Lifecycle) context).stop();
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
}
