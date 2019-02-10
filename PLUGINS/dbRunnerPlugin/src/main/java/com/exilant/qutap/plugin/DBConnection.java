package com.exilant.qutap.plugin;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection extends DBAction {

	String mesg;
	
	public String getConnection(String url, String username, String password, String action) throws ClassNotFoundException, SQLException {
		if (action.equalsIgnoreCase("mysqlconnection")){
		
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection(url, username, password);
				stmt = con.createStatement();
				mesg="Conected to mysql";			
		}
		else 
			if (action.equalsIgnoreCase("oracleconnection")){			
					Class.forName("oracle.jdbc.driver.OracleDriver");
					con = DriverManager.getConnection(url, username, password);
					stmt = con.createStatement();
					mesg="Conected to oracle";				
			}
		return mesg;
			
	}
}
