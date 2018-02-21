package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import chatroom.calculation;


public class TcpServer{
	public static ArrayList<TCPServerThread> allclient = new ArrayList<TCPServerThread>();
	public static int clientnum = 0; // num of client
	public static Queue<String> Allnewmessages = new ConcurrentLinkedQueue<String>();

	/**
	 * Create the chatroom
	 *
	 * @param args
	 */
	public static void main(String[] args){
		easyChatRoom();
	}

	public static void easyChatRoom(){
		try{
			ServerSocket serverSocket = new ServerSocket(8800);
			int num = 0;
			System.out.println ("The Server is running.");
			//private static PrintWriter Wri=null;
      //private static BufferedReader Rea=null;

			SynchronizeThread Synchronization = new SynchronizeThread();
			Synchronization.start();
			while (true){
				Socket socket = serverSocket.accept();
				System.out.println ("Get the user!");
				DataInputStream dps = new DataInputStream(socket.getInputStream());
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				TCPServerThread chat_thread = new TCPServerThread(socket,dos, dps);
				allclient.add(chat_thread); // add this threaed to ArrayList
				chat_thread.start();
				clientnum++;
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	static class TCPServerThread extends Thread{
		private int num;
		private Socket socket;
		private DataOutputStream dos;
		private DataInputStream dps;

		public void closesocket() throws IOException {
			socket.close();
		}

		public TCPServerThread(Socket socket, DataOutputStream dos, DataInputStream dps){
			this.dos = dos;
			this.dps = dps;
			this.socket = socket;
		}

		public void run(){
			// send the message back to client
			while (true){
				this.num = allclient.indexOf(this);
				try{
					//String message = "User" + num + ":" + dps.readUTF();
					String message = num + ":" + dps.readUTF();
					Allnewmessages.offer(message);
					/*
					for (int i = 0; i < clientnum; i++){
						if (i != num){
							allclient.get(i).dos.writeUTF(message); //update all clients' output??
						}
						else{
							String finalmessage = "";
							remessage = finalmessage.replace("User" + num, "You");
							*/

							// show 'my' sessage on the right of the screen
							//int space_num = 35 - remessage.length();
							//StringBuffer space = new StringBuffer();

							// print space
							/*for (int j = 0; j < (space_num - 1); j++){
								space.append(" ");
							}
							allclient.get(i).dos.writeUTF((space.toString()) + remessage+"num="+clientnum); */

							//String finalmessage = "";
							String finalmessage = "";
							finalmessage = message.replace(""+num, "You");
							allclient.get(num).dos.writeUTF(finalmessage+"  -your num="+ (clientnum-1));
					//	}
					//}
				} catch (IOException e){
					e.printStackTrace();
					try{
						socket.close();
						clientnum--;
						allclient.remove(num);
					} catch (IOException e1){
						e1.printStackTrace();
					}finally {
						break;
					}
				}
			}
		}
	}

	static class SynchronizeThread extends Thread{
		public void run(){
			while(true){
				while ( ! Allnewmessages.isEmpty()){
					String queuemessage = Allnewmessages.peek();
					String num_of_user = queuemessage.substring(0, queuemessage.indexOf(":"));
					int intNum = calculation.strtoint(num_of_user); //get the number of the user who sent this message.
					System.out.println ("The char is "+num_of_user+" int is "+ intNum +"clinumber is "+clientnum);
					for (int i =0; i< clientnum; i++){
						if (i != intNum)
							try {
								allclient.get(i).dos.writeUTF("User" + Allnewmessages.peek());
							} catch (IOException e) {
								e.printStackTrace();
								try{
									allclient.get(i).closesocket();
									clientnum--;
									allclient.remove(i);
								} catch (IOException e1){
									e1.printStackTrace();
								}finally {
									break;
								}
							}
					}
					Allnewmessages.poll();
				}
			}
		}
	}

}
