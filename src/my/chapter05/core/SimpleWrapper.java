package my.chapter05.core;

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
import org.apache.catalina.Wrapper;

public class SimpleWrapper implements Wrapper{

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Loader getLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLoader(Loader loader) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Container getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParent(Container container) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ClassLoader getParentClassLoader() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
	public Servlet allocate() throws ServletException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
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

	
}
