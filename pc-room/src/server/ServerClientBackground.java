package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerClientBackground implements Runnable{
	private final String SERVER_IP = "127.0.0.1";

	private Thread 				thread;
	private Socket 				socket;
	private DataInputStream 	in;
	private DataOutputStream 	out;
	private ServerChatGui	 	gui;
	private String 				msg;
	private String				seatNumber;

	public final void setGui(ServerChatGui gui) {
		this.gui = gui;
	}

	public void connet(String seatNumber) {
		this.seatNumber = seatNumber;
		thread = new Thread(this);
		thread.start();
	}

	public void sendMessage(String msg2) {
		try {
			out.writeUTF("S"+this.seatNumber+msg2);
			gui.appendMsg(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	@Override
	public void run() {
		try {
			socket = new Socket(SERVER_IP, 5000);
			System.out.println("[Server Client ("+seatNumber+") ] : 서버 연결됨.");
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			out.writeUTF("SN"+seatNumber);
			while(in!=null){
				msg=in.readUTF();
				System.out.println(msg);
				gui.appendMsg(msg);				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
