package com.pajk.pages.adaisle;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ActivityPage extends WebDriverSteps {
    private static final Logger logger = LogManager.getLogger(ActivityPage.class);
    public ActivityPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    @FindBy(xpath = "//div/div[2]/button[@type='button']/span")
    public WebElement submit_btn;

    public void clickSubmitButon() throws Exception{
        clickWithJS(submit_btn);
        //Utils.waitABit(5000);
        //browserRefresh();
    }

    public void activityListClickButton(String button) throws Exception{
        try
        {
            if (button.equals("终 止")){
                WebElement element = waitFor(By.xpath("//span[contains(text(),'终 止')]"));
                clickWithJS(element);
                element.click();
                Utils.waitABit(1000);
                clickWithActions(element);
            }
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+button+"')]")));
            acceptAlert();
        }
        catch (Exception e)
        {

        }
    }

    public void chooseActivity(String activityname) throws Exception{
        waitFor(By.xpath("//td[contains(text(),'"+activityname+"')]/../td[1]/span")).click();
        Utils.waitABit(500);
    }

    public void selectActivityClickButton(String activityname,String button) throws Exception{
        clickWithJS(waitFor(By.xpath("//td[contains(text(),'"+activityname+"')]/..//*[contains(text(),'"+button+"')]")));
        Utils.waitABit(500);
    }

    public void checkActivityStatus(String status) throws Exception{
        Utils.waitABit(1500);
        String result=waitFor(By.xpath("//label[contains(@class,'ant-radio-wrapper-checked')]/../../../td[9]")).getAttribute("innerText");
        Assert.isEqual(status,result);
    }

    public void checkActivityStatus(String activityname,String status) throws Exception{
        Utils.waitABit(2000);
        try{
            List<String> statusList = getListElementsText(findAll(By.xpath("//tbody//tr/td[2][contains(text(),'"+activityname+"')]/../td[9]")));
            Assert.assertListContainsValue(statusList,status,"预期状态不正确:"+statusList);
        }catch (Exception e){
            browserRefresh();
            List<String> statusList = getListElementsText(findAll(By.xpath("//tbody//tr/td[2][contains(text(),'"+activityname+"')]/../td[9]")));
            Assert.assertListContainsValue(statusList,status,"预期状态不正确:"+statusList);
        }

    }

    public void fillActivityInformation(String activityname,String signupstartdate,String signupenddate,String acvtivitystartdate,String acvtivityenddate,String salestext,String commission) throws Exception{
        WebElement element = waitFor(By.id("activityName"));
        element.click();
        Utils.waitABit(500);
        putInValue(element,activityname);
/*        String js1 = "document.querySelector('span#activityPeriod>span>input:nth-child(1)').removeAttribute('readonly')";
        String js2 = "document.querySelector('span#registryPeriod>span>input:nth-child(3)').removeAttribute('readonly')";
        String js3 = "document.querySelector('span#activityPeriod>span>input:nth-child(1)').removeAttribute('readonly')";
        String js4 = "document.querySelector('span#activityPeriod>span>input:nth-child(3)').removeAttribute('readonly')";
        executeJS(js1);
        executeJS(js2);
        executeJS(js3);
        executeJS(js4);*/
/*        putInValue(By.xpath("//span[@id='registryPeriod']/span/input[1]"),signupstartdate);
        putInValue(By.xpath("//span[@id='registryPeriod']/span/input[2]"),signupenddate);*/
        //通过输出后选择日期输入日期控件
/*        clickWithJS(waitFor(By.xpath("//span[@id='registryPeriod']//input[@placeholder='开始日期']")));
        Utils.waitABit(500);
        putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"),signupstartdate);
        //clickWithJS(waitFor(By.xpath("//div[@class='ant-calendar-picker-container ant-calendar-picker-container-placement-bottomLeft']//td[@class='ant-calendar-cell ant-calendar-selected-start-date ant-calendar-selected-day']")));
        clickWithJS(waitFor(By.className("ant-calendar-selected-start-date")));

        clickWithJS(waitFor(By.xpath("//span[@id='registryPeriod']/span/input[@placeholder='结束日期']")));
        Utils.waitABit(500);
        putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='结束日期']"),signupenddate);
        //clickWithJS(waitFor(By.xpath("//div[@class='ant-calendar-picker-container ant-calendar-picker-container-placement-bottomLeft']//td[@class='ant-calendar-cell ant-calendar-selected-end-date ant-calendar-selected-day']")));
        clickWithJS(waitFor(By.className("ant-calendar-selected-end-date")));
        clickWithActions(element);

        clickWithJS(waitFor(By.xpath("//span[@id='activityPeriod']//input[@placeholder='开始日期']")));
        Utils.waitABit(500);
        putInValue(By.cssSelector("input.ant-calendar-input[placeholder='开始日期']"),acvtivitystartdate);
        clickWithJS(waitFor(By.className("ant-calendar-selected-start-date")));

        clickWithJS(waitFor(By.xpath("//span[@id='activityPeriod']/span/input[@placeholder='结束日期']")));
        Utils.waitABit(500);
        putInValue(waitFor(By.cssSelector("input.ant-calendar-input[placeholder='结束日期']")),acvtivityenddate);
        clickWithJS(waitFor(By.className("ant-calendar-selected-end-date")));*/

        inputDate("//span[@id='registryPeriod']//input[@placeholder='开始日期']",signupenddate,signupenddate);
        Utils.waitABit(2000);
        inputDate("//span[@id='activityPeriod']//input[@placeholder='开始日期']",acvtivitystartdate,acvtivityenddate);

        //通过JS设置Value
 /*     WebElement registryPeriodStartDate =waitFor(By.xpath("//span[@id='registryPeriod']//input[@placeholder='开始日期']"));
        WebElement registryPeriodEndDate =waitFor(By.xpath("//span[@id='registryPeriod']//input[@placeholder='结束日期']"));

        WebElement activityPeriodStartDate =waitFor(By.xpath("//span[@id='activityPeriod']//input[@placeholder='开始日期']"));
        WebElement activityPeriodEndDate =waitFor(By.xpath("//span[@id='activityPeriod']//input[@placeholder='结束日期']"));

        setValue(registryPeriodStartDate,signupstartdate);
        Utils.waitABit(1000);
        setValue(registryPeriodEndDate,signupenddate);
        Utils.waitABit(1000);
        setValue(activityPeriodStartDate,acvtivitystartdate);
        Utils.waitABit(1000);
        setValue(activityPeriodEndDate,acvtivityenddate);
        Utils.waitABit(1000);*/

        putInValue(By.cssSelector("span.ant-form-item-children>input[id=desc]"),salestext);
        putInValue(By.xpath("//input[@placeholder='请输入佣金门槛']"),commission);
    }

    public void fillActivityCoupon(String couponId) throws Exception{
        putInValue(By.id("couponsList"),couponId);
    }

}
