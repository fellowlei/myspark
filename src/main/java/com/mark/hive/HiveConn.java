package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by root on 17-1-17.
 */
public class HiveConn {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";
    public static Connection getConnection() throws Exception {
        Class.forName(driverName);
        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb", "", "");
        return conn;
    }
}
