//package config;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//
//public class ElasticClient {
//    private static final RestClient restClient = RestClient.builder(
//            new HttpHost("127.0.0.1", 9200)).build();
//    private static final ElasticsearchTransport transport = new RestClientTransport(
//            restClient, new JacksonJsonpMapper());
//
//    private static final ElasticsearchClient client = new ElasticsearchClient(transport);
//
//    public static ElasticsearchClient getClient() { return client; }
//
//}
