package SqlTest;

import java.util.Iterator;

import classTestBest.user;

public class Test
{
	public static void main(String[] args)
	{
		user us = new user();
		us.setName("李四");
		us.setPassword("123456");
//		us.setSay("吾问无为谓");
//		us.setFlag(2);
		UserBean ub = new UserBean();
		//ub.addUser(us);
		user it = ub.getUser("李四s");
		System.out.println(it);
	}
}
