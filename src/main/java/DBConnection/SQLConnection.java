package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class SQLConnection {
	private Connection con;
	private String host="localhost";
	private String port="3306";
	private String dbname="EADCheck";
	private String uname="root";
	private String password="Accurate@123";
	private static Logger log = LogManager.getLogger(SQLConnection.class.getName());
	
	public void getConnection() {
		try {
			con= DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname,uname,password);
			Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select  * from field_ofc limit 1");
			while(rs.next()) {
				System.out.println(rs.getString("f_ofc"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ResultSet executeQuery(String query) {
		ResultSet rs = null;
		try {
			con= DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname,uname,password);
			Statement s = con.createStatement();
			 rs = s.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString("f_ofc"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}
	
	public void executeQuerynoRS(String query) {
		try {
			con= DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname,uname,password);
			Statement s = con.createStatement();
			 s.executeUpdate(query);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
			
		}

	}

}
