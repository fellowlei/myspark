package com.mark.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;

/**
 * Created by root on 17-1-11.
 */
public class ZKDelete {
    private static ZooKeeper zk;
    private static  ZooKeeperConnection conn;

    public static void  delete(String path) throws KeeperException, InterruptedException {
        zk.delete(path,zk.exists(path,true).getVersion());
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        try{
            conn = new ZooKeeperConnection();
            zk = conn.connect("localhost");
            delete(path);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
