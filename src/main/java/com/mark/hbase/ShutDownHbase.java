package com.mark.hbase;

import org.apache.hadoop.hbase.client.Admin;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class ShutDownHbase {

    public static void main(String[] args) throws IOException {
        Admin admin =  HbaseConn.getAdmin();
        System.out.println("Shutting down hbase");
        admin.shutdown();
    }
}
