package my.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import my.socket.HttpServer;

public class Response implements ServletResponse{

	private final int BUFFER_SIZE = 1024;
	
	private Request request;
	
	private OutputStream output;
	
	private PrintWriter writer;
	
	public Response(OutputStream output) {
		this.output = output;
	}
	
	public void setRequest(Request request) {
		this.request = request;
	}
	
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fis = null;
		try {
			File file = new File(HttpServer.WEB_ROOT, request.getUri());
			if (file.exists()) {
				fis = new FileInputStream(file);
				@SuppressWarnings("unused")
				int ch = 0;
				while ((ch = fis.read(bytes, 0, BUFFER_SIZE)) != -1) {
					output.write(bytes, 0, BUFFER_SIZE);
				}
			}else {
				String errorMessage = "HTTP/1.1 404 File Not Found\r\n" + 
						"Content-Type: text/html\r\n" + 
						"Content-Length: 23\r\n" + 
						"\r\n" + 
						"<h1>File Not Found</h1>";
				output.write(errorMessage.getBytes());
				output.flush();
			}
		}catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				fis.close();
			}
		}
	}

	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		writer = new PrintWriter(output, true);
		return writer;
	}

	@Override
	public void setContentLength(int len) {
		
	}

	@Override
	public void setContentType(String type) {
		
	}

	@Override
	public void setBufferSize(int size) {
		
	}

	@Override
	public int getBufferSize() {
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
		
	}

	@Override
	public void resetBuffer() {
		
	}

	@Override
	public boolean isCommitted() {
		return false;
	}

	@Override
	public void reset() {
		
	}

	@Override
	public void setLocale(Locale loc) {
		
	}

	@Override
	public Locale getLocale() {
		return null;
	}

}
