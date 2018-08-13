package com.winmax.pages.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.winmax.config.Const;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Excel;
import com.winmax.utils.FileUtil;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class AnalysisPage extends  WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(AttendanceSettingPage.class);	
	public AnalysisPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnExportImportButton(String type) throws Exception {
		openNewWindowWith(waitFor(By.xpath("//div[@id='TotalMonthListDiv']//a[contains(.,'" + type + "')]")));
	}
	
	public void selectExportType(String exportMethod) throws Exception {
		waitFor(By.xpath("//span[contains(text(),'" + exportMethod + "')]")).click();
		waitLoading();
	}
	
	public void selectPeriodDetail(String period, String details) throws Exception {
		waitForVisible(By.xpath("//li[@id='TotalPeriod']//span[contains(text(),'考勤周期统计')]")).click();
		//选择期间
		waitForVisible(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
		waitForVisible(By.id("dvPeriodsearch")).sendKeys(period);
		waitLoading();
		waitFor(By.xpath("//div[@id='dvPeriodgrid']/table//tr[1]/td[normalize-space(text())='" + period + "']")).click();
		Utils.waitABit(1000);
		
		//选择周期明细
		waitFor(By.xpath("//div[@id='dvPeriodDetailShowWindow']/span")).click();
		waitForVisible(By.id("dvPeriodDetailsearch")).sendKeys(details);
		waitLoading();
		waitFor(By.xpath("//div[@id='dvPeriodDetailgrid']/table//tr[1]/td[normalize-space(text())='" + details + "']")).click();
		Utils.waitABit(1000);
		//点击确认
		find(By.id("btnSurePayterm")).click();
		waitLoading();
	}
	
	public void searchAttendanceSummary(String period, String detail, String staff) throws Exception {
		//选择期间
		if (period!=null) {
			waitForVisible(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
			waitForVisible(By.id("dvPeriodsearch")).sendKeys(period);
			waitLoading();
			waitFor(By.xpath("//div[@id='dvPeriodgrid']/table//tr[1]/td[normalize-space(text())='" + period + "']")).click();
			Utils.waitABit(1000);
		}
		
		if (detail!=null) {
			//选择周期明细
			waitFor(By.xpath("//div[@id='dvPeriodDetailShowWindow']/span")).click();
			waitForVisible(By.id("dvPeriodDetailsearch")).sendKeys(detail);
			waitLoading();
			waitFor(By.xpath("//div[@id='dvPeriodDetailgrid']/table//tr[1]/td[normalize-space(text())='" + detail + "']")).click();
			Utils.waitABit(1000);
		}
		
		if (staff!=null) {
			WebDriverUtils.advanceSearchById(staff, driver);
		}
		
		//点击确认
		find(By.id("btnSearch")).click();
		waitLoading();		
	}
	
	public void verifySearchResult(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		List<WebElement> headersEles = waitForMinimumNum(By.xpath("//table[@id='headTable']/thead/tr/td"), 1);
		List<WebElement> recordsList = waitForMinimumNum(By.xpath("//table[@id='headTable']/tbody/tr"), 1);
		List<String> headers = getListElementsText(headersEles);
		for (String key : keys) {
			int index = headers.indexOf(key) + 1;
			for (WebElement row : recordsList) {
				String actual = row.findElement(By.xpath("./td[" + index + "]")).getAttribute("innerText").trim();
				Assert.isEqual(data.get(key), actual, key);
			}	
		}	
	}
	
	public String[] selectHeaders(String header) throws Exception {
		String[] array = header.split("\\|");
		String expect = "";
		for(int i=0;i<array.length;i++) {
			expect = expect + array[i] + ",";
			checkChkRadio(By.xpath("//dl[@id='categoty']//label[./span[normalize-space(text())='" + array[i] + "']]/input"), true);
		}
		Utils.waitABit(1000);
		String selectedText = waitFor(By.xpath("//div[@id='selectedFieldList']//label")).getText().trim();
		Assert.isEqual(expect.substring(0, expect.length()-1), selectedText);
		return array;
	}
	
	public String saveAsTemplate() throws Exception {
    	find(By.id("btnSaveTemplate")).click();
    	String tempalteName = waitForVisible(By.id("txt_templateName")).getText().trim();
    	find(By.xpath("//*[@id='div_saveTemplate_dlg']//input[@value='保存']")).click();
    	WebDriverUtils.confirmActionSuccess(driver);
    	return tempalteName;
    }
	
	public void advanceSearchStaffExport(String staff) throws Exception {
		WebElement personnel_link = waitFor(By.xpath("//li[@id='liPersonnel']/a/span"));
		if (!personnel_link.getAttribute("class").contains("active")) {
			scrollTo(personnel_link);
			personnel_link.click();
		}
		WebElement staffNo_input = waitFor(By.id("dvStaffdisplay"));
		scrollTo(staffNo_input);
		staffNo_input.sendKeys(staff);		
    }
	
	public List<HashMap<String, String>> getPreviewData() throws Exception {
    	List<WebElement> headersList = this.waitForMinimumNum(By.xpath("//table[@id='tbMainTable']/thead/tr/td"), 2);
    	List<WebElement> records = this.waitForMinimumNum(By.xpath("//table[@id='tbMainTable']/tbody/tr"), 1);
    	ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
    	for (int i=0;i<records.size();i++) {
    		List<WebElement> tds = records.get(i).findElements(By.xpath(".//td"));
    		HashMap<String, String> oneRow = new HashMap<String, String>();
    		for (int m=0;m<headersList.size();m++) {
    			oneRow.put(headersList.get(m).getText().trim(), tds.get(m).getText().trim());
    		} 
    		data.add(oneRow);
    	}
    	return data; 
    }
	
	public String downloadFile() throws Exception {
		clickNextButton();
		Utils.waitABit(5000);
		return WebDriverUtils.waitForDownloadFile(FileUtil.countDownloadFolderFileNo());
    }
	
	public void verifyExportedFileAndDelete(String fileName, List<HashMap<String, String>> previewData) throws Exception {
		String downloadFile = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER + fileName ;
		List<LinkedHashMap<String, String>> excelData = Excel.readWorkBook(downloadFile);
		FileUtil.deleteFile(downloadFile);
		Assert.isEqual(previewData.size(), excelData.size());
		for (int i=0;i<excelData.size();i++) {
			boolean flag = excelData.get(i).equals(previewData.get(i)) && previewData.get(i).equals(excelData.get(i));
			Assert.isTrue(flag, "excel 数据 ## " + excelData + ", 预览的数据 ### " + previewData);
		}
    }
	
	public void clickNextButton() throws Exception {
		WebElement nextBtn = find(By.id("btnNext"));
		scrollTo(nextBtn);
		nextBtn.click();
		waitLoading();
	}
}
 