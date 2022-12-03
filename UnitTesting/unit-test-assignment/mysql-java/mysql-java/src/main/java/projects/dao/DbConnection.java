package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import projects.exception.DbException;

public class DbConnection {
	@SuppressWarnings("unused")
	private static String HOST = "localhost";
	@SuppressWarnings("unused")
	private static String PASSWORD = "projects";
	@SuppressWarnings("unused")
	private static int PORT = 3306;
	@SuppressWarnings("unused")
	private static String SCHEMA = "projects";
	@SuppressWarnings("unused")
	private static String USER = "projects";
	
	public static Connection getConnection() {
		String uri = 
				String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST, PORT, SCHEMA, USER, PASSWORD);
		
		System.out.println("Connecting with uri=" + uri);
		
		try {
			Connection conn = DriverManager.getConnection(uri);
			System.out.println("Connection to schema '" +SCHEMA+ "' is successful.");
			return conn;
		} catch (SQLException e) {
			System.out.println("Unable to get connection at " + uri);
			throw new DbException("Unable to get connection at \" + uri");
			
		}
		
		
		
	}
}
