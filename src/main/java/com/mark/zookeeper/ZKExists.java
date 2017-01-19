package com.mark.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/**
 * Created by root on 17-1-11.
 */
public class ZKExists {
    private static ZooKeeper zk;
    private static  ZooKeeperConnection conn;

    public static Stat znode_exists(String path) throws KeeperException, InterruptedException {
        return zk.exists(path,true);
    }

    public static void main(String[] args) {
        String path = "/MyFirstZnode";
        try{
            conn = new ZooKeeperConnection();
            zk = conn.connect("localhost");
            Stat stat =  znode_exists(path);
            if(stat != null){
                System.out.println("Node exists and the node version is " +
                        stat.getVersion());
            }else{
                System.out.println("Node does not exists");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
