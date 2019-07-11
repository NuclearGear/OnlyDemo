package com.pajk.utils;

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
import com.pajk.config.Const;
 
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
    
    public static void scrollTo(WebElement element, WebDriver driver) {
    	JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(false);", element);
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
		LinkedHashMap<String, String> actualData = new LinkedHashMap<String, String>();		
		List<WebElement> headers = waitForMinimumNum(By.xpath(trRowXpath + "/../../thead/tr[1]"), 3, driver);
		List<WebElement> values = waitForMinimumNum(By.xpath(trRowXpath + "/td"), 3, driver);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		return actualData;
	}

	public static LinkedHashMap<String, String> getRowData(String trRowXpath, String tdRowXpath,int minNum,WebDriver driver) throws Exception {
		LinkedHashMap<String, String> actualData = new LinkedHashMap<String, String>();
		List<WebElement> headers = waitForMinimumNum(By.xpath(trRowXpath), minNum, driver);
		List<WebElement> values = waitForMinimumNum(By.xpath(tdRowXpath), minNum, driver);
		for (int i = 0; i < headers.size(); i++) {
			if (!headers.get(i).getText().trim().isEmpty()) {
				actualData.put(headers.get(i).getText().trim(), values.get(i).getText().trim());
			}
		}
		return actualData;
	}

	public static LinkedHashMap<String, String> getRowData(List<WebElement> headers, List<WebElement> values) throws Exception {
		LinkedHashMap<String, String> actualData = new LinkedHashMap<String, String>();
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
	
	public static void waitForNotVisibleLong(final By by, WebDriver driver) {
        waitForNotVisible(by, 3*Const.TIMEOUT, driver);
    }
	
	public static WebElement waitForVisibleLong(final By by, WebDriver driver) {
        return waitForVisible(by, Const.TIMEOUT*3, driver);
    }

	
	public static String getIndex(String tableXpath, String column, WebDriver driver) {
		List<WebElement> headList = driver.findElements(By.xpath(tableXpath + "/thead/td"));
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
