package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Member {
	private int no;
	private String name;
	private String id;
	private String password;
	private int restTime;
	private Connection conn;
	private DefaultTableModel model;

	PreparedStatement pstmt = null;
	ResultSet rs = null;
	StringBuffer sql;

	Member(DefaultTableModel model) {
		this.model = model;
	}

	public void search(String keyword) {
		model.setNumRows(0);
		conn = MakeConnection.getConnection();
		System.out.println("키워드받아옴  : " + keyword);
		sql = new StringBuffer();
		sql.append("select mb_name, mb_id, mb_resttime ");
		sql.append("from member ");

		try {
			if (keyword.equals("")) {
				System.out.println("아무것도 입력하지 않음");
				System.out.println("현재 쿼리 : " + sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
			} else {
				sql.append("where mb_name=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, keyword);
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				name = rs.getString("mb_name");
				id = rs.getString("mb_id");
				restTime = rs.getInt("mb_resttime");
				String[] tableStatement = { name, id, Integer.toString(restTime) };

				for (String i : tableStatement) {
					System.out.print(i + "  ");
				}
				model.addRow(tableStatement);
			}
		} catch (SQLException e1) {
			System.out.println("search query 오류");
		} finally {
			try {
				if (rs != null)
					rs.close();
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

	public void searchById(String id) {
		model.setNumRows(0);
		conn = MakeConnection.getConnection();
		System.out.println("키워드받아옴  : " + id);
		sql = new StringBuffer();
		sql.append("select mb_name, mb_id, mb_resttime ");
		sql.append("from member ");
		sql.append("where mb_id=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				name = rs.getString("mb_name");
				id = rs.getString("mb_id");
				restTime = rs.getInt("mb_resttime");
				String[] tableStatement = { name, id, Integer.toString(restTime) };

				for (String i : tableStatement) {
					System.out.print(i + "  ");
				}
				model.addRow(tableStatement);
			}
		} catch (SQLException e1) {
			System.out.println("search query 오류");
		} finally {
			try {
				if (rs != null)
					rs.close();
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

	public void delete(String id) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("delete from member ");
		sql.append("where mb_id=?");
		System.out.println("삭제할 아이디 : " + id);

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
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
		model.setNumRows(0);
		search("");
	}

	public void edit(String editName, String editId, String beforeId) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("update member ");
		sql.append("set mb_name=?, ");
		sql.append("mb_id=? ");
		sql.append("where mb_id=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, editName);
			pstmt.setString(2, editId);
			pstmt.setString(3, beforeId);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "수정이 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
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
		model.setNumRows(0);
		searchById(editId);
	}

	public void charge(int chargeTime, String id) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("update member ");
		sql.append("set mb_resttime=?+ ");
		sql.append("(select mb_resttime from member where mb_id=?) ");
		sql.append("where mb_id=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, chargeTime);
			pstmt.setString(2, id);
			pstmt.setString(3, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.setNumRows(0);
		searchById(id);
	}

	public void pwdReset() {

	}
}
