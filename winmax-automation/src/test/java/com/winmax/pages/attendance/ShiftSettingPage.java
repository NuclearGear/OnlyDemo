package com.winmax.pages.attendance;

import java.util.HashMap;
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

public class ShiftSettingPage  extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(ShiftPage.class);
	
	public ShiftSettingPage() {
		PageFactory.initElements(driver, this);
	}

	public void fillShiftRecord(String code, String name, String desc, String come, String go, String no) throws Exception {	
		waitFor(By.xpath("//li[@id='shiftAdd']/a")).click();
		WebDriverUtils.waitLoading(driver);
		WebElement table = waitFor(By.xpath("//div[@id='dvShift']/div[2]//table"));
		putInValue(find(table, By.xpath("//tr[1]/td[2]/input")), code);
		fillInNameDesc(name, desc);
		
		//排序编号
		if (no != null) {
			putInValue(find(table, By.xpath("//input[contains(@data-bind,'OrderNo')]")), no);
		}
		//进出时间设定
		find(By.xpath("//button[contains(@data-bind, 'AddDetail')]")).click();
		fillComeGo(come, go);
	}
	
	public void fillComeGo(String come, String go) throws Exception {
		WebElement tr = find(By.xpath("//div[@id='dvShift']/div[4]//div[@id='D1']//center/table//tr[2]"));
		List<WebElement> inputs = findAll(tr, By.xpath(".//input[contains(@name,'display')]"));
		putInValue(inputs.get(0), come);
		Utils.waitABit(500);
		putInValue(inputs.get(1), go);
		Utils.waitABit(500);
	}
	
	//进去时间和日期的设置
	public void fillComeGo1(String come, String comeDay, String go, String goDay) throws Exception {
		//选进的日期， 选出的日期
		List<WebElement> select_all = findAll(By.xpath("//div[@id='D1']//tbody[contains(@data-bind,'Details')]/tr[./td[contains(text(),'进')]]/td//span[contains(@class,'select')]/span"));
		List<WebElement> input_all = findAll(By.xpath("//div[@id='D1']//tbody[contains(@data-bind,'Details')]/tr[./td[contains(text(),'进')]]/td//span[contains(@class,'input')]"));
		Actions action = new Actions(driver);
		if(comeDay!=null) {
			if (!input_all.get(0).getText().contains(comeDay)) {
				select_all.get(0).click();
				String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + comeDay + "')]";
				WebElement comeDaySel = waitForVisibleShort(By.xpath(xpath));
				action.moveToElement(comeDaySel).click(comeDaySel).build().perform();
				Utils.waitABit(1000);
			}
		}
		WebElement tr = find(By.xpath("//div[@id='dvShift']/div[4]//div[@id='D1']//center/table//tr[2]"));
		List<WebElement> inputs = findAll(tr, By.xpath(".//input[contains(@name,'display')]"));
		putInValue(inputs.get(0), come);
		Utils.waitABit(500);
		
		if(goDay!=null) {
			if (!input_all.get(1).getText().contains(goDay)) {
				select_all.get(1).click();
				String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + goDay + "')]";
				WebElement goDaySel = waitForVisibleShort(By.xpath(xpath));
				action.moveToElement(goDaySel).click(goDaySel).build().perform();
				Utils.waitABit(1000);
			}	
		}
		inputs = findAll(tr, By.xpath(".//input[contains(@name,'display')]"));
		putInValue(inputs.get(1), go);
		Utils.waitABit(500);
	}
	
	//进出时间设定 - 勾选模糊返回
	public void fillComeGoRange(String comeTime, String comeDay, String comeTime1, String comeDay1, 
			String goTime, String goDay, String goTime1, String goDay1) throws Exception {
		checkChkRadio(By.xpath("//td[contains(.,'模糊范围')]/input"), true);
		WebElement tr = waitForVisible(By.xpath("//tr[./td[contains(text(),'模糊范围')]]"));
		List<WebElement> input_list = findAll(tr, By.xpath("./td/div/input[not(contains(@style,'none'))]"));
		putInValue(input_list.get(0), comeTime);
		putInValue(input_list.get(1), comeTime1);
		putInValue(input_list.get(2), goTime);
		putInValue(input_list.get(3), goTime);
		List<WebElement> select_all = findAll(tr, By.xpath("./td/div/span//span[contains(@class,'drop-down')]"));
		List<WebElement> values_all_eles = findAll(tr, By.xpath("./td//span[contains(@class,'input')]"));
		List<String> values_all = getListElementsText(values_all_eles);
		
		Actions action = new Actions(driver);
		if (comeDay!=null && (!values_all.get(0).contains(comeDay))) {
			select_all.get(0).click();
			String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + comeDay + "')]";
			WebElement comeDaySel = waitForVisibleShort(By.xpath(xpath));
			action.moveToElement(comeDaySel).click().build().perform();	
			Utils.waitABit(1000);
		}
		if (comeDay1!=null && (!values_all.get(1).contains(comeDay1))) {
			select_all.get(1).click();
			String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + comeDay1 + "')]";
			WebElement comeDaySel = waitForVisibleShort(By.xpath(xpath));
			action.moveToElement(comeDaySel).click().build().perform();	
			Utils.waitABit(1000);
		}
		if (goDay!=null && (!values_all.get(2).contains(goDay))) {
			select_all.get(2).click();
			String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + goDay + "')]";
			WebElement goDaySel = waitForVisibleShort(By.xpath(xpath));
			action.moveToElement(goDaySel).click().build().perform();	
			Utils.waitABit(1000);
		}
		if (goDay1!=null && (!values_all.get(3).contains(goDay1))) {
			select_all.get(3).click();
			String xpath = "//div[@data-role='popup']/ul[@aria-hidden='false']/li[contains(text(),'" + goDay1 + "')]";
			WebElement goDaySel = waitForVisibleShort(By.xpath(xpath));
			action.moveToElement(goDaySel).click().build().perform();	
			Utils.waitABit(1000);
		}
	}
	
	public void fillInNameDesc(String name, String desc) throws Exception {
		WebElement table = waitFor(By.xpath("//div[@id='dvShift']/div[2]//table"));
		if (name != null) {
			putInValue(find(table, By.xpath("//tr[1]/td[4]/input")), name);
		}
		if (desc != null) {
			putInValue(find(table, By.xpath("//tr[5]/td[2]/textarea")), desc);
		}
	}
	//輸入所属组织单元， 班值名称(英文) 
	public void fillEngNameOrg(String englishName, String org) throws Exception {
		if (englishName!=null) {
			putInValue(By.xpath("//input[contains(@data-bind,'Name.English')]"), englishName);
		}
		if (org!=null) {
			selectByVisibleText(By.id("slUnitId"), org);
		}
	}
	
	//设置弹性
	public void setBuffer(String min1, String min2) throws Exception {
		checkChkRadio(waitFor(By.xpath("//div[@id='D1']//input[contains(@data-bind, 'IsShowBuffer')]")), true);
		waitForVisible(By.xpath("//div[@id='D1']//input[contains(@data-bind, 'Start.Buffer')]"));
		putInValue(By.xpath("//div[@id='D1']//input[contains(@data-bind, 'Start.Buffer')]"), min1);
		putInValue(By.xpath("//div[@id='D1']//input[contains(@data-bind, 'End.Buffer')]"), min2);
	}
	
	public void updatedShiftRecord(String code, String name, String desc, String come, String go) throws Exception {	
		WebElement td2 = waitFor(By.xpath("//div[@id='details']//tr[./td[1][normalize-space(text())='" + code + "']]/td[2]"));
		scrollTo(td2);
		td2.click();		
		waitFor(By.xpath("//li[@id='shiftModify']/a")).click();
		WebDriverUtils.waitLoading(driver);
		fillInNameDesc(name, desc);
		
		//进出时间设定
		fillComeGo(come, go);
		
		//save
		saveShiftRecordSuccess();
	}
	
	private String update_color_shift_code = null;
	
	//选一条随机的记录, 用的 默认的 颜色，然后编辑为指定颜色
	public void updatedShiftRecord_color(List<String> styleList) throws Exception {
		if (update_color_shift_code == null) {
			List<WebElement> list = findAll(By.xpath("//div[@id='details']//tr/td[1][contains(@style,'white; color: black')]"));
			int index = Utils.getRandInt(0, list.size());
			update_color_shift_code = list.get(index).getText().trim();
		}
		openEditShiftRecordForm(update_color_shift_code);
			 
		//修改颜色
		WebElement color_tr = find(By.xpath("//div[@id='dvShift']//table//tr[./td[contains(text(),'前景颜色')]]"));
		List<WebElement> div_color = findAll(color_tr, By.xpath("./td//div[@style]"));
		for (int i=0; i<div_color.size(); i++) {
			String color = styleList.get(i);
			div_color.get(i).click();
			WebElement popup = waitForVisible(By.xpath("//div[contains(@class,'sp-palette-buttons')][not(contains(@class,'sp-hidden'))]"));
			
			if (color.toLowerCase().contains("white") || color.toLowerCase().contains("black")) {
				WebElement cell = waitFor(popup, By.xpath(".//span[contains(@class,'sp-thumb-el')][@title='" + color + "']/span"));				
				cell.click();
			} else {
				putInValue(waitFor(popup, By.xpath(".//div[contains(@class,'sp-input-container sp-cf')]/input")), color);		
			}
			waitFor(popup, By.xpath(".//button[not(contains(text(),'less'))]")).click();
		}
		//save
		saveShiftRecordSuccess();
	}
	
	//验证 班值 列的颜色
	public void verifyShiftStyle(String styleStr) throws Exception {
		String tr_xpath = "//div[@id='details']//tr[./td[1][normalize-space(text())='" + update_color_shift_code + "']]";
		String attribute_style = find(By.xpath(tr_xpath + "/td[1]")).getAttribute("style");
		Assert.isContains(attribute_style, styleStr, "班值列的颜色修改成功");
	}
	
	//排序编号功能校验
	public void verifyShiftOrder(String nameStr) throws Exception {
		String[] name_array = nameStr.split("-");
		List<WebElement> all_records = findAll(By.xpath("//div[@id='details']/table//tr/td[1]"));
		List<String> all_names = getListElementsText(all_records);
		int[] name_order = new int[name_array.length];
		for(int i=0; i<name_array.length;i++) {
			name_order[i] = all_names.indexOf(name_array[i]);
			if (i>1) {
				Assert.isTrue(name_order[i]>name_order[i-1], name_array[i] + " order is " + name_order[i] + "; " + 
						name_array[i-1] + " order is " + name_order[i-1]);
			}
		}
	}
	
	public void searchShiftRecord(String code, String name, String desc, String come, String go, String org) throws Exception {	
	    WebDriverUtils.waitLoading(driver);
		WebElement tr = waitFor(By.xpath("//div[@id='details']//tr[./td[1][normalize-space(text())='" + code + "']]"));
		String actualName = find(tr, By.xpath("./td[2]")).getText().trim();
		String actualDesc = find(tr, By.xpath("./td[4]")).getText().trim();
		String actualCome = find(tr, By.xpath("./td[5]")).getText().trim();
		String actualGo = find(tr, By.xpath("./td[6]")).getText().trim();
		Assert.isEqual(name, actualName, "班值名称");
		Assert.isEqual(desc, actualDesc, "描述");
		Assert.isEqual(come, actualCome, "一段進");
		Assert.isEqual(go, actualGo, "一段出");
		if (org!=null) {
			String actualOrg = find(tr, By.xpath("./td[3]")).getText().trim();
			Assert.isEqual(org, actualOrg, "组织节点");
		}
	}
	
	public void openEditShiftRecordForm(String codeOrName) throws Exception {
		WebElement td2 = waitFor(By.xpath("//div[@id='details']//tr[./td[normalize-space(text())='" + codeOrName + "']]/td[2]"));
		scrollTo(td2);
		td2.click();	
		waitFor(By.xpath("//li[@id='shiftModify']/a")).click();
		WebDriverUtils.waitLoading(driver);	
	}
	
	public void getShiftRecordInformation(String codeOrName) throws Exception {
		WebElement td2 = waitFor(By.xpath("//div[@id='details']//tr[./td[normalize-space(text())='" + codeOrName + "']]/td[2]"));
		scrollTo(td2);
		td2.click();	
		waitFor(By.xpath("//li[@id='shiftModify']/a")).click();
		WebDriverUtils.waitLoading(driver);	
	}
	
	public void noShiftRecord(String code) throws Exception {	
	    WebDriverUtils.waitLoading(driver);
		String msg = "Can not find shift record with code " + code;
		Assert.notPresent(By.xpath("//div[@id='details']//tr[./td[1][normalize-space(text())='" + code + "']]"), msg, driver); 
	}
	
	public void addOtherSettings(String morningTime1, String morningTime2, String afterTime1, String afterTime2) {
		find(By.xpath("//div[@id='dvShift']//a[contains(text(),'其他设定')]")).click();
		putInValue(By.id("morningHalfDayStartDatedisplay"), morningTime1);
		Utils.waitABit(500);
		putInValue(By.id("morningHalfDayEndDatedisplay"), morningTime2);
		Utils.waitABit(500);
		putInValue(By.id("afterHalfDayStartDatedisplay"), afterTime1);
		Utils.waitABit(1000);
		putInValue(By.id("endHalfDayStartDatedisplay"), afterTime2);
		Utils.waitABit(500);
	}
	
	//工作时间设定 填充
	public void addWorkTimeSetting(String workingH, String effectiveWorkingH, String shift, String workingDaya, String timeScale) {
		find(By.xpath("//div[@id='dvShift']//a[contains(text(),'工作时间设定')]")).click();	
		if (workingH != null) {
			putInValue(By.id("WorkingHours"), workingH);
			Utils.waitABit(500);
		}
		if (effectiveWorkingH != null) {
			putInValue(By.id("EffectiveWorkingHours"), effectiveWorkingH);
			Utils.waitABit(500);
		}
		
		if (shift != null) {
			selectByVisibleText(By.id("shiftId"), shift);
		}
		if (workingDaya != null) {
			putInValue(By.id("workingDays"), workingDaya);
			Utils.waitABit(1000);
		}
		if (timeScale != null) {
			putInValue(By.xpath("//tr[./td[contains(text(),'班值工作小时计算')]]/td/input"), timeScale);
			Utils.waitABit(500);
		}
	}
	
	//迟到早退设定
	public void setEarlyLate(HashMap<String, String> data) {
		waitFor(By.xpath("//div[@id='dvShift']//a[contains(text(),'迟到早退设定')]")).click();	
		Set<String> keys = data.keySet();
    	for (String key : keys) {
    		String xpath = "//tr/td[normalize-space(text())='" + key + ":']/following-sibling::td[1]/*";
    		String value = data.get(key);
    		WebElement field_ele = waitFor(By.xpath(xpath));
    		String type = field_ele.getAttribute("type").trim();
    		if (type.toLowerCase().equals("text")) {		
    			putInValue(field_ele, value);
    		} else if (type.toLowerCase().contains("select")) {
    			selectByVisibleText(By.xpath(xpath), value);
    		}
    	}    	
	}
	
	//自动生成加班单
	public void setGenerateOT(HashMap<String, String> data) {
		waitFor(By.xpath("//div[@id='dvShift']//a[contains(text(),'自动生成加班单')]")).click();	
		Set<String> keys = data.keySet();
    	for (String key : keys) {
    		String xpath = "//tr/td[normalize-space(text())='" + key + ":']/following-sibling::td[1]/*";
    		String value = data.get(key);
    		WebElement field_ele = waitFor(By.xpath(xpath));
    		String type = field_ele.getAttribute("type").trim();
    		if (key.contains("自动转换成加班的OT类型")) {
    			List<WebElement> ot_list = findAll(By.xpath(xpath));
    			if (value.isEmpty()) {
    				checkChkRadio(ot_list.get(0), false);
    				checkChkRadio(ot_list.get(1), false);
    				checkChkRadio(ot_list.get(2), false);
    			} else {
    				String[] radios = value.split(",");
    				for (int i=0;i<radios.length;i++) {
    					String index = radios[i].replace("OT", "").trim();
    					checkChkRadio(ot_list.get(Integer.valueOf(index)-1), true);
    				}
    			}
    			continue;
    		}
    		if (type.toLowerCase().equals("text")) {		
    			putInValue(field_ele, value);
    		} else if (type.toLowerCase().contains("select")) {
    			selectByVisibleText(By.xpath(xpath), value);
    		} else if (type.toLowerCase().equals("checkbox")) {
    			checkChkRadio(field_ele, value.equalsIgnoreCase("true"));
    		}
    	}   
	}
	
	//自动生成加班单
	public void setOT(HashMap<String, String> data) {
		waitFor(By.xpath("//div[@id='dvShift']//a[normalize-space(text())='加班']")).click();	
		Set<String> keys = data.keySet();
    	for (String key : keys) {
    		String[] array = key.split(":");
    		String section = array[0].trim();
    		String label = array[1].trim();
    		String trXpath = "//div[contains(@class,'active')]//table/tbody/tr[./td[1][contains(text(),'" + section + "')]]";
    		String value = data.get(key);
    		if (key.contains("OT时间刻度")) {
    			if (label.contains("分钟")) {
    				putInValue(waitFor(By.xpath(trXpath + "//input[contains(@data-bind,'TimeScale')]")), value);
    			} else {
    				selectByVisibleText(By.xpath(trXpath + "//select"), value);
    			}
    		} else if (key.contains("加班临界点")) {
    			if (label.contains("过夜天数")) {
    				putInValue(waitFor(By.xpath("//input[contains(@data-bind,'OvernightDayCount')]")), value);
    			} else {
    				putInValue(waitFor(By.id("AddOTTippingPointdisplay")), value);
    			}
    		} else {
    			WebElement field_ele = waitFor(By.xpath(trXpath + "/td[normalize-space(text())='" + label + "']/following-sibling::td[1]//input[not(contains(@style,'none'))]"));
    			putInValue(field_ele, value);
    		}
    	}   
	}	
	
	//工作时间设定 验证值
	public void verifyWorkTimeSetting(String workingH, String effectiveWorkingH, String shift, String workingDaya, String timeScale) {
		find(By.xpath("//div[@id='dvShift']//a[contains(text(),'工作时间设定')]")).click();	
		if (workingH != null) {
			Assert.assertElementAttributeValue(By.id("WorkingHours"), workingH, "班值工作时数", driver);
		}
		if (effectiveWorkingH != null) {
			Assert.assertElementAttributeValue(By.id("WorkingHours"), workingH, "班值工作基数", driver);
		}
			
		if (shift != null) {
			Assert.assertDropDownDefaultSelected(By.id("shiftId"), shift, "计算工作小时数类型:", driver);		
		}
		if (workingDaya != null) {
			Assert.assertElementAttributeValue(By.id("WorkingHours"), workingH, "班值工作天数", driver);
		}
		if (timeScale != null) {
			Assert.assertElementAttributeValue(By.id("WorkingHours"), workingH, "班值工作小时计算刻度", driver);
		}
	}	
	
	public void saveShiftRecordSuccess() throws Exception {
		WebElement save_btn = find(By.xpath("//li[@id='settingSave']/a"));
		try {
			save_btn.click();
		} catch (Exception e1) {
			scrollTo(save_btn);
			save_btn.click();
		}
		WebDriverUtils.confirmSave(driver);
		try {
			closeAddEditForm();
		} catch (Exception e) {
		}
		WebDriverUtils.waitLoading(driver);
	}
	
	public void saveShiftRecordFail(String msg) throws Exception {		
		find(By.xpath("//li[@id='settingSave']/a")).click();
		String actualText = waitForVisible(By.id("showP")).getText().trim();
		Assert.isEqual(msg, actualText, "fail to save shift record");
	    waitForVisible(By.xpath("//button[normalize-space(text())='确定']")).click();
	}
	
	public void closeAddEditForm() throws Exception {
		find(By.xpath("//div[./span[text()='修改']]/button")).click();
		WebDriverUtils.waitLoading(driver);
	}
	
	public void deleteShiftRecord(String code) throws Exception {
		WebElement td2 = waitFor(By.xpath("//div[@id='details']//tr[./td[1][normalize-space(text())='" + code + "']]/td[2]"));
		try {
			td2.click();
		} catch (Exception e1) {
			scrollTo(td2);
			td2.click();
		}
		find(By.xpath("//li[@id='shiftDelete']/a")).click();
		String msg = waitForVisible(By.id("showP")).getText().trim();
		Assert.isContains(msg, "确认删除否?", "確定刪除消息");
		waitForVisible(By.xpath("//button[text()='确定']")).click();
		waitLoading();
		try {
			waitForVisible(By.xpath("//div[./div/p[text()='删除成功！']]/div/div/button")).click();
		} catch (Exception e) {
			Utils.waitABit(3000);
			waitForVisible(By.xpath("//div[./div/p[text()='删除成功！']]/div/div/button")).click();
		}
		WebDriverUtils.waitLoading(driver);	
		//click menu to refresh page
		find(By.xpath("//div[@id='Navbreadcrumb']//a[contains(text(),'考勤班值设定')]")).click();
		WebDriverUtils.waitLoading(driver);
	}
	
	public void deleteShiftRecordIfExist(String code) throws Exception {
		WebDriverUtils.waitLoading(driver);
		try {
			waitForShort(By.xpath("//div[@id='details']//tr[./td[1][normalize-space(text())='" + code + "']]"));
		} catch (Exception e) {
			return;
		}		
		deleteShiftRecord(code);
	}
	
	public void closeShiftRecordForm() throws Exception {
		find(By.xpath("//div[./span[normalize-space(text())='修改']]/button")).click();
		Utils.waitABit(1000);
	}
	
	private int come_go_line_no;
	private String comeGo_xpath = "//div[@id='D1']//tbody[contains(@data-bind,'Details')]//tr[./td/input[@type='checkbox']]";
	
	//点击“增加新数据”按钮
	public void clickAddComeGoLine() throws Exception {
		List<WebElement> come_go_lines = findAll(By.xpath(comeGo_xpath));
		come_go_line_no = come_go_lines.size();
		waitFor(By.xpath("//div[@id='D1']//button[contains(.,'增加新数据')]")).click();	
		Utils.waitABit(1000);
	}
	
	public void verifyComeGoLines() throws Exception {
		List<WebElement> come_go_lines_after_add = findAll(By.xpath(comeGo_xpath));
		Assert.isEqual(come_go_line_no, come_go_lines_after_add.size()-1);
		come_go_line_no = come_go_lines_after_add.size();
	}
	
	public void deleteLastComeGoLine() throws Exception {
		String comeGoCheckbox_xpath = "//div[@id='D1']//tbody[contains(@data-bind,'Details')]//tr/td/input[@type='checkbox']";
		List<WebElement> come_go_lines = findAll(By.xpath(comeGoCheckbox_xpath));
		checkChkRadio(come_go_lines.get(come_go_lines.size()-1), true);
		waitFor(By.xpath("//div[@id='D1']//button[contains(.,'删除选中项')]")).click();	
		Utils.waitABit(1000);
	}
	
	public void verifyComeGoDeleted() throws Exception {
		List<WebElement> come_go_lines_after_delete = findAll(By.xpath(comeGo_xpath));
		Assert.isEqual(come_go_line_no , come_go_lines_after_delete.size()+1);
		come_go_line_no = come_go_lines_after_delete.size();
	}
}
