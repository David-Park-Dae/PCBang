package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import client.LoginFrame;
import client.MainFrame;
import client.Member;

public class SqlUtil {
	
	public static Connection conn = 		null;
	public static ResultSet rs = 			null;
	public static PreparedStatement pstmt = null;
	
	public static boolean flag = true;
	
	private static Member loginUser;
	
	// 로그인 sql 처리
	public static void login(LoginFrame lf, String inputId, String inputPasswd) {
		conn = DBConnection.getConnection();
		String sql = "SELECT mb_pwd, mb_resttime FROM member WHERE mb_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			rs = pstmt.executeQuery();
			
			// 값이 있을 경우에만 실행
			if(rs.next() == true) {
				long restTime = rs.getLong("mb_resttime");
				String passwd = rs.getString("mb_pwd");
				if(passwd.equals(inputPasswd))  {
					
					// 로그인한 유저의 정보를 담는다.
					loginUser = new Member(inputId,passwd,restTime);
					
					// 유저의 남은 시간을 검사 후
					// 없을 경우 로그인하지 못하게 막음.
					if(loginUser.getRestTime() == 0) {
						JOptionPane.showMessageDialog(null, "충전 후 이용해주세요.");
					} else {
						new MainFrame(loginUser);	// 유저정보를 메인 프레임에 넘긴다. ( 서버한테도 보내야함 )
						lf.dispose();	// 로그인프레임 종료 시킨다.
					}
				} else {
					JOptionPane.showMessageDialog(null, "아이디와 비밀번호를 확인해주세요.");
				}
			} else { // 값이 없을 경우는 회원정보가 없는것을 뜻함
				JOptionPane.showMessageDialog(null, "회원정보가 없습니다.");
			}
			
			System.out.println("sql 전송 완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "서버와 연결이 끊겼습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		sqlExit();
	} // 로그인 끝

	// 중복검사 처리
	public static boolean chkId(String inputId) {
		conn = DBConnection.getConnection();
		String sql = "SELECT mb_no FROM member WHERE mb_id=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, inputId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) flag = true;
			else flag = false;
			
			System.out.println("sql 전송 완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "서버와 연결이 끊겼습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		sqlExit();
		return flag;
	} // 끝
	
	// 회원가입 처리
	public static boolean signUp(String name, String username, String passwd) {
		conn = DBConnection.getConnection();
		String sql = "INSERT INTO member VALUES(member_no.nextval,?,?,?,0)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, username);
			pstmt.setString(3, passwd);
			rs = pstmt.executeQuery();
			flag = true;
			System.out.println("sql 전송 완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "서버와 연결이 끊겼습니다.");
			e.printStackTrace();
			System.exit(0);
		}
		sqlExit();
		return flag;
	} // 회원가입 끝
	
	// 자원 정리
	public static void sqlExit() {
		try {
			conn.close();
			rs.close();
			System.out.println("DB / ResultSet 해제");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // 자원 정리 끝
}
