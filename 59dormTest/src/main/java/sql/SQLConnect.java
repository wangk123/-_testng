package sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class SQLConnect {
	
	private static Logger log = Logger.getLogger(SQLConnect.class);

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
    private static final String USERPASSWORD = "admin";  
  
    /** 
     * 创建数据库连接对象 
     */  
    private Connection connnection = null;  
  
    /** 
     * 创建PreparedStatement对象 
     */  
    private PreparedStatement preparedStatement = null;  
      
    /** 
     * 创建CallableStatement对象 
     */  
    private CallableStatement callableStatement = null;  
  
    /** 
     * 创建结果集对象 
     */  
    private ResultSet resultSet = null;  
	
    static {  
        try {  
            // 加载数据库驱动程序  
            Class.forName(DRIVER);  
        } catch (ClassNotFoundException e) {  
        	log.error("加载驱动错误" + e.getMessage(),e); 
        }  
    }  
  
    /** 
     * 建立数据库连接 
     * @return 数据库连接 
     */  
    public Connection getConnection(String dataBaseName) {  
        try {  
            // 获取连接  
            connnection = DriverManager.getConnection(URLSTR + dataBaseName, USERNAME,  
                    USERPASSWORD);  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        }  
        return connnection;  
    }  
  
    /** 
     * insert update delete SQL语句的执行的统一方法 （动态赋值）
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 受影响的行数 
     */  
    public int executeUpdate(String sql, String dataBaseName, Object[] params) {  
        // 受影响的行数  
        int affectedLine = 0;  
          
        try {  
            // 获得连接  
            connnection = this.getConnection(dataBaseName);  
            // 调用SQL   
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            affectedLine = preparedStatement.executeUpdate();  
  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        } finally {  
            // 释放资源  
            closeAll();  
        }  
        return affectedLine;  
    }  
  
    /** 
     * SQL 查询将查询结果直接放入ResultSet中 (动态赋值)
     * @param sql SQL语句 
     * @param params 参数数组，若没有参数则为null 
     * @return 结果集 
     */  
    private ResultSet executeQueryRS(String sql, String dataBaseName, Object[] params) {  
        try {  
            // 获得连接  
            connnection = this.getConnection(dataBaseName);  
              
            // 调用SQL  
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 参数赋值  
            if (params != null) {  
                for (int i = 0; i < params.length; i++) {  
                    preparedStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 执行  
            resultSet = preparedStatement.executeQuery();  
  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        }  
  
        return resultSet;  
    }  
  
    /**
     * SQL 查询将查询结果放入List中 (动态赋值)
     * @param sql
     * @param dataBaseName
     * @param params
     * @return List 存放多个map对象（每一行数据存放到一个map对象中，可通过列名取出对应的值）
     */
    public List<Object> excuteQuery(String sql, String dataBaseName, Object[] params) {  
        // 执行SQL获得结果集  
        ResultSet rs = executeQueryRS(sql, dataBaseName, params);  
          
        // 创建ResultSetMetaData对象  
        ResultSetMetaData rsmd = null;  
          
        // 结果集列数  
        int columnCount = 0;  
        try {  
            rsmd = rs.getMetaData();  
              
            // 获得结果集列数  
            columnCount = rsmd.getColumnCount();  
        } catch (SQLException e1) {  
        	log.error(e1.getMessage(),e1);   
        }  
  
        // 创建List  
        List<Object> list = new ArrayList<Object>();  
  
        try {  
            // 将ResultSet的结果保存到List中  
            while (rs.next()) {  
                Map<String, Object> map = new HashMap<String, Object>();  
                for (int i = 1; i <= columnCount; i++) {  
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));  
                }  
                list.add(map);  
            }  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e);   
        } finally {  
            // 关闭所有资源  
            closeAll();  
        }  
  
        return list;  
    }  
    
    /**
     * insert update delete SQL语句的执行的统一方法(非动态赋值)
     * @param sql sql语句
     * @param dataBaseName 库名
     * @return
     */
    public int executeUpdate(String sql, String dataBaseName) {  
        // 受影响的行数  
        int affectedLine = 0;  
          
        try {  
            // 获得连接  
            connnection = this.getConnection(dataBaseName);  
            // 调用SQL   
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 执行  
            affectedLine = preparedStatement.executeUpdate();  
  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        } finally {  
            // 释放资源  
            closeAll();  
        }  
        return affectedLine;  
    }  
  
    /**
     * SQL 查询将查询结果直接放入ResultSet中 (非动态赋值)
     * @param sql
     * @param dataBaseName
     * @return
     */
    private ResultSet executeQueryRS(String sql, String dataBaseName) {  
        try {  
            // 获得连接  
            connnection = this.getConnection(dataBaseName);  
              
            // 调用SQL  
            preparedStatement = connnection.prepareStatement(sql);  
              
            // 执行  
            resultSet = preparedStatement.executeQuery();  
  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        }  
  
        return resultSet;  
    }  
  
    /**
     * SQL 查询将查询结果放入List中 (非动态赋值)
     * @param sql sql语句
     * @param dataBaseName 库名
     * @return List 存放多个map对象（每一行数据存放到一个map对象中，可通过列名取出对应的值）
     */
    public List<Object> excuteQuery(String sql, String dataBaseName) {  
        // 执行SQL获得结果集  
        ResultSet rs = executeQueryRS(sql, dataBaseName);  
          
        // 创建ResultSetMetaData对象  
        ResultSetMetaData rsmd = null;  
          
        // 结果集列数  
        int columnCount = 0;  
        try {  
            rsmd = rs.getMetaData();  
              
            // 获得结果集列数  
            columnCount = rsmd.getColumnCount();  
        } catch (SQLException e1) {  
        	log.error(e1.getMessage(),e1);   
        }  
  
        // 创建List  
        List<Object> list = new ArrayList<Object>();  
  
        try {  
            // 将ResultSet的结果保存到List中  
            while (rs.next()) {  
                Map<String, Object> map = new HashMap<String, Object>();  
                for (int i = 1; i <= columnCount; i++) {  
                    map.put(rsmd.getColumnLabel(i), rs.getObject(i));  
                }  
                list.add(map);  
            }  
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e);   
        } finally {  
            // 关闭所有资源  
            closeAll();  
        }  
  
        return list;  
    }
      
    /** 
     * 存储过程带有一个输出参数的方法 
     * @param sql 存储过程语句 
     * @param params 参数数组 
     * @param outParamPos 输出参数位置 
     * @param SqlType 输出参数类型 
     * @return 输出参数的值 
     */  
    public Object excuteQuery(String sql, String dataBaseName, Object[] params,int outParamPos, int SqlType) {  
        Object object = null;  
        connnection = this.getConnection(dataBaseName);  
        try {  
            // 调用存储过程  
            callableStatement = connnection.prepareCall(sql);  
              
            // 给参数赋值  
            if(params != null) {  
                for(int i = 0; i < params.length; i++) {  
                    callableStatement.setObject(i + 1, params[i]);  
                }  
            }  
              
            // 注册输出参数  
            callableStatement.registerOutParameter(outParamPos, SqlType);  
              
            // 执行  
            callableStatement.execute();  
              
            // 得到输出参数  
            object = callableStatement.getObject(outParamPos);  
              
        } catch (SQLException e) {  
        	log.error(e.getMessage(),e); 
        } finally {  
            // 释放资源  
            closeAll();  
        }  
          
        return object;  
    }  
  
    /** 
     * 关闭所有资源 
     */  
    private void closeAll() {  
        // 关闭结果集对象  
        if (resultSet != null) {  
            try {  
                resultSet.close();  
            } catch (SQLException e) {  
            	log.error(e.getMessage(),e); 
            }  
        }  
  
        // 关闭PreparedStatement对象  
        if (preparedStatement != null) {  
            try {  
                preparedStatement.close();  
            } catch (SQLException e) {  
            	log.error(e.getMessage(),e); 
            }  
        }  
          
        // 关闭CallableStatement 对象  
        if (callableStatement != null) {  
            try {  
                callableStatement.close();  
            } catch (SQLException e) {  
            	log.error(e.getMessage(),e);
            }  
        }  
  
        // 关闭Connection 对象  
        if (connnection != null) {  
            try {  
                connnection.close();  
            } catch (SQLException e) {  
            	log.error(e.getMessage(),e);
            }  
        }     
    }  
}
