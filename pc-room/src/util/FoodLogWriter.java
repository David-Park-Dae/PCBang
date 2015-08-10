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

	public static void writeLog(String fdName, String type, String log) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("insert into foodlog ");
		sql.append("values(foodlog_no.nextval, ?, ?, sysdate, ?)");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, fdName);
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
		model.setNumRows(0);
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fl_name, fl_type, fl_log ");
		sql.append("from foodlog");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fl_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");

				String[] tableStatement = { date, name, type, log };
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
		model.setNumRows(0);
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fl_name, fl_type, fl_log ");
		sql.append("from foodlog ");
		System.out.println("FoodWriter>>검색할 타입 :" + insertedType);

		try {
			if (insertedType.equals("입고"))
				sql.append("where fl_type = '입고'");
			if (insertedType.equals("판매"))
				sql.append("where fl_type = '판매'");
			if (insertedType.equals("추가"))
				sql.append("where fl_type = '추가'");
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fl_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");
				System.out.println("FoodWriter>>" + date + name + type + log);
				String[] tableStatement = { date, name, type, log };
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
		model.setNumRows(0);
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fl_date, fl_name, fl_type, fl_log ");
		sql.append("from foodlog ");
		try {
			if (fdName.equals("")) {
				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
			} else {
				sql.append("where fl_name = ?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, fdName);
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				// jtable의 model을 매개변수로 받고 출력하자
				String date = rs.getString("fl_date");
				String name = rs.getString("fl_name");
				String type = rs.getString("fl_type");
				String log = rs.getString("fl_log");

				String[] tableStatement = { date, name, type, log };
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

	public static String searchFdName(int fdNo) {
		conn = DBConnection.getConnection();
		StringBuffer sql = new StringBuffer();
		sql.append("select fd_name from food ");
		sql.append("where fd_no=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, fdNo);
			rs = pstmt.executeQuery();
			String fdName = null;
			while (rs.next()) {
				fdName = rs.getString("fd_name");
			}
			return fdName;
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
		return null;
	}
}
