package chatroom;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TcpClient {
	private static DataInputStream inpu;
	private static DataOutputStream outpu;
    private static Socket socket;
    static Scanner scanner=new Scanner(System.in);
    /**
     * @param args
     */
    public static void main(String[] args) {
        client();
    }

    public static void client (){
        try {
            socket = new Socket("127.0.0.1", 8800);
            outpu = new DataOutputStream(socket.getOutputStream());
						Receiver rece = new Receiver(socket);
						rece.start();
            while(true){
                System.out.println("Please print: ");
                String str = scanner.nextLine();
                outpu.writeUTF(str); //close??
                if(str.equals("exit")){
                    break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("connection is broken! \nDo you want to reconnect? (y/n)");
            String recon = scanner.nextLine();
            if (recon.equals("y"))
                client();
            else
                return;
        }
    }


		static class Receiver extends Thread{
        //build public receiver(Socket socket) first!!!
            private Socket socket;
            public Receiver (Socket socket){ this.socket = socket; }
			public void run (){
					try{
					inpu = new DataInputStream(socket.getInputStream());
					while (true){
							String gotmessage = inpu.readUTF();;
							System.out.println(gotmessage);
						}
					}catch (IOException e){
		          e.printStackTrace();
							System.out.println("connection error");
									return;
						}
					}
				}

			}
