	package com.winmax.pages.attendance;
	
	import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
	








import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

	import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;
	
	public class PayRollPage extends WebDriverSteps{
		
		private static final Logger logger = LogManager.getLogger(PayRollPage.class);
		private WebDriverSteps webSteps = new WebDriverSteps() ;
		VacationPage vp = new VacationPage();
		
	/**
	 * 审批薪资数据
	 * @param staffno 雇员工号
	 */
	public void AuditedpayCodeData(String staffno) {
		waitFor(By.xpath(".//input[@id='ULCheckAll']")).click();
		waitFor(By.xpath(".//*[contains(@data-bind,'ConfirmeData')]/i")).click();
		WebDriverUtils.confirmSave(driver);	
		waitFor(By.xpath(".//a[text()='员工自助']")).click();
	}
	
	/**
	 * 验证数据是否审核成功
	 * @param staffNo      雇员工号
	 * @param basicPay     基本工资
	 * @param pendingData  生效日期
	 */
	public void checkOutPayCodeData(String payItems, String basicPay) {
		 String CheckoutbasicPay = waitFor(By.xpath(".//tr[./td[text()='" + payItems + "']]/td[5]/input")).getAttribute("value").trim();
		 Assert.isEqual(basicPay,CheckoutbasicPay);	
	}
	
	/**
	 * 点击新增保存薪资账套
	 * @param payCondition         薪资帐套条件
	 * @param ChieseDescription    中文描述
	 * @param EnglishDescriptions  英文描述
	 * @param operationalRole      可操作角色
	 * @throws Exception 
	 */
	public void addPayrollAccount(String payCondition, String ChieseDescription, String EnglishDescriptions, String operationalRole) throws Exception {
		waitFor(By.xpath("//a[text()='新增']")).click();
		if(payCondition != null){
			 putInValue(waitFor(By.xpath("//div[@id='dvNew']//div[./label[text()='薪资帐套']]/div[1]/input")), payCondition);
		}
		if(ChieseDescription != null){
			 putInValue(waitFor(By.xpath("//div[@id='dvNew']//div[./label[text()='中文描述']]/div[2]/input")), ChieseDescription);
		}
		if(EnglishDescriptions != null){
			 putInValue(waitFor(By.xpath("//div[@id='dvNew']//div[./label[text()='英文描述']]/div[1]/input")), EnglishDescriptions);
		}
		if(operationalRole != null) {
			 waitFor(By.xpath(".//div[@id='ddlRolesAdd_chosen']/ul")).click();
			 waitFor(By.xpath("//li[text()='Administrator']")).click();
		}
		waitFor(By.xpath("//div[@id='dvNew']/form/div/div[5]/div/button[1]")).click();
		waitLoading();
	}
	
	/**
	 * 验证薪资帐套存在
	 * @param payAccount
	 */
	public void verifyPayTerms(String payAccount) {
		String verifypayAccount = waitFor(By.xpath(".//tr[./td[text()='"+ payAccount +"']]/td[2]")).getText();	
		Assert.isEqual(payAccount, verifypayAccount);
	}
	
	/**
	 * 删除薪资账套
	 * @param condition  薪资条件【薪资帐套】
	 * @throws Exception 
	 */
	public void deletePayTerms(String condition) throws Exception {
		waitFor(By.xpath(".//tr[./td[text()='"+ condition +"']]/td[2]")).click();
		find(By.xpath("//div[@id='ehr-xsbd']/div[1]/div/div/ul/li[5]/i/a")).click();
		Alert alert = driver.switchTo().alert();
	    alert.accept();
	    waitLoading();
	}
	
	/**
	 * 新增薪资代码
	 * @param payRollCode      薪资帐套编码
	 * @param payDescriptions  薪资描述
	 * @param payRollFormula   
	 */
	public void addPayRollFormula(String payRollCode, String payDescriptions, String payRollFormula){
		waitFor(By.xpath("//a[text()='新增']//i")).click();
		if(payRollCode != null){
			waitFor(By.xpath(".//tr[./td[normalize-space(text()='工资代码')]]/td[2]/input[contains(@data-bind,'PayrollCode')]")).sendKeys(payRollCode);
		}
		if(payDescriptions !=null){
			waitFor(By.xpath(".//tr[./td[normalize-space(text()='描述')]]/td[2]/input[contains(@data-bind,'Chinese')]")).sendKeys(payDescriptions);
		}
		if(payRollFormula !=null){
			waitFor(By.xpath("//textarea[contains(@class,'DivTextarea')][contains(@data-bind,'Formula')]")).sendKeys(payRollFormula);
		}
	      waitFor(By.xpath(".//button[contains(@onclick,'Save')][normalize-space(text()='保存')]")).click();
	}
	
	/**
	 * 校验薪资帐套保存提示是否一致
	 * @param payRollCode   薪资代码
	 */
	public void verifyPayRollCode(String payRollCode){
		String verifyPayCode = waitFor(By.xpath("//p[@id='showP']/strong")).getText();
		Assert.isEqual(verifyPayCode, payRollCode);
	}
	
	/**
	 * 修改薪资报表标题设定
	 * @param payRollHeader  薪资帐套标题
	 * @param column         列
	 * @param row            行
	 */
	public void editPayRollReportHeader(String payRollHeader, String column, String row) {
		waitFor(By.xpath(".//tr[./td[(text()='"+payRollHeader+"')]]/td[1]/span")).click();
		if(column != null) {
			putInValue(waitFor(By.xpath("//input[contains(@data-bind,'ColumnIndex')]")),column);
		}
		if(row != null) {
			putInValue(waitFor(By.xpath("//input[contains(@data-bind,'RowIndex')]")),row);
		}
	    waitFor(By.xpath(".//button[contains(@onclick,'Save')][normalize-space(text()='保存')]")).click();
	    waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
	}
	
	/**
	 * 验证薪资修改后数据标题设定显示
	 * @param payRollHeader  薪资帐套标题
	 * @param column         列
	 * @param row            行
	 */
	public void verifyPayRollReportHeader(String payRollHeader, String column, String row) {
			String actualColumn = waitFor(By.xpath("//tr[./td[(text()='" + payRollHeader + "')]]/td[9]")).getText().trim();
			Assert.isEqual(column, actualColumn);
			String actualRow = waitFor(By.xpath("//tr[./td[(text()='" + payRollHeader + "')]]/td[10]")).getText().trim();
	    	Assert.isEqual(row, actualRow);
	}
	
	/**
	 * 新增汇率保存
	 * @param rate        汇率比例
	 * @param ChineseDes  描述
	 */
	public void addExchangeRate(String rate, String ChineseDes) {
		waitFor(By.xpath(".//a[text()='新增']")).click();
		if(rate != null) {
			putInValue(waitFor(By.xpath("//input[@id='txtRate']")),rate);
		}
		if(ChineseDes !=null) {
			putInValue(waitFor(By.xpath("//input[@id='txtEditChinese']")),ChineseDes);
		}
		waitFor(By.xpath(".//button[contains(@onclick,'Save')][normalize-space(text()='保存')]")).click();
	}
	
	/**
	 * 验证是否有提示
	 * @param hint  提示信息
	 */
	public void verifyRateHint(String hint) {
		String actualHint = waitFor(By.id("showP")).getText();
		Assert.isEqual(hint, actualHint);
		find(By.xpath(".//button[text()='确定']")).click();
	}
	
	/**
	 * 选择异动项目
	 * @param moveType    异动类型
	 * @throws Exception 
	 */
	public void chooseProject(String moveType) throws Exception {
		waitFor(By.xpath("//div[@id='dvChangeTypeShowWindow']/span")).click();
		waitFor(By.xpath(".//tr[./td[text()='" + moveType + "']]/td[2]")).click();
		waitLoading();
		String xpath = "//input[contains(@type,'checkbox')][contains(@onchange,'hr_Staff_PolicyBenefitPolicy')]";
		WebElement checkBox = driver.findElement(By.xpath(xpath));
		waitFor(By.xpath(xpath), 5000);
		checkBox.click();
	}
	
	/**
	 * 输入薪资异动项目值
	 * @param staffno        雇员工号
	 * @param welfarePolicy  福利政策类型
	 * @param basePay        基本工资
	 * @param effectiveDate  异动生效日期
	 */
	public void inputMoveProject(String staffno, String welfarePolicy, String basePay, String effectiveDate) {
		if(welfarePolicy !=null) {
			waitFor(By.xpath("//div[@id='dvMasterBenefitPolicyShowWindow']/span")).click();
			waitFor(By.xpath("//div[contains(@style,'block')]//input[contains(@id,'search')][normalize-space(text()='查询')]")).sendKeys(welfarePolicy);
			waitFor(By.xpath("//tr[./td[text()='" + welfarePolicy + "']]/td[2]")).click();
		}
		if(basePay !=null) {
			putInValue(waitFor(By.xpath("//div[.//label[text()='异动后税前收入(办公室):']]/following-sibling::*[1]/input")),basePay);
		}
		if(effectiveDate !=null) {
			putInValue(waitFor(By.xpath("//input[contains(@onchange,'Payroll[1001]')][contains(@data-bind,'EffectDate')]")),effectiveDate);
		}
	    waitFor(By.xpath("//button[normalize-space(text()='直接提交')][contains(@onclick,'SaveChangeHistory')]")).click();
	}
	/**
	 * 验证薪资福利数据
	 * @param tab            异动类型
	 * @param welfarePolicy  福利政策
	 * @param basePay        基本工资
	 */
	public void verifyPayRollData(String welfarePolicy, String basePay) {
		String actualBasePay = waitFor(By.xpath(".//div[@id='rowbtnindex'][.//*[contains(text(),'" + basePay + "')]]/div[2]/label")).getText().trim();
		Assert.isEqual(basePay, actualBasePay);
	}
	
	/**
	 * 点击计算雇员薪资数据
	 * @param staffNo
	 * @throws Exception 
	 */
	public void payRollComputation(String staffNo) throws Exception {
		 waitLoading();
		 waitFor(By.xpath(".//button[@id='btnCalculate']")).click();
		try {
			for (int i = 0; i < 6 ; i++) {
			  Utils.waitABit(5000);
			}
		} catch (Exception e) {
		   ;
		}
	}
	
	/**
	 * 点击薪资跟踪查询
	 */
	public void clickPayTracking() {
		waitFor(By.xpath(".//a[text()='跟踪']/i")).click();		
	}
	
	/**
	 * 高级筛选人事主档信息后，点击确定。
	 */
	public void makeSurePersonnel() {
	  waitFor(By.xpath("//button[text()='确定']")).click();
	}
	
	/**
	 * 查看人事主当页签【薪酬】
	 * @param personnelTab
	 */
	public void selectTab(String personnelTab) {
		waitFor(By.xpath("//a[text()='" + personnelTab + "']")).click();
	 }
	
	/**
	 * 编辑保存人事主档，薪资数据信息
	 * @param textType
	 * @param payInfoData
	 */
	public void editPayRollInfoData(String textType, HashMap<String, String> payInfoData) {
		for (Entry<String, String> entry : payInfoData.entrySet()) {
			String columnName = entry.getKey();
			String value = entry.getValue();
			String xpath = ".//div[@id='rowbtnindex'][.//*[contains(text(),'" + columnName + "')]]/div[2]/input";
			if (textType.equals("文本框")) {
		        waitFor(By.xpath(xpath)).clear();
				waitFor(By.xpath(xpath)).sendKeys(value);
			} else if (textType.equals("下拉框")) {
			  waitFor(By.xpath(".//div[@id='rowbtnindex'][.//*[contains(text(),'" + columnName + "')]]/div[2]/div/div/span")).click();
			  waitFor(By.xpath(".//div[contains(@style,'block')]//input[contains(@id,'search')][normalize-space(text()='查询')]")).sendKeys(value);
			  waitFor(By.xpath(".//tr[./td[text()='" + value + "']]/td[2]")).click();
			} else {
				//否则就是单选框[计算类型]
				Boolean Bl = new Boolean(value);
				boolean bl = Bl.booleanValue();
				checkChkRadio(waitFor(By.xpath(xpath)), bl);	
			}
			waitFor(By.xpath("//a[@id='btnSave']/i")).click();
			waitFor(By.xpath("//button[text()='确定']")).click();
		}
	}	
	
	/**
	 * 选择过往薪资报表的实际爱你范围
	 * @param startTime  开始时间
	 * @param endTime    结束时间
	 */
	public void choosePayRollRange(String startTime, String endTime) {
		if (startTime != null) {
			putInValue(waitFor(By.xpath("//input[@id='dvStartDate']")), startTime);
		}
		if (endTime != null) {
			putInValue(waitFor(By.xpath("//input[@id='dvEndDate']")), endTime);
		}
		waitFor(By.xpath(".//input[@id='btnSearch']")).click();
	}
	
	/**
	 * 验证历史工资单帐套信息
	 * @param totalPayAmount
	 * @param performanceBonus
	 * @throws Exception 
	 */
	public void verifyHistoricalPayslips(String reportName) throws Exception {	
		waitLoading();
		driver.switchTo().frame("P54HistoryViewer1_Splitter_Viewer_ContentFrame");
		String actualReportName = find(By.xpath("//nobr[contains(text(),'" + reportName + "')]")).getText().trim();
		Assert.isContains(actualReportName, reportName, "验证过往支薪报表");
		driver.switchTo().defaultContent();
	}
	
	/**
	 * 薪酬管理，高级筛选薪资帐套
	 * @param conditions
	 * @throws Exception
	 */
	public void advanceSearchPayRollConditions(String conditions) throws Exception {
		WebDriverUtils.advanceSearchPayRollConditions(conditions, driver);
	}
	
	/**
	 * Common  验证薪资计算处理结果
	 * @param staffno
	 * @param payRollResult
	 */
	public void verifyPayRollInfo(int payRollCode, HashMap<String, String> payRollResult) {
		for (Entry<String, String> entry : payRollResult.entrySet()) {
			String columnName = entry.getKey();
			String value = entry.getValue();
			String xpathLess = "//tr[./td[@title='"+ columnName +"']]/td[3]";
			String xpathGreater = "//tr[./td[text()='"+ columnName + "']]/td[4]";
			if (payRollCode < 5000) {
				String actualResult =  waitFor(By.xpath(xpathLess)).getText().trim();
				Assert.isEqual(value, actualResult);
			} else {
				String actualComputation =  waitFor(By.xpath(xpathGreater)).getText().trim();
				Assert.isEqual(value, actualComputation);
			}
		}
	
	}
	
	/**
	 * 查询薪资帐套的状态。
	 * @param payRollName
	 * @param status
	 */
	public void searchPayrollAccountName(String payRollName){
		String actualStatus = waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).click();
		if (actualStatus.equals("未发起")) {
			waitFor(By.xpath("//div[@id='dv1']")).click();//点击发起
			waitFor(By.xpath(".//button[text()='确定']")).click();
			waitFor(By.xpath("//button[contains(@onclick,'Back')]")).click();
		} else if(actualStatus.equals("冻结")) {
			waitFor(By.xpath("//div[text()='取消冻结']")).click();
			waitFor(By.xpath(".//button[text()='确定']")).click();
			waitFor(By.xpath("//button[contains(@onclick,'Back')]")).click();
		}  else if(actualStatus.equals("已确认")) {
			waitFor(By.xpath("//div[text()='取消确认']")).click();
			waitFor(By.xpath(".//button[text()='确定']")).click();	
			waitFor(By.xpath("//div[text()='取消冻结']")).click();
			waitFor(By.xpath(".//button[text()='确定']")).click();
			waitFor(By.xpath("//button[contains(@onclick,'Back')]")).click();
		}else {
			String workFlowstatus = "取消发起";
			String actualWorkFlowStatus = waitFor(By.xpath("//div[@id='dv1']")).getText().trim();
			Assert.isEqual(workFlowstatus, actualWorkFlowStatus);
			waitFor(By.xpath("//div[@id='dv1']")).click();//点击取消发起
			waitFor(By.xpath(".//button[text()='确定']")).click();
			waitFor(By.xpath("//div[@id='dv1']")).click();//点击取消发起
			waitFor(By.xpath(".//button[text()='确定']")).click();
			waitFor(By.xpath("//button[contains(@onclick,'Back')]")).click();
		}	
	}
	
	/**
	 * 验证薪资帐套帐套
	 * @param status
	 */
	public void verifyPayrollAccount(String payRollName, String status) {
		String actualStatus = waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		Assert.isEqual(status, actualStatus);
	}
	
	/**
	 *  编辑信息数据输入Common， 并能保存成功。
	 * @param staffno
	 * @param payRollData
	 */
	public void editPayRollDataCommon(String staffno, HashMap<String, String> payRollData) {
		for(Entry<String, String>entry :payRollData.entrySet()) {
			String columnName = entry.getKey();
			String value = entry.getValue();
			String xpath = ".//tr[./td[text()='"+ columnName +"']]/td[5]/input";
			waitFor(By.xpath(xpath)).clear();
			waitFor(By.xpath(xpath)).sendKeys(value);
		}
		 waitFor(By.xpath(".//a[contains(@onclick,'SaveData')]/span")).click();
		 waitFor(By.xpath(".//button[text()='确定']")).click();
	}
	
	/**
	 * 验证本期工资单雇员数据只有一条信息
	 * @param data
	 * @throws Exception 
	 */
	public void verifyPayRollDataOnly(String staffNo) throws Exception {
		Utils.waitABit(10000);
		waitLoading();
	    String frameElement="SalaryViewer1_Splitter_Viewer_ContentFrame";
		driver.switchTo().frame(frameElement);
		waitLoading();
		String actualStaffNo = waitFor(By.xpath(".//*[text()='" + staffNo + "']")).getText().trim();
		Assert.isEqual(staffNo, actualStaffNo);
		driver.switchTo().defaultContent();
	}
	
	/**
	 * 验证薪资账套管理，字段权限。
	 * @param hint
	 */
	public void verifyPaySystemPrivileges(String hint) {
		waitFor(By.xpath("//div[@id='dv2']")).click();//点击发起
		String actualHint = waitFor(By.id("showP")).getText().trim();
		Assert.isEqual(hint, actualHint);
	}
	
	/**
	 * 修改薪资帐套的审核状态
	 * @param payConditions
	 * @param auditType
	 * @throws Exception 
	 */
	public void editPayTerms(String payConditions, String auditType) throws Exception {
		waitFor(By.xpath("//tr[./td[text()='" + payConditions + "']]/td[2]")).click();
		waitFor(By.xpath("//li[contains(@onclick,'dataEdit')]/i")).click();
		selectByVisibleText(By.xpath("//select[contains(@data-bind,'AuditType')]"), auditType);
		waitFor(By.xpath(".//tr[@id='dvEdit']/td/form/div[2]/div[2]/div[2]/button[1]")).click();
		waitLoading();
		waitFor(By.xpath("//div[@id='Navbreadcrumb']/div/ul/li[2]/a")).click();
		waitLoading();
	}
	
	/**
	 * 薪资帐套流程发起
	 * @param payRollName
	 */
	public void clickPayrollAccountNameWorkFlow(String payRollName) {
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).click();
		waitFor(By.xpath(".//div[@id='dv2']")).click();
		waitFor(By.xpath(".//button[text()='确定']")).click();
		waitFor(By.xpath(".//div[@id='dv4']")).click();
	}
	
	/**
	 * 在薪资帐套设定菜单，查找帐套的时间范围
	 * @param payAccountName  帐套名称
	 * @param startDate       开始日期
	 * @param endDate         结束日期
	 * @throws Exception 
	 */
	public void selectPayAccountDetailSetting(String payAccountName, String startDate, String endDate) throws Exception {
		if(startDate != null){
			putInValue(By.xpath("//input[@id='txtStartDate']"), startDate);
		}
		if(endDate != null){
			putInValue(By.xpath(".//input[@id='txtEndDate']"), endDate);
		}
		selectByVisibleText(By.xpath("//select[@id='sesearchTerm']"), payAccountName);
		waitLoading();
	}
	
	/**
	 * 新增周期明细时间
	 * @param year        薪资条件年
	 * @param month       薪资条件月
	 * @param startDate   开始日期
	 * @param endDate     结束日期
	 */
	public void addPayCycleDetails(String year, String month, String startDate, String endDate) {
		waitFor(By.xpath("//li[contains(@onclick,'dataNew')]/i")).click();
		selectByVisibleText(By.xpath("//select[contains(@data-bind,'AllYear')]"), year);
		selectByVisibleText(By.xpath("//select[contains(@data-bind,'AllMonth')]"), month);
		if(startDate != null){
			putInValue(By.xpath("//input[contains(@data-bind,'PeriodRangeStartDate')]"), startDate);
		}
		if(endDate != null){
			putInValue(By.xpath("//input[contains(@data-bind,'PeriodRangeEndDate')]"), endDate);
		}
		waitFor(By.xpath("//input[contains(@data-bind,'PeriodRangeStartDate')]")).click();
		waitFor(By.xpath("//button[text()='保存']")).click();
	}
	
	/**
	 * 生成薪资周期明细
	 * @param startDate   开始日期
	 * @param endDate     结束日期
	 */
	public void createPayCycleDetails(String startDate, String endDate) {
		waitFor(By.xpath("//li[contains(@onclick,'dataCreate')]/i")).click();
		if(startDate != null){
			putInValue(By.xpath("//input[contains(@data-bind,'CreateStartDate')]"), startDate);
		}
		if(endDate != null){
			putInValue(By.xpath("//input[contains(@data-bind,'CreateEndDate')]"), endDate);
		}
		waitFor(By.xpath("//input[contains(@data-bind,'CreateStartDate')]")).click();
		waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
	}
	
	/**
	 * 新增福利类型
	 * @param welfareCode         福利类型代码
	 * @param welfareName	                    福利类型名称
	 * @param welfareDescription  福利类型描述
	 */
	public void addWelfareTypeMaintenance(String welfareCode, String welfareName, String welfareDescription) {
	    waitFor(By.xpath("//a[contains(@href,'AddNew')]/span")).click();
	    waitFor(By.xpath("//input[@id='txtCode']")).sendKeys(welfareCode);
	    waitFor(By.xpath("//input[@id='txtName']")).sendKeys(welfareName);
	    waitFor(By.xpath(".//input[@id='txtDesc']")).sendKeys(welfareDescription);
	    waitFor(By.xpath(".//button[contains(@onclick,'DetailSave')]")).click();
	    waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
	}
	
	/**
	 * 验证福利政策信息
	 * @param welfareCode 福利政策代码
	 */
	public void verifyWelfareTypeMaintenance(String welfareCode) {
		String actualWelfareCode = waitFor(By.xpath("//tr[./td[text()='" + welfareCode + "']]/td[1]")).getText().trim();
		Assert.isEqual(welfareCode, actualWelfareCode);
	}
	
	/**
	 * 删除福利类型
	 * @param welfareCode 福利政策代码
	 */
	public void deleteWelfareTypeMaintenance(String welfareCode) {
		waitFor(By.xpath("//tr[./td[text()='" + welfareCode + "']]/td[1]")).click();
		waitFor(By.xpath(".//a[contains(@href,'Delete')]/span")).click();
		waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
	}
	
	/**
	 * 选择薪资帐套名称
	 * @param accountName 帐套名称
	 */
	public void selectPayRollCycleAdjustment(String accountName) {
		selectByVisibleText(By.xpath("//select[@id='TermSelect']"), accountName);
	}
	
	/**
	 * 删除薪资周期雇员
	 * @param staffNo
	 * @throws Exception 
	 */
	public void deletePayRollCycle(String staffNo) throws Exception{
		waitFor(By.xpath("//i[contains(@data-bind,'Delete')]")).click();
		waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
		waitLoading();
		waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
		waitLoading();
	}
	
	/**
	 * 新增薪资周期雇员
	 * @param staffNo
	 * @throws Exception 
	 */
	public void addPayRollCycle(String staffNo) throws Exception {
		waitFor(By.xpath("//li[contains(@onclick,'Add')]/i")).click();
		waitLoading();
		waitFor(By.xpath(".//tr[./td[text()='" + staffNo +"']]/td[1]/input")).click();
		waitFor(By.xpath("//li[contains(@onclick,'savestaff')]/i")).click();
	    waitFor(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']")).click();
	    waitLoading();
	    waitFor(By.xpath("//span[@id='ui-id-1']//following-sibling::*")).click();
	}
	
	/**
	 * 验证薪资计算是否成功
	 * @param result 薪资计算结果
	 */
	public void verifySalaryCalculation(String result) {
		String actualResult = waitFor(By.xpath("//div[@id='calculateErrorStaff']/h5")).getText().trim();
		Assert.isEqual(result, actualResult);
	}
	
	/**
	 * 流程审批界面，审批流程表单至完全同意
	 * @param workFlowName  流程名称
	 * @param workFlowID    流程ID
	 */
	public void salaryProcessApprovalWorkFlow(String workFlowName, String workFlowId) {
		waitFor(By.xpath(".//tr[./td[text()='"+ workFlowId +"']]/td[3]/a[text()='"+ workFlowName +"']")).click();
		String winHandleCurrent = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(winHandleCurrent)) {
				driver.switchTo().window(winHandle);
				break;
			}
		}
		waitFor(By.xpath("//button[normalize-space(text()='同意')][contains(@onclick,'Commit')]")).click();
		waitFor(By.xpath("//button[text()='确定']")).click();
		switchToOpenerWindow();
		driver.close();
	}
	
	/**
	 * 切换流程审批状态
	 * @param status  流程状态
	 */
	public void switchApprovalStatus(String status) {
		selectByVisibleText(By.xpath("//select[@id='ApprovalStatus']"), status);
	}
	
	/**
	 * 验证薪资流程审批状态。
	 * @param workFlowId
	 * @param status
	 */
	public void verifySalaryProcessApproval(String workFlowId, String status) {
		String actualStatus = waitFor(By.xpath(".//tr[./td[text()='"+ workFlowId +"']]/td[3]/a")).getText().trim();
		Assert.isEqual(status, actualStatus);
	}
	
	/**
	 * 验证薪资帐套不可见
	 * @param payRollName 帐套名称
	 */
	public void verifyNotVisiblePayTerms(String payRollName){
		Assert.notVisible(By.xpath(".//tr[./td[text()='"+ payRollName + "']]/td[2]"), "帐套信息不存在", driver);
	}
	
	/**
	 * 将薪资帐套冻结
	 * @param payRollName 薪资帐套
	 */
	public void clickFreezeAccountPayName(String payRollName){
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).click();
		waitFor(By.xpath(".//div[@id='dv2']")).click();
		waitFor(By.xpath(".//button[text()='确定']")).click();
		waitFor(By.xpath("//a[contains(@href,'ParameterID=W02010100')]")).click();
	}
	
	/**
	 * 验证帐套未发起，薪资数据无法编辑保存。
	 * @param info 
	 */
	public void verifyAccountPayNameSystemMenuInfo(String info){
		String actualInfo = waitFor(By.xpath("//li[contains(@data-bind,'!CanModify')]/font/span")).getText().trim();
		Assert.isEqual(info, actualInfo);
		Assert.isContains(actualInfo, info, "该雇员所属账套未在'发起中'状态,不能编辑");
	}
	
	/**
	 * 将新娘子帐套确认
	 * @param PayRollName 薪资帐套
	 */
	public void clickAffirmAccountPayName(String payRollName){
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		waitFor(By.xpath("//tr[./td[text()='" + payRollName + "']]/td/button[contains(@data-bind,'Status')]")).click();
		waitFor(By.xpath(".//div[@id='dv2']")).click();
		waitFor(By.xpath(".//button[text()='确定']")).click();
		waitFor(By.xpath(".//*[@id='dv3']")).click();
		waitFor(By.xpath(".//button[text()='确定']")).click();
		waitFor(By.xpath("//a[contains(@href,'ParameterID=W02010100')]")).click();
	}
	
	/**
	 * 系统菜单
	 * @param menu
	 * @throws Exception 
	 */
	public void clickSystmeMenu(String menu, String sysMenu) throws Exception{
		waitFor(By.xpath(".//a[text()='"+ menu +"'][@id='ess']")).click();
		waitLoading();
		waitFor(By.xpath("//div[@class='quick-link']/a[text()='"+ sysMenu +"']")).click();
		String winHandleCurrent = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(winHandleCurrent)) {
				driver.switchTo().window(winHandle);
				break;
			}
		}
	}
	
	/**
	 *  验证员工自助工资单
	 * @param staffNo   员工自助
	 * @param salary    实发工资
	 * @param unionFees 工会费
	 * @throws Exception 
	 */
	public void verifyCurrentPaySheet(String staffNo, String salary, String unionFees) throws Exception {
		waitLoading();
		String frameElement="PaySliphistoryViewer2_Splitter_Viewer_ContentFrame";
        driver.switchTo().frame(frameElement);
    	waitLoading();
        String actualSalary = waitFor(By.xpath("//nobr[text()='"+ salary +"']")).getText().trim();
        Assert.isEqual(salary, actualSalary);
        String actualUnionFees = waitFor(By.xpath("//nobr[text()='"+ unionFees +"']")).getText().trim();
        Assert.isEqual(unionFees, actualUnionFees);
        driver.switchTo().defaultContent();
	}
	
	
	/**
	 * 薪资账套设定菜单的xpath
	 * @param condition
	 * @return
	 */
	public String getTrXpathByTotalItemCode(String condition) {
		return ".//tr[./td[text()='"+ condition +"']]/td[2]" ;
	}
	
	/**
	 * 判断薪资账套是否存在， 
	 * @param condition  薪资账套名称
	 * @return           True 存在/ False 不存在
	 */
    public boolean isPayAccountThere(String condition) {
    	try {
    	  waitForVisible(By.xpath(getTrXpathByTotalItemCode(condition)));
		  return true;
		} catch (Exception e) {
		  return false;
		}
    }
    
    /**
     * 薪资数据导入
     * @param fileName  导入文件名
     * @throws Exception 
     */
    public void importSalaryModuleExcel(String fileName) throws Exception{
    	waitFor(By.xpath(".//input[@id='btnSelectFile']")).click();
		WebDriverUtils.importParyRollFile(fileName, driver);
        waitLoading();
        waitFor(By.xpath("//input[@id='btnNext']")).click();
        waitFor(By.xpath("//input[@id='btnNext']")).click();
        waitLoading();
		String actualMsg = waitFor(By.xpath("//div[@id='messageDialog']/p")).getText().trim();
		Assert.isContains(actualMsg, "总行数:1,有效数据列3,导入成功行数1", "导入成功行数1");
		find(By.xpath("//button[text()='确定']")).click();
    }
    
    /**
     * 薪资导入校验提示
     * @param fileName
     * @throws Exception
     */
    public void importSalaryModuleExcelVerification(String fileName) throws Exception{
    	waitFor(By.xpath(".//input[@id='btnSelectFile']")).click();
		WebDriverUtils.importParyRollFile(fileName, driver);
        waitLoading();
        waitFor(By.xpath("//input[@id='btnNext']")).click();
        waitFor(By.xpath("//input[@id='btnNext']")).click();
        waitLoading();    	
    }
    
    /**
     * 选择薪资导入的页签
     * @param tabName
     */
    public void selectImportTab(String tabName) {
    	waitFor(By.xpath("//span[text()='"+ tabName +"']")).click();
    	waitFor(By.xpath("//input[@id='btnNext']")).click();
    	waitFor(By.xpath("//input[@id='cb_firstRowIsTitle']")).click();
    }
    
    /**
     * 验证薪资数据输入数据
     * @param staffNo  
     * @param payRollData  
     */
    public void verifyPayRollDataInfo(HashMap<String, String> payRollData) {
		for(Entry<String, String>entry :payRollData.entrySet()) {
			String columnName = entry.getKey();
			String value = entry.getValue();
			String xpath = "//tr[./td[text()='"+ columnName +"']]/td[5]/input";
			String actualValue = waitFor(By.xpath(xpath)).getAttribute("value").trim();
			Assert.isEqual(value, actualValue);
		}
		 
	}
    
    /**
     * 验证薪资数据导入校验
     * @param hint  校验信息
     */
    public void verifyImportSalaryHint(String hint) {
    	String actualMsg = waitFor(By.xpath("//div[@id='messageDialog']/p")).getText().trim();
		Assert.isContains(actualMsg, "导入成功行数0", "导入成功行数0");
    }
    
    /**
     * 薪资数据输入保存
     */
    public void saveSalaryData() {
       waitFor(By.xpath(".//a[contains(@onclick,'SaveData')]/span")).click();
	   waitFor(By.xpath(".//button[text()='确定']")).click();
    }
    
    /**
    * 查看工资单，点击生成工资单邮件按钮
    * @throws Exception 
    */
    public void creatEmailButton() throws Exception {
    	waitFor(By.xpath("//input[@id='btnSearch']")).click();
    	waitLoading();
    	waitFor(By.xpath("//a[contains(@onclick,'SendEmail')]/i")).click();
    }
    
    /**
     * 验证生成工资单邮件结果
     * @param emailResult   生成工资低钠邮件信息
     */
    public void verifyCreatePayrollMailContainsEmail(String emailResult) {
    	String actualEmailResult = waitFor(By.xpath("//label[@id='lbLog']")).getText().trim();
    	//Assert.isEqual(emailResult, actualEmailResult);
    	Assert.isContains(actualEmailResult, emailResult, "Email工资单生成完成");
    }
    
    /**
     * 将薪资帐套的流程发起状态，改成取消流程发起
     * @param condition
     */
    public void cancelWorkflowStartPayrollAccountName(String condition) {
    	waitFor(By.xpath("//tr[./td[text()='" + condition + "']]/td/button[contains(@data-bind,'Status')]")).getText().trim();
		waitFor(By.xpath("//tr[./td[text()='" + condition + "']]/td/button[contains(@data-bind,'Status')]")).click();
		waitFor(By.xpath("//div[@id='dv4']")).click();
		waitFor(By.xpath(".//button[text()='确定']")).click();
    }
    
 }
