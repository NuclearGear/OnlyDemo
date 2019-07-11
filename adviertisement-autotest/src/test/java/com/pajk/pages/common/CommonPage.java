package com.pajk.pages.common;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.*;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;

import com.pajk.config.Const;
import com.pajk.utils.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.pajk.config.Config;
import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.FileUtil;
import com.pajk.utils.Utils;

import static com.pajk.utils.WebDriverUtils.getRowData;

public class CommonPage extends WebDriverSteps {
	private static final Logger logger = LogManager.getLogger();
	
	public CommonPage() {
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//button[@type='submit']")
	private WebElement login_btn;

	public void navigateToLoginPage() throws Exception {
		if (!driver.getCurrentUrl().equals(Config.get().getEnvironment())) {
			logger.info("Open system url " + Config.get().getEnvironment());
			driver.navigate().to(Config.get().getEnvironment());
		}	
	}

	public void navigateToLoginPage(String url) throws Exception {
		if (!driver.getCurrentUrl().equals(url)) {
			logger.info("Open system url " + url);
			driver.navigate().to(url);
			//Utils.waitABit(1000);
			//browserRefresh();
		}
	}
	
	 /**
     * login PAJK
     * @param userName
     * @param pwd
     * @throws Exception
     */
    public void loginPAJK(String userName, String pwd) throws Exception {
        try {
            /*String js = "document.getElementById('xpassword').style.display='block';";
            WebDriverUtils.executeJS(""+ js +"", driver);*/
			//waitForShort(By.xpath("//input[@id='userName']"));
			boolean needLogin = waitForVisibleOrNot(By.xpath("//input[@id='userName']"),2);
			if(needLogin){
                putInValue(waitFor(By.xpath("//input[@id='userName']")), userName);
                putInValue(waitFor(By.xpath("//input[@id='password']")), pwd);
                login_btn.click();
                Utils.waitABit(1000);
                logger.info("I log in system with name: " + userName);
            }

        }
        catch (Exception e){

        }
    }

	public void clickAdMenu(String menuName) throws Exception {
    	try{
    		if (menuName.contains(">")) {
				String[] array = menuName.trim().split(">");
				waitFor(By.xpath("//span[contains(text(),'" + array[0] + "')]")).click();
				Utils.waitABit(500);
				clickWithJS(waitFor(By.xpath("//span[contains(text(),'" + array[1] + "')]")));
				//executeJS("document.querySelector('ul>li>a>span').click();");
				//waitFor(By.xpath("//ul/li/a/child::*[contains(text(),'"+array[1]+"')]")).click();
				//waitFor(By.cssSelector("ul[id='/merchant$Menu']>li:nth-child(2)>a")).click();
/*				List<String> menu = this.getListElementsText(findAll(By.cssSelector("ul>li.ant-menu-submenu-open>ul>li>a")));
				if (menu.contains(array[1])) {
					waitFor(By.cssSelector("ul>li.ant-menu-submenu-open>ul>li:nth-child("+String.valueOf(menu.indexOf(array[1])+1)+")>a")).click();
				}*/
			}
			else {
/*				Actions actions = new Actions(driver);
				actions.moveToElement(element).click().build().perform();*/
				clickWithJS(waitFor(By.xpath("//li/a/span[contains(text(),'" + menuName + "')]")));
			}
            //List<WebElement> all_list = findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr[1]/td"));
			//List<String> plan = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr[1]/td/span")));
            //waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[11][contains(text(),'投放中')][1]")).click();

    	}catch (Exception e){
			e.printStackTrace();
		}
		Utils.waitABit(1000);
	}

    public void clickButton(String button) throws Exception {
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithJS(element);
        Utils.waitABit(1000);
    }

	public String promptText() throws Exception{
    	String prompttext=waitFor(By.cssSelector("div.ant-message>span>div:last-child>div>div>span:last-child")).getAttribute("innerText");
		return prompttext;
	}

    public void categorySearch(String topcategory,String secondarycategory,String category) throws Exception{
        try {
            List<WebElement> categoryList = findAll(By.xpath("//div[@class='ant-select-selection__rendered']"));
            WebElement element = categoryList.get(0);
            clickWithJS(element);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + topcategory + "')]")));
            WebElement element2 = categoryList.get(1);
            clickWithJS(element2);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + secondarycategory + "')]")));
            WebElement element3 = categoryList.get(2);
            clickWithJS(element3);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + category + "')]")));
        }catch (Exception e){
            List<WebElement> categoryList = findAll(By.xpath("//div[@class='ant-select-selection__rendered']"));
            WebElement element = categoryList.get(0);
            clickWithJS(element);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + topcategory + "')]")));
            WebElement element2 = categoryList.get(1);
            clickWithJS(element2);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + secondarycategory + "')]")));
            WebElement element3 = categoryList.get(2);
            clickWithJS(element3);
            Utils.waitABit(1500);
            clickWithJS(waitFor(By.xpath("//div[contains(@style,'position: absolute;')][last()]//li[contains(text(),'" + category + "')]")));
        }
        clickWithJS(waitFor(By.xpath("//span[contains(text(),'查询')]")));
    }

    public void uploadMaterial(String fileName) throws Exception {
        String uploadFile = System.getProperty("user.dir")+Const.MATERITAL_FOLDER +fileName;
        Robot robot = new Robot();
        Utils.waitABit(2000);
        StringSelection sel = new StringSelection(uploadFile);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel,null);
        // 按下回车
        robot.keyPress(KeyEvent.VK_ENTER);
        // 释放回车
        robot.keyRelease(KeyEvent.VK_ENTER);
        // 按下 CTRL+V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        // 释放 CTRL+V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(1000);
        // 点击回车 Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        Utils.waitABit(500);
    }

	public void assertPromptText(String text) throws Exception{
		String promptText=promptText();
        Utils.waitABit(3000);
		logger.info("验证提示:"+text+" 实际提示:"+promptText);
		Assert.isEqual(text,promptText,"提示不符合预期");
	}

    public void assertPromptTextContains(String text) throws Exception{
        String prompttext=promptText();
        logger.info("验证提示:"+text+" 实际提示:"+prompttext);
        Assert.isContains(prompttext,text,"提示不符合预期");
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

	public void checkAdaisleLogin() throws Exception{
		Assert.visible(By.xpath("//span[@class='helloTxt__2O2ri']"),"登录失败",driver);
        logger.info("Adaisle Login succeeded!");
	}

	public void checkAdbackendLogin() throws Exception{
		Assert.visible(By.xpath("//span[contains(text(),'广告管理')]"),"登录失败",driver);
		logger.info("Adbackend Login succeeded!");
	}

    public void checkExportFile(String fileName) throws Exception {
        logger.info("验证"+fileName+"是否下载成功");
        Assert.fileNameContains(fileName,fileName+"导出失败");
    }

	public List<LinkedHashMap> getAdfrontendListData() throws Exception{
        Utils.waitABit(2000);
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = new LinkedList<LinkedHashMap>();
        List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@class='ant-table-body']//thead/tr/th[not(@class='ant-table-selection-column')]")));
		List<WebElement> elements = findAll(By.xpath("//div[@class='ant-table-body']//tbody/tr"));
		if (elements.size() !=0) {
            scrollTo(elements.get(0));
            for (int i = 0; i < elements.size(); i++) {
                LinkedHashMap<String, String> listData = getRowData("//div[@class='ant-table-body']//thead/tr/th[not(@class='ant-table-selection-column')]", "//div[@class='ant-table-body']//tbody/tr[" + (i + 1) + "]/td[not(@class='ant-table-selection-column')]", headers.size(), driver);
                if(listData.containsKey("开始时间")||listData.containsKey("结束时间")){
                    if(listData.get("开始时间").contains("/")){
                        String startDate = listData.get("开始时间").replace("/","-");
                        String endDate = listData.get("结束时间").replace("/","-");
                        listData.put("开始时间",startDate);
                        listData.put("结束时间",endDate);
                    }
                }
                data.add(listData);
            }
        }else {
		    throw new Exception(title+"列表无数据");
        }
        return data;
	}

    public List<LinkedHashMap> getAdfrontendListTopData() throws Exception{
        Utils.waitABit(3000);
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = new LinkedList<LinkedHashMap>();
        List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@class='ant-table-body']//thead/tr/th[not(@class='ant-table-selection-column')]")));
        List<WebElement> elements = findAll(By.xpath("//div[@class='ant-table-body']//tbody/tr[1]"));
        scrollTo(elements.get(0));
        if (elements.size() !=0) {
            for (int i = 0; i < elements.size(); i++) {
                LinkedHashMap<String, String> listData = getRowData("//div[@class='ant-table-body']//thead/tr/th[not(@class='ant-table-selection-column')]", "//div[@class='ant-table-body']//tbody/tr[" + (i + 1) + "]/td[not(@class='ant-table-selection-column')]", headers.size(), driver);
                if(listData.containsKey("开始时间")||listData.containsKey("结束时间")){
                    if(listData.get("开始时间").contains("/")){
                        String startDate = listData.get("开始时间").replace("/","-");
                        String endDate = listData.get("结束时间").replace("/","-");
                        listData.put("开始时间",startDate);
                        listData.put("结束时间",endDate);
                        }
                }
                data.add(listData);
            }
        }else {
            throw new Exception(title+"列表无数据");
        }
        return data;
    }

    public void checkAdfrontendListData(String ...args) throws Exception{
        Utils.waitABit(3000);
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = getAdfrontendListData();
        for(LinkedHashMap<String,String> map:data){
            Collection<String> valueCollection = map.values();
            final int size = valueCollection.size();
            List<String> valueList = new ArrayList<String>(valueCollection);
            String[] valueArray = new String[size];
            map.values().toArray(valueArray);
            for(int i=0;i<valueList.size();i++){
                if(valueList.get(i).contains("\n")){
                    String value =valueList.get(i).replace("\n",",");
                    valueList.set(i,value);
                }
            }
            for(String arg :args){
                Assert.assertListContainsValue(valueList,arg,"验证"+title+"列表数据与预期不符:"+valueList+"不包含"+arg);
            }
        }
    }

    public void checkAdfrontendListData(Map<String,String> checkData) throws Exception{
        Utils.waitABit(3000);
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = getAdfrontendListData();
        for(LinkedHashMap<String,String> map:data){
            Collection<String> valueCollection = map.values();
            final int size = valueCollection.size();
            List<String> valueList = new ArrayList<String>(valueCollection);
            String[] valueArray = new String[size];
            map.values().toArray(valueArray);
            for(int i=0;i<valueList.size();i++){
                if(valueList.get(i).contains("\n")){
                    String value =valueList.get(i).replace("\n",",");
                    valueList.set(i,value);
                }
            }
            for(String arg :checkData.values()){
                Assert.assertListContainsValue(valueList,arg,"验证"+title+"列表数据与预期不符:"+valueList+"不包含"+arg);
            }
        }
    }

    public void checkAdfrontendListTopData(String ...args) throws Exception{
        Utils.waitABit(3000);
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = getAdfrontendListTopData();
        for(LinkedHashMap<String,String> map:data){
            Collection<String> valueCollection = map.values();
            final int size = valueCollection.size();
            List<String> valueList = new ArrayList<String>(valueCollection);
            String[] valueArray = new String[size];
            map.values().toArray(valueArray);
            for(int i=0;i<valueList.size();i++){
                if(valueList.get(i).contains("\n")){
                    String value =valueList.get(i).replace("\n",",");
                    valueList.set(i,value);
                }
            }
            for(String arg :args){
                Assert.assertListContainsValue(valueList,arg,"验证"+title+"列表数据与预期不符:"+valueList+"不包含"+arg);
            }
        }
    }

    public void checkAdfrontendListTopData(Map<String,String> checkData) throws Exception{
        Utils.waitABit(3000);
        try{
            clickButton("查 询");
        }catch (Exception e){

        }
        String title = waitFor(By.xpath("//div[@class='ant-breadcrumb']/span[last()]//span[@class='ant-breadcrumb-link']")).getAttribute("innerText");
        List<LinkedHashMap> data = getAdfrontendListTopData();
        for(LinkedHashMap<String,String> map:data){
            Collection<String> valueCollection = map.values();
            final int size = valueCollection.size();
            List<String> valueList = new ArrayList<String>(valueCollection);
            String[] valueArray = new String[size];
            map.values().toArray(valueArray);
            for(int i=0;i<valueList.size();i++){
                if(valueList.get(i).contains("\n")){
                    String value =valueList.get(i).replace("\n",",");
                    valueList.set(i,value);
                }
            }
            for(String arg :checkData.values()){
                Assert.assertListContainsValue(valueList,arg,"验证"+title+"列表数据与预期不符:"+valueList+"不包含 "+arg);
            }
        }
    }

	public void uploadFile(StringSelection sel,WebElement upButton) throws Exception {
        // 把图片文件路径复制到剪贴板
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(sel, null);
        System.out.println("selection" + sel);
         // 点击本地上传图片
        clickWithActions(upButton);
        // 新建一个Robot类的对象
        Robot robot = new Robot();
        Thread.sleep(1000);

        // 按下回车
        robot.keyPress(KeyEvent.VK_ENTER);
        // 释放回车
        robot.keyRelease(KeyEvent.VK_ENTER);
        // 按下 CTRL+V
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        // 释放 CTRL+V
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyRelease(KeyEvent.VK_V);
        Thread.sleep(1000);

        // 点击回车 Enter
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}