package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ObjectClient {
	Socket socketToServer;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	InputStreamReader isr;
	BufferedReader br;
	Object obj;
	Member mb;
	String signal;

	public void connectServer() {
		//테스트용 맴버 객체
		Member mb = new Member("20", "kht", "Kim", 120);
		try {
			socketToServer = new Socket("169.254.55.75", 5000);
			System.out.println(socketToServer);
			oos = new ObjectOutputStream(socketToServer.getOutputStream());
			ois = new ObjectInputStream(socketToServer.getInputStream());
			
			//클라이언트는 송신은 객체를,수신은 문자를 받으므로 bufferedreader 생성
			br = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
			System.out.println("client : 접속했으요");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			oos.writeObject(mb); //해당 member가 컴퓨터를 시작한다고 객체를 보내자
			oos.flush(); // flush.. 사실 쓸필요 없을지도
			while ((signal = br.readLine()) != null) { //보내고나서 서버의 ok메세지를 기다린다.
				if (signal.equals("member")) {
					System.out.println("나를 봐주셨어!!");
					// 여기에 사용허가기능(로그인 허용같은 기능)
				}else{
					System.out.println("아니야 나를봤어!");
				}
			}
			System.out.println("client : 객체보내기, 메세지 받기 성공");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ObjectClient oc = new ObjectClient();
		oc.connectServer();
	}
}
