package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import util.SetFrameDisplay;

public class ClientMain extends JFrame{
	public static String TITLE = "PCBang Management";
	
	JButton btnSend;
	JTextField tfSeatNumber;
	
	public ClientMain() {
		super("자리번호 입력");
		setSize(200, 100);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		SetFrameDisplay.setFrameCenter(this);
		
		tfSeatNumber = new JTextField();
		tfSeatNumber.setBounds(20, 15, 100, 50);
		
		btnSend = new JButton("전송");
		btnSend.setBounds(tfSeatNumber.getX()+tfSeatNumber.getWidth()+10, tfSeatNumber.getY(), 50, 50);
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String seatNumber = tfSeatNumber.getText();
				int sn = Integer.parseInt(seatNumber);
				System.out.println(sn);
				if(sn > 25) {
					JOptionPane.showMessageDialog(null, "25대의 PC밖에 없습니다. 다시 입력해주십시오.");
				} else {
					if(sn < 10) seatNumber = "0"+seatNumber;
					new LoginFrame(seatNumber, TITLE);
					dispose();
				}
			}
		});
		
		add(tfSeatNumber);
		add(btnSend);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ClientMain();
	}
}
