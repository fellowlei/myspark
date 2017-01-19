package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveQLWhere {
    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws Exception {
        // Register driver and create driver instance
        Class.forName(driverName);

        // get connection
        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb", "", "");


        Statement stmt =conn.createStatement();
        ResultSet res =  stmt.executeQuery("select * from employee where salary > 30000");
        System.out.println("Result:");
        System.out.println(" ID \t Name \t Salary \t Designation \t Dept ");

        while (res.next()) {
            System.out.println(res.getInt(1) + " " + res.getString(2) + " " + res.getDouble(3) + " " + res.getString(4) );
        }

        conn.close();



    }
}
