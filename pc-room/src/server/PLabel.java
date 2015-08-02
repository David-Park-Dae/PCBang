package server;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PLabel extends JLabel{
	private ImageIcon imgIcon;
	private String message;
	
	public ImageIcon getImgIcon() {
		return imgIcon;
	}

	public void setImgIcon(ImageIcon img) {
		this.imgIcon = img;
	}

	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public PLabel()
	{
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(message, 30, 50);
	}
}