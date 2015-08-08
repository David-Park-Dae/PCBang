package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerChatGui extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea(40, 25);
	private JTextField jtf = new JTextField(25);
	private ServerClientBackground server = new ServerClientBackground();
	
	public String seatNumber;

	public ServerChatGui(String seatNumber) throws IOException {
		super(seatNumber+"번 PC");
		this.seatNumber = seatNumber;
		System.out.println("서버 채팅 클라이언트 seatNumber : " + this.seatNumber);
		add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		setVisible(false);
		setBounds(200, 100, 400, 600);

		server.setGui(this);
		server.connet(this.seatNumber);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		String msg = "관리자 : "+ jtf.getText() + "\n";
		System.out.print(msg);
		server.sendMessage(msg);
		jtf.setText("");
	}

	public void appendMsg(String msg) {
		jta.append(msg);
	}
}
