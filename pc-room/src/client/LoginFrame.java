package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.ClientExit;
import util.SetFrameDisplay;
import util.SqlUtil;

public class LoginFrame extends JFrame {
//	static int LOGIN_DISPLAY_WIDTH = SetFrameDisplay.LOGIN_DISPLAY_WIDTH;
//	static int LOGIN_DISPLAY_HEIGHT = SetFrameDisplay.LOGIN_DISPLAY_HEIGHT;
	static int LOGIN_DISPLAY_WIDTH = 1600;
	static int LOGIN_DISPLAY_HEIGHT = 900;
	
	String seatNumber;
	
	Member loginUser;
	
	LoginFrame 		inchent;
	JPanel 			panelDefault;
	JPanel 			panelNorth;
	JPanel 			panelSouth;
	JPanel 			panelInput;
	
	JLabel 			lbUsername;
	JLabel 			lbPasswd;
	JTextField 		tfUsername;
	JPasswordField 	tfPasswd;
	
	JButton 		btnSignIn;
	JButton 		btnSignUp;
	JButton 		btnShutDown;
	
	public LoginFrame(String seatNumber, String title) {
		super(title);
		this.seatNumber = seatNumber;
		
		setLayout(null);
		setSize(LOGIN_DISPLAY_WIDTH, LOGIN_DISPLAY_HEIGHT);
		setUndecorated(true);	// 타이틀바 
		SetFrameDisplay.setFrameCenter(this);	// 화면 가운데 표시
		
		initPanel();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		inchent = this;
		setVisible(true);
	}
	
	void initPanel() {
		panelDefault = new JPanel();
		panelDefault.setSize(this.getWidth(),this.getHeight());
		panelDefault.setLocation(0,0);
		panelDefault.setLayout(null);
		
		panelNorth = new JPanel();
		panelNorth.setSize(LOGIN_DISPLAY_WIDTH,700);
		panelNorth.setLocation(0, 0);
		panelNorth.setBackground(Color.BLACK);
		
		panelSouth = new JPanel();
		panelSouth.setSize(LOGIN_DISPLAY_WIDTH,200);
		panelSouth.setLayout(null);
		panelSouth.setBackground(Color.CYAN);
		panelSouth.setLocation(0, LOGIN_DISPLAY_HEIGHT-panelSouth.getHeight());
		
		btnShutDown = new JButton("O");
		btnShutDown.setBounds(50, panelSouth.getHeight()-100, 50, 50);
		btnShutDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 컴퓨터 종료
				ClientExit.exit();
			}
		});
		
		panelInput = new JPanel();
		panelInput.setSize(370,200);
		panelInput.setLayout(null);
		panelInput.setLocation(panelSouth.getWidth()-panelInput.getWidth(), 0);
		
		int xgab = 30;
		int ygab = 30;
		
		lbUsername = new JLabel("아이디");
		lbUsername.setSize(50,30);
		lbUsername.setLocation(xgab, ygab);
		
		tfUsername = new JTextField();
		tfUsername.setSize(150,30);
		tfUsername.setLocation(xgab+lbUsername.getWidth()+10, lbUsername.getY());
		
		lbPasswd = new JLabel("비밀번호");
		lbPasswd.setSize(50,30);
		lbPasswd.setLocation(xgab, (ygab*2)+lbUsername.getHeight());
		
		tfPasswd = new JPasswordField();
		tfPasswd.setSize(150,30);
		tfPasswd.setLocation(xgab+lbPasswd.getWidth()+10, lbPasswd.getY());
		
		btnSignIn = new JButton("로그인");
		btnSignIn.setSize(70,90);
		btnSignIn.setLocation(tfUsername.getX()+tfUsername.getWidth()+xgab, tfUsername.getY());
		btnSignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String strId = tfUsername.getText();
				String strPasswd = new String(tfPasswd.getPassword());
				System.out.println(strId);
				System.out.println(strPasswd);
				// data base 검사
				SqlUtil.login(inchent, strId, strPasswd);
			}
		});
		
		btnSignUp = new JButton("회원가입");
		btnSignUp.setSize(100,40);
		btnSignUp.setLocation((panelInput.getWidth()-btnSignUp.getWidth())/2, btnSignIn.getY()+btnSignIn.getHeight()+20);
		btnSignUp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp(inchent);
			}
		});
		
		panelInput.add(lbUsername);
		panelInput.add(tfUsername);
		panelInput.add(lbPasswd);
		panelInput.add(tfPasswd);
		panelInput.add(btnSignIn);
		panelInput.add(btnSignUp);
		
		panelSouth.add(btnShutDown);
		panelSouth.add(panelInput);
		
		panelDefault.add(panelNorth);
		panelDefault.add(panelSouth);
		add(panelDefault);
	}
	
	public String getSeatNumber() {
		return this.seatNumber;
	}
}