package my.chapter09.startup;

import java.io.IOException;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Manager;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.loader.WebappLoader;
import org.apache.catalina.session.StandardManager;

import my.chapter09.core.SimpleContextConfig;
import my.chapter09.core.SimpleWrapper;

public class BootStrap {

	public static void main(String[] args) {
		System.setProperty("catalina.base", System.getProperty("user.dir"));
		Connector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Session");
		wrapper1.setServletClass("SessionServlet");
		
		Context context = new StandardContext();
		context.setPath("/webroot");
		context.setDocBase("webroot");
		context.addChild(wrapper1);
		
		context.addServletMapping("/webroot/Session", "Session");
		
		LifecycleListener listener = new SimpleContextConfig();
		((Lifecycle) context).addLifecycleListener(listener);
		
		Loader loader = new WebappLoader();
		context.setLoader(loader);
		connector.setContainer(context);
		
		Manager mananger = new StandardManager();
		context.setManager(mananger);
		
		try {
			connector.initialize();
			((Lifecycle) connector).start();
			((Lifecycle) context).start();
			
			System.in.read();
		} catch (LifecycleException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
