package com.pajk.steps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Function;
import com.pajk.config.Const;
import com.pajk.utils.Utils;

public class WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(WebDriverSteps.class);
		
	protected static WebDriver driver;

	public static void setDriver(WebDriver driver) {
		WebDriverSteps.driver = driver;
	}

    public static void browserForward(){
        driver.navigate().forward();
    }

    public static void browserBack(){
        driver.navigate().back();
    }

    public static void browserRefresh(){
	    driver.navigate().refresh();
    }

    public static String getBrowserUrl(){
        String url=driver.getCurrentUrl();
        return url;
    }

    public static String getBrowserTitle(){
        String title=driver.getTitle();
        return title;
    }

	protected WebElement find(By by) {
	    return driver.findElement(by);
	}
	
	protected List<WebElement> findAll(By by) {
        List<WebElement> elements = driver.findElements(by);      
        return elements;
    }
	
	protected WebElement find(WebElement parent_ele, By by) {
		return parent_ele.findElement(by);
	}
	
	protected List<WebElement> findAll(WebElement parent_ele, By by) {
        List<WebElement> elements = parent_ele.findElements(by);      
        return elements;
    }
	
	protected void putInValue(WebElement el, String value) {
	    if(!el.getAttribute("defaultValue").equals(value)){
            highLight(el);
            el.clear();
            el.sendKeys(value);
        }
	}
	
	protected void putInValue(By by, String value) {
		WebElement ele = waitFor(by);
        if(!ele.getAttribute("defaultValue").equals(value)){
            highLight(ele);
		    ele.clear();
		    ele.sendKeys(value);
        }
	}
	
	protected void sendKeysByAction(By by, String keys) {
		WebElement ele = waitForShort(by);
		Actions action = new Actions(driver);  
		action.click(ele).sendKeys(keys);
	}
	
	protected WebElement waitFor(final WebElement parent_ele, final By by, long timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class); 
		WebElement e = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
            	  return parent_ele.findElement(by);	         
		}
		});
		return e;
    }
	
	protected WebElement waitFor(final By by, long timeout) {
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
	
	protected Alert switchToAlert() {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NullPointerException.class); 
		Alert alert = wait.until(new Function<WebDriver, Alert>() {
			public Alert apply(WebDriver driver) {
            	  Alert a = driver.switchTo().alert();            	  
            	  return a;            	  
		}
		});
		return alert;
    }
	
	protected String dismissAlert() {
		 Alert a = switchToAlert();
         String text = a.getText().trim();
         a.dismiss();
         return text;            	  
	}
	
	protected String acceptAlert() {
		Alert a = switchToAlert();
        String text = a.getText().trim();
        a.accept();
        return text;            	  
	}
	
	protected List<WebElement> waitForMinimumNum(final By by, final int minNum, WebDriver driver) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(WebDriverSteps.driver)
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
		return WebDriverSteps.driver.findElements(by);
    }	
	
	protected WebElement waitFor(final By by) {
        return waitFor(by, Const.TIMEOUT);
    }
	
	protected WebElement waitFor(final WebElement parent_ele, final By by) {
        return waitFor(parent_ele, by, Const.TIMEOUT);
    }
	
	protected WebElement waitForLong(final By by) {
        return waitFor(by, 2*Const.TIMEOUT);
    }
    
    protected WebElement waitForShort(final By by) {
        return waitFor(by, Const.SHORT_TIMEOUT);
    }
    
	protected WebElement waitForVisible(final By by) {
        return waitForVisible(by, Const.TIMEOUT);
    }
	
	protected WebElement waitForVisibleLong(final By by) {
        return waitForVisible(by, Const.TIMEOUT*3);
    }
    
    protected WebElement waitForVisibleShort(final By by) {
        return waitForVisible(by, Const.SHORT_TIMEOUT);
    }
    
    protected WebElement waitForVisible(final By by, long timeout) {
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

	protected boolean waitForVisibleOrNot(final By by, long timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class);
		try{
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
            if (e!=null){
                return true;
            }else {
                return false;
            }
		}
		catch (Exception e){
		    return false;
        }
	}

    protected void waitForNotVisibleShort(final By by) {
        waitForNotVisible(by, Const.SHORT_TIMEOUT);
    }
    
    protected void waitForNotVisible(final By by) {
        waitForNotVisible(by, Const.TIMEOUT);
    }
    
    protected void waitForNotVisibleLong(final By by) {
        waitForNotVisible(by, 3*Const.TIMEOUT);
    }
    
    protected void waitForNotVisible(final By by, long timeout) {
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
    
    protected WebElement waitForNotPresent(final By by) {
        return waitFor(by, Const.TIMEOUT);
    }
    
    protected WebElement waitForNotPresentShort(final By by) {
        return waitFor(by, Const.SHORT_TIMEOUT);
    }
    
    protected void waitForNotPresent(final By by, long timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(timeout, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS);
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
                    driver.findElement(by);
                    return false;
                } catch (Exception e) {
                    return true;
                }            	        
		}
		});
    }

	protected void _switchToFrame(String frameName) {
        driver.switchTo().defaultContent();
        waitFor(By.name(frameName));
        driver.switchTo().frame(frameName);
    }
  
    protected void switchToParentWindow() {
        driver.switchTo().window(driver.getWindowHandles().iterator().next());
   
    }

    protected void switchToOpenerWindow() {
        // for situations when window closes with delay, and we are trying to switch to it instead of the window from which it was opened
        try {
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        } catch (NoSuchWindowException e) {
            driver.switchTo().window(driver.getWindowHandles().iterator().next());
        }
    }
    
    protected void closeCurrentWindow() {
        driver.close();
    }

    /**
     * click check box or radio button
     * @param obj
     * @param check  true or false
     */
    protected void checkChkRadio(Object obj, Boolean check) {
    	WebElement checkbox = null;
    	if(obj instanceof By) {
    		checkbox = find((By)obj);
    	}else{
    		checkbox = (WebElement) obj;
    	} 
    	if(((!checkbox.isSelected()) && check) || ((!check) && (checkbox.isSelected()))) {
    		checkbox.click();
    	}
	}

	protected void clickWithActions(final WebElement element) {
        highLight(element);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}


    protected void highLight(WebElement element) {
        scrollTo(element);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("element = arguments[0];" +
                "original_style = element.getAttribute('style');" +
                "element.setAttribute('style', original_style + \";" +
                "background: #97CBFF; border: 2px solid red;\");" +
                "setTimeout(function(){element.setAttribute('style', original_style);}, 1000);", element);
    }

    protected void clickWithJS(final WebElement element) {
        highLight(element);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected Object executeJS(String code) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        return executor.executeScript(code);
    }

    protected Object executeJS(String code, WebElement el) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;       
        return executor.executeScript(code, el);
    }

    protected void removeReadOnly(WebElement el) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].removeAttribute('readonly')", el);
    }

    protected void setDisplayBlock(WebElement el) {
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].style=arguments[1]",el,"display: block;");
    }

    protected void setValue(WebElement el,String value) {
        JavascriptExecutor  executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].value=\""+value+"\"",el);
    }
    
    protected List<String> getListElementsText(List<WebElement> listElements) {
      	 List<String> textList = new ArrayList<String>();
      	 for (WebElement ele : listElements) {
      	     try{
      	         if(ele.getAttribute("innerText").length()!=0){

      	             textList.add(ele.getAttribute("innerText").trim().replace("\n", " "));
      	         }
      	         else if (ele.getText().length()!=0){

      	             textList.add(ele.getText().trim().replace("\n", " "));
      	         }
      	         else if (ele.getAttribute("outerText").length()!=0){

      	             textList.add(ele.getAttribute("outerText").trim().replace("\n", " "));
      	         }
      	         else if (ele.getAttribute("defaultValue").length()!=0){

      	             textList.add(ele.getAttribute("defaultValue").trim().replace("\n", " "));
      	         }
      	 }catch (Exception e){
                continue;
             }
      	 }
      	 return textList;
      }
    
    protected void mouseHoverJScript(WebElement HoverElement) {
		try {
				String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');"
						+ "evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
				((JavascriptExecutor) driver).executeScript(mouseOverScript,
						HoverElement);
		 
		} catch (Exception e) {
			logger.error("Failed to mouse hover to element", e); 
		}
	}
    
    protected String waitForAttributeChanged(final By by, final String attr, final String value) {
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class); 
    	return wait.until(new Function<WebDriver, String>() {
			public String apply(WebDriver driver) {
                    try {
                        WebElement element = driver.findElement(by);
                        String cValue = element.getAttribute(attr);
                        logger.debug(cValue + " vs " + value);
                        if (!cValue.equals(value)) {
                            return cValue;
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        return null;
                    }
                }
            });       
    } 
    
    protected void waitForAttribute(final By by,  final String attr, final String value) {
    	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
				.pollingEvery(Const.POLL, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementNotVisibleException.class); 
    	wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
                try {
                	 WebElement element = driver.findElement(by);
                     String cValue = element.getAttribute(attr);
                    if (value == null && cValue == null) {
                        return true;
                    } else if (value != null){
                    	if (value.equals(cValue)) {
                    		return true;
                    	} else {
                    		return false;
                    	}	
                	} else {
                        return false;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });
    }

    protected void waitForWindows(final int expectedWindowsNumber) {
        try {
        	Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
    				.withTimeout(Const.TIMEOUT, TimeUnit.SECONDS)
    				.pollingEvery(Const.POLL, TimeUnit.SECONDS);
        	wait.until(new Function<WebDriver, Boolean>() {
        		public Boolean apply(WebDriver driver) {
                    int windowsCount = driver.getWindowHandles().size();
                    if (windowsCount == expectedWindowsNumber) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Wrong number of windows: [" + driver.getWindowHandles().size() + "] when expected [" + expectedWindowsNumber + "]", e);
        }
    }
    
    protected void openNewWindowWith(final WebElement opener) throws Exception {
        Set<String> wndsBefore = driver.getWindowHandles();
        opener.click();
        waitForWindows(wndsBefore.size() + 1);
        Set<String> wnds = driver.getWindowHandles();
        wnds.removeAll(wndsBefore);
        if (wnds.size() != 1) {
            throw new RuntimeException("Wrong number of newly opened windows: " + wnds.size());
        }
        driver.switchTo().window(wnds.iterator().next());
    }
    
    public void selectByVisibleText(By by, String text) {
    	Select sel = new Select(waitForShort(by));
    	sel.selectByVisibleText(text);
    }
    
    public void scrollTo(WebElement element) {
    	JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(false);", element);
        //executor.executeScript("arguments[0].scrollIntoView();", element);
    }

    public void scrollToTop() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0,0)");
    }

	public void scrollToBase() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollTo(0,document.body.scrollHeight)");
	}

    public String waitForSomeText(final By by, final String text) {
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
    
    public void switchToPartialIframe(){
		driver.switchTo().defaultContent();
   	 	String frameId = "_PartialIframe";
   	 	waitFor(By.id(frameId));
   	 	driver.switchTo().frame(frameId);
    }
    
    public void switchToIframe(String frameId){
		driver.switchTo().defaultContent();
     	 	waitFor(By.id(frameId));
   	 	driver.switchTo().frame(frameId);
    }

    //日期控件
    public void inputDate(String path,String startDate,String endDate) throws Exception{
        WebElement element =waitFor(By.xpath(path));
        String startTime = startDate;
        String endTime = endDate;
        if(startDate.contains("-")){
            Date date1 = new SimpleDateFormat("yyyy-M-d").parse(startDate);
            startDate = new SimpleDateFormat("yyyy年M月d日").format(date1);
            Date date2 = new SimpleDateFormat("yyyy-M-d").parse(endDate);
            endDate = new SimpleDateFormat("yyyy年M月d日").format(date2);
        }
        try{
            WebElement element1 =waitForShort(By.xpath(path+"/..//i[@class='anticon anticon-close-circle ant-calendar-picker-clear']"));
            clickWithActions(element1);
        }catch (Exception e){
            //pass
        }
        Utils.waitABit(500);
        clickWithJS(waitFor(By.xpath(path)));
/*        WebElement element1 = waitFor(By.xpath("//td[@title='"+startDate+"'][@role='gridcell']"));
        WebElement element2 = waitFor(By.xpath("//td[@title='"+endDate+"'][@role='gridcell']"));*/

        if(waitForVisibleOrNot(By.xpath("//td[@title='"+startDate+"'][@role='gridcell']"),1)&&waitForVisibleOrNot(By.xpath("//td[@title='"+endDate+"'][@role='gridcell']"),1)){
            WebElement element1 = waitFor(By.xpath("//td[@title='"+startDate+"'][@role='gridcell']"));
            WebElement element2 = waitFor(By.xpath("//td[@title='"+endDate+"'][@role='gridcell']"));

            clickWithJS(element1);
            Utils.waitABit(1500);
            clickWithJS(element2);
        }
        else{
            putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"),startTime);
            clickWithJS(waitFor(By.xpath("//td[@title='"+startDate+"'][@role='gridcell']")));
            Utils.waitABit(1000);
            clickWithJS(element);
            putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='结束日期']"),endTime);
            clickWithJS(waitFor(By.xpath("//td[@title='"+startDate+"'][@role='gridcell']")));
            Utils.waitABit(1000);
            clickWithJS(waitFor(By.xpath("//td[@title='"+endDate+"'][@role='gridcell']")));
        }
        Utils.waitABit(1000);
    }

    public static void main(String[] args) throws Exception {
        //WebDriverSteps test = new WebDriverSteps();
    }

}
