package com.dorm.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bean.Http;
import util.HttpRequest;
import util.SpliceParam;



/**
 * 并发Http请求测试
 * @author wangk
 *
 */
public class ConcurrentTest  implements Runnable {
    
	public void run() {
     //这里实现发送请求
		
		Http.setRequestPath("/creditcard/apply/open");
		
		Http.setParam(SpliceParam.sParam("token", "avv7cNRSKWAC41A%2FPzqkaaphSrRKQkbs%2FCv6ODjs1xtsZEOhDldfU8BGJrPcpBPtH9tq%2FaauRgjSRHd%2BjA4A1UNztUlSLMpc44oU2a%2FwTlmqKW8kJ%2BN4DA%3D%3D") 
				+ SpliceParam.sParam("type", "2") + SpliceParam.sParam("sdk_str", "") + SpliceParam.sParam("device_type", "0"));
		
//		Map<Object,Object> param = new HashMap<Object,Object>();
//		
//		param.put("token", "avv7cNRSKWC9ngNFjExUZyR5WGMYFZ5kxrnV%2B890nteWurzyNuGcdXQC02DG%2BcNS8ZQ%2FzAXLdv0NUC04Jsy1gKtzk22hQ0cZPUjs%2FWijjug%3D");
//		param.put("type", 2);
//		param.put("sdk_str", "");
//		param.put("device_type", 0);
//		
//		Http.setParam(SpliceParam.sParam(param));
		
		HttpRequest.post("http://dormappapi.59store.net", Http.getRequestPath(), Http.getParam());
		
    }

	public static void main(String[] args) {
		
		ExecutorService service = null;
		
		try {
			
			service = Executors.newFixedThreadPool(10);
			
			for (int i = 0; i < 10; i++){
				
			   service.execute(new ConcurrentTest());
			}
			
		} finally {
			
			service.shutdownNow();
			
		}
		
	}
	
}
