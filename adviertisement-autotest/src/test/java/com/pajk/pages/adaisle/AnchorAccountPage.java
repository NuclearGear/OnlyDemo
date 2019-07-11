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

import java.util.*;

public class AnchorAccountPage extends WebDriverSteps{
    private static final Logger logger = LogManager.getLogger(AnchorAccountPage.class);
    public AnchorAccountPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    public void anchorListClickButton(String button) throws Exception{
        if(button.equals("开启账户")){
            try{
                WebElement element = waitFor(By.xpath("//a[contains(text(),'开启账户')]"));
                clickWithJS(element);
            }catch (Exception e){
                clickWithJS(waitFor(By.xpath("//a[contains(text(),'关闭账户')]")));
                Utils.waitABit(2000);
                WebElement element = waitFor(By.xpath("//a[contains(text(),'开启账户')]"));
                clickWithJS(element);
            }
        }
        else if(button.equals("关闭账户")){
            try{
                WebElement element = waitFor(By.xpath("//a[contains(text(),'关闭账户')]"));
                clickWithJS(element);
            }catch (Exception e){
                clickWithJS(waitFor(By.xpath("//a[contains(text(),'开启账户')]")));
                Utils.waitABit(2000);
                WebElement element = waitFor(By.xpath("//a[contains(text(),'关闭账户')]"));
                clickWithJS(element);
            }
        }
        else {
            WebElement element = waitFor(By.xpath("//*[contains(text(),'" + button + "')]"));
            clickWithJS(element);
            }
        Utils.waitABit(500);
    }

    public void editAnchorClickButton(String button) throws Exception{
        Utils.waitABit(2000);
        if(button.equals("删除")){
            while(waitForVisibleOrNot(By.xpath("//div[@role='tabpanel'][@aria-hidden='false']//i[@class='anticon anticon-delete'][not(@title)]/*[name()='svg']"),1)){
                List<WebElement> elements = findAll(By.xpath("//div[@role='tabpanel'][@aria-hidden='false']//i[@class='anticon anticon-delete'][not(@title)]/*[name()='svg']"));
                for(WebElement element:elements){
                    scrollTo(element);
                    clickWithActions(element);
                    Utils.waitABit(1000);
                }
            }
        }
        else if(button.equals("置顶")){
            scrollToBase();
            Utils.waitABit(500);
            WebElement element = waitFor(By.xpath("//div[@role='tabpanel'][@aria-hidden='false']//tr[last()]//i[@class='anticon anticon-arrow-up'][not(@title)]/*[name()='svg']"));
            scrollTo(element);
            Utils.waitABit(500);
            clickWithActions(element);
            Utils.waitABit(1000);
        }
        else{
            WebElement element = waitFor(By.xpath("//span[contains(text(),'"+button+"')]"));
            clickWithJS(element);
        }
    }

    public void chooseGoodsBack() throws Exception{
        String url = getBrowserUrl();
        if(url.contains("chooseGoods")) {
            browserBack();
        }
        Utils.waitABit(1000);
        scrollToBase();
    }

    public void chooseGoodsClickButton(String button) throws Exception{
        Utils.waitABit(1500);
        WebElement element = waitFor(By.xpath("//span[contains(text(),'"+button+"')]"));
        clickWithJS(element);
        Utils.waitABit(500);
    }

    public void searchAnchor(HashMap searchdata) throws Exception{
        Iterator iterator = searchdata.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = searchdata.get(key).toString();
            if(key.equals("手机号")){
               putInValue(By.id("mobilePhone"),value);
            }
            else if (key.equals("主播名称")){
                putInValue(By.id("anchorName"),value);
            }
        }
        anchorListClickButton("查 询");
        Utils.waitABit(2000);
    }

    public void checkAnchorList(HashMap checkData) throws Exception{
        Iterator iterator = checkData.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = checkData.get(key).toString();
            if(key.equals("手机号")){
                String phone = waitFor(By.xpath("//div[@class='ant-table-body']//td[1]")).getAttribute("innerText");
                Assert.isEqual(value,phone);
            }
            else if (key.equals("主播名称")){
                String anchorname = waitFor(By.xpath("//div[@class='ant-table-body']//td[3]")).getAttribute("innerText");
                Assert.isEqual(value,anchorname);
            }
        }
    }

    public void addAnchor(String phone) throws Exception{
        Utils.waitABit(1000);
        putInValue(By.id("mobilePhone"),phone);
        anchorListClickButton("保 存");
    }

    public void fillAnchorInformation(HashMap anchordata) throws Exception{
        Iterator iterator = anchordata.keySet().iterator();
        while(iterator.hasNext()) {
            String key = iterator.next().toString();
            String value = anchordata.get(key).toString();
            if(key.equals("引导话术")){
                WebElement element =waitFor(By.id("0_guideWords"));
                putInValue(element,value);
            }
            else if(key.equals("优惠券")){
                WebElement element =waitFor(By.xpath("//input[@id='0_coupons']"));
/*                clickWithJS(element);
                String[] couponsList=value.split(",");
                for(String coupons:couponsList){
                    putInValue(element,coupons);
                }*/
                boolean result = waitForVisibleOrNot(By.xpath("//span[@class='ant-select-selection__choice__remove']"),1);
                if(result){
                    List<WebElement>deletebutton =findAll(By.xpath("//span[@class='ant-select-selection__choice__remove']"));
                    for(WebElement button:deletebutton){
                        button.click();
                    }
                }
                putInValue(element,value+",");
            }
        }
        anchorListClickButton("确 认");
    }

    public List<String> getChooseGoodsData() throws Exception{
        Utils.waitABit(2000);
        List<String> goodstitle = this.getListElementsText(findAll(By.xpath("//div[@class='ant-spin-container']//div[@class='title']")));

        List<String> goodsstatus = this.getListElementsText(findAll(By.xpath("//div[@class='ant-spin-container']//button/span")));
        List<String> choosegoodsdata = new LinkedList<String>();
        for(int i = 0;i<goodstitle.size();i++) {
            if (goodsstatus.get(i).equals("取消商品")){
                String title = goodstitle.get(i).replace("【直播专享】","");
                choosegoodsdata.add(title);
            }
        }
        return choosegoodsdata;
    }

    public List<String> getAnchorChooseGoods() throws Exception{
        WebElement element =waitFor(By.xpath("//thead[@class='ant-table-thead']/tr/th[1]"));
        scrollTo(element);
        Utils.waitABit(4000);
        List<String> anchorchoosegoods = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr//h3")));
        for (int i = 0;i<anchorchoosegoods.size();i++){
            if(anchorchoosegoods.get(i).contains("活动")){
                anchorchoosegoods.set(i,anchorchoosegoods.get(i).replace("活动",""));
            }
        }
        return anchorchoosegoods;
    }

    public void checkChooseGoods(List<String> data) throws Exception{
        List<String> anchorchoosegoods = getAnchorChooseGoods();
        logger.info("主播已选商品列表:"+anchorchoosegoods);
        for (int i=0;i<data.size();i++){
            Assert.assertListContainsValue(anchorchoosegoods,data.get(i),"选品页与已选列表商品不一致");
        }
    }

    public void checkAnchorChooseGoods(List<String> data) throws Exception{
        Utils.waitABit(3000);
        List<String> anchorchoosegoods = getAnchorChooseGoods();
        logger.info("置顶后主播已选商品列表:"+anchorchoosegoods);
        Assert.isEqual(data.get(data.size()-1),anchorchoosegoods.get(0));
    }

    public void checkAnchorDeleteGoods() throws Exception{
        Utils.waitABit(3000);
        List<String> anchorchoosegoods = getAnchorChooseGoods();
        logger.info("删除商品后主播商品列表:"+anchorchoosegoods);
        Assert.isEqual(0,anchorchoosegoods.size());
    }



}
