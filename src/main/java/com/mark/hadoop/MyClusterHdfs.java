package com.mark.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * Created by root on 17-1-19.
 */
public class MyClusterHdfs {

    public static void main(String[] args) throws IOException {
        System.setProperty("hadoop.home.dir", "/user/local/hadoop");

        Logger logger = Logger.getLogger(MyClusterHdfs.class);

        Configuration conf = new Configuration();
        conf.set("fs.defaultFS", "hdfs://cluster");
        conf.set("dfs.nameservices", "cluster");
        conf.set("dfs.ha.namenodes.cluster", "nn1,nn2");
        conf.set("dfs.namenode.rpc-address.cluster.nn1", "nnode:8020");
        conf.set("dfs.namenode.rpc-address.cluster.nn2", "dnode1:8020");
        conf.set("dfs.client.failover.proxy.provider.cluster",
                "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");


        FileSystem fs = FileSystem.get(conf);
        RemoteIterator<LocatedFileStatus> it = fs.listFiles(new Path("/"), true);
        while (it.hasNext()) {
            LocatedFileStatus loc = it.next();
            logger.info(loc.getPath().getName() + "|" + loc.getLen() + loc.getOwner());
        }
    }
}
