package com.dorm.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.testng.log4testng.Logger;

import sql.SQLConnect;

public class SQLConnectTest {
	
	private static Logger log = Logger.getLogger(SQLConnectTest.class);
	
	/**
	 * 数据库驱动类名称
	 */
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	
	/**
	 * 连接字符串
	 */
	private static final String URLSTR = "jdbc:mysql://db.59temai.com:3306/";
	
	/**
	 * 用户名
	 */
	private static final String USERNAME = "admin";
	
	/**
	 * 密码
	 */
	private static final String PASSWORD = "admin";
	
	/**
	 * 数据库连接对象
	 */
	private static Connection connection = null;
	
	/**
	 * ResultSet对象
	 */
	private static ResultSet resultSet = null;
	
	/**
	 * PreparedStatement对象
	 */
	private static PreparedStatement preparedStatement = null;
	
	static {
		
		try {
			//加载数据库驱动程序
			Class.forName(DRIVER);
			
		} catch (Exception e) {
			log.error("加载数据库驱动程序错误" + e.getMessage(),e);
		}
		
	}
	
	/**
	 * 建立数据库连接
	 * @param dataBaseName
	 * @return Connection conn
	 */
	public static Connection getConnection(String dataBaseName) {
		
		try {
			connection = DriverManager.getConnection(URLSTR + dataBaseName, USERNAME, PASSWORD);
		} catch (SQLException e) {
			log.error("数据库连接错误" + e.getMessage(), e);
		}
		
		return connection;
	}
	
	private static ResultSet executeQueryRS(String sql, String dataBaseName, Object[] params) {
		
		try {
			//获得连接
			connection = getConnection(dataBaseName);
			//调用sql
			preparedStatement = connection.prepareStatement(sql);
			
			if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }   
			
			//执行
			resultSet = preparedStatement.executeQuery();
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			
		}
		
		return resultSet;
	}
	
	
	public static void main(String[] args) {
		
		String sql = "select * from 59_user where uid > 7000 and uid <= 7008";
		
		SQLConnect mysql = new SQLConnect();
		
		List<Object> list = mysql.excuteQuery(sql, "db59store_qa");
		
		Map<String, Object> param = null;
		
		for(Object obj:list) {
			param = ((Map<String, Object>) obj);

			System.out.println("uid:" + param.get("uid") + " -- " + "uname:" + param.get("uname"));
		}
		
	}

}
