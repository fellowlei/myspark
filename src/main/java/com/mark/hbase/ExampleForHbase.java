package com.mark.hbase;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * Created by root on 17-1-17.
 */
public class ExampleForHbase {
    public static Configuration configuration;
    public static Connection connection;
    public static Admin admin;

    public static void init() {
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.rootdir", "hdfs://localhost:9000/hbase");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getHbaseConn() throws IOException {
        Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.rootdir","hdfs://localhost:9000/hbase");
        Connection conn  = ConnectionFactory.createConnection(conf);
        return conn;
    }

    public static Admin getAdmin() throws IOException {
        Connection conn =  getHbaseConn();
        return conn.getAdmin();
    }

    public static void close() {
        try {
            if (admin != null) {
                admin.close();
            }
            if (null != connection) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException {
//        createTable("Score", new String[]{"sname", "course"});

//        listTable();

//        insertRow("Score", "95001", "sname", "", "Mary");
//        insertRow("Score", "95001", "course", "Math", "88");
//        insertRow("Score", "95001", "course", "English", "85");

//        deleteRow("Score", "95001", "course", "Math");

//        deleteRow("Score", "95001", "course", "");

//        deleteRow("Score", "95001", "", "");

//        getData("Score", "95001", "course", "Math");

//        getData("Score", "95001", "sname", "");

        deleteTable("Score");
    }

    private static void getData(String tbName, String rowKey, String colFamily, String col) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tbName));
        Get get = new Get(rowKey.getBytes());

        get.addColumn(colFamily.getBytes(),col.getBytes());
        Result result = table.get(get);
        showCell(result);
        table.close();
        close();
    }

    private static void showCell(Result result) {
        Cell[] cells =  result.rawCells();
        for(Cell cell: cells){
            System.out.println("RowName: " + new String(CellUtil.cloneRow(cell)) + " ");
            System.out.println("Timestamp: " + cell.getTimestamp() + " ");
            System.out.println("column Family: " + new String(CellUtil.cloneFamily(cell)));
            System.out.println("row Name: " +new String(CellUtil.cloneQualifier(cell)) );
            System.out.println("value: " + new String(CellUtil.cloneValue(cell)));
        }
    }

    private static void deleteRow(String tbName, String rowKey, String colFamily, String col) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tbName));
        Delete delete = new Delete(rowKey.getBytes());

//        delete.addColumn(colFamily.getBytes(),col.getBytes());

//        delete.addFamily(colFamily.getBytes());

        table.delete(delete);
        table.close();
        close();
    }

    private static void insertRow(String tbName, String rowKey, String colFamily, String col, String val) throws IOException {
        init();
        Table table = connection.getTable(TableName.valueOf(tbName));
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        table.put(put);
        table.close();
        close();
    }

    private static void deleteTable(String tbName) throws IOException {
        init();
        TableName tableName = TableName.valueOf(tbName);
        if (admin.tableExists(tableName)) {
            admin.disableTable(tableName);
            admin.deleteTable(tableName);
        }
        close();
    }

    private static void createTable(String tbName, String[] colNames) throws IOException {
        init();
        TableName tableName = TableName.valueOf(tbName);
        if (admin.tableExists(tableName)) {
            System.out.println("talbe is exists!");
        } else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String name : colNames) {
                HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(name);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }

            admin.createTable(hTableDescriptor);
            System.out.println("create table success");

        }
        close();

    }

    public static  void listTable() throws IOException {
        init();
        HTableDescriptor[] hTableDescriptors = admin.listTables();
        for(HTableDescriptor htd: hTableDescriptors){
            System.out.println(htd.getNameAsString());
        }

        close();
    }

}
