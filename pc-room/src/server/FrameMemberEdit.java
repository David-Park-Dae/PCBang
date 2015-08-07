package server;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.SetFrameDisplay;

class FrameMemberEdit extends JFrame implements ActionListener, KeyListener {
	String beforeId;
	boolean idFlag = true;
	JOptionPane jop;
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	StringBuffer sql;

	JPanel plEditBackground;
	JPanel plEditLine1;
	JPanel plEditLine2;
	JPanel plEditLine3;
	JPanel plEditLine4;

	JLabel lbEditName;
	JLabel lbEditId;
	JLabel lbEditPwd;

	JTextField tfEditName;
	JTextField tfEditId;

	JButton btnEditConfirm;
	JButton btnEditCancel;
	JButton btnEditResetPwd;

	MemberHelper member;

	public FrameMemberEdit(String name, String id, MemberHelper member) {
		this.member = member;
		beforeId = id;
		setTitle("회원정보수정");
		setSize(300, 180);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		plEditBackground = new JPanel();
		plEditLine1 = new JPanel();
		plEditLine2 = new JPanel();
		plEditLine3 = new JPanel();
		plEditLine4 = new JPanel();
		lbEditName = new JLabel("이름  :  ");
		lbEditId = new JLabel("아이디 : ");
		lbEditPwd = new JLabel("비밀번호:");
		tfEditName = new JTextField(name, 7);
		tfEditId = new JTextField(id, 7);
		btnEditConfirm = new JButton("수정");
		btnEditCancel = new JButton("취소");
		btnEditResetPwd = new JButton("초기화");

		tfEditId.addKeyListener(this);
		btnEditConfirm.addActionListener(this);
		btnEditCancel.addActionListener(this);

		plEditLine1.add(lbEditName);
		plEditLine1.add(tfEditName);
		plEditLine2.add(lbEditId);
		plEditLine2.add(tfEditId);
		plEditLine3.add(lbEditPwd);
		plEditLine3.add(btnEditResetPwd);
		plEditLine4.add(btnEditConfirm);
		plEditLine4.add(btnEditCancel);

		plEditBackground.setLayout(new GridLayout(4, 1));
		plEditBackground.add(plEditLine1);
		plEditBackground.add(plEditLine2);
		plEditBackground.add(plEditLine3);
		plEditBackground.add(plEditLine4);
		add(plEditBackground);

		setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		checkId();
		if (idFlag == false) {
			tfEditId.setBackground(Color.red);
		} else {
			tfEditId.setBackground(Color.white);
		}
		System.out.println(idFlag);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnEditConfirm)) {
			if (idFlag == true) {
				if (!tfEditId.getText().equals("") && !tfEditName.getText().equals("")) {
					member.edit(tfEditName.getText(), tfEditId.getText(), beforeId);
					this.dispose();
				}else
				{
					jop.showMessageDialog(null, "값을 입력해주셔야 합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				// 아이디 중복 경고 다이얼로그
				jop.showMessageDialog(null, "중복되는 아이디가 있습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (obj.equals(btnEditCancel)) {
			this.dispose();
		}
		if (obj.equals(btnEditResetPwd)) {
			member.pwdReset();
		}
	}

	private void checkId() {
		conn = MakeConnection.getConnection();

		sql = new StringBuffer();
		sql.append("select mb_id from member ");
		sql.append("where mb_id!=? ");
		sql.append("and mb_id=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, beforeId);
			pstmt.setString(2, (tfEditId.getText()));
			rs = pstmt.executeQuery();
			if (rs.next()) {
				idFlag = false;
			} else
				idFlag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("쿼리오류");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("접속종료오류");
			}
		}
	}
}