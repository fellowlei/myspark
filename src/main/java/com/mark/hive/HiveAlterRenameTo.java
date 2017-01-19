package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveAlterRenameTo {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws Exception {
            Class.forName(driverName);

            Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb","","");

        Statement stmt = conn.createStatement();
        stmt.execute("alter table employee rename to emp");
        System.out.println("Table Renamed Successfully");
        conn.close();
    }
}
