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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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

import util.PLabel;
import util.Resttimer;
import util.SetFrameDisplay;

public class FrameServer extends JFrame {

	public static int PC_TOTAL = 25; // 컴퓨터의 총 갯수
	public static int PC_AVAILABLE = 0; // 현재 사용가능한 PC갯수
	MemberHelper memberHelper;

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
	ObjectMultiServer oms;
	Thread threadMultiServer;
	Thread threadBringMember;
	
	//Resttimer를 자리마다 배치하기위한 배열
	Resttimer[] resttimer;

	public FrameServer() {
		setTitle("관리자 프로그램");
		oms = new ObjectMultiServer();
		threadMultiServer = new Thread(oms);
		threadMultiServer.start();
		
		memberHelper = new MemberHelper();
		
		//resttimer를 자리마다 두기위해 자릿수만큼 생성
		resttimer = new Resttimer[PC_TOTAL];
		for(int i=0 ; i<PC_TOTAL; i++){
			resttimer[i] = new Resttimer();
		}

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
				lbLaptop[num].setMessageLine1("");
				lbLaptop[num].setMessageLine2("");
				lbLaptop[num].setMessageLine3("");
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

			// 매뉴바의 회원관리 .. 액션리스너가 안먹혀서 마우스 리스너로(매뉴에 마우스 리스너를 줘서)
			if (obj.equals(menuMember)) {
				new FrameMember();
			}
			// 매뉴바의 음식관리
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
				jpm.setVisible(false); // 팝업매뉴 꺼져라
				if (imgLaptopR[currentPcNumber].equals(lbLaptop[currentPcNumber].getIcon())) {
					System.out.println(currentPcNumber + "번 PC가 이미 실행 중입니다.");
					String message = (currentPcNumber + 1) + "번 PC가 이미 실행 중입니다.";
					// 알림 다이얼로그 출력
					JOptionPane.showMessageDialog(null, message, "알림", JOptionPane.INFORMATION_MESSAGE);
					currentPcNumber = -1;
				} else if (imgLaptopS[currentPcNumber].equals(lbLaptop[currentPcNumber].getIcon())) {
					System.out.println(currentPcNumber + 1 + "번 PC시작");
					// 시작 아이콘으로 변경
					lbLaptop[currentPcNumber].setIcon(imgLaptopR[currentPcNumber]);
					pcMessage = (currentPcNumber + 1) + "가 실행중";
					lbLaptop[currentPcNumber].setMessageLine1(pcMessage);
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
						lbLaptop[currentPcNumber].setMessageLine1(pcMessage);
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

			// 기본 매뉴 종료 버튼
			if (obj.equals(itemBasicExit)) {
				int result = JOptionPane.showConfirmDialog(null, "종료하시겠습니까?", "알림", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		}
	}

	// 프레임이 켜짐과 동시에 켜지는 멀티 서버
	public class ObjectMultiServer extends Thread {
		// 클라이언트들을 하나씩 담긔
		ArrayList<ObjectServer> objClientList;

		// 여러인원이 접속할 수 있도록 while문이 계속 돌아가므로 Thread를 준다.
		@Override
		public void run() {
			// ArrayList 초기화
			objClientList = new ArrayList<ObjectServer>();
			try {
				ServerSocket ss = new ServerSocket(5000);
				// 계속 돌아가면서 클라이언트들을 받는다.
				while (true) {
					System.out.println("Object Server 접속 대기중 ...");
					Socket socketClient = ss.accept();
					ObjectServer os = new ObjectServer(socketClient);
					objClientList.add(os); // 접속한 클라이언트를 리스트에 추가
					os.start(); // 받으면 클라이언트 하나당 쓰레드를 만든다.
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// 클라이언트 하나당 돌아갈 내부 클래스
		class ObjectServer extends Thread {
			Socket socketClient;
			ObjectInputStream ois;
			ObjectOutputStream oos;
			OutputStreamWriter osw;
			BufferedWriter bw;
			PrintWriter pw;
			Object messageObject;
			InetAddress inet;
			String clientIp;
			String welcome;

			// 생성될때 접속한 클라이언트의 소켓을 가져온다.
			public ObjectServer(Socket socketClient) {
				this.socketClient = socketClient;
			}

			@Override
			public void run() {
				System.out.println(socketClient);
				inet = socketClient.getInetAddress();
				clientIp = inet.getHostAddress();
				welcome = clientIp + "가 서버에 접속"; // 서버 접속한 클라이언트 아이피 표시
				System.out.println(welcome);
				try {
					// Object를 주고받는 스트림 연결
					ois = new ObjectInputStream(socketClient.getInputStream());
					oos = new ObjectOutputStream(socketClient.getOutputStream());
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())));

					// 보낸 Object를 읽는다.
					messageObject = ois.readObject();
					System.out.println("받은 Object : " + messageObject); // 확인할겸
																		// 받은거
																		// 출력해보자

					// objectIs method를 통해 해당 Object가 어떤 클래스를 담은건지 파악하여 담는다.
					String objectIs = objectIs(messageObject.toString());
					System.out.println(objectIs);

					// ***수정하긔
					// 만약 TestObject에서 온거라면
					if (objectIs.equals("Member")) {
						// 맴버를 받았다고 알려주자
						pw.println("member");
						pw.flush();
						takeMember((Member) messageObject);
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (socketClient != null)
							socketClient.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		// 클라이언트에게 받은 오브젝트는 무슨 클래스에서 만들어졌는가 판단
		// object.toString 문자열에서 .과 @의 문자를 추출하여 판단 (ex. server.Member@~~~~)
		public String objectIs(String tag) {
			int dot = tag.indexOf('.');
			int sign = tag.indexOf('@');
			return tag.substring(dot + 1, sign);
		}

		// 클라이언트와 통신하고 있는 서버 쓰레드가 member instance를 받으면 laptop아이콘에 member정보를 그린다.
		public void takeMember(Member member) {
			String seatNum = member.getSeatNum();
			int intSeatNum = Integer.parseInt(seatNum);
			int arrSeatNum = intSeatNum-1;
			String id = member.getId();
			String name = member.getName();
			int resttime = member.getResttime();

			// 해당 자리번호 라벨의 아이콘 이미지가 실행중 아이콘인지 정지중 아이콘인지 판단하여 실행중/정지상태를 판단
			if (imgLaptopR[arrSeatNum].equals(lbLaptop[arrSeatNum].getIcon())) {
				System.out.println(intSeatNum + "번 PC종료");
				
				//종료되면 해당 pc의 resttimer를 종료
				resttimer[arrSeatNum].cancelTimer();
				//종료될 때의 시간과 아이디를 가지고 memberHelper를 통해 업데이트해준다.
				memberHelper.edit(id, resttimer[arrSeatNum].getResttime());
				System.out.println(resttimer[arrSeatNum].getResttime()+"로 업데이트");
				
				pcMessage = "";
				lbLaptop[arrSeatNum].setIcon(imgLaptopS[arrSeatNum]);
				lbLaptop[arrSeatNum].setMessageLine1(pcMessage);
				lbLaptop[arrSeatNum].setMessageLine2(pcMessage);
				lbLaptop[arrSeatNum].setMessageLine3(pcMessage);
				//메세지 라인이 3개인 이유는 글자 그리는게 한줄씩 그릴수밖에 없어서..
				//여기에서 타이머를 끄자
				
			} else if (imgLaptopS[arrSeatNum].equals(lbLaptop[arrSeatNum].getIcon())) {
				System.out.println((intSeatNum) + "번 PC시작");
				// 시작 아이콘으로 변경
				lbLaptop[arrSeatNum].setIcon(imgLaptopR[arrSeatNum]);
				lbLaptop[arrSeatNum].setMessageLine1(id);
				lbLaptop[arrSeatNum].setMessageLine2(name);
				
				//여기에 타이머를 실행시키자
				resttimer[arrSeatNum].setResttime(resttime);
				resttimer[arrSeatNum].start(lbLaptop[arrSeatNum]);
			}
		}
	}

	public static void main(String[] args) {
		new FrameServer();
	}
}
