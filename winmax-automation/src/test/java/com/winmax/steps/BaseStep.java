package com.winmax.steps;

import java.io.File;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.winmax.config.Config;
import com.winmax.config.Const;
import com.winmax.driver.DriverFactory;
import com.winmax.utils.TestlinkUtils;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseStep {
	private static final Logger logger = LogManager.getLogger(BaseStep.class);
	
	protected static WebDriver driver;
	
	public static void setDriver(WebDriver driver) {
		BaseStep.driver = driver;
	}
	
	@Before
	public void beforeScenario() throws Exception {
		//clear screenshot folder
		String screenshot = Const.SCREENSHOTS_SUBFOLDER_PARAM;
		Utils.deleteDir(new File(screenshot));
	    
		if (null==driver) {
			driver = DriverFactory.startDriver();	
			WebDriverSteps.setDriver(driver);
		}
	}
	
    @After
	public void afterScenario(Scenario scenario) throws Exception {
    	try {
			driver.switchTo().alert().dismiss();
		} catch (Exception e1) {
		}
    	//testlink上更新执行结果
    	if (Config.get().updateTestLink()) {
	    	ArrayList<String> tcList = Utils.getTestCaseIDs(scenario);
	    	if (scenario.isFailed()) {
	    		TestlinkUtils.addTCResult(tcList, false);
	    	} else {	    	
	    		TestlinkUtils.addTCResult(tcList, true);
	    	}
    	}
    	//截图
    	Object[] array = scenario.getSourceTagNames().toArray();
    	String name = null;
    	for (int i=0;i<array.length;i++) {
    		String tag = array[i].toString();
    		if(tag.startsWith("@E")) {
    			name = tag.replace("@", "") + "_;";
    			break;
    		}
    	}
    	if (name.endsWith("_;")) {
    		name = name.substring(0, name.length()-2);
    	}
		WebDriverUtils.takeScreenshot(name, driver);
		
		//关闭多余窗口
    	if (scenario.isFailed()) {
    		final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    		scenario.embed(screenshot, "image/png");
    		//close new open window
    		if (driver.getWindowHandles().size()> 1) {
    			int total = driver.getWindowHandles().size();
	    		for (int i=0; i<total-1; i++) {
		    		try {
						driver.close();
						driver.switchTo().window(driver.getWindowHandles().iterator().next());
					} catch (Exception e) {
					}
	    		}
    		}
    	} else { 
    		
    	}    	
	}  
}
