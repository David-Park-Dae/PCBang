package Home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

public class FrameServer extends JFrame
{
	Dimension d1;// 모니터의 크기를 구하기 위한 인스턴스
	JOptionPane jop; // 알림표시를 위한 jop

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
	JPanel[] lines;
	JPanel plLine1;
	JPanel plLine2;
	JPanel plLine3;
	JPanel plLine4;
	JPanel plLine5;

	// 컴퓨터 아이콘 이미지와 배열
	ImageIcon[] imgLaptopR;
	ImageIcon imgLaptop01r;
	ImageIcon imgLaptop02r;
	ImageIcon imgLaptop03r;
	ImageIcon imgLaptop04r;
	ImageIcon imgLaptop05r;
	ImageIcon imgLaptop06r;
	ImageIcon imgLaptop07r;
	ImageIcon imgLaptop08r;
	ImageIcon imgLaptop09r;
	ImageIcon imgLaptop10r;
	ImageIcon imgLaptop11r;
	ImageIcon imgLaptop12r;
	ImageIcon imgLaptop13r;
	ImageIcon imgLaptop14r;
	ImageIcon imgLaptop15r;
	ImageIcon imgLaptop16r;
	ImageIcon imgLaptop17r;
	ImageIcon imgLaptop18r;
	ImageIcon imgLaptop19r;
	ImageIcon imgLaptop20r;
	ImageIcon imgLaptop21r;
	ImageIcon imgLaptop22r;
	ImageIcon imgLaptop23r;
	ImageIcon imgLaptop24r;
	ImageIcon imgLaptop25r;

	ImageIcon[] imgLaptopS;
	ImageIcon imgLaptop01s;
	ImageIcon imgLaptop02s;
	ImageIcon imgLaptop03s;
	ImageIcon imgLaptop04s;
	ImageIcon imgLaptop05s;
	ImageIcon imgLaptop06s;
	ImageIcon imgLaptop07s;
	ImageIcon imgLaptop08s;
	ImageIcon imgLaptop09s;
	ImageIcon imgLaptop10s;
	ImageIcon imgLaptop11s;
	ImageIcon imgLaptop12s;
	ImageIcon imgLaptop13s;
	ImageIcon imgLaptop14s;
	ImageIcon imgLaptop15s;
	ImageIcon imgLaptop16s;
	ImageIcon imgLaptop17s;
	ImageIcon imgLaptop18s;
	ImageIcon imgLaptop19s;
	ImageIcon imgLaptop20s;
	ImageIcon imgLaptop21s;
	ImageIcon imgLaptop22s;
	ImageIcon imgLaptop23s;
	ImageIcon imgLaptop24s;
	ImageIcon imgLaptop25s;

	ImageIcon imgBlank; //복도를 표현할 그냥 하얀 빈 이미지
	
	// 컴퓨터 라벨과 배열
	JLabel lbBlank1;
	JLabel lbBlank2;
	JLabel lbBlank3;
	JLabel lbBlank4;
	JLabel lbBlank5;
	
	JLabel[] lbLaptop;
	JLabel lbLaptop01;
	JLabel lbLaptop02;
	JLabel lbLaptop03;
	JLabel lbLaptop04;
	JLabel lbLaptop05;
	JLabel lbLaptop06;
	JLabel lbLaptop07;
	JLabel lbLaptop08;
	JLabel lbLaptop09;
	JLabel lbLaptop10;
	JLabel lbLaptop11;
	JLabel lbLaptop12;
	JLabel lbLaptop13;
	JLabel lbLaptop14;
	JLabel lbLaptop15;
	JLabel lbLaptop16;
	JLabel lbLaptop17;
	JLabel lbLaptop18;
	JLabel lbLaptop19;
	JLabel lbLaptop20;
	JLabel lbLaptop21;
	JLabel lbLaptop22;
	JLabel lbLaptop23;
	JLabel lbLaptop24;
	JLabel lbLaptop25;

	// 우클릭시 팝업 이미지
	JPopupMenu jpm;
	JMenuItem popItemName;
	JMenuItem popItemStart;
	JMenuItem popItemPay;
	JMenuItem popItemCharge;
	JMenuItem popItemChat;

	// Pc지정시 팝업매뉴 액션 리스너에게 번호를 전달할 놈
	int currentPcNumber = -1;
	
	int pcTotal = 25; //컴퓨터의 총 갯수
	int pcAvailable = 0; //현재 사용가능한 PC갯수

	public FrameServer()
	{
		setTitle("관리자 프로그램");
		Toolkit tool = Toolkit.getDefaultToolkit();
		d1 = tool.getScreenSize();// 모니터 사이즈 측정
		// width는 화면 크기로, height는 작업표시줄을 고려해 50을 잘랐음
		setBounds(0, 0, (int) d1.getWidth(), (int) d1.getHeight() - 50);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);// x눌러도 종료시키지 말자.
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				int result = jop.showConfirmDialog(null, "종료하시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		});

		// 핸들러들 생성
		MouseHandler mouseHandler = new MouseHandler();
		ActionHandler actionHandler = new ActionHandler();

		// 초기화
		jop = new JOptionPane();
		lines = new JPanel[5];
		plLine1 = new JPanel();
		plLine2 = new JPanel();
		plLine3 = new JPanel();
		plLine4 = new JPanel();
		plLine5 = new JPanel();
		lines[0] = plLine1;
		lines[1] = plLine2;
		lines[2] = plLine3;
		lines[3] = plLine4;
		lines[4] = plLine5;

		plBackground = new JPanel();
		mainScreen = new JPanel();
		mainScrollPane = new JScrollPane(mainScreen,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// 상단매뉴바
		bar = new JMenuBar();
		menuBasic = new JMenu("기본설정");
		menuMember = new JMenu("회원관리");
		menuFood = new JMenu("음식관리");
		itemBasicMoney = new JMenuItem("요금제관리");
		itemBasicExit = new JMenuItem("프로그램 종료");

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
		for (JPanel j : lines)
		{
			j.setBackground(Color.WHITE);
			j.setLayout(new FlowLayout(FlowLayout.LEFT));
			j.addMouseListener(mouseHandler);
		}
		mainScreen.add(plLine1);
		mainScreen.add(plLine2);
		mainScreen.add(plLine3);
		mainScreen.add(plLine4);
		mainScreen.add(plLine5);

		// PC라벨과 아이콘놈들 초기화하고 배열에 넣기
		imgBlank = new ImageIcon("src/images/laptops/blank.jpg");
		
		imgLaptopR = new ImageIcon[pcTotal];
		imgLaptop01r = new ImageIcon("src/images/laptops/laptop01r.jpg");
		imgLaptop02r = new ImageIcon("src/images/laptops/laptop02r.jpg");
		imgLaptop03r = new ImageIcon("src/images/laptops/laptop03r.jpg");
		imgLaptop04r = new ImageIcon("src/images/laptops/laptop04r.jpg");
		imgLaptop05r = new ImageIcon("src/images/laptops/laptop05r.jpg");
		imgLaptop06r = new ImageIcon("src/images/laptops/laptop06r.jpg");
		imgLaptop07r = new ImageIcon("src/images/laptops/laptop07r.jpg");
		imgLaptop08r = new ImageIcon("src/images/laptops/laptop08r.jpg");
		imgLaptop09r = new ImageIcon("src/images/laptops/laptop09r.jpg");
		imgLaptop10r = new ImageIcon("src/images/laptops/laptop10r.jpg");
		imgLaptop11r = new ImageIcon("src/images/laptops/laptop11r.jpg");
		imgLaptop12r = new ImageIcon("src/images/laptops/laptop12r.jpg");
		imgLaptop13r = new ImageIcon("src/images/laptops/laptop13r.jpg");
		imgLaptop14r = new ImageIcon("src/images/laptops/laptop14r.jpg");
		imgLaptop15r = new ImageIcon("src/images/laptops/laptop15r.jpg");
		imgLaptop16r = new ImageIcon("src/images/laptops/laptop16r.jpg");
		imgLaptop17r = new ImageIcon("src/images/laptops/laptop17r.jpg");
		imgLaptop18r = new ImageIcon("src/images/laptops/laptop18r.jpg");
		imgLaptop19r = new ImageIcon("src/images/laptops/laptop19r.jpg");
		imgLaptop20r = new ImageIcon("src/images/laptops/laptop20r.jpg");
		imgLaptop21r = new ImageIcon("src/images/laptops/laptop21r.jpg");
		imgLaptop22r = new ImageIcon("src/images/laptops/laptop22r.jpg");
		imgLaptop23r = new ImageIcon("src/images/laptops/laptop23r.jpg");
		imgLaptop24r = new ImageIcon("src/images/laptops/laptop24r.jpg");
		imgLaptop25r = new ImageIcon("src/images/laptops/laptop25r.jpg");
		imgLaptopR[0] = imgLaptop01r;
		imgLaptopR[1] = imgLaptop02r;
		imgLaptopR[2] = imgLaptop03r;
		imgLaptopR[3] = imgLaptop04r;
		imgLaptopR[4] = imgLaptop05r;
		imgLaptopR[5] = imgLaptop06r;
		imgLaptopR[6] = imgLaptop07r;
		imgLaptopR[7] = imgLaptop08r;
		imgLaptopR[8] = imgLaptop09r;
		imgLaptopR[9] = imgLaptop10r;
		imgLaptopR[10] = imgLaptop11r;
		imgLaptopR[11] = imgLaptop12r;
		imgLaptopR[12] = imgLaptop13r;
		imgLaptopR[13] = imgLaptop14r;
		imgLaptopR[14] = imgLaptop15r;
		imgLaptopR[15] = imgLaptop16r;
		imgLaptopR[16] = imgLaptop17r;
		imgLaptopR[17] = imgLaptop18r;
		imgLaptopR[18] = imgLaptop19r;
		imgLaptopR[19] = imgLaptop20r;
		imgLaptopR[20] = imgLaptop21r;
		imgLaptopR[21] = imgLaptop22r;
		imgLaptopR[22] = imgLaptop23r;
		imgLaptopR[23] = imgLaptop24r;
		imgLaptopR[24] = imgLaptop25r;

		imgLaptopS = new ImageIcon[pcTotal];
		imgLaptop01s = new ImageIcon("src/images/laptops/laptop01s.jpg");
		imgLaptop02s = new ImageIcon("src/images/laptops/laptop02s.jpg");
		imgLaptop03s = new ImageIcon("src/images/laptops/laptop03s.jpg");
		imgLaptop04s = new ImageIcon("src/images/laptops/laptop04s.jpg");
		imgLaptop05s = new ImageIcon("src/images/laptops/laptop05s.jpg");
		imgLaptop06s = new ImageIcon("src/images/laptops/laptop06s.jpg");
		imgLaptop07s = new ImageIcon("src/images/laptops/laptop07s.jpg");
		imgLaptop08s = new ImageIcon("src/images/laptops/laptop08s.jpg");
		imgLaptop09s = new ImageIcon("src/images/laptops/laptop09s.jpg");
		imgLaptop10s = new ImageIcon("src/images/laptops/laptop10s.jpg");
		imgLaptop11s = new ImageIcon("src/images/laptops/laptop11s.jpg");
		imgLaptop12s = new ImageIcon("src/images/laptops/laptop12s.jpg");
		imgLaptop13s = new ImageIcon("src/images/laptops/laptop13s.jpg");
		imgLaptop14s = new ImageIcon("src/images/laptops/laptop14s.jpg");
		imgLaptop15s = new ImageIcon("src/images/laptops/laptop15s.jpg");
		imgLaptop16s = new ImageIcon("src/images/laptops/laptop16s.jpg");
		imgLaptop17s = new ImageIcon("src/images/laptops/laptop17s.jpg");
		imgLaptop18s = new ImageIcon("src/images/laptops/laptop18s.jpg");
		imgLaptop19s = new ImageIcon("src/images/laptops/laptop19s.jpg");
		imgLaptop20s = new ImageIcon("src/images/laptops/laptop20s.jpg");
		imgLaptop21s = new ImageIcon("src/images/laptops/laptop21s.jpg");
		imgLaptop22s = new ImageIcon("src/images/laptops/laptop22s.jpg");
		imgLaptop23s = new ImageIcon("src/images/laptops/laptop23s.jpg");
		imgLaptop24s = new ImageIcon("src/images/laptops/laptop24s.jpg");
		imgLaptop25s = new ImageIcon("src/images/laptops/laptop25s.jpg");
		imgLaptopS[0] = imgLaptop01s;
		imgLaptopS[1] = imgLaptop02s;
		imgLaptopS[2] = imgLaptop03s;
		imgLaptopS[3] = imgLaptop04s;
		imgLaptopS[4] = imgLaptop05s;
		imgLaptopS[5] = imgLaptop06s;
		imgLaptopS[6] = imgLaptop07s;
		imgLaptopS[7] = imgLaptop08s;
		imgLaptopS[8] = imgLaptop09s;
		imgLaptopS[9] = imgLaptop10s;
		imgLaptopS[10] = imgLaptop11s;
		imgLaptopS[11] = imgLaptop12s;
		imgLaptopS[12] = imgLaptop13s;
		imgLaptopS[13] = imgLaptop14s;
		imgLaptopS[14] = imgLaptop15s;
		imgLaptopS[15] = imgLaptop16s;
		imgLaptopS[16] = imgLaptop17s;
		imgLaptopS[17] = imgLaptop18s;
		imgLaptopS[18] = imgLaptop19s;
		imgLaptopS[19] = imgLaptop20s;
		imgLaptopS[20] = imgLaptop21s;
		imgLaptopS[21] = imgLaptop22s;
		imgLaptopS[22] = imgLaptop23s;
		imgLaptopS[23] = imgLaptop24s;
		imgLaptopS[24] = imgLaptop25s;
		
		lbBlank1 = new JLabel();
		lbBlank2 = new JLabel();
		lbBlank3 = new JLabel();
		lbBlank4 = new JLabel();
		lbBlank5 = new JLabel();
		
		lbLaptop = new JLabel[pcTotal];
		lbLaptop01 = new JLabel();
		lbLaptop02 = new JLabel();
		lbLaptop03 = new JLabel();
		lbLaptop04 = new JLabel();
		lbLaptop05 = new JLabel();
		lbLaptop06 = new JLabel();
		lbLaptop07 = new JLabel();
		lbLaptop08 = new JLabel();
		lbLaptop09 = new JLabel();
		lbLaptop10 = new JLabel();
		lbLaptop11 = new JLabel();
		lbLaptop12 = new JLabel();
		lbLaptop13 = new JLabel();
		lbLaptop14 = new JLabel();
		lbLaptop15 = new JLabel();
		lbLaptop16 = new JLabel();
		lbLaptop17 = new JLabel();
		lbLaptop18 = new JLabel();
		lbLaptop19 = new JLabel();
		lbLaptop20 = new JLabel();
		lbLaptop21 = new JLabel();
		lbLaptop22 = new JLabel();
		lbLaptop23 = new JLabel();
		lbLaptop24 = new JLabel();
		lbLaptop25 = new JLabel();
		lbLaptop[0] = lbLaptop01;
		lbLaptop[1] = lbLaptop02;
		lbLaptop[2] = lbLaptop03;
		lbLaptop[3] = lbLaptop04;
		lbLaptop[4] = lbLaptop05;
		lbLaptop[5] = lbLaptop06;
		lbLaptop[6] = lbLaptop07;
		lbLaptop[7] = lbLaptop08;
		lbLaptop[8] = lbLaptop09;
		lbLaptop[9] = lbLaptop10;
		lbLaptop[10] = lbLaptop11;
		lbLaptop[11] = lbLaptop12;
		lbLaptop[12] = lbLaptop13;
		lbLaptop[13] = lbLaptop14;
		lbLaptop[14] = lbLaptop15;
		lbLaptop[15] = lbLaptop16;
		lbLaptop[16] = lbLaptop17;
		lbLaptop[17] = lbLaptop18;
		lbLaptop[18] = lbLaptop19;
		lbLaptop[19] = lbLaptop20;
		lbLaptop[20] = lbLaptop21;
		lbLaptop[21] = lbLaptop22;
		lbLaptop[22] = lbLaptop23;
		lbLaptop[23] = lbLaptop24;
		lbLaptop[24] = lbLaptop25;

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
		// Line1에 추가
		for (int i=0 ; i < 5; i++)
		{
			if(i==3)
			{
				lbBlank1.setIcon(imgBlank);
				plLine1.add(lbBlank1);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine1.add(lbLaptop[i]);
		}
		//Line2
		for (int i = 5; i < 10; i++)
		{
			if(i==8)
			{
				System.out.println("8번일때");
				lbBlank2.setIcon(imgBlank);
				plLine2.add(lbBlank2);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine2.add(lbLaptop[i]);
		}
		
		//Line3
		for (int i = 10; i < 15; i++)
		{
			if(i==13)
			{
				lbBlank3.setIcon(imgBlank);
				plLine3.add(lbBlank3);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine3.add(lbLaptop[i]);
		}
		
		//Line4
		for (int i = 15; i < 20; i++)
		{
			if(i==18)
			{
				lbBlank4.setIcon(imgBlank);
				plLine4.add(lbBlank4);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine4.add(lbLaptop[i]);
		}
		
		//Line5
		for (int i = 20; i < 25; i++)
		{
			if(i==23)
			{
				lbBlank5.setIcon(imgBlank);
				plLine5.add(lbBlank5);
			}
			lbLaptop[i].setIcon(imgLaptopS[i]);
			lbLaptop[i].setBackground(Color.WHITE);
			lbLaptop[i].addMouseListener(mouseHandler);
			plLine5.add(lbLaptop[i]);
		}

		setVisible(true);
	}

	class MouseHandler implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if (e.isMetaDown()) // 마우스를 우클릭하면
			{
				Object obj = e.getSource();
				for (int i = 0; i < 25; i++) // 컴퓨터 라벨 갯수만큼 돌릴 예정
				{
					if (obj.equals(lbLaptop[i])) // 만약 일치하는 라벨이 있을 경우
					{
						popItemName.setText((i + 1) + "번 PC"); // 팝업매뉴에 pc이름 표기
						jpm.setLocation(e.getXOnScreen(), e.getYOnScreen()); // 스크린을
																				// 기준으로
																				// x와
																				// y의
																				// 좌표
																				// 가져와
																				// 팝업매뉴
																				// 위치지정
						jpm.setVisible(true); // 팝업매뉴 나와랑
						currentPcNumber = i; // 현재 PC번호를 저장
						System.out.println("laptop : " + currentPcNumber);
						break;// 더 돌릴 필요 없으니 break;
					}
				}
			} else
			{
				jpm.setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			// TODO Auto-generated method stub

		}
	}

	class ActionHandler implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Object obj = e.getSource();

			// 팝업에 PC시작을 누르면
			if (obj.equals(popItemStart))
			{
				// 선택한 pc번호로 라벨의 아이콘을 가져와 아이콘 배열과 비교, 켜져있는지 꺼져있는지 판단
				// (ex) 1번 피씨 lbLaptop[0] 1번 실행중인 아이콘 배열 imgLaptopR[0] / 1번 꺼져있는
				// 아이콘 배열 imgLaptopS[0]
				if (imgLaptopR[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					System.out.println(currentPcNumber + "번 PC가 이미 실행 중입니다.");
					String message = (currentPcNumber + 1)
							+ "번 PC가 이미 실행 중입니다.";
					// 알림 다이얼로그 출력
					jop.showMessageDialog(null, message, "알림",
							JOptionPane.INFORMATION_MESSAGE);
					jpm.setVisible(false); // 팝업매뉴 꺼져라
					currentPcNumber = -1;
				} else if (imgLaptopS[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					System.out.println(currentPcNumber + 1 + "번 PC시작");
					// 시작 아이콘으로 변경
					lbLaptop[currentPcNumber]
							.setIcon(imgLaptopR[currentPcNumber]);
					jpm.setVisible(false); // 팝업매뉴 꺼졍
					currentPcNumber = -1;
				}
			}

			// 팝업정산버튼
			if (obj.equals(popItemPay))
			{
				if (imgLaptopR[currentPcNumber]
						.equals(lbLaptop[currentPcNumber].getIcon()))
				{
					int result = jop.showConfirmDialog(null, "정산하시겠습니까?", "알림",
							JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION)
					{
						System.out.println(currentPcNumber + 1 + "번 PC종료");
						lbLaptop[currentPcNumber]
								.setIcon(imgLaptopS[currentPcNumber]);
						jpm.setVisible(false);
						currentPcNumber = -1;
					}
					jpm.setVisible(false);
					currentPcNumber = -1;
				} else
				{
					System.out.println(currentPcNumber + "번 PC는 실행 중이 아닙니다.");
					String message = (currentPcNumber + 1)
							+ "번 PC는 실행 중이 아닙니다.";
					jop.showMessageDialog(null, message, "알림",
							JOptionPane.INFORMATION_MESSAGE);
					jpm.setVisible(false);
					currentPcNumber = -1;
				}
			}

			// 기본 매뉴 종료 버튼
			if (obj.equals(itemBasicExit))
			{
				int result = jop.showConfirmDialog(null, "종료하시겠습니까?", "알림",
						JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION)
				{
					System.exit(0);
				}
			}
		}
	}

	public static void main(String[] args)
	{
		new FrameServer();
	}
}
