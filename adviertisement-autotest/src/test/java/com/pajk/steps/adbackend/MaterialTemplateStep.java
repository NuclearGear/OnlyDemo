package com.pajk.steps.adbackend;

import com.pajk.pages.adbackend.MaterialTemplatePage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class MaterialTemplateStep {
    private static final Logger logger = LogManager.getLogger(MaterialTemplateStep.class);
    private MaterialTemplatePage MT = new MaterialTemplatePage();
    private CommonPage CP = new CommonPage();
    String templateName =null;
    Map<String,String> checkData = new HashMap<String,String>();

    @When("^素材模板列表点击 (.+)按钮$")
    public void templatePageClickButton(String button) throws Exception {
        logger.info("素材模板列表点击"+button+"按钮");
        CP.clickButton(button);
    }

    @When("^查询素材模板 (.+)$")
    public void searchAdvertiser(String templateName) throws Exception {
        logger.info("查询素材模板:"+templateName);
        checkData.put("素材模版名称",templateName);
        MT.searchTemplate(templateName);
    }

    @When("^填写素材模板信息$")
    public void fillTemplateInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        if(data.containsKey("素材模版名称")){
            checkData.put("素材模版名称",data.get("素材模版名称"));
        }
        if(data.containsKey("素材模版元素")){
            checkData.put("素材模版元素",data.get("素材模版元素"));
        }
        logger.info("填写素材模板信息:"+data);
        MT.fillTemplateInformation(data);
    }

    @When("^填写素材模板元素信息$")
    public void fillTemplateElementInformation(DataTable dataTable) throws Exception {
        HashMap<String,String> data = DataTableUtils.toHashMap(dataTable);
        logger.info("填写素材模板元素信息:"+data);
        MT.fillTemplateElementInformation(data);
    }

    @When("^提交模板信息$")
    public void submit() throws Exception {
        MT.submit();
        Utils.waitABit(1000);
    }

    @Then("^验证素材模板列表数据$")
    public void checkTemplateListData() throws Exception {
        logger.info("验证素材模板列表数据");
        CP.checkAdfrontendListData(checkData);
    }

    @Then("^验证素材模板列表首行数据$")
    public void checkTemplateListTopData() throws Exception {
        logger.info("验证素材模板列表首行数据:"+checkData);
        CP.checkAdfrontendListTopData(checkData);
    }
}
