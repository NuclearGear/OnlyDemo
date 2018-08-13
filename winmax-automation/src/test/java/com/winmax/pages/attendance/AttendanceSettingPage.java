package com.winmax.pages.attendance;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

//考勤班组设定
public class AttendanceSettingPage extends WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(AttendanceSettingPage.class);	
	public AttendanceSettingPage() {
		PageFactory.initElements(driver, this);
	}
	
	private WebElement getTrByCode(String code) throws Exception {
		return waitFor(By.xpath("//tr[./td[contains(@data-bind, 'Code')][normalize-space(text())='"  + code + "']]"));
	}
	//点击按钮
	public void clickButton(String btnName) throws Exception {
		waitFor(By.xpath("//*[@id='ehr-xsbd']//a[contains(text(),'" + btnName + "')]")).click();
	}
	
	//填充 考勤班组设定
	public void fillGroupSettingBasicInfo(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		waitForVisible(By.xpath("//div[./span[contains(text(),'查看修改班组')]]"));
		for (String key : keys) {
			String value = data.get(key);
			if (key.contains("时间")) {
				value = Utils.getDate(value);
			}
			By by = By.xpath("//*[@id='dvGroupBaseInfo']//td[normalize-space(text())='" + key + "']/following-sibling::td[1]/*");
			WebElement field = waitForVisible(by);
			if (key.equals("假期类别")) {
				Utils.waitABit(2000);
				WebElement icon = waitForVisible(By.xpath("//*[@id='dvHolidayPolicyShowWindow']/span"));
				Actions action = new Actions(driver);
				action.moveToElement(icon).click().build().perform();
				Utils.waitABit(1000);
				WebElement input = waitFor(By.id("dvHolidayPolicysearch"));
				try {
					input.clear();
					action.moveToElement(input).sendKeys(value).build().perform();
				} catch (Exception e) {
					;
				}
				waitLoading();
				WebElement item = waitFor(By.xpath("//*[@id='dvHolidayPolicygrid']/table//td[normalize-space(text())='" + value + "']"));
				action.moveToElement(item).click().build().perform();
				Utils.waitABit(1000);
				continue;
			} else if (key.equals("法定节假日类型")) {
				List<WebElement> radioList = findAll(By.xpath("//div[@class='input-group']//input[@type='radio']"));
				if (value.contains("替换")) {
					checkChkRadio(radioList.get(0), true);
				} else if (value.contains("顺延")) {
					checkChkRadio(radioList.get(1), true);
				} else if (value.contains("不处理")) {
					checkChkRadio(radioList.get(2), true);
				}
				continue;
			} else if (field.getTagName().equals("input")) {
				putInValue(field, value);
			}
		}		
	}
	
	//打开 考勤班组设定 窗口，验证信息
	public void verifyGroupSettingBasicInfo(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		waitForVisible(By.xpath("//div[./span[contains(text(),'查看修改班组')]]"));
		for (String key : keys) {
			String value = data.get(key);
			if (key.contains("时间")) {
				value = Utils.getDate(value);
			}
			By by = By.xpath("//*[@id='dvGroupBaseInfo']//td[normalize-space(text())='" + key + "']/following-sibling::td[1]/*");
			WebElement field = waitForVisible(by);
			String actualValue = null;
			if (key.equals("假期类别")) {
				actualValue = field.findElement(By.xpath("./input[1]")).getAttribute("value").trim();	
			} else if (key.equals("法定节假日类型")) {
				List<WebElement> radioList = findAll(By.xpath("//div[@class='input-group']//input[@type='radio']"));
				actualValue = "替换";
				if (radioList.get(1).isSelected()) {
					actualValue = "顺延";
				} else if (radioList.get(2).isSelected()) {
					actualValue = "不处理";
				} 
			} else if (waitForVisible(by).getTagName().equals("input")) {
				actualValue = waitForVisible(by).getAttribute("value").trim();				
			}
			Assert.isEqual(value, actualValue, key);
		}			
	}

	public void saveGroupSettingInBasicInfo() throws Exception {
		try {
			waitForVisible(By.xpath("//*[@id='dvGroupBaseInfo']//button[normalize-space(text())='保存班组']")).click();
		} catch (StaleElementReferenceException e) {
			waitForVisible(By.xpath("//*[@id='dvGroupBaseInfo']//button[normalize-space(text())='保存班组']")).click();
		}
		WebDriverUtils.confirmSave(driver);
	}
	
	public void closeSettingWindow() throws Exception {
		find(By.xpath("//div[./span[contains(text(),'查看修改班组')]]/button")).click();
		waitLoading();
	}
	
	public void openUpdateSettingWindow(String code) throws Exception {
		WebElement tr = getTrByCode(code);
		waitFor(tr, By.xpath("./td[1]")).click();
		clickButton("修改");
	}
	
	public void deleteGroupSetting(String code) throws Exception {		
		WebElement tr = getTrByCode(code);
		waitFor(tr, By.xpath("./td[1]")).click();
		clickButton("删除");
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		waitLoading();
		WebDriverUtils.confirmDelete(driver);
		try {
			getTrByCode(code);
			Assert.fail("删除失败： " + code);
		} catch (Exception e) {			
		}
	}
	
	public void deleteGroupSettingIfExist(String code) throws Exception {	
		try {
			getTrByCode(code);
		} catch (Exception e) {
			return;
		}
		deleteGroupSetting(code);
	}
	
	public void verifyExistSettigRecord(String code) throws Exception {	
		try {
			getTrByCode(code);			
		} catch (Exception e) {	
			Assert.fail("没有考勤班值设定： " + code);
		} 
	}
	
	public void verifyNoSettigRecord(String code) throws Exception {	
		try {
			getTrByCode(code);	
			Assert.fail("存在考勤班值设定： " + code);
		} catch (Exception e) {				
		} 
	}
	
	//点击  查看排班明细
	public void openDetailLink(String code) throws Exception {
		if (code==null) {
			code = waitForVisible(By.xpath("//tr[@id='0']/td[2]")).getText().trim();					
		}
		WebElement tr = getTrByCode(code);
		WebElement link = find(tr, By.xpath("./td/a[contains(text(),'查看排班明细')]"));
		openNewWindowWith(link);
		waitForVisible(By.id("myCalendar"));
	}
	
	//关闭 查看排班明细 页面，并回到前页面
	public void closeDetailNewWindow() throws Exception {
		driver.close();
		waitForWindows(1);
		switchToOpenerWindow();
	}
	
	//验证记录的值
	public void verifyRecordColumnValues(String name, String code, String beginDate, String endDate) throws Exception {
		WebElement tr = getTrByCode(code);
		String actualName = waitFor(tr, By.xpath("./td[1]")).getText().trim();
		Assert.isEqual(name, actualName, "班组");		
		String actualBeginDate = waitFor(tr, By.xpath("./td[3]")).getText().trim();
		Assert.isEqual(beginDate, actualBeginDate, "开始时间");
		String actualEndDate = waitFor(tr, By.xpath("./td[4]")).getText().trim();
		Assert.isEqual(endDate, actualEndDate, "结束时间");
	}
	
	//点击 设定排班规则 按钮
	public void clickSetRuleBtn() throws Exception {
		waitForVisible(By.id("btnSetRole")).click();
		WebElement navImg = waitFor(By.xpath("//div[./span[contains(text(),'设定排班规则')]]/img"));
		Assert.isContains(navImg.getAttribute("src"), "nav_Orange.png", "设定排班规则 导航条变成橙色");
	}
	
	//点击 生成班值排列 button
	public void clickGenGroupShiftDailyBtn() throws Exception {
		waitForVisible(By.id("btGenShift")).click();
		WebElement navImg = waitFor(By.xpath("//div[./span[contains(text(),'生成班值排列')]]/img"));
		Assert.isContains(navImg.getAttribute("src"), "nav_Orange.png", "设定排班规则 导航条变成橙色");
	}
	
	//在设定排班规律页面中，在“循环周期”输入框内，输入数字，点击“生成小循环”
	public void generateShiftByCycle(String cycle) throws Exception {
		putInValue(By.id("dvCycleNum"), cycle);
		find(By.xpath("//div[@id='dvCycleInfo']//button[contains(text(),'生成小循环')]")).click();
		By allTr = By.xpath("//div[@id='dvCycleInfo']//table/tbody//tr");
		List<WebElement> trs = waitForMinimumNum(allTr, Integer.valueOf(cycle));
		Assert.isEqual(Integer.valueOf(cycle), trs.size(), "生成的一个循环的排班总数");
	}	
	
	private String getID(String row0ID, String index) throws Exception {
		return row0ID.replace("0", index);
	}
	
	private By getGridBy(String value) throws Exception {
		String xpath = "//div[contains(@style,'block')]//div[contains(@id,'grid')]/table/tbody//td[normalize-space(text())='" + value + "']";
		return By.xpath(xpath);
	}
	
	private void sendKeyAndSelect(String value) throws Exception {
		By searchInputBy = By.xpath("//div[contains(@style,'block')]//input[contains(@id,'earch')][@placeholder='查询']");
		waitForVisible(searchInputBy).sendKeys(value);
		waitLoading();
		waitForVisible(getGridBy(value)).click();
		Utils.waitABit(1000);
	}
	
	//为一行日期设置班值，假期，班前加班，班中加班，班后加班等值
	public void setCycleDetailForDate(String date, String shift, String holiday, String ot1, String ot2, String ot3) throws Exception {
		WebElement tr = waitFor(By.xpath("//tr[./td[2][contains(text(),'" + date + "')]]"));
		if (holiday!=null) {
			checkChkRadio(tr.findElement(By.xpath("./td/input[contains(@data-bind, 'IsHoliday')]")), holiday.toLowerCase().contains("true"));
		}
		
		int rowNoInt = Integer.valueOf(tr.findElement(By.xpath("./td[1]")).getText().trim())-1;	
		String rowNo = String.valueOf(rowNoInt);

		//班值
		tr.findElement(By.xpath("./td[contains(@onclick, 'ChangeShift')]")).click();
		waitFor(By.xpath("//div[@id='" + getID("dvShift0ShowWindow", rowNo) + "']/span")).click();
		sendKeyAndSelect(shift);
		//班前加班
		tr.findElement(By.xpath("./td[contains(@onclick, 'ChangeOTBefore')]")).click();
		waitFor(By.xpath("//div[@id='" + getID("dvOTBefore0ShowWindow", rowNo) + "']/span")).click();
		sendKeyAndSelect(ot1);
		//班中加班
		tr.findElement(By.xpath("./td[contains(@onclick, 'ChangeOTMiddle')]")).click();
		waitFor(By.xpath("//div[@id='" + getID("dvOTMiddle0ShowWindow", rowNo) + "']/span")).click();
		sendKeyAndSelect(ot2);
		//班后加班
		tr.findElement(By.xpath("./td[contains(@onclick, 'ChangeOTAfter')]")).click();
		waitFor(By.xpath("//div[@id='" + getID("dvOTAfter0ShowWindow", rowNo) + "']/span")).click();
		sendKeyAndSelect(ot3);		
	}
	
	//点击保存按钮 在设定排班规则 tab
	public void saveCycleDetailsOnNav2() throws Exception {
		waitFor(By.id("btSaveCycle")).click();
		WebDriverUtils.confirmSave(driver);
	}
	
	//点击 生成班值排列 按钮 在 生成班值排列 tab
	public void generateGroupShiftDaily() throws Exception {
		try {
			waitFor(By.id("btGenShift")).click();
		} catch (StaleElementReferenceException e) {
			Utils.waitABit(1000);
			waitForVisible(By.id("btGenShift")).click();
		}
		String confirmText= waitForVisible(By.id("showP")).getText().trim();
		Assert.isEqual("确认生成班值？", confirmText, "生成班值的确认信息");
		waitForVisible(By.xpath("//div[.//p[contains(text(),'生成班值')]]//button[contains(text(),'确定')]")).click();
		waitForNotVisible(By.xpath("//*[@id='showP'][contains(text(),'确认生成班值')]"));
		String confirmTextSuccess = waitForVisible(By.id("showP")).getText().trim();
		Assert.isEqual("排班生成成功!是否浏览明细？", confirmTextSuccess, "生成班值的成功信息");
		waitForVisible(By.xpath("//div[.//p[contains(text(),'排班生成成功')]]//button[contains(text(),'取消')]")).click();
	}
	
	//##################### 用餐时间设定 ##########################
	public void clickAddDining() throws Exception {
		waitFor(By.xpath("//div[@id='ehr-xsbd']//a[contains(@onclick, 'AddNew')]")).click();		
	}
	
	public void fillDining(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);			
			By by = By.xpath("//*[@id='tr_hide_add']/td/table/tbody//tr/td[normalize-space(text())='" + key + "']/following-sibling::td[1]/input");
			WebElement field = waitForVisible(by);
			if (field.getAttribute("type").contains("text")) {
				putInValue(field, value);
			}
		}	
	}

	public void saveDinging() throws Exception {
		find(By.xpath("//tr[@id='tr_hide_add']//input[@value='保存']")).click();
		waitLoading();
		WebDriverUtils.confirmSave(driver);
	}
	
	private String getTrXpathByDingCode(String diningCode) {
		return "//tbody[@id='mainTbody']//tr[.//*[normalize-space(text())='" + diningCode + "']]";
	}
	
	public void verifyDiningRecord(HashMap<String, String> data) throws Exception {
		String diningCode = data.get("代码");
		HashMap<String, String> actualData = new HashMap<String, String>();
		List<WebElement> headers = driver.findElements(By.xpath("//*[@id='div_bind']//div[contains(@class,'table')]/table//td"));
		List<WebElement> values = driver.findElements(By.xpath(getTrXpathByDingCode(diningCode) + "//td"));
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		Assert.assertHashMapAContainsHashMapB(actualData, data);
	}
	
	public void clickEditDingRecord(String diningCode) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByDingCode(diningCode)));
		tr.findElement(By.xpath("./td[1]/a[contains(@data-bind,'Edit')]")).click();
		waitFor(By.xpath("//tr[@id='tr_hide_add']//input[@value='保存']"));
	}
	
	public void deleteDingRecord(String diningCode) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByDingCode(diningCode)));
		tr.findElement(By.xpath("./td[1]/a[contains(@data-bind,'Delete')]")).click();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		waitLoading();
		WebDriverUtils.confirmActionSuccess(driver);
	}
	
	public void verifyNoDiningRecordInList(String diningCode) throws Exception {
		try {
			waitFor(By.xpath(getTrXpathByDingCode(diningCode)));
			Assert.fail("Ther is dining record " + diningCode);
		} catch (Exception e) {
			;
		}
	}
	
	public boolean isDiningReocrdThere(String diningCode) throws Exception {
		try {
			waitFor(By.xpath(getTrXpathByDingCode(diningCode)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickDetailsForDiningRecord(String diningCode) throws Exception {
		WebElement tr = null;
		if (diningCode.contains("random")) {
			tr = waitFor(By.xpath("//tbody[@id='mainTbody']/tr[1]"));
		} else {
			tr = waitFor(By.xpath(getTrXpathByDingCode(diningCode)));
		}
		tr.findElement(By.xpath("./td/a[contains(@data-bind,'Details')]")).click();
		waitForVisible(By.xpath("//span[contains(text(),'列表')]"));
	}
	
	public void clickAddDiningDetail() throws Exception {
		waitFor(By.xpath("//div[@id='ShiftMealDetails']//a[contains(@onclick, 'shiftmealdetailViewModel.Add')]")).click();		
	}
	
	public void fillDiningDetail(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		By by = null;
		for (String key : keys) {
			String value = data.get(key);			
			if (key.contains("抵扣")) {
				by = By.xpath("//*[@id='tr_hide_details']/td/table/tbody//tr/td[contains(., '" + key + "')]/input");
			} else {
				by = By.xpath("//*[@id='tr_hide_details']/td/table/tbody//tr/td[normalize-space(text())='" + key + "']/following-sibling::td[1]/input");
			}
			WebElement field = waitForVisible(by);			
			if (field.getAttribute("type").contains("text")) {
				putInValue(field, value);
			} else if (field.getAttribute("type").contains("checkbox")) {
				checkChkRadio(field, value.toLowerCase().contains("true"));
			}
		}	
	}

	public void saveDingingDetail() throws Exception {
		find(By.xpath("//tr[@id='tr_hide_details']//input[@value='保存']")).click();
		waitLoading();
		WebDriverUtils.confirmSave(driver);
	}
	
	private String getDetailTrXpathByDingCode(String name) {
		return "//tbody[@id='tbodyDetail']//tr[.//*[normalize-space(text())='" + name + "']]";
	}
	
	public boolean isDiningDetailReocrdThere(String name) throws Exception {
		try {
			waitFor(By.xpath(getDetailTrXpathByDingCode(name)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void verifyDetailRecord(HashMap<String, String> data) throws Exception {
		String name = data.get("中文名称");
		HashMap<String, String> actualData = new HashMap<String, String>();
		List<WebElement> headers = driver.findElements(By.xpath("//*[@id='bindDetails']//div[contains(@class,'table')]/table//td"));
		List<WebElement> values = driver.findElements(By.xpath(getDetailTrXpathByDingCode(name) + "//td"));
		for (int i = 0; i < headers.size(); i++) {
			if (headers.get(i).getText().trim().contains("抵扣")) {
				WebElement checkbox = values.get(i).findElement(By.xpath("./input"));
				String actualVal = checkbox.isSelected()? "true" : "false";
				actualData.put(headers.get(i).getText().trim(), actualVal);
			} else if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		Assert.assertHashMapAContainsHashMapB(actualData, data);
	}
	
	public void clickEditDetailRecord(String name) throws Exception {
		WebElement tr = waitFor(By.xpath(getDetailTrXpathByDingCode(name)));
		tr.findElement(By.xpath("./td/a[contains(@data-bind,'Edit')]")).click();
		waitFor(By.xpath("//tr[@id='tr_hide_details']//input[@value='保存']"));
	}
	
	public void deleteDetailRecord(String name) throws Exception {
		WebElement tr = waitFor(By.xpath(getDetailTrXpathByDingCode(name)));
		tr.findElement(By.xpath("./td/a[contains(@data-bind,'Delete')]")).click();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		waitLoading();
		WebDriverUtils.confirmActionSuccess(driver);
	}
	
	public void verifyNoDetailRecordInList(String name) throws Exception {
		try {
			waitFor(By.xpath(getDetailTrXpathByDingCode(name)));
			Assert.fail("Ther is dining detail " + name);
		} catch (Exception e) {
			;
		}
	}
	
	public void closeDiningDetailPopup() throws Exception {
		find(By.xpath("//div[./span[contains(text(),'列表')]]/button[contains(@class,'close')]")).click();
		Utils.waitABit(1000);
	}
	
}
