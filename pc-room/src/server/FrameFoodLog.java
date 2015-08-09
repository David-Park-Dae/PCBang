package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import util.FoodLogWriter;
import util.SetFrameDisplay;

public class FrameFoodLog extends JFrame implements ActionListener {

	// Log
	JTable tableLog;
	DefaultTableModel modelLog;
	String[] colLogName = { "시간", "음식이름", "유형", "내용"};
	String[][] rowLogData;

	JPanel plLogBackground;
	JScrollPane plLogScrollPane;

	JPanel plLogBtmscreen;
	JPanel plLogBtmInner1;
	JPanel plLogBtmInner2;

	JButton btnLogSearch;
	JButton btnLogAdd;
	JButton btnLogAll;
	JButton btnLogSell;
	JButton btnLogInput;
	JButton btnLogGoFood;

	JLabel lbLogFoodName;
	JTextField tfLogFoodName;

	public FrameFoodLog() {
		setTitle("로그보기");
		setSize(900, 500);
		SetFrameDisplay.setFrameCenter(this);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// Log
		plLogBackground = new JPanel();
		add(plLogBackground);
		plLogBackground.setLayout(new BorderLayout());

		modelLog = new DefaultTableModel(rowLogData, colLogName) {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		tableLog = new JTable(modelLog);
		tableLog.getTableHeader().setReorderingAllowed(false);
//		tableLog.getTableHeader().setResizingAllowed(false);
		tableLog.setAutoCreateRowSorter(true);
		TableRowSorter logSorter = new TableRowSorter(tableLog.getModel());
		tableLog.setRowSorter(logSorter);

		plLogScrollPane = new JScrollPane(tableLog, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		plLogBtmscreen = new JPanel();
		plLogBtmscreen.setLayout(new GridLayout(2, 1));
		plLogBtmInner1 = new JPanel();
		plLogBtmInner2 = new JPanel();

		btnLogSearch = new JButton("검색");
		btnLogAll = new JButton("전체검색");
		btnLogSell = new JButton("판매로그검색");
		btnLogAdd = new JButton("추가로그검색");
		btnLogInput = new JButton("입고로그검색");
		btnLogGoFood = new JButton("음식매뉴보기");
		btnLogGoFood.setBackground(Color.orange);
		
		btnLogSearch.addActionListener(this);
		btnLogAdd.addActionListener(this);
		btnLogAll.addActionListener(this);
		btnLogSell.addActionListener(this);
		btnLogInput.addActionListener(this);
		btnLogGoFood.addActionListener(this);

		lbLogFoodName = new JLabel("음식이름 : ");
		tfLogFoodName = new JTextField(7);

		plLogBtmInner1.add(lbLogFoodName);
		plLogBtmInner1.add(tfLogFoodName);
		plLogBtmInner1.add(btnLogSearch);

		plLogBtmInner2.add(btnLogAll);
		plLogBtmInner2.add(btnLogAdd);
		plLogBtmInner2.add(btnLogSell);
		plLogBtmInner2.add(btnLogInput);
		plLogBtmInner2.add(btnLogGoFood);

		plLogBackground.add(plLogScrollPane, "Center");
		plLogBackground.add(plLogBtmscreen, "South");
		plLogBtmscreen.add(plLogBtmInner1);
		plLogBtmscreen.add(plLogBtmInner2);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = new Object();
		obj = e.getSource();
		if(obj.equals(btnLogAll)){
			FoodLogWriter.readLog(modelLog);
		}
		
		if(obj.equals(btnLogSearch)){
			String keyword = tfLogFoodName.getText();
			FoodLogWriter.readLogByFood(modelLog, keyword);
		}
		
		if(obj.equals(btnLogAdd)){
			FoodLogWriter.readLogByType(modelLog, "추가");
		}
		
		if(obj.equals(btnLogInput)){
			FoodLogWriter.readLogByType(modelLog, "입고");
		}
		
		if(obj.equals(btnLogSell)){
			FoodLogWriter.readLogByType(modelLog, "판매");
		}
		
		if(obj.equals(btnLogGoFood)){
			this.dispose();
		}
	}
	
	public static void main(String[] args) {
		new FrameFoodLog();
	}
}
