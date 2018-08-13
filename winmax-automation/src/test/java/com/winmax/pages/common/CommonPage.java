package com.winmax.pages.common;

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import br.eti.kinoshita.testlinkjavaapi.util.Util;

import com.winmax.config.Config;
import com.winmax.pages.attendance.ShiftPage;
import com.winmax.steps.WebDriverSteps;
import com.winmax.utils.Assert;
import com.winmax.utils.FileUtil;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

public class CommonPage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger(ShiftPage.class);	
	
	public CommonPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="loginBt")
	private WebElement login_btn;
	
	public void navigateToLoginPage() throws Exception {
		if (!driver.getCurrentUrl().equals(Config.get().getEnvironment())) {
			logger.info("Open system url " + Config.get().getEnvironment());
			driver.navigate().to(Config.get().getEnvironment());
		}	
	}
	
	 /**
     * login 神州租租车
     * @param userName
     * @param pwd
     * @throws Exception
     */
    public void login(String userName, String pwd) throws Exception {
        //waitFor(By.xpath("//a[@id='loginBT'][text()='登录']")).click();
        putInValue(waitFor(By.xpath("//input[@id='normal_id']")), userName);
        String js = "document.getElementById('xpassword').style.display='block';";
        WebDriverUtils.executeJS(""+ js +"", driver);
        waitFor(By.xpath("//input[@id='xpassword']")).sendKeys(pwd);
        login_btn.click();
        Utils.waitABit(3000);
    }

	 /**
     * login 赢卖通
     * @param userName
     * @param pwd
     * @throws Exception
     */   
    public void loginwinmax(String userName, String pwd) throws Exception {
        putInValue(waitFor(By.xpath("//input[@id='LoginForm_username']")), userName);
        putInValue(waitFor(By.xpath("//input[@id='LoginForm_password']")), pwd);
        waitFor(By.xpath("//input[@id='LoginForm_verifyCode']")).sendKeys("imqa");
        waitFor(By.id("login")).click();
        Utils.waitABit(3000);
    }
    
	public void logOut() throws Exception {
		Actions action = new Actions(driver);
		WebElement loginfor = find(By.xpath("//li[@id='H_LoginInfo']/div/a[@class='sp-staffname']"));
		scrollTo(loginfor);
		action.moveToElement(loginfor).build().perform();
		WebElement logoutBtn = null;
		try {
			logoutBtn = waitForVisible(By.id("LoginOut"));
		} catch (Exception e) {
			action.moveToElement(loginfor).build().perform();
			Utils.waitABit(1000);
			logoutBtn = waitForVisible(By.id("LoginOut"));
		}
		action.moveToElement(logoutBtn).click().build().perform();
	}
	/**
	 * after login click on HCM page, then click on main Menu, then sub menu
	 * @param menuName: 时间管理>排班管理>下属排班管理 
	 * @throws Exception
	 */
	public void clickMenu(String menuName) throws Exception {
		//default show homepage
		//waitForShort(By.xpath("//ul[@id='AYDSPMainMenu']/li[1]/a")).click();

				
		// click on 1st level menu
		String[] array = menuName.split(">");		
		String xpath1 ="//ul[@id='AYDSPMainMenu']/li/a/span[text()='"+ array[0] + "']";
		waitForShort(By.xpath(xpath1)).click();
		
		
		// click on the next level

		String xpath2 = "//ul[@id='AYDSPMainMenu']/li/ul/li/a[text()='"+ array[1] + "']";
		waitForShort(By.xpath(xpath2)).click();
		Utils.waitABit(3000);
	}
	
	public boolean isMyApplicationsExist() throws Exception {
		boolean flag = true;
		try {
			waitForShort(By.xpath("//div[@id='Movecontainer']//span[contains(text(),'我的申请')]"));
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public void clickMenuFromMyApplications(String subMenu) throws Exception {
		// if not in 员工自助  page, then click on this link
		try {
			find(By.id("ess")).click();
		} catch (Exception e) {
			;
		}
		WebElement link = waitFor(By.xpath("//div[@id='InstanceApproval']/p/label[contains(text(),'" + subMenu + "')]"));
		openNewWindowWith(link);
		WebDriverUtils.waitLoading(driver);
	}	
	
	/**
	 * 搜索员工
	 * @param no
	 * @throws Exception
	 */
	public void searchStaff(String no) throws Exception {
		WebDriverUtils.searchByStaffNo(no, driver);
		
	}
	
	public void advanceSearchStaff(String no) throws Exception {
		WebDriverUtils.advanceSearchById(no, driver);		
	}
	
	public void searchSalaryInfo(String comboBox) {
		
		
	}
	
	public void clickMenuFromNewFlow(String subMenu) throws Exception {		
		WebElement link = waitFor(By.xpath("//div[@id='Movecontainer']//a[contains(text(),'申请新流程')]"));
		openNewWindowWith(link);
		waitFor(By.xpath("//div[@id='divFlowList']//a[contains(text(),'" + subMenu + "')]")).click();
		WebDriverUtils.waitLoading(driver);
	}
	
	public void openShortCutMenu_homePage(String menuName) throws Exception {
		WebElement link = waitFor(By.xpath(".//li[contains(@id,'shortCut')]//a[normalize-space(text())='进行中流程']"));
		openNewWindowWith(link);
	}
	
	public void closeWindowToParent() throws Exception {
		driver.close();
		switchToOpenerWindow();
	}
	
	public void clickLink(String menu) throws Exception {
		find(By.xpath("//div[@id='Navbreadcrumb']//a[normalize-space(text())='" + menu + ">']")).click();
		WebDriverUtils.waitLoading(driver);
	}
	
	//关掉当前窗口，回到url包含指定字符串的窗口
	public void createShortCutMenu(String menu) throws Exception {
		try {
			WebElement icon = waitForVisibleShort(By.xpath("//div[@id='NavMeauList']//div[@class='NavPreBg']//li[@title='展开']"));
			icon.click();
		} catch (Exception e) {
			;
		}
	}
	
	public void closeCurrWindSwitchToOpener(String url) throws Exception {
		int winSizeBefore = driver.getWindowHandles().size();
		driver.close();
		waitForWindows(winSizeBefore-1);
		Set<String> wins = driver.getWindowHandles();
		for (String w:wins) {
			driver.switchTo().window(w);
			if (driver.getCurrentUrl().contains(url)) {
				break;
			}
		}
	}
	
	public void deleteDownloadFileIfExist(String fileName) throws Exception {
		FileUtil.deleteFile(Utils.getExportExcelFilePath(fileName));
	}
}