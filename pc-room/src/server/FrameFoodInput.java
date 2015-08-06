package server;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.SetFrameDisplay;

public class FrameFoodInput extends JFrame implements ActionListener {
	JOptionPane jop;
	Connection conn;
	ResultSet rs = null;
	PreparedStatement pstmt = null;
	StringBuffer sql;

	JPanel plInputBackground;
	JPanel plInputLine1;
	JPanel plInputLine2;
	JPanel plInputLine3;

	JLabel lbInputName;
	JLabel lbInputStock;

	JComboBox comboboxInputName;
	JTextField tfInputStock;

	JButton btnInputConfirm;
	JButton btnInputCancel;

	Food food;

	public FrameFoodInput(Food food) {
		this.food = food;
		setTitle("음식입고");
		setSize(300, 150);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		comboboxInputName = new JComboBox(food.getNameArray());
		
		plInputBackground = new JPanel();
		plInputLine1 = new JPanel();
		plInputLine2 = new JPanel();
		plInputLine3 = new JPanel();
		lbInputName = new JLabel("제품명  : ");
		lbInputStock = new JLabel("입고량 : ");
		tfInputStock = new JTextField(4);
		btnInputConfirm = new JButton("추가");
		btnInputCancel = new JButton("취소");

		tfInputStock.addKeyListener(new KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}
			}
		});
		btnInputConfirm.addActionListener(this);
		btnInputCancel.addActionListener(this);

		plInputLine1.add(lbInputName);
		plInputLine1.add(comboboxInputName);
		plInputLine2.add(lbInputStock);
		plInputLine2.add(tfInputStock);
		plInputLine3.add(btnInputConfirm);
		plInputLine3.add(btnInputCancel);

		plInputBackground.setLayout(new GridLayout(3, 1));
		plInputBackground.add(plInputLine1);
		plInputBackground.add(plInputLine2);
		plInputBackground.add(plInputLine3);
		add(plInputBackground);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnInputConfirm)) {
			System.out.println((String)comboboxInputName.getSelectedItem());
			if (!tfInputStock.getText().equals("")) {
				food.input((String)comboboxInputName.getSelectedItem(), Integer.parseInt(tfInputStock.getText()));
				this.dispose();
			} else {
				jop.showMessageDialog(null, "값을 입력해주셔야 합니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (obj.equals(btnInputCancel)) {
			this.dispose();
		}
	}
}
