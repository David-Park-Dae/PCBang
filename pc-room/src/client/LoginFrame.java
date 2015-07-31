package client;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.SetFrameCenter;
import util.SetFullMode;

public class LoginFrame extends JFrame {
	
	JPanel panelDefault;
	JPanel panelInput;
	
	JLabel lbUsername;
	JLabel lbPasswd;
	JTextField tfUsername;
	JTextField tfPasswd;
	
	JButton btnSignIn;
	JButton btnSignUp;
	
	public LoginFrame(String title) {
		super(title);
		
		setSize(1600,900);
		SetFrameCenter.setFrameCenter(this);	// 화면 가운데 표시
		SetFullMode.setFullMode(this);	// 전체화면 모드
		
		setVisible(true);
	}
}
