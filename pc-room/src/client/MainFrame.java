package client;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.SetFrameDisplay;

public class MainFrame extends JFrame {
	public static int CLIENT_DISPLAY_WIDTH = 400;
	public static int CLIENT_DISPLAY_HEIGHT = 200;
	
	JPanel a;
	
	public MainFrame() {
		setTitle("PC방 클라이언트");
		setBounds(SetFrameDisplay.DISPLAY_WIDTH-CLIENT_DISPLAY_WIDTH, 0, CLIENT_DISPLAY_WIDTH, CLIENT_DISPLAY_HEIGHT);
		setUndecorated(true);
		init();
		
		setVisible(true);
	}
	
	public void init() {
		
	}
}