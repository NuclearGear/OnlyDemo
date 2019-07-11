package com.pajk.steps.adaisle;

import com.pajk.pages.adaisle.AnchorCommissionPage;
import com.pajk.pages.common.CommonPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AnchorCommissionStep {
    private static final Logger logger = LogManager.getLogger(AnchorCommissionStep.class);
    private AnchorCommissionPage AC = new AnchorCommissionPage();
    String a,b;

    @When("^(.+)点击编辑按钮，输入(.+)$")
    public void fillcommission(String type,String value) throws Exception {
        a=type;
        b=value;
        logger.info("编辑"+a+b);
        AC.fillCommission(type,value);
    }

    @Then("^验证分成修改成功$")
    public void checkcommission() throws Exception {
        logger.info("验证分成修改");
        AC.checkCommission(a,b+"%");
    }
}
