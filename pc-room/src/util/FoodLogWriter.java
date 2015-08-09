package util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

public class FoodLogWriter {
	// 음식 판매 로그입력 + 출력
	// 음식 입고 로그입력 + 출력
	// 음식정보수정 로그입력 + 출력

	public static Connection conn = null;
	public static ResultSet rs = null;
	public static PreparedStatement pstmt = null;

	public static void writeLog(int fdNo, String type, String log) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into foodlog ");
		sql.append("values(foodlog_no.nextval, ?, ?, sysdate, ?)");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, fdNo);
			pstmt.setString(2, type);
			pstmt.setString(3, log);
			pstmt.executeUpdate();
			System.out.println("WriteInputLog : 로그기록완료");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("WriteInputLog : 로그기록 오류");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static void readLog(DefaultTableModel model) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fd_name, fl_type, fl_log, fd_stock");
		sql.append("from food natural join foodlog");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fd_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");
				int stock = rs.getInt("fd_stock");

				String[] tableStatement = { date, name, type, log, Integer.toString(stock) };
				model.addRow(tableStatement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("readLog : 로그읽기오류");
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
	}

	public static void readLogByType(DefaultTableModel model, String insertedType) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fd_name, fl_type, fl_log, fd_stock ");
		sql.append("from food natural join foodlog ");
		sql.append("where fl_type = ?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, insertedType);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fd_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");
				int stock = rs.getInt("fd_stock");

				String[] tableStatement = { date, name, type, log, Integer.toString(stock) };
				model.addRow(tableStatement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("readLog : 로그읽기오류");
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
	}

	public static void readLogByFood(DefaultTableModel model, String fdName) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fd_name, fl_type, fl_log, fd_stock ");
		sql.append("from food natural join foodlog ");
		try {
			if (fdName.equals("")) {
				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
			} else {
				sql.append("where fd_name = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, fdName);
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fd_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");
				int stock = rs.getInt("fd_stock");

				String[] tableStatement = { date, name, type, log, Integer.toString(stock) };
				model.addRow(tableStatement);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("readLog : 로그읽기오류");
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
	}

	public static int searchFdNo(String fdName) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fd_no from food ");
		sql.append("where fd_name=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, fdName);
			rs = pstmt.executeQuery();
			int fdNo = -1;
			while (rs.next()) {
				fdNo = rs.getInt("fd_no");
			}
			return fdNo;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("readLog : 로그읽기오류");
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
		return -1;
	}
}
