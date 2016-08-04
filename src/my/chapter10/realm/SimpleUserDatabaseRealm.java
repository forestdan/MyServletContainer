package my.chapter10.realm;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;
import org.apache.catalina.users.MemoryUserDatabase;

public class SimpleUserDatabaseRealm extends RealmBase {

	protected UserDatabase database = null;

	protected static final String name = "SimpleUserDatabaseRealm";

	protected String resourceName = "UserDatabase";

	@Override
	protected String getName() {
		return name;
	}

	@Override
	protected String getPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Principal getPrincipal(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createDatabase(String path) {
		database = new MemoryUserDatabase(name);
		((MemoryUserDatabase) database).setPathname(path);
		try {
			database.open();
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
	
	public Principal authenticate(String username, String credentials) {
		User user = database.findUser(username);
		if (user == null) {
			return null;
		}

		boolean validated = false;
		if (hasMessageDigest()) {
			validated = digest(credentials).equalsIgnoreCase(user.getPassword());
		} else {
			validated = digest(credentials).equals(user.getPassword());
		}

		if (!validated) {
			return null;
		}

		ArrayList<String> combined = new ArrayList<>();
		Iterator roles = user.getRoles();
		while (roles.hasNext()) {
			Role role = (Role) roles.next();
			String roleName = role.getName();
			if (!combined.contains(roleName)) {
				combined.add(roleName);
			}
		}
		
		Iterator groups = user.getGroups();
		while (groups.hasNext()) {
			Group group = (Group) groups.next();
			roles = group.getRoles();
			while (roles.hasNext()) {
				Role role = (Role) roles.next();
				String roleName = role.getRolename();
				if (!combined.contains(roleName)) {
					combined.add(roleName);
				}
			}
		}
		return (new GenericPrincipal(this, user.getUsername(), user.getPassword(), combined));
	}
}
