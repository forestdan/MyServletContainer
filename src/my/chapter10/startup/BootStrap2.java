package my.chapter10.startup;

import java.io.IOException;

import org.apache.catalina.Connector;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Realm;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.http.HttpConnector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityCollection;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.loader.WebappLoader;

import my.chapter10.core.SimpleContextConfig;
import my.chapter10.core.SimpleWrapper;
import my.chapter10.realm.SimpleRealm;
import my.chapter10.realm.SimpleUserDatabaseRealm;

public class BootStrap2 {

	public static void main(String[] args) {
System.setProperty("catalina.base", System.getProperty("user.dir"));
		
		Connector connector = new HttpConnector();
		Wrapper wrapper1 = new SimpleWrapper();
		wrapper1.setName("Primitive");
		wrapper1.setServletClass("PrimitiveServlet");
		Wrapper wrapper2 = new SimpleWrapper();
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
		
		SecurityCollection securityCollection = new SecurityCollection();
		securityCollection.addPattern("/");
		securityCollection.addMethod("GET");
		
		SecurityConstraint constraint = new SecurityConstraint();
		constraint.addCollection(securityCollection);
		constraint.addAuthRole("manager");
		LoginConfig loginConfig = new LoginConfig();
		loginConfig.setRealmName("Simple Realm");
		
		//Realm可以看做是一个身份认证的库，里面存有用户，用户名以及用户对应的身份信息
		Realm realm = new SimpleUserDatabaseRealm();
		((SimpleUserDatabaseRealm) realm).createDatabase("conf/tomcat-users.xml");
		context.setRealm(realm);
		context.addConstraint(constraint);
		context.setLoginConfig(loginConfig);
		
		connector.setContainer(context);
		
		try {
			connector.initialize();
			((Lifecycle) connector).start();
			((Lifecycle) context).start();
			System.in.read();
			
			((Lifecycle) context).stop();
		}catch(IOException | LifecycleException e) {
			e.printStackTrace();
		}
	}
}
