package com.pajk.pages.adbackend;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AdvertiserPage extends WebDriverSteps {
    private static final Logger logger = LogManager.getLogger(AdvertiserPage.class);
    public AdvertiserPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    @FindBy(xpath = "//*[contains(text(),'导出')]")
    public WebElement export_btn;

    public void advertiserPageClickButton(String button) throws Exception{
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithJS(element);
    }

    public void clickExportButton() throws Exception {
        clickWithJS(export_btn);
        Utils.waitABit(2500);
    }

    public void searchAdvertiser(HashMap searchData) throws Exception{
        Iterator iterator = searchData.keySet().iterator();
        while(iterator.hasNext()) {
            String key=iterator.next().toString();
            String value=searchData.get(key).toString();
            if(key.equals("公司简称")){
                putInValue(By.id("companyName"), value);
            }
            else if(key.equals("类型")){
                waitFor(By.xpath("//div[@class='ant-select-selection__rendered']")).click();
                WebElement element = waitFor(By.xpath("//li[@class='ant-select-dropdown-menu-item'][contains(text(),'"+value+"')]"));
                clickWithJS(element);
            }
        }
        advertiserPageClickButton("查 询");
        Utils.waitABit(2000);
    }

    public void fillAdvertiserInformation(String name,String type) throws Exception{
        if (!name.isEmpty()){
            waitFor(By.id("name")).clear();
            putInValue(By.id("name"),name);
            waitFor(By.id("fullName")).clear();
            putInValue(By.id("fullName"),name);
        }
        if (type != null){
            if(type.equals("运营")){
                clickWithJS(waitFor(By.xpath("//div[contains(@class,'ant-select-selection__rendered')]/div[@title ='商业']")));
                clickWithJS(waitFor(By.xpath("//li[contains(text(),'运营')]")));
            }
            else if(type.equals("商业")){
                putInValue(By.id("merchantId"), "123");
            }
        }
        waitFor(By.id("industry")).click();
        clickWithJS(waitFor(By.xpath("//ul[contains(@class,'ant-cascader-menu')]/li[@title='汽车']")));
        clickWithJS(waitFor(By.xpath("//ul[contains(@class,'ant-cascader-menu')]/li[@title='机动车']")));
        advertiserPageClickButton("提 交");

    }

    public void fillAdvertiserBooth(String boothName) throws Exception{
        advertiserPageClickButton("选择");
        advertiserPageClickButton("全部不选");
        advertiserPageClickButton(boothName);
        WebElement element = waitFor(By.xpath("//div[@class='renderCheckboxModalFooterBtnCls']//*[contains(text(),'提 交')]"));
        clickWithJS(element);
        advertiserPageClickButton("提 交");
    }

    public List<String> fillAdvertiserPersonincharge() throws Exception{
        WebElement element = waitFor(By.xpath("//ul[@class='ant-transfer-list-content']/div[1]"));
        clickWithActions(element);
        clickWithJS(waitFor(By.xpath("//div[@class='ant-transfer-operation']/button[2]")));
        List principalList =getListElementsText(findAll(By.xpath("//div[@class='ant-transfer']//div[@class='ant-transfer-list'][2]//ul[@class='ant-transfer-list-content']//li")));
        advertiserPageClickButton("提 交");
        return principalList;
    }

    public void fillIncomeDetail(String name,String income) throws Exception{
        putInValue(By.id("activityName"),name);
        clickWithJS(waitFor(By.xpath("//div[@class='ant-select-selection__rendered']")));
        clickWithJS(waitFor(By.xpath("//li[@unselectable='unselectable'][last()]")));
        putInValue(By.id("income"),income);
        try{
            clickWithJS(waitForShort(By.xpath("//div[@class='ant-modal-body']//*[contains(text(),'新 增')]")));
        }catch (Exception e){
            clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-body']//*[contains(text(),'更 新')]")));
        }
    }

    public void checkAdvertiserListData(String type,String companyName) throws Exception{
        Utils.waitABit(1000);
        List<String> companyNameList =getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[3]")));
        List<String> advertiserTypeList =getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[4]")));
        if(!companyName.isEmpty()) {
            for (String value : companyNameList) {
                Assert.isContains(value,companyName,"广告主列表数据不正确:"+value);
            }
            for(String value:advertiserTypeList){
                Assert.isContains(value,type,"广告主列表数据不正确:"+value);
            }
        }
        else {
            throw new Exception("广告主列表无数据");
        }
    }

    public void checkAdvertiserListData(List<String> principalList) throws Exception{
        List<String> advertiserListData =getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[5]")));
        for(String principal :principalList) {
            for(String principalListData :advertiserListData){
                String[] a =principalListData.split(",");
                List<String> list = Arrays.asList(a);
                Assert.assertListContainsValue(list,principal,"广告主列表负责人与预期不符"+principal);
            }
        }
    }

    public void checkAdvertiserBoothData(String boothName) throws Exception{
        String boothList = waitFor(By.xpath("//a[contains(text(),'选择')]/../span")).getAttribute("innerText");
        Assert.isContains(boothList,boothName,"广告主已选广告位与预期不符:"+boothList);
    }
}
