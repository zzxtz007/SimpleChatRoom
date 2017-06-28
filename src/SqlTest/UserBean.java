package SqlTest;

import org.hibernate.*;  
import org.hibernate.cfg.*;

import classTestBest.user;

import java.util.*;  
  
/** 
 * 和course相关的业务逻辑 
 */  
public class UserBean extends HibernateBase {  
    public UserBean() throws HibernateException {  
        super();  
    }  
  
    /** 
     * 增加一个Course 
     */  
    public void addUser(user us) throws HibernateException {  
        beginTransaction();  
        session.save(us);  
        endTransaction(true);  
    }  
  
    /** 
     * 查询系统中所有的Course，返回的是包含有Course持久对象的Iterator。 
     */  
    public Iterator getAllUsers() throws HibernateException {  
        String queryString = "select u from user as u";  
        beginTransaction();  
        Query query = session.createQuery(queryString);  
        Iterator it = query.iterate();  
        return it;  
    }  
  
    /** 
     * 删除给定ID的course 
     */  
    public void deleteUser(String id) throws HibernateException {  
        beginTransaction();  
        user us = (user) session.load(user.class, id);  
        session.delete(us);  
        endTransaction(true);  
    }  
  
    /** 
     * 按course的名字进行模糊查找，返回的是包含有Course持久对象的Iterator。 
     */  
    public Iterator getSomeUser(String name) throws HibernateException {  
        String queryString = "select u from user as u where u.name like :name";  
        beginTransaction();  
        Query query = session.createQuery(queryString);  
        query.setString("name", "%" + name + "%");  
        Iterator it = query.iterate();  
        return it;  
    }  
    
    /** 
     * 精确查询系统中所有的Course，返回的是包含有Course持久的对象。 
     */  
    public user getUser(String name) throws HibernateException {  
        beginTransaction();  
        user it = (user) session.get(user.class, name);
        return it;  
    }  
}  