package com.pajk.pages.adaisle;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class AnchorCommissionPage extends WebDriverSteps {
    private static final Logger logger = LogManager.getLogger(AnchorCommissionPage.class);
    public AnchorCommissionPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    public void fillCommission(String type,String value){
        if(type.equals("固定分成")){
            waitFor(By.xpath("//table/tbody/tr[1]/td[2]//i")).click();
        }else {
            waitFor(By.xpath("//table/tbody/tr[2]/td[2]//i")).click();
        }
        putInValue(waitFor(By.xpath("//input")),value);
        Utils.waitABit(500);
        waitFor(By.cssSelector("i.anticon-check")).click();
        Utils.waitABit(500);
    }

    public void checkCommission(String type,String value){
        String actual;
        if(type.equals("固定分成")){
            actual=waitFor(By.xpath("//table/tbody/tr[1]/td[2]/div/div")).getAttribute("innerText");

        }else {
            actual=waitFor(By.xpath("//table/tbody/tr[2]/td[2]/div/div")).getAttribute("innerText");
        }
        Assert.isEqual(value,actual);
    }


}
