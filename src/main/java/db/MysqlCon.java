package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by ywcrm on 2017/5/13.
 */
public enum MysqlCon {
    ;
    private static Connection conn;
//    private static Statement stmt;

    public static Connection getConn() {
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/xiaomi?user=root&password=root";
                conn = DriverManager.getConnection(url);
//                if (stmt == null) {
//                    stmt = conn.createStatement();
//                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

//    public static Statement getStmt() {
//        if (conn == null) {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//                String url = "jdbc:mysql://localhost:3306/xiaomi?user=root&password=root";
//                conn = DriverManager.getConnection(url);
//                if (stmt == null) {
//                    stmt = conn.createStatement();
//                }
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return stmt;
//    }
}
