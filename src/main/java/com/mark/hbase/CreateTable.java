package com.mark.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class CreateTable {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Admin admin = conn.getAdmin();

        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf("emp"));
        hTableDescriptor.addFamily(new HColumnDescriptor("personal"));
        hTableDescriptor.addFamily(new HColumnDescriptor("professional"));

        admin.createTable(hTableDescriptor);
        System.out.println("Table created");

    }
}
