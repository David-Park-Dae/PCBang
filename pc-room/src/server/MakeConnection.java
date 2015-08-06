package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MakeConnection {
	private static String DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static String URL = "jdbc:oracle:thin:@169.254.55.75:1521:orcl";
	private static String ID = "scott";
	private static String PWD = "tiger";
	private static Connection conn;

	private static MakeConnection mc;

	private MakeConnection() {
	}

	public static MakeConnection getInstance() {
		if (mc != null)
			mc = new MakeConnection();
		return mc;
	}

	public static Connection getConnection() {
		try {
			Class.forName(DRIVER);
			conn = DriverManager.getConnection(URL, ID, PWD);
		} catch (ClassNotFoundException e) {
			System.out.println("driver 설정 실패");
		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
		}
		return conn;
	}
}
