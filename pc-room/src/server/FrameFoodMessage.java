package server;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import client.FoodObject;
import util.FoodHelper;
import util.SetFrameDisplay;

public class FrameFoodMessage extends JFrame {
	JTextArea taMain;

	JPanel plSouth;
	JButton btnConfirm;
	JButton btnCancel;

	ArrayList<Object> updateList;

	public FrameFoodMessage(FoodObject orderObject) {
		updateList = new ArrayList<Object>();

		Object[][] orderList = orderObject.getFoodInfo();
		setTitle(orderObject.getSeatNumber() + "님의 주문");
		setSize(300, 180);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		plSouth = new JPanel();

		taMain = new JTextArea();

		for (int i = 0; i < orderList.length; i++) {
			taMain.append("주문음식 " + (i + 1) + " : ");
			for (int j = 0; j < orderList[i].length; j++) {
				if (j == 0) {
					updateList.add(orderList[i][j]);
				}
				taMain.append(orderList[i][j] + " \n");
			}
		}

		btnConfirm = new JButton("확인");
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub[
				for (int i = 0; i < updateList.size(); i++) {
					System.out.println(updateList.get(i).toString());
					FoodHelper.sell(updateList.get(i).toString());
				}
				JOptionPane.showMessageDialog(null, "판매가 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			}
		});
		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "취소하시겠습니까?", "알림", JOptionPane.OK_CANCEL_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});

		plSouth.add(btnConfirm);
		plSouth.add(btnCancel);

		add(taMain, "Center");
		add(plSouth, "South");
		setVisible(true);
	}
}
