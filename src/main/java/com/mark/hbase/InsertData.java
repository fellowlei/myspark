package com.mark.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by root on 17-1-18.
 */
public class InsertData {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);
        Table table = conn.getTable(TableName.valueOf("emp"));

        Put put = new Put(Bytes.toBytes("row1"));
        put.addColumn(Bytes.toBytes("personal"),Bytes.toBytes("name"),Bytes.toBytes("raju"));
        put.addColumn(Bytes.toBytes("personal"),Bytes.toBytes("city"),Bytes.toBytes("hyder"));
        put.addColumn(Bytes.toBytes("professional"), Bytes.toBytes("salary"),Bytes.toBytes("50000"));
        table.put(put);

        System.out.println("data inserted");
        table.close();

    }
}
