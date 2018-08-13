package com.winmax.pages.attendance;

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
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class AttendanceParameterPage extends WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(AttendanceSettingPage.class);	
	public AttendanceParameterPage() {
		PageFactory.initElements(driver, this);
	}
	
	//##################### 考勤参数 - 万年历设置  ##########################
	public void clickAddHoliday() throws Exception {
		//refresh current page 
		waitFor(By.xpath("//*[@id='Navbreadcrumb']//a[contains(text(),'万年历设置')]")).click();
		waitLoading();
		Actions action = new Actions(driver);
		WebElement addBtn = find(By.xpath("//div[@id='divHolidayMaster']//a[contains(@href,'AddHoliday')]/i"));
		action.moveToElement(addBtn).click(addBtn).build().perform();
	}
	
	public void fillHoliday(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);			
			By by = By.xpath("//tr[@id='divEditHoliMas']//table/tbody//tr/td[normalize-space(text())='" + key + "']/following-sibling::td[1]/input");
			WebElement field = waitForVisible(by);
			if (field.getAttribute("type").contains("text")) {
				putInValue(field, value);
			}
		}	
	}

	public void saveHoliday() throws Exception {
		find(By.xpath("//tr[@id='divEditHoliMas']//button[contains(.,'保存')]")).click();
		waitLoading();
		WebDriverUtils.confirmSave(driver);
	}
	
	public void clickCancelWhenAddHoliday() throws Exception {
		find(By.xpath("//tr[@id='divEditHoliMas']//button[contains(.,'取消')]")).click();
		waitForNotVisible(By.xpath("//tr[@id='divEditHoliMas']//button[contains(.,'取消')]"));
	}
	
	private String getTrXpathByHolidayCode(String holidayType) {
		return "//table[@id='tbShowDetails']//tr[.//*[normalize-space(text())='" + holidayType + "']]";
	}
	
	public void verifyHolidayRecord(HashMap<String, String> data) throws Exception {
		String holiday = data.get("假期类别");
		HashMap<String, String> actualData = new HashMap<String, String>();
		
		List<WebElement> headers = waitForMinimumNum(By.xpath("//*[@id='divHolidayMaster']//div[contains(@class,'table')]/table//td"), 3);
		List<WebElement> values = waitForMinimumNum(By.xpath(getTrXpathByHolidayCode(holiday) + "//td"), 3);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		Assert.assertHashMapAContainsHashMapB(actualData, data);
	}
	
	public void clickEditHolidayRecord(String holidayType) throws Exception {
		//refresh current page 
		waitFor(By.xpath("//*[@id='Navbreadcrumb']//a[contains(text(),'万年历设置')]")).click();
		waitLoading();
		WebElement tr = waitFor(By.xpath(getTrXpathByHolidayCode(holidayType)));
		tr.click();
		Utils.waitABit(500);
		WebElement editBtn = find(By.xpath("//div[@id='divHolidayMaster']//a[contains(@href,'UpdateHoliday')]/i"));
		editBtn.click();
		waitFor(By.xpath("//tr[@id='divEditHoliMas']//button[contains(.,'保存')]"));
	}
	
	public void deleteHolidayRecord(String holidayType) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByHolidayCode(holidayType)));
		tr.click();
		Utils.waitABit(500);
		Actions action = new Actions(driver);
		WebElement clearBtn = find(By.xpath("//div[@id='divHolidayMaster']//a[contains(@href,'ClearHoliday')]/i"));
		action.moveToElement(clearBtn).click(clearBtn).build().perform();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		waitLoading();
		WebDriverUtils.confirmActionSuccess(driver);
	}
	
	public void verifyNoHolidayRecordInList(String holidayType) throws Exception {
		try {
			waitFor(By.xpath(getTrXpathByHolidayCode(holidayType)));
			Assert.fail("Ther is Holiday record " + holidayType);
		} catch (Exception e) {
			;
		}
	}
	
	public boolean isHolidayReocrdThere(String holiday) throws Exception {
		try {
			waitForVisible(By.xpath(getTrXpathByHolidayCode(holiday)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void clickDetailsForHolidayRecord(String holiday) throws Exception {
		WebElement tr = null;
		if (holiday.contains("random")) {
			tr = waitFor(By.xpath("//table[@id='tbShowDetails']//tr[1]"));
		} else {
			tr = waitFor(By.xpath(getTrXpathByHolidayCode(holiday)));
		}
		WebElement link = tr.findElement(By.xpath("./td/a[contains(@onclick,'Detail')]"));
		openNewWindowWith(link);
	}
	
	public void closeDetailsForHolidayRecord() throws Exception {
		waitForVisible(By.id("divMoreMonth"));
		driver.close();
		switchToOpenerWindow();
		waitForWindows(1);
	}
	
	//点击清除
	public void clearDataInDetailsForHolidayRecord() throws Exception {
		waitForVisible(By.xpath("//a[contains(@href,'ClearDay')][contains(.,'清除')]/i")).click();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		WebDriverUtils.confirmActionSuccess(driver);
		Assert.notPresent(By.xpath("//div[@id='jCalTarget']/div/div[contains(@style, 'rgb(255')]"), "所有日期字体为非红色", driver);
	}
	
	//点击上年
	public void clickYearInDetails(String year) throws Exception {
		List<WebElement> year_eles = waitForMinimumNum(By.xpath("//div[@class='jCalMo']/div/div/span[1]"), 12);
		int expectYear = Integer.valueOf(year_eles.get(0).getText().trim());
		String expect = null;
		if(year.contains("上年")) {
			expect = String.valueOf(expectYear-1);
		} else if(year.contains("下年")) {
			expect = String.valueOf(expectYear+1);
		}
		waitForVisible(By.xpath("//a[contains(@href,'GetByYear')][contains(.,'" + year + "')]/i")).click();
		waitLoading();
		year_eles = waitForMinimumNum(By.xpath("//div[@class='jCalMo']/div/div/span[1]"), 12);
		List<String> year_vals = getListElementsText(year_eles);
		Assert.isEqual(12, year_vals.size(), "假期明显显示12个月");
		for(String y : year_vals) {
			Assert.isEqual(expect, y, year + " 值");
		}
	}
	
	//取得特定月份的所有星期几的日期
	private List<String> getAllDaysOfWeekDay(String month, String weekDay) {
		List<String> list = new ArrayList<String>();
		List<WebElement> all = findAll(By.xpath("//div[@id='jCalTarget']/div[" + month + "]/div[contains(@class,'day')]"));
		List<String> all_days = getListElementsText(all);
		List<WebElement> aMonthDays = waitForMinimumNum(By.xpath("//div[@id='jCalTarget']/div[" + month + "]/div[@class='day']"), 20);
		
		int index = all_days.subList(0, all.size()-7).indexOf("1");
		int weekD = Integer.valueOf(weekDay);
		int beginDay = 0;
		if (weekD>=index) {
			beginDay = weekD - index + 1;
		} else {
			beginDay = 1+7 - (index - weekD);
		}
		for(int i=0;i<=6;i++)  {
			int d = beginDay+7*i;
			if (d<=aMonthDays.size()) {
				list.add(String.valueOf((d)));
			} else {
				break;
			}
		}		
		return list;
	}
	
	//验证星期几的日期为假期， 字体颜色为红色
	private void verifyAllDaysOfWeekDayRed(String month, String weekDay) {
		List<String> days = getAllDaysOfWeekDay(month, weekDay);
		for (int i=0;i<days.size();i++) {
			WebElement cell = find(By.xpath("//div[@id='jCalTarget']/div[" + month + "]/div[normalize-space(text())='" + days.get(i) + "'][@class='day']"));
			String style = cell.getAttribute("style");
			Assert.isContains(style, "rgb(255, 0, 0)", "月: " + month + ", 星期: " + weekDay + ", 日期: " + days.get(i) + ", actual " + style);
		}
		
	}
	
	//点击星期几 ,并验证设置成功
	public void clickWeekDayInDetails(String weekDay) throws Exception {
		waitFor(By.xpath("//div[@id='divMoreMonth']//a[@class='bootstro'][contains(@href,'" + weekDay + "')]")).click();
		waitLoading();
		Utils.waitABit(2000);
		WebDriverUtils.confirmActionSuccess(driver);
		for (int i=1;i<13;i++) {
			verifyAllDaysOfWeekDayRed(String.valueOf(i), weekDay);
		}
	}

	private String getCellIDByDate(String date) {
		String[] str = date.split("-");
		String day = str[2].startsWith("0") ? str[2].substring(1, 1) : str[2];
		String month = str[1].startsWith("0") ? str[1].substring(1, 1) : str[1];
		return month + "_" + day + "_" + str[0];
	}
	
	//点击万年历上的一个日期
	public void clickDateInDetails(String date) throws Exception {		
		WebElement cell = waitFor(By.xpath("//div[contains(@id,'" + getCellIDByDate(date) + "')]"));
		scrollTo(cell);
		cell.click();
		waitFor(By.id("divClick"));
	}
	
	//点击万年历上的一个日期后, 验证弹出的菜单选项
	public void verifyDateMenusInDetails(List<String> expectedMenu) throws Exception {
		List<WebElement> menus_eles = this.waitForMinimumNum(By.xpath("//div[@id='divClick']//li[not(@style)]/a"), expectedMenu.size());
		List<String> menus = getListElementsText(menus_eles);
		for (int i=0;i<menus.size();i++) {
			String expected = expectedMenu.get(i);
			expected = ((i==0? Utils.getDate(expected):expected)).replace("-", "/");
			Assert.isEqual(expected, menus.get(i));
		}
	}
	
	//更改日期为某种假期类型
	public void setHolodayForDay(String date, String holiday) throws Exception {
		Actions action = new Actions(driver);
		WebElement menu = waitFor(By.xpath("//div[@id='divClick']//li[not(@style)]/a[contains(text(),'" + holiday + "')]"));
		action.moveToElement(menu).click(menu).build().perform();
		WebDriverUtils.confirmActionSuccess(driver);
		WebElement dateCell = waitFor(By.xpath("//div[contains(@id,'" + getCellIDByDate(date) + "')]"));
		String style = dateCell.getAttribute("style");
		Assert.isContains(style, "rgb(255, 0, 0)", "today's date actual style " + style);
	}
	
	//#################### 时间字典维护 ######################
	
	//展开左侧时间字典
	public void expandDict() throws Exception {
		By by_expand = By.xpath("//div[contains(@style,'display: block')]//div/span[@title='收折']");
		WebElement btn = waitFor(By.xpath("//*[@title='展开']"));
		if (btn.isDisplayed()) {
			btn.click();
		}
		waitFor(by_expand);
	}
	
	//在左侧弹出时间字典上，点击菜单
	public void clickMenusOnDict(String menus) throws Exception {
		String[] array = menus.split(">");
		By menu1By = By.xpath("//ul[@id='dicSite']/li/span[normalize-space(text())='" + array[0] + "']");
		By menu2By = By.xpath(".//div[@id='" + array[0] + "']/ul/li/a[contains(text(),'" + array[1] + "')]");
		if (!waitForShort(menu2By).isDisplayed()) {
			find(menu1By).click();				
		}
		waitForVisible(menu2By).click();
		waitLoading();
		waitFor(By.xpath(".//input[@id='lblDict'][contains(@value,'" + array[1] + "')]"));		
	}
	
	//主菜单中出现menu数据字典的相关数据
	public void verifyDictDataInMainArea(String menu) throws Exception {
		waitForMinimumNum(By.xpath("//div[@id='DictInfoListDiv']//tr[@name='trEach']"), 1);		
	}
	
	//取得吃饭时间的数据的名称
	public List<String> getMealTimeNames() throws Exception {
		List<WebElement> names_eles = findAll(By.xpath("//div[@id='DictInfoListDiv']//tr[@name='trEach']/td[2]"));
		List<String> names = this.getListElementsText(names_eles);
		return names;
	}
	
	//搜索功能
	public void quickSearchDict(String keyword) throws Exception {
		putInValue(By.id("Filter"), keyword);
		find(By.xpath("//div[@id='DictInfoListDiv']//a[contains(@href,'Search')]")).click();
		waitLoading();
	}
	
	public void verifyQuickSearchDictResult(String keyword) throws Exception {
		String failMsg = "时间字典中快速搜索 " + keyword;
		Assert.present(By.xpath("//div[contains(@class,'table')]//td[1][normalize-space(text())='" + keyword + "']"), failMsg, driver);
	}
		
	//去加班单维护,选择编辑第一条数据, 验证吃饭时间下拉框的值和时间字典中吃饭的值是一样的
	public void verifyOTMealTimeDropDown(List<String> mealNames) throws Exception {
		waitForVisible(By.xpath(".//*[@id='tbOtTranMaintenance']/tbody/tr[1]//a/i[contains(@data-bind,'Edit')]")).click();
		Select meal_sel = new Select(waitFor(By.xpath("//*[@id='dvEditOt']//select[contains(@data-bind,'OTMeal')]")));
		List<String> actuaVals = getListElementsText(meal_sel.getOptions());
		find(By.xpath(".//*[@id='dvEditOt']//button[contains(text(),'取消')]")).click();
		
	}
	
	public void clickAddDict() throws Exception {
		waitFor(By.xpath("//div[@id='DictInfoListDiv']//a[contains(@href,'Add')]")).click();		
	}
	
	public void fillDict(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);			
			By by = By.xpath("//tr[@id='dvEdit']//table/tbody//tr/td[normalize-space(text())='" + key + "']/following-sibling::td[1]/input");
			WebElement field = waitForVisible(by);
			if (field.getAttribute("type").contains("text")) {
				putInValue(field, value);
			}
		}	
	}

	public void saveDict() throws Exception {
		find(By.xpath("//tr[@id='dvEdit']//button[contains(.,'保存')]")).click();
		waitLoading();
		WebDriverUtils.confirmSave(driver);
	}
	
	private String getTrXpathByDictCode(String dict) {
		return "//div[@id='DictInfoListDiv']//table/tbody[contains(@data-bind,'Detail')]//tr[./td[1][normalize-space(text())='" + dict + "']]";
	}
	
	public void verifyDictRecord(HashMap<String, String> data) throws Exception {
		String dictCode = data.get("字典代码");
		HashMap<String, String> actualData = new HashMap<String, String>();		
		List<WebElement> headers = waitForMinimumNum(By.xpath("//*[@id='DictInfoListDiv']//div[contains(@class,'table')]/table//th"), 3);
		List<WebElement> values = waitForMinimumNum(By.xpath(getTrXpathByDictCode(dictCode) + "//td"), 1);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		Assert.assertHashMapAContainsHashMapB(actualData, data);
	}
	
	public void clickEditDictRecord(String dict) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByDictCode(dict)));
		tr.click();
		Utils.waitABit(500);
		WebElement editBtn = find(By.xpath("//div[@id='DictInfoListDiv']//a[contains(@href,'Edit')]/i"));
		editBtn.click();
		waitFor(By.xpath("//tr[@id='dvEdit']//button[contains(.,'保存')]"));
	}
	
	public void deleteDictRecord(String dictCode) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByDictCode(dictCode)));	
		tr.click();
		WebElement deleteBtn = find(By.xpath("//div[@id='DictInfoListDiv']//a[contains(@href,'Delete')]"));
		deleteBtn.click();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		waitLoading();
		WebDriverUtils.confirmActionSuccess(driver);
	}
	
	public void verifyNoDictRecordInList(String dictCode) throws Exception {
		try {
			waitFor(By.xpath(getTrXpathByDictCode(dictCode)));
			Assert.fail("Ther is dict record " + dictCode);
		} catch (Exception e) {
			;
		}
	}
	
	public boolean isDictReocrdThere(String dictCode) throws Exception {
		try {
			waitForVisible(By.xpath(getTrXpathByDictCode(dictCode)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//############### 考勤分析规则  #########################	
	private By sarchBtnBy = By.xpath("//div[@id='div_ko_bind']//a[contains(@onclick, 'Search')]");
	
	public void searchAnalysisRuleByDropDown(String value, String sel_name) throws Exception {
		String id = null;
		if (sel_name.equals("类型")) {
			id = "selType";
		} else {
			id = "selEnable";
		}
		Select sel = new Select(waitFor(By.id(id)));
		sel.selectByVisibleText(value);
		find(sarchBtnBy).click();
		waitLoading();
	}
	
	public void verifySearchAnalysisRuleByDropDownResult(String column, String val) throws Exception {
		List<String> results = getAnalysisRuleValueInOnePageByColumn(column);
		for (int i=0;i<results.size();i++) {
			if (column.equals("启用")) {
				if (results.get(i).equals("是") || results.get(i).equals("否")) {
					;
				} else {
					Assert.fail("column " + column + " value is incorrect: " + results.get(i));
				}
			} else {	
				Assert.isEqual(val, results.get(i));
			}
		}
	}
	
	public void keywordSearchAnalysisRule(String keyword) throws Exception {
		putInValue(By.id("SearchContent"), keyword);
		find(sarchBtnBy).click();
		waitLoading();
	}
	
	public void verifyKeywordSearchAnalysisRule(String keyword) throws Exception {
		List<String> headers = getListElementsText(findAll(By.xpath("//div[@id='div_ko_bind']//table/thead/tr/td")));
		int total = headers.size();
		List<WebElement> records = findAll(By.xpath("//tbody[@id='tbody_Figure']//tr"));
		for (int i=0;i<records.size();i++) {
			boolean flag = false;
			for (int j=1;j<=total;j++) {
				String expectText = records.get(i).findElement(By.xpath("./td[" + j + "]")).getAttribute("innerText").trim();
				if (expectText.toLowerCase().contains(keyword.toLowerCase())) {
					flag = true;
					break;
				}
			}
			Assert.isTrue(flag, "第" + (i+1) + "行数据不包含关键字 " + keyword);
		}
 
	}
	
	private List<String> getAnalysisRuleValueInOnePageByColumn(String columnName) throws Exception {
		List<String> result = new ArrayList<String>();
		List<String> headers = getListElementsText(findAll(By.xpath("//div[@id='div_ko_bind']//table/thead/tr/td")));
		int index = headers.indexOf(columnName) + 1;
		List<WebElement> records = findAll(By.xpath("//tbody[@id='tbody_Figure']//td[" + index + "]"));
		for (int i=0;i<records.size();i++){
			result.add(records.get(i).getText().trim());
		}
		return result;
	}
 
	//############### 统计项目设置  #########################	
	public void clickAddTotalItem() throws Exception {
		waitFor(By.xpath("//div[@id='div_ko_bind']//a[contains(@data-bind,'Add')]")).click();		
	}
	
	public void fillTotalItem(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);	
			if (key.equals("Code:")) {
				selectByVisibleText(By.id("selCode"), value);
			} else if (key.equals("TotalVariantKey:")) {
				selectByVisibleText(By.id("selTotalItems"), value);
			} else {
				By by = By.xpath("//tr[@id='dvEdit']//table/tbody//tr/td[normalize-space(text())='" + key + "']/following-sibling::td[1]/input");
				WebElement field = waitForVisible(by);
				if (field.getAttribute("type").contains("text")) {
					putInValue(field, value);
				}
			}
		}	
	}

	public void saveTotalItem() throws Exception {
		WebElement saveBtn = find(By.xpath("//tr[@id='dvEdit']//input[contains(@value,'保存')]"));
		scrollTo(saveBtn);
		saveBtn.click();
		waitLoading();
		WebDriverUtils.confirmSave(driver);
	}
	
	private String getTrXpathByTotalItemCode(String code) {
		return "//tbody[@id='tbody_Figure']//tr[./td[4]/*[normalize-space(text())='" + code + "']]";
	}
	
	public void verifyTotalItemRecord(String code, HashMap<String, String> data) throws Exception {
		HashMap<String, String> actualData = new HashMap<String, String>();		
		List<WebElement> headers = waitForMinimumNum(By.xpath("//div[@id='div_ko_bind']//table/thead//td"), 3);
		List<WebElement> values = waitForMinimumNum(By.xpath(getTrXpathByTotalItemCode(code) + "//td"), 1);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		Assert.assertHashMapAContainsHashMapB(actualData, data);
	}
	
	public void clickEditTotalItemRecord(String dict) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByTotalItemCode(dict)));		
		WebElement editBtn = tr.findElement(By.xpath("./td//a/i[contains(@class,'pencil')]"));
		editBtn.click();
		waitFor(By.xpath("//tr[@id='dvEdit']//input[contains(@value,'保存')]"));
	}
	
	public void deleteTotalItemRecord(String dictCode) throws Exception {
		WebElement tr = waitFor(By.xpath(getTrXpathByTotalItemCode(dictCode)));	
		WebElement deleteBtn = tr.findElement(By.xpath("./td//a/i[contains(@class,'trash')]"));
		WebDriverUtils.acceptAllConfirmations(driver);
		deleteBtn.click();		
		waitLoading();
		WebDriverUtils.confirmActionSuccess(driver);
	}
	
	public void verifyNoTotalItemRecordInList(String code) throws Exception {
		By by = By.xpath(getTrXpathByTotalItemCode(code));
		Assert.notPresent(by, "No total itme record " + code, driver);		
	}
	
	public boolean isTotalItemReocrdThere(String code) throws Exception {
		try {
			waitForVisible(By.xpath(getTrXpathByTotalItemCode(code)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void verifyOrderColumnValue() throws Exception {
		List<String> orders = getListElementsText(findAll(By.xpath("//tbody[@id='tbody_Figure']//td[3]")));
		for (int i=1;i<orders.size()-1;i++) {
			if (Integer.valueOf(orders.get(i+1))<Integer.valueOf(orders.get(i))) {
				Assert.fail("排序错误");
			}
		}		
	}
	
	//勾选第一行的checkbox
	public void checkSelectAllCheckbox() throws Exception {
		checkChkRadio(waitFor(By.id("CheckAll")), true);
	}
	
	public void verifyAllRecordsChecked() throws Exception {
		List<WebElement> checkList = findAll(By.xpath("//tbody[@id='tbody_Figure']//td[1]/input"));
		for (WebElement chk : checkList) {
			Assert.isTrue(chk.isSelected());
		}
	}

	//############### 考勤周期设置  #########################	
	public void clickAddPeriod() throws Exception {
		waitFor(By.xpath("//div[@id='TotalPeriod']//a/i[contains(@class,'plus')]")).click();		
	}
	
	//导入
	public void imporPeriod(String fileName) throws Exception {
		waitFor(By.xpath("//div[@id='TotalPeriod']//a[contains(.,'导入文件')]")).click();
		WebDriverUtils.importFile(fileName, driver);
		WebDriverUtils.verifyProcessImportFile(driver);		
	}
		
	public void fillPeriod(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);			
			By by = By.xpath("//*[@id='tdTotalPeriod']//td[contains(text(),'" + key + "')]/following-sibling::td[1]/*");
			if (key.contains("默认周期")) {
				this.checkChkRadio(By.xpath("//tr[@id='trTotalPeriod']//input[contains(@data-bind,'IsDefault')]"), value.contains("true"));
			} else if (waitForVisible(by).getTagName().equals("input")) {
				putInValue(waitForVisible(by), value);				 
			} else if (waitForVisible(by).getTagName().contains("textarea")) {
				find(by).clear();
				find(by).sendKeys(value);				
			}
		}		
	}
	
	public void savePeriod() throws Exception {
		waitForVisible(By.xpath("//td[@id='tdTotalPeriod']//button[contains(text(),'保存')]")).click();
		WebDriverUtils.confirmSave(driver);
	}
	
	public void selectPeriodAndSearch(String period) throws Exception {
		selectByVisibleText(By.id("SelectCycle"), period);
		waitFor(By.xpath("//*[@id='TotalPeriod']/div[1]//a/i[contains(@class,'search')]")).click();
	}
	
	public void openPeriodEditForm() throws Exception {
		waitFor(By.xpath("//table//tr[1]/td/i[contains(@data-bind,'Edit')]")).click();
	}
	
	public void searchPeriodAndDelete(String period) throws Exception {		
		if (findAll(By.xpath("//tr[./td[normalize-space(text())='" + period + "']]")).size()==0) {
			selectPeriodAndSearch(period);	
		}
		waitFor(By.xpath("//tr[./td[normalize-space(text())='" + period + "']]/td/i[contains(@data-bind,'Delete')]")).click();
		waitFor(By.xpath("//button[text()='确定']")).click();
		WebDriverUtils.confirmDelete(driver);
	}
	
	public void searchPeriodAndDeleteIfExist(String period) throws Exception {
		if (isPeriodSelectContainsPeriod(period)) {
			searchPeriodAndDelete(period);
		}		
	}

	public boolean isPeriodSelectContainsPeriod(String period) throws Exception {
		Select periodSel = new Select(waitFor(By.id("SelectCycle")));
		List<String> options = new ArrayList<String>();
		for (WebElement e : periodSel.getOptions()){
			options.add(e.getText().trim());
		}
		return options.contains(period)? true:false;		
	}
	
	public void verifyPeriodSelectNotContainsPeriod(String period) throws Exception {
		boolean actulflag = isPeriodSelectContainsPeriod(period);
		Assert.isTrue(!actulflag, "请选择周期 下拉框不包括 选项 ： " + period);
	}

	public void verifyPeriodRecord(String name, String isDefault, String desc) throws Exception {
		WebElement tr = waitFor(By.xpath("//tr[./td[normalize-space(text())='" + name + "']]"));
		boolean defaultVal = tr.findElement(By.xpath("./td[5]/input")).isSelected();
		String actualDes = tr.findElement(By.xpath("./td[6]")).getText().trim();
		if (isDefault!=null) {
			if (isDefault.contains("true")) {
				Assert.isTrue(defaultVal, "默认周期值");
			} else {
				Assert.isTrue(!defaultVal, "默认周期值");
			}
		}
		if (desc!=null) {
			Assert.isEqual(desc, actualDes, "描述值");
		}
	}
	
	//验证记录导入后的数据比对
	public void verifyPeriodUIDataWithExcel(HashMap<String, String> excelData) throws Exception { 
		String trXpath = "//table[contains(@class,'table-hover')]//tbody/tr[1]";
		verifyPeriodUIDataWithExcelByRow(trXpath, excelData);
	}
	
	//验证记录导入后的数据比对
	public void verifyPeriodUIDataWithExcelByRow(String trXpath, HashMap<String, String> excelData) throws Exception { 
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
	
	public void exportPeriod(String downloadFileName) throws Exception {
		waitFor(By.xpath("//div[@id='TotalPeriod']//a/i[contains(@class,'export')]")).click();
		WebDriverUtils.waitForDownloadFileByName(downloadFileName);
	}
	
	public void verifyPeriodExportDataCompareWithUI(List<LinkedHashMap<String, String>> excelData) throws Exception {		
		for (int i=0;i<excelData.size();i++) {
			String trXpath = "//table[contains(@class,'table-hover')]//tbody/tr[" + (i+1) + "]";
			verifyPeriodUIDataWithExcelByRow(trXpath, excelData.get(i));
		}
	}
	 
}
