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
public class RetriveData {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();

        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("emp"));
        Get get = new Get(Bytes.toBytes("row1"));

        Result result = table.get(get);
        byte[] value = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("name"));
        byte[] value1 = result.getValue(Bytes.toBytes("personal"),Bytes.toBytes("city"));
        String name = Bytes.toString(value);
        String city = Bytes.toString(value1);

        System.out.println("name: " + name + " city: " + city);
    }
}
