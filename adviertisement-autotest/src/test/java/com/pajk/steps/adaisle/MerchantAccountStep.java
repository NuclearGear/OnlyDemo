package com.pajk.steps.adaisle;

import com.pajk.db.DB_yacht;
import com.pajk.pages.adaisle.MerchantAccountPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class MerchantAccountStep {
    private static final Logger logger = LogManager.getLogger(MerchantAccountStep.class);
    private MerchantAccountPage MA = new MerchantAccountPage();
    private CommonPage CP = new CommonPage();
    private DB_yacht yacht = new DB_yacht();

    String searchmerchantid =null;
    String searchmerchantname =null;
    String sum = null;
    String reason = null;

    @When("^数据库清理商户ID (.+)$")
    public void deleteMerchantById(String merchantid) throws Exception {
        yacht.deleteMerchantById(merchantid);
    }

    @When("^商户账户列表点击 (.+)按钮$")
    public void merchantListClickButton(String button) throws Exception {
        logger.info("商户账户列表点击"+button+"按钮");
        MA.merchantListClickButton(button);
    }

    @When("^填写充值金额 (.+)$")
    public void accountRecharge(String amount) throws Exception {
        sum = "+"+amount;
        logger.info("充值金额:"+amount);
        MA.accountRecharge(amount);
    }

    @When("^填写扣费信息$")
    public void account(DataTable data) throws Exception {
        HashMap<String,String> merchantData = DataTableUtils.toHashMap(data);
        String deductionamount = merchantData.get("扣费金额");
        String deduction = merchantData.get("扣费原因");
        sum = "-"+deductionamount;
        reason = deduction;
        logger.info("扣费金额:"+deductionamount);
        MA.accountDeductionAmount(deductionamount,deduction);
    }

    @When("^查询商户账户$")
    public void searchMerchant(DataTable data) throws Exception {
        HashMap<String,String> merchantData = DataTableUtils.toHashMap(data);
        logger.info("查询商户"+merchantData);
        String merchantid= merchantData.get("商户ID");
        String merchantName = merchantData.get("商户名称");
        searchmerchantid = merchantid;
        searchmerchantname = merchantName;
        MA.searchMerchant(merchantid,merchantName);
    }

    @And("^填写商户信息$")
    public void addMerchant(DataTable data) throws Exception {
        HashMap<String, String> merchantData = DataTableUtils.toHashMap(data);
        logger.info("新建商户信息"+merchantData);
        String merchantid= merchantData.get("商户ID");
        String income = merchantData.get("充值金额");
        MA.addMerchant(merchantid,income);
    }

    @Then("^验证商户列表查询数据$")
    public void checkMerchantList() throws Exception {
        logger.info("验证商户列表数据:"+searchmerchantid+","+searchmerchantname);
        MA.checkMerchantList(searchmerchantid,searchmerchantname);
    }

    @Then("^验证商户列表账户状态为 (.+)$")
    public void checkMerchantList(String type) throws Exception {
        logger.info("商户列表账户状态:"+MA.getMerchantListData().get(4));
        MA.checkMerchantList(type);
    }
    @Then("^验证账户明细增加充值记录且充值金额计算准确$")
    public void checkMerchantDepositDetail() throws Exception {
        logger.info("验证商户充值记录:"+sum);
        MA.checkMerchantDetail("充值",sum);
    }

    @Then("^验证账户明细增加扣费记录且扣费金额计算准确$")
    public void checkMerchantConsumeDetail() throws Exception {
        logger.info("验证商户扣费记录:"+sum);
        MA.checkMerchantDetail("扣费",sum,reason);
    }

    @Then("^验证账户明细账户余额与实时余额相同$")
    public void checkMerchantDetail() throws Exception {
        logger.info("商户列表实时余额:"+MA.getMerchantListData().get(3));
        String balance = "¥ "+MA.getMerchantListData().get(3);
        MA.checkMerchantDetail(balance);
    }


}
