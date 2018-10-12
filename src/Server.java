import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Server extends JFrame implements ActionListener{

	static ServerSocket serv_sock;
	static Socket sock;
	JPanel panel;
	JTextField msg;
	JTextArea chat_field;
	JButton button;
	DataInputStream dis;
	DataOutputStream dos;


	public Server() throws IOException{
		panel = new JPanel();
		msg = new JTextField();
		chat_field = new JTextArea();
		button = new JButton("Enter");
		
		this.setTitle("Server");
		this.setVisible(true);
		this.setSize(350, 350);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setBackground(Color.GRAY);
		panel.setLayout(null);
		this.add(panel);
		
		//edit and add chat display area 
		chat_field.setBounds(20, 20, 310, 210);	
		panel.add(chat_field);
		
		//edit and add message text field
		msg.setBounds(20, 270, 230, 30);
		panel.add(msg);
		
		//edit and add send button
		
		button.setBounds(250, 270, 80, 30);
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.setOpaque(true);
		button.setBorderPainted(false);
		panel.add(button);
		button.addActionListener(this);
		
		
		
		serv_sock = new ServerSocket(2000, 1, InetAddress.getLocalHost());
		chat_field.setText("Waiting for connection");
		sock = serv_sock.accept();
		chat_field.setText(chat_field.getText() + '\n' + "Client detected! You can begin conversation.");
		
		while (true) {
				InputStream s1 = sock.getInputStream();
				DataInputStream dis = new DataInputStream(s1);
				String string = dis.readUTF();
				chat_field.setText(chat_field.getText() + '\n' + "Client:" + string);				
		}
	}
		
		
		@Override
		public void actionPerformed(ActionEvent e){
			if ((e.getSource() == button) && (msg.getText() != "")) {
				chat_field.setText(chat_field.getText() + '\n' + "Me:"
						+ msg.getText());
			try{
					DataOutputStream dos = new DataOutputStream(
					sock.getOutputStream());
					dos.writeUTF(msg.getText());
			}catch(IOException exc){
				System.out.println(e);
			}
					
		}
				msg.setText("");
	}
		
		public static void main(String[] args) throws IOException {
			Server server = new Server();
		}
}


	
		


