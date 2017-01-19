package com.mark.hbase;

import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class DeleteTable {
    public static void main(String[] args) throws IOException {
       Admin admin =  HbaseConn.getAdmin();
        admin.disableTable(TableName.valueOf("emp"));
        admin.deleteTable(TableName.valueOf("emp"));
        System.out.println("Table deleted");
    }
}
