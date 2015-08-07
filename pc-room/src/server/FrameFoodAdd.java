package server;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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

import util.DBConnection;
import util.SetFrameDisplay;

public class FrameFoodAdd extends JFrame implements ActionListener, KeyListener {
	boolean nameFlag = true;
	JOptionPane jop;
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	StringBuffer sql;
	
	JPanel plAddBackground;
	JPanel plAddLine1;
	JPanel plAddLine2;
	JPanel plAddLine3;

	JLabel lbAddName;
	JLabel lbAddStock;

	JTextField tfAddName;
	JTextField tfAddStock;

	JButton btnAddConfirm;
	JButton btnAddCancel;
	
	FoodHelper food;

	public FrameFoodAdd(FoodHelper food) {
		this.food = food;
		setTitle("음식추가");
		setSize(300, 150);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		plAddBackground = new JPanel();
		plAddLine1 = new JPanel();
		plAddLine2 = new JPanel();
		plAddLine3 = new JPanel();
		lbAddName = new JLabel("이름  : ");
		lbAddStock = new JLabel("재고 : ");
		tfAddName = new JTextField(7);
		tfAddStock = new JTextField(7);
		btnAddConfirm = new JButton("추가");
		btnAddCancel = new JButton("취소");

		tfAddName.addKeyListener(this);
		tfAddStock.addKeyListener(new KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}
			}
		});
		btnAddConfirm.addActionListener(this);
		btnAddCancel.addActionListener(this);

		plAddLine1.add(lbAddName);
		plAddLine1.add(tfAddName);
		plAddLine2.add(lbAddStock);
		plAddLine2.add(tfAddStock);
		plAddLine3.add(btnAddConfirm);
		plAddLine3.add(btnAddCancel);

		plAddBackground.setLayout(new GridLayout(3, 1));
		plAddBackground.add(plAddLine1);
		plAddBackground.add(plAddLine2);
		plAddBackground.add(plAddLine3);
		add(plAddBackground);

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
		checkName();
		if (nameFlag == false) {
			tfAddName.setBackground(Color.red);
		} else {
			tfAddName.setBackground(Color.white);
		}
		System.out.println(nameFlag);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnAddConfirm)) {
			if (nameFlag == true) {
				if(!tfAddStock.getText().equals("") && !tfAddName.getText().equals("")){
				food.add(tfAddName.getText(), Integer.parseInt(tfAddStock.getText()));
				this.dispose();
				}else{
					jop.showMessageDialog(null, "값을 입력해주셔야 합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				// 아이디 중복 경고 다이얼로그
				jop.showMessageDialog(null, "상품이 이미 등록되어 있습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (obj.equals(btnAddCancel)) {
			this.dispose();
		}
	}

	private void checkName() {
		conn = DBConnection.getConnection();

		sql = new StringBuffer();
		sql.append("select fd_name from food ");
		sql.append("where fd_name=? ");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, tfAddName.getText());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				nameFlag = false;
			} else
				nameFlag = true;
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
