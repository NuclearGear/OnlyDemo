package com.pajk.steps.adaisle;

import com.pajk.pages.adaisle.DataReportPage;
import com.pajk.utils.DataTableUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class DataReportStep {
    private static final Logger logger = LogManager.getLogger(DataReportStep.class);
    private DataReportPage DR = new DataReportPage();

    @When("^点击报表 (.+)$")
    public void clickReport(String reportName) throws Exception {
        logger.info("点击"+reportName);
        DR.clickReport(reportName);
    }
    @When("^点击导出按钮$")
    public void clickExportButton() throws Exception {
        logger.info("点击导出按钮");
        DR.clickExportButton();
    }
    @When("^查询报表数据$")
    public void searchdata(DataTable data) throws Exception {
        HashMap<String, String> searchdata = DataTableUtils.toHashMap(data);
        logger.info("查询筛选报表数据:"+searchdata);
        DR.searchReportData(searchdata);
    }

    @When("^获取报表列表数据$")
    public void getReportData() throws Exception {
        DR.getReportData();
    }

    @Then("^(.+)验证总计列数据$")
    public void checkReportData(String reportname) throws Exception {
        logger.info("验证报表数据:"+reportname);
        DR.checkReportData(reportname);
    }

}
