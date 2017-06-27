package SqlTest;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import userInfomion.users;

public class userSqlUtil 
{	
	private SessionFactory sessionfactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init()
	{
		// 创建配置对象
		Configuration config = new Configuration().configure();

		// 创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
				.buildServiceRegistry();

		// 创建会话工厂对象
		sessionfactory = config.buildSessionFactory(serviceRegistry);

		// 会话对象
		session = sessionfactory.openSession();

		// 开启事务
		transaction = session.beginTransaction();
	}
	

	@Test
	public void userCreate()
	{
		users user = new users(1, "张三","男", new Date(), "北京");  
		session.save(user);
	}
	
	@Test
	public void testgetuser()
	{
		users user = (users) session.get(users.class, 1);
		System.out.println(user);
	}
	
	@Test
	public void testloaduser()
	{
		users user = (users) session.load(users.class, 1);
		System.out.println(user);
	}
	
	@Test
	public void testupdateuser()
	{
		users user = (users) session.get(users.class, 1);
		user.setGender("女");
		session.update(user);
		System.out.println(user);

	}
	
	@Test
	public void testDeleteuser()
	{
		users user = (users) session.get(users.class, 1);
		session.delete(user);
	}
		
	
	@After
	public void destory()
	{
		transaction.commit();// 提交事务
		session.close();// 关闭会话
		sessionfactory.close();// 关闭会话工厂
	}
}
