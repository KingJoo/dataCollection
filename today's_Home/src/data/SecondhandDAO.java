package data;

import java.sql.*;

public class SecondhandDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.238.142.186:1521:XE";
	
	public SecondhandDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getConnection() {
		try {
			conn=DriverManager.getConnection(URL,"hr","happy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void disConnection() {
		try {
			if(ps!=null)	ps.close();
			if(conn!=null)	conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void imgInsert(String s,int no) {
		try {
			getConnection();
			String sql="UPDATE secondhand_item SET img=? WHERE no=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, s);
			ps.setInt(2, no);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
	}
	
	public void dataInsert(SecondhandVO vo) {
		try {
			getConnection();
			//img,title,comment,price,cate1,cate2,cate3,c1Num,c2Num,c3Num;
			String sql="INSERT INTO secondhand_item(no,img,title,cmt,price,cate1,cate2,cate3,c1Num,c2Num,c3Num) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ps.setString(2, vo.getImg());
			ps.setString(3, vo.getTitle());
			ps.setString(4, vo.getCmt());
			ps.setString(5, vo.getPrice());
			ps.setString(6, vo.getCate1());
			ps.setString(7, vo.getCate2());
			ps.setString(8, vo.getCate3());
			ps.setInt(9, vo.getC1Num());
			ps.setInt(10, vo.getC2Num());
			ps.setInt(11, vo.getC3Num());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
}

