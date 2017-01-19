package com.mark.hive;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveLoadData {

    private static String driverName = "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args) throws Exception {
        Class.forName(driverName);

        Connection conn = DriverManager.getConnection("jdbc:hive2://localhost:10000/userdb","","");

        Statement stmt = conn.createStatement();
        boolean result = stmt.execute("load data local inpath '/home/hadoop/sample.txt' overwrite into table employee");
        System.out.println("Load Data into employee successful" + result);
        conn.close();
    }
}
