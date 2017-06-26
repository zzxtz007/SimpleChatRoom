package classTestServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import classTestBest.user;

public class classTestSever {

	/**
	 * 将socket封装到user类中，user有4个属性 name password say flag
	 * 在socket中使用switch来通过flag表示用户想要进行的操作
	 * 
	 * */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket serverSocket = new ServerSocket(8800);
		System.out.println("等待客户端发送");
		
		while(true){
			Socket socket = serverSocket.accept();
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Object obj = ois.readObject();
			if(obj instanceof user){
				user us = (user) obj;
				System.out.println("asaa");
				switch(us.getFlag())
				{
				case 0:new LoginThread(us, socket);System.out.println("1");;break;//进入登陆线程
				case 1:new RegisterThread(us, socket);System.out.println("2");;break;//进入注册线程
				case 2:new CharRoomThread(us, socket);System.out.println(3);;break;//进入聊天室线程
				case 3:new FileUpThread();System.out.println(4);;break;//进入文件存储线程
				}
			}
		}
	}

}
