package com.pajk.steps.adbackend;

import com.pajk.pages.adbackend.BoothPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class BoothStep {
    private static final Logger logger = LogManager.getLogger(BoothStep.class);
    private BoothPage BP = new BoothPage();
    private CommonPage CP = new CommonPage();
    Map<String, String> checkData = new HashMap<String, String>();

    @When("^广告位列表点击 (.+)按钮$")
    public void boothPageClickButton(String button) throws Exception {
        if(button.equals("置为无效")){
            checkData.put("状态","无效");
            Utils.waitABit(500);
        }
        else if (button.equals("置为有效")){
            checkData.put("状态","有效");
            Utils.waitABit(500);
        }
        logger.info("广告位列表点击:"+button+"按钮");
        CP.clickButton(button);
    }

    @When("^查询广告位$")
    public void searchBooth(DataTable dataTable) throws Exception {
        HashMap<String,String> searchData = DataTableUtils.toHashMap(dataTable);
        if(searchData.containsKey("广告位名称")){
            checkData.put("广告位名称",searchData.get("广告位名称"));
        }
        logger.info("查询广告位:"+searchData);
        BP.searchBooth(searchData);
    }

    @When("^填写广告位信息$")
    public void fillBoothInformation(DataTable dataTable) throws Exception {
        HashMap<String, String> data = DataTableUtils.toHashMap(dataTable);
        if(data.containsKey("广告位名称")){
            checkData.put("广告位名称",data.get("广告位名称"));
        }
        if(data.containsKey("广告位Code")){
            checkData.put("广告位Code",data.get("广告位Code"));
        }
        if(data.containsKey("所属频道")){
            checkData.put("所属频道",data.get("所属频道"));
        }
        if(data.containsKey("素材模板")){
            checkData.put("素材模板",data.get("素材模板"));
        }

        logger.info("填写广告位信息:" + data);
        BP.fillBoothInformation(data);
    }

    @When("^填写广告位定向条件 (.+)$")
    public void fillBoothTarget(String targetType) throws Exception{
        logger.info("填写广告位定向条件:"+targetType);
        checkData.put("定向条件",targetType);
        BP.fillBoothTargetTypeInformation(targetType);
    }

    @When("^填写广告位跳转类型 (.+)$")
    public void fillBoothLinkType(String linkType) throws Exception{
        logger.info("填写广告位跳转条件:"+linkType);
        checkData.put("跳转类型",linkType);
        BP.fillBoothLinkTypeInformation(linkType);
    }

    @When("^填写广告位推广通配置$")
    public void fillBoothSupportTgtInformation(DataTable dataTable) throws Exception{
        HashMap<String, String> data = DataTableUtils.toHashMap(dataTable);
        logger.info("填写广告位推广通配置:"+data);
        String templateName = data.get("推广通素材模版");
        String filledFrames = data.get("插入帧数");
        String floorPrice = data.get("底价");
        checkData.putAll(data);
        BP.fillBoothSupportTgtInformation(templateName,filledFrames,floorPrice);
    }

    @When("^填写广告位程序化配置 (.+)$")
    public void fillBoothSupportDspInformation(String dspName) throws Exception{
        logger.info("填写广告位程序化配置:"+dspName);
        checkData.put("投放渠道",dspName);
        BP.fillBoothSupportDspInformation(dspName);
    }

    @When("^上传预览图 (.+)$")
    public void uploadMaterial(String size) throws Exception {
        BP.clickUploadButton();
        logger.info("上传预览图:"+size);
        CP.uploadMaterial(size);
        Utils.waitABit(2000);
    }

    @When("^提交广告位信息$")
    public void submit() throws Exception {
        BP.boothPageClickButton("提 交");
        Utils.waitABit(1000);
    }

    @When("^获取广告位信息$")
    public void getBoothInformation() throws Exception {
        Map<String,String>boothData=BP.getBoothInformation();
        logger.info("广告位信息:"+boothData);
    }

    @Then("^验证广告位列表首行数据$")
    public void checkTemplateListTopData() throws Exception {
        logger.info("验证广告位列表首行数据:"+checkData);
        CP.checkAdfrontendListTopData(checkData);
    }

    @Then("^验证广告位列表数据$")
    public void checkTemplateListData() throws Exception {
        logger.info("验证广告位列表数据");
        CP.checkAdfrontendListData(checkData);
    }

    @When("^验证广告位信息$")
    public void check() throws Exception {
        logger.info("验证广告位信息:"+checkData);
        BP.checkBoothData((HashMap<String, String>)checkData);
    }


}