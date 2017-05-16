import java.sql.Connection;
import java.sql.DriverManager;

public class dbcon {
	Connection conn = null;
	String jdbc_driver = "com.mysql.jdbc.Driver";
	String jdbc_url = "jdbc:mysql://localhost:3306/pctector?CharacterEncoding=utf-8";
	
	
	public Connection connect(){
		try{
			Class.forName(jdbc_driver);
			conn= DriverManager.getConnection(jdbc_url,"root", "kgu123");
		}catch(Exception e){
		}
		return conn;
	}
	public void disconnect(){
		try{
			conn.close();
		}catch(Exception e){
		}
	}
}
