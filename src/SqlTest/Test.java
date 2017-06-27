package SqlTest;

import java.util.Iterator;

import classTestBest.user;

public class Test
{
	public static void main(String[] args)
	{
		user us = new user();
//		us.setName("李四");
//		us.setPassword("123456");
//		us.setSay("吾问无为谓");
//		us.setFlag(2);
		UserBean ub = new UserBean();
//		ub.addUser(us);
		Iterator it = ub.getSomeUser("三");
		while (it.hasNext())
		{
			us = (user)it.next();
			System.out.println(us);
		}
	}
}
