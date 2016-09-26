package util;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;


public class JsonShemaValidator {
	
	private final static JsonSchemaFactory factory = JsonSchemaFactory.byDefault();
	
	private static Logger log = Logger.getLogger(JsonShemaValidator.class);

    /**
     * 验证json数据结构
     * @param fileName http://jsonschema.net/#/转换后数据结构存放文件
     * @param instance json字符串
     */
    public static boolean validatorSchema(String fileName, JSONObject instance) {
        ProcessingReport processingReport = null;
		try {
			JsonNode mainNode = JsonLoader.fromString(ReadJsonFile.readJsonFile(fileName));
			JsonNode instanceNode = JsonLoader.fromString(instance.toString());
	        JsonSchema schema = factory.getJsonSchema(mainNode);
	        processingReport = schema.validate(instanceNode);
	        if(processingReport.isSuccess() == false){
	        	//数据结构验证不通过，设置断言结果为false
	        	return false;
	        }
	        log.info("\n 执行结果：" + processingReport);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return false;
		}
		return true; 
    }

}
