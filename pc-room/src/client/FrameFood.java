package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import util.FoodHelper;

public class FrameFood extends JFrame {
	private static final long serialVersionUID = 1L;

	/**** 범용 클래스 선언 ****/
	private FoodHelper foodHelper;
	private Member loginUser;
	
	/**** 상단 패널 선언 ****/
	private JPanel northPanel;
	private DefaultTableModel defaultTableModel;
	private JTable showTable;
	private JScrollPane jScollPane;
	private JButton btnAdd;
	
	/**** 하단 패널 선언 ****/
	private JPanel southPanel;
	private DefaultTableModel southTableModel;
	private JTable southTable;
	private JScrollPane southJScollPane;
	private JButton btnDelete;
	private JButton btnOrder;

	public FrameFood() {
		super("showTable 예제");
		setSize(800, 600);
		setLayout(null);
		
		// 상단 패널 초기화
		initNorthPanel();
		
		// 하단 패널 초기화
		initSouthPanel();

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	public FrameFood(Member loginUser) {
		this();
		this.loginUser = loginUser;
	}
	/*
	 * 
	 *  상단 패널 초기화
	 * 
	 */
	private void initNorthPanel() {
		northPanel = new JPanel();
		northPanel.setBounds(0, 0, 800, 300);
		northPanel.setLayout(null);
		
		defaultTableModel = new DefaultTableModel(new String[] {"상품번호","상품이름","상품가격"}, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// 테이블 선언
		showTable = new JTable(defaultTableModel);
		foodHelper = new FoodHelper( defaultTableModel );
		
		// DB에서 음식 정보를 가져온다.
		foodHelper.search();
		
		// 컬럼의 순서 변경 막기 , 테이블의 크기 조절 막기
		showTable.getTableHeader().setReorderingAllowed(false);
		showTable.getTableHeader().setResizingAllowed(false);
		
		//테이블에 Row를 미리 선택한 상태로 만들기!
		showTable.setRowSelectionInterval(0, 0);
		
		// 테이블을 먼저 생성후 스크롤 패널에 부착
		jScollPane= new JScrollPane(showTable);
		jScollPane.setSize(700,290);
		jScollPane.setLocation(0, 0);
		
		btnAdd = new JButton("추가");
		btnAdd.setBounds(jScollPane.getWidth()+10,jScollPane.getHeight()/2-30,80,60);
		btnAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selRow = showTable.getSelectedRow();
				String name = (String) defaultTableModel.getValueAt(selRow, 1);
				int price = (int) defaultTableModel.getValueAt(selRow, 2);
				
				Object[] addRow = { name, price };
				southTableModel.addRow(addRow);
			}
		});
		
		northPanel.add(jScollPane);
		northPanel.add(btnAdd);
		add(northPanel);
		
		// 테이블이 정상적으로 초기화 된지 출력
		afterCreateLog();
	}
	
	/*
	 * 
	 *  하단 패널 초기화
	 * 
	 */
	private void initSouthPanel() {
		southPanel = new JPanel();
		southPanel.setBounds(0, 300, 500, 300);
		southPanel.setLayout(null);
		
		southTableModel = new DefaultTableModel(new String[] {"상품이름","상품가격"}, 0) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		// 테이블 선언
		southTable = new JTable(southTableModel);
		
		// 컬럼의 순서 변경 막기 , 테이블의 크기 조절 막기
		southTable.getTableHeader().setReorderingAllowed(false);
		southTable.getTableHeader().setResizingAllowed(false);
		
		// 테이블을 먼저 생성후 스크롤 패널에 부착
		southJScollPane= new JScrollPane(southTable);
		southJScollPane.setSize(400,260);
		southJScollPane.setLocation(0, 0);
		
		btnDelete = new JButton("삭제");
		btnDelete.setBounds(southJScollPane.getWidth()+10,southJScollPane.getHeight()/2-30,80,60);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int selRow = southTable.getSelectedRow();
					southTableModel.removeRow(selRow);
				} catch(ArrayIndexOutOfBoundsException e1) {
					JOptionPane.showMessageDialog(null, "주문 취소할 음식이 없습니다.");
					System.out.println("주문 취소할 음식이 없습니다.");
				}
			}
		});
	
		btnOrder = new JButton("주문하기");
		btnOrder.setBounds(southPanel.getWidth(),southPanel.getY(),290,260);
		btnOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = southTable.getRowCount();
				int col = southTable.getColumnCount();
				Object[][] foodInfo = new Object[row][col];
				for(int i=0; i<row; i++) {
					for(int j=0; j<col; j++) {
						foodInfo[i][j] = southTableModel.getValueAt(i, j);
					}
				}
	
				FoodObject fo = new FoodObject(loginUser.getSeatNumber());
				fo.setFoodInfo(foodInfo);
				
				// 넘겨주는 함수
				
				JOptionPane.showMessageDialog(null,"주문이 완료되었습니다.");
				dispose();
			}
		});
		
		southPanel.add(southJScollPane);
		southPanel.add(btnDelete);
		add(southPanel);
		add(btnOrder);
		
	}
	
	private void afterCreateLog() {
		System.out.println("******** 테이블 확인 로그 ********");
		//행과 열 갯수 구하기
		System.out.println("총 상품 갯수 : " + defaultTableModel.getRowCount());

		// 0행의 데이터 가져오기
		System.out.print("불러온 데이터의 0행 : ");
		for(int i=0; i<defaultTableModel.getColumnCount(); i++ ) {
			System.out.print(defaultTableModel.getValueAt(0,i)+" ");
		} System.out.println();

		System.out.println("선택된 행 : " + showTable.getSelectedRow());
		String id = String.valueOf(defaultTableModel.getValueAt(showTable.getSelectedRow(), 0));
		System.out.println("선택된 행의 No : " + id);
	}
	
	public static void main(String[] args) {
		new FrameFood();
	}
}