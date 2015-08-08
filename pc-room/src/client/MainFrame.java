package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.ServerBackground;
import util.ClientExit;
import util.SetFrameDisplay;
import util.SetLabelAlignment;

public class MainFrame extends JFrame {
	public static int 		  CLIENT_DISPLAY_WIDTH 		= 400;
	public static int 		  CLIENT_DISPLAY_HEIGHT 	= 200;
	public static int 		  BAR_HEIGHT 				= 30;
	public static int 		  BAR_CONTENT_WIDTH 		= 30;
	
	public 	ConnectMember 	  loginUser     = null;
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
	
	public MainFrame(ConnectMember loginUser) throws IOException {
		setTitle("PC방 클라이언트");
		setLayout(null);
		setBounds(SetFrameDisplay.DISPLAY_WIDTH-CLIENT_DISPLAY_WIDTH, 25, CLIENT_DISPLAY_WIDTH, CLIENT_DISPLAY_HEIGHT);
		setUndecorated(true); 	// 타이틀바 삭제
		
		this.loginUser = loginUser;
		System.out.println(loginUser.getRestTime()+"\n"+loginUser.getSeatNumber()+"번 자리");
		
		initTopBar();
		initContent();
		
		initConnectServer();
		
		setVisible(true);
	}
	
	public void initTopBar() {
		pTopBar = new JPanel();
		pTopBar.setSize(CLIENT_DISPLAY_WIDTH,BAR_HEIGHT);
		pTopBar.setLocation(0, 0);
		pTopBar.setLayout(null);
		
		
		imgBarLogo = new ImageIcon("src/images/laptops/laptop01r.jpg");
		lbBarLogo = new JLabel("H");
		lbBarLogo.setIcon(imgBarLogo);
		lbBarLogo.setBounds(0,0,BAR_CONTENT_WIDTH,BAR_HEIGHT);
		
		lbPCBangName = new JLabel("PC BANG");
		lbPCBangName.setBounds(BAR_CONTENT_WIDTH,0,CLIENT_DISPLAY_WIDTH-BAR_CONTENT_WIDTH*2,BAR_HEIGHT);
		SetLabelAlignment.setAllCenterAlignment(lbPCBangName);
		
		btnMinimize = new JButton("-");
		btnMinimize.setBounds(lbPCBangName.getX()+lbPCBangName.getWidth(),0,BAR_CONTENT_WIDTH,BAR_HEIGHT);
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
		pContent.setBounds(0,BAR_HEIGHT,CLIENT_DISPLAY_WIDTH,CLIENT_DISPLAY_HEIGHT-BAR_CONTENT_WIDTH);
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
		lbRemainTime.setBounds(this.getWidth()-100,lbSeatNumber.getY(),80,35);
		SetLabelAlignment.setAllRightAlignment(lbRemainTime);
		lbLiveTime = new JLabel("15:23");
		lbLiveTime.setBounds(lbRemainTime.getX(), lbRemainTime.getY()+lbRemainTime.getHeight()+10, 80, 35);
		SetLabelAlignment.setAllRightAlignment(lbLiveTime);
		
		btnOrder 	= new JButton("주문하기");
		btnOrder.setSize(100,40);
		btnOrder.setLocation(lbLogo.getX()+25,lbLogo.getY()+lbLogo.getHeight()+15);
		btnOrder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 음식주문 프레임 띄우기
				
			}
		});
		
		btnMessage 	= new JButton("메세지");
		btnMessage.setSize(100,40);
		btnMessage.setLocation(btnOrder.getX()+btnOrder.getWidth()+10,btnOrder.getY());
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
		
		btnExit  	= new JButton("사용종료");
		btnExit.setSize(100,40);
		btnExit.setLocation(btnMessage.getX()+btnMessage.getWidth()+10,btnMessage.getY());
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 1. 남은 시간 저장
				loginUser.restTimeSave();
				// 2. 종료신호 서버에 전송
				// 3. 컴퓨터 종료
				ClientExit.exit();
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
	
	void initConnectServer() throws IOException {
		clientChatGui = new ClientChatGui(loginUser.getSeatNumber());
	}
}