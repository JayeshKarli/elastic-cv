//package utils;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch.core.BulkRequest;
//import co.elastic.clients.elasticsearch.core.BulkResponse;
//import co.elastic.clients.elasticsearch.core.IndexResponse;
//import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
//import config.ElasticClient;
//import model.CvElastic;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// *
// * This class will be used to perform CRUD operations on tmcvmakemodel and fuzzy_test types
// *
// */
//public class ElasticOperationsCv {
//
//    ElasticsearchClient client;
//    private final String INDEX = "master";
//    private final String TYPE  = "tmcvmakemodel";
//
//    public ElasticOperationsCv() {
//        client = ElasticClient.getClient();
//    }
//
//    /**
//     *
//     * @param doc A single CvElastic type object to be indexed
//     * @return Returns message with version number of indexed object
//     * @throws IOException
//     *
//     */
//    public void indexOneDocument(CvElastic doc) throws IOException {
//        IndexResponse response = client.index(i -> i
//                .index(INDEX)
//                .id("CV_" + doc.getId())
//                .document(doc)
//        );
//        PrintUtil.println(response.toString());
//    }
//
//    public void bulkIndex(List<CvElastic> docs) throws IOException {
//        BulkRequest.Builder br = new BulkRequest.Builder();
//
//        for(CvElastic doc: docs) {
//            br.operations(op -> op
//                    .index(idx -> idx
//                            .index(INDEX)
//                            .id("CV_" + doc.getId())
//                            .document(doc)
//                    )
//            );
//        }
//
//        BulkResponse result = client.bulk(br.build());
//
//        if (result.errors())
//            logBulkErrors(result.items());
//    }
//
//    private void logBulkErrors(List<BulkResponseItem> items) {
//        PrintUtil.println("Bulk had errors");
//        for (BulkResponseItem item: items) {
//            if (item.error() != null) {
//                PrintUtil.println(item.error().reason());
//            }
//        }
//    }
//
//}
