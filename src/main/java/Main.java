import utils.ElasticOperations;
import utils.PrintUtil;

public class Main {

    public static void main(String[] args) {
//        ElasticOperationsCv operationsCv = new ElasticOperationsCv();
        ElasticOperations elasticOperations = new ElasticOperations();

        try {
            PrintUtil.println("Indexing list of documents");
            elasticOperations.bulkUploadCVData();
            PrintUtil.println("Indexing done");
        } catch (Exception e) {
            PrintUtil.println("Exception occurred while indexing one document");
            PrintUtil.println(e.getLocalizedMessage());
        }

    }

}
