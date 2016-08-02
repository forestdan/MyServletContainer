package my.chapter06.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.management.ObjectName;
import javax.naming.directory.DirContext;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;

import org.apache.catalina.Cluster;
import org.apache.catalina.Container;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.InstanceListener;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Loader;
import org.apache.catalina.Logger;
import org.apache.catalina.Manager;
import org.apache.catalina.Mapper;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Realm;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;
import org.apache.catalina.Wrapper;
import org.apache.catalina.util.LifecycleSupport;

/**
 * 可以看做是用来组装一个servlet的工具
 * @author Administrator
 *
 */
public class SimpleWrapper implements Wrapper, Pipeline, Lifecycle{

	private Servlet instance;
	private Loader loader;
	protected Container parent;
	private String servletClass;
	private Pipeline pipeline = new SimplePipeline(this);
	private String name;
	protected boolean started = false;
	protected LifecycleSupport lifecycle = new LifecycleSupport(this);
	
	public SimpleWrapper() {
		pipeline.setBasic(new SimpleWrapperValve());
	}
	
	@Override
	public Servlet allocate() throws ServletException {
		if (instance == null) {
			try {
				instance = loadServlet();
			}catch(ServletException e) {
				throw new ServletException("Fail to load servlet");
			}
		}
		return instance;
	}
	
	private Servlet loadServlet() throws ServletException{
		if (instance != null) {
			return instance;
		}
		Servlet servlet = null;
		String actualClass = servletClass;
		if (actualClass == null) {
			throw new ServletException();
		}
		Loader loader = getLoader();
		if (loader == null) {
			throw new ServletException("No loader.");
		}
		ClassLoader classloader = loader.getClassLoader();
		
		Class clazz = null;
		try {
			if (classloader != null) {
				clazz = classloader.loadClass(actualClass);
			}
		}catch(ClassNotFoundException e) {
			throw new ServletException("Servlet class not found");
		}
		
		try {
			servlet = (Servlet) clazz.newInstance();
		}catch(Exception e) {
			throw new ServletException();
		}
		
		try {
			servlet.init(null);
		}catch(ServletException e) {
			throw new ServletException();
		}
		
		return servlet;
	}
	
	@Override
	public String getInfo() {
		return null;
	}

	@Override
	public Loader getLoader() {
		if (loader != null) {
			return loader;
		}
		if (parent != null) {
			return parent.getLoader();
		}
		return null;
	}

	@Override
	public void setLoader(Loader loader) {
		this.loader = loader;
	}

	@Override
	public Logger getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLogger(Logger logger) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Manager getManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setManager(Manager manager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Cluster getCluster() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCluster(Cluster cluster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Container getParent() {
		return parent;
	}

	@Override
	public void setParent(Container container) {
		this.parent = container;
	}

	@Override
	public ClassLoader getParentClassLoader() {
		return null;
	}

	@Override
	public void setParentClassLoader(ClassLoader parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Realm getRealm() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealm(Realm realm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DirContext getResources() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setResources(DirContext resources) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChild(Container child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container findChild(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Container[] findChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContainerListener[] findContainerListeners() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper findMapper(String protocol) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mapper[] findMappers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Request request, Response response) throws IOException, ServletException {
		pipeline.invoke(request, response);
	}

	@Override
	public Container map(Request request, boolean update) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeChild(Container child) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeContainerListener(ContainerListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeMapper(Mapper mapper) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getAvailable() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAvailable(long available) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getJspFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setJspFile(String jspFile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLoadOnStartup() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLoadOnStartup(int value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getRunAs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRunAs(String runAs) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getServletClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setServletClass(String servletClass) {
		this.servletClass = servletClass;
	}

	@Override
	public boolean isUnavailable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addInitParameter(String name, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addInstanceListener(InstanceListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addSecurityReference(String name, String link) {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void deallocate(Servlet servlet) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findInitParameter(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findInitParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String findSecurityReference(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] findSecurityReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void load() throws ServletException {
		instance = loadServlet();
	}

	@Override
	public void removeInitParameter(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeInstanceListener(InstanceListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeSecurityReference(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unavailable(UnavailableException unavailable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload() throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Valve getBasic() {
		return pipeline.getBasic();
	}
	
	@Override
	public void setBasic(Valve valve) {
		pipeline.setBasic(valve);
	}
	
	@Override
	public void addValve(Valve valve) {
		pipeline.addValve(valve);
	}
	
	@Override
	public Valve[] getValves() {
		return pipeline.getValves();
	}
	
	@Override
	public void removeValve(Valve valve) {
		pipeline.removeValve(valve);
	}

	
	
	@Override
	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	@Override
	public void start() throws LifecycleException {
		System.out.println("Starting Wrapper");
		if (started) {
			throw new LifecycleException("Wrapper already started");
		}
		lifecycle.fireLifecycleEvent(BEFORE_START_EVENT, null);
		started = true;
		
		if ((loader != null) && (loader instanceof Lifecycle)) {
			((Lifecycle) loader).start();
		}
		if ((pipeline != null) && (pipeline instanceof Lifecycle)) {
			((Lifecycle) pipeline).start();
		}
		lifecycle.fireLifecycleEvent(START_EVENT, null);
		lifecycle.fireLifecycleEvent(AFTER_START_EVENT, null);
	}

	@Override
	public void stop() throws LifecycleException {
		System.out.println("Stopping wrapper" + name);
		try {
			instance.destroy();
		}catch(Throwable e) {
			e.printStackTrace();
		}
		instance = null;
		if (!started) {
			throw new LifecycleException("Wrapper" + name + "not started");
		}
		lifecycle.fireLifecycleEvent(BEFORE_STOP_EVENT, null);
		lifecycle.fireLifecycleEvent(STOP_EVENT, null);
		started = false;
		if ((pipeline != null) && (pipeline instanceof Lifecycle)) {
			((Lifecycle) pipeline).stop();
		}
		if ((loader != null) && (loader instanceof Lifecycle)) {
			((Lifecycle) loader).stop();
		}
		lifecycle.fireLifecycleEvent(AFTER_STOP_EVENT, null);
	}

	
}
