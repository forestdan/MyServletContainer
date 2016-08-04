package my.chapter10.realm;

import java.beans.PropertyChangeListener;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Container;
import org.apache.catalina.Realm;
import org.apache.catalina.realm.GenericPrincipal;

public class SimpleRealm implements Realm {

	protected Container container;

	private ArrayList<User> users = new ArrayList<>();

	public SimpleRealm() {
		createUserDatabase();
	}

	private void createUserDatabase() {
		User user1 = new User("ken", "blackcomb");
		user1.addRole("manager");
		user1.addRole("programmer");
		User user2 = new User("cindy", "bamboo");
		user2.addRole("programmer");

		users.add(user1);
		users.add(user2);
	}

	@Override
	public Container getContainer() {
		return container;
	}

	@Override
	public void setContainer(Container container) {
		this.container = container;
	}

	@Override
	public String getInfo() {
		return "A simple Realm implemention";
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {

	}

	@Override
	public Principal authenticate(String username, String credentials) {
		System.out.println("SimpleRealm.authenticate()");
		if (username == null && credentials == null) {
			return null;
		}
		User user = getUser(username, credentials);
		if (user == null) {
			return null;
		}
		return new GenericPrincipal(this, user.userName, user.passWord, user.getRoles());
	}

	private User getUser(String username, String credentials) {
		Iterator iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = (User) iterator.next();
			if (user.userName.equals(username) && user.passWord.equals(credentials)) {
				return user;
			}
		}
		return null;
	}

	@Override
	public Principal authenticate(String username, byte[] credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal authenticate(String username, String digest, String nonce, String nc, String cnonce, String qop,
			String realm, String md5a2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Principal authenticate(X509Certificate[] certs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasRole(Principal principal, String role) {
		if ((principal == null) || (role == null) || !(principal instanceof GenericPrincipal))
			return false;
		//Principal代表的是一个用户的验证信息，到达这里的时候说明已经通过了用户名和密码的认证，接下来就是身份的验证
		GenericPrincipal gp = (GenericPrincipal) principal;
		if (!(gp.getRealm() == this))
			return false;
		boolean result = gp.hasRole(role);
		return result;
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {

	}

	class User {

		String userName;
		String passWord;
		ArrayList roles = new ArrayList<>();

		public User(String userName, String passWord) {
			this.userName = userName;
			this.passWord = passWord;
		}

		public void addRole(String role) {
			roles.add(role);
		}

		public ArrayList getRoles() {
			return roles;
		}
	}
}
