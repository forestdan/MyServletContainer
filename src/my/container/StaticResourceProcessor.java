package my.container;

import java.io.IOException;

import my.entity.Request;
import my.entity.Response;

public class StaticResourceProcessor {

	public void process(Request request, Response response) {
		try {
			response.sendStaticResource();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
