package com.winmax.pages.attendance;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.winmax.config.Const;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.DBHelper;
import com.winmax.utils.Excel;
import com.winmax.utils.FileUtil;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class VacationPage extends WebDriverSteps {
	@FindBy(xpath = "//div/span[contains(text(),'上传')]")
	public WebElement upload_btn;

	@FindBy(xpath = "//div[./span[text()='文件导入']]/button")
	private WebElement close_upload_btn;

	@FindBy(xpath = "//tr[./td[contains(text(),'休假类型')]]//select")
	private WebElement leave_select;

	private TimeManagePage tp = new TimeManagePage();

	/**
	 * 更新雇员休假结余数据
	 * 
	 * @param employeeNo
	 *            雇员工号
	 * @param lastYear
	 *            上年休假结余
	 * @param thisYear
	 *            本年享有
	 * @param countYear
	 *            本年调整数
	 * @param nextYear
	 *            下年享有
	 * @throws SQLException
	 */
	public void updateBanlance(String employeeNo, String lastYear,
			String thisYear, String countYear, String nextYear)
			throws SQLException {
		waitFor(
				By.xpath("//div[@class='table-responsive']//tr[.//span[normalize-space(text())='"
						+ employeeNo + "']]//a/i")).click();
		if (lastYear != null) {
			waitFor(By.xpath("//input[contains(@data-bind,'PreYearBalance')]"))
					.clear();
			waitFor(By.xpath("//input[contains(@data-bind,'PreYearBalance')]"))
					.sendKeys(lastYear);
		}
		if (thisYear != null) {
			waitFor(By.xpath("//input[contains(@data-bind,'CurYearBalance')]"))
					.clear();
			waitFor(By.xpath("//input[contains(@data-bind,'CurYearBalance')]"))
					.sendKeys(thisYear);
		}
		if (countYear != null) {
			waitFor(By.xpath("//input[contains(@data-bind,'CurrentAdjust')]"))
					.clear();
			waitFor(By.xpath("//input[contains(@data-bind,'CurrentAdjust')]"))
					.sendKeys(countYear);
		}
		if (nextYear != null) {
			waitFor(By.xpath("//input[contains(@data-bind,'NextYearBalance')]"))
					.clear();
			waitFor(By.xpath("//input[contains(@data-bind,'NextYearBalance')]"))
					.sendKeys(nextYear);
		}
		find(By.xpath(".//input[contains(@value,'保存')]")).click();
		WebDriverUtils.confirmSave(driver);
	}

	/**
	 * 验证休假结余设定保存
	 * 
	 * @param employeeNo
	 *            雇员工号
	 * @param lastYear
	 *            上年休假享有
	 * @param thisYear
	 *            本年休假享有
	 * @param countYear
	 *            本年调整
	 * @param nextYear
	 *            下年享有
	 * @throws SQLException
	 */
	public void verifyBanlance(String employeeNo, String lastYear,
			String thisYear, String countYear, String nextYear)
			throws SQLException {
		String xpath = "//div[@class='table-responsive']//tr[.//span[normalize-space(text())='"
				+ employeeNo + "']]";
		WebElement row = waitFor(By
				.xpath("//div[@class='table-responsive']//tr[.//span[normalize-space(text())='"
						+ employeeNo + "']]"));
		if (lastYear != null) {
			String actual = waitFor(row,
					By.xpath("//span[contains(@data-bind,'PreYearBalance')]"))
					.getText().trim();
			Assert.isEqual(lastYear, actual);
		}
		if (thisYear != null) {
			String actual = waitFor(
					By.xpath(xpath
							+ "//span[contains(@data-bind,'CurYearBalance')]"))
					.getText().trim();
			Assert.isEqual(thisYear, actual);
		}
		if (countYear != null) {
			String actual = row
					.findElement(
							By.xpath(xpath
									+ "//span[contains(@data-bind,'CurrentAdjust')]"))
					.getText().trim();
			Assert.isEqual(countYear, actual);
		}
		if (nextYear != null) {
			String actual = row
					.findElement(
							By.xpath(xpath
									+ "//span[contains(@data-bind,'NextYearBalance')]"))
					.getText().trim();
			Assert.isEqual(nextYear, actual);
		}

	}

	/**
	 * 休假结余设立菜单，搜索对应的休假代码
	 * 
	 * @param leaveCode
	 *            休假代码
	 * @param employeeID
	 *            雇员工号
	 * @throws Exception
	 */
	public void searchLeaveBalance(String leaveCode, String employeeID)
			throws Exception {
		putInValue(waitFor(By.id("divLeaveCodesdisplay")), leaveCode);
		putInValue(waitFor(By.id("lbcNOName")), employeeID);
		find(By.id("btnSearch")).click();
		waitForVisible(By
				.xpath("//div[@class='table-responsive']//tr[.//span[normalize-space(text())='"
						+ employeeID + "']]"));
		waitLoading();
		/*
		 * String sql =
		 * "update hr_staff_policy set HolidayPolicy =3  where staffno ='0092'";
		 * DBHelper.update(sql); System.out.println("SQL执行结果为："
		 * +DBHelper.update(sql));
		 */
	}

	/**
	 * 编辑持卡资料
	 * 
	 * @param employeeNo
	 * @param effectiveDate
	 * @param expiredDate
	 * @throws Exception
	 */
	public void editCardInformation(String cardNO, String effectiveDate,
			String expiredDate) throws Exception {
		waitFor(By.id("SearchCardContent")).sendKeys("cardNO");
		waitFor(
				By.xpath("//table[@class='table table-hover']//tr[.//td[normalize-space(text())='"
						+ cardNO + "']]/td[2]/i[1]")).click();
		if (cardNO != null) {
			putInValue(
					By.xpath("//tr[.//td[normalize-space(text())='卡号:']]/td[2]/input"),
					cardNO);
		}
		if (effectiveDate != null) {
			putInValue(By.xpath("//input[@id='RangeStartDate']"), effectiveDate);
		}
		if (expiredDate != null) {
			putInValue(By.xpath("//input[@id='RangeEndDate']"), expiredDate);
		}
		waitFor(
				By.xpath(".//textarea[contains(@data-bind,'Description.Chinese')]"))
				.click();
		/*
		 * String sql =
		 * "UPDATE At_Card SET DateRange_To = '2017-10-01'  WHERE  ac.StaffNo='2345' AND ac.No = '357223376'"
		 * ; boolean result = DBHelper.update(sql); Assert.isTrue(result);
		 * System.out.println("SQL执行结果为"+result);
		 */
		waitLoading();
		driver.switchTo().defaultContent();
	}

	/**
	 * 验证分析提示校验
	 * 
	 * @param hit
	 *            提示信息
	 */
	public void analysisResultHit(String hit) {
		String verifyHit = waitFor(By.xpath("//p[@id='showP']")).getText()
				.trim();
		Assert.isEqual(hit, verifyHit);
	}

	/**
	 * 休假单导入
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	// import leave file successfully
	public void importLeave(String fileName) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		waitFor(
				By.xpath("//div[@id='divLeaveImport']/div[1]/div/div/div/ul/li[1]/a[normalize-space(text()='导入文件')]"))
				.click();
		waitFor(By.id("file0")).sendKeys(filePath);
		Utils.waitABit(3000);
		upload_btn.click();
		String alertText = dismissAlert();
		Assert.isEqual("上传文件成功", alertText);
		Utils.waitABit(3000);
		waitFor(By.xpath("//div/span[contains(text(),'验证')]")).click();
		Utils.waitABit(3000);
		waitFor(By.xpath("//div/span[contains(text(),'处理')]")).click();
		waitFor(
				By.xpath("//div[.//span[normalize-space(text())='文件导入']]//button"))
				.click();
		waitFor(By.xpath(".//a[normalize-space(text())='时间管理']")).click();
		waitFor(By.xpath(".//a[normalize-space(text())='休假申报输入']")).click();
	}

	/**
	 * 验证休假申报输入数据存在。
	 * 
	 * @param leaveCode
	 *            休假代码
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public void verifyVacationDetails(String leaveCode, String startDate,
			String endDate) {
		if (startDate != null) {
			waitFor(
					By.xpath("//tr[./td[contains(@data-bind,'Des')][normalize-space(text())='"
							+ leaveCode + "']]/td[1]/i")).click();
			String verifyStratDate = waitFor(
					By.xpath(".//tr[./td[text()='2017-10-09']]/td[3]"))
					.getText();
			Assert.isEqual(startDate, verifyStratDate);
		}

		if (endDate != null) {
			waitFor(
					By.xpath("//tr[./td[contains(@data-bind,'Des')][normalize-space(text())='"
							+ leaveCode + "']]/td[1]/i")).click();
			String verifyEndDate = waitFor(
					By.xpath(".//tr[./td[text()='2017-10-09']]/td[4]"))
					.getText();
			Assert.isEqual(endDate, verifyEndDate);
		}

		if (startDate != null) {
			waitFor(
					By.xpath("//tr[./td[contains(@data-bind,'Des')][normalize-space(text())='"
							+ leaveCode + "']]/td[1]/i")).click();
			String verifyStratDate = waitFor(
					By.xpath(".//tr[./td[text()='2017-10-09']]/td[5]"))
					.getText();
			Assert.isEqual(startDate, verifyStratDate);
		}
	}

	/**
	 * 新增考勤周期
	 * 
	 * @param cycleName
	 * @param startDate
	 * @param endDate
	 */
	public void addPeriod(String winmaxSoftCycle, String cycleName,
			String year, String month, String startDate, String endDate) {
		waitFor(
				By.xpath("//tr[./td[text()='" + winmaxSoftCycle
						+ "']]/td[3]/i[2]")).click();
		if (cycleName != null) {
			putInValue(
					waitFor(By
							.xpath("//td[@id='tdPeriodDetail']/table/tbody/tr[1]/td[2]/input")),
					cycleName);
		}

		if (month != null) {
			putInValue(
					waitFor(By.xpath("//input[contains(@data-bind,'Month')]")),
					month);
		}

		if (month != null) {
			putInValue(
					waitFor(By.xpath("//input[contains(@data-bind,'Year')]")),
					month);
		}

		if (startDate != null) {
			putInValue(
					waitFor(By
							.xpath("//input[contains(@data-bind,'StartDate')]")),
					startDate);
		}

		if (endDate != null) {
			putInValue(
					waitFor(By.xpath("//input[contains(@data-bind,'EndDate')]")),
					endDate);
		}
		waitFor(By.xpath("//input[contains(@data-bind,'IsCurrent')]")).click();
		waitFor(By.xpath("//button[contains(@onclick,'SavePeriodDetail')]"))
				.click();
		waitFor(By.xpath("//button[text()='确定']")).click();
	}

	/**
	 * 验证考勤周期明细
	 * 
	 * @param winmaxSoftCycle
	 * @param cycleName
	 * @param month
	 * @param startDate
	 * @param endDate
	 */
	public void verifyPreiodDetails(String winmaxSoftCycle, String cycleName,
			String startDate, String endDate) {
		waitFor(
				By.xpath("//tr[./td[text()='" + winmaxSoftCycle
						+ "']]/td[3]/i[1]")).click();
		if (startDate != null) {
			putInValue(waitFor(By.xpath("//input[@id='SDate']")), startDate);
		}
		if (endDate != null) {
			putInValue(waitFor(By.xpath("//input[@id='EDate']")), endDate);
		}
		waitFor(
				By.xpath("//a[contains(@onclick,'SearchTotalPeriodDetailByDate')]/i"))
				.click();
		String actualCycleName = waitFor(
				By.xpath(".//tr[./td[text()='" + cycleName + "']]/td[3]"))
				.getText();
		Assert.isEqual(cycleName, actualCycleName);
	}

	public void importVacationBalance(String fileName) throws Exception {
		waitFor(By.xpath("//div[@id='divListData']//a[contains(.,'导入')]"))
				.click();
		WebDriverUtils.importFile(fileName, driver);
		WebDriverUtils.verifyProcessImportFile(driver);
	}

	/**
	 * 新增休假申报输入明细 【新增其它时间的休假】
	 * 
	 * @param leaveType
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void addLeaveRecord(String leaveCode, String leaveType,
			String startDate, String endDate, String startTime, String endTime)
			throws Exception {
		waitFor(
				By.xpath("//tr[@id='" + leaveCode
						+ "']//i[contains(@data-bind,'Add')]")).click();
		if (startDate != null) {
			WebElement element = find(By
					.xpath("//input[contains(@data-bind,'StartDate')]"));
			element.sendKeys(Keys.CONTROL, "a");// Ctrl+A
			waitFor(By.xpath("//input[contains(@data-bind,'StartDate')]"))
					.sendKeys(startDate);
		}
		waitLoading();
		if (endDate != null) {
			WebElement element = find(By
					.xpath("//input[contains(@data-bind,'EndDate')]"));
			element.sendKeys(Keys.CONTROL, "a");
			waitFor(By.xpath("//input[contains(@data-bind,'EndDate')]"))
					.sendKeys(endDate);
		}
		waitLoading();
		WebElement selector = waitFor(By.xpath("//select[@id='0']"));
		Select sel = new Select(selector);
		sel.selectByVisibleText(leaveType);
		if (startTime != null) {
			putInValue(By.xpath("//input[@id='StartTime0']"), startTime);
		}
		waitLoading();
		if (endTime != null) {
			putInValue(By.xpath("//input[@id='EndTime0']"), endTime);
		}
		waitFor(By.xpath("//table[@id='TranDetailDiv']/tbody/tr/td[8]/input"))
				.click();
		waitLoading();
		find(
				By.xpath("//td[@id='dvtdTransationDetail']//button[normalize-space(text())='保存']"))
				.click();
		String saveText = waitFor(By.id("showP")).getText().trim();
		if (saveText.equals("操作成功!")) {
			Assert.isEqual("操作成功!", saveText, "成功增加休假单");
			waitFor(By.xpath("//div[@class='ui-dialog-buttonset']//button"))
					.click();
		}
	}

	/**
	 * 删除休假明细记录
	 * 
	 * @param leave
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void deleteLeaveRecord(String leave, String startDate, String endDate)
			throws Exception {
		String leaveCode = tp.getLeaveCode(leave);
		tp.expandLeaveType(leaveCode);
		String xpath = "//tr[starts-with(@id,'Detail"
				+ leaveCode
				+ "')]"
				+ "[./td[contains(@data-bind,'StartDate')][normalize-space(text())='"
				+ startDate + "']]" + "/td/i[contains(@class,'trash')]";
		waitFor(By.xpath(xpath)).click();
		// 确认删除
		waitForVisible(By.xpath("//button[text()='确定']")).click();
		String deleteText = waitForVisible(By.id("showP")).getText().trim();
		waitForVisible(By.xpath("//button[text()='确定']")).click();
		Assert.isEqual("删除成功！", deleteText, "删除休假单");
		waitForNotVisible(By.xpath(xpath));
	}

	/**
	 * 点击考勤统计、查询按钮
	 */
	public void clickStatisticsButton() {
		waitFor(By.xpath(".//a[@id='btnSearch']/i")).click();
		waitFor(By.xpath(".//a[contains(@onclick,'Census')]/i")).click();
	}

	public void verifyVacationBalanceUIWithExcel(
			List<LinkedHashMap<String, String>> excelData) throws Exception {
		for (int i = 0; i < excelData.size(); i++) {
			LinkedHashMap<String, String> rowExcelData = excelData.get(i);
			HashMap<String, String> hData = new HashMap<String, String>();
			hData.put("员工工号", rowExcelData.get("员工工号"));
			hData.put("休假代码", rowExcelData.get("休假代码"));
			String tableXpath = "//table[./tbody[contains(@data-bind,'ListData')]]";
			String rowXpath = WebDriverUtils.getRowTrXpathByHashMap(tableXpath,
					hData, driver);
			HashMap<String, String> uiData = WebDriverUtils.getRowData(
					rowXpath, driver);
			Set<String> keys = rowExcelData.keySet();
			String errorMsg = "下列值不对： ";
			boolean flag = true;
			for (String k : keys) {
				if (!rowExcelData.get(k).equals(uiData.get(k))) {
					errorMsg = errorMsg + "列名 <" + k + ">, 页面值 <"
							+ uiData.get(k) + ">, excel值<"
							+ rowExcelData.get(k) + ">; ";
					flag = false;
				}
			}
			Assert.isTrue(flag, errorMsg);
		}
	}

	/**
	 * 编辑休假代码设定
	 * 
	 * @param keyWords
	 * @param minimumLeaveNumber
	 * @param minimumLeaveUnit
	 * @throws Exception
	 */
	public void editLeaveCodeSet(String keyWords, String leaveCode,
			String minimumLeaveNumber, String minimumLeaveUnit)
			throws Exception {
		waitFor(By.xpath("//input[@id='Filter']")).sendKeys(keyWords);
		waitFor(By.xpath(".//a[contains(@data-bind,'Search')]/span")).click();
		waitLoading();
		waitFor(By.xpath(".//tr[./td[text()='" + leaveCode + "']]/td[1]/a"))
				.click();
		String xpath = "//div[@id='editorDiv']/fieldset[4]/table/tfoot/tr/td[2]/button[1]";
		WebElement saveButton = waitFor(By.xpath(xpath));
		WebDriverUtils.scrollTo(saveButton, driver);
		if (minimumLeaveNumber != null) {
			putInValue(By.xpath("//input[contains(@data-bind,'DayMinData')]"),
					minimumLeaveNumber);
		}
		if (minimumLeaveUnit != null) {
			putInValue(By.xpath("//input[contains(@data-bind,':LimitUnit')]"),
					minimumLeaveUnit);
		}
		find(By.xpath(xpath)).click();
		waitLoading();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
	}

	/**
	 * 新增万年历
	 * 
	 * @param calendarWatch
	 */
	public void addCalendarWatch(String vacationType, String ChineseDes,
			String EnglishDes) {
		waitFor(By.xpath("//a[text()='新增']/i")).click();
		if (vacationType != null) {
			putInValue(
					By.xpath("//input[contains(@data-bind,'HolidayPolicy,disable')]"),
					vacationType);
		}
		if (ChineseDes != null) {
			putInValue(
					By.xpath("//input[contains(@data-bind,'Description.Chinese')]"),
					ChineseDes);
		}

		if (EnglishDes != null) {
			putInValue(
					By.xpath("//input[contains(@data-bind,'Description.English')]"),
					EnglishDes);
		}
		waitFor(By.xpath("//button[contains(@onclick,'SaveHolidayMaster')]"))
				.click();
		WebDriverUtils.confirmSave(driver);
	}

	/**
	 * 删除万年历
	 * 
	 * @param vacationType
	 * @throws Exception
	 */
	public void deleteCalendarWatch(String vacationType) throws Exception {
		waitFor(By.xpath(".//tr[./td[text()='" + vacationType + "']]/td[1]"))
				.click();
		waitFor(By.xpath(".//a[text()='清除']/i")).click();
		waitFor(
				By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"))
				.click();
		waitLoading();
		waitFor(
				By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"))
				.click();
	}

	/**
	 * 验证新增日历是否存在
	 * 
	 * @param calendarName
	 */
	public void verifycalendarWatch(String calendarName) {
		String actualCalendarName = waitFor(
				By.xpath("//tr[./td[text()='" + calendarName + "']]/td[2]"))
				.getText().trim();
		Assert.isEqual(calendarName, actualCalendarName);
	}

	/**
	 * 搜索员工持卡资料关键字
	 * 
	 * @param card
	 * @throws Exception
	 */
	public void searchAttendanceCard(String card) throws Exception {
		driver.switchTo().defaultContent();
		String frameElement = "_PartialIframe";
		driver.switchTo().frame(frameElement);
		waitLoading();
		putInValue(By.id("SearchCardContent"), card);
		waitFor(By.xpath("//a[@id='btnSearch']/i")).click();
		waitLoading();
	}

	/**
	 * 
	 * @param leaveCode
	 * @param startDate
	 * @param endDate
	 * @throws Exception
	 */
	public void addLeaveDetails(String leaveCode, String startDate,
			String endDate) throws Exception {
		waitFor(
				By.xpath("//tr[@id='" + leaveCode
						+ "']//i[contains(@data-bind,'Add')]")).click();
		if (startDate != null) {
			WebElement element = find(By
					.xpath("//input[contains(@data-bind,'StartDate')]"));
			element.sendKeys(Keys.CONTROL, "a");
			waitFor(By.xpath("//input[contains(@data-bind,'StartDate')]"))
					.sendKeys(startDate);
		}
		waitLoading();
		if (endDate != null) {
			WebElement element = find(By
					.xpath("//input[contains(@data-bind,'EndDate')]"));
			element.sendKeys(Keys.CONTROL, "a");
			waitFor(By.xpath("//input[contains(@data-bind,'EndDate')]"))
					.sendKeys(endDate);
		}
		waitLoading();
		WebElement ele = waitFor(By
				.xpath(".//input[contains(@data-bind,'Remark,enable')]"));
		scrollTo(ele);
		ele.click();
		find(
				By.xpath("//td[@id='dvtdTransationDetail']//button[normalize-space(text())='保存']"))
				.click();
		WebDriverUtils.confirmSave(driver);
	}

	/**
	 * 验证休假申报输入(包含)提示
	 * 
	 * @param hint
	 *            提示信息
	 */
	public void verifyLeaveDetails(String saveHint) {
		String acutalSaveHint = waitFor(By.id("showP")).getText().trim();
		Assert.isContains(acutalSaveHint, saveHint, "验证提示信息");
	}

	/**
	 * 点击休假上年年结处理
	 * 
	 * @param yearType
	 * @param cleanupDeadline
	 * @throws Exception
	 */
	public void clickYearEndButton(String yearType, String cleanupDeadline)
			throws Exception {
		waitFor(
				By.xpath("//tr[./td[.//*[text()='" + yearType
						+ "']]]/td[5]/a[2]/i")).click();
		if (cleanupDeadline != null) {
			WebElement element = find(By
					.xpath("//input[@id='txt_clearEndDate']"));
			element.sendKeys(Keys.CONTROL, "a");
			waitFor(By.xpath("//input[@id='txt_clearEndDate']")).sendKeys(
					cleanupDeadline);
			waitFor(By.xpath("//input[@id='txt_lastEndDate']")).click();
			Utils.waitABit(3000);
		}
		waitFor(By.xpath("//input[contains(@onclick,'LeaveSurplusHandle')]"))
				.click();
		waitLoading();
	}

	/**
	 * 新增加班单
	 * 
	 * @param staffNo
	 *            雇员工号
	 * @param date
	 *            加班日期
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param deadline
	 *            最后生效日期
	 * @param switchTime
	 *            转换小时数
	 */
	public void addOvertimeApplication(String staffNo, String date,
			String beginTime, String endTime, String deadline, String switchTime) {
		waitFor(By.xpath("//a[text()='新增']/i")).click();
		waitFor(By.xpath("//div[@id='dvSelStaffNoShowWindow']/span")).click();
		waitFor(By.xpath("//input[@id='dvSelStaffNosearch']"))
				.sendKeys(staffNo);
		waitFor(By.xpath("//td[text()='" + staffNo + "']")).click();
		if (date != null) {
			putInValue(By.xpath("//input[@id='inpDate']"), date);
			waitFor(By.xpath("//input[contains(@data-bind,'BatchCode')]"))
					.click();
		}
		if (beginTime != null) {
			putInValue(find(By.id("dvStartDatedisplay")), beginTime);
		}
		if (endTime != null) {
			putInValue(find(By.id("dvEndDatedisplay")), endTime);
		}
		if (deadline != null) {
			putInValue(By.xpath("//input[@id='dvUseOtLastDate']"), deadline);
		}
		if (switchTime != null) {
			putInValue(By.xpath("//input[@id='InpswitchHours']"), switchTime);
		}
		waitFor(By.xpath("//input[contains(@name,'strantype')][@title='1']"))
				.click();
		tp.saveOT();
	}

	/**
	 * 模糊搜索休假代码
	 * 
	 * @param keyWords
	 *            休假政策代码
	 * @throws Exception
	 */
	public void inputKeyWordsleaveCode(String keyWords) throws Exception {
		waitFor(By.xpath("//input[@id='Filter']")).sendKeys(keyWords);
		waitFor(By.xpath(".//a[contains(@data-bind,'Search')]/span")).click();
		waitLoading();
	}

	public void openEditLeaveCodeForm(String leaveCode) throws Exception {
		waitFor(By.xpath(".//tr[./td[text()='" + leaveCode + "']]/td[1]/a"))
				.click();
	}

	/**
	 * 设置事后部假是否开启
	 * 
	 * @param leaveCode
	 * @param checkBox
	 * @param allowDays
	 * @throws Exception
	 */
	public void editLeaveCodeSettings(String leaveCode, String checkBox,
			String allowDays) throws Exception {
		waitFor(By.xpath(".//tr[./td[text()='" + leaveCode + "']]/td[1]/a"))
				.click();
		String xpath = "//div[@id='editorDiv']/fieldset[4]/table/tfoot/tr/td[2]/button[1]";
		WebElement saveButton = waitFor(By.xpath(xpath));
		WebDriverUtils.scrollTo(saveButton, driver);
		WebElement apple = waitFor(By
				.xpath("//input[contains(@data-bind,'checked:IsAfterwards')]"));
		boolean checkBoxStatus = apple.isSelected();
		if (checkBoxStatus == true) {
			waitFor(By.xpath("//input[contains(@data-bind,'DayOfAfterwards')]"))
					.sendKeys(allowDays);
		} else {
			waitFor(
					By.xpath("//input[contains(@data-bind,'checked:IsAfterwards')]"))
					.click();
			waitFor(By.xpath("//input[contains(@data-bind,'DayOfAfterwards')]"))
					.sendKeys(allowDays);
		}
		saveButton.click();
		waitLoading();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
	}

	// 修改休假代码 -》假期审批选项
	public void updateLeaveCodeItems(HashMap<String, String> data)
			throws Exception {
		String xpath = "//*[@id='editorDiv']//button[contains(@data-bind,'Save')]";
		WebElement saveButton = waitFor(By.xpath(xpath));
		WebElement legend = waitFor(By
				.xpath("//fieldset[./legend[text()='假期审批选项']]"));
		scrollTo(legend);
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);
			By by = By
					.xpath("//fieldset[./legend[text()='假期审批选项']]//tr/td[contains(.,'"
							+ key + "')]/input");
			WebElement field = legend.findElement(by);
			if (field.getAttribute("type").contains("text")) {
				putInValue(field, value);
			} else {
				// not implement yet
			}
		}
		WebDriverUtils.scrollTo(saveButton, driver);
		saveButton.click();
		waitLoading();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
	}

	/**
	 * 判断某个单选框是否已经被选择
	 * 
	 * @param by
	 * @return 判断单选框的状态
	 */
	public static boolean checkBoxStatus(String selector) {
		String xpath = "" + selector + "";
		WebElement apple = driver.findElement(By.xpath(xpath));
		boolean isAppleSelect = apple.isSelected();
		if (isAppleSelect == true) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 高级筛选雇员工号
	 * 
	 * @param staffno
	 *            雇员工号
	 * @throws Exception
	 */
	public void advancedFilterStaffNo(String staffNo) throws Exception {
		waitLoading();
		waitFor(By.xpath("//a[@id='btnSenior']/i")).click();
		waitFor(By.xpath("//li[@id='liPersonnel']/a/span")).click();
		waitFor(By.xpath("//span[@id='dvStaffShowWindow']")).click();
		waitFor(By.xpath("//input[@id='dvStaffSearchFilter']")).sendKeys(
				staffNo);
		waitFor(By.xpath("//input[@id='btndvStaffSearch']")).click();
		waitFor(By.xpath("//tr[./td[text()='" + staffNo + "']]/td[1]/input"))
				.click();
		waitFor(By.xpath("//input[@id='confirmdvStaffSelection']")).click();
		waitLoading();
		waitFor(By.xpath("//div[@id='dvConfirmSearch']/span")).click();
	}

	/**
	 * 雇员考勤分析
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @throws Exception
	 */
	public void employeeAttendanceAnalysis(String startDate, String endDate)
			throws Exception {
		String begin = "//input[contains(@data-bind,'StartDate')]";
		String end = "//input[contains(@data-bind,'EndDate')]";
		if (startDate != null) {
			putInValue(waitFor(By.xpath(begin)), startDate);
		}
		if (endDate != null) {
			putInValue(waitFor(By.xpath(end)), endDate);
		}
		String analyse = "//input[@value='分析']";
		waitFor(By.xpath(analyse)).click();
		waitLoading();
		waitFor(By.xpath("//button[text()='确定']")).click();
	}

	/**
	 * 考勤周期明细统计
	 * 
	 * @param period
	 *            考勤周期名称
	 * @param detail
	 *            明细时间
	 * @throws Exception
	 */
	public void attendanceCountsPeriod(String period, String detail)
			throws Exception {
		if (period != null) {
			waitFor(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
			waitFor(By.xpath("//td[text()='" + period + "']")).click();
			waitLoading();
		}
		if (detail != null) {
			waitFor(By.xpath("//div[@id='dvPeriodDetailShowWindow']/span"))
					.click();
			waitFor(By.xpath("//td[text()='" + detail + "']")).click();
			waitLoading();
		}
		waitFor(By.xpath("//a[contains(@onclick,'Census')]/i")).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
	}

	/**
	 * 验证考勤周期统计的休假总小时数是否正确
	 * 
	 * @param totolHours
	 *            休假代码总计小时数
	 */
	public void verifyTotalHoursLeaveTime(String totolHours, String staffNo) {
		String actualTotolHours = waitFor(
				By.xpath(".//tr[./td[text()='" + staffNo + "']]/td[7]/label"))
				.getText().trim();
		Assert.isEqual(totolHours, actualTotolHours);
	}

	/**
	 * 验证休假单总小时数,[全天]
	 * 
	 * @param leave
	 *            休假代码设定
	 * @param totalHours
	 *            休假明细小时数
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @throws Exception
	 */
	public void verifyVacationTotalHours(String leave, String totalHours,
			String startDate, String endDate) throws Exception {
		String leaveCode = tp.getLeaveCode(leave);
		tp.expandLeaveType(leaveCode);
		String xpath = "//tr[starts-with(@id,'Detail"
				+ leaveCode
				+ "')]"
				+ "[./td[contains(@data-bind,'StartDate')][normalize-space(text())='"
				+ startDate + "']]"
				+ "//td[contains(@data-bind,'TotalNoOfHours')]";
		String actualTotalHours = waitFor(By.xpath(xpath)).getText().trim();
		Assert.isEqual(totalHours, actualTotalHours);
	}

	/**
	 * 清空日期控件
	 */
	public void clearOffVacationDate() {
		waitFor(By.xpath("//input[@id='StartDate']")).clear();
		waitFor(By.xpath("//button[text()='确定']")).click();
		waitFor(By.xpath("//input[@id='StartDate']")).clear();
		waitFor(By.xpath(".//input[@id='EndDate']")).click();
	}

	/**
	 * 点击流程按钮【发起、提交、撤销】
	 * 
	 * @param buttonName
	 *            按钮名称
	 */
	public void clickWorkFlowFormsButton(String buttonName) {
		waitFor(By.xpath(".//button[@id='" + buttonName + " ']")).click();
	}

	/**
	 * 验证流程校验信息
	 * 
	 * @param information
	 */
	public void verifyWorkFlowInformation(String information) {
		String actualInfomation = waitFor(By.xpath("//p[@id='showP']"))
				.getText().trim();
		Assert.isEqual(information, actualInfomation);
	}

	/**
	 * 休假单导入
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	public void importLeaveDetailsExcel(String fileName) throws Exception {
		waitFor(By.xpath("//a[text()='导入文件']/i")).click();
		WebDriverUtils.importFile(fileName, driver);
		find(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]"))
				.click();
		waitForVisible(
				By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"))
				.click();
		Utils.waitABit(3000);
		waitForNotVisibleLong(By
				.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
	}

	/**
	 * 休假结余设立菜单，筛选多个休假类型。
	 * 
	 * @param leaveCode
	 * @throws Exception
	 */
	public void selectLeaveCodeFor(String leaveCode) throws Exception {
		waitFor(By.xpath("//span[@id='divLeaveCodesShowWindow']")).click();
		String[] array = leaveCode.split("\\|");
		for (int i = 0; i < array.length; i++) {
			String xpath = "//tr[./td[text()='" + array[i] + "']]/td[1]/input";
			waitFor(By.xpath(xpath)).click();
		}
		waitLoading();
		waitFor(
				By.xpath("//input[@id='confirmdivLeaveCodesSelection'][@value='确认']"))
				.click();
		waitFor(By.xpath("//a[@id='btnSearch']/i")).click();
		waitLoading();
	}

	/**
	 * 在休假结余设立界面店家导出
	 */
	public void clickExportButtonLeaveBanlance() {
		waitFor(By.xpath(".//a[text()='导出']/i")).click();
		String winHandleCurrent = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(winHandleCurrent)) {
				driver.switchTo().window(winHandle);
				break;
			}
		}
		Utils.waitABit(5000);
		switchToOpenerWindow();
	}

	public LinkedHashMap<String, String> getRowLeaveBanlanceData(
			String trRowXpath) throws Exception {
		waitLoading();
		LinkedHashMap<String, String> actualData = new LinkedHashMap<String, String>();
		List<WebElement> headers = waitForMinimumNum(
				By.xpath(trRowXpath + "/../../thead/tr/td"), 1);
		List<WebElement> values = waitForMinimumNum(
				By.xpath(trRowXpath + "/td"), 1);
		for (int i = 1; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i)
						.getText().trim());
			}
		}
		return actualData;
	}

	/**
	 * 用于休假结余设立菜单，返回页面的List<LinkedHashMap>
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> getLeaveBanlaceList() throws Exception {
		List<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		List<WebElement> trList = findAll(By
				.xpath("//table[contains(@class,'condensed')]/tbody/tr"));
		for (int i = 1; i < trList.size(); i++) {
			String trRowXpath = "//table[contains(@class,'condensed')][contains(@style,'margin-bottom')]/tbody/tr["
					+ i + "]";
			LinkedHashMap<String, String> dataOneRow = getRowLeaveBanlanceData(trRowXpath);
			data.add(dataOneRow);
		}
		return data;
	}

	/**
	 * 验证导出表格的List和传入的List
	 * 
	 * @param fileName
	 * @param previewData
	 * @throws Exception
	 */
	public void verifyExportExcelDataCompareWithInterfaceTable(String fileName,
			List<HashMap<String, String>> previewData) throws Exception {
		String downloadFile = System.getProperty("user.dir")
				+ Const.DOWNLOAD_FOLDER + fileName + ".xlsx";
		List<LinkedHashMap<String, String>> excelData = Excel
				.readWorkBook(downloadFile);
		FileUtil.deleteFile(downloadFile);
		Assert.isEqual(previewData.size(), excelData.size());
		for (int i = 0; i < excelData.size(); i++) {
			boolean flag = excelData.get(i).equals(previewData.get(i))
					&& previewData.get(i).equals(excelData.get(i));
			Assert.isTrue(flag, "excel 数据 ## " + excelData + ", 预览的数据 ### "
					+ previewData);
		}
	}

	/**
	 * 标准导入验证步骤
	 * 
	 * @throws Exception
	 */
	public void standardImportProcess() throws Exception {
		waitFor(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]"))
				.click();
		Utils.waitABit(20000);
		waitForVisible(
				By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"))
				.click();
		Utils.waitABit(3000);
		waitForNotVisibleLong(By
				.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
		String actualImportInfo = waitFor(
				By.xpath("//div[@id='DataImport']/div[3]/p")).getText().trim();
		Assert.isContains(actualImportInfo, "导入成功!", "导入成功!");
		waitFor(By.xpath("//span[text()='文件导入']//following-sibling::*"))
				.click();// 点击关闭
		waitLoading();
	}

	/**
	 * 下属排班导入
	 * 
	 * @param fileName
	 *            导入文件名
	 * @throws Exception
	 */
	public void importAttendanceExcel(String fileName) throws Exception {
		waitFor(By.xpath("//a[@id='importShift']/span")).click();
		WebDriverUtils.importFile(fileName, driver);
		standardImportProcess();
		waitFor(By.xpath("//div[@id='Navbreadcrumb']/div/ul/li[2]/a")).click();
	}

	/**
	 * 验证考勤周期名称
	 * 
	 * @param preiodName
	 *            周期名称
	 * @param staffNo
	 *            雇员工号
	 */
	public void verifyAttendancePreiod(String preiodName, String staffNo) {
		waitFor(By.xpath("//a[contains(@data-bind,'SearchCardInfo')]/i"))
				.click();
		String actualPreiodName = waitFor(
				By.xpath("//tr[./td[text()='" + staffNo + "']]/td[5]"))
				.getText().trim();
		Assert.isEqual(preiodName, actualPreiodName);
	}

	/**
	 * 导入当月下属排班计算
	 * 
	 * @param fileName
	 *            Excel文件名
	 * @throws Exception
	 */
	public void importWorkingHoursMonthExcel(String fileName) throws Exception {
		waitFor(By.xpath("//a[contains(@onclick,'OpenImportDlg')]/i")).click();
		WebDriverUtils.importFile(fileName, driver);
		waitFor(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]"))
				.click();
		Utils.waitABit(3000);
		waitForVisible(
				By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"))
				.click();
		Utils.waitABit(3000);
		waitForNotVisibleLong(By
				.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"));
		String actualImportInfo = waitFor(By.id("showP")).getText().trim();
		Assert.isContains(actualImportInfo, "导入成功!", "验证导入是否成功");
		waitFor(
				By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"))
				.click();
	}

	/**
	 * 当月排班计算选择周期，周期明细时间
	 * 
	 * @param preiodName
	 * @param preiodDetails
	 * @throws Exception
	 */
	public void selectAttendancePeriodAttendanceDetail(String preiodName,
			String preiodDetails) throws Exception {
		waitFor(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
		waitFor(By.xpath("//input[@id='dvPeriodsearch']")).sendKeys(preiodName);
		waitLoading();
		waitFor(By.xpath("//td[text()='" + preiodName + "']")).click();
		waitFor(By.xpath("//div[@id='dvPeriodDetailShowWindow']/span")).click();
		waitFor(By.xpath("//input[@id='dvPeriodDetailsearch']")).sendKeys(
				preiodDetails);
		waitLoading();
		waitFor(By.xpath("//td[text()='" + preiodDetails + "']")).click();
		waitLoading();
	}

	/**
	 * 点击同步工时点击冻结
	 * 
	 * @throws Exception
	 */
	public void clickSyncCalculateBtnAndFreeze() throws Exception {
		waitFor(By.xpath("//a[text()='冻结']/i")).click();
		waitLoading();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
		String actualFreezeInfo = waitFor(By.id("showP")).getText().trim();
		Assert.isContains(actualFreezeInfo, "冻结成功", "验证冻结是否成功");
		waitFor(
				By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"))
				.click();
	}

	/**
	 * 验证排班小时数，应工作小时数，欠班小时数
	 * 
	 * @param staffNo
	 * @param hoursAttendance
	 * @param shouldBeWorkingHours
	 * @param oweClassHours
	 * @throws Exception
	 */
	public void verifyAttendanceCalculationResult(String staffNo,
			String hoursAttendance, String shouldBeWorkingHours,
			String oweClassHours) throws Exception {
		waitLoading();
		String actualHoursAttendance = waitFor(
				By.xpath("//tr[./td[text()='" + staffNo + "']]/td[5]"))
				.getText().trim();
		Assert.isEqual(hoursAttendance, actualHoursAttendance);
		String actualShouldBeWorkingHours = waitFor(
				By.xpath("//tr[./td[text()='" + staffNo + "']]/td[6]"))
				.getText().trim();
		Assert.isEqual(shouldBeWorkingHours, actualShouldBeWorkingHours);
		String actualOweClassHours = waitFor(
				By.xpath("//tr[./td[text()='" + staffNo + "']]/td[7]"))
				.getText().trim();
		Assert.isEqual(oweClassHours, actualOweClassHours);
	}

	public void cancelCalculateFreezeBtn() throws Exception {
		waitFor(By.xpath("//a[text()='取消冻结']/i")).click();
		Alert alert = driver.switchTo().alert();
		alert.accept();
		waitLoading();
		String actualCancelFreezeInfo = waitFor(By.id("showP")).getText()
				.trim();
		Assert.isContains(actualCancelFreezeInfo, "取消冻结成功", "验证取消冻结");
		waitFor(
				By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"))
				.click();
		waitLoading();
	}

	/**
	 * 选择订单日期范围
	 * 
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public void selectOrderDateRange(String startDate, String endDate) {
		String js = "document.getElementById('fromDate').removeAttribute('readonly');";
		WebDriverUtils.executeJS("" + js + "", driver);
		putInValue(By.xpath("//input[@id='fromDate']"), startDate);
		String toDate = "document.getElementById('toDate').removeAttribute('readonly');";
		WebDriverUtils.executeJS("" + toDate + "", driver);
		putInValue(By.xpath("//input[@id='toDate']"), startDate);
		waitFor(By.xpath(".//input[@id='searchBtn']")).click();
	}

	public void verifyOrderInformationContains(String info) {
		String actual = waitFor(
				By.xpath(".//img[contains(@src,'grayben.png')]//following-sibling::*[1]"))
				.getText().trim();
		Assert.isContains(actual, info, "验证查询信息");
	}
	
   public void optionOrder(){
	   waitFor(By.xpath(".//div[@id='alreadyLogin']/div[1]/a")).click();
   }
   
   public void  visitBaiduInBrowser(){
	   driver.get("https://www.baidu.com/");
   }
   
   public void findInfomation(String content){
	   waitFor(By.xpath("//input[@id='kw']")).sendKeys(content);
	   waitFor(By.xpath("//input[@id='su']")).click();
   }

}
