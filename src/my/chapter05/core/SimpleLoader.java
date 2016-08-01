package my.chapter05.core;

import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.DefaultContext;
import org.apache.catalina.Loader;

/**
 * 负责类的载入工作
 * @author Administrator
 *
 */
public class SimpleLoader implements Loader{

	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";
	
	ClassLoader classLoader = null;
	Container container = null;
	
	public SimpleLoader() {
		try {
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classPath = new File(WEB_ROOT);
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null, repository, streamHandler);
			classLoader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Container getContainer() {
		return container;
	}

	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public ClassLoader getClassLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultContext getDefaultContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultContext(DefaultContext defaultContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getDelegate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDelegate(boolean delegate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getReloadable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReloadable(boolean reloadable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addRepository(String repository) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] findRepositories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		// TODO Auto-generated method stub
		
	}

}
