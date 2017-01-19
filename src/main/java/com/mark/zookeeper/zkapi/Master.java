package com.mark.zookeeper.zkapi;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Random;

/**
 * Created by root on 17-1-11.
 */
public class Master implements Watcher {
    private ZooKeeper zk;
    private String host;
    private Random random = new Random();
    private String serverId = Integer.toHexString(random.nextInt());
    private boolean isLeader;

    public Master(String host) {
        this.host = host;
    }

    public void startZK() throws IOException {
        zk = new ZooKeeper(host, 15000, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent);
    }

    public void runForMaster() {
        while (true) {
            try {
                zk.create("/master", serverId.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                isLeader = true;
                break;
            } catch (KeeperException.NodeExistsException e) {
                isLeader = false;
                break;
            } catch (Exception e) {

            }
            if (checkMaster()) break;
        }
    }

    public void bootstrap() {
        // 存放可用的Worker
        createParent("/workers", new byte[0]);
        // 存放Worker下存放的任务
        createParent("/assign", new byte[0]);
        // 待执行和新提交的任务
        createParent("/tasks", new byte[0]);
        createParent("/status", new byte[0]);
    }

    private void createParent(String path, byte[] data) {
        zk.create(path,
                data,
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.PERSISTENT, // 持久节点
                createParentCb,
                data); // 将data传给ctx，这样可以在Callback中继续尝试创建节点
    }

    private AsyncCallback.StringCallback createParentCb = new AsyncCallback.StringCallback() {

        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    createParent(path, (byte[]) ctx);
                    break;
                case OK:
                    System.out.println("Parent created");
                    break;
                case NODEEXISTS:
                    System.out.println("Parent already registered: " + path);
                    break;
                default:
                    System.err.println("Something went wrong: " + KeeperException.create(KeeperException.Code.get(rc), path));
            }
        }
    };

    private boolean checkMaster() {
        while (true) {
            try {
                Stat stat = new Stat();
                byte[] datas = zk.getData("/master", false, stat);
                isLeader = new String(datas).equals(serverId);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    private AsyncCallback.StringCallback masterCreateCb = new AsyncCallback.StringCallback() {


        @Override
        public void processResult(int rc, String path, Object ctx, String name) {
            switch (KeeperException.Code.get(rc)) {
                case CONNECTIONLOSS:
                    checkMaster();
                    return;
                case OK:
                    isLeader = true;
                    break;
                default:
                    isLeader = false;
            }
            System.out.println("I'm " + (isLeader ? "" : "not ") + "the leader");
        }
    };


    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "localhost:2181";
        Master master = new Master(host);
        master.startZK();
        master.runForMaster();
        if (master.isLeader) {
            System.out.println("I am leader");
        } else {
            System.out.println("I am not leader");
        }
        Thread.sleep(60000);
    }
}
