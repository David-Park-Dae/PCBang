package server;

import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import util.MoneyConverter;
import util.SetFrameDisplay;

public class FrameMember extends JFrame implements ActionListener {
	String id;
	String[] selectedCell = new String[3];
	String selectedId;

	private Connection conn;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql;

	JTable table;
	DefaultTableModel model;
	String[] colName = { "이름", "아이디", "남은시간" };
	String[][] rowData;
	MemberHelper member;

	JPanel plBackground;
	// JPanel plMainScreen;
	JScrollPane plMainScrollPane;
	JPanel plBtmscreen;
	JPanel plBtmInner1;
	JPanel plBtmInner2;

	JButton btnSearch;
	JButton btnAdd;
	JButton btnDelete;
	JButton btnEdit;
	JButton btnCharge;

	JLabel lbName;
	JTextField tfName;

	JOptionPane jop;

	public FrameMember() {
		setTitle("회원관리");
		setSize(800, 400);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		model = new DefaultTableModel(rowData, colName) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};

		table = new JTable(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setAutoCreateRowSorter(true);
		TableRowSorter foodSorter = new TableRowSorter(table.getModel());
		table.setRowSorter(foodSorter);
		member = new MemberHelper(model);

		plBackground = new JPanel();
		plBackground.setLayout(new BorderLayout());
		// plMainScreen = new JPanel();
		plMainScrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		plBtmscreen = new JPanel();
		plBtmInner1 = new JPanel();
		plBtmInner2 = new JPanel();
		plBackground.add(plMainScrollPane, "Center");

		plBtmscreen.setLayout(new GridLayout(2, 1));
		plBtmscreen.add(plBtmInner1);
		plBtmscreen.add(plBtmInner2);
		plBackground.add(plBtmscreen, "South");

		btnSearch = new JButton("조회");
		btnAdd = new JButton("추가");
		btnDelete = new JButton("삭제");
		btnEdit = new JButton("수정");
		btnCharge = new JButton("충전");

		btnSearch.addActionListener(this);
		btnDelete.addActionListener(this);
		btnEdit.addActionListener(this);
		btnCharge.addActionListener(this);

		lbName = new JLabel("이름");
		tfName = new JTextField(10);

		plBtmInner1.add(lbName);
		plBtmInner1.add(tfName);
		plBtmInner1.add(btnSearch);

		// plBtmInner2.add(btnAdd);
		plBtmInner2.add(btnCharge);
		plBtmInner2.add(btnEdit);
		plBtmInner2.add(btnDelete);

		add(plBackground);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = new Object();
		obj = e.getSource();
		String keyword = tfName.getText();
		if (obj.equals(btnSearch)) {

			member.search(keyword);
		}

		if (obj.equals(btnEdit)) {

			selectedCell = getSelectedCell();
			if (!selectedCell[1].equals("-1")) {
				FrameMemberEdit fme = new FrameMemberEdit(selectedCell[0], selectedCell[1], member);
			} else {
				jop.showMessageDialog(null, "셀을 선택하여 주세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (obj.equals(btnDelete)) {
			selectedId = getSelectedId();
			if (!selectedId.equals("-1")) {
				int result = jop.showConfirmDialog(null, "삭제하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION)
					member.delete(getSelectedId());
			} else {
				jop.showMessageDialog(null, "셀을 선택하여 주세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (obj.equals(btnCharge)) {
			selectedId = getSelectedId();
			if (!selectedId.equals("-1")) {
				new FrameCharge();
			} else {
				jop.showMessageDialog(null, "셀을 선택하여 주세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	private String getSelectedId() {
		int row = table.getSelectedRow();
		int col = 1;
		try {
			return (String) table.getValueAt(row, col);
		} catch (Exception e) {
			return "-1";
		}
	}

	private String[] getSelectedCell() {
		String[] cell = new String[3];
		int row = table.getSelectedRow();
		int col = 0;
		try {
			for (int i = 0; i < 3; i++) {
				cell[i] = (String) table.getValueAt(row, i);
			}
			return cell;
		} catch (Exception e) {
			for (int i = 0; i < 3; i++) {
				cell[i] = "-1";
			}
			return cell;
		}
	}

	class FrameCharge extends JFrame implements ActionListener {
		JPanel plChargeLine1;
		JPanel plChargeLine2;

		JLabel lbCharge;
		JLabel lbWon;
		JTextField tfCharge;
		JButton btnCharge;
		JButton btnCancel;

		public FrameCharge() {
			setTitle(selectedId + " 충전금액입력");
			setSize(300, 120);
			SetFrameDisplay.setFrameCenter(this);
			setDefaultCloseOperation(DISPOSE_ON_CLOSE);

			setLayout(new GridLayout(2, 1));

			plChargeLine1 = new JPanel();
			plChargeLine2 = new JPanel();

			lbCharge = new JLabel("충전할 금액 : ");
			lbWon = new JLabel("원");
			tfCharge = new JTextField(6);
			btnCharge = new JButton("충전");
			btnCancel = new JButton("취소");

			tfCharge.addKeyListener(new KeyAdapter() {
				public void keyTyped(java.awt.event.KeyEvent e) {
					char c = e.getKeyChar();
					if (!Character.isDigit(c)) {
						e.consume();
						return;
					}
				}
			});
			btnCharge.addActionListener(this);
			btnCancel.addActionListener(this);

			plChargeLine1.add(lbCharge);
			plChargeLine1.add(tfCharge);
			plChargeLine1.add(lbWon);
			plChargeLine2.add(btnCharge);
			plChargeLine2.add(btnCancel);

			add(plChargeLine1);
			add(plChargeLine2);

			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object obj = new Object();
			obj = e.getSource();

			// 충전하기 버튼을 누르면
			if (obj.equals(btnCharge)) {

				System.out.println("이게뭐길래:" + tfCharge.getText());
				if (!tfCharge.getText().equals("")) {
					int intChargeMoney = Integer.parseInt(tfCharge.getText());
					if (intChargeMoney > 0) {
						int result = JOptionPane.showConfirmDialog(null, "충전하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
						if (result == JOptionPane.YES_OPTION) {
							System.out.println("FrameMember>>" + selectedId + ": PC충전");
							member.charge(intChargeMoney, selectedId);
							JOptionPane.showMessageDialog(null, "충전이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
							this.dispose();
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "금액을 입력하여 주새요", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			}

			if (obj.equals(btnCancel)) {
				this.dispose();
			}
		}
	}
}
