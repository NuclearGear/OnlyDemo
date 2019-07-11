package com.pajk.steps.adaisle;

import java.util.HashMap;
import java.util.List;

import com.pajk.pages.adaisle.PlanPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;

import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlanStep {
    private static final Logger logger = LogManager.getLogger(PlanStep.class);
    private PlanPage PP = new PlanPage();
    private CommonPage CP = new CommonPage();

    @When("^获取计划列表数据$")
    public void getPlanListData() throws Exception {
        List<List<String>> planlistdata=PP.getPlanListData();
/*        for (List<String> data: planlistdata){
            logger.info(data);
        }*/
    }

    @When("^查询推广计划$")
    public List<List<String>> searchPlan(DataTable data) throws Exception {
        HashMap<String, String> searchData = DataTableUtils.toHashMap(data);
        logger.info("查询计划:"+searchData);
        List<List<String>>aftersearchData=PP.searchPlan(searchData);
        return  aftersearchData;
    }

    @When("^推广计划列表点击(.+)计划 (.+)按钮$")
    public void planListClickButton(String plantype,String button) throws Exception {
        logger.info("推广计划列表点击"+button+"按钮");
        PP.planListClickButton(plantype,button);
    }

    @And("^提交计划表单点击确认按钮$")
    public void planClickSubmitButton() throws Exception {
        PP.clickSubmitButon();
    }

    @When("^推广计划列表点击导出按钮$")
    public void clickExportButton() throws Exception {
        PP.clickExportButton();
    }

    @When("^导出(.+)计划报表$")
    public void clickReportExportButton(String reportName) throws Exception {
        logger.info("导出"+reportName+"报表");
        CP.clickButton(reportName+"计划报表");
        Utils.waitABit(3000);
    }

    @And("^获取计划信息$")
    public void getPlanData() throws Exception {
        HashMap plandata=PP.getPlanData();
        logger.info("计划信息:"+plandata.toString());
    }

    @And("^填写推广计划信息$")
    public void fillPlanData(DataTable data) throws Exception {
        HashMap<String, String> planData = DataTableUtils.toHashMap(data);
        String planname = null;
        try {
            planname = planData.get("推广名称");
        }
        catch (Exception e){ }
        String startdate = planData.get("开始日期");
        String enddate = planData.get("结束日期");
        logger.info("填写计划信息:"+planData);
        PP.fillPlanInformation(planname,startdate,enddate);
    }

    @And("^填写推广计划佣金 (.+)$")
    public void fillPlanData(String brokerage) throws Exception {
        logger.info("填写计划佣金:"+brokerage);
        PP.fillPlanInformationBrokerage(brokerage);
    }

    @Then("^验证计划列表数据$")
    public void checkPlanData(DataTable data) throws Exception {
        HashMap<String, String> checkPlanData = DataTableUtils.toHashMap(data);
        logger.info("验证计划列表数据:"+checkPlanData);
        PP.checksearchresult(checkPlanData);
    }

}
