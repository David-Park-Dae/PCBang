package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import oracle.net.aso.q;
import util.DBConnection;
import util.FoodLogWriter;

public class FoodHelper {
	DefaultTableModel model;
	static Connection conn;
	StringBuffer sql;
	static PreparedStatement pstmt;
	static ResultSet rs;

	int no;
	int stock;
	String name;
	int price;

	public FoodHelper(DefaultTableModel model) {
		this.model = model;
	}

	public void search(String keyword) {
		model.setNumRows(0);
		conn = DBConnection.getConnection();
		System.out.println("키워드받아옴  : " + keyword);
		sql = new StringBuffer();
		sql.append("select fd_no, fd_name, fd_stock, fd_price ");
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
				price = rs.getInt("fd_price");
				
				String[] tableStatement = { Integer.toString(no), name, Integer.toString(stock), Integer.toString(price) };

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
		conn = DBConnection.getConnection();
		System.out.println("번호받아옴  : " + no);
		sql = new StringBuffer();
		sql.append("select fd_no, fd_name, fd_stock, fd_price ");
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
				price = rs.getInt("fd_price");
				String[] tableStatement = { Integer.toString(no), name, Integer.toString(stock), Integer.toString(price)};

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
		String fdName = FoodLogWriter.searchFdName(no);
		conn = DBConnection.getConnection();
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
			FoodLogWriter.writeLog(fdName, "판매", quantity+"개를 판매");
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

	public void add(String name, int stock, int price) {
		conn = DBConnection.getConnection();
		sql = new StringBuffer();
		sql.append("insert into food ");
		sql.append("values(food_no.nextval, ?, ?, ?)");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, name);
			pstmt.setInt(2, stock);
			pstmt.setInt(3, price);
			pstmt.executeUpdate();
			FoodLogWriter.writeLog(name, "추가", "새로운 매뉴 "+stock+"개를 추가함");
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

	public void edit(String editName, int editStock, int editPrice, String beforeName, String beforeStock, String beforePrice) {
		conn = DBConnection.getConnection();
		sql = new StringBuffer();
		sql.append("update food ");
		sql.append("set fd_name=?, ");
		sql.append("fd_stock=?, ");
		sql.append("fd_price=? ");
		sql.append("where fd_name=?");

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1, editName);
			pstmt.setInt(2, editStock);
			pstmt.setInt(3, editPrice);
			pstmt.setString(4, beforeName);
			pstmt.executeUpdate();
			
			StringBuffer log = new StringBuffer();
			log.append(editName);
			log.append(" 재고:");
			log.append(beforeStock);
			log.append("=>");
			log.append(editStock);
			log.append(" 가격:");
			log.append(beforePrice);
			log.append("=>");
			log.append(editPrice);
			FoodLogWriter.writeLog(beforeName, "수정", log.toString());
			
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
		conn = DBConnection.getConnection();
		String fdName = FoodLogWriter.searchFdName(no);
		System.out.println("delete : search한 fdName" + fdName);
		sql = new StringBuffer();
		sql.append("delete from food ");
		sql.append("where fd_no=?");
		System.out.println("삭제할 번호 : " + no);

		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setInt(1, no);
			pstmt.executeUpdate();
			FoodLogWriter.writeLog(fdName, "삭제", "매뉴에서 삭제됨");
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
		conn = DBConnection.getConnection();
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
			
			StringBuffer log = new StringBuffer();
			log.append(quantity);
			log.append("개가 입고됨");
			FoodLogWriter.writeLog(name, "입고", log.toString());
			
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
		conn = DBConnection.getConnection();
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
	
	//클라이언트에서 매뉴를 만들때
	//이름을 가져오되 가격을 : 로 연결시켜준다.
	//매뉴판에서 그대로 뿌려주고
	//가격을 계산할 땐 :를 기준으로 잘라서 parseInt하여 계산한다.
	public static Vector getNameAndPriceArray() {
		conn = DBConnection.getConnection();
		Vector<String> nameList = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select fd_name, fd_price ");
		sql.append("from food");
		
		try {
			System.out.println("현재 쿼리 : " + sql.toString());
			pstmt = conn.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();
			
			nameList = new Vector<String>();

			while (rs.next()) {
				String name = rs.getString("fd_name");
				String price = Integer.toString(rs.getInt("fd_price"));
				String message = name + ":" + price;
				nameList.add(message);
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
