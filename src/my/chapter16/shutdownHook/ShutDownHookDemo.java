package my.chapter16.shutdownHook;

import java.io.IOException;

public class ShutDownHookDemo {

	public void start() {
		System.out.println("Demo");
		ShutdownHook shutdownHook = new ShutdownHook();
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}
	
	public static void main(String[] args) {
		ShutDownHookDemo shutDownHookDemo = new ShutDownHookDemo();
		shutDownHookDemo.start();
		try {
			System.in.read();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	class ShutdownHook extends Thread {
		public void run() {
			System.out.println("Shutting down");
		}
	}
}
