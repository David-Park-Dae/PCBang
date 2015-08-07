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

	public final void setGui(ClientChatGui gui) {
		this.gui = gui;
	}

	public void connet() {
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

	public void setSeatNumber(String seatNumber) throws IOException {
		this.seatNumber = seatNumber;
		out.writeUTF("CN"+this.seatNumber);
	}

	@Override
	public void run() {
		try {
			socket = new Socket(SERVER_IP, 5000);
			
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			//접속하자마자 자리번호를 전송
			out.writeUTF("CS"+seatNumber); 
			while(in!=null){
				msg=in.readUTF();
				System.out.println(msg);
				
				if(gui == null && msg.equals("New")) {
					new ClientChatGui(seatNumber);
				} else if(gui != null) {
					gui.appendMsg(msg);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
