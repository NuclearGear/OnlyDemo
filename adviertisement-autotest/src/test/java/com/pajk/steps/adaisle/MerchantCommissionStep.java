package com.pajk.steps.adaisle;

import com.pajk.pages.adaisle.MerchantCommissionPage;
import com.pajk.pages.common.CommonPage;
import com.pajk.utils.DataTableUtils;
import com.pajk.utils.Utils;
import cucumber.api.DataTable;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MerchantCommissionStep {
    private static final Logger logger = LogManager.getLogger(MerchantCommissionStep.class);
    private MerchantCommissionPage MC = new MerchantCommissionPage();
    private CommonPage CP = new CommonPage();

    List<String> checkData = new ArrayList<String>();

    @When("^查询商品分类$")
    public void categorySearch(DataTable data) throws Exception {
        HashMap<String,String> searchdata = DataTableUtils.toHashMap(data);
        logger.info("查询商品类目:"+searchdata);
        String topcategory = searchdata.get("一级分类");
        String secondarycategory = searchdata.get("二级分类");
        String category = searchdata.get("三级分类");
        checkData.add(topcategory);
        checkData.add(secondarycategory);
        checkData.add(category);
        Utils.waitABit(4000);
        CP.categorySearch(topcategory,secondarycategory,category);
    }

    @When("^编辑修改最低佣金 (.+)$")
    public void editCommission(String commission) throws Exception {
        logger.info("修改最低佣金"+commission);
        checkData.add(commission+"%");
        MC.editCommission(commission);
    }

    @Then("^验证查询商品列表数据$")
    public void checkCategoryList() throws Exception {
        logger.info("验证商品列表数据:"+checkData);
        MC.checkCategoryList(checkData);
    }

    @Then("^验证查询商品列表最低佣金成功修改$")
    public void checkEditCategory() throws Exception {
        logger.info("验证商品最低佣金:"+checkData);
        MC.checkCategoryList(checkData);
    }
}
