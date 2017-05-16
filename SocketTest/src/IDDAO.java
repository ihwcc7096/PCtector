

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class IDDAO {
	public boolean checkUser(IDBean idbean){	// 로그인
		try{
			dbcon dao = new dbcon();
			Connection conn = dao.connect();
			ResultSet rs = null;
			Statement stmt = null;
			String sql=null;
			PreparedStatement pstmt = null;
			
			stmt = conn.createStatement();
			sql = "select * from member where id=? and pw=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,idbean.getid());
			pstmt.setString(2,idbean.getpw());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
					IDBean.setfolder(rs.getString("folder"));
					return true;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
	
	public boolean JoinUser(IDBean idbean){
		try{
			if(idbean.getid().trim()==" ")
				return false;
			if(checkUser(idbean))
				return false;
			
			dbcon dao = new dbcon();
			Connection conn = dao.connect();
			ResultSet rs = null;
			Statement stmt = null;
			String sql=null;
			PreparedStatement pstmt = null;
			
			stmt = conn.createStatement();
			sql = "insert into member(id,pw) values(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,idbean.getid());
			pstmt.setString(2,idbean.getpw());
			
			pstmt.executeUpdate();
			return true;
			
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
	public static boolean decideFolder(){	// 파일 지정
		try{
			dbcon dao = new dbcon();
			Connection conn = dao.connect();
			ResultSet rs = null;
			Statement stmt = null;
			String sql=null;
			PreparedStatement pstmt = null;
			stmt = conn.createStatement();
			sql = "update member set folder=? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,IDBean.getfolder());
			System.out.println(IDBean.getfolder());
			pstmt.setString(2,IDBean.getid());
			pstmt.executeUpdate();
			
		}catch(Exception e){
			System.out.println(e);
		}
		return true;
	}
	public boolean receivefolder (IDBean idbean){	// 로그인
		try{
			dbcon dao = new dbcon();
			Connection conn = dao.connect();
			ResultSet rs = null;
			Statement stmt = null;
			String sql=null;
			PreparedStatement pstmt = null;
			
			stmt = conn.createStatement();
			sql = "select * from member where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,idbean.getid());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
					IDBean.setfolder(rs.getString("folder"));
					return true;
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return false;
	}
}
