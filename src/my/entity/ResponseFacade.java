package my.entity;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

/**
 * 用来屏蔽Response中的公有方法，暴露给容器中的servlet的只有ServletResponse的方法
 *
 */
public class ResponseFacade implements ServletResponse{

	private Response response;
	
	public ResponseFacade(Response response) {
		this.response = response;
	}
	
	@Override
	public void flushBuffer() throws IOException {
		response.flushBuffer();
	}

	@Override
	public int getBufferSize() {
		return response.getBufferSize();
	}

	@Override
	public String getCharacterEncoding() {
		return response.getCharacterEncoding();
	}

	@Override
	public Locale getLocale() {
		return response.getLocale();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return response.getOutputStream();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return response.getWriter();
	}

	@Override
	public boolean isCommitted() {
		return response.isCommitted();
	}

	@Override
	public void reset() {
		response.reset();
	}

	@Override
	public void resetBuffer() {
		response.resetBuffer();
	}

	@Override
	public void setBufferSize(int arg0) {
		response.setBufferSize(arg0);
	}

	@Override
	public void setContentLength(int arg0) {
		response.setContentLength(arg0);
	}

	@Override
	public void setContentType(String arg0) {
		response.setContentType(arg0);
	}

	@Override
	public void setLocale(Locale arg0) {
		response.setLocale(arg0);
	}

	
}
