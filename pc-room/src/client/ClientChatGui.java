package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientChatGui  extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextArea jta = new JTextArea(40, 25);
	private JTextField jtf = new JTextField(25);
	private ClientBackground client = new ClientBackground();
	public String seatNumber;
	
	public ClientChatGui(String seatNumber) throws IOException {
		super(seatNumber+"번 자리 메신저");
		this.seatNumber = seatNumber;
		System.out.println("사용자 채팅 클라이언트 seatNumber : " + this.seatNumber);
		add(jta, BorderLayout.CENTER);
		add(jtf, BorderLayout.SOUTH);
		jtf.addActionListener(this);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		
		setBounds(800, 100, 400, 600);
		setVisible(true);
		
		client.setGui(this);
		client.setSeatNumber(seatNumber);
	}

	@Override
	// 버튼없이 앤터를 눌러도 자동으로 매세지를 보내주는 역할
	public void actionPerformed(ActionEvent e) {
		String msg = seatNumber+ "번 자리 :" + jtf.getText()+"\n";
		client.sendMessage(msg);
		jtf.setText("");
	}

	// 메세지 gui화면에 출력
	public void appendMsg(String msg) {
		jta.append(msg);
	}
}
