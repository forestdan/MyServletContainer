package my.chapter14.startup;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Engine;
import org.apache.catalina.Host;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.core.StandardService;
import org.apache.catalina.core.StandardWrapper;
import org.apache.catalina.loader.WebappLoader;

import my.chapter14.core.SimpleContextConfig;

public final class BootStrap {

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
		//这个path的设置，只有context在host容器里面的时候才会有用
		context.setPath("/webapp");
		context.setDocBase("");
		
		LifecycleListener listener = new SimpleContextConfig();
		((Lifecycle) context).addLifecycleListener(listener);
		
		Host host = new StandardHost();
		host.addChild(context);
		host.setName("localhost");
		host.setAppBase("webroot");
		
		context.addChild(wrapper1);
		context.addChild(wrapper2);
		
		Loader loader = new WebappLoader();
		context.setLoader(loader);
		
		context.addServletMapping("/Primitive", "Primitive");
		context.addServletMapping("/Modern", "Modern");
		
		Engine engine = new StandardEngine();
		engine.addChild(host);
		engine.setDefaultHost("localhost");
		
		Service service = new StandardService();
		service.setName("Stand-alone service");
		Server server = new StandardServer();
		server.addService(service);
		service.addConnector(connector);
		service.setContainer(engine);
		
		if (server instanceof Lifecycle) {
			try {
				server.initialize();
				((Lifecycle) server).start();
				server.await();
			}catch(LifecycleException e) {
				e.printStackTrace();
			}
		}
		
		if (server instanceof Lifecycle) {
			try {
				((Lifecycle) server).stop();
			}catch(LifecycleException e) {
				e.printStackTrace();
			}
		}
	}
}
