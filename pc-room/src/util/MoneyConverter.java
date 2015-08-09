package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoneyConverter {

	public static Connection conn = null;
	public static ResultSet rs = null;
	public static PreparedStatement pstmt = null;

	public static int basicConvert(int money) {
		// db에서 1000원당 얼마인지 가져온다.
		// money를 1000으로 나누고 곱해주자
		int rateMoney = money / 1000;
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select rate_time ");
		sql.append("from rate ");
		sql.append("where rate_name='기본요금제'");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int rateTime = rs.getInt("rate_time");
				return rateMoney * rateTime;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (rs != null)
					rs.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;

	}
}
