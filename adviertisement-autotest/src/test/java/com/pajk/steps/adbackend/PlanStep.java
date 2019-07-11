package com.pajk.steps.adbackend;

import com.pajk.pages.adbackend.PlanPage;
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

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PlanStep {
    private static final Logger logger = LogManager.getLogger(PlanStep.class);
    private PlanPage PP = new PlanPage();
    private CommonPage CP = new CommonPage();
    Map<String,String> checkData = new  HashMap<String,String>();
    Map<String,String> editPlanData = new  HashMap<String,String>();

    @When("^广告投放列表点击 (.+)按钮$")
    public void planPageClickButton(String button) throws Exception {
        logger.info("广告投放列表点击"+button+"按钮");
        PP.planPageClickButton(button);
    }

    @And("^点击选中首位列表数据$")
    public void planListSelectTopData() throws Exception {
        logger.info("选中首位投放");
        PP.selectTopData();
    }

    @And("^点击选中所有投放$")
    public void planListSelectAllData() throws Exception {
        logger.info("选中所有投放");
        PP.selectAll();
    }

    @When("^获取投放信息$")
    public void getPlanInformation() throws Exception {
        Map<String, String> planData = PP.getPlanInformation();
        logger.info("广告投放信息:"+planData);
    }

    @When("^查询广告投放$")
    public void searchPlan(DataTable dataTable) throws Exception {
        HashMap<String,String> searchData = DataTableUtils.toHashMap(dataTable);
        logger.info("查询广告投放:"+searchData);
        PP.searchPlan(searchData);
        //logger.info("查询后投放列表数据:"+CP.getAdfrontendListData());
        checkData = searchData;
        checkData.remove("开始日期");
        checkData.remove("结束日期");
    }

    @When("^填写广告投放信息$")
    public void fillPlanInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        checkData = data;
        logger.info("填写投放信息:"+data);
        PP.fillPlanInformation(data);
        Utils.waitABit(3000);
    }

    @When("^编辑广告投放信息$")
    public void editPlanInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        editPlanData = data;
        logger.info("编辑投放信息:"+data);
        PP.fillPlanInformation(data);
        Utils.waitABit(2000);
    }

    @When("^编辑广告投放定向信息$")
    public void fillPlanTarget(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        editPlanData = data;
        logger.info("编辑投放信息:"+data);
        PP.fillPlanTarget(data);
        Utils.waitABit(2000);
    }

    @When("^上传素材 (.+)$")
    public void uploadMaterial(String size) throws Exception {
        PP.clickUploadButton();
        logger.info("上传素材:"+size);
        CP.uploadMaterial(size);
    }

    @When("^根据提示上传素材$")
    public void uploadMaterial() throws Exception {
        String size = PP.getReminderSize();
        logger.info("上传提示素材:"+size+".jpg");
        PP.clickUploadButton();
        CP.uploadMaterial(size+".jpg");
        Utils.waitABit(500);
    }

    @When("^提交投放信息$")
    public void submitPlan() throws Exception {
        PP.submitPlan();
    }

    @Then("^验证广告投放列表数据$")
    public void checkPlanListData() throws Exception {
        logger.info("验证投放列表数据:"+checkData);
        CP.checkAdfrontendListTopData(checkData);
    }

    @Then("^验证广告投放列表首行数据$")
    public void checkPlanListTopData() throws Exception {
        logger.info("验证投放列表首行数据:"+checkData);
        CP.checkAdfrontendListTopData(checkData);
    }
    @Then("^验证编辑广告投放信息$")
    public void checkPlanInformation() throws Exception {
        logger.info("验证广告投放信息:"+editPlanData);
        PP.checkPlanData((HashMap<String, String>) editPlanData);
    }

}
