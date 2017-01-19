package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by root on 17-1-16.
 */
public class HiveCreateTable {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws Exception {
        Class.forName(driverName);

        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb","","");
        String sql = "CREATE TABLE IF NOT EXISTS employee ( eid int, name String,\n" +
                "salary String, destination String)\n" +
                "COMMENT 'Employee details'\n" +
                "ROW FORMAT DELIMITED\n" +
                "FIELDS TERMINATED BY '\\t'\n" +
                "LINES TERMINATED BY '\\n'\n" +
                "STORED AS TEXTFILE";
        Statement stmt = conn.createStatement();
        boolean result = stmt.execute(sql);
        System.out.println("Table employee created." + result);
        conn.close();
    }
}
