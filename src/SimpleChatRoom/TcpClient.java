package SimpleChatRoom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import classTestBest.userChat;

import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class TcpClient extends JFrame
{
	private Socket socket;
	private JPanel contentPane;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JTextArea textArea;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	userChat uc;
	userChat oc;
	int i =0;
	String[] ss = {"11","22","33","44","55"};

	/**
	 * 启动程序.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					TcpClient frame = new TcpClient();
					frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 创建界面.
	 */
	public TcpClient()
	{
		uc = new userChat("aaa","asdasdas");
		socketSet();
		View();
		System.out.println(333);
		new ThreadMessageListener(ois, textArea).start();
	}

	private void socketSet()
	{
		try
		{
			socket = new Socket("127.0.0.1", 8801);
			ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
			oos = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void View()
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle(uc.getChatname());
		
		btnNewButton = new JButton("发送");

		btnNewButton.setBounds(20, 231, 93, 23);
		contentPane.add(btnNewButton);

		btnNewButton_1 = new JButton("关闭");

		btnNewButton_1.setBounds(292, 231, 93, 23);
		contentPane.add(btnNewButton_1);

		textField = new JTextField();
		textField.setBounds(20, 199, 365, 23);
		contentPane.add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 414, 179);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		Listener();
	}

	private void Listener()
	{
		// 发送按钮的监听
		btnNewButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					i++;
					i=i%5;
					uc.setSay(ss[i]);
					//oos.writeObject(uc);
					oos.writeUTF(textField.getText());
					oos.flush();
					System.out.println("action::"+uc);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		// 关闭按钮的监听
		btnNewButton_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});
	}

	class ThreadMessageListener extends Thread
	{
		private ObjectInputStream ois;
		private JTextArea area;
		
		public ThreadMessageListener(ObjectInputStream ois, JTextArea area)
		{
			this.ois = ois;
			this.area = area;
		}

		public void run()
		{
			while (true)
			{
				try
				{
					System.out.println(444);
					oc= (userChat) ois.readObject();
					System.out.println(oc.getFlag());
					String s = oc.toString();
					area.append(s+"\n");

				} catch (Exception e)
				{
					e.printStackTrace();
					break;
				}
			}
		}

	}

}
