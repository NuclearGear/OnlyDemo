package com.winmax.pages.attendance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;


public class AttendancePage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(LeaveFormPage.class);
	
	@FindBy(xpath = "//div/span[contains(text(),'上传')]")
	public WebElement upload_btn;

	@FindBy(xpath = "//div[./span[text()='文件导入']]/button")
	private WebElement close_upload_btn;
	
	@FindBy(xpath="//tr[./td[contains(text(),'休假类型')]]//select")
	private WebElement leave_select;
	
	public AttendancePage() {
		PageFactory.initElements(driver, this);
	}
	
	/**
	 * 休假单导入
	 * @param fileName
	 * @throws Exception
	 */
	// import leave file successfully
	public void importLeave(String fileName) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		waitFor(By.xpath("//div[@id='divLeaveImport']/div[1]/div/div/div/ul/li[1]/a[normalize-space(text()='导入文件')]")).click();
		waitFor(By.id("file0")).sendKeys(filePath);
		Utils.waitABit(3000);
		upload_btn.click();
		String alertText = dismissAlert();
		Assert.isEqual("上传文件成功", alertText);
		Utils.waitABit(3000);
		waitFor(By.xpath("//div/span[contains(text(),'验证')]")).click();
		Utils.waitABit(3000);
		waitFor(By.xpath("//div/span[contains(text(),'处理')]")).click();
		waitFor(By.xpath("//div[.//span[normalize-space(text())='文件导入']]//button")).click();
	    //driver.close();
		waitFor(By.xpath(".//a[normalize-space(text())='时间管理']")).click();
		waitFor(By.xpath(".//a[normalize-space(text())='休假申报输入']")).click();
	}
	
	/**
	 * 在文本框输入休假代码：NAL,输入框输入雇员工号
	 * @param empolyee
	 */
	public void searchEmpolyee(String leaveCode, String empolyee) {
		find(By.xpath("//input[@id='divLeaveCodesdisplay']")).sendKeys(leaveCode);	  
		waitFor(By.xpath("//input[@id='lbcNOName']")).sendKeys(empolyee);	
		waitFor(By.xpath("//a[@id='btnSearch'][text()='查询']")).click();// click button 查询
		Utils.waitABit(3000);
	}
	
	/**
	 * 修改编辑休假结余
	 * @param 
	 * @param 
	 * @throws Exception
	 */
	public void updateBanlance(String[] lastBanlance, String[] thisBanlance) throws Exception {
		find(By.xpath("//tbody/tr[1]/td[1]/a")).click();
		for (int i = 0; i < lastBanlance.length; i++) {
			String lastYear = "//tbody/tr[2]/td[2]/input";
			waitFor(By.xpath(lastYear)).clear();
			waitFor(By.xpath(lastYear)).sendKeys(lastBanlance);
		}
		
	}
	
	public void batchDeleteAttendanceCardIfExist(String staffNo, HashMap<String, String> hdata) throws Exception{
		List<WebElement> headersEle = findAll(By.xpath("//table[contains(@class,'hover')]/thead//td"));
		List<String> headers = new ArrayList<String>();		
		for (int i=0;i<headersEle.size();i++) {
			headers.add(headersEle.get(i).getText().trim());
		}
		String trXpath = "//table[contains(@class,'hover')]/tbody/tr[./td[contains(@data-bind,'StaffNo')][normalize-space(text())='" + staffNo + "']]";
		Set<String> keys = hdata.keySet();
		for (String k : keys) {
			if (!k.contains("效日期")) {
				String value = hdata.get(k);
				int index = headers.indexOf(k)+1;			
				trXpath = trXpath + "[./td[" + index + "][normalize-space(text())='" + value + "']]";
			}
		}		 
		List<WebElement> trs = findAll(By.xpath(trXpath));
		int count=0;
		for (int i=0;i<trs.size();i++) {
			String expireDate = Utils.getDate(hdata.get("失效日期")); 
			if (expireDate!=null) {
				int inx = headers.indexOf("失效日期")+1;
				String uiDate = trs.get(i).findElement(By.xpath("./td[" + inx + "]")).getAttribute("innerText").trim();
				if (Utils.compare_date(expireDate, uiDate)<=0) {
					trs.get(i).findElement(By.xpath("./td/input")).click();
					count = count+1;
				}
			} else {
				trs.get(i).findElement(By.xpath("./td/input")).click();
				count = count+1;
			}			
		}
		if (count>0) {
			find(By.xpath("//div[@id='CardHoder']//a/i[contains(@class,'trash')]")).click();
			waitFor(By.xpath("//button[text()='确定']")).click();
			WebDriverUtils.confirmDelete(driver);
		}
	}
	
	public void deleteAttendanceCardIfExist(String staffNo, HashMap<String, String> data) throws Exception{
		List<WebElement> tr = findAll(By.xpath("//table[contains(@class,'hover')]/tbody/tr[./td[contains(@data-bind,'StaffNo')][normalize-space(text())='" + staffNo + "']]"));
		if (tr.size()==0) {
			return;
		} else {
			batchDeleteAttendanceCardIfExist(staffNo, data);
		}	
	}
	
	//导入员工持卡设定
	public void imporStaffCard(String fileName) throws Exception {
		waitFor(By.xpath("//div[@id='CardHoder']//a[contains(.,'导入文件')]")).click();
		WebDriverUtils.importFile(fileName, driver);
		find(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]")).click();
		waitForVisible(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]")).click();
		Utils.waitABit(3000);
		waitForNotVisibleLong(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
		String actualMsg = waitForVisibleLong(By.id("showP")).getText().trim();
		Assert.isContains(actualMsg, "导入成功!一共导入:1条记录,耗时", "成功导入员工持卡记录");
		find(By.xpath("//button[text()='确定']")).click();
	}
	
	//验证员工持卡设定记录导入后的数据比对
	public void verifyUIDataWithExcel(HashMap<String, String> excelData) throws Exception {
		driver.switchTo().defaultContent();
   	 	String frameElement = "_PartialIframe";
   	 	driver.switchTo().frame(frameElement);
		String expireDate = excelData.get("失效日期");
		if (expireDate.contains("/")) {                    //数据格式yyyy/mm/dd
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			Date date = sdf.parse(expireDate);
			expireDate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		}
		String trXpath = "//table[contains(@class,'table-hover')]//tr[./td[contains(text(),'" + expireDate + "')]]";
		HashMap<String, String> uiData = WebDriverUtils.getRowData(trXpath, driver);		
		Set<String> keys = excelData.keySet();
		for (String k : keys) {
			if (k.equals("持卡类型")) {
				Assert.isEqual("考勤卡", uiData.get(k), "列名： " + k + " 数据显示不正确");
			} else {
				Assert.isEqual(excelData.get(k), uiData.get(k), "列名： " + k + " 数据显示不正确");
			}
		}
	}	
}