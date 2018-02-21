package SimpleChatRoom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class TcpServer
{
	public static ArrayList<tcpServerThread> allclient = new ArrayList<tcpServerThread>(); // 存放所有通信线程
	public static int clientnum = 0; // 统计客户连接的计数变量

	/**
	 * 建立在客户端建立聊天室
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		easyChatRoom();
	}

	public static void easyChatRoom()
	{
		try
		{
			// 服务器端建立监听端口 8800
			ServerSocket serverSocket = new ServerSocket(8800);

			int num = 0;

			// 开始循环监听
			while (true)
			{
				Socket socket = serverSocket.accept();
				DataInputStream dps = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				tcpServerThread x = new tcpServerThread(socket,dos, dps, clientnum);
				allclient.add(x); // 将线程加入ArrayList中
				x.start();
				clientnum++;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class tcpServerThread extends Thread
	{
		private int num;
		private Socket socket;
		private DataOutputStream dos;
		private DataInputStream dps;

		public tcpServerThread(Socket socket, DataOutputStream dos, DataInputStream dps, int num)
		{
			this.dos = dos;
			this.dps = dps;
			this.num = num;
			this.socket = socket;
		}

		public void run()
		{// 同步消息至所有客户端
			while (true)
			{
				try
				{
					String message = "用户" + num + dps.readUTF();
					for (int i = 0; i < clientnum; i++)
					{
						if (i != num)
						{
							allclient.get(i).dos.writeUTF(message);
						}
						else
						{
							String remessage = "";
							remessage = message.replace("用户" + num, "自己");
							
							// 使自己发送的信息显示在屏幕的右边
							int aa = 35 - remessage.length();
							StringBuffer space = new StringBuffer();
							
							// 输出指定的空格
							for (int j = 0; j < (aa - 1); j++)
							{
								space.append(" ");// 这里是空格
							}
							allclient.get(i).dos.writeUTF((space.toString()) + remessage+"num="+clientnum);
						}
					}
				} catch (IOException e)
				{
					e.printStackTrace();
					try
					{
						socket.close();
						clientnum--;
						allclient.remove(num);
					} catch (IOException e1)
					{
						e1.printStackTrace();
					}finally {
						break;
					}
				}
			}
		}

	}

}
