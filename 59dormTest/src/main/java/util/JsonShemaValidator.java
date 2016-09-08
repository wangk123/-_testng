package util;

import org.apache.log4j.Logger;

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
    public static void validatorSchema(String fileName, String instance) {
        ProcessingReport processingReport = null;
		try {
			JsonNode mainNode = JsonLoader.fromString(ReadJsonFile.readJsonFile(fileName));
			JsonNode instanceNode = JsonLoader.fromString(instance);
	        JsonSchema schema = factory.getJsonSchema(mainNode);
	        processingReport = schema.validate(instanceNode);
	        if(processingReport.isSuccess() == false){
	        	//数据结构验证不通过，设置断言结果为false
	        	Assertion.flag = false;
	        }
	        log.info(instance + "\n 执行结果：" + processingReport);
		} catch (Exception e) {
			Assertion.flag = false;
			log.error(e.getMessage(),e);
		} 
		
    }

}
