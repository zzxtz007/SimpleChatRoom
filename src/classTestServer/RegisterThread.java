package classTestServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.hibernate.HibernateException;

import SqlTest.UserBean;
import classTestBest.user;

public class RegisterThread extends Thread {
	private ObjectOutputStream oos = null;
	private user ss = null;
	private Socket socket = null;

	public RegisterThread(user ss, Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
		this.ss = ss;
		this.start();
	}

	public void run() {
		// 连接数据库 hibernte模块
		UserBean ur = new UserBean();
		try {
			user login;
			if((login = ur.getUser(ss.getName()))==null)
			{
				ur.addUser(ss);
				ss.setFlag(101);
			}else
				ss.setFlag(96);
			
		} catch (HibernateException e) {
			ss.setFlag(97);
		}
		// 若注册成功返回flag=101 
		// 若不成功返回    flag=96账号被占用
		// 				flag=97服务器错误
		try {
			oos.writeObject(ss);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
