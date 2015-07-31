package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.SetFrameDisplay;

public class LoginFrame extends JFrame {
	static int DISPLAYWIDTH = SetFrameDisplay.DISPLAYWIDTH;
	static int DISPLAYHEIGHT = SetFrameDisplay.DISPLAYHEIGHT;
	
	JPanel panelDefault;
	JPanel panelInput;
	
	JLabel pgTitle;
	JLabel lbUsername;
	JLabel lbPasswd;
	JTextField tfUsername;
	JTextField tfPasswd;
	
	JButton btnSignIn;
	JButton btnSignUp;
	JButton btnShutDown;
	
	public LoginFrame(String title) {
		super(title);

		setLayout(null);
		setSize(DISPLAYWIDTH, DISPLAYHEIGHT);
		SetFrameDisplay.setFrameCenter(this);	// 화면 가운데 표시
		SetFrameDisplay.setFullMode(this);	// 전체화면 모드
		
		initDefaultPanel();
		
		setVisible(true);
	}
	
	void initDefaultPanel() {
		panelDefault = new JPanel();
		panelDefault.setSize(this.getWidth(),this.getHeight());
		panelDefault.setLocation(0,0);

		panelDefault.setLayout(null);
		
		pgTitle = new JLabel("프로그램");
		pgTitle.setSize(100,50);
		pgTitle.setLocation((DISPLAYWIDTH-pgTitle.getWidth())/2, pgTitle.getHeight()+20);
		
		btnShutDown = new JButton("컴퓨터종료");
		btnShutDown.setBounds(50, DISPLAYHEIGHT-120, 70, 70);
		btnShutDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 컴퓨터 종료 코드
				System.out.println(System.getProperty("os.name"));
//				Runtime runtime = Runtime.getRuntime();
//				try {
//					
//					Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
//					OutputStream os = process.getOutputStream();
//					os.write("shutdown -s -f \n\r".getBytes());
//					os.close();
//					process.waitFor();
//				} catch (IOException e1) {
//					System.err.println("runtimeExec IOException: " + e1.getMessage());
//				} catch (InterruptedException e1) {
//					e1.printStackTrace();
//				}
			}
		});
		panelDefault.add(pgTitle);
		panelDefault.add(btnShutDown);
		add(panelDefault);
		
	}
}
