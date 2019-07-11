package com.pajk.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cucumber.api.DataTable;

public class DataTableUtils {

	/**
	 * for data table with 1 row header + 1 row value
	 * @param data
	 * @return
	 * @throws Exception 
	 */
	public static HashMap<String, String> toHashMap(DataTable data) throws Exception {
		List<String> keys = data.raw().get(0);
		List<String> values = data.raw().get(1);
		LinkedHashMap<String, String> dataHash = new LinkedHashMap<String, String>();
		for (int i=0; i<keys.size(); i++) {
			dataHash.put(keys.get(i), Utils.getDate(Utils.getTestDataProperty(values.get(i))));
		}
		return dataHash;
	}
	
	//取得table的第二行值
	public static List<String> toList(DataTable data) {
		List<String> values = data.raw().get(1);
		return values;
	}

	//取得table的第-行值(header)
	public static List<String> getHeader(DataTable data) {
		List<String> values = data.raw().get(0);
		return values;
	}	
}
