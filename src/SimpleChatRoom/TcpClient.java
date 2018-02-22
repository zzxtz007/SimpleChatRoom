package SimpleChatRoom;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	DataInputStream dis;
	DataOutputStream dos;

	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public TcpClient()
	{
		socketSet();
		View();
		new ThreadMessageListener(dis, textArea).start();
	}

	private void socketSet()
	{
		try
		{
			socket = new Socket("127.0.0.1", 8800);
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
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
					dos.writeUTF(textField.getText().toString());
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
		private DataInputStream dis;
		private JTextArea area;

		public ThreadMessageListener(DataInputStream dis, JTextArea area)
		{
			this.dis = dis;
			this.area = area;
		}

		public void run()
		{
			while (true)
			{
				try
				{
					String s = dis.readUTF();
					area.append(s+"\n");

				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}

	}

}
