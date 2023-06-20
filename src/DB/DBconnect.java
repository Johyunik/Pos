package DB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnect {
	
	public static final String dbDriver = "com.mysql.cj.jdbc.Driver";
	public static final String dbUrl = "jdbc:mysql://localhost:3306/pos?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";
	public static final String dbUser = "root";
	public static final String dbPwd = "1234";
	public static Connection conn = null;
	
	public DBconnect() {
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		connect();	
		close();
	}
	
	public static Connection connect() {
		
		try {
			Class.forName(dbDriver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
			if(conn != null) {
				System.out.println("Connection Success!");
			} else {
				System.out.println("Connection Failed");
			}
			 
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("JDBC 드라이버 로드 오류!");
		} catch (Exception e) {
			System.out.println("DB 연결 오류!");
		}		
		
		return conn;
	}
	
	public static void close() {
		
		if(conn != null) {
			System.out.println("DB 연결 해제 중~~~!");
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("DB 연결 해제 오류!");
			}
		}
		
	}
	
}
