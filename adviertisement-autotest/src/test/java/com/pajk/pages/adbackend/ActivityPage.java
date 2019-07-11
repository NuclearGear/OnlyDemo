package com.pajk.pages.adbackend;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ActivityPage extends WebDriverSteps  {
    private static final Logger logger = LogManager.getLogger(ActivityPage.class);
    public ActivityPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    public void activityPageClickButton(String button) throws Exception{
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithJS(element);
    }

    public void searchActivity(HashMap searchData) throws Exception{
        Iterator iterator = searchData.keySet().iterator();
        while(iterator.hasNext()) {
            String key=iterator.next().toString();
            String value=searchData.get(key).toString();
            if(key.equals("活动名称")){
                WebElement element = waitFor(By.id("advtlistname"));
                putInValue(element, value);
            }
            else if(key.equals("类型")){
                waitFor(By.xpath("//div[@class='advertiserList']/form//div[@class='ant-select-selection__rendered']/div")).click();
                WebElement element = waitFor(By.xpath("//li[@class='ant-select-dropdown-menu-item'][contains(text(),'"+value+"')]"));
                clickWithJS(element);
            }
        }
        activityPageClickButton("查 询");
        Utils.waitABit(2000);
    }

    public void fillActivityInformation(String name,String owner) throws Exception{
        waitFor(By.id("adActivityName")).clear();
        putInValue(By.id("adActivityName"),name);
        WebElement element = waitFor(By.id("advertiserId"));
        clickWithJS(element);
        Utils.waitABit(500);
        putInValue(By.id("advertiserId"),owner);
        Utils.waitABit(1000);
        waitFor(By.id("adActivityName")).click();
        Utils.waitABit(500);
        Boolean type = waitForVisibleOrNot(By.xpath("//*[contains(text(),'活动渠道')]"),1);
        if(type){
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'活动渠道')]/../..//div[@class='ant-select-selection__rendered']")));
            Utils.waitABit(500);
            clickWithJS(waitFor(By.xpath("//body/div[last()]//li[contains(text(),'UI-自动化')]")));
        }
        activityPageClickButton("提 交");
    }

    public void editActivityInformation(String name) throws Exception{
        waitFor(By.id("adActivityName")).clear();
        putInValue(By.id("adActivityName"),name);
        activityPageClickButton("提 交");
    }

    public void checkActivityListData(String activityName,String type) throws Exception{
        List<String> activityNameList =getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[2]")));
        List<String> activityTypeList =getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[7]")));
        if(!activityName.isEmpty()) {
            for (String value : activityNameList) {
                Assert.isContains(value,activityName,"广告活动列表数据不正确:"+value);
            }
            for(String value:activityTypeList){
                Assert.isContains(value,type,"广告活动列表数据不正确:"+value);
            }
        }
        else {
            throw new Exception("广告活动列表无数据");
        }
    }

}
