package chatroom;

//This is other's code!!! Not ours!!!

public class ClientView extends JFrame implements ActionListener, KeyListener, Runnable {
    private JTextArea textArea;
    private JTextField textField, tfName;
    private JButton btnSend, btnId;
    private JLabel label;
    private JPanel jp1, jp2;
    public boolean isConnect = false;
    private Socket socket = null;
    private DataInputStream inputStream = null;
    private DataOutputStream outputStream = null;
    private JScrollPane scrollPane;
    private static ClientView view;

    public JTextArea getTextArea() {
        return textArea;
    }

    public DataInputStream getInputStream() {
        return inputStream;
    }
    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public static void main(String[] args) {
				try {
						socket = new Socket("127.0.0.1", 8800);

				} catch (IOException e) {
						e.printStackTrace();
				}
        view = new ClientView();
        ServiceView.clientViews.add(view);
        Thread thread = new Thread(view);
        thread.start();
    }

    private void ClientView() {
        textArea = new JTextArea(20, 20);
        textArea.setEditable(false);
        scrollPane = new JScrollPane(textArea);
        textField = new JTextField(15);
        textField.addKeyListener(this);
        btnSend = new JButton("send");
        btnSend.addActionListener(this);
        label = new JLabel("name:");
        tfName = new JTextField(8);
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp1.add(label);
        jp1.add(tfName);
        tfName.setText("User0");
        jp1.setLayout(new FlowLayout(FlowLayout.CENTER));
        jp2.add(textField);
        jp2.add(btnSend);
        jp2.setLayout(new FlowLayout(FlowLayout.CENTER));

        add(jp1, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(jp2, BorderLayout.SOUTH);
        setTitle("chatroom");
        setSize(500, 500);
        setLocation(450, 150);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() { //close the linked
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (socket != null)
                        socket.close();
                    if (inputStream!= null)
                        inputStream.close();
                    if (outputStream != null)
                        outputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSend) {
            sendMsg();
        }
    }

    private void sendMsg() {
        try {
            String s = textField.getText();
            if (!s.equals("")) { //send message
                textField.setText("");
                textArea.append("I(" + tfName.getText() +  "):\r\n" + s + "\r\n");
                outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.writeUTF(tfName.getText() + "#" + s);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void keyPressed(KeyEvent arg0) {
        if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
            sendMsg();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }

    @Override
    public void run() {
        try {
            inputStream = new DataInputStream(socket.getInputStream());

            while (true) {
                String[] s = inputStream.readUTF().split("#");
                textArea.append(s[0] + ":\r\n" + s[1] + "\r\n");
            }
        } catch (IOException e) {
        }

    }
}
