package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ServerBackground implements Runnable {

	private static ServerBackground serverBackground = new ServerBackground();
	
	private ServerSocket serverSocket;
	private Socket socket;
	private String msg;
	public  String seatNumber;
	public  int    chkFlag;
	
	/** 사용자의 정보를 저장하는 맵 */
	private Map<String, DataOutputStream> clientsServerMap 	= new HashMap<String, DataOutputStream>();
	
	private Map<String, DataOutputStream> clientsMap 		= new HashMap<String, DataOutputStream>();
	private Map<String, DataOutputStream> serversMap 		= new HashMap<String, DataOutputStream>();
	
	private ServerBackground() {}
	
	public static ServerBackground getInstance() {
		if(serverBackground == null) serverBackground = new ServerBackground();
		return serverBackground;
	}
	@Override
	public void run() {
		// 1. 사용자정보가 들어갈 해쉬맵 정리
		Collections.synchronizedMap(clientsMap);
		Collections.synchronizedMap(serversMap);
		try {
			serverSocket = new ServerSocket(5000);
			// 2. 서버 초기화
			while (true) {
				System.out.println("채팅서버 초기화 완료.");
				// 3. 접속 대기
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress() + "에서 접속했습니다.");
				// 접속한 사용자의 쓰레드 생성.
				Receiver receiver = new Receiver(socket);
				receiver.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 클라이언트 저장  삭제
	public void addClientChatInfo(String seatNumber, DataOutputStream out) throws IOException {
		System.out.println(seatNumber + "저장 완료 [ 클라이언트 ]");
		clientsMap.put(seatNumber, out);
	}

	public void removeClientChatInfo(String seatNumber) {
		clientsMap.remove(seatNumber);
	}
	
	// 서버클라이언트 저장  삭제
	public void addServerChatInfo(String seatNumber, DataOutputStream out) throws IOException {
		System.out.println(seatNumber + "저장 완료 [ 서버 ]");
		serversMap.put(seatNumber, out);
	}

	public void removeServerChatInfo(String seatNumber) {
		serversMap.remove(seatNumber);
	}
	

	// 메시지 내용 전달
	public void sendClientMessage(String msg, String seatNumber) throws IOException {
		System.out.println("sendClientMessage HEAD : " +seatNumber);
		System.out.println("sendClientMessage BODY : " +msg);
		
		clientsMap.get(seatNumber).writeUTF(msg);
	}
	
	// 메시지 내용 전달
	public void sendServerMessage(String msg, String seatNumber) throws IOException {
		System.out.println("sendServerMessage HEAD : " +seatNumber);
		System.out.println("sendServerMessage BODY : " +msg);
		
		serversMap.get(seatNumber).writeUTF(msg);
	}

	/* -------------------------------------------------- */
	class Receiver extends Thread {
		private DataInputStream in;
		private DataOutputStream out;
		private String seatNumber;

		// 접속자의 정보를 맵에 추가
		public Receiver(Socket socket) throws IOException {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
			
			seatNumber = in.readUTF();
			
			String seatNumberHead = seatNumber.substring(0,2);
			System.out.println("seatNumberHead : "+seatNumberHead);
			
			String seatNumberBody = seatNumber.substring(2);
			System.out.println("seatNumberBody : "+seatNumberBody);
			
			DataOutputStream chkClient = clientsMap.get(seatNumberBody);
			DataOutputStream chkServer = serversMap.get(seatNumberBody);
			
			// 클라이언트가 로그인 하자마자 채팅서버 설정
			if(seatNumberHead.equals("CS")) {
				addClientChatInfo(seatNumberBody,out);
			}

			if(seatNumberHead.equals("CN")) {
				if(chkServer == null) {
					new ServerChatGui(seatNumberBody);
				}
			} else if(seatNumberHead.equals("SN")) {
				if(chkServer == null) {
					addServerChatInfo(seatNumberBody, out);
					clientsMap.get(seatNumberBody).writeUTF("New");
				}
			}
		}

		public void run() {
			try {// 받은 메세지를 서버 채팅 클라이언트와 사용자 클라이언트에 표시
				while (in != null) {
					msg = in.readUTF();
					String msgCode = msg.substring(0,1);
					String msgSeatNumber = msg.substring(1,3);
					String msgs = msg.substring(3);
					if(msgCode.equals("C")) {
						sendServerMessage(msgs,msgSeatNumber);
					} else if(msgCode.equals("S")) {
						sendClientMessage(msgs,msgSeatNumber);
					}
				}
			} catch (IOException e) {
				// 클라이언트 접속 사용자가 그냥 종료 할 시 IOException 발생
				// 발생 시 클라이언트 정보를 지운다.
				removeClientChatInfo(seatNumber);
			}
		}
	}
}
