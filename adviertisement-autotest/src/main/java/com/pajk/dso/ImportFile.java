package com.pajk.dso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.pajk.utils.Excel;
import com.pajk.utils.Utils;

public class ImportFile {
	
	private String fileName;
	private List<LinkedHashMap<String, String>> recordsList;

	public ImportFile(String fileName) throws Exception {
		this.fileName = fileName;
		recordsList = Excel.readWorkBook(Utils.getImportExcelFilePath(fileName));
	}
	
	public List<LinkedHashMap<String, String>> getFileRecords() throws Exception {
		return recordsList;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public List<String> getValuesByHeader(String headerName) throws Exception {
		List<String> returnList = new ArrayList<String>();
		for (int i=0;i<recordsList.size();i++) {
			returnList.add(recordsList.get(i).get(headerName));
		}
		return returnList;
	}
}
