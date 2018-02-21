package classTestServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import classTestBest.user;

public class CharRoomThread extends Thread {
	private ObjectOutputStream oos = null; //输出流 发送文件
	private ObjectInputStream ois = null; //输入流 读取文件
	private user ss = null;
	private Socket socket = null;

	public CharRoomThread(user ss,Socket socket) throws IOException {
		super();
		this.socket = socket;
		//this.ois = new ObjectInputStream(this.socket.getInputStream());
		this.oos = new ObjectOutputStream(this.socket.getOutputStream());
		this.ss = ss;
		this.start();
	}

	public void run() {
		try {
			Class c = this.getClass();
			ss.setSay("我在"+c.getName());
			oos.writeObject(ss);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
				//ois.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//若登陆成功返回flag=100 若不成功返回flag=99
		
	}

}
