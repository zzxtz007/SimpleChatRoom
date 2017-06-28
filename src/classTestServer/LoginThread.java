package classTestServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.hibernate.HibernateException;

import SqlTest.UserBean;
import classTestBest.user;

public class LoginThread extends Thread {
	private ObjectOutputStream oos = null;
	private user ss = null;
	private Socket socket = null;

	public LoginThread(user ss,Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
		this.ss = ss;
		this.start();
	}

	public void run() {
		//连接数据库 hibernte模块
		UserBean ur = new UserBean();
		user login;
		//搜索数据库并核对密码
		try {
			if( (login = ur.getUser(ss.getName()))!=null)
				if(ss.getPassword().equals(login.getPassword()))
					ss.setFlag(100);
				else
					ss.setFlag(99);
			else
				ss.setFlag(98);
		} catch (HibernateException e) {
			// TODO: handle exception
			e.printStackTrace();
			ss.setFlag(97);
		}
			//获取login并核对密码
		
		
		//若登陆成功返回flag=100 
		//若不成功返回 flag=99密码错误
		//			 flag=98无此账户
		//			 flag=97客户端错误
		try {
			oos.writeObject(ss);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(oos!=null)
					oos.close();
				if(socket!=null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
