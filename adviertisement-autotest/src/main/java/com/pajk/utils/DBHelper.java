package com.pajk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pajk.config.Const;

public class DBHelper {
	private static final Logger logger = LogManager.getLogger(DBHelper.class);
	
	static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	static String url = "jdbc:sqlserver://"+ Const.DB_URL +";DatabaseName="+ Const.DB_DatabaseName +"";
	 
	private static Connection getDBConnection() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, ""+ Const.DB_UserName + "", ""+ Const.DB_Password +"");
		} catch (ClassNotFoundException e) {
			logger.error("装载 JDBC/ODBC 驱动程序失败。");
		} catch (SQLException e) {
			logger.error("无法连接数据库");
		}
		return con;
	}
	
	private static void closeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			;
		}
	}
	
    /**
     * 查询SQL方法
     * @param sql
     * @return
     * @throws SQLException
     */
    public static ArrayList<String> query(String sql) throws SQLException{//对数据库进行数据查询
        //获得连接
    	Connection con = getDBConnection();
    	Statement st = con.createStatement();
    	ResultSet res;
        try {
        	res = st.executeQuery(sql);
        } catch (SQLException e) {
	        throw new RuntimeException("查询失败： " + sql);
        }  
        ArrayList<String> result = new ArrayList<String>();
		while(res.next()) {
        	ResultSetMetaData rsmd = res.getMetaData();
        	int columnCount = rsmd.getColumnCount();
        	for (int i=0;i<columnCount;i++) {
        		result.add(res.getString(i+1));
        	}
        }
		closeConnection(con);
		return result;        
    }
    /**
     * SQL删除修改
     * @param sql
     * @return
     * @throws SQLException
     */
    public static void updateDB(String sql) throws SQLException{//对增删改
        //获得连接
    	Connection con = getDBConnection();
    	Statement st = con.createStatement();
    	int recordsNo = st.executeUpdate(sql);
        if(recordsNo == 0) {
        	throw new RuntimeException("执行失败： " + sql);
        } else if(recordsNo == 1){
        	logger.info("更新成功1条： " + sql);
        } else {
        	logger.info("更新成功条数： " + recordsNo);
        }
        closeConnection(con);
    }
    
    //存在数据的时候更新，不存在的时候不需要更新，影响数据行数为0或者1
    public static void updateDBIfExist(String sql) throws SQLException{//对增删改
        //获得连接
    	Connection con = getDBConnection();
    	Statement st = con.createStatement();
        int recordsNo = st.executeUpdate(sql);
        logger.info("更新成功的record数量 " + recordsNo);   
        closeConnection(con);
    }
    
    public static void deleteVacationBalance(String code, String staffId) throws SQLException {
    	String sql = "DELETE lb FROM Leave_Balance lb LEFT JOIN Leave_Code lc ON lc.id = lb.LeaveCode_id "
    			+ "WHERE lc.LeaveBenefitCode ='" + code + "' and taffno ='" + staffId + "'";
    	updateDB(sql);
    } 
    
    /**
     * 删除历史组织架构
     * @param code
     * @param staffId
     * @throws SQLException
     */
    public static void deleteHistoryOrganzationalStructure(String hisname) throws SQLException {
    	String sql = "DELETE FROM Org_DataType WHERE NAME='" + hisname + "'";
    	updateDB(sql);
    } 
    
    /**
     * 取得员工工号，该员工没有卡，用于case E-653
     * @return
     * @throws SQLException
     */
    public static String getStaffNoWithoutCard() throws SQLException {
    	String sql = "SELECT ac.staffNo FROM At_Card ac INNER JOIN hr_Staff hs ON ac.StaffNo = hs.StaffNo "
    			+ "WHERE ac.No = ''  AND hs.StaffType  = 'Active'";
    	String id = query(sql).get(0);
    	String sqlDelete = String.format("DELETE ap  FROM At_PunchClockInfo ap INNER JOIN At_Card ac ON ap.At_Card_id = ac.Id "
    			+ "WHERE ac.StaffNo ='%s'", id);
    	updateDB(sqlDelete);
    	return id;
    }
}
