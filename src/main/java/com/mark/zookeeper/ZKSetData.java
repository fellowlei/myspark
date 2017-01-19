package com.mark.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by root on 17-1-11.
 */
public class ZKSetData {
    private static ZooKeeper zk;
    private static  ZooKeeperConnection conn;

    public static void update(String path,byte[] data) throws KeeperException, InterruptedException {
        zk.setData(path,data,zk.exists(path,true).getVersion());
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        byte[] data = "Success".getBytes();

        try{
            conn = new ZooKeeperConnection();
            zk = conn.connect("localhost");
            update(path,data);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
