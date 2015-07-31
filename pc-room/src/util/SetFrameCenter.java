package util;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class SetFrameCenter {
	
	/*
	 * setFrameCenter 설명
	 * 화면에 출력되는 Frame을 어느 모니터든 가운데에 출력하게하는 메서드
	 * Dimension 모니터의 해상도를 구하기 위한 코드
	 * 
	 * 화면 중앙에 띄우는 공식
	 * screenWidth : (전체 해상도 Width - 지정한 Frame Width)/2 => int만 가능
	 * screenHeight : (전체 해상도 Height - 지정한 Frame Height)/2 => int만 가능 
	 */
	public static void setFrameCenter(JFrame frame) {
		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) (res.getWidth()-frame.getWidth())/2;
		int screenHeight = (int) (res.getHeight()-frame.getHeight())/2;
		System.out.println(screenHeight + ", " + screenHeight);
		frame.setLocation(screenWidth, screenHeight);
	}

}
