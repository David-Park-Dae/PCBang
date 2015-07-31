package util;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class SetFullMode {
	
	public static void setFullMode(JFrame frame) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		frame.setUndecorated(true);
		gd.setFullScreenWindow(frame);
	}
}
