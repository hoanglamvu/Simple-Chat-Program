import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame implements ActionListener{
	
	static Socket sock;
	JPanel panel;
	JTextField msg;
	JTextArea chat_field;
	JButton button;
	
	
	public Client() throws UnknownHostException, IOException{
		panel = new JPanel();
		msg = new JTextField();
		chat_field = new JTextArea();
		button = new JButton("Enter");
		panel.setBackground(Color.YELLOW);
		
		this.setTitle("Client");
		this.setVisible(true);
		this.setSize(350, 350);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
				button.addActionListener(this);
				panel.add(button);
				
				sock = new Socket(InetAddress.getLocalHost(), 2000);
				chat_field.setText("Connected to Server! You can begin conversation.");
				
				while (true) {
					InputStream s1 = sock.getInputStream();
					DataInputStream dis = new DataInputStream(s1);
					String string = dis.readUTF();
					chat_field.setText(chat_field.getText() + '\n' + "Server:" + string);
				}
		}
	
		@Override
		public void actionPerformed(ActionEvent e){
			if ((e.getSource() == button) && (msg.getText() != "")){
				chat_field.setText(chat_field.getText() + '\n' + "Me:"+ msg.getText());
				try {
					OutputStream sout = sock.getOutputStream();
					DataOutputStream dos = new DataOutputStream(sout);
					dos.writeUTF(msg.getText());
				} catch (IOException e1) {
					System.out.println(e);
					}
				}
				msg.setText("");
			
			}
		
		public static void main(String[] args) throws IOException {
			Client client = new Client();
		}
	}


