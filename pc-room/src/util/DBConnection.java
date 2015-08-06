package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String URL = "jdbc:oracle:thin:@128.199.195.9:1521:orcl";
	private static final String USER = "oracle";
	private static final String PASSWD = "asd789";
	private static Connection conn = null;
	
	private static DBConnection dc = new DBConnection();
	
	private DBConnection(){}
	
	public static DBConnection getInstance() {
		if(dc==null) dc = new DBConnection();
		return dc;
	}
	
	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL,USER,PASSWD);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			System.out.println("db 연결 실패");
			e.printStackTrace();
		} finally {
			System.out.println("DB 연결 완료");
		}
		return conn;
	}
}