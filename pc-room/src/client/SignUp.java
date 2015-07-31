package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignUp extends JFrame{

	public static int XMARGIN = 20;
	public static int YMARGIN = 30;
	public static int XGAB = 20;
	public static int YGAB = 15;
	
	JPanel panelMain;
	
	JLabel lbUsername;
	JLabel lbName;
	JLabel lbPasswd;
	JLabel lbChkPasswd;
	
	JTextField tfUsername;
	JTextField tfName;
	JPasswordField tfPasswd;
	JPasswordField tfChkPasswd;
	
	JButton btnSignUp;
	JButton btnSignUpCancel;
	
	public SignUp(JFrame owner) {
		this.setTitle("회원가입");
		this.setLayout(null);
		this.setSize(320,300);
		this.setLocation(owner.getX()+(owner.getWidth()-this.getWidth())/2, owner.getY()+(owner.getHeight()-this.getHeight())/2);
		
		// 메인 판넬
		panelMain = new JPanel();
		panelMain.setLayout(null);
		panelMain.setSize(260,230);
		panelMain.setLocation(XMARGIN, YMARGIN);
		
		
		
		lbName = new JLabel("이름");
		lbName.setSize(70,30);
		lbName.setLocation(XGAB, 0);
		
		tfName = new JTextField();
		tfName.setSize(150,30);
		tfName.setLocation(XGAB*2+lbName.getWidth(), lbName.getY());
		
		lbUsername = new JLabel("아이디");
		lbUsername.setSize(70,30);
		lbUsername.setLocation(XGAB, lbName.getY()+lbName.getHeight()+YGAB);
		
		tfUsername = new JTextField();
		tfUsername.setSize(150,30);
		tfUsername.setLocation(XGAB*2+lbUsername.getWidth(), lbUsername.getY());
		
		lbPasswd = new JLabel("비밀번호");
		lbPasswd.setSize(70,30);
		lbPasswd.setLocation(XGAB, lbUsername.getY()+lbUsername.getHeight()+YGAB);
		
		tfPasswd = new JPasswordField();
		tfPasswd.setSize(150,30);
		tfPasswd.setLocation(XGAB*2+lbPasswd.getWidth(), lbPasswd.getY());
		
		lbChkPasswd = new JLabel("비밀번호 확인");
		lbChkPasswd.setSize(70,30);
		lbChkPasswd.setLocation(XGAB, lbPasswd.getY()+lbPasswd.getHeight()+YGAB);
		
		tfChkPasswd = new JPasswordField();
		tfChkPasswd.setSize(150,30);
		tfChkPasswd.setLocation(XGAB*2+lbChkPasswd.getWidth(), lbChkPasswd.getY());
		
		
		btnSignUp = new JButton("확인");
		btnSignUp.setSize(70,40);
		btnSignUp.setLocation(XMARGIN+XGAB*2, lbChkPasswd.getHeight()+lbChkPasswd.getY()+YGAB);
		btnSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 중복처리
				
				
				// 회원가입 완료
//				JOptionPane.showMessageDialog(panelMain, "회원가입이 완료되었습니다.");
//				dispose();
				
				JOptionPane.showMessageDialog(panelMain, "회원가입이 아이디와 비밀번호를 확인해주세요.");
				tfName.requestFocus();
				
			}
		});
		
		btnSignUpCancel = new JButton("취소");
		btnSignUpCancel.setSize(70,40);
		btnSignUpCancel.setLocation(btnSignUp.getX()+btnSignUp.getWidth()+XGAB, lbChkPasswd.getHeight()+lbChkPasswd.getY()+YGAB);
		btnSignUpCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		
		panelMain.add(lbName);
		panelMain.add(tfName);
		
		panelMain.add(lbUsername);
		panelMain.add(tfUsername);
		
		panelMain.add(lbPasswd);
		panelMain.add(tfPasswd);
		panelMain.add(lbChkPasswd);
		panelMain.add(tfChkPasswd);
		
		panelMain.add(btnSignUp);
		panelMain.add(btnSignUpCancel);
		
		add(panelMain);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		setResizable(false);
		setVisible(true);
	}
}
