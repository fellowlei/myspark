package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by root on 17-1-16.
 */
public class HiveDropDb {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws Exception {
        Class.forName(driverName);

        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/default","","");
        Statement stmt = conn.createStatement();
        stmt.execute("drop database userdb");
        System.out.println("Drop userdb database successful.");
        conn.close();
    }
}
