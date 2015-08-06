package util;

import javax.swing.JLabel;

public class SetLabelAlignment {
	
	public static void setHorizontalCenterAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
	}
	
	public static void setVerticalCenterAlignment(JLabel label) {
		label.setVerticalAlignment(JLabel.CENTER);
	}
	
	public static void setAllCenterAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
	}
	
	public static void setHorizontalRightAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.RIGHT); 
	}
	
	public static void setVerticalRightAlignment(JLabel label) {
		label.setVerticalAlignment(JLabel.RIGHT);
	}
	
	public static void setAllRightAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.RIGHT);
		label.setVerticalAlignment(JLabel.CENTER);
	}
	
}
