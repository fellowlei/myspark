package com.mark.hive;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveAlterAddColumn {


    public static void main(String[] args) throws Exception {
        Connection conn =  HiveConn.getConnection();

        Statement stmt = conn.createStatement();
        stmt.execute("alter table employee add columns (dept string comment 'Department name')");
        System.out.println("Add column successful.");
        conn.close();
    }
}
