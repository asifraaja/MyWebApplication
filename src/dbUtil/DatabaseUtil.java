package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseUtil {
	private static Connection con;
	
	public static Connection getConnection(String username, String password, String db_name) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db_name, username, password);
			System.out.println("Database connected");
		}catch(Exception e) {
			System.out.println("database could not be connectd");
			e.printStackTrace();
		}
		return con;
	}
	
}
