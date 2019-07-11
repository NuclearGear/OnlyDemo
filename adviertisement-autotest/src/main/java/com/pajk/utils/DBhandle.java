package com.pajk.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.pajk.config.Const;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * mysql数据库操作
 * @@author
 * 创建时间：
 * 更新时间：
 *
 */
public class DBhandle {
    private static final Logger logger = LogManager.getLogger(DBhandle.class);
    private String url,db,user,pass;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String character = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
    static Connection conn = null;
    static Statement statement = null;
    static PreparedStatement ps = null;
    static ResultSet rs = null;
    List list = new ArrayList();//返回所有记录
    public DBhandle(String url, String db, String user, String pass){
        this.url = url;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    /*
     * 连接数据库
     */
    public void connDB() {
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(this.url+this.db+character, this.user, this.pass);
            if (!conn.isClosed()) {
                //logger.info("Succeeded connecting to MySQL!");
            }

            statement = conn.createStatement();
        } catch (ClassNotFoundException e) {
            logger.error("装载 JDBC/ODBC 驱动程序失败。");
        } catch (SQLException e) {
            logger.error("无法连接数据库");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /*
     * 关闭数据库
     */
    public static void closeDB() {
        if(rs != null ){
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();
                //logger.info("Database connection terminated!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 查询数据表
     * 一列数据返回value
     */
    public List query(String sql){
        list.clear();
        connDB();
        int count;
        try {
            rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd;
            rsmd = rs.getMetaData();
            count = rsmd.getColumnCount();
            if(count==1){
                while(rs.next()){
                    String value= rs.getString(1);
                    list.add(value);
                }
            }
            else {
                while(rs.next()){
                    Map  map = new HashMap();
                    for(int i=1;i<=count;i++){
                        //获取指定列的表目录名称
                        String label=rsmd.getColumnLabel(i);
                        //以 Java 编程语言中 Object 的形式获取此 ResultSet 对象的当前行中指定列的值
                        String value= rs.getString(i);
                        //把数据库中的字段名和值对应为一个map对象中的一个键值对
                        map.put(label.toLowerCase(), value);
                    }
                    list.add(map);
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeDB();
        }
        return list;
    }

    /*
     * 数据插入
     */
    public void insert(String sql){
        connDB();
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    /*
     * 数据更新
     */
    public void update(String sql){
        connDB();
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }

    /*
     * 数据删除
     */
    public void delete(String sql){
        connDB();
        try {
            statement.execute(sql);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            closeDB();
        }
    }
    public static void main(String[] args) throws Exception {
    }
}
