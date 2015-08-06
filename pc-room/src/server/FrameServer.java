package server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import util.SetFrameDisplay;

public class FrameServer extends JFrame {

	public static int PC_TOTAL = 25; // 컴퓨터의 총 갯수
	public static int PC_AVAILABLE = 0; // 현재 사용가능한 PC갯수

	// Pc지정시 팝업매뉴 액션 리스너에게 번호를 전달할 놈
	int currentPcNumber = -1;

	String pcMessage; // pc에 표시하기위한 message
	Dimension d1;// 모니터의 크기를 구하기 위한 인스턴스

	JPanel plBackground; // 프레임 위에 바로 깔 판넬
	JScrollPane mainScrollPane; // 메인 스크린을 넣을 스크롤 판
	JPanel mainScreen; // 매뉴바 아래 스크린, 스크롤 판에 들어간다.
	JMenuBar bar; // 매뉴바
	JMenu menuBasic; // 기본설정매뉴
	JMenu menuMember; // 회원관리매뉴
	JMenu menuFood; // 음식관리매뉴
	JMenuItem itemBasicMoney; // 기본설정 아이템 요금제관리
	JMenuItem itemBasicExit; // 기본설정 아이템 종료

	// mainscreen gridLayout에 들어갈 라인들
	JPanel[] pLines;

	// 컴퓨터 아이콘 이미지와 배열
	ImageIcon[] imgLaptopR;
	ImageIcon[] imgLaptopS;

	ImageIcon imgBlank; // 복도를 표현할 그냥 하얀 빈 이미지

	// 컴퓨터 라벨과 배열
	JLabel[] lbBlank;

	PLabel[] lbLaptop;

	// 우클릭시 팝업 이미지
	JPopupMenu jpm;
	JMenuItem popItemName;
	JMenuItem popItemStart;
	JMenuItem popItemPay;
	JMenuItem popItemCharge;
	JMenuItem popItemChat;

	public FrameServer() {
		setTitle("관리자 프로그램");

		// width는 화면 크기로, height는 작업표시줄을 고려해 50을 잘랐음
		setBounds(0, 0, SetFrameDisplay.DISPLAY_WIDTH, SetFrameDisplay.DISPLAY_HEIGHT - 50);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// x눌러도 종료시키지 말자.
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		// 핸들러들 생성
		MouseHandler mouseHandler = new MouseHandler();
		ActionHandler actionHandler = new ActionHandler();

		// 초기화
		pLines = new JPanel[5];
		for (int i = 0; i < pLines.length; i++) {
			pLines[i] = new JPanel();
		}

		plBackground = new JPanel();
		mainScreen = new JPanel();
		mainScrollPane = new JScrollPane(mainScreen, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 상단매뉴바
		bar = new JMenuBar();
		menuBasic = new JMenu("기본설정");
		menuMember = new JMenu("회원관리");
		menuFood = new JMenu("음식관리");
		itemBasicMoney = new JMenuItem("요금제관리");
		itemBasicExit = new JMenuItem("프로그램 종료");

		menuMember.addActionListener(actionHandler);
		itemBasicExit.addActionListener(actionHandler);

		// add부분
		// plBackground add
		add(plBackground);
		plBackground.setLayout(new BorderLayout());
		plBackground.add(bar, "North");
		plBackground.add(mainScrollPane, "Center");

		// 매뉴바 add
		bar.add(menuBasic);
		bar.add(menuMember);
		bar.add(menuFood);
		menuBasic.add(itemBasicMoney);
		menuBasic.add(itemBasicExit);

		bar.addMouseListener(mouseHandler);
		menuBasic.addMouseListener(mouseHandler);
		menuFood.addMouseListener(mouseHandler);
		menuMember.addMouseListener(mouseHandler);

		// 이 밑은 모두 메인스크린 관련
		mainScreen.setLayout(new GridLayout(5, 1));

		// 메인스크린 그리드 레이아웃 행 한줄당 들어갈 패널들
		for (JPanel j : pLines) {
			j.setBackground(Color.WHITE);
			j.setLayout(new FlowLayout(FlowLayout.LEFT));
			j.addMouseListener(mouseHandler);
			mainScreen.add(j);
		}

		// PC라벨과 아이콘놈들 초기화하고 배열에 넣기
		imgBlank = new ImageIcon("src/images/laptops/blank.jpg");

		imgLaptopR = new ImageIcon[PC_TOTAL];
		for (int i = 0; i < imgLaptopR.length; i++) {
			if (i < 9)
				imgLaptopR[i] = new ImageIcon("src/images/laptops/laptop0" + (i + 1) + "r.jpg");
			else
				imgLaptopR[i] = new ImageIcon("src/images/laptops/laptop" + (i + 1) + "r.jpg");
		}

		imgLaptopS = new ImageIcon[PC_TOTAL];
		for (int i = 0; i < imgLaptopS.length; i++) {
			if (i < 9)
				imgLaptopS[i] = new ImageIcon("src/images/laptops/laptop0" + (i + 1) + "s.jpg");
			else
				imgLaptopS[i] = new ImageIcon("src/images/laptops/laptop" + (i + 1) + "s.jpg");
		}

		lbBlank = new JLabel[5];
		for (int i = 0; i < lbBlank.length; i++) {
			lbBlank[i] = new JLabel();
		}

		lbLaptop = new PLabel[PC_TOTAL];
		for (int i = 0; i < lbLaptop.length; i++) {
			lbLaptop[i] = new PLabel();
		}

		// 팝업매뉴
		jpm = new JPopupMenu();
		popItemStart = new JMenuItem("시작하기");
		popItemPay = new JMenuItem("종료하기");
		popItemCharge = new JMenuItem("충전하기");
		popItemChat = new JMenuItem("채팅하기");
		popItemName = new JMenuItem("야임마");
		jpm.add(popItemName);
		jpm.addSeparator();
		jpm.add(popItemStart);
		jpm.add(popItemPay);
		jpm.add(popItemCharge);
		jpm.add(popItemChat);

		popItemPay.addActionListener(actionHandler);
		popItemStart.addActionListener(actionHandler);

		// 라벨 배열을 이용하여 이미지 아이콘 삽입 + 배경색지정 + 마우스 리스너 추가
		int num = 0;
		for (int i = 0; i < pLines.length; i++) {
			for (int j = 0; j < 5; j++) {
				if (j == 3) {
					lbBlank[i].setIcon(imgBlank);
					pLines[i].add(lbBlank[i]);
				}
				lbLaptop[num].setIcon(imgLaptopS[num]);
				lbLaptop[num].setMessage("");
				lbLaptop[num].setBackground(Color.WHITE);
				lbLaptop[num].addMouseListener(mouseHandler);
				pLines[i].add(lbLaptop[num]);
				num++;
			}
		}

		setVisible(true);
	}

	class MouseHandler implements MouseListener {
		@Override
		public void mouseClicked(MouseEvent e) {
			Object obj = e.getSource();

			// 매뉴바의 회원관리 .. 액션리스너가 안먹혀서 마우스 리스너로
			if (obj.equals(menuMember)) {
				new FrameMember();
			}

			if (obj.equals(menuFood)) {
				new FrameFood();
			}

			if (e.isMetaDown()) // 마우스를 우클릭하면
			{

				for (int i = 0; i < PC_TOTAL; i++) // 컴퓨터 라벨 갯수만큼 돌릴 예정
				{
					if (obj.equals(lbLaptop[i])) // 만약 일치하는 라벨이 있을 경우
					{
						popItemName.setText((i + 1) + "번 PC"); // 팝업매뉴에 pc이름 표기
						// 스크린을 기준으로 마우스 위치를 가져와 팝업매뉴를 위치시킴
						jpm.setLocation(e.getXOnScreen(), e.getYOnScreen());
						jpm.setVisible(true); // 팝업매뉴 나와랑
						currentPcNumber = i; // 현재 PC번호를 저장
						System.out.println("laptop : " + currentPcNumber);
						break;// 더 돌릴 필요 없으니 break;
					}
				}
			} else {
				jpm.setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	}

	class ActionHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();

			// 팝업매뉴
			// 팝업에 PC시작을 누르면
			if (obj.equals(popItemStart)) {
				// 선택한 pc번호로 라벨의 아이콘을 가져와 아이콘 배열과 비교, 켜져있는지 꺼져있는지 판단
				// (ex) 1번 피씨 lbLaptop[0] 1번 실행중인 아이콘 배열 imgLaptopR[0] / 1번 꺼져있는
				// 아이콘 배열 imgLaptopS[0]
				if (imgLaptopR[currentPcNumber].equals(lbLaptop[currentPcNumber].getIcon())) {
					System.out.println(currentPcNumber + "번 PC가 이미 실행 중입니다.");
					String message = (currentPcNumber + 1) + "번 PC가 이미 실행 중입니다.";
					// 알림 다이얼로그 출력
					jpm.setVisible(false); // 팝업매뉴 꺼져라
					JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
					currentPcNumber = -1;
				} else if (imgLaptopS[currentPcNumber].equals(lbLaptop[currentPcNumber].getIcon())) {
					System.out.println(currentPcNumber + 1 + "번 PC시작");
					jpm.setVisible(false); // 팝업매뉴 꺼졍
					// 시작 아이콘으로 변경
					lbLaptop[currentPcNumber].setIcon(imgLaptopR[currentPcNumber]);
					pcMessage = (currentPcNumber + 1) + "가 실행중";
					lbLaptop[currentPcNumber].setMessage(pcMessage);
					currentPcNumber = -1;
				}
			}

			// 팝업정산버튼
			if (obj.equals(popItemPay)) {
				if (imgLaptopR[currentPcNumber].equals(lbLaptop[currentPcNumber].getIcon())) {
					jpm.setVisible(false);
					String dialogMessage = (currentPcNumber + 1) + "번 PC를 정산하시겠습니까?";
					int result = JOptionPane.showConfirmDialog(null, dialogMessage, "알림", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						System.out.println(currentPcNumber + 1 + "번 PC종료");
						lbLaptop[currentPcNumber].setIcon(imgLaptopS[currentPcNumber]);
						pcMessage = "";
						lbLaptop[currentPcNumber].setMessage(pcMessage);
						currentPcNumber = -1;
					}
					currentPcNumber = -1;
				} else {
					jpm.setVisible(false);
					System.out.println(currentPcNumber + "번 PC는 실행 중이 아닙니다.");
					String message = (currentPcNumber + 1) + "번 PC는 실행 중이 아닙니다.";
					JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
					currentPcNumber = -1;
				}
			}

			// 팝업매뉴 끝

			// 기본매뉴
			// 기본매뉴 회원관리버튼

			// 기본 매뉴 종료 버튼
			if (obj.equals(itemBasicExit)) {
				int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		}
	}
}
