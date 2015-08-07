package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

//클라이언트 서버는 충전과 접속종료 신호를 받는 서버다.
public class SignalWaiter {
	ServerSocket sc;
	BufferedReader br;
	BufferedWriter bw;
	InetAddress inet;
	String serverIp;
	String welcome;
	Socket socketToManager;

	public SignalWaiter() {
		try {
			sc = new ServerSocket(6000);
			while (true) {
				System.out.println("서버신호를 기다리는중 ...");
				socketToManager = sc.accept();
				inet = socketToManager.getInetAddress();
				serverIp = inet.getHostAddress();
				System.out.println(serverIp + "님이 접속");

				br = new BufferedReader(new InputStreamReader(socketToManager.getInputStream()));
				bw = new BufferedWriter(new OutputStreamWriter(socketToManager.getOutputStream()));
				String message;
				System.out.println("SignalWaiter : 메세지 받기 전");
				while ((message = br.readLine()) != null) {
					System.out.println(message);
					System.out.println("SignalWaiter : while문 안쪽");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(sc!=null)sc.close();
				if(br!=null)br.close();
				if(bw!=null)bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new SignalWaiter();
	}
}
