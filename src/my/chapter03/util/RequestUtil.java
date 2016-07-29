package my.chapter03.util;

import java.util.ArrayList;

import javax.servlet.http.Cookie;

public class RequestUtil {

	public static Cookie[] parseCookieHeader(String header) {
		if (header == null || header.isEmpty()) {
			return new Cookie[0];
		}
		ArrayList<Cookie> cookies = new ArrayList<>();
		while (header.length() > 0) {
			int semicolon = header.indexOf(';');
			if (semicolon < 0) {
				semicolon = header.length();
			}
			if (semicolon == 0) {
				break;
			}
			String token = header.substring(0, semicolon);
			if (semicolon < header.length()) {
				header = header.substring(semicolon + 1);
			}else {
				header = "";
			}
			try {
				int equals = token.indexOf('=');
				if (equals > 0) {
					String name = token.substring(0, equals).trim();
					String value = token.substring(equals + 1);
					cookies.add(new Cookie(name, value));
				}
			}catch(Throwable e) {
				e.printStackTrace();
			}
		}
		return cookies.toArray(new Cookie[cookies.size()]);
	}
}
