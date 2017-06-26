package classTestServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
		
		//若登陆成功返回flag=100 若不成功返回flag=99
		
	}

}
