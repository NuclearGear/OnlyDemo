package com.pajk.steps.adbackend;

import com.pajk.pages.adbackend.AdvertiserPage;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdvertiserStep {
    private static final Logger logger = LogManager.getLogger(AdvertiserStep.class);
    private AdvertiserPage AP = new AdvertiserPage();
    private CommonPage CP = new CommonPage();
    String type = null;
    String companyName = null;
    String booth = null;
    List<String> principalList = new ArrayList<String>();
    Map<String,String> checkData = new  HashMap<String,String>();

    @When("^广告主列表点击 (.+)按钮$")
    public void advertiserPageClickButton(String button) throws Exception {
        logger.info("广告主列表点击"+button+"按钮");
        CP.clickButton(button);
    }

    @When("^收入明细列表点击 (.+)按钮$")
    public void incomeDetailClickbutton(String button) throws Exception {
        logger.info("收入明细列表点击"+button+"按钮");
        CP.clickButton(button);
    }

    @When("^收入明细列表点击导出按钮$")
    public void incomeDetailClickExportbutton() throws Exception {
        logger.info("导出广告主收入明细");
        AP.clickExportButton();
        //Utils.waitABit(2000);
    }

    @When("^填写广告主信息$")
    public void fillAdvertiserInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        checkData = data;
        companyName = data.get("公司简称");
        type = data.get("类型");
        logger.info("填写广告主信息"+data);
        AP.fillAdvertiserInformation(companyName,type);
    }

    @When("^新增广告主明细$")
    public void addIncomeDetail(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        String name = data.get("活动名称");
        String income = data.get("收入金额");
        logger.info("新建广告主:"+data);
        AP.fillIncomeDetail(name,income);
    }

    @When("^编辑广告主明细$")
    public void editIncomeDetail(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        String name = data.get("活动名称");
        String income = data.get("收入金额");
        logger.info("编辑广告主明细:"+data);
        AP.fillIncomeDetail(name,income);
    }

    @When("^编辑广告主信息$")
    public void editAdvertiserInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        try {
            companyName = data.get("公司简称");
            type = data.get("类型");
        }catch (Exception e){
        }
        logger.info("编辑广告主:"+data);
        AP.fillAdvertiserInformation(companyName,type);
    }

    @When("^编辑广告主选择广告位 (.+)$")
    public void editAdvertiserBooth(String boothName) throws Exception {
        booth = boothName;
        logger.info("编辑广告主广告位:"+boothName);
        AP.fillAdvertiserBooth(boothName);
    }

    @When("^编辑广告主选择负责人$")
    public void editAdvertiserPersonincharge() throws Exception {
        logger.info("编辑广告主负责人");
        principalList = AP.fillAdvertiserPersonincharge();
    }

    @When("^查询广告主$")
    public void searchAdvertiser(DataTable dataTable) throws Exception {
        HashMap<String,String> searchData = DataTableUtils.toHashMap(dataTable);
        logger.info("查询广告主:"+searchData);
        try{
            type = searchData.get("类型");
            companyName = searchData.get("公司简称");
        }catch (Exception e){
        }
        AP.searchAdvertiser(searchData);
    }

    @Then("^验证广告主列表数据$")
    public void checkAdvertiserListData() throws Exception {
        logger.info("验证广告主列表数据");
        AP.checkAdvertiserListData(type,companyName);
    }

    @Then("^验证广告主列表负责人数据$")
    public void checkAdvertiserListPrincipalData() throws Exception {
        logger.info("验证广告主负责人:"+principalList);
        AP.checkAdvertiserListData(principalList);
    }

    @Then("^验证广告主已选广告位数据$")
    public void checkAdvertiserBoothData() throws Exception {
        logger.info("验证广告主已选广告位:"+booth);
        AP.checkAdvertiserBoothData(booth);
    }

    @Then("^验证广告主列表首行数据$")
    public void checkAdvertiserListTopData() throws Exception {
        logger.info("验证广告主列表首行数据:"+checkData);
        CP.checkAdfrontendListTopData(checkData);
    }
}
