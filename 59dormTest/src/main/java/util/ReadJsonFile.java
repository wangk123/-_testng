package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadJsonFile {
	
	/**
	 * 读取json文件
	 * @param name 文件名(文件请到/src/test/resources路径下) 
	 * @return
	 */
	public static String readJsonFile(String name){
		
		    File file = new File("src/test/resources/" + name);
		    BufferedReader reader = null;
		    String laststr = "";
		    try {
		     //System.out.println("以行为单位读取文件内容，一次读一整行：");
		     reader = new BufferedReader(new FileReader(file));
		     String tempString = null;
		     //一次读入一行，直到读入null为文件结束
		     while ((tempString = reader.readLine()) != null) {
		      laststr = laststr + tempString;
		     }
		     reader.close();
		    } catch (IOException e) {
		     e.printStackTrace();
		    } finally {
		     if (reader != null) {
		      try {
		       reader.close();
		      } catch (IOException e1) {
		      }
		     }
		    }
		    return laststr;
		
	}

}
