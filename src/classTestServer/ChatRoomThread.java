package classTestServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import classTestBest.userChat;

public class ChatRoomThread extends Thread {
	boolean isChatRoomOpen;
	ArrayList<chatThread> chatroom = new ArrayList<chatThread>();
	//Map<userChat, chatThread> chatroom = new HashMap<userChat, chatThread>();
	int InNum = 0;
	public ChatRoomThread(boolean isChatRoomOpen) throws IOException {
		this.isChatRoomOpen = isChatRoomOpen;
		this.start();
	}

	public void run() {
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(8801);
			while(isChatRoomOpen){
				Socket socket = serverSocket.accept();
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				chatThread x = new chatThread(socket,oos, ois, InNum);
				//chatroom.put(arg0, arg1)
				System.out.println(111111);
				chatroom.add(x); // 将线程加入ArrayList中
				x.start();
				InNum++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	class chatThread extends Thread{
		private int num;
		private Socket socket;
		private ObjectOutputStream oos;
		private ObjectInputStream ois;

		public chatThread(Socket socket, ObjectOutputStream oos, ObjectInputStream ois, int num)
		{
			this.oos = oos;
			this.ois = ois;
			this.num = num;
			this.socket = socket;
		}

		public void run()
		{// 同步消息至所有客户端
			while (true)
			{
				try
				{
					System.out.println(222222222);
					userChat uc = (userChat)ois.readObject(); 
					for(int i=0;i<InNum;i++)
					{
						if(i!=num)
						{
							uc.setFlag(50);
							chatroom.get(i).oos.writeObject(uc);
							oos.flush();
						}
						else
							uc.setFlag(45);
							chatroom.get(i).oos.writeObject(uc);
							oos.flush();
					}
					//flag = 50 别人发送的
					//flag = 45 自己发送的
				} catch (IOException e)
				{
					e.printStackTrace();
					try
					{
						socket.close();
						InNum--;
						chatroom.remove(num);
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}finally {
						if(InNum==0)
							isChatRoomOpen = false;
						break;
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
