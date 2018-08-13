package com.winmax.pages.attendance;


import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class ShiftPage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(ShiftPage.class);
	
	public ShiftPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//div/span[contains(text(),'上传')]")
	private WebElement upload_btn;
	
	@FindBy(xpath="//div[./span[text()='文件导入']]/button")
	private WebElement close_upload_btn;	
		
	// import shift file successfully
	public void importShift(String fileName) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		waitFor(By.id("importShift")).click();
		waitFor(By.id("file0")).sendKeys(filePath);
		upload_btn.click();
		String alertText = dismissAlert();
		Assert.isEqual("上传文件成功", alertText);
	}
	
	// Close import file pop up
	public void closeImportPopup() throws Exception {
		close_upload_btn.click();
		waitForNotVisible(By.id("file0"));
	}
	
	/**
	 * 高级筛选： 通过人事字段-员工编号
	 * @param id
	 * @throws Exception
	 */
	public void advanceSearchShiftRecordById(String id) throws Exception {
		WebDriverUtils.advanceSearchById(id, driver);
	}	
	
	/**
	 * get shift list 1st row values;
	 * @throws Exception
	 */
	public void getShiftList() throws Exception {
		List<WebElement> headers = waitForMinimumNum(By.xpath("//table[@id='shiftTable']//tr/th[not(contains(@style,'none'))]"), 4);
		List<WebElement> values = findAll(By.xpath("//table[@id='shiftTableList']/tbody/tr/td[not(contains(@style,'none'))]"));
		for (int i=0;i<values.size();i++) {
			logger.info("## " + headers.get(i).getText() + ", " + values.get(i).getText());
		}	
	}
	
	//######### 时间管理>排班管理>雇员班组设定 ###################
    public void deleteShiftSetting(String staffId) throws Exception {
    	WebElement tr = waitForShort(By.xpath("//table[contains(@class,'table-hover')]//tr[./td[contains(@data-bind,'StaffNo')][normalize-space(text())='" + staffId + "']]"));
		tr.findElement(By.xpath("./td/i[contains(@class,'trash')]")).click();
    	waitFor(By.xpath("//div[contains(@style,'block')]//button[text()='确定']")).click();
    	waitLoading();
    	WebDriverUtils.confirmDelete(driver);
    }
    
    public void deleteShiftSettingIfExist(String staffId) throws Exception {
    	try {
			waitForShort(By.xpath("//table[contains(@class,'table-hover')]//tr[./td[contains(@data-bind,'StaffNo')][normalize-space(text())='" + staffId + "']]"));
			deleteShiftSetting(staffId);
    	} catch (Exception e) {
			;
		}
    }
    
    //导入雇员班组设定
    public void imporShitSetting(String fileName) throws Exception {
  		waitFor(By.xpath("//a[contains(.,'导入文件')]")).click();
  		WebDriverUtils.importFile(fileName, driver);
  		find(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]")).click();
  		waitForVisible(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]")).click();
  		Utils.waitABit(3000);
  		waitForNotVisibleLong(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
  		String actualMsg = waitForVisibleLong(By.id("showP")).getText().trim();
  		Assert.isContains(actualMsg, "导入成功!一共导入:1条记录,耗时", "成功导入员工持卡记录");
  		find(By.xpath("//button[text()='确定']")).click();
  	}
  	
  	//验证雇员班组设定导入后的数据比对
  	public void verifyShiftSettingUIDataWithExcel(HashMap<String, String> excelData) throws Exception {
  		driver.switchTo().defaultContent();
   	 	String frameElement = "_PartialIframeGroup";
   	 	driver.switchTo().frame(frameElement);
	    String id = excelData.get("员工编号");
  		String trXpath = "//table[contains(@class,'table-hover')]//tr[./td[contains(text(),'" + id + "')]]";
  		HashMap<String, String> uiData = WebDriverUtils.getRowData(trXpath, driver);	
  		uiData.remove("操作");
  		Assert.isHashMapEqual(excelData, uiData);
  	}
  	
  	public void advanceSearchStaffShiftSetting(String id) throws Exception {
  		driver.switchTo().defaultContent();
   	 	String frameElement = "_PartialIframeGroup";
   	 	driver.switchTo().frame(frameElement);
   	 	WebDriverUtils.advanceSearchById(id, driver);
  	}
}
