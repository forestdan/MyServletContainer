package my.chapter05.core;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.catalina.Container;
import org.apache.catalina.Pipeline;
import org.apache.catalina.Request;
import org.apache.catalina.Response;
import org.apache.catalina.Valve;

public class SimplePipeline implements Pipeline{

	// The basic Valve (if any) associated with this Pipeline.
	protected Valve basic = null;
	// The Container with which this Pipeline is associated.
	protected Container container = null;
	// the array of Valves
	protected Valve[] valves = new Valve[0];
	
	@Override
	public Valve getBasic() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setBasic(Valve valve) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addValve(Valve valve) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Valve[] getValves() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void invoke(Request request, Response response)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub
		
	}
	  
}
