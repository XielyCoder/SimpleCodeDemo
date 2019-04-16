package com.xiely.common.rpc.common.protocol;
/**
 * PropertiesUtils
 * 
 */

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {
	
	private Properties file;
	
	private static PropertiesUtils instance = new PropertiesUtils();
	
	private PropertiesUtils() {
		file = new Properties();
		try {
			file.load(PropertiesUtils.class.getResourceAsStream("/app.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getProperties(String key) {
		return (String) instance.file.get(key);
	}
	
}

