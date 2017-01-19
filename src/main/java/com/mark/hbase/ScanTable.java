package com.mark.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by root on 17-1-18.
 */
public class ScanTable {

    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection conn = ConnectionFactory.createConnection(conf);

        Table table = conn.getTable(TableName.valueOf("emp"));

        Scan scan = new Scan();
        scan.addColumn(Bytes.toBytes("personal"),Bytes.toBytes("name"));
        scan.addColumn(Bytes.toBytes("personal"),Bytes.toBytes("city"));
        ResultScanner results = table.getScanner(scan);
        for(Result result: results){
            System.out.println("found row: " + result);
            showCell(result);
        }
        results.close();

    }

    private static void showCell(Result result) {
        Cell[] cells = result.rawCells();
        for(Cell cell: cells){
            System.out.println("rowName: " + new String(CellUtil.cloneRow(cell)));
            System.out.println("timestamp: " + cell.getTimestamp());
            System.out.println("column family: " + new String(CellUtil.cloneFamily(cell)));
            System.out.println("row name:" + new String(CellUtil.cloneQualifier(cell)));
            System.out.println("value: " + new String(CellUtil.cloneValue(cell)));

        }
    }
}
