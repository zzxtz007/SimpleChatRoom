package classTestClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import classTestBest.user;

public class classTestClient {

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket socket = new Socket("127.0.0.1", 8800);
		user wang = new user();
		wang.setPassword("11111");
		wang.setFlag(2);
		OutputStream os = socket.getOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(wang);
		oos.flush();
		System.out.println("已发送");
		
//		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
//		user ss = (user) ois.readObject();
//		System.out.println(ss.getFlag());
//		ois.close();
		oos.close();
		os.close();
		socket.close();
	}

}
