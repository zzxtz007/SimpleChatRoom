package classTestServer;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import classTestBest.userChat;

public class ChatRoomThread extends Thread
{
	boolean[] isChatRoomOpen;
	ArrayList<chatThread> chatroom = new ArrayList<chatThread>();
	// Map<userChat, chatThread> chatroom = new HashMap<userChat, chatThread>();
	int InNum = 0;

	public ChatRoomThread(boolean[] isChatRoomOpen) throws IOException
	{
		this.isChatRoomOpen = isChatRoomOpen;
		this.start();
	}

	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(8801);
			while (true)
			{
				System.out.println("正在监听是否有新的加入");
				Socket socket = serverSocket.accept();
				System.out.println("未有聊天室加入新成员"+chatroom);
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				chatThread x = new chatThread(socket, oos, ois, InNum);
				// chatroom.put(arg0, arg1)
				System.out.println("聊天室进程工作正常");
				chatroom.add(x); // 将线程加入ArrayList中
				x.start();
				InNum++;
				System.out.println("聊天者人数为："+InNum);
				System.out.println(chatroom);
			}
		} catch (IOException e)
		{
			System.out.println("聊天室线程即将关闭");
			if(serverSocket!=null){
				try
				{
					System.out.println("关闭serverSocket");
					serverSocket.close();
				} catch (IOException e1)
				{
					System.out.println("关闭serverSocket错误");
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}

	}

	class chatThread extends Thread
	{
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
					System.out.println("同步消息进程监听中");
//					userChat uc = (userChat) ois.readObject();
					String s = ois.readUTF();
					userChat uc = new userChat("text", s);
					for (int i = 0; i < InNum; i++)
					{
						if (i != num)
						{
							uc.setFlag(50);
							chatroom.get(i).oos.writeObject(uc);
							System.out.println("发送给别人"+"num::"+num+" InNum::"+InNum);
							oos.flush();
							//System.out.println(uc);
							} else
						{
							uc.setFlag(45);
							chatroom.get(i).oos.writeObject(uc);
							oos.flush();
							System.out.println("发送给自己"+"num::"+num+" InNum::"+InNum);
						}
						System.out.println("发送的内容：：发送者：："+uc.getChatname()+"内容："+uc.getSay());
					}
					// flag = 50 别人发送的
					// flag = 45 自己发送的
				} catch (IOException e)
				{
					System.out.println("无法连接至远程客户端，已断开连接");
					e.printStackTrace();
					try
					{
						socket.close();
						InNum--;
						chatroom.remove(num);
					} catch (IOException e1)
					{
						e1.printStackTrace();
					} finally
					{
						if (InNum == 0)
							isChatRoomOpen[1] = false;
						//System.out.println(isChatRoomOpen[1]);
						break;
					}
				}
			}
		}

	}

}
