package client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.SetFrameDisplay;
import util.SetLabelAlignment;

public class MainFrame extends JFrame {
	public static int CLIENT_DISPLAY_WIDTH = 400;
	public static int CLIENT_DISPLAY_HEIGHT = 250;
	public static int BAR_HEIGHT = 30;
	public static int BAR_CONTENT_WIDTH = 30;
	
	JPanel			  pTopBar;
	JLabel 			  lbBarLogo;
	ImageIcon 		  imgBarLogo;
	JLabel 		 	  lbPCBangName;
	JButton 		  btnMinimize;
	
	JPanel 			  pContent;
	ImageIcon 		  imgLogo;
	JLabel 			  lbSeatNumber;
	JLabel 			  lbRemainTime;
	JLabel 			  lbLiveTime;
	
	JButton 		  btnOrder;
	JButton 		  btnMessage;
	JButton 	  	  btnExit;
	
	public MainFrame() {
		setTitle("PC방 클라이언트");
		setLayout(null);
		setBounds(SetFrameDisplay.DISPLAY_WIDTH-CLIENT_DISPLAY_WIDTH, 25, CLIENT_DISPLAY_WIDTH, CLIENT_DISPLAY_HEIGHT);
		setUndecorated(true); 	// 타이틀바 삭제
		
		initTopBar();
		initContent();
		
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
		SetLabelAlignment.setAllAlignment(lbPCBangName);
		
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
		pContent.setBackground(Color.BLACK);
		
		add(pContent);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}