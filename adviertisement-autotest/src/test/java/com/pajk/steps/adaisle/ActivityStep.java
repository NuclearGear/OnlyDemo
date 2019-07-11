package com.pajk.steps.adaisle;

import com.pajk.pages.adaisle.ActivityPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class ActivityStep {
    private static final Logger logger = LogManager.getLogger(ActivityStep.class);
    private ActivityPage AP = new ActivityPage();
    private CommonPage CP = new CommonPage();
    String activityName = null;

    @When("^推广活动列表点击 (.+)按钮$")
    public void clickActivityListButton(String button) throws Exception {
        logger.info("活动列表点击"+button+"按钮");
        AP.activityListClickButton(button);
    }

    @When("^推广活动列表选择(.+)点击 (.+)按钮$")
    public void selectActivityClickButton(String name,String button) throws Exception {
        logger.info("活动列表点击"+name+"活动"+button+"按钮");
        AP.selectActivityClickButton(name,button);
    }

    @When("^填写推广活动信息$")
    public void fillActivityInformation(DataTable data) throws Exception {
        HashMap<String, String> activitydata = DataTableUtils.toHashMap(data);
        String activityname = activitydata.get("活动名称");
        activityName = activityname;
        String signupstartdate = activitydata.get("报名开始日期");
        String signupenddate = activitydata.get("报名结束日期");
        String activitystartdate = activitydata.get("活动开始日期");
        String activityenddate = activitydata.get("活动结束日期");
        String salestext= activitydata.get("促销内容");
        String commission= activitydata.get("佣金门槛");
        logger.info("填写活动信息:"+activitydata);
        AP.fillActivityInformation(activityname,signupstartdate,signupenddate,activitystartdate,activityenddate,salestext,commission);
    }

    @When("^编辑推广活动信息$")
    public void editActivityInformation(DataTable data) throws Exception {
        HashMap<String, String> activitydata = DataTableUtils.toHashMap(data);
        String activityname = activitydata.get("活动名称");
        activityName = activityname;
        String signupstartdate = activitydata.get("报名开始日期");
        String signupenddate = activitydata.get("报名结束日期");
        String activitystartdate = activitydata.get("活动开始日期");
        String activityenddate = activitydata.get("活动结束日期");
        String salestext= activitydata.get("促销内容");
        String commission= activitydata.get("佣金门槛");
        logger.info("编辑活动信息:"+activitydata);
        AP.fillActivityInformation(activityname,signupstartdate,signupenddate,activitystartdate,activityenddate,salestext,commission);
    }

    @When("^编辑推广活动信息优惠卷ID(.+)$")
    public void fillActivityCoupon(String couponId) throws Exception {
        logger.info("填写活动优惠卷:"+couponId);
        AP.fillActivityCoupon(couponId);
    }

    @And("^提交活动表单点击确认按钮$")
    public void activityClickSubmitButton() throws Exception {
        AP.clickSubmitButon();
    }

    @When("^推广活动列表选择活动名称 (.+)$")
    public void chooseActivity(String activityname) throws Exception {
        logger.info("选择活动:"+activityname);
        activityName =activityname;
        AP.chooseActivity(activityname);
    }

    @Then("^验证活动列表状态为 (.+)$")
    public void checkActivityListStatus(String status) throws Exception {
        logger.info("验证"+activityName+"活动状态为"+status);
        AP.checkActivityStatus(activityName,status);
    }

    @Then("^验证编辑活动后列表状态为 (.+)$")
    public void checkAddActivityStatus(String status) throws Exception {
        logger.info("验证"+activityName+"活动状态为"+status);
        AP.checkActivityStatus(activityName,status);
    }

    @Then("^验证新建活动后列表状态为 (.+)$")
    public void checkEditActivityStatus(String status) throws Exception {
        logger.info("验证"+activityName+"活动状态为"+status);
        AP.checkActivityStatus(activityName,status);
    }

}
