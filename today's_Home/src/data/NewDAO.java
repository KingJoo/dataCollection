package data;

import java.sql.*;

public class NewDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL="jdbc:oracle:thin:@211.238.142.186:1521:XE";
	
	public NewDAO() {
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
	
	public void dataInsert(NewVO vo) {
		try {
			getConnection();
			//no,img,title,cmt,regdate,score,price,reviewCnt
			String sql="INSERT INTO new_item(no,cate1,cate2,img,title,cmt,regdate,score,price,reviewCnt) "
					+ "VALUES(?,?,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vo.getNo());
			ps.setString(2, vo.getCate1());
			ps.setString(3, vo.getCate2());
			ps.setString(4, vo.getImg());
			ps.setString(5, vo.getTitle());
			ps.setString(6, vo.getCmt());
			ps.setString(7, vo.getRegdate());
			ps.setDouble(8, vo.getScore());
			ps.setString(9, vo.getPrice());
			ps.setInt(10, vo.getReviewCnt());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			disConnection();
		}
	}
}

