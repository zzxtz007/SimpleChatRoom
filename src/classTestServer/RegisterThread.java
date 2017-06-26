package classTestServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import classTestBest.user;

public class RegisterThread extends Thread {
	private ObjectOutputStream oos = null;
	private user ss = null;
	private Socket socket = null;

	public RegisterThread(user ss,Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
		this.ss = ss;
		this.start();
	}

	public void run() {
		//连接数据库 hibernte模块
		
		//若注册成功返回flag=101 若不成功返回flag=99
		
	}

}
