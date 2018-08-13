package com.winmax.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.Function;
import com.winmax.config.Const;
 
public class WebDriverUtils {
	
	private static final Logger logger = LogManager.getLogger(WebDriverUtils.class);
	
	public static WebElement waitFor(final By by, WebDriver driver, long timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class); 
		WebElement e = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
            	  return driver.findElement(by);	         
		}
		});
		return e;
    }	
	
    public static WebElement waitFor(final By by, WebDriver driver) {
        return waitFor(by, driver, Const.TIMEOUT);
    }
    
    public static WebElement waitForShort(final By by, WebDriver driver) {
        return waitFor(by, driver, Const.SHORT_TIMEOUT);
    }
    
    public static void waitForNotVisible(final By by, WebDriver driver) {
        waitForNotVisible(by, Const.TIMEOUT, driver);
    }
    
    public static void waitForNotVisible(final By by, long timeout, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
                    WebElement element = driver.findElement(by);
                    if (element.isDisplayed()) {
                        return false;
                    } else {
                        return true;
                    }
                } catch (Exception e) {
                    return true;
                }            	        
		}
		});
    }
    
    public static WebElement waitForVisible(final By by, long timeout, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class); 
		WebElement e = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				try {
                    WebElement element = driver.findElement(by);
                    if (element.isDisplayed()) {
                        return element;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    return null;
                }         
		}
		});
		return e;
    }
    
    public static void inputDate(final By inputBy, String dateStr, WebDriver driver) {
    	waitFor(inputBy, driver).clear();
    	waitFor(inputBy, driver).sendKeys(dateStr);
    	try {
			waitForShort(By.xpath("//div[./div/p[contains(text(),'日期不能为空')]]//button[text()='确定']"), driver).click();
		} catch (Exception e) {
			;
		}
    }
    
    public static WebElement waitForVisible(final By by, WebDriver driver) {
        return waitForVisible(by, Const.TIMEOUT, driver);
    }
    
    public static WebElement waitForVisibleShort(final By by, WebDriver driver) {
        return waitForVisible(by, Const.SHORT_TIMEOUT, driver);
    }
    
    public static void acceptAllConfirmations(WebDriver driver) {
      WebDriverUtils.executeJS("window.confirm = function () {return true;};", driver);
  }

  public static void acceptAllAlerts(WebDriver driver) {
      WebDriverUtils.executeJS("window.alert = function () {return true;};", driver);
  }
    
    public static WebElement find(By by, WebDriver driver) {
		return driver.findElement(by);
	}
    
    public static void fireHTMLEvent(WebElement el, String event, WebDriver driver) {
        String code = "if (document.createEvent) {\n" +
                "var simulateEvent = document.createEvent('HTMLEvents');\n" +
                "simulateEvent.initEvent('" + event + "',true,true,document.defaultView,0,0,0,0,0,false,false,false,0,null,null);\n" +
                "arguments[0].dispatchEvent(simulateEvent);\n" +
                "} else {\n" +
                "var evObj = document.createEventObject();\n" +
                "arguments[0].fireEvent('on" + event + "', evObj);\n" +
                "}";
        executeJS(code, el, driver);
    }
    
    public static Object executeJS(String code, WebElement el, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(code, el);
    }
    
    public static Object executeJS(String code, WebDriver driver) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(code);
    }
    
    public static void inputStaffNoWhenSearch(String parent_xpath, String no, WebDriver driver) throws Exception {
    	String searchDiv_xpath = parent_xpath + "//div[contains(@id, 'aff')][@data-role='combogrid']";    	
    	waitForVisible(By.xpath(searchDiv_xpath + "//span[contains(@class,'orange-drop')]"), driver).click();
		
		String input_xpath = "//div[contains(@id, 'earch')]/input[@placeholder='查询']";
		WebElement input;
		try {
			input = waitForVisibleShort(By.xpath(input_xpath), driver);
		} catch (Exception e1) {
			try {
				input_xpath = "//div[contains(@id, 'aff')]/input[@placeholder='查询']";
				input = waitForVisibleShort(By.xpath(input_xpath), driver);
			} catch (Exception e) {
				input_xpath = "//div[contains(@style,'block')]//input[@placeholder='查询']";
				input = waitForVisibleShort(By.xpath(input_xpath), driver);
			}
		}
		input.clear();
		input.sendKeys(no);
		waitLoading(driver);
		String grid_xpath = input_xpath + "/..//div[contains(@id,'grid')]"; 
		WebElement item = null;		
		try {
			item = waitForVisible(By.xpath(grid_xpath + "//td[normalize-space(text())='" + no + "']"), driver);
		} catch (StaleElementReferenceException e) {
			item = find(By.xpath(grid_xpath + "//td[normalize-space(text())='" + no + "']"), driver);
		}
		item.click();
		Utils.waitABit(1000);
    }
    
    public static void inputStaffNoWhenSearch(String no, WebDriver driver) throws Exception {
    	String searchDiv_xpath = "//div[contains(@id, 'aff')][@data-role='combogrid']";    	
		waitForShort(By.xpath(searchDiv_xpath + "//span[contains(@class,'orange-drop')]"), driver).click();
		
		String input_xpath = "//div[contains(@id, 'earch')]/input[@placeholder='查询']";
		WebElement input;
		try {
			input = waitForVisibleShort(By.xpath(input_xpath), driver);
		} catch (Exception e1) {
			input_xpath = "//div[contains(@id, 'aff')]/input[@placeholder='查询']";
			input = waitForVisibleShort(By.xpath(input_xpath), driver);
		}		
		input.clear();
		input.sendKeys(no);
		waitLoading(driver);
		String grid_xpath = input_xpath + "/..//div[contains(@id,'grid')]";
		WebElement item = null;		
		try {
			item = waitForVisibleShort(By.xpath(grid_xpath + "//td[normalize-space(text())='" + no + "']"), driver);
		} catch (StaleElementReferenceException e) {
			item = find(By.xpath(grid_xpath + "//td[normalize-space(text())='" + no + "']"), driver);
		}
		item.click();
		Utils.waitABit(1000);
    }
    
    public static void searchByStaffNo(String no, WebDriver driver) throws Exception {
    	inputStaffNoWhenSearch(no, driver);
		try {
			find(By.id("searchBtn"), driver).click();
		} catch (Exception e) {
			find(By.xpath("//*[text()='查询']"), driver).click();
		}
		Utils.waitABit(2000);
    }
    
    public static String waitForSomeText(final By by, final String text, WebDriver driver) {
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class); 
    	return wait.until(new Function<WebDriver, String>() {
			public String apply(WebDriver driver) {
				try {
					WebElement element = driver.findElement(by);
					String txt = element.getText().trim();
					if (txt.contains(text)) {
						return txt;
					} else {
						return null;
					}
				} catch (Exception e) {
					return null;
                    }
                }
			});   
    }
    
    public static void confirmSave(WebDriver driver) {
    	Utils.waitABit(2000);
    	waitForSomeText(By.id("showP"), "成功", driver);    	
		try {
			waitForVisible(By.xpath("//div[contains(@style,'block')]//button[normalize-space(text())='确定']"), driver).click();
		} catch (Exception e) {
			waitForVisible(By.xpath("//div[.//div/p[contains(text(),'成功')]]//button[contains(@class,'close')]"), driver).click();
		}		
		Utils.waitABit(500);
    }
    
    public static void confirmDelete(WebDriver driver) {
    	confirmSave(driver);
    }
    
    public static void confirmActionSuccess(WebDriver driver) {
    	confirmSave(driver);
    }
    
    public static void scrollTo(WebElement element, WebDriver driver) {
    	JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(false);", element);
    }
    /**
	 * 高级筛选： 通过人事字段-员工编号
	 * @param id
	 * @throws Exception
	 */
	public static void advanceSearchById(String id, WebDriver driver) throws Exception {		
		WebElement advanceSearch = waitFor(By.id("btnSenior"), driver);
		scrollTo(advanceSearch, driver);
		advanceSearch.click();
		waitLoading(driver);
		// click on 人事字段
		WebElement personnel_link = waitFor(By.xpath("//li[@id='liPersonnel']/a/span"), driver);
		if (!personnel_link.getAttribute("class").contains("active")) {
			scrollTo(personnel_link, driver);
			personnel_link.click();
		}
		WebElement staffNo_input = waitFor(By.id("dvStaffdisplay"), driver);
		scrollTo(staffNo_input, driver);
		staffNo_input.clear();
		staffNo_input.sendKeys(id);
		WebElement btn = waitFor(By.xpath("//div[@id='dvConfirmSearch']/span"), driver);
		scrollTo(btn, driver);
		btn.click();
		waitLoading(driver);
	}
	
	/**
	 * 高级筛选，薪资帐套【薪资1、2】
	 * @param conditions
	 * @param driver
	 * @throws Exception
	 */
	public static void advanceSearchPayRollConditions(String conditions, WebDriver driver) throws Exception {		
		waitFor(By.id("btnSenior"), driver).click();
		waitLoading(driver);
		// click on 模糊字段
		WebElement personnel_link = waitFor(By.xpath("//li[@id='liModule']/a/span"), driver);
		personnel_link.click();
		WebElement moreInfo = waitFor(By.xpath("//div[@id='dvadvanced3']/div/i"),driver);
		moreInfo.click();
		WebElement conditions_input = waitFor(By.xpath(".//dl[.//dt[normalize-space(text())='薪资帐套:']]//div//dd//span[normalize-space(text())='" + conditions + "']//preceding-sibling::*[1]"), driver);
		conditions_input.click();
		WebElement btn = waitFor(By.xpath("//div[@id='dvConfirmSearch']/span"), driver);
		scrollTo(btn, driver);
		WebElement sure = waitFor(By.xpath(".//div[@id='dvConfirmSearch']/span"),driver);
		sure.click();
		waitLoading(driver);
	}
	
	
	public static void waitLoading(WebDriver driver) throws Exception {
		try {
			waitForNotVisible(By.xpath("//div[@id='loadingDiv']//img[contains(@src, 'load')]"), driver);
		} catch (Exception e) {
			waitForNotVisible(By.xpath("//div[@id='loadingDiv']//img[contains(@src, 'load')]"), driver);
		}
	}
	
	public static void takeScreenshot(String fileName, WebDriver driver) throws Exception {
		String screenshotFloder = Const.SCREENSHOTS_SUBFOLDER_PARAM;
		if (!(new File(screenshotFloder).isDirectory())) {  // 判断是否存在该目录
			new File(screenshotFloder).mkdir();             // 如果不存在则新建一个目录
		}
		try {
			File source_file = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE); 
			FileUtils.copyFile(source_file, new File(screenshotFloder + File.separator + fileName + ".png"));   
		} catch (IOException e) {
	        logger.info("Failed to take screenshot.");
		}
	}

    /**
     * 将滚动条滚到适合的位置  
     * @param driver
     * @param height
     */
    public static void setScroll(WebDriver driver,int height){  
        try {  
            String setscroll = "document.documentElement.scrollTop=" + height;  
              
            JavascriptExecutor jse=(JavascriptExecutor) driver;  
            jse.executeScript(setscroll);  
        } catch (Exception e) {  
            System.out.println("Fail to set the scroll.");  
       }             
	}
    
    public static List<WebElement> waitForMinimumNum(final By by, final int minNum, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					List<WebElement> list = driver.findElements(by);
					if (list.size() >= minNum) {
						return true;
					} else {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
		});		
		return driver.findElements(by);
    }	

    /**
     * 
     * @param trRowXpath: 目标数据的tr的xpath
     * @param driver
     * @return 这行数据的hashmap
     * @throws Exception
     */
	public static LinkedHashMap<String, String> getRowData(String trRowXpath, WebDriver driver) throws Exception {
		waitLoading(driver);
		LinkedHashMap<String, String> actualData = new LinkedHashMap<String, String>();		
		List<WebElement> headers = waitForMinimumNum(By.xpath(trRowXpath + "/../../thead/tr/td"), 3, driver);		
		List<WebElement> values = waitForMinimumNum(By.xpath(trRowXpath + "/td"), 3, driver);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		return actualData;
	}
	
	public static String waitForDownloadFile(int currentNo) throws Exception {
		for (int i=0;i<3;i++) {
			int newTotal = FileUtil.countDownloadFolderFileNo();
			if(newTotal > currentNo) {
				if (FileUtil.isDownloadFileExist(FileUtil.getLatestDownloadFile())) {
					break;
				}
			}
			Utils.waitABit(1000);
		}
		return FileUtil.getLatestDownloadFile();
	}
	
	public static void waitForDownloadFileByName(String fileName) throws Exception {
		for (int i=0;i<3;i++) {			 
			if (FileUtil.isDownloadFileExist(fileName)) {
				break;
			}
			Utils.waitABit(1000);
		}
	}
	
	public static Alert switchToAlert(WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NullPointerException.class)
				.ignoring(NoAlertPresentException.class); 
		Alert alert = wait.until(new Function<WebDriver, Alert>() {
			public Alert apply(WebDriver driver) {
            	  Alert a = driver.switchTo().alert();            	  
            	  return a;            	  
		}
		});
		return alert;
    }
	
	public static void importFile(String fileName, WebDriver driver) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		waitFor(By.id("file0"), driver).sendKeys(filePath);		 
		waitFor(By.xpath("//div/span[contains(text(),'上传')]"), driver).click(); 
		Alert alert = switchToAlert(driver);		
        String alertText = alert.getText().trim();
        alert.dismiss();
		Assert.isEqual("上传文件成功", alertText);
	}
	
	public static void waitForNotVisibleLong(final By by, WebDriver driver) {
        waitForNotVisible(by, 3*Const.TIMEOUT, driver);
    }
	
	public static WebElement waitForVisibleLong(final By by, WebDriver driver) {
        return waitForVisible(by, Const.TIMEOUT*3, driver);
    }    
    
	public static void verifyProcessImportFile(WebDriver driver) throws Exception {
		find(By.xpath("//div[@id='DataImport']/span[contains(text(),'验证')]"), driver).click();
		waitForVisible(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"), driver).click();
		Utils.waitABit(3000);
		waitForNotVisibleLong(By.xpath("//div[@id='DataImport']/span[contains(text(),'处理')]"), driver);
		String actualMsg = waitForVisibleLong(By.id("showP"), driver).getText().trim();
		if (actualMsg.isEmpty()) {
			actualMsg = find(By.xpath("//*[@id='DataImport']//div[contains(@data-bind,'Message')]/p"), driver).getText().trim();
		}
		Assert.isContains(actualMsg, "导入成功", "成功导入excel信息不正确： " + actualMsg);
		try {
			find(By.xpath("//button[text()='确定']"), driver).click();
		} catch (Exception e) {
			find(By.xpath("//div[./span[text()='文件导入']]/button[contains(@class,'close')]"), driver).click();
		}
	}
	
	public static void importParyRollFile(String fileName, WebDriver driver) throws Exception {
		String filePath = Utils.getImportExcelFilePath(fileName);
		waitFor(By.id("file0"), driver).sendKeys(filePath);
		waitFor(By.xpath("//div/span[contains(text(),'上传')]"), driver).click(); 
	}
	
	public static String getIndex(String tableXpath, String column, WebDriver driver) {
		List<WebElement> headList = driver.findElements(By.xpath(tableXpath + "/thead//td"));
		for (int i=0;i<headList.size();i++) {
			if (headList.get(i).getAttribute("innerText").trim().equals(column)) {
				return String.valueOf(i+1);
			}
		}
		throw new RuntimeException("找不到列名： " + column);
	}
	
	public static String getRowTrXpathByHashMap(String tableXpath, HashMap<String, String> data, WebDriver driver) {
		Set<String> keys = data.keySet();
		String bodyTrXpath = tableXpath + "/tbody/tr";
		String rowXpath = bodyTrXpath;
		for (String k : keys) {
			String index = getIndex(tableXpath, k, driver);
			rowXpath = rowXpath + "[./td[" + index + "][normalize-space(.)='" + data.get(k) + "']]";
		}	
		
		return rowXpath;		
	}
}
