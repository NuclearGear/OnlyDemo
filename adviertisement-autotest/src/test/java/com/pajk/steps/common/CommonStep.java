package com.pajk.steps.common;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pajk.config.Const;
import com.pajk.pages.test.TestPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.Excel;
import com.pajk.utils.Utils;

import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class CommonStep {
	
	private static final Logger logger = LogManager.getLogger(CommonStep.class);
	
	private CommonPage hp = new CommonPage();
	
	@When("^I login system$")
    public void login_system() throws Exception{
		hp.navigateToLoginPage();
		//logger.info("I log in system with name: " + Const.USER_NAME);
		hp.loginPAJK(Const.USER_NAME, Const.PASSWORD);
    }

	@When("^I login system (.+)$")
	public void login_system(String url) throws Exception{
		String username = Const.USER_NAME;
		String password = Const.PASSWORD;
	    if (url.equals("推广通")){
	        url="http://www.test.pajkdc.com/tgt/#/admin";
        }
		else if (url.equals("商家推广通")){
			url="http://www.test.pajk.cn/tgt/#/shanghudenglu";
			username = "shiwenjing_001";
			password = "th82pm2e";
		}
		else if (url.equals("主播推广通")){
			url="http://www.test.pajk.cn/tgt/#/zhubodenglu";
			username = "18588888888";
			password = "666666";
		}
        else if(url.equals("广告后台")){
            url="http://www.test.pajkdc.com/adfrontend";
        }
		hp.navigateToLoginPage(url);
		//logger.info("I login system with name: " + username);
		hp.loginPAJK(username, password);
	}
	
	@When("^I login system with name (.+) password (.+)$")
    public void login_system(String name, String pwd) throws Exception {
		hp.navigateToLoginPage();
		hp.loginPAJK(name, pwd);
    }

	@When("^推广通点击菜单 (.+)$")
	public void clickAdaisleMenu(String menu) throws Exception {
		logger.info("推广通点击菜单:"+menu);
		hp.clickAdMenu(menu);
	}

	@When("^广告后台点击菜单 (.+)$")
	public void clickAdbackendMenu(String menu) throws Exception {
		logger.info("广告后台点击菜单:"+menu);
		hp.clickAdMenu(menu);
	}

	@Then("^验证推广通登录成功$")
	public void checkAdaisleLogin() throws Exception {
		hp.checkAdaisleLogin();
	}

	@When("^验证成功导出文件名包含 (.+)$")
	public void checkExportFile(String fileName) throws Exception {
		hp.checkExportFile(fileName);
	}

	@Then("^验证提示为 (.+)$")
	public void checkoutpromptText(String text) throws Exception {
		hp.assertPromptText(text);
	}

	@Then("^验证提示包含 (.+)$")
	public void checkoutpromptTextContains(String text) throws Exception {
		hp.assertPromptTextContains(text);
	}

	@And("^I close current page and switch to window with url contains (.+)$")
	public void closeWinSwitchToParent(String url) throws Exception {
		hp.closeCurrWindSwitchToOpener(url);
	}

	@Then("^广告后台登录成功$")
	public void 广告后台登录成功() throws Throwable {
		hp.checkAdbackendLogin();
	}

	@And("^I update import file (.+)$")
	public void updateImportExcelFile(String fileName, DataTable dataT) throws Exception {
		HashMap<String, String[]> data = new HashMap<String, String[]>();
		List<String> keys = dataT.raw().get(0);
		int valueRowSize = dataT.raw().size() - 1;
		for (int i=0; i<keys.size(); i++) {
			String newData[] = new String[valueRowSize];
			for (int j=1;j<valueRowSize+1;j++) {
				String value = dataT.raw().get(j).get(i);
				if (value.startsWith("@")) {
					value = Utils.getDate(value);
				}
				newData[j-1] = value;
				data.put(keys.get(i), newData);
			}
		}
		Excel.updateImportWorkBook(fileName, data);
	}
	
	@And("^I delete (.+) in download folder")
	public void delete_file_from_download_folder(String fileName) throws Exception {
		hp.deleteDownloadFileIfExist(fileName);
	}

}
