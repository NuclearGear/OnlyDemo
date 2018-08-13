package com.winmax.pages.attendance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.DataTableUtils;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

import cucumber.api.DataTable;

public class TimeManagePage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(TimeManagePage.class);
	
	public TimeManagePage() {
		PageFactory.initElements(driver, this);
	}
	
	
	//###############  时间管理>休假管理>休假申报输入 ###############
	/**
	 * 
	 * @param leaveCode
	 * @param startDate : if null, today date
	 * @param endDate: if null, today date
	 * @throws Exception
	 */
	public void addLeaveType(String leave, String startDate, String endDate) throws Exception {	
		inputBeignEndDatePostDate(leave, startDate, endDate, null);
		//click save
		saveLeaveSuccess();			
	}
	
	//输入开始时间，结束时间，过数日期
	public void inputBeignEndDatePostDate(String leave, String startDate, String endDate, String postDate) throws Exception {	
		String leaveCode = getLeaveCode(leave);
		waitFor(By.xpath("//tr[@id='" + leaveCode + "']//i[contains(@data-bind,'Add')]")).click();
		//input startDate
		By startInputBy = By.xpath("//input[contains(@data-bind,'StartDate')]");
		
		if (startDate!=null) {
			WebDriverUtils.inputDate(startInputBy, startDate, driver); 
		} 
		
		//input endDate
		By endInputBy = By.xpath("//input[contains(@data-bind,'EndDate')]");
		if (endDate!=null) {
			WebDriverUtils.inputDate(endInputBy, endDate, driver);	
		}
		startDate = (startDate==null? Utils.getTodayDate():startDate);
		
		By postInputBy = By.xpath("//input[contains(@data-bind,'PostingDate')]");
		if (postDate!=null) {
			WebDriverUtils.inputDate(postInputBy, postDate, driver);	
		}
	}
	
	//更新类型，开始时间，结束时间，备注
	public void fillOTDetails(String type, String startTime, String endTime, String note) throws Exception {	
		selectByVisibleText(By.id("0"), type);	
		WebDriverUtils.waitLoading(driver);
		
		//input startTime
		By startInputBy = By.id("StartTime0");		
		if (startTime!=null) {
			Utils.waitABit(1000);
			putInValue(startInputBy, startTime);
			waitLoading();
			Utils.waitABit(1000);
		} 
		
		//input endTime
		By endInputBy = By.id("EndTime0");
		if (endTime!=null) {
			putInValue(endInputBy, endTime);
			waitLoading();
			Utils.waitABit(1000);
		} 
		if (note!=null) {			
			putInValue(By.xpath("//table[@id='TranDetailDiv']/tbody/tr//input[contains(@data-bind,'Remark')]"), note);
		}
	}
	
	//检查小时数正确
	public void verifyTotalHour(String totalHour, String beginDate, String endDate) throws Exception {
		String xpath = "//tr[contains(@style,'table-row')]//table//"
				+ "tr[./td[contains(@data-bind,'StartDate')][normalize-space(text())='" + beginDate + "']]"
				+ "[./td[contains(@data-bind,'EndDate')][normalize-space(text())='" + endDate + "']]";
		String actualHour = find(waitFor(By.xpath(xpath)), By.xpath("./td[contains(@data-bind,'NoOfHours')]")).getText().trim();
		Assert.isEqual(totalHour, actualHour, "休假总小时数");
	}

	/**
	 * 编辑特定的休假代码， 和开始时间的休假单
	 * @param leaveCode
	 * @param startStr
	 * @throws Exception
	 */
	public void clickEditLeaveType(String leave, String startStr) throws Exception {
		String leaveCode = getLeaveCode(leave);
		expandLeaveType(leaveCode);
		startStr = (startStr==null? Utils.getTodayDate():startStr);
		String xpath = "//tr[starts-with(@id,'Detail" + leaveCode + "')]"
				+ "[./td[contains(@data-bind,'StartDate')][normalize-space(text()='" + startStr + "')]]"
				+ "/td/i[contains(@class,'pencil')]";	
		
		waitFor(By.xpath(xpath)).click();
		waitForVisible(By.xpath("//td[contains(@id,'Detail')]//button[normalize-space(text())='保存']"));
	}
	
	/**
	 * 展开特定的休假代码
	 * @param leaveCode
	 * @throws Exception
	 */
	public void expandLeaveType(String leave) throws Exception {
		String leaveCode = getLeaveCode(leave);
		WebElement tr = waitFor(By.id("dvTransation" + leaveCode));
		if (tr.getAttribute("style").contains("display: none")) {
			find(By.id(leaveCode + "Flag")).click();
			waitForVisible(By.id("dvDetailHead" + leaveCode));
		}
	}
	
	//根据 休假代码例如： 年假，得到休假代码NAL, 如果参数是休假代码， 则直接返回
	String getLeaveCode(String leave) {
		boolean isWord = leave.matches("[a-zA-Z]+");
		if (!isWord) {
			WebElement tr = waitFor(By.xpath("//tr[./td[contains(@data-bind,'Des')][normalize-space(text())='" + leave + "']]"));
			return tr.getAttribute("id").trim();
		} else {
			return leave;
		}
	}
	
	/**
	 * 编辑时，验证类型和小时
	 * @param dayType
	 * @param dayTime
	 * @throws Exception
	 */
	public void checkTypeAndHours(String[] dayType, String[] dayTime) throws Exception {
		for (int i=0; i<dayType.length; i++) {
			selectByVisibleText(By.id("0"), dayType[i].trim());		 
		    String time = find(By.xpath("//td[contains(@data-bind,'text:TotalOfHour')]")).getText().trim();
		    Assert.isEqual(time, dayTime[i], dayType[i] + " 类型的小时数不正确");
		}   
		find(By.xpath("//td[contains(@id,'Detail')]//button[contains(text(),'取消')]")).click();
	}
	
	/**
	 * 删除记录
	 * @param leaveCode
	 * @param startStr
	 * @throws Exception
	 */
	public void deleteLeaveType(String leave, String startStr) throws Exception {
		String leaveCode = getLeaveCode(leave);
		expandLeaveType(leaveCode);
		String xpath = "//tr[starts-with(@id,'Detail" + leaveCode + "')]"
				+ "[./td[contains(@data-bind,'StartDate')][normalize-space(text())='" + startStr + "']]"
				+ "/td/i[contains(@class,'trash')]";
		WebElement record = waitFor(By.xpath(xpath));
		scrollTo(record);
		record.click();
		//确认删除
		waitForVisible(By.xpath("//button[text()='确定']")).click();		
		String deleteText = waitForVisible(By.id("showP")).getText().trim();
		waitForVisible(By.xpath("//button[text()='确定']")).click();
		Assert.isEqual("删除成功！", deleteText, "删除休假单");
		waitForNotVisible(By.xpath(xpath));
	}
	
	public void deleteLeaveTypeifExist(String leave, String startStr) throws Exception {
		String leaveCode = getLeaveCode(leave);
		expandLeaveType(leaveCode);
		String xpath = "//tr[starts-with(@id,'Detail" + leaveCode + "')]"
				+ "[./td[contains(@data-bind,'StartDate')][normalize-space(text())='" + startStr + "']]"
				+ "/td/i[contains(@class,'trash')]";
		if (findAll(By.xpath(xpath)).size()==0) {
			return;
		}
		WebElement record = waitFor(By.xpath(xpath));
		scrollTo(record);
		record.click();
		//确认删除
		waitForVisible(By.xpath("//button[text()='确定']")).click();		
		String deleteText = waitForVisible(By.id("showP")).getText().trim();
		waitForVisible(By.xpath("//button[text()='确定']")).click();
		Assert.isEqual("删除成功！", deleteText, "删除休假单");
		waitForNotVisible(By.xpath(xpath));
	}
	
	/**
	 * verify start date,end date and holiday hour
	 * @param hhour：holiday hours
	 * @param stdate：start date
	 * @param eddate：end date
	 * @throws Exception
	 */
	public void verifyLeaveHours(String startDate, String endDate, String hours) throws Exception {
		String xpath = "//tr[./td[contains(@data-bind, Start)]"
				+ "[normalize-space(text())='" + startDate + "']]"
				+ "[./td[contains(@data-bind, End)]"
				+ "[normalize-space(text())='" + endDate + "']]";
		WebElement tr = waitFor(By.xpath(xpath));		
		
		//验证小时数"hour"
		String actualHour = find(tr, By.xpath("./td[contains(@data-bind,'NoOfHours')]")).getText().trim();
		Assert.isEqual(hours, actualHour); 
	}
	
	//保存 休假申报失败
	public void failToSaveLeave(String msg) throws Exception {
		find(By.xpath("//td[@id='dvtdTransationDetail']//button[contains(text(),'保存')]")).click();
		String failText = waitForVisible(By.xpath("//div[@id='ps_alert']/p")).getText().trim();		
		Assert.isContains(failText, msg, "message");	
		find(By.xpath("//div[./div[@id='ps_alert']]/div[contains(@class,'button')]//button")).click();
	}
	
	public void saveLeaveSuccess() throws Exception {
		find(By.xpath("//td[@id='dvtdTransationDetail']//button[normalize-space(text())='保存']")).click();
		String deleteText = waitFor(By.id("showP")).getText().trim();
		Assert.isEqual("操作成功!", deleteText, "成功增加休假单");
		waitFor(By.xpath("//div[@class='ui-dialog-buttonset']//button")).click();	
	}
	
	//检查特定的休假，不存在每个时间点的记录
	public void verifyNoRecords(String startDate, String endDate) throws Exception {
		String xpath = "//tr[./td[contains(@data-bind, Start)]"
				+ "[normalize-space(text())='" + startDate + "']]"
				+ "[./td[contains(@data-bind, End)]"
				+ "[normalize-space(text())='" + endDate + "']]";
		Assert.notPresent(By.xpath(xpath), "There is not record with date between " + startDate + " and " + endDate, driver);
	} 
	
	//###############  时间管理>分析统计>计算时间修定  ###############	
	/**
	 * Search by the staff number, start date and end date. if StaffNo=N/A, 不输入staff no
	 * @param sdate：Start date
	 * @param edate：End date
	 * @param num：The staff number
	 * @throws Exception
	 */
	public void searchRecord(String sdate, String edate, String staffNo) throws Exception {
		inputDateSearchCriteria(sdate, edate);
		//选择工号员工	
		if (!staffNo.equals("N/A")) {  //taffNo=N/A, 不输入staff no
			WebDriverUtils.inputStaffNoWhenSearch(staffNo, driver);	
		}
		//查询
		find(By.id("btnSearch")).click();
		waitLoading();
	}
	
	public void importCorrectionRecord(String fileName) throws Exception {
		waitFor(By.xpath("//div[@id='ehr-xsbd']//a[contains(@onclick,'Import')]")).click();
		WebDriverUtils.importFile(fileName, driver);
		WebDriverUtils.verifyProcessImportFile(driver);
	}
	
	public void inputDateSearchCriteria(String sdate, String edate) throws Exception {
		//输入开始日期
		waitFor(By.id("dvStartDate")).clear();
		waitFor(By.id("dvStartDate")).sendKeys(sdate);
		
		//输入结束日期
		waitFor(By.id("dvEndDate")).clear();
		waitFor(By.id("dvEndDate")).sendKeys(edate);
	}	
	
	public void exportCorrection(String downloadFileName) throws Exception {
		waitFor(By.xpath("//a[contains(@onclick,'Import')]/i[contains(@class,'export')]")).click();
		waitForVisible(By.xpath("//div[@id='divDetail']//button[text()='导出']")).click();
		WebDriverUtils.waitForDownloadFileByName(downloadFileName);
		find(By.xpath("//div[./span[text()='导出']]/button")).click();
	}
	
	public void verifyCorrectionExportDataCompareWithUI(List<LinkedHashMap<String, String>> excelData) throws Exception {		
		for (int i=0;i<excelData.size();i++) {
			String trXpath = "//*[@id='tbStaffDaily_tableColumnClone']/tbody/tr[" + (i+1) + "]";
			verifyUIDataWithExcelByRow(trXpath, excelData.get(i));
		}
	}
	
	//验证记录导入后的数据比对
	private void verifyUIDataWithExcelByRow(String trXpath, HashMap<String, String> excelData) throws Exception { 
		HashMap<String, String> uiData = WebDriverUtils.getRowData(trXpath, driver);		
		Set<String> keys = excelData.keySet();
		for (String k : keys) {
			if (k.equals("默认周期")) {				
				boolean defaultVal = find(By.xpath(trXpath + "/td[5]/input")).isSelected();
				if (excelData.get(k).contains("否")) {
					Assert.isTrue(!defaultVal, "默认周期值");
				} else {
					Assert.isTrue(defaultVal, "默认周期值");
				}				
			} else {
				Assert.isEqual(excelData.get(k), uiData.get(k), "列名： " + k + " 数据显示不正确");
			}
		}
	}	
	
	public void advanceSearchRecord(String staffNo) throws Exception {
		WebDriverUtils.advanceSearchById(staffNo, driver);

		//查询
		find(By.id("btnSearch")).click();
		waitLoading();
	}
	
	//检查计算时间修订页面，按开始时间，结束时间搜索的记录时间范围正确
	public void verifyAttendanceSearchResultDateRange(String startDate, String endDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = df.parse(startDate);
        Date dt2 = df.parse(endDate);
        List<WebElement> dateColumns = waitForMinimumNum(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr/td[4]"), 1);
        for (WebElement dateRecord : dateColumns) {
        	scrollTo(dateRecord);
        	String actual = dateRecord.getText().trim();
        	Date dtActual = df.parse(actual);
            boolean flag =(dtActual.getTime() >= dt1.getTime()) && (dtActual.getTime() <= dt2.getTime());
            Assert.isTrue(flag, "日期范围不正确");
        }    
	}
	
	//检查计算时间修订页面，按雇员搜索的记录正确
	public void verifyAttendanceSearchResult(String column, String value) throws Exception {
		int index  = (column.equals("雇员编号")? 2:3);
		List<WebElement> staffColumns = waitForMinimumNum(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr/td[" + index + "]"), 1);
        for (WebElement staff : staffColumns) {
        	Assert.isEqual(value, staff.getText().trim());
        }
	}
	
	//选中“列表 ” checkbox
	public void selectListCheckbox() throws Exception {
		checkChkRadio(By.id("ShowList"), true);
	}
	
	//验证左侧的List 列表
	public void verifyListInLeftFrame() throws Exception {
		String staffNo = waitForVisible(By.xpath("//div[@id='staff']/table/thead/tr/td[1]")).getText().trim();
		String staffName = waitForVisible(By.xpath("//div[@id='staff']/table/thead/tr/td[2]")).getText().trim();
		Assert.isEqual("员工编号", staffNo, "List header column 1");
		Assert.isEqual("员工姓名", staffName, "List header column 2");
	}
	
	//根据 员工编号或者员工姓名选择一行 (从中间区域选择第二行数据的staff)
	public String selectLineInListInLeftArea() throws Exception {
		String staffNo = waitFor(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr[2]/td[2]")).getText().trim();
		find(By.xpath("//tbody[contains(@data-bind,'StaffList')]//tr/td[normalize-space(text())='" + staffNo + "']")).click();
		waitLoading();
		return staffNo;
	}
	
	//验证当在左侧列表页面点击一行记录后，第一行的数据在中间区域显示正确，
	public void verifyRecordInMainArea(String staffNo) throws Exception {
		String actualStaffNo = waitFor(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr[1]/td[2]")).getText().trim();
		Assert.isEqual(staffNo, actualStaffNo, "验证当在左侧列表页面点击一行记录后，第一行的数据在中间区域显示正确");
	}
	/**
	 * verify Employee holidays and hours
	 * @param hotype：vacation request menu
	 * @param hohour：holiday hours
	 * @param pnumber：staff number
	 * @throws Exception
	 */
	public void verifyRecordData(String date, String expectedType, String expectedHour) throws Exception {
		String xpath = "//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr[./td[contains(@class,'itemsdate')]"
				+ "[normalize-space(text())='" + date + "']]";
		WebElement leaveType = find(By.xpath(xpath + "/td[contains(@data-bind,'LeaveCode')]"));
		WebElement hour = find(By.xpath(xpath + "/td[contains(@data-bind,'TotalOfHour')]"));
		
		//verify type
		Assert.isEqual(expectedType, leaveType.getAttribute("innerText").trim());

		//verify hour		
		Assert.isEqual(expectedHour, hour.getAttribute("innerText").trim());
	}	
	
	public void clickOnMenu(String menu) throws Exception {
		String xpath = "//a[normalize-space(.)='" + menu + "']";
		waitFor(By.xpath(xpath)).click();
	}

	//点击员工编号的超链接，打开每日时间修正窗口
	public void clickSaffNolink(String date) throws Exception {		
		String xpath = "//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr[./td[contains(@class,'itemsdate')]"
				+ "[normalize-space(text())='" + date + "']]";
		WebElement link = waitFor(By.xpath(xpath + "/td[@class='itemstaffno']"));
		scrollTo(link);
		link.click();
		waitForVisible(By.xpath("//span[text()='每日计算时间修正']"));
	}
	
	//更改考勤数据，如果time的值是N/A，则 不更改该时间
	public void changeAttendance(String time1, String time2) throws Exception {
	 	waitForVisible(By.id("tbDetail"));
	 	if (!time1.equals("N/A")) {
	 		putInValue(By.id("dvStart0display"), time1);
	 	}
	 	if (!time2.equals("N/A")) {
	 		putInValue(By.id("dvEnd0display"), time2);
	 	}
		find(By.id("savebtn")).click();		
		String text = waitFor(By.id("showP")).getText().trim();
		Assert.isEqual("保存成功", text, "修改考勤数据成功");		
		waitFor(By.xpath("//div/button[normalize-space(text())='确定']")).click();
	}
	
	//删除考勤数据
	public void deleteAttendanceIfExist() throws Exception {
	 	waitForVisible(By.id("tbDetail"));
	 	String comeTime = waitFor(By.id("dvStart0display")).getAttribute("value").trim();
	 	String goTime = waitFor(By.id("dvEnd0display")).getAttribute("value").trim();
	 	if (comeTime.isEmpty()&&goTime.isEmpty()) {
	 		return;
	 	}
	 	waitFor(By.xpath("//tbody[contains(@data-bind,'DailyDetails')]//i[contains(@class,'trash')]")).click();	 	
		find(By.id("savebtn")).click();		
		String text = waitFor(By.id("showP")).getText().trim();
		Assert.isEqual("保存成功", text, "修改考勤数据成功");		
		waitFor(By.xpath("//div/button[normalize-space(text())='确定']")).click();
	}
	
	//重新分析考勤数据
	public void clikAnalyze() throws Exception {
		waitFor(By.xpath("//div[@id='dvEditStaffDaily']//a[contains(@onclick,'Analyst')]")).click();
		String text = waitFor(By.id("showP")).getText().trim();
		Assert.isEqual("分析成功", text, "分析考勤数据成功");		
		waitFor(By.xpath("//div/button[normalize-space(text())='确定']")).click();
	}
	
	public void closeAnalysisDialog() {
	    waitFor(By.xpath("//div[./span[text()='每日计算时间修正']]/button")).click();
	    Utils.waitABit(3000);
		waitForNotVisible(By.id("dvEditStaffDaily"));
	}
	
	//检查工作小时 和加班1字段的值
	public void verifyWorkingHoursOTHours(String date, String whour, String otHour) throws Exception {
		String xpath = "//table[@id='tbStaffDaily_tableColumnClone']/tbody/tr[./td[contains(@class,'itemsdate')]"
				+ "[normalize-space(text())='" + date + "']]";
		String workHourUI = waitFor(By.xpath(xpath + "/td[contains(@data-bind,'WorkHour')]")).getAttribute("innerText").trim();
		String otHourUI = waitFor(By.xpath(xpath + "/td[contains(@data-bind,'fnOT1')]")).getAttribute("innerText").trim();
		Assert.isEqual(whour, workHourUI, "工作小时");
		Assert.isEqual(otHour, otHourUI, "加班1字段");
	}
	
	//设定显示列并保存-新加显示列
	public void addNewColumn(String column) throws Exception {
		waitForVisible(By.id("originalColumns"));
		WebElement columnEle = find(By.xpath("//fieldset[@id='originalColumns']//li/span[normalize-space(text())='" + column + "']"));
		scrollTo(columnEle);
		Actions action = new Actions(driver);
		WebElement selectedColumnsArea = find(By.xpath("//fieldset[@id='selectedColumns']//li[1]"));
		action.moveToElement(columnEle).dragAndDrop(columnEle, selectedColumnsArea).build().perform();
		Utils.waitABit(1000);
		waitFor(By.xpath("//fieldset[@id='selectedColumns']//li/span[normalize-space(text())='" + column + "']"));
		WebElement saveBtn = find(By.id("btnSave"));
		scrollTo(saveBtn);
		saveBtn.click();
		waitLoading();
		//等待页面重新load
		Utils.waitABit(2000);
		waitLoading();
	}
	
	//设定显示列并保存-移除显示列
	public void removeExistingColumn(String column) throws Exception {
		waitForVisible(By.id("originalColumns"));
		WebElement columnEle = find(By.xpath("//fieldset[@id='selectedColumns']//li/span[normalize-space(text())='" + column + "']"));
		scrollTo(columnEle);
		Actions action = new Actions(driver);
		WebElement originalColumnsArea = find(By.xpath("//fieldset[@id='originalColumns']//li[1]"));
		action.moveToElement(columnEle).dragAndDrop(columnEle, originalColumnsArea).build().perform();
		Utils.waitABit(1000);
		waitFor(By.xpath("//fieldset[@id='originalColumns']//li/span[normalize-space(text())='" + column + "']"));
		WebElement saveBtn = find(By.id("btnSave"));
		scrollTo(saveBtn);
		saveBtn.click();
		waitLoading();
		//等待页面重新load
		Utils.waitABit(2000);
		waitLoading();
	}
	
	//验证设定的列显示或者不显示在页面
	public void verifyColumnExistOrNot(String column, boolean flag) throws Exception {
		List<WebElement> headerEleList = findAll(By.xpath("//table[@id='tbStaffDaily_tableFixClone']/thead/tr/td"));
		List<String> headerList = new ArrayList<String>();
     	for (WebElement ele : headerEleList) {
     		headerList.add(ele.getAttribute("innerText").trim().replace("\n", " "));
     	}
     	if (flag) {
     		Assert.isTrue(headerList.indexOf(column)!=-1, "计算时间修定页面应该显示列名： " + column);
     	}
	}
	
	//################# 时间管理>休假结余>休假结余设立 ###################

	
	//###############  时间管理>加班调休>加班单维护   ###############	
	private String getRowXpath(String staffNo, String date, String beginTime, String endTime) {
		String row_xpath = "//table[@id='tbOtTranMaintenance']/tbody/tr[./td[normalize-space(text())='" + staffNo + "']]";
		if (date != null) {
			row_xpath = row_xpath + "[./td[normalize-space(text())='" + date + "']]";
		}
		if (beginTime != null) {
			row_xpath = row_xpath + "[./td[contains(text(),'" + beginTime + "')]]";
		} 
		if (beginTime != null) {
			row_xpath = row_xpath + "[./td[contains(text(),'" + endTime + "')]]";
		} 
		return row_xpath;
	}
	
	public void addOT(String staffNo, String date, String beginTime, String endTime) throws Exception {
		//新增加班单
		waitForVisible(By.xpath("//div[@id='dvIndex']//a[contains(@data-bind,'Add')]")).click();
		fillInOTFields(staffNo, date, beginTime, endTime);
		saveOT();
	}
	
	public void saveOT() {
		//点击保存弹出提示
		waitFor(By.xpath("//*[@id='dvEditOt']//button[contains(text(),'保存')]")).click();
		String save_msg = waitFor(By.xpath(".//*[@id='showP']")).getText().trim();
		Assert.isEqual("保存成功！", save_msg, "增加/修改加班单成功");
		waitFor(By.xpath("//div[@class='ui-dialog-buttonset']/button[normalize-space(text()='确定')]")).click();
	}
	
	public void fillInOTFields(String staffNo, String date, String beginTime, String endTime) throws Exception {
		//选择员工
		if (staffNo!= null) {
			WebDriverUtils.inputStaffNoWhenSearch("//tr[@id='dvEditOt']", staffNo, driver);
		}
		//输入加班日期
		if (date != null) {
			putInValue(waitFor(By.id("inpDate")), date);
		}
				
		//维护开始时间、结束时间 
		if (beginTime != null) {
			putInValue(find(By.id("dvStartDatedisplay")), beginTime);
		}
		
		if (endTime != null) {
			putInValue(find(By.id("dvEndDatedisplay")), endTime);
		}
	}
	
	public void clickEditOT(String staffNo, String date, String beginTime, String endTime) throws Exception {
		WebElement row = find(By.xpath(getRowXpath(staffNo, date, beginTime, endTime)));
		find(row, By.xpath(".//i[contains(@class,'pencil')]")).click();
		waitForVisible(By.xpath("//*[@id='dvEditOt']//button[contains(text(),'保存')]"));
	}
	
	public void quickSearchOT(String date1, String date2, String staffNo) throws Exception {
		waitFor(By.id("SelDateStartPicker")).sendKeys(date1);
		waitFor(By.id("SelDateEndPicker")).sendKeys(date2);
		WebDriverUtils.inputStaffNoWhenSearch(staffNo, driver);
		find(By.id("btnSearch")).click();
		waitForNotVisible(By.xpath("//img[contains(@src, 'loading')]"));
		waitFor(By.xpath(getRowXpath(staffNo, null, null, null)));
	}
	
	public void verifyOT(String staffNo, String date, String beginTime, String endTime) throws Exception {
		waitFor(By.xpath("//table[@id='tbOtTranMaintenance']/tbody/tr/td[1]"));
		try {
			waitFor(By.xpath(getRowXpath(staffNo, date, beginTime, endTime)));
		} catch (Exception e) {
			Assert.fail("No OT record with staff " + staffNo + ", date: " + date + ", time between " + beginTime + " and " + endTime);
		}
		
	} 
	
	public void verifyOT(HashMap<String, String> map) throws Exception {
		waitFor(By.xpath("//table[@id='tbOtTranMaintenance']/tbody/tr/td[1]"));
		List<WebElement> head = findAll(By.xpath("//table[@id='tbOtTranMaintenance']/thead/tr/td"));
		List<String> head_vals = getListElementsText(head);
		String staffNo = map.get("员工编号");
		String date = Utils.getDate(map.get("加班日期"));
		String beginTime = map.get("开始时间");
		String endTime = map.get("结束时间");
		WebElement tr = null;
		try {
			tr = waitFor(By.xpath(getRowXpath(staffNo, date, beginTime, endTime)));
		} catch (Exception e) {
			Assert.fail("No OT record with staff " + staffNo + ", date: " + date + ", time between " + beginTime + " and " + endTime);
			return;
		}
		Set<String> keys = map.keySet();
		for (String column : keys) {
			if ((!column.equals("员工编号")) && (!column.equals("加班日期")) &&(!column.equals("开始时间")) &&(!column.equals("结束时间"))) {
				int index = head_vals.indexOf(column) + 1;
				String actual = find(tr, By.xpath("./td[" + index  + "]")).getText().trim();
				Assert.isEqual(map.get(column), actual, column);
			}
		}
		
	} 
	
	
	//############ 时间管理>排班管理>下属排班管理  ##########
	/**
	 * 高级筛选0042
	 */
	public void advanceSearch(String staffno) throws Exception {
		WebDriverUtils.advanceSearchById(staffno, driver);
	}

	//时间管理>排班管理>下属排班管理页面，双击日历表上的日期
	public void doubleClickOnDate(String day) throws Exception {	
		Select month = new Select(waitFor(By.id("selectMonth_calendar")));
		String currentMonth = month.getFirstSelectedOption().getText().trim();
		String selectMonth = Utils.getDate(day).split("-")[1].trim();
		if (selectMonth.startsWith("0")) {
			selectMonth = selectMonth.substring(1);
		}
		if (!selectMonth.equals(currentMonth)) {
			selectByVisibleText(By.id("selectMonth_calendar"), selectMonth);
			waitLoading();
		}
		String xpath = "//div[@id='staffShiftDailyCalendar']//td[@data-date='" + day + "']//div/span[1]";
		WebElement dateCell = waitFor(By.xpath(xpath));
		scrollTo(dateCell);
		Actions action = new Actions(driver); 
		action.moveToElement(dateCell).doubleClick().perform();	
		waitLoading();
	}
	
	public void editRest(String shift) throws Exception {
		WebElement popup = waitFor(By.xpath("//span[contains(text(),'班值调整')]"));
		scrollTo(popup);
		if (waitFor(By.id("divShiftId1display")).getAttribute("value").equals(shift)) {
			find(By.xpath("//button[text()='取消']")).click();
			return;
		}
		Actions action = new Actions(driver);
		waitFor(By.xpath("//div[@id='divShiftId1ShowWindow']/span")).click();
		WebElement input = waitFor(By.id("divShiftId1search"));		
		action.sendKeys(input, shift).build().perform();
		Utils.waitABit(2000);
		waitFor(By.xpath("//div[@id='divShiftId1grid']/table/tbody/tr[1]/td[text()='" + shift + "']")).click();
		//点击保存
		waitFor(By.xpath("//button[text()='保存']")).click();	
		WebDriverUtils.confirmSave(driver);		
		waitFor(By.xpath("//div[@id='divSSDDetailModel']//button[contains(text(),'取消')]")).click();
	}
	
	//班值调整
	public void editShiftValues(String shift, String isHolidayStr, String beforeOTShift, String OTshift, String afterOTShift) throws Exception {
		Actions action = new Actions(driver);
		//班值
		if (shift!=null) {
			waitFor(By.xpath("//div[@id='divShiftId1ShowWindow']/span")).click();
			WebElement input = waitFor(By.id("divShiftId1search"));
			action.sendKeys(input, shift).build().perform();
			Utils.waitABit(2000);
			waitFor(By.xpath("//div[@id='divShiftId1grid']/table/tbody/tr[1]/td[text()='" + shift + "']")).click();
		}
		//假期
		if (isHolidayStr!=null) {
			boolean isHoliday = isHolidayStr.toLowerCase().equals("true");
			checkChkRadio(By.id("isHoliday"), isHoliday);
		}
		//上班前加班值1,2,3   
		if(beforeOTShift!=null) {
			selectByVisibleText(By.id("beforeOT"), beforeOTShift);
		}
		//中班前加班值1,2,3   
		if(OTshift!=null) {
			selectByVisibleText(By.id("middleOT"), OTshift);
		}
		//下班前加班值1,2,3   
		if(afterOTShift!=null) {
			selectByVisibleText(By.id("afterOT"), afterOTShift);
		}
		//点击保存
		waitFor(By.xpath("//button[text()='保存']")).click();	
		WebDriverUtils.confirmSave(driver);		
		waitFor(By.xpath("//div[@id='divSSDDetailModel']//button[contains(text(),'取消')]")).click();
	}
	
	public void clickAdd(String flow) throws Exception {
		waitFor(By.xpath("//ul//li//a[text()='流程状态:']"));
		WebElement link1 = find(By.xpath("//ul//li//a[@onclick='openUrl();']"));
		openNewWindowWith(link1);
		WebElement link = waitFor(By.xpath("//tr//td//a[contains(@data-bind,'Chinese')][text()='" + flow + "']"));
		WebDriverUtils.scrollTo(link, driver);
		link.click();
		waitLoading();
	}
	
	//############ 休假申请表单，请假日是休息班值，系统提示“参考考勤，无排班”##########
	public void selectPeriod(String period, String detail) throws Exception {
		if (period!= null) {
			waitFor(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
			waitFor(By.xpath("//td[text()='" + period + "']")).click();
		}
		if (detail != null) {
			waitFor(By.xpath("//div[@id='dvPeriodDetailShowWindow']/span")).click();
			waitFor(By.xpath("//td[text()='" + detail + "']")).click();
		}
		waitFor(By.id("btnSearch")).click();
	}
	
	public void verifyLabel(String label,String frozen) throws Exception {
		String ptext = waitFor(By.xpath("//tbody//td[@data-bind='text:PostStatus']")).getText().trim();
		logger.info(ptext);
		Assert.isEqual(label, ptext);
		//验证已冻结
		String frozenText = waitFor(By.xpath("//tr//td[contains(@data-bind,'text:Freeze')]")).getText().trim();
		logger.info(frozenText);
		Assert.isEqual(frozen, frozenText);
	}
	
	public void updateTime(String startTime,String endTime) throws Exception {
		if (startTime!= null) {
			putInValue(waitFor(By.id("dvStartDate")), startTime);
		}
		if (endTime != null) {
			putInValue(waitFor(By.id("dvEndDate")), endTime);
		}
	}
	
	public void verifyText(String label) throws Exception {
		String text = waitFor(By.xpath("//tr//td[@data-bind='text:PostStatus']")).getText().trim();
		logger.info(text);
		Assert.isEqual(label, text);
	}
	
	public void verifyNoDeleteButton(String startTime, String type) throws Exception {
		//get id by leave type		
		String id = waitFor(By.xpath("//table[@id='leaveDiv']//tr[./td[normalize-space(text())='" + type + "']]")).getAttribute("id");
		//验证编辑元素
		String tr_xpath = "//tr[contains(@id,'Detail" + id + "')][./td[contains(@data-bind,'StartDate')][text()='" + startTime + "']]";
		WebElement tr = waitFor(By.xpath(tr_xpath));
		try {
			waitForVisible(By.xpath("./td[contains(@data-bind, 'Delete')]/i[contains(@style,'none')]"));
		} catch (Exception e) {
			Assert.fail("删除按钮应该不存在， 时间  " + startTime + ", 类型 " + type);
		}
	}
	
	public void inputOtInformation(String date, String beginTime, String endTime) throws Exception {
		if (date != null) {
			putInValue(waitFor(By.id("inpDate")), date);
		}
		if (beginTime != null) {
			putInValue(waitFor(By.id("dvStartDatedisplay")), beginTime);
		}
		if (endTime != null) {
			putInValue(waitFor(By.id("dvEndDatedisplay")), endTime);
		}
		String save = "//tr[@id='dvEditOt']//button[normalize-space(text())='保存']";
		waitFor(By.xpath(save)).click();
	}
	
	public void verifyTheDate(String staff, String date, String beginTime, String endTime) throws Exception {
		String table = "//table[@id='tbOtTranMaintenance']";
		if(staff != null){
			String thstaff = waitFor(By.xpath(table + "//td[contains(@data-bind,'Staff.StaffNo')][text()='" + staff + "']")).getText().trim();
			Assert.isEqual(staff, thstaff);	
		}
		if(date != null){
			String thdate = waitFor(By.xpath(table + "//td[contains(@data-bind,'dateReadOnly:Date')][text()='" + date + "']")).getText().trim();
			Assert.isEqual(date, thdate);
		}
		if(date != null){
			String begintime = waitFor(By.xpath(table + "//td[contains(@data-bind,'TimeSpanBind(TimeRange,1)')][contains(text(),'" + beginTime + "')]")).getText().trim();
			Assert.isEqual(beginTime, begintime);	
		}
		if(date != null){
			String endtime = waitFor(By.xpath(table + "//td[contains(@data-bind,'TimeSpanBind(TimeRange,2)')][contains(text(),'" + endTime + "')]")).getText().trim();	
			Assert.isEqual(endTime, endtime);	
		}
	}
	
	//###############  时间管理>加班调休>加班对比过数   ###############
	public void countDateAndStaff(String staffNo, String begindate, String enddate) throws Exception {
		//维护
		if (begindate != null) {
			putInValue(waitFor(By.id("startDate")), begindate);
		}
		if (enddate != null) {
			putInValue(waitFor(By.id("endDate")), enddate);
		}
		WebDriverUtils.advanceSearchById(staffNo,driver);
		waitFor(By.id("btnSearch")).click();
		String staffno = waitFor(By.xpath(".//table[@id='MyTable_tableColumnClone']//td[text()='" + staffNo + "']")).getText().trim();
		Assert.isEqual(staffNo, staffno);
		clickCountButton();
	}
	
	public void clickCountButton() throws Exception {
		String count = "//a[normalize-space(text())='过数']";
		waitFor(By.xpath(count)).click();
	}
	
	//点击提示中的确定
	public void confirmButtonSuccess(String prompt) throws Exception {
		String showP = "//div[@id='ps_alert']/p[text()='" + prompt + "']";
		String save_msg = waitFor(By.xpath(showP)).getText().trim();
		Assert.isEqual(prompt, save_msg);
		waitFor(By.xpath("//div[@class='ui-dialog-buttonset']/button[normalize-space(text()='确定')]")).click();
	}
	
	//###############  时间管理>分析统计>考勤时间分析  ###############
	//分析， force： true则为强制分析
	public void analyseStaffBetween(String staffNo, String begindate, String enddate, boolean force) throws Exception {
		String begin = "//input[contains(@data-bind,'StartDate')]";
		String end = "//input[contains(@data-bind,'EndDate')]";
		if(begindate != null) {
			putInValue(waitFor(By.xpath(begin)), begindate);
		}
		if(enddate != null) {
			putInValue(waitFor(By.xpath(end)), enddate);
		}		
		//搜索员工
		if(staffNo!=null) {
			WebDriverUtils.advanceSearchById(staffNo, driver);
		}
		//强制分析
		if(force) {
			checkChkRadio(By.xpath("//div[@id='div_analyse_bind']//input[contains(@data-bind,'Force')]"), true);
		} 
		//点击‘分析’按钮后弹出的提示窗口
		String analyse = "//input[@value='分析']";
		waitFor(By.xpath(analyse)).click();
		try {
			waitLoading();
		} catch (Exception e) {
			waitLoading();
		}
	}
	
	//计算时间修定页面，验证值
	public void verifyAnalyseResult(HashMap<String, String> data) throws Exception {
		String date = Utils.getDate(data.get("日期"));
		String trxpath = "//table[@id='tbStaffDaily_tableColumnClone']//tbody//tr[1]"; 
		if (date!=null) {
			trxpath = "//table[@id='tbStaffDaily_tableColumnClone']//tr[./td[normalize-space(text())='" + date + "']]";
		}
		WebElement tr = waitFor(By.xpath(trxpath));
		scrollTo(tr);
		List<WebElement> headerEleList = findAll(By.xpath("//table[@id='tbStaffDaily_tableFixClone']/thead/tr/td"));
		List<String> headerList = new ArrayList<String>();
     	 for (WebElement ele : headerEleList) {
     		headerList.add(ele.getAttribute("innerText").trim().replace("\n", " "));
     	 }
		Set<String> keySet = data.keySet();
		String message = "不正确的栏目： ";
		boolean flag = true;				
		for (String key : keySet) {
			String value = data.get(key);
			if (!key.equals("日期")) {
				int index = headerList.indexOf(key)+1;
				if (index==0) {
					Assert.fail(key + " 不存在该栏目");
				}
				String actual = tr.findElement(By.xpath("./td[" + index + "]")).getAttribute("innerText").trim();
				if (!actual.equals(value)) {
					flag = false;
					message = message + "[" + key + "]的值不正确，预期是： " + value + ", 实际的值是： " + actual + "; ";
				}
			}
		}
		Assert.isTrue(flag, message);
	}
	
	public void analyseResultShouldBe(String staff, String analyse, String date, String overtime, String overtime2,  String overtime3) throws Exception {
		WebElement tr = waitFor(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']//tr[./td[normalize-space(text())='" + date + "']]"));
		scrollTo(tr);
		if(staff != null){
			String actualStaff = waitFor(tr, By.xpath("./td[contains(@data-bind,'Staff.StaffNo')]")).getText().trim();
			Assert.isEqual(staff, actualStaff, "雇员编号");
		}
		if(analyse != null){
			String actualAnalyse = waitFor(tr, By.xpath("./td[contains(@data-bind,'GetTranslateStatus')]")).getText().trim();
			Assert.isEqual(analyse, actualAnalyse, "分析状态为");
		}		
		scrollTo(find(By.xpath("//*[@id='tbStaffDaily_tableHeadClone']/thead/tr/td[14]")));
		if(overtime != null){
			WebElement ot1 = waitFor(tr, By.xpath("./td[contains(@data-bind,'fnOT1')]"));
			scrollTo(ot1);
			String actualOT = ot1.getText().trim();
			Assert.isEqual(overtime, actualOT, "加班时间");
		}
		if(overtime2 != null){
			WebElement ot2 = waitFor(tr, By.xpath("./td[contains(@data-bind,'fnOT2')]"));
			scrollTo(ot2);
			String actualOT = ot2.getText().trim();
			Assert.isEqual(overtime2, actualOT, "加班时间2");
		}
		if(overtime3 != null){
			WebElement ot3 = waitFor(tr, By.xpath("./td[contains(@data-bind,'fnOT3')]"));
			scrollTo(ot3);
			String actualOT = ot3.getText().trim();
			Assert.isEqual(overtime3, actualOT, "加班时间3");
		}
	}
	
	//计算时间修订页面，点击确定打勾
	public void selectConfirmCheck() throws Exception {
		WebDriverUtils.acceptAllConfirmations(driver);
		find(By.xpath("//a[contains(text(),'确认打钩')]")).click();
		waitLoading();
	}
	
	//计算时间修订页面，选中有checkbox的栏目
	public void checkColumnCheckbox(String checkColumn, String staff, String date) throws Exception {
		WebElement tr = waitFor(By.xpath("//table[@id='tbStaffDaily_tableColumnClone']//tr[./td[normalize-space(text())='" + date + "']]"));
		if (checkColumn.equals("确认加班")){
			WebElement check = tr.findElement(By.xpath("./td/input[contains(@data-bind,'AllowOt')]"));
			scrollTo(check);
			checkChkRadio(check, true);
		} else {
			Assert.fail("未实现其他栏目");
		}
	}
	
//############休假政策复制，能复制休假代码##########	
	 //新增休假政策
	public void vacation_policy(String VPolicy, String VCopy, String VType, String VDepict) throws Exception{
		 //新增
		 waitFor(By.xpath("//div[@id='PolicysListDiv']/div[1]/div/nav/ul/li/a")).click();
		 //给休假政策赋值
		 waitFor(By.xpath("//input[contains(@data-bind,'LeavePolicy')]")).sendKeys(VPolicy);
		 //点击复制休假政策下拉框
		 waitFor(By.xpath("//select[@id='CopyId']")).click();
		 //点击休假政策
		 waitForVisible(By.xpath("//select[@id='CopyId']/option[text()='"+VCopy+"']")).click();
		 //给休假类型赋值
		 waitFor(By.xpath("//input[contains(@data-bind,'PlanCodeSecurity')]")).sendKeys(VType);
		//给休假政策描述赋值
		 waitFor(By.xpath("//input[contains(@data-bind,'Description.Chinese')]")).sendKeys(VDepict);
		 //点击保存
		 waitFor(By.xpath("//button[contains(@data-bind,'$root.Save')][normalize-space(text()='保存')]")).click();
		 //验证新增成功
		 String prompt = waitFor(By.xpath("//p[@id='showP'][normalize-space(text()='成功')]")).getText().trim();
		 logger.info("print prompt text :" +prompt);
		 Assert.isEqual("成功", prompt);
		 waitForVisible(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();

	 }
       
	//搜索新增好的休假政策
    public void searchVacationPolicy(String vacation_policy) throws Exception{
    	waitFor(By.xpath("//input[@id='Filter']")).sendKeys(vacation_policy);
    	waitForVisible(By.xpath("//a[contains(@data-bind,'Search')]/span[normalize-space(text()='查找')]")).click();
        WebDriverUtils.waitLoading(driver);
    }
     //验证页面数据 total number
    public void searchTotal(String total) throws Exception{
    	java.util.List<WebElement> tr_list = findAll(By.xpath("//div[@id='codeListDiv']//table/tbody//tr"));
        logger.info("当前页面复制的总数为  :" +tr_list.size());
        Assert.isEqual(Integer.valueOf(total), tr_list.size());
        
    }
    //获取NAL所在的行，点击编辑按钮
    public void updateVacationCode(String vacationcode) throws Exception{
       WebElement tr = waitFor(By.xpath(".//div[@id='codeListDiv']/div[2]/div/div/div/table/tbody//td[contains(@data-bind,'LeaveBenefitCode')][text()='"+vacationcode+"']"));
       find(tr,By.xpath("//div[@id='codeListDiv']//a/i")).click();

    }
    //验证休假政策是:之前新增的休假政策的休假政策描述
    public void checkPolicy(String policy) throws Exception{
    	String Vacationcode = waitFor(By.xpath("//select[@id='Id']/option[text()='" +policy+ "']")).getText().trim();
    	Assert.isEqual(policy, Vacationcode);
    	logger.info("休假政策为 :" +Vacationcode);
    	waitFor(By.xpath("//div[@id='editorDiv']/fieldset[4]//button[2][normalize-space(text()='取消')]")).click();
    
    }
	//############### 时间管理>排班管理>原始打卡记录 ################ 
    //快速搜索原始打卡记录
    public void quickSearchAttendanceCard(String staff, String card, String dateFrom, String dateTo) throws Exception {
		if (staff!=null) {
			waitFor(By.xpath("//div[@id='SelectStaffNoShowWindow']/span")).click();
			putInValue(By.id("SelectStaffNosearch"), staff);
			waitLoading();
			WebElement ele = waitFor(By.xpath("//div[@id='SelectStaffNogrid']//tr/td[normalize-space(text())='" + staff + "']"));
			WebDriverUtils.scrollTo(ele, driver);
			ele.click();
		} 
		if (card!=null) {
			putInValue(By.id("texCardNo"), card);
		}
		if (dateFrom!=null) {
			putInValue(By.id("txtDateFrom"), dateFrom);
		}
		if (dateTo!=null) {
			putInValue(By.id("txtDateTo"), dateTo);
		}
		find(By.id("btnSearch")).click();
		waitLoading();
	}
    
    //如果存在符合条件的原始打卡记录则删除
    public void deleteAttendanceCardIfExist(HashMap<String, String> data) throws Exception {
    	Set<String> keys = data.keySet();
    	String trXpath = "//tbody[contains(@data-bind,'Punch')]/tr";
    	String tableXpath = "//tbody[contains(@data-bind,'Punch')]/..";
    	for (String k : keys) {
    		String td = WebDriverUtils.getIndex(tableXpath, k, driver);
    		String val = Utils.getTestDataProperty(data.get(k));
    		trXpath = trXpath + "[./td[" + td + "][normalize-space(text())='" + val + "']]";
    	}
    	List<WebElement> trs = findAll(By.xpath(trXpath));
    	if (trs.size()>0) {
    		for (int i=0;i<trs.size();i++) {
    			checkChkRadio(trs.get(i).findElement(By.xpath(".//input")), true);    			
    		}
    		WebDriverUtils.acceptAllConfirmations(driver);
    		find(By.xpath("//*[@id='DivAll']//a/i[contains(@class,'trash')]")).click();
    		WebDriverUtils.confirmDelete(driver);
    	}    	
    }
    
    //导入原始打卡记录
  	public void importPunchingRecord(String fileName) throws Exception {
  		waitFor(By.xpath("//input[@value='导入']")).click();
  		WebDriverUtils.importFile(fileName, driver);
  		find(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]")).click();
  		waitForVisible(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]")).click();
  		Utils.waitABit(3000);
  		waitForNotVisibleLong(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
  		String actualMsg = waitForVisibleLong(By.id("showP")).getText().trim();
  		Assert.isContains(actualMsg, "导入成功!一共导入:2条记录,耗时", "成功导入员工持卡记录");
  		find(By.xpath("//button[text()='确定']")).click();
  	}
  	
    //验证员工考勤记录导入后的数据比对
  	public void verifyPunchingDataUIDataWithExcel(List<LinkedHashMap<String, String>> excelData) throws Exception {
  		for (int i=0;i<2;i++) {
  			logger.info("第 " + i + " 行数据");
  			LinkedHashMap<String, String> excelD = excelData.get(i);
	  		String date = excelD.get("打卡日期");
	  		String time = excelD.get("打卡时间");
	  		if (time.length()==5) {
	  			time = time + ":00";
	  		}
	  		String rowIndex = String.valueOf(i+1);
	  		String trXpath = "//tbody[contains(@data-bind,'PunchInfo')]/tr[" + rowIndex + "]";
	  		LinkedHashMap<String, String> uiData = WebDriverUtils.getRowData(trXpath, driver);
	  		uiData.remove("编辑");	
	  		Assert.isEqual("导入", uiData.get("数据来源"));
	  		uiData.remove("数据来源");
	  		excelD.remove("打卡日期");
	  		excelD.put("打卡时间", date + " " + time);
	  		Assert.isHashMapEqual(excelD, uiData);
  		}
  	}	
}
