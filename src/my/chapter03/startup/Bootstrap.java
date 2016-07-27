package my.chapter03.startup;

import my.chapter03.connector.HttpConnector;

public final class Bootstrap {

	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
}
