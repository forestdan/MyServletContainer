package my.chapter03.processor;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import my.chapter03.connector.Constants;
import my.chapter03.entity.HttpRequest;
import my.chapter03.entity.HttpResponse;

public class ServletProcessor {

	public void process(HttpRequest request, HttpResponse response) {
		String uri = request.getRequestURI();
		String servletName = uri.substring(uri.lastIndexOf("/my") + 1);
		servletName = servletName.replaceAll("/", ".");
		URLClassLoader loader  = null;
		URL[] urls = new URL[1];
		URLStreamHandler streamhandler = null;
		File classPath = new File(Constants.WEB_ROOT);
		try {
			String repository = (new URL("file", null, classPath.getCanonicalPath() + File.separator)).toString();
			urls[0] = new URL(null, repository, streamhandler);
			//此加载器加载类的路径
			loader = new URLClassLoader(urls);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Class myclass = null;
		try {
			myclass = loader.loadClass(servletName);
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Servlet servlet = null;
		try {
			servlet = (Servlet) myclass.newInstance();
			servlet.service((ServletRequest) request, (ServletResponse) response);
		}catch(IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
