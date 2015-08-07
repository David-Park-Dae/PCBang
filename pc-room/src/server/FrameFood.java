package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;

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

import util.SetFrameDisplay;

public class FrameFood extends JFrame implements ActionListener {
	String[] selectedCell = new String[3];
	String selectedNo;
	JOptionPane jop;
	FoodHelper food;

	JPanel plBackground;

	// Food
	JTable tableFood;
	DefaultTableModel modelFood;
	String[] colFoodName = { "음식번호", "음식이름", "재고" };
	String[][] rowFoodData;

	JPanel plFoodBackground;
	JScrollPane plFoodScrollPane;

	JPanel plFoodBtmscreen;
	JPanel plFoodBtmInner1;
	JPanel plFoodBtmInner2;

	JButton btnFoodSearch;
	JButton btnFoodSell;
	JButton btnFoodAdd;
	JButton btnFoodEdit;
	JButton btnFoodDelete;
	JButton btnFoodInput;
	JButton btnFoodGoLog;

	JLabel lbFoodName;
	JLabel lbFoodBlank;
	JLabel lbFoodHowManySell;
	JTextField tfFoodName;
	JTextField tfFoodHowManySell;

	public FrameFood() {
		setTitle("음식관리");
		setSize(900, 500);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		jop = new JOptionPane();
		// Food
		plFoodBackground = new JPanel();
		add(plFoodBackground);
		plFoodBackground.setLayout(new BorderLayout());

		modelFood = new DefaultTableModel(rowFoodData, colFoodName) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		tableFood = new JTable(modelFood);
		tableFood.getTableHeader().setReorderingAllowed(false);
		tableFood.getTableHeader().setResizingAllowed(false);
		tableFood.setAutoCreateRowSorter(true);
		TableRowSorter foodSorter = new TableRowSorter(tableFood.getModel());
		tableFood.setRowSorter(foodSorter);

		food = new FoodHelper(modelFood);
		plFoodScrollPane = new JScrollPane(tableFood, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		plFoodBtmscreen = new JPanel();
		plFoodBtmscreen.setLayout(new GridLayout(2, 1));
		plFoodBtmInner1 = new JPanel();
		plFoodBtmInner2 = new JPanel();

		lbFoodName = new JLabel("음식이름 : ");
		lbFoodBlank = new JLabel("       ");
		lbFoodHowManySell = new JLabel(" 개");
		tfFoodName = new JTextField(7);
		tfFoodHowManySell = new JTextField(2);
		btnFoodSearch = new JButton("검색");
		btnFoodSell = new JButton("판매");

		btnFoodAdd = new JButton("추가");
		btnFoodEdit = new JButton("수정");
		btnFoodDelete = new JButton("삭제");
		btnFoodInput = new JButton("입고");
		btnFoodGoLog = new JButton("로그보기");
		btnFoodGoLog.setBackground(Color.pink);

		btnFoodSearch.addActionListener(this);
		btnFoodSell.addActionListener(this);

		btnFoodAdd.addActionListener(this);
		btnFoodEdit.addActionListener(this);
		btnFoodDelete.addActionListener(this);
		btnFoodInput.addActionListener(this);
		btnFoodGoLog.addActionListener(this);

		tfFoodHowManySell.addKeyListener(new KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}
			}
		});

		plFoodBtmInner1.add(lbFoodName);
		plFoodBtmInner1.add(tfFoodName);
		plFoodBtmInner1.add(btnFoodSearch);
		plFoodBtmInner1.add(lbFoodBlank);
		plFoodBtmInner1.add(tfFoodHowManySell);
		plFoodBtmInner1.add(lbFoodHowManySell);
		plFoodBtmInner1.add(btnFoodSell);

		plFoodBtmInner2.add(btnFoodAdd);
		plFoodBtmInner2.add(btnFoodEdit);
		plFoodBtmInner2.add(btnFoodDelete);
		plFoodBtmInner2.add(btnFoodInput);
		plFoodBtmInner2.add(btnFoodGoLog);

		plFoodBackground.add(plFoodScrollPane, "Center");
		plFoodBackground.add(plFoodBtmscreen, "South");
		plFoodBtmscreen.add(plFoodBtmInner1);
		plFoodBtmscreen.add(plFoodBtmInner2);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if (obj.equals(btnFoodGoLog)) {
			FrameFoodLog fl = new FrameFoodLog();
		}
		if (obj.equals(btnFoodSearch)) {
			String keyword = tfFoodName.getText();
			food.search(keyword);
		}
		if (obj.equals(btnFoodSell)) {
			selectedCell = getSelectedCell();
			System.out.println("번호 : " + selectedNo);
			if (selectedCell[0] != "-1" && !tfFoodHowManySell.getText().equals("")) {
				int sellQuantity = Integer.parseInt(tfFoodHowManySell.getText());
				if (sellQuantity < Integer.parseInt(selectedCell[2])) {
					int result = jop.showConfirmDialog(null, "판매하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.OK_OPTION)
						food.sell(Integer.parseInt(selectedCell[0]), sellQuantity);
				} else {
					jop.showMessageDialog(null, "재고가 부족합니다", "알림", JOptionPane.INFORMATION_MESSAGE);
				}
			} else {
				jop.showMessageDialog(null, "셀을 선택하고 값을 입력하세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (obj.equals(btnFoodDelete)) {
			int selectedNo = getSelectedNo();
			System.out.println("번호 : " + selectedNo);
			if (selectedNo != -1) {
				int result = jop.showConfirmDialog(null, "삭제하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.OK_OPTION) {
					food.delete(selectedNo);
				}
			} else {
				jop.showMessageDialog(null, "셀을 선택하세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (obj.equals(btnFoodAdd)) {
			new FrameFoodAdd(food);
		}

		if (obj.equals(btnFoodEdit)) {

			selectedCell = getSelectedCell();
			if (!selectedCell[0].equals("-1")) {
				FrameFoodEdit fme = new FrameFoodEdit(selectedCell[1], Integer.parseInt(selectedCell[2]), food);
			} else {
				jop.showMessageDialog(null, "셀을 선택하여 주세요", "알림", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if (obj.equals(btnFoodInput)) {
			new FrameFoodInput(food);
		}
	}

	private int getSelectedNo() {
		int row = tableFood.getSelectedRow();
		System.out.println("row :" + row);
		int col = 0;
		try {
			return Integer.parseInt(tableFood.getValueAt(row, col).toString());
		} catch (Exception e) {
			return -1;
		}
	}

	private String[] getSelectedCell() {
		String[] cell = new String[3];
		int row = tableFood.getSelectedRow();
		int col = 0;
		try {
			for (int i = 0; i < 3; i++) {
				cell[i] = (String) tableFood.getValueAt(row, i);
			}
			return cell;
		} catch (Exception e) {
			for (int i = 0; i < 3; i++) {
				cell[i] = "-1";
			}
			return cell;
		}
	}
}
