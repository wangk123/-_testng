package com.dorm.test;

import java.util.HashMap;
import java.util.Map;

public class TestClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		
		map.put("token", "avv7cNRSKWC9ngNFjExUZyR5WGMYFZ5kMMokQjKQTO1Wv7pAg24Ls3QC02DG%2BcNS8ZQ%2FzAXLdv0NUC04Jsy1gKtzk22hQ0cZPUjs%2FWijjug%3D");
		
		map.put("type", 2);
		
		map.put("device_type", 0);
		
		map.put("sdk_str", "");
		
		for(Object key : map.keySet()){
			Object value = map.get(key);
			System.out.println(key + ":" + value);
		}

	}

}
