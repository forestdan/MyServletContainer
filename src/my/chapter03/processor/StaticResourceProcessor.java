package my.chapter03.processor;

import java.io.IOException;

import my.chapter03.entity.HttpRequest;
import my.chapter03.entity.HttpResponse;

public class StaticResourceProcessor {

	public void process(HttpRequest request, HttpResponse response) {
		try {
			response.sendStaticResource();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}
