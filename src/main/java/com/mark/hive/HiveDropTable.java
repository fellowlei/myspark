package com.mark.hive;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by root on 17-1-17.
 */
public class HiveDropTable {

    public static void main(String[] args) throws Exception {
        Connection conn= HiveConn.getConnection();

        Statement stmt = conn.createStatement();
        stmt.execute("drop table if exists employee");
        System.out.println("Drop table successful.");
        conn.close();
    }
}
