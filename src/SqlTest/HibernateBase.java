package SqlTest;

import org.hibernate.*;  
import org.hibernate.cfg.*;  
       
     public abstract class HibernateBase   
     {  
     protected SessionFactory sessionFactory;//会话工厂，用于创建会话  
     protected Session session;//hibernate会话  
     protected Transaction transaction; //hiberante事务  
       
     public HibernateBase()throws HibernateException  
     {  
     this.initHibernate();  
     }  
     // 帮助方法  
     protected void initHibernate()  
     throws HibernateException {  
       
     // 装载配置，构造SessionFactory对象  
     sessionFactory = new Configuration().configure().buildSessionFactory();  
     }  
       
     /** 
     *开始一个hibernate事务 
     */  
     protected void beginTransaction()  
     throws HibernateException {  
       
     session = sessionFactory.openSession();  
     transaction = session.beginTransaction();  
     }  
       
     /** 
     *结束一个hibernate事务。 
     */  
     protected void endTransaction(boolean commit)  
     throws HibernateException {  
       
     if (commit) {  
     transaction.commit();  
     } else {  
     //如果是只读的操作，不需要commit这个事务。  
     transaction.rollback();  
     }  
     session.close();  
     }  
}  