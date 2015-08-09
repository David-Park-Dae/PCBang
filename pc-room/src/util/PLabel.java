package util;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PLabel extends JLabel{
	private ImageIcon imgIcon;
	private String messageLine1;
	private String messageLine2;
	private String messageLine3;
	
	public ImageIcon getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(ImageIcon img) {
		this.imgIcon = img;
	}

	public String getMessageLine1() {
		return messageLine1;
	}

	public void setMessageLine1(String messageLine1) {
		this.messageLine1 = messageLine1;
		repaint();
	}

	public String getMessageLine2() {
		return messageLine2;
	}

	public void setMessageLine2(String messageLine2) {
		this.messageLine2 = messageLine2;
		repaint();
	}

	public String getMessageLine3() {
		return messageLine3;
	}

	public void setMessageLine3(String messageLine3) {
		this.messageLine3 = messageLine3;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(messageLine1, 70, 50);
		g.drawString(messageLine2, 70, 70);
		g.drawString(messageLine3, 55, 90);
	}
}