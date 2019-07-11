package com.pajk.pages.adaisle;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MerchantCommissionPage extends WebDriverSteps {
    private static final Logger logger = LogManager.getLogger(MerchantCommissionPage.class);
    public MerchantCommissionPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    public void editCommission(String Commission) throws Exception{
        WebElement element = waitFor(By.xpath("//i[contains(@class,'edit')]"));
        clickWithJS(element);
        Utils.waitABit(1000);
        waitFor(By.xpath("//input[@class='ant-input']")).click();
        waitFor(By.xpath("//input[@class='ant-input']")).clear();
        putInValue(By.xpath("//input[@class='ant-input']"),Commission);
        waitFor(By.xpath("//input[@class='ant-input']/../i")).click();
    }

    public List<String> getCategoryList() throws Exception{
        Utils.waitABit(1000);
        List<String> categorylist = this.getListElementsText(findAll(By.xpath("//div[@class='ant-table-body']//tbody[@class='ant-table-tbody']/tr/td")));
        return  categorylist;
    }

    public void checkCategoryList(List<String> data) throws Exception{
        List<String> list= getCategoryList();
        for (String value:data){
            Assert.assertListContainsValue(list,value,"商品列表值不符合预期值:"+value);
        }
    }
}
;