package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class EfficientServer
{
	public static ArrayList<TCPServerThread> allclient = new ArrayList<TCPServerThread>();
	public static int clientnum = 0; // num of client

	/**
	 * Create the chatroom, this server file is more faster, but it also needs more CPU resources
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

			ServerSocket serverSocket = new ServerSocket(8800);
			int num = 0;
			System.out.println ("The Server is running.");
			//private static PrintWriter Wri=null;
      //private static BufferedReader Rea=null;


			while (true)
			{
				Socket socket = serverSocket.accept();
				System.out.println ("Get the user!");
				DataInputStream dps = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				TCPServerThread chat_thread = new TCPServerThread(socket,dos, dps);
				allclient.add(chat_thread); // add this threaed to ArrayList
				chat_thread.start();
				clientnum++;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	static class TCPServerThread extends Thread
	{
		private int num;
		private Socket socket;
		private DataOutputStream dos;
		private DataInputStream dps;

		public TCPServerThread(Socket socket, DataOutputStream dos, DataInputStream dps)
		{
			this.dos = dos;
			this.dps = dps;
			this.socket = socket;
		}

		public void run()
		{// send the message back to client
			while (true)
			{
				this.num = allclient.indexOf(this);
				try
				{
					String message = "User" + num + ":" + dps.readUTF();
					for (int i = 0; i < clientnum; i++) //if clientnum become smaller, let's say, from 12 to 11, but num is still 11, but its real number should be 11-1.
					{
						if (allclient.get(i) != this)
						{
							allclient.get(i).dos.writeUTF(message); //update all clients' output??
						}
						else
						{
							String finalmessage = "";
							finalmessage = message.replace("User" + num, "You");

							// show 'my' sessage on the right of the screen
							//int space_num = 35 - remessage.length();
							//StringBuffer space = new StringBuffer();

							// print space
							/*for (int j = 0; j < (space_num - 1); j++)
							{
								space.append(" ");
							}
							allclient.get(i).dos.writeUTF((space.toString()) + remessage+"num="+clientnum); */
							allclient.get(i).dos.writeUTF(finalmessage+"  -your num="+ clientnum);
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
