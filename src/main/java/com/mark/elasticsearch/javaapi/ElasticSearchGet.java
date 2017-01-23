package com.mark.elasticsearch.javaapi;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17-1-23.
 * sw elasticsearch-2.3.4.tar.gz
 */
public class ElasticSearchGet {
    /* 创建客户端 */
    public Client getClient() throws UnknownHostException {
        Client client = TransportClient.builder().build().addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        return client;
    }

    public static List<String> getDataList() {
        List<String> list = new ArrayList<String>();
        String data1 = JsonUtil.model2Json(new Blog(1, "git简介", "2016-06-19", "SVN与Git最主要的区别..."));
        String data2 = JsonUtil.model2Json(new Blog(2, "Java中泛型的介绍与简单使用", "2016-06-19", "学习目标 掌握泛型的产生意义..."));
        String data3 = JsonUtil.model2Json(new Blog(3, "SQL基本操作", "2016-06-19", "基本操作：CRUD ..."));
        String data4 = JsonUtil.model2Json(new Blog(4, "Hibernate框架基础", "2016-06-19", "Hibernate框架基础..."));
        String data5 = JsonUtil.model2Json(new Blog(5, "Git基本知识git", "2016-06-19", "Shell是什么..."));
        String data6 = JsonUtil.model2Json(new Blog(6, "C++基本知识", "2016-06-19", "Shell是什么..."));
        String data7 = JsonUtil.model2Json(new Blog(7, "Mysql基本知识", "2016-06-19", "git是什么..."));
        list.add(data1);
        list.add(data2);
        list.add(data3);
        list.add(data4);
        list.add(data5);
        list.add(data6);
        list.add(data7);
        return list;

    }

    public void initQueryData() {
        try {
            Client client = getClient();
            List<String> jsonData = getDataList();

            for (int i = 0; i < jsonData.size(); i++) {
                IndexResponse response = client.prepareIndex("blog", "article").setSource(jsonData.get(i)).get();
                if (response.isCreated()) {
                    System.out.println("创建成功!");
                }
            }
            client.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchGet() {
        try {
            Client client = getClient();
//            QueryBuilder qb1 = QueryBuilders.termQuery("title", "hibernate");
            QueryBuilder qb2 = QueryBuilders.multiMatchQuery("git", "title", "content");

            SearchResponse response = client.prepareSearch("blog").setTypes("article").setQuery(qb2).execute()
                    .actionGet();

            SearchHits hits = response.getHits();
            if (hits.totalHits() > 0) {
                for (SearchHit hit : hits) {
                    System.out.println("score:" + hit.getScore() + ":\t" + hit.getSource());// .get("title")
                }
            } else {
                System.out.println("搜到0条结果");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws UnknownHostException {
        ElasticSearchGet elasticSearchGet = new ElasticSearchGet();
        elasticSearchGet.initQueryData();
        elasticSearchGet.searchGet();

    }
}
