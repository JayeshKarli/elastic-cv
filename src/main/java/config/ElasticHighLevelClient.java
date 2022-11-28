package config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticHighLevelClient {

    private static final int PORT_NUMBER = 9200;
    private static final String HOSTNAME = "127.0.0.1";

    static RestClient restClient = RestClient.builder(new HttpHost(HOSTNAME, PORT_NUMBER)).build();
    private static RestHighLevelClient client = new RestHighLevelClient(restClient);

    /**
     *
     * The client object is configured to connect to localhost on 9200 port number
     *
     * @return Returns an instance of RestHighLevelClient from org.elasticsearch.client package
     */
    public static RestHighLevelClient getClient() { return client; }
}
