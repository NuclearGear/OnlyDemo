package com.winmax.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.plexus.util.StringUtils;

public class Excel {
	
	private static int width = 3000;
	
	public static List<LinkedHashMap<String, String>> readWorkBook(String filePath) throws Exception {
		return	readWorkBook(filePath, 0);
	}
	
	public static List<LinkedHashMap<String, String>> readImportWorkBook(String fileName) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		return	readWorkBook(filePath, 0);
	}
	
	public static List<String> getImportExcelColumnValue(String fileName, String columnName) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		return	getColumnValue(filePath, columnName);
	}
	
	public static List<String> getColumnValue(String filePath, String columnName) throws Exception {
		List<LinkedHashMap<String, String>> values = readWorkBook(filePath, 0);
		List<String> data = new ArrayList<String>();
		for (int i=0;i<values.size();i++) {
			if (!values.get(i).containsKey(columnName)) {
				throw new RuntimeException("文件 [" + filePath + "]不包含列名： " + columnName);
			}
			data.add(values.get(i).get(columnName));
		}
		return data;
	}

	//the format of the excel need to contain column name
	//then can retrieve values by key (column name)
	public static List<LinkedHashMap<String, String>> readWorkBook(String filePath, int sheetNumber) throws Exception {
		InputStream ins = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(ins);
		XSSFSheet sheet1 = wb.getSheetAt(sheetNumber);		
		int rowCount = sheet1.getLastRowNum();
		int firstRowNum = 0;
		List<LinkedHashMap<String, String>> values = new ArrayList<LinkedHashMap<String, String>>();
		List<String> head = new ArrayList<String>();
		for (int currentRowNum = firstRowNum; currentRowNum <= rowCount; currentRowNum++) {
			XSSFRow row = sheet1.getRow(currentRowNum);
			LinkedHashMap<String, String> rowValue = new LinkedHashMap<String, String>();
			for (int i = 0; i < sheet1.getRow(0).getLastCellNum(); i++) {
				// get column name values
				if (currentRowNum == 0) {
					head.add(getStrValue(row.getCell(i)));
				} else {
					rowValue.put(head.get(i), getStrValue(row.getCell(i)));					
				}
			}
			if(currentRowNum > 0) { //will add 1st row value to the list
				values.add(rowValue);
			}
		}
		ins.close();
		return values;
	}	
	
	//default the 1st sheet
	public static void updateWorkBook(String fileName,String[][] fieldValues) throws Exception {
		updateWorkBook(fileName, 0, fieldValues);
	}
	
	/**
	 * 
	 * @param fileName: just file name default location is src/test/resources
	 * @param sheetNumber: 1st sheet is 0
	 * @param fieldValues: all values that need to be updated
	 * @throws Exception
	 */
	public static void updateWorkBook(String fileName, int sheetNumber, String[][] fieldValues) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		InputStream ins = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(ins);
		XSSFSheet sheet1 = wb.getSheetAt(sheetNumber);		
		int rowCount = sheet1.getLastRowNum();
		int firstRowNum = 1;
		for (int currentRowNum = firstRowNum; currentRowNum <= rowCount; currentRowNum++) {
			XSSFRow row = sheet1.getRow(currentRowNum);
			for (int i = 0; i < sheet1.getRow(0).getLastCellNum(); i++) {
				// get column name values
                XSSFCell cell = row.getCell((short) i);
                cell.setCellValue(fieldValues[currentRowNum-1][i]);	
			}			
		}
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.close();
	}
	
	/**
	 * 
	 * @param fileName: just file name default location is src/test/resources
	 * @param sheetNumber: 1st sheet is 0
	 * @param fieldValues: key是column name， String[] 是数据，一行数据，数组长度为1， 2行数据，数组长度为2， 以此类推
	 * @throws Exception
	 */
	public static void updateImportWorkBook(String fileName, HashMap<String, String[]> fieldValues) throws Exception {
		int sheetNumber = 0;
		String filePath = Utils.getImportExcelFilePath(fileName);
		InputStream ins = new FileInputStream(filePath);
		XSSFWorkbook wb = new XSSFWorkbook(ins);
		XSSFSheet sheet1 = wb.getSheetAt(sheetNumber);	
		List<String> head = new ArrayList<String>();
		int rowCount = sheet1.getLastRowNum();
		int firstRowNum = 0;
		for (int currentRowNum = firstRowNum; currentRowNum <= rowCount; currentRowNum++) {
			XSSFRow row = sheet1.getRow(currentRowNum);
			for (int i = 0; i < sheet1.getRow(0).getLastCellNum(); i++) {
				if (currentRowNum == 0) {
					head.add(getStrValue(row.getCell(i)));
				} else {
					if (fieldValues.containsKey(head.get(i))) {
						XSSFCell cell = row.getCell((short) i);						
						try {
							cell.setCellValue(fieldValues.get(head.get(i))[currentRowNum-1]);
						} catch (NullPointerException e) {
							cell = row.createCell((short) i);
							cell.setCellValue(fieldValues.get(head.get(i))[currentRowNum-1]);
						}	
					}
				}
			}			
		}
		FileOutputStream fileOut = new FileOutputStream(filePath);
		wb.write(fileOut);
		fileOut.close();
	}	
	 
	public static String getStrValue(XSSFCell cell) {
		if (cell==null) {
			return "";
		}
		String strval = null;
		switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BOOLEAN:
				strval = cell.getBooleanCellValue() + "";
				break;
			case Cell.CELL_TYPE_FORMULA:
				strval = cell.getCellFormula();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					SimpleDateFormat sdf = null; 
			        if (cell.getCellStyle().getDataFormat() == HSSFDataFormat.getBuiltinFormat("h:mm")) { 
			          sdf = new SimpleDateFormat("HH:mm"); 
			        } else {// 日期 
			          sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			        } 
			        Date date = cell.getDateCellValue(); 
			        strval = sdf.format(date); 
				} else {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					strval = cell.getStringCellValue();
				}
				break;
			case Cell.CELL_TYPE_STRING:
				try {
					strval = cell.getStringCellValue();
				} catch (IllegalStateException e) {
					cell.setCellType(Cell.CELL_TYPE_STRING);
					strval = cell.getStringCellValue();
				}
				break;
					
			case Cell.CELL_TYPE_BLANK:  
		        return "";  
		}		
		if (null == strval || "NULL".equalsIgnoreCase(strval)) {
			strval = "";
		}
		char nbsp = 0x00a0;
		return StringUtils.trim(strval.replace(nbsp, ' '));
	}

	public static XSSFCellStyle getCellStyle(XSSFWorkbook workbook, boolean isHeader) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		if (isHeader) {
			style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			XSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style.setFont(font);
		}
		return style;
	}

	public static void generateHeader(XSSFWorkbook workbook, XSSFSheet sheet, String[] headerColumns) {
		XSSFCellStyle style = getCellStyle(workbook, true);
		Row row = sheet.createRow(0);
		row.setHeightInPoints(30);
		for (int i = 0; i < headerColumns.length; i++) {
			Cell cell = row.createCell(i);
			sheet.setColumnWidth(i, width);
			cell.setCellValue(headerColumns[i]);
			cell.setCellStyle(style);
		}
	}

	public static XSSFSheet creatExcelSheet(XSSFWorkbook workbook, String sheetName, String[] headerColumns, String[][] fieldValues) throws Exception {
		XSSFSheet sheet = workbook.createSheet(sheetName);		
		generateHeader(workbook, sheet, headerColumns);
		XSSFCellStyle style = getCellStyle(workbook, false);

		for (int i=1;i<=fieldValues.length;i++) {
			Row row = sheet.createRow(i);
			row.setHeightInPoints(25);
			String[] oneRowValues = fieldValues[i-1];
			for (int m=0;m<oneRowValues.length;m++) {
				String cellValue = oneRowValues[m];
				cellValue = null != cellValue ? cellValue.toString() : "";
				Cell cell = row.createCell(m);
				cell.setCellValue(cellValue);
				cell.setCellStyle(style);				
			}
		}
		return sheet;
	}
	
	public static void generateExcelFile(String fileName, String[] headerColumns, String[][] fieldValues) throws Exception {
		XSSFWorkbook workbook = new XSSFWorkbook();
		creatExcelSheet(workbook, fileName, headerColumns, fieldValues);
		FileOutputStream fileOut = new FileOutputStream(Utils.getImportExcelFilePath(fileName));
		workbook.write(fileOut);
		fileOut.close();
	}

}
	

