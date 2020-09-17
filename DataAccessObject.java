package org.bridge.address;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataAccessObject 
{
	protected static Connection dataBase()
	{
		String url="jdbc:mysql://localhost:3306?user=root&password=12345";
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection=DriverManager.getConnection(url);
			return connection;
		
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	
	}
}
