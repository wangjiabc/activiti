package org.activiti.manage.context;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBUtils {

	    private static Properties prop = new Properties();  
	    static{  
	        ClassLoader loader = DBUtils.class.getClassLoader();  
	        InputStream in = loader.getResourceAsStream("config.properties");  
	        try {  
	            prop.load(in);  
	            Class.forName(prop.getProperty("driverClassName"));  
	        } catch (Exception e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public static Connection getConnection() throws Exception{  
	        return DriverManager.getConnection(prop.getProperty("jdbc_url"),prop.getProperty("jdbc_username"),prop.getProperty("jdbc_password"));  
	    }  
	      
	    public static void close(Connection conn){  
	        if(conn != null){  
	            try {  
	                conn.close();  
	            } catch (SQLException e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
 
	
}
