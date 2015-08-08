package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientBackground implements Runnable {
	private final String SERVER_IP = "127.0.0.1";

	private Thread 				thread;
	
	private Socket 				socket;
	private DataInputStream 	in;
	private DataOutputStream 	out;
	private ClientChatGui	 	gui = null;
	private String 				msg;
	private String 				seatNumber;
	
	private static ClientBackground 	clientBackground = new ClientBackground();
	
	private ClientBackground() {}
	
	public static ClientBackground getInstance() {
		if(clientBackground == null) clientBackground = new ClientBackground();
		return clientBackground;
	}

	public final void setGui(ClientChatGui gui) {
		this.gui = gui;
	}

	public void connet(String seatNumber) {
		this.seatNumber = seatNumber;
		thread = new Thread(this);
		thread.start();
	}

	public void sendMessage(String msg2) {
		try {
			out.writeUTF("C"+this.seatNumber+msg2);
			gui.appendMsg(msg2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			socket = new Socket(SERVER_IP, 5001);
			System.out.println("서버와 채팅서버 연결 완료 구동중 ...");
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			//접속하자마자 자리번호를 전송
			out.writeUTF("CS"+seatNumber); 
			while(in!=null){
				msg=in.readUTF();
				System.out.println(msg);
				if(msg.equals("New\n")) {
					gui.appendMsg(msg);
				} else {
					gui.appendMsg(msg);
					gui.setVisible(true);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
