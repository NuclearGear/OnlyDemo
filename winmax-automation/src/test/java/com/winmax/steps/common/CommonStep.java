package com.winmax.steps.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.winmax.config.Const;
import com.winmax.pages.common.CommonPage;
import com.winmax.utils.DataTableUtils;
import com.winmax.utils.Excel;
import com.winmax.utils.Utils;
import com.winmax.utils.WebDriverUtils;

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
		logger.info("I log in system with name: " + Const.USER_NAME + " password: " + Const.PASSWORD);
		hp.login(Const.USER_NAME, Const.PASSWORD);		
    }

	@When("^I login winmax$")
    public void login_winmax() throws Exception{
		hp.navigateToLoginPage();
		logger.info("I log in system with name: " + Const.USER_NAME + " password: " + Const.PASSWORD);
		hp.loginwinmax(Const.USER_NAME, Const.PASSWORD);		
    }
	
	@When("^I login system with name (.+) password (.+)$")
    public void login_system1(String name, String pwd) throws Exception {
		hp.navigateToLoginPage();
		logger.info("I log in system with name: " + name + " password: " + pwd);
		hp.login(name, pwd);		
    }
	
	@When("^I log out system$")
    public void logout_system() throws Exception {		
		hp.logOut();		
    }

	@When("^I click on menu (.+) from selfplatform page$")
    public void open_menu(String menu) throws Exception{
		 hp.clickMenu(menu);		 
    }
	
	@When("^I click on my applications (.+)$")
	public void click_Myapplications(String subMenu) throws Exception {
		if (hp.isMyApplicationsExist()) {
			hp.clickMenuFromMyApplications(subMenu);
		} else {
			hp.clickMenuFromNewFlow(subMenu);
		}
	}

	@Then("^I search staff by no (.+)$")
	public void search_staff(String staffNo) throws Exception {
		 hp.searchStaff(staffNo);	
	}
	
	@Then("^I advance search staff by no (.+)$")
	public void advance_search_staff(String staffNo) throws Exception {
		 hp.advanceSearchStaff(Utils.getTestDataProperty(staffNo));
	}
	
	@When("^I open (.+) from home page")
	public void open_from_home_page(String menuName) throws Exception {
		 hp.openShortCutMenu_homePage(menuName);	
	}

	@And("^I close current page and switch to parent page$")
	public void close_current_to_parent() throws Exception {
		hp.closeWindowToParent();
	}

	@And("^I drag (.+) to home page$")
	public void drag_menu_to_home_page(String menu) throws Exception {
		hp.createShortCutMenu(menu);
	}
	
	@And("^I click (.+) to refresh current page$")
	public void click_last_link(String menu) throws Exception {
		hp.clickLink(menu);
	}
	
	@And("^I close current page and switch to window with url contains (.+)$")
	public void closeWinSwitchToParent(String url) throws Exception {
		hp.closeCurrWindSwitchToOpener(url);
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
