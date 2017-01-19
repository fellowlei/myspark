package com.mark.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class HbaseConn {

    public static Connection getConn() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        return conn;
    }

    public static Admin getAdmin() throws IOException {
        Connection conn = getConn();
        return conn.getAdmin();
    }
}
