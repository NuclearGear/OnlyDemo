package com.pajk.pages.adbackend;

import com.google.common.base.Joiner;
import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BoothPage extends WebDriverSteps  {
    private static final Logger logger = LogManager.getLogger(BoothPage.class);
    public BoothPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    public void boothPageClickButton(String button) throws Exception{
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithJS(element);
    }

    public void searchBooth(HashMap data) throws Exception{
/*        if(data.containsKey("广告位名称")){
            String value = data.get("广告位名称").toString();
            putInValue(By.id("boothName"),value);
        }
        if(data.containsKey("频道")){
            String value = data.get("频道").toString();
            clickWithJS(waitFor(By.xpath("//div[@class='advertiserList']/form/div[2]")));
            clickWithJS(waitFor(By.xpath("//li[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("类型")){
            String value = data.get("类型").toString();
            clickWithJS(waitFor(By.xpath("//div[@class='advertiserList']/form/div[3]")));
            clickWithJS(waitFor(By.xpath("//li[contains(text(),'"+value+"')]")));
        }
        boothPageClickButton("查 询");
        Utils.waitABit(2000);*/
        String value = data.get("广告位名称").toString();
        putInValue(By.id("boothName"),value);
        clickWithJS(waitFor(By.xpath("//button[@type='submit']")));
        putInValue(By.id("boothName"),value);
        clickWithJS(waitFor(By.xpath("//button[@type='submit']")));
        Utils.waitABit(2000);
    }

    public void fillBoothInformation(HashMap data) throws Exception{
        if(data.containsKey("广告位名称")){
            String value = data.get("广告位名称").toString();
            putInValue(By.id("boothName"),value);
        }
        if(data.containsKey("广告位Code")){
            String value = data.get("广告位Code").toString();
            putInValue(By.id("boothCode"),value);
        }
        if(data.containsKey("帧数")){
            String value = data.get("帧数").toString();
            putInValue(By.id("frameCount"),value);
        }
        if(data.containsKey("资源位类型")){
            String value = data.get("资源位类型").toString();
            clickWithJS(waitFor(By.xpath("//label[@for='resourceType']/../following-sibling::div//span[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("资源刊例价")){
            String value = data.get("资源刊例价").toString();
            String[] val=value.split(",");
            for(int i=0;i<val.length;i++){
                String[] text=val[i].split("=");
                putInValue(By.xpath("//span[contains(text(),'"+text[0]+"')]/following-sibling::*[@class='ant-input']"),text[1]);
            }
        }
        if(data.containsKey("所属频道")){
            String value = data.get("所属频道").toString();
            clickWithJS(waitFor(By.xpath("//label[@for='positionId']/../following-sibling::div//span[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("投放平台")){
            List<WebElement> elements =findAll(By.xpath("//*[contains(text(),'投放平台')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
            for(WebElement element:elements){
                element.click();
                Utils.waitABit(500);
            }
            String value = data.get("投放平台").toString();
            if(value.contains(",")){
                String[] val=value.split(",");
                for(int i=0;i<val.length;i++){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'投放平台')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/..//input")));
                }
            }else {
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'投放平台')]/../following-sibling::*//*[contains(text(),'"+value+"')]/..//input")));
            }
        }
        if(data.containsKey("广告投放策略")){
            String value = data.get("广告投放策略").toString();
            clickWithJS(waitFor(By.xpath("//label[@for='deliveryStrategy']/../following-sibling::div//span[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("素材模版类型")){
            String value = data.get("素材模版类型").toString();
            clickWithJS(waitFor(By.xpath("//label[@for='style']/../following-sibling::div//span[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("广告位特殊说明")){
            String value = data.get("广告位特殊说明").toString();
            putInValue(By.id("comment"),value);
        }
        if(data.containsKey("素材模板")){
            if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'素材模板')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"),1)){
                List<WebElement> elements =findAll(By.xpath("//*[contains(text(),'素材模板')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
                for(WebElement element:elements){
                    element.click();
                    Utils.waitABit(500);
                }
            }
            String value = data.get("素材模板").toString();
            if(value.contains(",")){
                String[] val=value.split(",");
                for(int i=0;i<val.length;i++){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'素材模板')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/..//input")));
                }
            }else {
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'素材模板')]/../following-sibling::*//*[contains(text(),'"+value+"')]/..//input")));
            }
        }
        if(data.containsKey("是否可关闭")){
            String value = data.get("是否可关闭").toString();
            clickWithJS(waitFor(By.xpath("//label[@for='closeable']/../following-sibling::div//span[contains(text(),'"+value+"')]")));
        }
    }

    public void fillBoothTargetTypeInformation(String targetType) throws Exception{
        clickWithJS(waitFor(By.xpath("//label[@title='定向条件']/../following-sibling::div//a")));
        Utils.waitABit(500);
        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'全部不选')]")));
        if(targetType.contains(",")){
            String[] val = targetType.split(",");
            for(int i=0;i<val.length;i++){
                clickWithActions(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'"+val[i]+"')]")));
            }
        }else{
            clickWithActions(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'"+targetType+"')]")));
        }
        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'确 定')]")));
    }

    public void fillBoothLinkTypeInformation(String linkType) throws Exception{
        clickWithJS(waitFor(By.xpath("//label[@title='跳转类型']/../following-sibling::div//a")));
        Utils.waitABit(500);
        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'全部不选')]")));
        if(linkType.contains(",")){
            String[] val=linkType.split(",");
            for(int i=0;i<val.length;i++){
                clickWithActions(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'"+val[i]+"')]")));
            }
        }else{
            clickWithActions(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'"+linkType+"')]")));
        }
        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[contains(text(),'确 定')]")));
    }

    public void fillBoothSupportTgtInformation(String templateName,String filledFrames,String floorPrice) throws Exception {
        clickWithJS(waitFor(By.xpath("//label[@title='是否推广通']/../following-sibling::div//span[contains(text(),'是')]")));
        Utils.waitABit(500);
        if (filledFrames.contains(",")) {
            String[] val = filledFrames.split(",");
            for (int i = 0; i < val.length; i++) {
                clickWithActions(waitFor(By.xpath("//label[@for='filledFrames']/../following-sibling::div//span[contains(text(),'" + val[i] + "')]")));
            }
        }
        clickWithJS(waitFor(By.xpath("//span[contains(text(),'推广通素材模版')]/../../following-sibling::div//div[@class='ant-select ant-select-enabled']")));
        Utils.waitABit(1000);
        clickWithJS(waitFor(By.xpath("//li[contains(text(),'" + templateName + "')]")));
        putInValue(By.id("floorPrice"), floorPrice);
    }

    public void fillBoothSupportDspInformation(String dspName) throws Exception{
        clickWithJS(waitFor(By.xpath("//label[@title='是否程序化']/../following-sibling::div//span[contains(text(),'是')]")));
        if(dspName.contains(",")){
            String[] val=dspName.split(",");
            for(int i=0;i<val.length;i++){
                clickWithActions(waitFor(By.xpath("//label[@title='投放渠道']/../following-sibling::div//span[contains(text(),'"+val[i]+"')]")));
            }
        }
        else {
            clickWithActions(waitFor(By.xpath("//label[@title='投放渠道']/../following-sibling::div//span[contains(text(),'"+dspName+"')]")));
        }
        List<WebElement> SupportDsp =findAll(By.xpath("//label[@title='投放渠道']/../following-sibling::div//button"));
        for(WebElement element:SupportDsp){
            clickWithJS(element);
            putInValue(waitFor(By.id("frameNo")),"1");
            putInValue(waitFor(By.xpath("//label[@title='Android广告位ID']/../following-sibling::div//input")),"1");
            putInValue(waitFor(By.xpath("//label[@title='IOS广告位ID']/../following-sibling::div//input")),"1");
            //Android素材模板
            clickWithJS(waitFor(By.xpath("//label[@title='Android广告位ID']/../following-sibling::div//div[@class='ant-select ant-select-enabled']")));
            Utils.waitABit(500);
            clickWithJS(waitFor(By.xpath("//body/div[contains(@style,'position')][2]//li[1]")));
            //Ios素材模板
            Utils.waitABit(1000);
            clickWithJS(waitFor(By.xpath("//label[@title='IOS广告位ID']/../following-sibling::div//div[@class='ant-select ant-select-enabled']")));
            Utils.waitABit(500);
            clickWithJS(waitFor(By.xpath("//body/div[contains(@style,'position')][last()]//li[1]")));
            clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-footer']//span[contains(text(),'确 定')]")));
        }
    }

    public void clickUploadButton() throws Exception {
        Utils.waitABit(500);
        clickWithActions(waitFor(By.xpath("//label[@for='upload']/../following-sibling::div//span[@class='ant-upload']")));
    }

    public Map<String,String> getBoothInformation() throws Exception{
        List<String> headers = getListElementsText(findAll(By.xpath("//form[@class='ant-form ant-form-horizontal']//div[@class='ant-col-6 ant-form-item-label']")));
        Map<String,String> boothInformation = new LinkedHashMap<String, String>();
        for(int i=0;i<headers.size();i++){
            String key = headers.get(i);
            if(key.equals("广告位名称")||key.equals("广告位Code")||key.equals("帧数")||key.equals("新用户定义")||key.equals("底价")){
                String value = waitFor(By.xpath("//*[contains(text(),'"+key+"')]/../following-sibling::div//input")).getAttribute("defaultValue");
                boothInformation.put(headers.get(i),value);
            }
            else if(key.equals("资源位类型")||key.equals("所属频道")||key.equals("广告投放策略")||key.equals("用户可见策略")||key.equals("素材模版类型")||key.equals("是否可关闭")||key.equals("是否推广通")||key.equals("关键字来源")||key.equals("是否屏蔽同品类")||key.equals("计价方式")||key.equals("是否程序化")){
                String value = waitFor(By.xpath("//*[contains(text(),'"+key+"')]/../following-sibling::div//*[@class='ant-radio ant-radio-checked']/following-sibling::*")).getAttribute("innerText");
                boothInformation.put(headers.get(i),value);
            }
            else if(key.equals("投放平台")||key.equals("素材模板")||key.equals("投放渠道")){
                List<String> valueList =getListElementsText(findAll(By.xpath("//*[contains(text(),'"+key+"')]/../following-sibling::div//*[@class='ant-checkbox ant-checkbox-checked']/../span[2]")));
                String value = Joiner.on(",").join(valueList);
                boothInformation.put(headers.get(i),value);
            }
            else if(key.equals("定向条件")||key.equals("跳转类型")||key.equals("广告投放策略")||key.equals("用户可见策略")){
                String value = waitFor(By.xpath("//*[contains(text(),'"+key+"')]/../following-sibling::div//span/span/span")).getAttribute("innerText");
                boothInformation.put(headers.get(i),value);
            }
            else if(key.equals("资源刊例价")){
                List<String> valueList = getListElementsText(findAll(By.xpath("//*[contains(text(),'资源刊例价')]/../../..//input")));
                String cpm="cpm="+valueList.get(0);
                String cpc="cpc="+valueList.get(1);
                String cpd="cpd="+valueList.get(2);
                String cpa="cpa="+valueList.get(3);
                String cps="cps="+valueList.get(4);
                boothInformation.put(headers.get(i),cpm+","+cpc+","+cpd+","+cpa+","+cps);
            }
            else if(key.equals("广告位特殊说明")){
                String value = waitFor(By.xpath("//*[contains(text(),'广告位特殊说明')]/../following-sibling::div//textarea")).getAttribute("defaultValue");
                boothInformation.put(headers.get(i),value);
            }
            else if(key.contains("推广通素材模版")){
                String value = waitFor(By.xpath("//span[contains(text(),'推广通素材模版')]/../../following-sibling::div//div[@class='ant-select ant-select-enabled']//div[@class='ant-select-selection-selected-value']")).getAttribute("innerText");
                boothInformation.put("推广通素材模版",value);
            }
            else if(key.contains("插入帧数")){
                List<String> valueList =getListElementsText(findAll(By.xpath("//*[contains(text(),'插入帧数')]/../../following-sibling::*//*[@class='ant-checkbox ant-checkbox-checked']/../span[2]")));
                String value = Joiner.on(",").join(valueList);
                boothInformation.put("插入帧数",value);
            }
        }
        return boothInformation;
    }

    public void checkBoothData(HashMap<String,String>checkData) throws Exception{
        HashMap<String,String> boothData = (HashMap<String,String>)getBoothInformation();
        Assert.assertHashMapAContainsHashMapB(boothData,checkData);
        //Assert.assertHashMapAValueContainsHashMapBValue(boothData,checkData);
    }

}
