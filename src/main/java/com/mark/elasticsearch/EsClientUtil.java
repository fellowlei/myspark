package com.mark.elasticsearch;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by root on 17-1-23.
 * get client for es
 */
public class EsClientUtil {
    //节点方式创建
    public static Client getClientByClusterName() {
        Node node = NodeBuilder.nodeBuilder().clusterName("yourclustername").node();
        Client client = node.client();
        return client;
    }

    //指定 ip地址创建
    public static Client getClientByIp() throws UnknownHostException {
        Client client = TransportClient.builder().build()
                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("host2"), 9300));
        return client;
    }

    //按集群名称创建
    public static Client getClientByClusterName2() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch").build();
        Client client = TransportClient.builder().settings(settings).build();
        return client;
    }

    //同一内网Ip段,嗅的方式自己查找,组成集群
    public static Client getClientBySniff() {
        Settings settings = Settings.settingsBuilder()
                .put("client.transport.sniff", true).build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        return client;
    }
}
