package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.DBConnection;
import util.SqlUtil;

public class SignUp extends JFrame{

	public final int XMARGIN = 20;
	public final int YMARGIN = 30;
	public final int XGAB = 20;
	public final int YGAB = 15;

	Connection conn;
	
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
	
	boolean chkIdFlag = false;
	boolean chkPasswdFlag = false;
	
	String strName;
	String strUsername;
	String strPasswd;
	
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
		tfUsername.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				strUsername = tfUsername.getText();
				// DB에 가서 검사
				chkIdFlag = SqlUtil.chkId(strUsername);

				if(chkIdFlag) 	tfUsername.setBackground(Color.red);
				else 			tfUsername.setBackground(Color.WHITE);
			}
		});
		
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
		tfChkPasswd.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				strPasswd = new String(tfPasswd.getPassword());
				String strChkPasswd = new String(tfChkPasswd.getPassword());
				// 비밀번호가 같은지 같지 않는지 검사
				if(!strPasswd.equals(strChkPasswd)) {
					chkPasswdFlag = false;
					tfChkPasswd.setBackground(Color.red);
				} else {
					chkPasswdFlag = true;
					tfChkPasswd.setBackground(Color.WHITE);
				}
			}
		});
		
		
		btnSignUp = new JButton("확인");
		btnSignUp.setSize(70,40);
		btnSignUp.setLocation(XMARGIN+XGAB*2, lbChkPasswd.getHeight()+lbChkPasswd.getY()+YGAB);
		btnSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				strName = tfName.getText();
				strUsername = tfUsername.getText();
				strPasswd = new String(tfPasswd.getPassword());
				
				if(strName.equals("")) {
					JOptionPane.showMessageDialog(panelMain, "이름을 확인해주세요.");
					tfName.requestFocus();
				} else if(!chkPasswdFlag) {	// 입력한 패스워드가 같지 않을 경우
					JOptionPane.showMessageDialog(panelMain, "비밀번호를 확인해주세요.");
					tfChkPasswd.requestFocus();
				} else if (chkIdFlag) {
					JOptionPane.showMessageDialog(panelMain, "아이디를 확인해주세요.");
					tfUsername.requestFocus();
				} else {
					// DB처리
					boolean flag = SqlUtil.signUp(strName, strUsername, strPasswd);
					if(flag)
						JOptionPane.showMessageDialog(panelMain, "회원가입이 완료되었습니다.");
					else
						JOptionPane.showMessageDialog(panelMain, "회원가입에 실패하였습니다.");
					dispose();
				}
				
			}
		});
		
		btnSignUpCancel = new JButton("취소");
		btnSignUpCancel.setSize(70,40);
		btnSignUpCancel.setLocation(btnSignUp.getX()+btnSignUp.getWidth()+XGAB, lbChkPasswd.getHeight()+lbChkPasswd.getY()+YGAB);
		btnSignUpCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int end = JOptionPane.showConfirmDialog(panelMain, "저장되지 않습니다. 취소하시겠습니까?", "주의!",JOptionPane.YES_NO_OPTION);
				if(end == JOptionPane.OK_OPTION) 
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
				int end = JOptionPane.showConfirmDialog(panelMain, "저장되지 않습니다. 종료하시겠습니까?", "주의!",JOptionPane.YES_NO_OPTION);
				if(end == JOptionPane.OK_OPTION) 
					dispose();
			}
		});
		
		setResizable(false);
		setVisible(true);
	}
}
