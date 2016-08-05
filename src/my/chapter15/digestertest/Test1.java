package my.chapter15.digestertest;

import java.io.File;

import org.apache.commons.digester.Digester;

public class Test1 {

	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + File.separator + "etc";
		File file = new File(path, "employee1.xml");
		Digester digester = new Digester();
		digester.addObjectCreate("employee", "my.chapter15.digestertest.Employee");
		digester.addSetProperties("employee");
		digester.addCallMethod("employee", "printName");
		
		try {
			Employee employee = (Employee) digester.parse(file);
			System.out.println("FirstName :" + employee.getFirstName());
			System.out.println("LastName :" + employee.getLastName());
		}catch(Throwable e) {
			e.printStackTrace();
		}
	}
}
