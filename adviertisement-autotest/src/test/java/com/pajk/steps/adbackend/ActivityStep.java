package com.pajk.steps.adbackend;

import com.pajk.pages.adbackend.ActivityPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.helper.DataUtil;

import java.util.HashMap;
import java.util.Map;

public class ActivityStep {
    private static final Logger logger = LogManager.getLogger(ActivityStep.class);
    private ActivityPage AP = new ActivityPage();
    private CommonPage CP = new CommonPage();
    String activityName =null;
    String type = null;
    Map<String,String> checkData = new HashMap<String,String>();

    @When("^广告活动列表点击 (.+)按钮$")
    public void advertiserPageClickButton(String button) throws Exception {
        logger.info("广告活动列表点击"+button+"按钮");
        CP.clickButton(button);
        //if(button.equals("新建广告投放"))
    }

    @And("^填写广告活动信息$")
    public void fillActivityInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        checkData = data;
        String name = data.get("活动名称");
        String owner = data.get("广告主");
        logger.info("填写广告活动信息:"+name);
        AP.fillActivityInformation(name,owner);
    }

    @And("^编辑广告活动信息$")
    public void editActivityInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        checkData = data;
        String name = data.get("活动名称");
        logger.info("编辑广告活动名称:"+name);
        AP.editActivityInformation(name);
    }

    @When("^查询广告活动$")
    public void searchAdvertiser(DataTable dataTable) throws Exception {
        HashMap<String,String> searchData = DataTableUtils.toHashMap(dataTable);
        logger.info("查询广告活动:"+searchData);
        try{
            activityName = searchData.get("活动名称");
            type = searchData.get("类型");
        }catch (Exception e){
        }
        AP.searchActivity(searchData);
    }

    @Then("^验证广告活动列表数据$")
    public void checkActivityListData() throws Exception {
        logger.info("验证活动列表数据:"+activityName+","+type);
        AP.checkActivityListData(activityName,type);
    }

    @Then("^验证广告活动列表首行数据$")
    public void checkActivityListTopData() throws Exception {
        logger.info("验证活动列表首行数据:"+checkData);
        Utils.waitABit(2000);
        CP.checkAdfrontendListTopData(checkData);
    }

}
