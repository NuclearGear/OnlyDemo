package com.winmax.pages.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.winmax.config.Config;
import com.winmax.pages.attendance.ShiftPage;
import com.winmax.steps.WebDriverSteps;

public class HomePage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(ShiftPage.class);	
	
	public HomePage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="dengluBtn")
	private WebElement login_btn;
	
	public void navigateToLoginPage() throws Exception {
		if (!driver.getCurrentUrl().equals(Config.get().getEnvironment())) {
			logger.info("Open system url " + Config.get().getEnvironment());
			driver.navigate().to(Config.get().getEnvironment());
		}	
	}
	
	/**
	 * login EHR system
	 * @param userName
	 * @param pwd
	 * @throws Exception
	 */
	public void login(String userName, String pwd) throws Exception {
		putInValue(waitFor(By.id("UserName")), userName);
		putInValue(waitFor(By.id("Password")), pwd);
		login_btn.click();
		waitFor(By.id("HR"));		
	}
	
	/**
	 * after login click on HCM page, then click on main Menu, then sub menu
	 * @param menuName: 时间管理>排班管理>下属排班管理 
	 * @throws Exception
	 */
	public void clickMenu(String menuName) throws Exception {
		//default show HR page
		waitFor(By.id("HR")).click();		
		waitForVisible(By.xpath("//li[contains(@class,'ul_liFirst')]/a[text()='人事管理']"));
		waitForVisible(By.id("_imgPersonnel"));
				
		// click on 1st level menu
		String[] array = menuName.split(">");		
		String xpath1 = "//li[contains(@class,'ul_li')]/a[normalize-space(text())='" + array[0] + "']";
		WebElement menu1 = waitFor(By.xpath(xpath1));
		menu1.click();
		waitFor(By.xpath("//li[contains(@class,'liFirst')]/a[normalize-space(text())='" + array[0] + "']"));
		
		// click on the next level
		if (array.length>2 && (!array[1].trim().isEmpty())) {
			String xpath2 = "//div[./dd/a[normalize-space(text())='" + array[1] + "']]"
					+ "//a[normalize-space(text())='" + array[2] + "']";
			waitFor(By.xpath(xpath2)).click();
		}
	}
	
	public void clickMenuFromMyApplications(String subMenu) throws Exception{
		// if not in 员工自助  page, then click on this link
		try {
			find(By.id("ess")).click();
		} catch (Exception e) {
			;
		}
		WebElement link = waitFor(By.xpath("//div[@id='InstanceApproval']/p/label[contains(text(),'" + subMenu + "')]"));
		openNewWindowWith(link);
	}
}
