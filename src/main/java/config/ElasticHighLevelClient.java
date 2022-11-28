package config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ElasticHighLevelClient {

    static RestClient restClient = RestClient.builder(new HttpHost("127.0.0.1", 9200)).build();
    private static RestHighLevelClient client = new RestHighLevelClient(restClient);

    public static RestHighLevelClient getClient() { return client; }
}
