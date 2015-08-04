package util;

import javax.swing.JLabel;

public class SetLabelAlignment {
	
	public static void setHorizontalAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
	}
	
	public static void setVerticalAlignment(JLabel label) {
		label.setVerticalAlignment(JLabel.CENTER);
	}
	
	public static void setAllAlignment(JLabel label) {
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
	}
	
}
