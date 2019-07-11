package com.pajk.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertyUtil {
	
	public static String getValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream (new FileInputStream(filePath));  
			pps.load(in);
			String value = pps.getProperty(key);
			return value;          
		} catch (IOException e) {
			throw new RuntimeException("没有key" + key);
		}
	}
		     
	//写入Properties信息
	public static void updateProperties (String filePath, String pKey, String pValue) throws IOException {
	    Properties pps = new Properties();
	      
	    InputStream in = new FileInputStream(filePath);
	      //从输入流中读取属性列表（键和元素对） 
	    pps.load(in);
	     
	    OutputStream out = new FileOutputStream(filePath);
	    pps.setProperty(pKey, pValue);   
	    pps.store(out, "Update " + pKey + " name");
  }
		 
}
