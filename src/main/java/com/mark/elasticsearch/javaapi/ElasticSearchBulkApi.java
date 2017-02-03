package com.mark.elasticsearch.javaapi;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;

import java.io.*;
import java.net.InetAddress;

/**
 * Created by root on 17-2-3.
 */
public class ElasticSearchBulkApi {

    /**
     * bulk output demo
     * @throws IOException
     */
    public static void bulkOutput() throws IOException {
        Settings settings = Settings.settingsBuilder().put("cluster.name", "clustername").build();

        Client client = TransportClient.builder().build().addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        QueryBuilder qb = QueryBuilders.matchAllQuery();
        SearchResponse response = client.prepareSearch("blog").setTypes("article").setQuery(QueryBuilders.matchAllQuery()).execute().actionGet();

        SearchHits resultHits = response.getHits();

        File article = new File("bulk.txt");
        BufferedWriter bfw = new BufferedWriter(new FileWriter(article));

        if (resultHits.getHits().length == 0) {
            System.out.println("查到0条数据!");
        } else {
            for (int i = 0; i < resultHits.getHits().length; i++) {
                String jsonStr = resultHits.getHits()[i].getSourceAsString();
                System.out.println(jsonStr);
                bfw.write(jsonStr);
                bfw.write("\n");
            }
        }
        bfw.close();
    }

    /**
     * bulk input demo
     * @throws IOException
     */
    public static void bulkInput() throws IOException {
        Settings settings = Settings.settingsBuilder().put("cluster.name", "clustername").build();

        Client client = TransportClient.builder().build().addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));

        File article = new File("bulk.txt");
        BufferedReader bfr = new BufferedReader(new FileReader(article));

        BulkRequestBuilder bulkRequest = client.prepareBulk();

        int count = 0;
        String line = null;
        while ((line = bfr.readLine()) != null) {
            bulkRequest.add(client.prepareIndex("test", "article").setSource(line));
            if (count % 10 == 0) {
                bulkRequest.execute().actionGet();
            }
            count++;
            System.out.println(line);

        }
        bulkRequest.execute().actionGet();
        bfr.close();
    }

    public static void main(String[] args) throws IOException {
        bulkInput();
    }
}
