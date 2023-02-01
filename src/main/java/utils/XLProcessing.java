package utils;

import model.CvElastic;
import model.CvType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class XLProcessing {

    /**
     *
     * Was written for java client version 8
     * Not useful in our case
     *
     */
    public List<CvElastic> sheetToCVElasticList() {
        List<CvElastic> docs = new ArrayList<>();
        try {
            FileInputStream fis  = new FileInputStream("src/main/resources/imm.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int count = 0;
            for (Row row : sheet) {
                if (count++ == 0)
                    continue;

                CvElastic cvElasticObj = new CvElastic();
                try {
                    setCellProperties(row, cvElasticObj);
                } catch (Exception ex) {
                    PrintUtil.println(ex.getLocalizedMessage());
                    count--;
                    continue;
                }
                docs.add(cvElasticObj);
            }

            PrintUtil.println(String.format("%d values added to list", count));
        } catch (Exception e) {
            PrintUtil.println("Failed to read excel file");
            e.printStackTrace();
        }
        return docs;
    }

    private void setCellProperties(Row row, CvElastic cvElasticObj) {
        try {
            cvElasticObj.setId(row.getCell(0).getStringCellValue());
            cvElasticObj.setMake(row.getCell(1).getStringCellValue());
            cvElasticObj.setModel(row.getCell(2).getStringCellValue());
            cvElasticObj.setMakemodel(row.getCell(1).getStringCellValue() + " " + row.getCell(2).getStringCellValue());

            cvElasticObj.setSupportedSC(List.of(row.getCell(9).getStringCellValue()));
            cvElasticObj.setSupportedCC(List.of(row.getCell(7).getStringCellValue()));
            cvElasticObj.setSupportedGVW( List.of( Integer.parseInt(row.getCell(8).getStringCellValue()) ) );

            cvElasticObj.setCv_type(List.of(new CvType()));
        } catch (Exception e){
            throw e;
        }
    }

    /**
     *
     * @return BulkRequest object containing all the IndexRequest objects of data from imm.xlsx sheet
     *
     */
    public BulkRequest getBulkRequest() {
        BulkRequest request = new BulkRequest();

        try {
            FileInputStream fis  = new FileInputStream("src/main/resources/imm.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sheet = wb.getSheetAt(0);

            int count = 0;
            for (Row row : sheet) {
                if (count == 0) {
                    count++;PrintUtil.println("After getting indexRequest");
                    continue;
                }
                try {
                    IndexRequest indexRequest = getRestHighLevelClientIndexRequest(row);
                    request.add(indexRequest);
                    count++;
                } catch (Exception ex) {
                    PrintUtil.println(ex.getLocalizedMessage());
                }
            }
            PrintUtil.println(String.format("%d values added to list", --count));
        } catch (Exception e) {
            PrintUtil.println("Failed to read excel file");
            e.printStackTrace();
        }

        return request;
    }

    /**
     *
     * @param row A row of data from Excel file being used to import data
     * @return IndexRequest object
     * @throws IOException
     *
     */
    private IndexRequest getRestHighLevelClientIndexRequest(Row row) throws IOException {
        XContentBuilder builder;
        try {
            builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("id", row.getCell(0).getStringCellValue());
                builder.field("make", row.getCell(1).getStringCellValue());
                builder.field("model", row.getCell(2).getStringCellValue());
                String makeModel = row.getCell(1).getStringCellValue() + " " + row.getCell(2).getStringCellValue();
                builder.field("makemodel", makeModel);
                builder.field("makemodelfuzzy", makeModel);
                builder.field("vertical", "CV");
                builder.field("cvVehicleClass", "MISCD");

                Map<String, Object> cvType = new LinkedHashMap<>();
                cvType.put("id", 62L);
                cvType.put("vehicle_type", "Default Misc ICICI");
                cvType.put("body_type", null);
                cvType.put("usage_type", null);
                cvType.put("subtype_payout", "DEFAULT MISC ICICIC");
                cvType.put("display_name", "DEFAULT MISC ICICI");
                builder.field("cv_type", List.of(cvType));

                builder.field("supportedFuel", List.of("Diesel"));
                builder.field("supportedSC", List.of(row.getCell(9).getStringCellValue()));
                builder.field("supportedCC", List.of(row.getCell(7).getStringCellValue()));
                builder.field("supportedGVW", List.of( Integer.parseInt(row.getCell(8).getStringCellValue()) ));
            }
        } catch (Exception e) {
            PrintUtil.println("Error in getting high level client or setting values for high level client");
            PrintUtil.println(e.getLocalizedMessage());
            throw e;
        }
        builder.endObject();
        String INDEX = "master";
        String TYPE = "tmcvmakemodel";
        return new IndexRequest(INDEX, TYPE, "CV_" + row.getCell(0).getStringCellValue()).source(builder);
    }
}
