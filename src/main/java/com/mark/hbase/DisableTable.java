package com.mark.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class DisableTable {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();

        Boolean bool = admin.isTableDisabled(TableName.valueOf("emp"));
        System.out.println(bool);
        if(!bool){
            admin.disableTable(TableName.valueOf("emp"));
            System.out.println("Table disabled");
        }
    }
}
