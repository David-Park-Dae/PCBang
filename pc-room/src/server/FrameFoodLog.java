package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import util.SetFrameDisplay;

public class FrameFoodLog extends JFrame {
	
		// Log
		JTable tableLog;
		DefaultTableModel modelLog;
		String[] colLogName = { "판매번호", "음식이름", "유형", "수량", "시간", "재고" };
		String[][] rowLogData;

		JPanel plLogBackground;
		JScrollPane plLogScrollPane;

		JPanel plLogBtmscreen;
		JPanel plLogBtmInner1;
		JPanel plLogBtmInner2;

		JButton btnLogSearch;
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
			tableLog.getTableHeader().setResizingAllowed(false);
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
			btnLogInput = new JButton("입고로그검색");
			btnLogGoFood = new JButton("음식매뉴보기");
			btnLogGoFood.setBackground(Color.orange);

			lbLogFoodName = new JLabel("음식이름 : ");
			tfLogFoodName = new JTextField(7);

			plLogBtmInner1.add(lbLogFoodName);
			plLogBtmInner1.add(tfLogFoodName);
			plLogBtmInner1.add(btnLogSearch);

			plLogBtmInner2.add(btnLogAll);
			plLogBtmInner2.add(btnLogSell);
			plLogBtmInner2.add(btnLogInput);
			plLogBtmInner2.add(btnLogGoFood);

			plLogBackground.add(plLogScrollPane, "Center");
			plLogBackground.add(plLogBtmscreen, "South");
			plLogBtmscreen.add(plLogBtmInner1);
			plLogBtmscreen.add(plLogBtmInner2);

			setVisible(true);
		}
		
		public static void main(String[] args) {
			new FrameFoodLog();
		}
	}
