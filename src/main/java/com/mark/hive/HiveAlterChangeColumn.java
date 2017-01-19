package com.mark.hive;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveAlterChangeColumn {

    public static void main(String[] args) throws Exception {
        Connection conn = HiveConn.getConnection();
        Statement stmt = conn.createStatement();

        stmt.execute("alter table employee change name  ename String");
        stmt.execute("alter table employee change salary salary Double");

        System.out.println("Change column successful.");
        conn.close();
    }
}
