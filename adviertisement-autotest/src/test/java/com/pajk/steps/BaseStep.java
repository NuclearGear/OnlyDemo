package com.pajk.steps;

import java.io.File;
import java.util.*;

import com.pajk.db.DB_yacht;
import com.pajk.suites.BaseSuiteTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.pajk.config.Const;
import com.pajk.driver.DriverFactory;
import com.pajk.utils.Utils;
import com.pajk.utils.WebDriverUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class BaseStep {
	private static final Logger logger = LogManager.getLogger(BaseStep.class);
	
	protected static WebDriver driver;
	
	public static void setDriver(WebDriver driver) {
		BaseStep.driver = driver;
	}

    public static Set<String> testCases = new TreeSet<String>();

    public static Set<String> succeededTestCases = new TreeSet<String>();

    public static Set<String> failedTestCases = new TreeSet<String>();

    public static Set<String> failedScenario = new TreeSet<String>();


    @Before
	public void beforeScenario(Scenario scenario) throws Exception {
		//clear screenshot folder
		//String screenshot = Const.SCREENSHOTS_SUBFOLDER_PARAM;
		//Utils.deleteDir(new File(screenshot));
		logger.info("\n------------ Execute Test Case: "+scenario.getName()+" ------------");

	    if(scenario.getSourceTagNames().contains("@upload")&&BaseSuiteTest.BrowserHeadless){
            driver.quit();
            Const.setBrowserHeadless(false);
            driver = DriverFactory.startDriver();
            WebDriverSteps.setDriver(driver);
            BaseSuiteTest.setDriver(driver);
            }
        else if(BaseSuiteTest.BrowserHeadless!=Const.getBrowserHeadless()){
            driver.quit();
            Const.setBrowserHeadless(BaseSuiteTest.BrowserHeadless);
            driver = DriverFactory.startDriver();
            WebDriverSteps.setDriver(driver);
            BaseSuiteTest.setDriver(driver);
        }
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
/*    	if (Config.get().updateTestLink()) {
	    	ArrayList<String> tcList = Utils.getTestCaseIDs(scenario);
	    	if (scenario.isFailed()) {
	    		TestlinkUtils.addTCResult(tcList, false);
	    	} else {
	    		TestlinkUtils.addTCResult(tcList, true);
	    	}
    	}*/

        //统计测试用例运行结果
        Collection<String> tagsList = scenario.getSourceTagNames();
        for(String tags:tagsList) {
            if (tags.contains("@E-")) {
                testCases.add(tags);
                if (scenario.isFailed()) {
                    failedTestCases.add(tags);
                    failedScenario.add(scenario.getName());
                } else {
                    succeededTestCases.add(tags);
                }
            }
        }

		//关闭多余窗口
    	if (scenario.isFailed()) {
            logger.info("Test Case Failed");
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
			//driver.manage().window().maximize();
			WebDriverUtils.takeScreenshot(name, driver);
            try{
                final byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
                scenario.embed(screenshot, "image/png");
            }catch (Exception e){

            }

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
            logger.info("Test Case succeeded");
    	}    	
	}  
}
