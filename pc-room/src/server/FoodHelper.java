package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FoodHelper {
	DefaultTableModel model;
	Connection conn;
	StringBuffer sql;
	PreparedStatement pstmt;
	ResultSet rs;

	int no;
	int stock;
	String name;

	public FoodHelper(DefaultTableModel model) {
		this.model = model;
	}

	public void search(String keyword) {
		model.setNumRows(0);
		conn = MakeConnection.getConnection();
		System.out.println("키워드받아옴  : " + keyword);
		sql = new StringBuffer();
		sql.append("select fd_no, fd_name, fd_stock ");
		sql.append("from food ");

		try {
			if (keyword.equals("")) {
				System.out.println("아무것도 입력하지 않음");
				System.out.println("현재 쿼리 : " + sql.toString());
				pstmt = conn.prepareStatement(sql.toString());
				rs = pstmt.executeQuery();
			} else {
				sql.append("where fd_name=?");
				pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1, keyword);
				rs = pstmt.executeQuery();
			}

			while (rs.next()) {
				no = rs.getInt("fd_no");
				name = rs.getString("fd_name");
				stock = rs.getInt("fd_stock");
				String[] tableStatement = { Integer.toString(no), name, Integer.toString(stock) };

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

	public void searchNo(int no) {
		model.setNumRows(0);
		conn = MakeConnection.getConnection();
		System.out.println("번호받아옴  : " + no);
		sql = new StringBuffer();
		sql.append("select fd_no, fd_name, fd_stock ");
		sql.append("from food ");
		sql.append("where fd_no=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				no = rs.getInt("fd_no");
				name = rs.getString("fd_name");
				stock = rs.getInt("fd_stock");
				String[] tableStatement = { Integer.toString(no), name, Integer.toString(stock) };

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

	public void sell(int no, int quantity) {
		conn = MakeConnection.getConnection();
		System.out.println("번호받아옴  : " + no);
		sql = new StringBuffer();
		sql.append("update food ");
		sql.append("set fd_stock=");
		sql.append("(select fd_stock from food where fd_no=?)-? ");
		sql.append("where fd_no=? ");
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.setInt(2, quantity);
			pstmt.setInt(3, no);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "판매가 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
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
		search("");
	}

	public void add(String name, int stock) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("insert into food ");
		sql.append("values(food_no.nextval, ?, ?)");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setInt(2, stock);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "추가가 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
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
		search(name);
	}

	public void edit(String editName, int editStock, String beforeName) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("update food ");
		sql.append("set fd_name=?, ");
		sql.append("fd_stock=? ");
		sql.append("where fd_name=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, editName);
			pstmt.setInt(2, editStock);
			pstmt.setString(3, beforeName);
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
		search(editName);
	}

	public void delete(int no) {
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("delete from food ");
		sql.append("where fd_no=?");
		System.out.println("삭제할 번호 : " + no);

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
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

	public void input(String name, int quantity) {
		System.out.println("input넘어온 값들 " +name+"  "+quantity);
		conn = MakeConnection.getConnection();
		sql = new StringBuffer();
		sql.append("update food ");
		sql.append("set fd_stock=");
		sql.append("(select fd_stock from food where fd_name=?) +? ");
		sql.append("where fd_name=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setInt(2, quantity);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "입고가 완료되었습니다", "알림", JOptionPane.INFORMATION_MESSAGE);
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
		search(name);
	}

	public Vector getNameArray() {
		conn = MakeConnection.getConnection();
		Vector<String> nameList = null;
		sql = new StringBuffer();
		sql.append("select fd_name ");
		sql.append("from food");
		
		try {
			System.out.println("현재 쿼리 : " + sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			nameList = new Vector<String>();

			while (rs.next()) {
				name = rs.getString("fd_name");
				nameList.add(name);
			}
			return nameList;
		} catch (SQLException e1) {
			nameList.clear();
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
		return nameList;
	}
}
