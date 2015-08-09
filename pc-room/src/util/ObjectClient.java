package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import client.Member;

public class ObjectClient {

	public static void sendObject(Object sendObj) {
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		BufferedReader br = null;
		Object obj;
		String signal;

		// 테스트용 맴버 객체
		try {
			Socket socketToServer = new Socket("169.254.55.75", 5000);
			System.out.println(socketToServer);
			oos = new ObjectOutputStream(socketToServer.getOutputStream());

			// 클라이언트는 송신은 객체를,수신은 문자를 받으므로 bufferedreader 생성
			br = new BufferedReader(new InputStreamReader(socketToServer.getInputStream()));
			System.out.println("ObjectClient : 접속했으요");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "로그인에 실패하였습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
		}

		try {
			oos.writeObject(sendObj); // 해당 member가 컴퓨터를 시작한다고 객체를 보내자
			oos.flush(); // flush.. 사실 쓸필요 없을지도
			signal = br.readLine();
			if (signal.equals("member")) {
				// 여기에 사용허가기능(로그인 허용같은 기능)
				System.out.println("ObjectClient : 서버 member Signal 받기 성공");
			}
			if (signal.equals("food")) {
				System.out.println("ObjectClient : 서버 food Signal 받기 성공");
			}
			System.out.println("ObjectClient : 객체보내기, 리턴신호 받기 성공");
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
}
