package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import util.MoneyConverter;
import util.ObjectClient;
import util.Resttimer;
import util.SetFrameDisplay;
import util.SetLabelAlignment;
import util.SignalDetector;

public class MainFrame extends JFrame {
	public static int 		  CLIENT_DISPLAY_WIDTH 		= 400;
	public static int 		  CLIENT_DISPLAY_HEIGHT 	= 200;
	public static int 		  BAR_HEIGHT 				= 30;
	public static int 		  BAR_CONTENT_WIDTH 		= 30;
	
	public 	Member 	  		  loginUser     = null;
	public 	ClientChatGui	  clientChatGui = null;
	
	private JPanel			  pTopBar;
	private JLabel 			  lbBarLogo;
	private ImageIcon 		  imgBarLogo;
	private JLabel 		 	  lbPCBangName;
	private JButton 		  btnMinimize;
	
	private JPanel 			  pContent;
	private JLabel 			  lbLogo;
	private ImageIcon 		  imgLogo;
	private JLabel 			  lbSeatNumber;
	private JLabel 			  lbRemainTime;
	private JLabel 			  lbLiveTime;
	
	private JButton 		  btnOrder;
	private JButton 		  btnMessage;
	private JButton 	  	  btnExit;
	
	Resttimer resttimer;
	SignalWaiter signalWaiter;
	
	public MainFrame(Member loginUser) throws IOException {
		signalWaiter = new SignalWaiter(); //클라이언트 서버 실행
		signalWaiter.start();
		setTitle("PC방 클라이언트");
		setLayout(null);
		setBounds(SetFrameDisplay.DISPLAY_WIDTH - CLIENT_DISPLAY_WIDTH, 25, CLIENT_DISPLAY_WIDTH,
				CLIENT_DISPLAY_HEIGHT);
		setUndecorated(true); // 타이틀바 삭제

		this.loginUser = loginUser;

		System.out.println(loginUser.getRestTime()+"\n"+loginUser.getSeatNumber()+"번 자리");
		
		initTopBar();
		initContent();
		
		initConnectServer();
		
		resttimer = new Resttimer();
		resttimer.start(loginUser, lbRemainTime, lbLiveTime);
		
		setVisible(true);
	}

	private void initConnectServer() throws IOException {
		clientChatGui = new ClientChatGui(loginUser.getSeatNumber());
	}

	public void initTopBar() {
		pTopBar = new JPanel();
		pTopBar.setSize(CLIENT_DISPLAY_WIDTH, BAR_HEIGHT);
		pTopBar.setLocation(0, 0);
		pTopBar.setLayout(null);

		imgBarLogo = new ImageIcon("src/images/laptops/laptop01r.jpg");
		lbBarLogo = new JLabel("H");
		lbBarLogo.setIcon(imgBarLogo);
		lbBarLogo.setBounds(0, 0, BAR_CONTENT_WIDTH, BAR_HEIGHT);

		lbPCBangName = new JLabel("PC BANG");
		lbPCBangName.setBounds(BAR_CONTENT_WIDTH, 0, CLIENT_DISPLAY_WIDTH - BAR_CONTENT_WIDTH * 2, BAR_HEIGHT);
		SetLabelAlignment.setAllCenterAlignment(lbPCBangName);

		btnMinimize = new JButton("-");
		btnMinimize.setBounds(lbPCBangName.getX() + lbPCBangName.getWidth(), 0, BAR_CONTENT_WIDTH, BAR_HEIGHT);
		btnMinimize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setState(JFrame.ICONIFIED);
			}
		});

		pTopBar.add(lbBarLogo);
		pTopBar.add(lbPCBangName);
		pTopBar.add(btnMinimize);
		add(pTopBar);
	}

	public void initContent() {
		pContent = new JPanel();
		pContent.setBounds(0, BAR_HEIGHT, CLIENT_DISPLAY_WIDTH, CLIENT_DISPLAY_HEIGHT - BAR_CONTENT_WIDTH);
		pContent.setLayout(null);

		imgLogo = new ImageIcon("./src/images/client/contentpanel/logo.jpeg");
		lbLogo = new JLabel();
		lbLogo.setIcon(imgLogo);
		lbLogo.setSize(80,80);
		lbLogo.setLocation(20,20);
		
		lbSeatNumber = new JLabel(loginUser.getSeatNumber());
		lbSeatNumber.setSize(80,80);
		lbSeatNumber.setLocation(lbLogo.getX()+lbLogo.getWidth(), lbLogo.getY());
		SetLabelAlignment.setAllCenterAlignment(lbSeatNumber);

		lbRemainTime = new JLabel("남은시간");
		lbRemainTime.setBounds(this.getWidth() - 100, lbSeatNumber.getY(), 80, 35);
		SetLabelAlignment.setAllRightAlignment(lbRemainTime);
		lbLiveTime = new JLabel("현재시간");
		lbLiveTime.setBounds(lbRemainTime.getX(), lbRemainTime.getY() + lbRemainTime.getHeight() + 10, 80, 35);
		SetLabelAlignment.setAllRightAlignment(lbLiveTime);

		btnOrder = new JButton("주문하기");
		btnOrder.setSize(100, 40);
		btnOrder.setLocation(lbLogo.getX() + 25, lbLogo.getY() + lbLogo.getHeight() + 15);
		btnOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 음식주문 프레임 띄우기
				new FrameFood(loginUser);
			}
		});

		btnMessage = new JButton("메세지");
		btnMessage.setSize(100, 40);
		btnMessage.setLocation(btnOrder.getX() + btnOrder.getWidth() + 10, btnOrder.getY());
		btnMessage.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 서버에 메세지 프레임 표시
				if(clientChatGui.isVisible() == false ) {
					System.out.println("메세지 호출 : "+loginUser.getSeatNumber());
					clientChatGui.setVisible(true);
				}
			}
		});

		btnExit = new JButton("사용종료");
		btnExit.setSize(100, 40);
		btnExit.setLocation(btnMessage.getX() + btnMessage.getWidth() + 10, btnMessage.getY());
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 남은 시간 저장
				resttimer.cancelTimer(); // 타이머 시간줄이기 캔슬
				loginUser.setRestTime(resttimer.getResttime()); // 맴버의 resttime을
																// resttimer의
																// 남은시간으로 저장
				loginUser.restTimeSave();
				// 2. 종료신호 서버에 전송
				ObjectClient.sendObject(loginUser);
				// 3. 컴퓨터 종료
				System.out.println("컴퓨터종료");
				// ClientExit.exit();
			}
		});

		pContent.add(lbLogo);
		pContent.add(lbSeatNumber);
		pContent.add(lbRemainTime);
		pContent.add(lbLiveTime);
		pContent.add(btnOrder);
		pContent.add(btnMessage);
		pContent.add(btnExit);
		add(pContent);
	}

	class SignalWaiter extends Thread {
		ServerSocket sc;
		BufferedReader br;
		PrintWriter pw;
		InetAddress inet;
		String serverIp;
		String welcome;
		Socket socketToManager;

		@Override
		public void run() {
			startWait();
		}

		public void startWait() {
			try {
				sc = new ServerSocket(6000);
				while (true) {
					System.out.println("서버신호를 기다리는중 ...");
					socketToManager = sc.accept();
					inet = socketToManager.getInetAddress();
					serverIp = inet.getHostAddress();
					System.out.println(serverIp + "님이 접속");

					br = new BufferedReader(new InputStreamReader(socketToManager.getInputStream()));
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketToManager.getOutputStream())));

					String message = br.readLine();
					System.out.println(message);
					String messageIs = SignalDetector.signalIs(message);
					System.out.println("SignalWaiter : 받은 메세지 " + messageIs);

					if (messageIs.equals("shutdown")) {
						// 해당 맴버 객체의 정보를 저장하고 종료하기
						System.out.println("SignalWaiter : shutdown");
						pw.println("executeShutdown");
						pw.flush();
						
						 resttimer.cancelTimer();
						 loginUser.setRestTime(resttimer.getResttime());
						 loginUser.restTimeSave();
						 System.out.println("컴퓨터종료");
//						 ClientExit.exit();
					}

					if (messageIs.equals("charge")) {
						pw.println("executeCharge");
						pw.flush();
						String chargeMoney = SignalDetector.chargeMoneyIs(message);
						System.out.println("SignalWaiter : time is " + chargeMoney);
						int chargeTime = MoneyConverter.basicConvert(Integer.parseInt(chargeMoney));
						// 해당 MainFrame의 resttimer의 시간을
						// resttimer의 시간으로 DB 업뎃도 한번 해주자

						 resttimer.setResttime(resttimer.getResttime()+chargeTime);
						 loginUser.setRestTime(resttimer.getResttime());
						 loginUser.restTimeSave();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(null, "매니저클라이언트 연결 오류");
				e.printStackTrace();
			} finally {
				try {
					if (sc != null)
						sc.close();
					if (br != null)
						br.close();
					if (pw != null)
						pw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}