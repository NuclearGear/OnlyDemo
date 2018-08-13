package com.winmax.pages.attendance;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.winmax.config.Const;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class LeaveFormPage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(LeaveFormPage.class);
	
	@FindBy(xpath="//tr[./td[contains(text(),'休假类型')]]//select")
	private WebElement leave_select;
	
	public LeaveFormPage() {
		PageFactory.initElements(driver, this);
	}

	public List<String> getLeaveTypesFromSelect() {
		Select leaveType = new Select(waitFor(By.xpath("//tr[./td[contains(text(),'休假类型')]]//select")));
		List<WebElement> values_ele = leaveType.getOptions();
		return getListElementsText(values_ele);
	}
	
	private void closeWarning() {
		try {
			waitForShort(By.xpath("//div[./div/p[contains(text(),'日期不能为空')]]//button[text()='确定']")).click();
		} catch (Exception e) {
			;
		}
	}
	
	/**
	 * 
	 * @param startDate 休假开始时间
	 * @param endDate  休假结束时间
	 * @param type: 休假类型
	 * @throws Exception
	 */
	public void fillLeaveForm(String startDate, String endDate, String type) throws Exception {
		putInValue(waitFor(By.id("StartDate")), startDate);
		//sometimes pop up alert 日期不能为空
		closeWarning();
		putInValue(waitFor(By.id("EndDate")), endDate);
		//sometimes pop up alert 日期不能为空
		closeWarning();
		Select leaveType = new Select(leave_select);
		leaveType.selectByVisibleText(type);		 
		
		//check total hours calculated correctly
		String label_text = waitFor(By.xpath("//div[@id='divLeaveApp']/table//tr[2]/td[contains(@style,'bold')]")).getText().trim();
		logger.info("UI calculate leave hours/days and display as: " + label_text);
		// 共 : x 天
		String days;
		try {
			String hours = label_text.substring(label_text.indexOf(":")+1, label_text.indexOf("小时")).trim();
			days = String.valueOf(Integer.valueOf(hours)/8);
		} catch (Exception e) {
			days = label_text.substring(label_text.indexOf(":")+1, label_text.indexOf("天")).trim();
		}
		logger.info("Total days calculated according to input date: " + days);
		
		//verify details lines should == calculate result
		int totalLines = findAll(By.xpath("//div[@id='leaveDetails']//tr")).size();
		Assert.isEqual(days, String.valueOf(totalLines));		
	}
	
	/**
	 * 休假备注
	 */
	public void fillComments() throws Exception {
		find(By.xpath("//tr[./td[contains(text(),'备注2')]]//textarea")).sendKeys(Utils.getRandString(10));
	}
	
	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @param type
	 * @throws Exception
	 */
	public void fillLeaveFormAllDefaultTime(String startDate, String endDate, String type) throws Exception {
		fillLeaveForm(startDate, endDate, type);
	}
	
	public void fillLeaveFormAllNoAttach(String startDate, String endDate, String type, String sTime, String endTime) throws Exception {
		fillLeaveForm(startDate, endDate, type);
		fillTime("1", sTime, endTime);
	}
	
	//current only update the begin time/end time for the 1st line
	public void fillTime(String lineNo, String sTime, String endTime) throws Exception {
		selectByVisibleText(By.id("0"), "其它");
		By sTime_input_by = By.xpath("//tr[1]/td/input[@id='StartTime0']");
		By endTime_input_by = By.xpath("//tr[1]/td/input[@id='EndTime0']");
		putInValue(sTime_input_by, sTime);
		find(sTime_input_by).click();
		Utils.waitABit(1000);
		putInValue(endTime_input_by, endTime);
		find(sTime_input_by).click();
		Utils.waitABit(1000);
	}
	
	public void checkFormSaved() throws Exception {	
		int winSize = driver.getWindowHandles().size();
		find(By.id("发起 ")).click();
		WebDriverUtils.waitLoading(driver);
		waitForWindows(winSize - 1);
		switchToOpenerWindow();		
	}
	
	public void checkFormSaveFailed(String msg) throws Exception {
		find(By.id("发起 ")).click();
		String failText = waitForVisible(By.xpath("//div[@id='ps_alert']/p")).getText().trim();
		if (msg.contains("试用期") || msg.contains("重复申请休假")) {
			Assert.isContains(failText, msg, "message");
		} else {		
			Assert.isEqual(msg, failText, "Failed to save leave form ");
		} 
		find(By.xpath("//div[./div[@id='ps_alert']]/div[contains(@class,'button')]//button")).click();
		closeCurrentWindow();		
		waitForWindows(1);
		switchToOpenerWindow();		
	}
	
	public void checkLeaveFormMessage(String msg) throws Exception {
		String failText = waitForVisible(By.xpath("//div[@id='ps_alert']/p")).getText().trim();
		Assert.isEqual(msg, failText, "Failed to save leave form ");		 
		find(By.xpath("//div[./div[@id='ps_alert']]/div[contains(@class,'button')]//button")).click();
		closeCurrentWindow();		
		waitForWindows(1);
		switchToOpenerWindow();	
	}
	
	public void uploadFile() throws Exception {
		WebElement file_upload = find(By.id("hrsimpleFile"));
		executeJS("arguments[0].removeAttribute('style')", file_upload);
		file_upload.sendKeys(Utils.getDefaultFilePath(Const.DEFAULT_ATTA));
		WebElement uploadBtn = find(By.xpath("//span[text()='上传']"));
		scrollTo(uploadBtn);
		uploadBtn.click();
		waitLoading();
		waitForVisible(By.xpath("//tbody[@id='attlistBody']/tr/td/a[text()='" + Const.DEFAULT_ATTA + "']"));
	}
	
	/**
	 * 验证请假时间是连续的，不排除周末和国家假期
	 * @param beginDate
	 * @param endDate
	 */
	public void checkLeaveDetailsLines(String startDate, String endDate) throws Exception {
		String days = Utils.getDaysNo(startDate, endDate);
		int expectTotal = Integer.valueOf(days) + 1;
		
		//验证 共  : 40 小时
		String text = waitFor(By.xpath("//div[@id='divLeaveApp']/table/tbody//td[contains(@data-bind,'TotalDetails')]")).getText().trim();
		Pattern pattern = Pattern.compile("[^0-9]");
        Matcher matcher = pattern.matcher(text);
        String totalNoStr = matcher.replaceAll("");
        int totalNo = Integer.valueOf(totalNoStr);
        if (text.contains("小时")) {
        	Assert.isEqual(expectTotal, totalNo/8, "请假总小时/天");
        } else {
        	Assert.isEqual(expectTotal, totalNo, "请假总小时/天");
        }		
		List<WebElement> date_list = findAll(By.xpath("//div[@id='leaveDetails']/table/tbody//td[contains(@data-bind,'Date')]"));

		Assert.isEqual(expectTotal, date_list.size(), "明细总条数");
		for (int i=0; i<expectTotal; i++) {
			Assert.isEqual(Utils.addDate(startDate, i), date_list.get(i).getText().trim());
		}
	}
}
