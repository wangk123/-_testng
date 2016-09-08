package util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.Assert;

public class Assertion {
	
	private static Logger log = Logger.getLogger(Assertion.class);
	
	public static boolean flag = true;

	public static List<Error> errors = new ArrayList<Error>();
	
	public static void verifyEquals(Object actual,Object expected){

		try {
			
			Assert.assertEquals(actual, expected);
			
		} catch (Error e) {
			errors.add(e);
			flag = false;
			log.error(e);
		}
		
	}
	
	public static void verifyEquals(Object actual,Object expected,String message){
	
		try {
			
			Assert.assertEquals(actual, expected, message);
			
		} catch (Error e) {
			errors.add(e);
			flag = false;
			log.error(message + ":\n" + e);
		}
		
	}
	
}
