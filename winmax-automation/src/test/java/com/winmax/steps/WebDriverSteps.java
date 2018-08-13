package com.winmax.steps;

import java.util.ArrayList;
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
import com.winmax.config.Const;
import com.winmax.utils.PropertyUtil;
import com.winmax.utils.Utils;

public class WebDriverSteps {
	
	private static final Logger logger = LogManager.getLogger(WebDriverSteps.class);
		
	protected static WebDriver driver;

	public static void setDriver(WebDriver driver) {
		WebDriverSteps.driver = driver;
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
		el.clear();
		el.sendKeys(value);
	}
	
	protected void putInValue(By by, String value) {
		WebElement ele = waitForShort(by);
		ele.clear();
		ele.sendKeys(value);
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
	
	protected List<WebElement> waitForMinimumNum(final By by, final int minNum) {
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
     * @param by
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
    
    protected void clickWithJS(final WebElement element) {
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
    
    protected List<String> getListElementsText(List<WebElement> listElements) {
      	 List<String> textList = new ArrayList<String>();
      	 for (WebElement ele : listElements) {
      		 textList.add(ele.getAttribute("innerText").trim().replace("\n", " "));
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
        waitLoading();
    }
    
    public void selectByVisibleText(By by, String text) {
    	Select sel = new Select(waitForShort(by));
    	sel.selectByVisibleText(text);
    }
    
    public void scrollTo(WebElement element) {
    	JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(false);", element);
    }
    
    public void waitLoading() throws Exception {
		try {
			waitForNotVisible(By.xpath("//div[@id='loadingDiv']//img[contains(@src, 'load')]"));
		} catch (Exception e) {
			Utils.waitABit(5000);
			waitForNotVisible(By.xpath("//div[@id='loadingDiv']//img[contains(@src, 'load')]"));
		}
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
}
