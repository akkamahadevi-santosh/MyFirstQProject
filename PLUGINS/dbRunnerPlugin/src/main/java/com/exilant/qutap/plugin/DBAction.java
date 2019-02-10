package com.exilant.qutap.plugin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public abstract class DBAction {
	
	Connection con;
	Statement stmt;
	String error="";
	Map<String,String> loadValue=new HashMap<String,String>();
	Map<String,String> selectedValue=new HashMap<String,String>();
	abstract public String getConnection(String url,String username,String password,String action) throws ClassNotFoundException, SQLException;
			
	public int executeUpdate(String query) throws SQLException{		
			int DMLint=stmt.executeUpdate(query);
			return DMLint;			
	}	
	public  Map<String, String> execute(String query,String action) throws SQLException{
	
		if(action.equalsIgnoreCase("select")){		
			selectedValue.clear();	    
			ResultSet rs=stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNumber = rsmd.getColumnCount();	
		while (rs.next()){						
			for (int i = 1; i <=columnNumber; i++) {
				String value=rs.getString(i);
				String colname=rsmd.getColumnName(i);
				this.selectedValue.put(colname, value);
			}
		}
		rs.close();
		return selectedValue;
		}
		else if(action.equalsIgnoreCase("loadTable")){	    
			ResultSet rs=stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnNumber = rsmd.getColumnCount();
		while (rs.next()){						
			for (int i = 1; i <=columnNumber; i++) {
				String value=rs.getString(i);
				String colname=rsmd.getColumnName(i);
				this.loadValue.put(colname, value);
			}
		}
		return loadValue;
		}
		else{
			return null;
		}	
	}	
}
