package server;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class FrameLogin extends JFrame implements ActionListener {
	Dimension d;
	// background 패널 하나 박고 그위에 라인 3개 두면서 패널 3개를 박음
	// line1 : 로고
	// line2 : 아이디
	// line3 : pwd
	// line4 : 버튼
	JPanel plBackground;
	JPanel plLine1;
	JPanel plLine2;
	JPanel plLine3;
	JPanel plLine4;

	JLabel lbId;
	JLabel lbPwd;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton btnLogin;
	JButton btnExit;

	JOptionPane jop;

	FrameLogin() {
		setTitle("관리자 로그인");

		jop = new JOptionPane();
		Toolkit tool = Toolkit.getDefaultToolkit();
		d = tool.getScreenSize();
		int frameX = 400;
		int frameY = 200;
		setBounds((d.width / 2) - (frameX / 2), (d.height / 2) - (frameY / 2), frameX, frameY);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 패널들 초기화
		plBackground = new JPanel();
		plBackground.setLayout(new GridLayout(4, 1, 0, -5));
		plLine1 = new JPanel();
		plLine2 = new JPanel();
		plLine3 = new JPanel();
		plLine4 = new JPanel();
		plBackground.add(plLine1);
		plBackground.add(plLine2);
		plBackground.add(plLine3);
		plBackground.add(plLine4);

		// 라벨, 버튼, 텍스트 필드 초기화
		lbId = new JLabel("관리자 계정 : ");
		lbPwd = new JLabel("비 밀 번 호 : ");
		tfId = new JTextField(10);
		pfPwd = new JPasswordField(10);
		btnLogin = new JButton("Login");
		btnExit = new JButton("Exit");

		// 액션리스너 추가
		btnLogin.addActionListener(this);
		btnExit.addActionListener(this);

		// 버튼, 텍스트 필드 넣기
		plLine2.add(lbId);
		plLine2.add(tfId);

		plLine3.add(lbPwd);
		plLine3.add(pfPwd);

		plLine4.add(btnLogin);
		plLine4.add(btnExit);

		add(plBackground);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = new Object();
		obj = e.getSource();

		if (obj.equals(btnLogin)) {
			FrameServer fs = new FrameServer();
			dispose();
		}
		if (obj.equals(btnExit)) {
			int result = jop.showConfirmDialog(null, "종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
	}
}
