package utils;

import config.ElasticHighLevelClient;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ElasticOperations {

    RestHighLevelClient client;

    public ElasticOperations() {
        this.client = ElasticHighLevelClient.getClient();
    }

    public void bulkUploadCVData() {
        XLProcessing xlProcessing = new XLProcessing();
        try {
            BulkResponse bulkResponse = client.bulk(xlProcessing.getBulkRequest());

            if (!bulkResponse.hasFailures())
                return;

            int failedOperationsCount = 0;
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    PrintUtil.println(failure.getCause().getLocalizedMessage());
                    failedOperationsCount++;
                }
            }
            PrintUtil.println(failedOperationsCount + " number of operations failed");
        } catch (IOException ioException) {
            PrintUtil.println("IOException occurred while bulk indexing");
            PrintUtil.println(ioException.getLocalizedMessage());
        } catch (Exception e) {
            PrintUtil.println("Some unexpected exception encountered while bulk indexing");
            PrintUtil.println(e.getLocalizedMessage());
            PrintUtil.println(e.toString());
        }
    }

}
