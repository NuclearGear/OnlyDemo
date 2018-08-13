package com.winmax.pages.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.winmax.config.Const;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Excel;
import com.winmax.utils.FileUtil;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class performanceManagePage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(performanceManagePage.class);
	
	public performanceManagePage() {
		PageFactory.initElements(driver, this);
	}
	
	//############绩效设置，维护考核方式######
	/**
	 * 点击添加新的考核方式Button
	 * @throws Exception
	 */
	public void clickAddNewAssessmentButton() throws Exception {
		waitFor(By.xpath(".//div[@id='StyleListDiv']//button")).click();
	}
    
	/**
	 * 
	 * @param assessmentDate
	 * @throws Exception
	 */
	public void addNewAssessmentMethods(HashMap<String, String> assessmentDate) throws Exception {
		waitLoading();
		for(Entry<String, String> entry : assessmentDate.entrySet()){
			String filed = entry.getKey();
			String value = entry.getValue();
			if(filed.equals("考核类型")){
				selectByVisibleText(By.xpath("//select[contains(@data-bind,'SelectData')]"), value);
			}
			else if (filed.equals("是否可用")){
				checkChkRadio(By.xpath("//input[contains(@data-bind,'checked')]"), true);
			}
			else{
				String input = null;
				if(Const.DEFAULT_ENVIRONMENT.equals("http://192.168.1.112:370")){
					 input = "//td[contains(text(), '" + filed + "')]//following-sibling::td[1]/input";
				} 
				else{
					 input = "//div[@id='StyleEditor']//td[./span[contains(text(),'" + filed + "')]]"
							+ "//following-sibling::td[1]/input";
				}
				putInValue(waitFor(By.xpath(input)), value);
				Utils.waitABit(2000);
			}
		}
	}
	
	/**
	 * 
	 * @param code
	 * @throws Exception
	 */
	public void clickEdit(String code) throws Exception {
		String editXpath = ".//td[contains(@data-bind,'Code')]"
				+ "[normalize-space(text())='" + code + "']//preceding-sibling::td[*]/a[contains(@data-bind,'Edit')]";
		waitFor(By.xpath(editXpath)).click();
		Utils.waitABit(2000);
	}
	
	/**
	 * 
	 * @param updateAssessmentValue
	 * @throws Exception
	 */
	public void updateAssessmentMethods(HashMap<String, String> updateAssessmentValue) throws Exception {
		addNewAssessmentMethods(updateAssessmentValue);
		save();
	}
	
	public void deleteAssessmentMrthods(String assessmentCode) throws Exception {
		String deleteButton = "//td[normalize-space(text())='" + assessmentCode + "']"
				+ "//preceding-sibling::td[*]/a[contains(@data-bind,'Delete')]";
		waitFor(By.xpath(deleteButton)).click();
		confirmDelete();
		waitLoading();
	}
	
    //############维护绩效考核周期##########
	/**
	 * 点击新增按钮
	 * @throws Exception
	 */
	public void clickAdd() throws Exception {
		waitFor(By.xpath("//a[@data-bind='click: Add']/i")).click();
	}
	
	/**
	 * 填写绩效数据
	 * @param performanceDate
	 * @throws Exception
	 */
	public void fillPerformancePeriod(HashMap<String, String> performanceDate) throws Exception {	
		for(Entry<String, String> entry : performanceDate.entrySet()){
			String field = entry.getKey();
			String value = entry.getValue();
			if(field.equals("周期类型")) {
				String radioButton = "//tr[@id='PeriodDetail']//label[contains(.,'" + value + "')]/input";
				checkChkRadio(By.xpath(radioButton),true);
			}
			else if(field.equals("所属年度")) {
				selectByVisibleText(By.id("selYear"), value);
			}
			else{
				String input = "//tr[@id='PeriodDetail']//table//td"
						+ "[./label[contains(text(),'" + field + "')]]//following-sibling::td[1]/input";
				putInValue(waitFor(By.xpath(input)), value);
			}
		}
	}
	
	/**
	 * 保存
	 * @throws Exception
	 */
    public void save() throws Exception{
    	waitFor(By.xpath("//button[contains(@data-bind,'Save')][normalize-space(text())='保存']")).click();
		WebDriverUtils.confirmSave(driver);
		waitLoading();
    }
    
	/**
	 * 往输入框赋值
	 * @param KeyWords 需要搜索的周期
	 * @throws Exception
	 */
	public void search(String KeyWords) throws Exception{
		Utils.waitABit(2000);
		String keyWord = "//input[contains(@data-bind,'KeyWords')]";
		putInValue(waitFor(By.xpath(keyWord)), KeyWords);
	}
	
	/**
	 * 单独点击查询按钮
	 * @throws Exception
	 */
	public void searchButton() throws Exception {
		String search= "//a[contains(@data-bind,'Search')]/i";
		WebElement searchTo = waitFor(By.xpath(search));
		searchTo.click();
	}
	
	/**
	 * 验证查找出的周期是否与新增的是同一条
	 * @param performanceName 周期名称
	 * @throws Exception
	 */
	public void verifyPerformancePeriodExist(String performanceName) throws Exception{
		Assert.present(By.xpath("//td[normalize-space(text())='" + performanceName + "']"),
				"find record " + performanceName, driver);  
	}
  
	/**
	 * 找到需要修改的绩效周期，点击编辑按钮
	 * @param trName 此周期的所属行
	 * @throws Exception
	 */
	public void findUpdate(String trName) throws Exception{
		waitLoading();
		String edit = "//td[normalize-space(text())='" + trName + "']"
				+ "//preceding-sibling::td[*]/i[contains(@data-bind,'Edit')]";
		waitFor(By.xpath(edit)).click();
	}
	
	/**
	 * 修改所新增的考核周期
	 * @param performanceDate
	 * @throws Exception
	 */
	public void updatePerformancePeriod(HashMap<String, String> update) throws Exception{
		fillPerformancePeriod(update);
		save();
	}
	
	/**
	 * 校验是否修改成功
	 * @param keysSend 已修改的绩效周期
	 * @throws Exception
	 */
	public void searchAgin(String keysSend) throws Exception{
		String keyWordXpath = "//input[contains(@data-bind,'KeyWords')]";
		putInValue(waitFor(By.xpath(keyWordXpath)), keysSend);
		searchButton();
	}

	/**
	 * 删除绩效周期
	 * @param performanceName 周期名称
	 * @throws Exception
	 */
	public void deletePerformancePeroid(String performanceName) throws Exception{
		String xpath = "//input[contains(@data-bind,'KeyWords')]";
		putInValue(waitFor(By.xpath(xpath)), performanceName);
		
		searchButton();
		waitFor(By.id("CheckAll")).click();
		waitFor(By.xpath("//a[contains(@data-bind,'Delete')]/i")).click();
	}
	 public void confirmDelete() throws Exception {
		 String text = waitFor(By.id("showP")).getText().trim();
//		 Assert.isEqual("确认删除", text);
		 waitFor(By.xpath("//button[text()='确定']")).click();
		 WebDriverUtils.confirmSave(driver);
	 }
	 
    //	############当结束时间小于开始时间时，点击保存是否会报错
    /**
     * 验证保存失败
     * @param tips
     * @throws Exception
     */
	public void failSave(String tips) throws Exception{
		waitFor(By.xpath("//button[contains(@data-bind,'Save')")).click();
		String check = waitFor(By.xpath("//p[@id='showP'][normalize-space(text()=' "+tips+" ')]")).getText().trim();
		waitFor(By.xpath("//button[@class='btn btn-default'][normalize-space(text())='确定']")).click();
		Utils.waitABit(1000);
		String checkText = waitFor(By.xpath("//p[@id='showP'][normalize-space(text()='" + tips + "')]")).getText().trim();
		Assert.isEqual(tips, checkText);
		logger.info(tips);
	}
	
	/**
	 * 
	 * @param cName
	 * @param eName
	 * @param CType
	 * @param SYear
	 * @param sTime
	 * @param eTime
	 * @throws Exception
	 */
	public void updatePeriodTime(HashMap<String, String> hash) throws Exception{
		fillPerformancePeriod(hash);
		save();
		
	}
	//############周期复制，验证源考核周期##########
	/**
	 * 验证源考核周期
	 * @param periodName
	 * @throws Exception
	 */
	public void VerifyCycle(String periodName) throws Exception{
		waitLoading();
		waitFor(By.xpath("//div[@id='PeriodListDiv2']//i[contains(@title,'周期复制')]")).click();
	    String checkPeriod = waitFor(By.xpath("//span[contains(@data-bind,'text:SourceName')][text()='" + periodName + "']")).getText().trim();
	    Assert.isEqual(periodName, checkPeriod);
	    logger.info(checkPeriod);
	}
	
	/**
	 * 
	 * @param performanceName
	 * @param performanceBewrite
	 * @param beginTime
	 * @param endTime
	 * @throws Exception
	 */
	public void fillPerformancePeriod(String performanceName, String performanceBewrite, String beginTime, String endTime) throws Exception{
		String targetName = "//div[@id='PeriodCopyDetail']//input[contains(@data-bind,'value:TargetName')]";
		String targetDescription = "//div[@id='PeriodCopyDetail']//input[contains(@data-bind,'value:TargetDescription')]";
		String targetStartDate = "//input[contains(@data-bind,'datepicker:TargetStartDate')]";
		String targetEndDate = "//input[contains(@data-bind,'datepicker:TargetEndDate')]";
		waitFor(By.xpath(targetName)).sendKeys(performanceName);
		waitFor(By.xpath(targetDescription)).sendKeys(performanceBewrite);
		putInValue(waitFor(By.xpath(targetStartDate)), beginTime);
		putInValue(waitFor(By.xpath(targetEndDate)), endTime);
	}
	
	/**
	 * 
	 * @param periodName
	 * @throws Exception
	 */
	public void copy(String periodName) throws Exception{
	    waitFor(By.xpath("//div[@id='PeriodCopyDetail']//tbody//button[1][normalize-space(text())='复制']")).click();	
	    String VerifyCycleName = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='" + periodName + "']")).getText().trim();
	    Assert.isEqual(periodName, VerifyCycleName);
	    waitFor(By.xpath("//button[@class='btn btn-default'][normalize-space(text())='确定']")).click();
	}
	
	/**
	 * 
	 * @param newPerformanceName
	 * @throws Exception
	 */
	public void updatePerformanceRevolution(String newPerformanceName) throws Exception{
		String targetName = "//div[@id='PeriodCopyDetail']//input[contains(@data-bind,'value:TargetName')]";
		putInValue(waitFor(By.xpath(targetName)), newPerformanceName);
	}
	
    /**
     * 
     * @throws Exception
     */
	public void saveSuccess() throws Exception{
		waitFor(By.xpath("//div[@id='PeriodCopyDetail']//button[normalize-space(text())='复制']")).click();
		WebDriverUtils.confirmSave(driver);
	}

    /**
     * 
     * @param checkPerfomancePeriod
     * @param periodType
     * @throws Exception
     */
	public void checkPerformancePeriod(String checkPerfomancePeriod, String periodType) throws Exception{
		String chineseXpath = "//div[@id='PeriodListDiv2']//tbody"
				+ "//td[contains(@data-bind,'text:Name.Chinese')][normalize-space(text())='" +checkPerfomancePeriod+ "']";
		String check = waitFor(By.xpath(chineseXpath)).getText().trim();
	    Assert.isEqual(checkPerfomancePeriod, check);
	    
	    String type = "//div[@id='PeriodListDiv2']//td[normalize-space(text())='" +checkPerfomancePeriod+ "']"
	    		+ "/following-sibling::td[text()='" +periodType+ "']";
	    String checkPeriodType = waitFor(By.xpath(type)).getText().trim();
	    Assert.isEqual(periodType, checkPeriodType);
	}
	
	/**
	 * 
	 * @param copyPerformanceName
	 * @throws Exception
	 */
	public void deleteFillperformancePeriod(String copyPerformanceName) throws Exception{
    	waitForVisible(By.xpath("//tr[.//td[normalize-space(text())='" + copyPerformanceName + "']]//td//input")).click();
	}
	
	/**
	 * 
	 * @param performanceCopyNameTwo
	 * @throws Exception
	 */
   public void deleteCopyPerformancePeriod(String performanceCopyNameTwo) throws Exception{
	    waitLoading();
	    waitFor(By.xpath("//tr[.//td[normalize-space(text())='" + performanceCopyNameTwo + "']]//td//input")).click();
		waitFor(By.xpath("//a[contains(@data-bind,'click: Delete')]")).click();
		String removeInformation = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='确认删除']")).getText().trim();
		Assert.isEqual("确认删除", removeInformation);
		waitFor(By.xpath("//button[@class='btn btn-default'][normalize-space(text())='确定']")).click();
		WebDriverUtils.confirmSave(driver);
		
		waitForVisible(By.xpath("//input[contains(@data-bind,'KeyWords')]")).clear();
   }
   
   //############维护绩效考核分组，且与绩效考核周期关联##########
   /**
    * 
    * @param automationTest
    * @throws Exception
    */
   public void choosePerformancePeriod(String automationTest) throws Exception {
	   waitFor(By.xpath("//div[@id='dvPeriodShowWindow']/span")).click();
	   waitFor(By.id("dvPeriodsearch")).sendKeys(automationTest);
	   waitFor(By.xpath("//td[contains(@role,'gridcell')][normalize-space(text())='" +automationTest+ "']")).click();
   }
   
   /**
    * 
    * @param groupChinese
    * @param groupEnglish
    * @throws Exception
    */
   public void addExaminationGrouping(String groupChinese, String groupEnglish) throws Exception {
	   Utils.waitABit(2000);
	   waitFor(By.id("GroupAdd")).click();
	   waitFor(By.xpath("//input[contains(@data-bind,'Chinese')]")).sendKeys(groupChinese);
	   waitFor(By.xpath("//input[contains(@data-bind,'English')]")).sendKeys(groupEnglish);
   }
   
   /**
    * 
    * @throws Exception
    */
   public void saveExaminationGrouping() throws Exception {
	   waitFor(By.xpath("//tr[@id='GroupTr']//button[contains(@data-bind,'Save')]")).click();
	   Utils.waitABit(2000);
	   WebDriverUtils.confirmSave(driver);
   }
   
   /**
    * 
    * @param groupChinese
    * @param automationGroupTest
    * @throws Exception
    */
   public void updateGroupChinese(String groupChinese, String automationGroupTest) throws Exception {
	   waitLoading();
	   String edit = "//td[text()='" + groupChinese + "']//preceding-sibling::td[5]/i[1]";
	   waitFor(By.xpath(edit)).click();
	   waitFor(By.xpath("//input[contains(@data-bind,'Chinese')]")).clear();
	   waitFor(By.xpath("//input[contains(@data-bind,'Chinese')]")).sendKeys(automationGroupTest);
	   saveExaminationGrouping();
   }
   
   /**
    * 
    * @param deleteAutomationGroupTest
    * @throws Exception
    */
   public void deleteExaminationGrouping(String deleteAutomationGroupTest) throws Exception {
	   String deletePeriod = "//td[normalize-space(text())='" + deleteAutomationGroupTest + "']";
	   WebElement td = waitFor(By.xpath(deletePeriod));
	   String deleteButton = "//i[contains(@data-bind,'Delete')]";
	   find(td, By.xpath(deleteButton)).click();
	   Utils.waitABit(1000);
	   
       String checkDelete = waitFor(By.xpath("//p[@id='showP']")).getText().trim();
       Assert.isEqual("是否删除?", checkDelete);
       waitFor(By.xpath("//button[normalize-space(text())='确定']")).click();
       
       Utils.waitABit(1000);
       WebDriverUtils.confirmSave(driver);
       
   }
   
   //############导入考核组人员##########

   /**
    * 
    * @param fileName
    * @throws Exception
    */
   public void importFile(String fileName) throws Exception {
	   waitLoading();
	   waitFor(By.xpath("//i[contains(@data-bind,'SetPartner')]")).click();
	   String filePath = Utils.getImportExcelFilePath(fileName);
	   waitFor(By.xpath("//li[@id='PartnerImport']/i[text()='导入']")).click();
	   waitFor(By.id("file0")).sendKeys(filePath);
	   Utils.waitABit(3000);
	   waitFor(By.xpath("//span[contains(@data-bind,'Upload')][normalize-space(text())='上传']")).click();

	   Alert alert=driver.switchTo().alert();
	   alert.accept();	   
	   waitFor(By.xpath("//span[contains(@data-bind,'CanUpload()')][normalize-space(text())='验证']")).click();
	   waitLoading();
	   waitFor(By.xpath("//span[contains(@data-bind,'Message()')][normalize-space(text())='处理']")).click();
	   Utils.waitABit(1000);
	   String checkText = waitFor(By.xpath("//p[@id='showP'][text()='导入成功']")).getText().trim();  	   
	   WebDriverUtils.confirmSave(driver);
   }
   
   /**
    * 验证导入的人员是不是对的
    * @param tatol
    * @throws Exception
    */
   public void findImportStaffNO(String tatol) throws Exception {
	  String tatolText = "//span[contains(@data-bind,'unLockedCount')][normalize-space(text())='" + tatol + "']";
	  String numberImport =  waitFor(By.xpath(tatolText)).getText().trim();
	  Assert.isEqual(tatol, numberImport);
	  logger.info("目前未发起的人数是：" +tatol );
	  
	  String unlockedCount = "//span[contains(@data-bind,'unLockedCount')][normalize-space(text())='" + tatol + "']";
	  waitFor(By.xpath(unlockedCount)).click();
	  waitLoading();
	  java.util.List<WebElement> tr_list = findAll(By.xpath("//tbody[@id='PartnerDetailTBody']/tr"));
	  logger.info("当前导入的人数为" +tr_list.size());
	  Assert.isEqual(Integer.valueOf(tatol), tr_list.size());
   }
   
   //############批量添加考核成员，已发起或已离职的人员不能参与发起##########
   public void clickBatchAddButton(String periodGroup) throws Exception {
	   Utils.waitABit(1000);
	   String batchButton = "//td[normalize-space(text())='" + periodGroup + "']"
	   		+ "//preceding-sibling::td[1]/i";
	   WebElement link = waitFor(By.xpath(batchButton));
	   openNewWindowWith(link);
   }
   
   public void clickTheEmployeeNumberLinkButton(String EmployeeField, String personnelField) throws Exception {
	   waitFor(By.xpath("//span[normalize-space(text())='" + personnelField + "']")).click();
	   String text = ".//div[.//dt[@id='title'][contains(text(),'" + EmployeeField + "')]]//following-sibling::div[1]"
	   		+ "//span[@id='dvStaffShowWindow']";
	   WebElement span = waitFor(By.xpath(text));
	   span.click();
   }
   
   public void chooseActiveEmployee(String activeStaffNo, String employeeTypeToActive) throws Exception {
	 if(activeStaffNo != null){
	     putInValue(waitFor(By.id("dvStaffSearchFilter")), activeStaffNo);
	     find(By.id("btndvStaffSearch")).click();
	     waitLoading();
	     String checkRadioButton = "//td[normalize-space(text())='" + activeStaffNo + "']"
	   		+ "//preceding-sibling::td[1]/input";
	     checkChkRadio(By.xpath(checkRadioButton), true);
	     String text = "//td[normalize-space(text())='" + activeStaffNo + "']//following-sibling::td[3]";
	     String assertEmployeeType = waitFor(By.xpath(text)).getText().trim();
	     Assert.isEqual(employeeTypeToActive, assertEmployeeType);
	  }
   }
   
   public void chooseInactiveEmployee(String inactiveStaffNo, String employeeTypeToInactive) throws Exception {
	   chooseActiveEmployee(inactiveStaffNo, employeeTypeToInactive);
   }
   
   public void addEmployeesToTheEmployeeNumberTextBox(String employee) throws Exception {
	   find(By.id("confirmdvStaffSelection")).click();
	   waitLoading();
	   try {
		 waitFor(By.xpath("//input[@id='dvStaffvalue'][contains(@value,'" + employee + "')]"));
	   } 
	   catch (Exception e) {
		 ;
	   }
   }
   
   public void clickBatchAddButton() throws Exception {
	   find(By.xpath("//a[contains(@onclick,'AddPartner')]/i")).click();
	   waitLoading();
	   switchToOpenerWindow();
   }
   
   public void clickAutomationHeadButton(String automationGroup) throws Exception {
	   String headButton = ".//td[normalize-space(text())='" + automationGroup + "']//preceding-sibling::td[*]"
	   		+ "/i[contains(@data-bind,'SetPartner')]";
	   find(By.xpath(headButton)).click();
	   waitLoading();
	   waitFor(By.xpath("//a[contains(@data-bind,'showUnlock')]")).click();
	   waitLoading();
   }
   
   public void verifyInactiveEmployee(String inactiveStaffNo) throws Exception {
	   String inactiveEmployee = "//tbody[@id='PartnerDetailTBody']/tr//td"
	   		+ "[contains(@data-bind,'StaffNo')][normalize-space(text())='" + inactiveStaffNo + "']";
	   Assert.notVisible(By.xpath(inactiveEmployee), "", driver);
   }
   
   public void verifyActiveEmployee(String staffNo, String state) throws Exception {
	   String activeEmployee = "//tbody[@id='PartnerDetailTBody']/tr//td"
	   		+ "[contains(@data-bind,'StaffNo')][normalize-space(text())='" + staffNo + "']";
	   String xpath = waitFor(By.xpath(activeEmployee)).getText().trim();
	   Assert.isEqual(staffNo, xpath);
	   
	   String initiatingState = "//td[normalize-space(text())='" + staffNo + "']//following-sibling::td[*]"
	   		+ "/input[contains(@data-bind,'false')]";
	   try {
		waitFor(By.xpath(initiatingState));
	} 
	   catch (Exception e) {
		;
	}
   }
   //############维护考核表单，与绩效周期关联##########
   /**
    * 新增表单
    * @param formsName
    * @param formWeight
    * @param minimumScore
    * @param maxScore
    * @throws Exception
    */
   public void fillAssessmentForm(String formsName, String formWeight, String minimumScore, String maxScore) throws Exception {
	   Utils.waitABit(2000);
	   waitFor(By.xpath("//button[contains(@data-bind,'click:AddForm')]")).click();
	   waitFor(By.xpath("//input[contains(@data-bind,'value:Name.Chinese')]")).sendKeys(formsName);
	   
	   String weight = "//input[contains(@data-bind,'value:Weight')]";
	   String minScore = "//input[contains(@data-bind,'numberMask:MinScore')]";
	   String numberMask = "//input[contains(@data-bind,'numberMask:MaxScore')]";
	   putInValue(waitFor(By.xpath(weight)), formWeight);
	   waitFor(By.xpath(minScore)).sendKeys(minimumScore);
	   waitFor(By.xpath(numberMask)).sendKeys(maxScore);
   }
   
   /**
    * 保存所新增的考核表单
    * @throws Exception
    */
   public void saveAssessmentForm() throws Exception {
	   waitFor(By.xpath("//button[contains(@data-bind,'visible:CanSave,click:Save')]")).click();
	   waitLoading();
	   WebDriverUtils.confirmSave(driver);
   }
   
   /**
    * 验证考核表单名称是否与维护的一致
    * @param AssessmentName
    * @throws Exception
    */
   public void checkAssessmentName(String AssessmentName) throws Exception {
	   waitLoading();
	   String checkName = waitFor(By.xpath("//span[normalize-space(text())='" + AssessmentName + "']")).getText().trim();
	   Assert.isEqual(AssessmentName, checkName);
   }
   
   /**
    * 新增维度
    * @param formsName
    * @param dimensionName
    * @param weight
    * @throws Exception
    */
   public void newDimension(String formsName, String dimensionName, String weight) throws Exception {
	  String xpath = "//div[@id='ztreeDiv']//span[normalize-space(text())='"+formsName+"']";
	  WebElement contextClick = waitFor(By.xpath(xpath));
	  Actions action = new Actions(driver);
	  action.moveToElement(contextClick).contextClick().build().perform();
	  waitLoading();
	  
	  waitFor(By.xpath("//li[@id='m_add'][normalize-space(text())='增加节点']")).click();
	  waitLoading();
	  
	  String verificationDimensions = "//select[@id='TypeSelect']/option[text()='维度']";
	  String checkDimensions = waitFor(By.xpath(verificationDimensions)).getText().trim();
	  Assert.isEqual("维度", checkDimensions);
	  logger.info("此处选择的新增类型是：" +checkDimensions);
	  
	  String diagXpath = "//div[@id='diagDimension']//input[contains(@data-bind,'value:Name.Chinese')]";
	  waitFor(By.xpath(diagXpath)).sendKeys(dimensionName);
	  waitLoading();
	  
	  String weightXpath = "//div[@id='diagDimension']//input[@id='Weight']";
	  waitFor(By.xpath(weightXpath)).clear();
	  waitFor(By.xpath(weightXpath)).sendKeys(weight);
     
      waitFor(By.xpath("//div[@class='ui-dialog-buttonset']/button[text()='确定']")).click();
      Utils.waitABit(2000);
      WebDriverUtils.confirmSave(driver); 
   }
   
   /**
    * 验证维度是否与新增是维护的一致
    * @param checkDimensionsName
    * @throws Exception
    */
   public void checkDimensionsName(String checkDimensionsName) throws Exception {
	   Utils.waitABit(2000);
	   String spanText = "//div[@id='ztreeDiv']//span[normalize-space(text())='"+checkDimensionsName+"']";
	   String checkName = waitFor(By.xpath(spanText)).getText().trim();
	   Assert.isEqual(checkDimensionsName, checkName);
   }
   
   /**
    * 新增指标
    * @param dimensionName
    * @param indexName
    * @param weight
    * @throws Exception
    */
   public void newIndex(String dimensionName, String indexName, String weight) throws Exception {
	   String dimension = "//div[@id='ztreeDiv']//span[normalize-space(text())='"+dimensionName+"']";
	   WebElement contextClick = waitFor(By.xpath(dimension));
	   Actions action = new Actions(driver);
	   action.moveToElement(contextClick).contextClick().build().perform();
	   
	   waitFor(By.xpath("//li[@id='m_add'][normalize-space(text())='增加节点']")).click();
	   waitLoading();
	   String verificationDimensions = "//select[@id='TypeSelect']/option[text()='指标']";
	   String checkDimensions = waitFor(By.xpath(verificationDimensions)).getText().trim();
	   Assert.isEqual("指标", checkDimensions);
	   logger.info("此处选择的新增类型是：" +checkDimensions);
	   
	   String chinese = "//div[@id='diagKPI']//input[contains(@data-bind,'value:Name.Chinese')]";
	   waitFor(By.xpath(chinese)).clear();
	   waitFor(By.xpath(chinese)).sendKeys(indexName);
	   
	   String weightText = "//div[@id='diagKPI']//input[contains(@data-bind,'value:Weight')]";
       waitFor(By.xpath(weightText)).clear();
       waitFor(By.xpath(weightText)).sendKeys(weight);
       
       waitFor(By.xpath("//div[@class='ui-dialog-buttonset']/button[text()='确定']")).click();
       Utils.waitABit(2000);
       WebDriverUtils.confirmSave(driver);
   }
   
   /**
    * 验证指标是否与新增一致
    * @param checkIndexName
    * @throws Exception
    */
   public void checkIndexName(String checkIndexName) throws Exception {
	   String checkIndex = waitFor(By.xpath("//div[@id='ztreeDiv']//span[normalize-space(text())='"+checkIndexName+"']")).getText().trim();
	   Assert.isEqual(checkIndexName, checkIndex);
   }
   
   /**
    * 删除表单
    * @param deleteForm
    * @throws Exception
    */
   public void deleteAssessmentForm(String deleteForm) throws Exception {
	   waitFor(By.xpath("//span[@id='formSetTree_1_span'][contains(text(),'" + deleteForm + "')]")).click();
	   waitFor(By.xpath("//a[@id='formSetTree_1_a']/span[@id='formSetTree_1_remove']")).click();
	   Utils.waitABit(2000);
	   String xpath = waitFor(By.xpath("//p[@id='showP'][contains(text(),'节点')]")).getText().trim();
	   Assert.isEqual("要删除的节点是表单，如果删除将连同模板以及其子节点一起删掉。 请确认！", xpath);
	   
	   waitFor(By.xpath("//button[contains(@class,'btn')][normalize-space(text())='确定']")).click();
	   waitLoading();
	   
	   WebDriverUtils.confirmSave(driver);
	   waitLoading();
   }
   
   //############周期属性设置，编辑考核结果等级##########
   /**
    * 
    * @throws Exception
    */
   public void entryAttributesIndex() throws Exception {
	   waitFor(By.xpath("//td[@style='text-align: center']/i[@title='属性设置']")).click();
	   waitLoading();
	   
	   String define = "//div[@id='AssessDefineListDiv']//th[normalize-space(text())='能力考核']";
	   String competenceAssessment = waitFor(By.xpath(define)).getText().trim();
	   Assert.isEqual("能力考核", competenceAssessment);
	   logger.info(competenceAssessment);
   }
   
   /**
    * 填写绩效考核等级
    * @param levelOne
    * @throws Exception
    */
   public void fillDefineLevelOne(String levelOne) throws Exception {
	   waitFor(By.xpath("//button[normalize-space(text())='编辑']")).click();
	   String classDefinition = waitFor(By.xpath("//span[normalize-space(text())='定义等级']")).getText().trim();
	   Assert.isEqual("定义等级", classDefinition);
	   logger.info(classDefinition);
	   
	   String afterXpath = "//input[contains(@data-bind,'afterkeydown')]";
	   waitFor(By.xpath(afterXpath)).clear();
	   waitFor(By.xpath(afterXpath)).sendKeys(levelOne);
   }
   
   /**
    * 
    * @throws Exception
    */
   public void Addlevel() throws Exception {
	   waitFor(By.xpath("//button[contains(@data-bind,' add')][normalize-space(text())='增加']")).click(); 
   }
   
   /**
    * 
    * @param levelTwo
    * @throws Exception
    */
   public void addDefineLevelTwo(String levelTwo) throws Exception {
	   waitFor(By.xpath("//input[contains(@data-bind,'afterkeydown')]")).clear();
	   waitFor(By.xpath("//input[contains(@data-bind,'afterkeydown')]")).sendKeys(levelTwo);
	   Addlevel();
   }
   
   /**
    * 
    * @param levelThree
    * @throws Exception
    */
   private static String xpath = "//input[contains(@data-bind,'afterkeydown')]";
   public void addDefineLevelThree(String levelThree) throws Exception {
	   putInValue(waitFor(By.xpath(xpath)), levelThree);
	   Addlevel();
   }
   
   /**
    * 
    * @param levelFour
    * @throws Exception
    */
   public void addDefineLevelFour(String levelFour) throws Exception {
	   waitFor(By.xpath("//input[contains(@data-bind,'afterkeydown')]")).clear();
	   waitFor(By.xpath("//input[contains(@data-bind,'afterkeydown')]")).sendKeys(levelFour);
	   Addlevel();
   }
   
   /**
    * 
    * @param allLevel
    * @throws Exception
    */
   public void saveDefineLevel(String allLevel) throws Exception {
	   waitFor(By.xpath("//button[@class='btn'][normalize-space(text())='确定']")).click();
	   String checkAllLevel = waitFor(By.xpath("//span[@id='gradeListView'][contains(text(),'" + allLevel + "')]")).getText().trim();
	   Assert.isEqual(allLevel, checkAllLevel);
	   logger.info("目前编辑的考核结果等级是：" +checkAllLevel);
   }

 //############周期发起，无发起人员不能发起##########
   /**
    * 
    * @param launchCycle
    * @throws Exception
    */
   public void launchCycle(String launchCycle) throws Exception {
	   waitFor(By.xpath("//td/i[@title='期初发起'][text()='" + launchCycle + "']")).click();	   
   }
   
   /**
    * 
    * @param confirm
    * @throws Exception
    */
   public void confirm(String confirm) throws Exception {
	   String checkCycleText = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='" + confirm + "']")).getText().trim();
	   Assert.isEqual(confirm, checkCycleText);
	   logger.info(checkCycleText);
	   waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
	   
	   String checkTo = waitFor(By.xpath("//p[@id='showP'][contains(text(),'该周期属性没有设置')]")).getText().trim();
	   Assert.isEqual("该周期属性没有设置，不能发起！", checkTo);
	   waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
   }

 //############周期属性设置，配置发起小组、表单、流程##########

   /**
    * 添加新的考核定义
    * @param groupName
    * @param formName
    * @param initialFlow
    * @param interimProcess
    * @param finalProcess
    * @throws Exception
    */
   public void addNewAssessmentDefinitions(String groupName, String formName, String initialFlow, String interimProcess, String finalProcess) throws Exception {
	   waitFor(By.xpath("//div[@id='AssessDefineListDiv']/button[contains(text(),'考核')]")).click();
	   waitLoading();
	   //选择分组
	   String groupMode = "//div[@id='AssessDefineEditor']//select[contains(@data-bind,'groupMode')]";
	   selectByVisibleText(By.xpath(groupMode), groupName);
	  
	   //选择表单
	   String selectXpath = "//div[@id='AssessDefineEditor']//tr[3]/td[2]/select";
	   selectByVisibleText(By.xpath(selectXpath), formName);
	  
	   //选择期初流程
	   waitLoading();
	   selectByVisibleText(By.xpath(selectXpath), initialFlow);
	   
	   //选择期中流程
	   waitLoading();
	   selectByVisibleText(By.xpath(selectXpath), interimProcess);
	  
	   //选择期末流程
	   waitLoading();
	   waitFor(By.xpath(selectXpath)).click();
   }

   /**
    * 
    * @param teamName 
    * @param assessmentForm
    * @throws Exception
    */
   public void checkAssessmentDefinitions(String teamName, String assessmentForm) throws Exception {
	   String checkTeamName = waitFor(By.xpath("//td[text()='" + teamName + "']")).getText().trim();
	   Assert.isEqual(teamName, checkTeamName);
	   
	   String checkAssessmentForm = waitFor(By.xpath("//td[text()='" + assessmentForm + "']")).getText().trim();
	   Assert.isEqual(assessmentForm, checkAssessmentForm);
   }
    
   /**
    * 
    * @param groupName
    * @throws Exception
    */
 
   public void deleteAssessmentDefinitions(String groupName) throws Exception {
	   WebElement tr = waitFor(By.xpath("//td[text()='" + groupName + "']"));
	   find(tr, By.xpath("//i[contains(@data-bind,'Delete')][@title='删除']")).click();

       String text = waitFor(By.xpath("//p[@id='showP'][text()='确认删除 ?']")).getText().trim();
       Assert.isEqual("确认删除 ?", text);
       logger.info(text);
       Utils.waitABit(1000);
       waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
       Utils.waitABit(1000);

       WebDriverUtils.confirmSave(driver);
   }	  
   
   /**
    * 
    * @param dominate
    * @param periodName
    * @param checkSuccess
    * @throws Exception
    */
   public void ealryStart(String dominate, String periodName, String checkSuccess) throws Exception{
	   WebElement tr = waitFor(By.xpath("//tbody//td[text()='" + periodName + "']"));
	   String initialXpath = "//td/i[text()='" + dominate + "']";
	   find(tr, By.xpath(initialXpath)).click();
       waitLoading();
       String checkSuccessful = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='" + checkSuccess + "']")).getText().trim();
       Assert.isEqual(checkSuccess, checkSuccessful);
       logger.info(checkSuccess);
       waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
   }
   
   /**
    * 校验周期状态
    * @param periodStatus
    * @param periodName
    * @param periodDate
    * @param KeyWords
    * @throws Exception
    */
   public void verificationPeriodStatus(String periodStatus, String periodName, String periodDate, String KeyWords) throws Exception{
	   driver.navigate().refresh();
	   waitLoading();
	   search(KeyWords);
	   Utils.waitABit(2000);
	   
	   String periodStatusXpath = "//div[@id='PeriodListDiv2']//tbody//td[contains(@data-bind,'Status')][text()='" + periodStatus + "']";
	   String checkStatus = waitFor(By.xpath(periodStatusXpath)).getText().trim();
	   Assert.isEqual(periodStatus, checkStatus);
	   
	   String periodDateXpath = "//div[@id='PeriodListDiv2']//td[contains(@data-bind,'LaunchDate')][text()='" + periodDate + "']";
	   String checkLaunchDate = waitFor(By.xpath(periodDateXpath)).getText().trim();
	   Assert.isEqual(periodDate, checkLaunchDate);
   }
   
 //############流程跟踪报表查看流程表单##########
   /**
    * 查看绩效流程表单
    * @param processName
    * @param instanceId
    * @param saveText
    * @param submitText
    * @param flowStatus
    * @throws Exception
    */
   public void seeProcessForm(String processName, String instanceId, String saveText, String submitText, String flowStatus) throws Exception {
	   waitFor(By.id("btnSenior")).click();
	   waitFor(By.id("InstanceId")).clear();
	   waitFor(By.id("InstanceId")).sendKeys(instanceId);
	   waitFor(By.id("dvConfirmSearch")).click();
	   waitLoading();
	   WebElement tr = waitFor(By.xpath("//div[@id='ApprovalDiv']//td[contains(text(),'" + instanceId + "')]"));
	   String formName = "//div[@id='ApprovalDiv']//a[normalize-space(text())='" + processName + "']";
	   find(tr, By.xpath(formName)).click();
	   openNewWindowWith(find(tr, By.xpath(formName))); 
	   
	   //保存表单信息
	   waitFor(By.id("保存 ")).click();
	   waitLoading();
	   String checkText = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='" + saveText + "']")).getText().trim();
	   Utils.waitABit(1000);
	   Assert.isEqual(saveText, checkText);
	   waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
	   waitLoading();
	   
	   //提交表单
	   driver.navigate().refresh();
	   waitFor(By.xpath("//button[@id='提交 '][normalize-space(text())='提交']")).click();
	   String checkSubmitText = waitFor(By.xpath("//p[@id='showP'][normalize-space(text())='" + submitText + "']")).getText().trim();
	   Assert.isEqual(submitText, checkSubmitText);
	   waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
	   waitLoading();

       WebDriverUtils.confirmSave(driver);
       waitFor(By.xpath("//button[@class='btn btn-default'][text()='确定']")).click();
   }
   
 //############周期发起之后，期间状态为期初/期中/期末发起##########
   public void checkPeriodStatus(String periodState, String periodName) throws Exception {
	 String checkPeriodStatus = waitFor(By.xpath("//td[text()='" + periodState + "']")).getText().trim();
     Assert.isEqual(periodState, checkPeriodStatus);
   }
   
 //############考核分组，导出当前考核人员##########
   /**
    * 点击examinationGroup的人员按钮
    * @param examinationGroup 绩效考核组名称
    * @throws Exception
    */
   public void clickPersonButton(String examinationGroup) throws Exception {
	   Utils.waitABit(5000);
	   String groupXpath = "//td[normalize-space(text())='" + examinationGroup + "']//preceding-sibling::td[3]/i";
	   waitFor(By.xpath(groupXpath)).click();
	   waitLoading();
   }
   
   /**
    * 选中staffNo
    * @param staffNo
    * @throws Exception
    */
   public void selectedEmployee(String staffNo) throws Exception {
	   if(staffNo != null){
		   String li = "//li[contains(@onclick,'CancelPartnerList')]//following-sibling::li[2]";
		   find(By.xpath(li)).click();
		   waitLoading();
		   String checkBox = "//td[normalize-space(text())='" + staffNo + "']//preceding-sibling::"
		   		+ "td[*]/input[@type='checkbox']";
		   waitFor(By.xpath(checkBox)).click();
	   }
	   else{
		   java.util.List<WebElement> tr_list = findAll(By.xpath("//tbody[@id='PartnerDetailTBody']/tr"));
		   logger.info("当前导入的人数为" +tr_list.size());
		   Assert.isEqual(Integer.valueOf(staffNo), tr_list.size());
	   }
   }
   
   /**
    * 点击导出按钮
    * @throws Exception
    */
   public void clickOnTheExportButton() throws Exception {
	   String exportButton = "//li[contains(@data-bind,'Export')]/i[normalize-space(text())='导出']";
	   find(By.xpath(exportButton)).click();
	   waitLoading();
	   String window = driver.getWindowHandle();
		 for (String winHandle : driver.getWindowHandles()) {
			if (!winHandle.equals(window)) {
				driver.switchTo().window(window);
				break;
				}
			}
			Utils.waitABit(5000);
			switchToOpenerWindow();
		}
   
   /**
    * 将当前页面要导的值存到List
    * @return
    * @throws Exception
    */
    public List<LinkedHashMap<String, String>> getTheCurrentData() throws Exception  {
    	//定义当前页面的表头，把值存在List,赋值时用
    	List<WebElement> values = waitForMinimumNum(By.xpath("//div[@id='PartnerListDiv']//table/thead/tr/th"), 1);
    	//定义表格数据
    	List<WebElement> tdValues = waitForMinimumNum(By.xpath("//tbody[@id='PartnerDetailTBody']/tr"), 1);
    	//建立一个空HashMap存放页面数据
    	ArrayList<LinkedHashMap<String, String>> groupData = new ArrayList<LinkedHashMap<String, String>>();
    	//一层循环 循环表格的List
    	for(int i = 0; i < tdValues.size(); i++){
    		//，拆分表格把当前循环到的表格赋给tds
    		List<WebElement> tds = tdValues.get(i).findElements(By.xpath(".//td"));
    		//定义一个空的row暂时放行数据
    		LinkedHashMap<String, String> row = new LinkedHashMap<String, String>();
    		//二层循环，循环表头
    		for(int j = 0; j < values.size(); j++){
    			//循环到编辑时跳过
    			if (j == 0 || j ==1 ) {
    				continue;
    			}
    			//判断(是否已发起复选框)的选中状态
    			if (j == 8) {
    				//如果这个复选框是选中状态，那么它的值是true,否则是false
    				if (tds.get(j+1).findElement(By.xpath(".//input")).isSelected()) {
    					row.put(values.get(j).getText().trim(), "true");
    				} else {
    					row.put(values.get(j).getText().trim(), "false");
    				}
    			} else {
    				//对应values，把当前循环到的数据进行赋值
        			row.put(values.get(j).getText().trim(), tds.get(j+1).getText().trim());
    			}
    		}
    		groupData.add(row);//把已经赋值的row添加到groupData中(column,row)
    	}
		return groupData;
    }
    
    public String downLoadFile() throws Exception {
    	Utils.waitABit(5000);
		return WebDriverUtils.waitForDownloadFile(FileUtil.countDownloadFolderFileNo());
    }
    
    public void verifyExcelData(String fileName, List<LinkedHashMap<String, String>> getTheCurrentData) throws Exception {
    	String downloadFile = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER + fileName;
    	List<LinkedHashMap<String, String>> excelExportData = Excel.readWorkBook(downloadFile);
    	FileUtil.deleteFile(downloadFile);
    	Assert.isEqual(getTheCurrentData.size(), excelExportData.size());
		Assert.isListWithLinkedHashMapEqual(getTheCurrentData, excelExportData);
    }
}