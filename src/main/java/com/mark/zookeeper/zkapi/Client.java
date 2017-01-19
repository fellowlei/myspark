package com.mark.zookeeper.zkapi;

import org.apache.zookeeper.*;

/**
 * Created by root on 17-1-11.
 */
public class Client implements Watcher {

    private ZooKeeper zk;

    private String connnectHost;

    public Client(String connnectHost) {
        this.connnectHost = connnectHost;
    }

    public void startZK() throws Exception {
        zk = new ZooKeeper(connnectHost, 15000, this);
    }

    public String queueCommand(String command) throws Exception {
        String name = null;
        while (true) {
            try {
                // 新节点名称
                name = zk.create("/tasks/task-",
                        command.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT_SEQUENTIAL); // 持久临时节点
                return name;
            } catch (KeeperException.NodeExistsException e) {
                throw new Exception(name + " already appears to be running");
            } catch (KeeperException.ConnectionLossException e) {
            }
        }
    }

    @Override
    public void process(WatchedEvent event) {

    }

    public static void main(String args[]) throws Exception {
        Client c = new Client("localhost:2181");
        c.startZK();
        String name = c.queueCommand("some cmd");
        System.out.println("Created " + name);
    }
}
