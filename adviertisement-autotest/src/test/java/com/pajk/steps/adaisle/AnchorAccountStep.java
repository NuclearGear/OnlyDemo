package com.pajk.steps.adaisle;

import com.pajk.db.DB_yacht;
import com.pajk.pages.adaisle.AnchorAccountPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.DataTableUtils;
import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

public class AnchorAccountStep {
    private static final Logger logger = LogManager.getLogger(AnchorAccountStep.class);
    private AnchorAccountPage AA = new AnchorAccountPage();
    private CommonPage CP = new CommonPage();
    private DB_yacht yacht = new DB_yacht();
    HashMap<String,String> checkData = null;
    List<String> choosegoodsdata = null;
    List<String> editchoosegoodsdata = null;

    @When("^数据库清理主播ID (.+)$")
    public void deleteAnchor(String Id) throws Exception {
        yacht.deleteAnchorById(Id);
    }

    @When("^主播账户列表点击 (.+)按钮$")
    public void anchorListClickButton(String button) throws Exception {
        logger.info("主播账户列表点击"+button+"按钮");
        AA.anchorListClickButton(button);
    }

    @When("^编辑主播页点击 (.+)按钮$")
    public void editAnchorClickButton(String button) throws Exception {
        logger.info("主播编辑页点击"+button+"按钮");
        AA.editAnchorClickButton(button);
    }

    @When("^编辑主播页点击 (.+)TAB$")
    public void editAnchorClickTAB(String button) throws Exception {
        logger.info("主播编辑页点击"+button+"TAB");
        AA.editAnchorClickButton(button);
    }

    @When("^置顶最后一位商品$")
    public void stickyLastGoods() throws Exception {
        logger.info("主播编辑页置顶最后一位商品");
        AA.editAnchorClickButton("置顶");
    }

    @When("^清空主播已选列表商品$")
    public void clearAnchorChooseGoods() throws Exception {
        logger.info("主播编辑页删除所有已选商品");
        AA.editAnchorClickButton("删除");
    }

    @When("^主播选品页点击 (.+)TAB$")
    public void chooseGoodsClickTab(String tabName) throws Exception {
        logger.info("选择"+tabName+"TAB");
        AA.chooseGoodsClickButton(tabName);
    }

    @When("^主播选品页点击 (.+)按钮$")
    public void chooseGoodsClickButton(String button) throws Exception {
        logger.info("主播选品页点击"+button+"按钮");
        AA.chooseGoodsClickButton(button);
        Utils.waitABit(1000);
    }

    @When("^获取选品页已选商品数据$")
    public void getChooseGoodsData() throws Exception {
        choosegoodsdata = AA.getChooseGoodsData();
        logger.info("主播选品页已选商品:"+choosegoodsdata);
    }

    @When("^获取主播已选商品数据$")
    public void getAnchorChooseGoods() throws Exception {
        editchoosegoodsdata = AA.getAnchorChooseGoods();
        logger.info("主播编辑页已选商品:"+editchoosegoodsdata);
    }

    @When("^返回编辑主播页$")
    public void anchorChooseGoodsBack() throws Exception {
        AA.chooseGoodsBack();
    }

    @When("^填写主播手机号 (.+)$")
    public void addAnchor(String phone) throws Exception {
        logger.info("添加主播:"+phone);
        AA.addAnchor(phone);
    }

    @When("^查询主播账号$")
    public void searchAnchor(DataTable data) throws Exception {
        HashMap<String,String> searchdata = DataTableUtils.toHashMap(data);
        checkData = searchdata;
        logger.info("查询主播:"+searchdata);
        AA.searchAnchor(searchdata);
    }

    @When("^填写主播直播间信息$")
    public void fillAnchorInformation(DataTable data) throws Exception {
        HashMap<String,String> anchordata = DataTableUtils.toHashMap(data);
        logger.info("填写直播间信息:"+anchordata);
        Utils.waitABit(1000);
        AA.fillAnchorInformation(anchordata);
    }

    @Then("^验证主播列表查询数据$")
    public void checkAnchorData() throws Exception  {
        logger.info("验证主播列表数据:"+checkData);
        AA.checkAnchorList(checkData);
    }

    @Then("^验证主播已选商品列表$")
    public void checkAnchorChooseGoodsData() throws Exception  {
        logger.info("验证主播已选商品列表数据"+choosegoodsdata);
        AA.checkChooseGoods(choosegoodsdata);
    }

    @Then("^验证主播已选商品列表顺序$")
    public void checkAnchorChooseGoods() throws Exception  {
        logger.info("验证主播已选商品列表顺序");
        AA.checkAnchorChooseGoods(editchoosegoodsdata);
    }

    @Then("^验证主播已选商品列表成功清空商品$")
    public void checkAnchorDeleteGoods() throws Exception  {
        logger.info("验证主播清空已选商品列表");
        AA.checkAnchorDeleteGoods();
    }

}
