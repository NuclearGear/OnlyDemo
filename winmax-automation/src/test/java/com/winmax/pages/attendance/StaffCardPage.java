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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import com.winmax.config.Const;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.Excel;
import com.winmax.utils.FileUtil;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class StaffCardPage extends  WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(StaffCardPage.class);	
	public StaffCardPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void quickSearchCard(String ks) {
		switchToPartialIframe();
		putInValue(waitFor(By.id("SearchCardContent")), ks);
		find(By.id("btnSearch")).click();
		Utils.waitABit(1000);
	}
	
	public void clickOnAddCard() {
		switchToPartialIframe();
		find(By.xpath("//div[@id='CardHoder']//a[contains(@data-bind,'Add')]")).click();
		waitForVisible(By.xpath("//td[@id='tdBlack']//button[contains(text(),'保存')]"));
	} 
	
	public void saveCardSuccess() {
		try {
			find(By.xpath("//*[@id='ui-datepicker-div']//button[text()='关闭']")).click();
			Utils.waitABit(1000);
		} catch (Exception e) {
			;
		}
		WebElement saveBtn = waitForVisible(By.xpath("//td[@id='tdBlack']//button[contains(text(),'保存')]"));
		scrollTo(saveBtn);
		saveBtn.click();
		WebDriverUtils.confirmSave(driver);
	} 
	
	//高级搜索持卡信息
	public void adanceSearchCardByStaffNo(String id) throws Exception {
		switchToPartialIframe();
		WebDriverUtils.advanceSearchById(id, driver);		
	}
	
	public void deleteCard(HashMap<String, String> data) {
		switchToPartialIframe();
		String trXpath = WebDriverUtils.getRowTrXpathByHashMap("//table[./tbody[contains(@data-bind,'CardlListInfo')]]", data, driver);
		waitForVisible(By.xpath(trXpath + "//td/i[contains(@class,'trash')]")).click();
		waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
		WebDriverUtils.confirmDelete(driver);
	} 
	
	public void deleteCardIfExist(HashMap<String, String> data) {
		switchToPartialIframe();
		String trXpath = WebDriverUtils.getRowTrXpathByHashMap("//table[./tbody[contains(@data-bind,'CardlListInfo')]]", data, driver);
		if (findAll(By.xpath(trXpath)).size()>0) {
			waitForVisible(By.xpath(trXpath + "//td/i[contains(@class,'trash')]")).click();
			waitForVisible(By.xpath("//button[contains(text(),'确定')]")).click();
			Utils.waitABit(2000);
	    	String text = waitForVisible(By.id("showP")).getText();
	    	if (text.contains("不能删除")) {
	    		Assert.fail("删除失败： " + text);
	    	}
			WebDriverUtils.confirmDelete(driver);
		}
	} 
	
	public void clickEditCardButton(HashMap<String, String> data) {
		switchToPartialIframe();
		String trXpath = WebDriverUtils.getRowTrXpathByHashMap("//table[./tbody[contains(@data-bind,'CardlListInfo')]]", data, driver);
		waitForVisible(By.xpath(trXpath + "//td/i[contains(@class,'pencil')]")).click();
	} 
	
	public void isCardExist(HashMap<String, String> data, boolean flag) {
		switchToPartialIframe();
		String trXpath = WebDriverUtils.getRowTrXpathByHashMap("//table[./tbody[contains(@data-bind,'CardlListInfo')]]", data, driver);
		if (flag) {
			Assert.isTrue(findAll(By.xpath(trXpath)).size()>0, "存在记录 " + data);
		} else {
			Assert.isTrue(findAll(By.xpath(trXpath)).size()==0, "不存在记录 " + data);
		}
	} 
	
	public void fillInCardInfo(HashMap<String, String> data) throws Exception {
		Set<String> keys = data.keySet();
		for (String key : keys) {
			String value = data.get(key);
			if (key.contains("日期")) {
				value = Utils.getDate(value);
			}
			By by = By.xpath("//td[@id='tdBlack']//td[contains(text(),'" + key + "')]/following-sibling::td[1]/*");
			if (key.contains("卡号")) {
				WebElement cardNo = find(By.xpath("//tr[contains(@id,'IsCardHidden')][contains(@style,'table')]//td[contains(text(),'" + key + "')]/following-sibling::td[1]/*"));
				cardNo.clear();
				cardNo.sendKeys(value);
				continue;
			} else if (key.contains("持卡类型")) {
				selectByVisibleText(By.id("AccountType"), value);
				continue;
			} else if (key.contains("刷卡进出")) {
				selectByVisibleText(By.id("CardIOType"), value);
				continue;
			} else if (key.contains("雇员")) {
				waitFor(By.xpath("//*[@id='SelectStaffNoShowWindow']/span")).click();
				WebElement input = waitForVisible(By.id("SelectStaffNosearch"));
				input.clear();
				input.sendKeys(value);
				waitLoading();
				waitForVisible(By.xpath("//*[@id='SelectStaffNogrid']/table//td[normalize-space(text())='" + value + "']")).click();
				Utils.waitABit(500);
				continue;
			} else if (waitForVisible(by).getTagName().equals("input")) {
				putInValue(waitForVisible(by), value);
				if (key.contains("效日期")) {
					try {
						waitForVisible(By.xpath("//*[@id='ui-datepicker-div']//button[text()='关闭']")).click();
						Utils.waitABit(1000);
					} catch (Exception e) {
						;
					}
				}
				continue;
			} else if (waitForVisible(by).getTagName().contains("textarea")) {
				find(by).clear();
				find(by).sendKeys(value);				
			}
		}		
	}

	
}
 