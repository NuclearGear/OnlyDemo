package com.pajk.pages.adbackend;

import com.google.common.base.Joiner;
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

import java.util.*;

import static com.pajk.utils.WebDriverUtils.getRowData;

public class PlanPage extends WebDriverSteps  {
    private static final Logger logger = LogManager.getLogger(PlanPage.class);
    public PlanPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    @FindBy(xpath = "//button[@class='ant-btn']//span[contains(text(),'点击上传')]")
    public WebElement upButton;

    public void planPageClickButton(String button) throws Exception{
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithJS(element);
        if(button.equals("编辑")){
            Utils.waitABit(2000);
        }
    }

    public void submitPlan() throws Exception{
        Utils.waitABit(1000);
        WebElement element = waitFor(By.xpath("//button[@type='submit']"));
        clickWithJS(element);
        try {
            Utils.waitABit(3000);
            WebElement element1 = waitForShort(By.xpath("//*[contains(text(),'继续保存')]"));
            clickWithJS(element1);
        }catch (Exception e){}
    }

    public void searchPlan(HashMap searchData) throws Exception{
        Iterator iterator = searchData.keySet().iterator();
        while(iterator.hasNext()) {
            String key=iterator.next().toString();
            String value=searchData.get(key).toString();
            Utils.waitABit(1000);
            if(key.equals("投放名称")){
                putInValue(By.id("planName"), value);
            }
            else if(key.equals("开始日期")){
                clickWithJS(waitFor(By.xpath("//input[@placeholder='开始日期']")));
                putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"),value);
            }
            else if(key.equals("结束日期")){
                //clickWithJS(waitFor(By.xpath("//input[@placeholder='结束日期']")));
                putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='结束日期']"),value);
            }
            else if(key.equals("广告位名称")){
                //String js = "document.querySelector('span#activityPeriod>span>input:nth-child(1)').removeAttribute('readonly')";
                WebElement element = waitFor(By.xpath("//form[normalize-space(@class='tabFormGlobalStyle')]/div[3]//div[@class='ant-select-selection__placeholder']"));
                element.click();
                putInValue(By.id("boothCodes"),value);
                //element.sendKeys(Keys.ENTER);
                waitFor(By.xpath("//li[@unselectable='unselectable'][contains(text(),'"+value+"')]")).click();
                Utils.waitABit(500);
                waitFor(By.id("planName")).click();

            }
            else if(key.equals("投放状态")){
                waitFor(By.xpath("//form[normalize-space(@class='tabFormGlobalStyle')]/div[4]//div[@class='ant-select-selection__rendered']/div[@class='ant-select-selection-selected-value']")).click();
                WebElement element = waitFor(By.xpath("//li[@class='ant-select-dropdown-menu-item'][contains(text(),'"+value+"')]"));
                clickWithJS(element);
            }
        }
        WebElement element = waitFor(By.xpath("//button[@type='submit']"));
        clickWithJS(element);
        Utils.waitABit(2000);
    }

    public void fillPlanInformation(HashMap data) throws Exception{
        Iterator iterator = data.keySet().iterator();
        while(iterator.hasNext()) {
            String key= iterator.next().toString();
            String value= data.get(key).toString();
            Utils.waitABit(1000);
            if(key.equals("投放名称")){
                putInValue(By.id("planName"), value);
            }
            else if(key.equals("开始日期")){
                if(!waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='开始日期']")).getAttribute("defaultValue").equals(value)){
                    clickWithJS(waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='开始日期']")));
                    putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"),value);
                    Utils.waitABit(500);
                    clickWithActions(waitFor(By.id("planName")));
                }
            }
            else if(key.equals("结束日期")){
                if(!waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='结束日期']")).getAttribute("defaultValue").equals(value)){
                    clickWithJS(waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='结束日期']")));
                    putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='结束日期']"),value);
                    Utils.waitABit(500);
                    clickWithActions(waitFor(By.id("planName")));
                }
            }
            else if(key.equals("广告类型")){
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+value+"')]/..//span[@class='ant-radio-inner']")));
            }
            else if(key.equals("售卖模式")){
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+value+"')]/..//span[@class='ant-radio-inner']")));
            }
            else if(key.equals("售卖价格")){
                putInValue(By.id("sellPrice"),value);
            }
            else if(key.equals("售卖预算")){
                putInValue(By.id("budget"),value);
            }
            else if(key.equals("广告位名称")){
                //String js = "document.querySelector('span#activityPeriod>span>input:nth-child(1)').removeAttribute('readonly')";
                WebElement element = waitFor(By.xpath("//input[@class='ant-select-search__field']"));
                scrollTo(element);
                element.click();
                putInValue(element,value);
                //element.sendKeys(Keys.ENTER);
                waitFor(By.xpath("//li[@unselectable='unselectable'][contains(text(),'"+value+"')]")).click();
                Utils.waitABit(500);
            }
            else if(key.equals("帧位")){
                try{
                    //clickWithActions(waitFor(By.xpath("//div[@id='frameNo']//span[contains(text(),'"+value+"')]")));
                    clickWithJS(waitFor(By.xpath("//div[@id='frameNo']/label["+value+"]/span[1]")));
                }catch (Exception e){
                    clickWithJS(waitFor(By.xpath("//div[@id='frameNo']/label[1]//span[1]")));
                }
            }
        }
        //帧位
        if(!data.containsKey("帧位")){
            clickWithJS(waitFor(By.xpath("//div[@id='frameNo']/label[1]//span[1]")));
            Utils.waitABit(1000);
        }
        //素材模板
        clickWithJS(waitFor(By.xpath("//div[contains(text(),'素材设置')]/..//form/div[1]//div[@class='ant-select-selection__rendered']")));
        Utils.waitABit(500);
        clickWithJS(waitFor(By.xpath("//body/div[normalize-space(@style='position:')][last()]//ul/li[1]")));
        //跳转链接
        Utils.waitABit(500);
        if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'跳转链接')]/../following-sibling::*//div[@class='ant-select-selection__rendered']"),1)){
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'跳转链接')]/../following-sibling::*//div[@class='ant-select-selection__rendered']")));
            Utils.waitABit(500);
            clickWithJS(waitFor(By.xpath("//body/div[normalize-space(@style='position:')][last()]//ul/li[1]")));
        }
        if(data.containsKey("商品SPUID")){
            WebElement element = waitFor(By.id("elm_bizContentId"));
            putInValue(element,data.get("商品SPUID").toString());
        }
        if(waitForVisibleOrNot(By.id("link_url"),1)){
            if(data.containsKey("跳转链接")){
                putInValue(By.id("link_url"),data.get("跳转链接").toString());
            }else {
                putInValue(By.id("link_url"),"https://www.baidu.com/");
            }
        }
        if(waitForVisibleOrNot(By.xpath("//*[@title='标题']/../following-sibling::*//input"),1)){
            putInValue(By.xpath("//*[@title='标题']/../following-sibling::*//input"),"UI-自动化");
        }
        if(waitForVisibleOrNot(By.xpath("//*[@title='副标题']/../following-sibling::*//input"),1)){
            putInValue(By.xpath("//*[@title='副标题']/../following-sibling::*//input"),"UI-自动化");
        }


    }

    public void fillPlanTarget(HashMap data) throws Exception{
        clickWithJS(waitFor(By.xpath("//*[contains(text(),'开启定向')]/../following-sibling::*//span[contains(text(),'是')]/..//input")));
        if(data.containsKey("渠道定向")){
            List<WebElement> elements =findAll(By.xpath("//*[contains(text(),'渠道定向')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
            for(WebElement element:elements){
                element.click();
                Utils.waitABit(500);
            }
            String value = data.get("渠道定向").toString();
            if(value.contains(",")){
                String[] val=value.split(",");
                for(int i=0;i<val.length;i++){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'渠道定向')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/..//input")));
                }
            }else {
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'渠道定向')]/../following-sibling::*//*[contains(text(),'"+value+"')]/..//input")));
            }
        }
        if(data.containsKey("地域定向")){
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'地域定向')]/../following-sibling::*//*[contains(text(),'编辑')]")));
            Utils.waitABit(1000);
            planPageClickButton("全部不选");
            String value = data.get("地域定向").toString();
            if(value.contains(",")){
                String[] val=value.split(",");
                for(int i=0;i<val.length;i++){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+val[i]+"')]/..//input")));
                }
            }else {
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+value+"')]/..//input")));
            }
            planPageClickButton("确 定");
        }
//        if(data.containsKey("时间定向")){
//            String value = data.get("时间定向").toString();
//            String[] val=value.split(",");
//            WebElement element1 =waitFor(By.xpath("//*[contains(text(),'时间定向')]/../following-sibling::*//span/span[1]/input"));
//            WebElement element2 =waitFor(By.xpath("//*[contains(text(),'时间定向')]/../following-sibling::*//span/span[2]/input"));
//
//            element1.click();
//            clickWithActions(waitFor(By.xpath("//a[@class='ant-time-picker-panel-clear-btn']")));
//            Utils.waitABit(500);
//            element1.click();
//            putInValue(element1,val[0]);
//
//            element2.click();
//            Utils.waitABit(500);
//            clickWithActions(waitFor(By.xpath("//a[@class='ant-time-picker-panel-clear-btn']")));
//            element2.click();
//            putInValue(element2,val[1]);
//            clickWithJS(waitFor(By.xpath("//*[contains(text(),'开启定向')]/../following-sibling::*//span[contains(text(),'是')]/..//input")));
//        }
        if(data.containsKey("性别定向")){
            String value = data.get("性别定向").toString();
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'性别定向')]/../following-sibling::*//div[@class='ant-select-selection-selected-value']")));
            Utils.waitABit(1000);
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'"+value+"')]")));
        }
        if(data.containsKey("关键字定向")){
            if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']//span[@class='ant-select-selection__choice__remove']"),1)){
                List<WebElement> romove =findAll(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']//span[@class='ant-select-selection__choice__remove']"));
                for(WebElement element:romove){
                    element.click();
                }
            }
            String value = data.get("关键字定向").toString();
            putInValue(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//input[@id='keywords']"),value+",");
            Utils.waitABit(1000);
        }
        if(data.containsKey("频次定向")){
            String value = data.get("频次定向").toString();
            String[] val=value.split(",");
            putInValue(By.xpath("//*[contains(text(),'频次定向')]/../following-sibling::*//span[@class='ant-form-item-children']//span/div[1]//input"),val[0]);
            Utils.waitABit(1000);
            putInValue(By.xpath("//*[contains(text(),'频次定向')]/../following-sibling::*//span[@class='ant-form-item-children']//span/div[2]//input"),val[1]);
        }
        if(data.containsKey("科室定向")){
            if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'科室定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']//span[@class='ant-select-selection__choice__remove']"),1)){
                List<WebElement> romove =findAll(By.xpath("//*[contains(text(),'科室定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']//span[@class='ant-select-selection__choice__remove']"));
                for(WebElement element:romove){
                    element.click();
                    Utils.waitABit(1000);
                }
            }
            String value = data.get("科室定向").toString();
            putInValue(By.xpath("//*[contains(text(),'科室定向')]/../following-sibling::*//input[@id='offices']"),value+",");
        }
        if(data.containsKey("APP平台定向")){
            List<WebElement> elements =findAll(By.xpath("//*[contains(text(),'APP平台定向')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
            for(WebElement element:elements){
                element.click();
                Utils.waitABit(500);
            }
            String value = data.get("APP平台定向").toString();
            if(value.contains(",")){
                String[] val=value.split(",");
                for(int i=0;i<val.length;i++){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'APP平台定向')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/..//input")));
                    Utils.waitABit(500);
                }
            }else {
                clickWithJS(waitFor(By.xpath("//*[contains(text(),'APP平台定向')]/../following-sibling::*//*[contains(text(),'"+value+"')]/..//input")));
                Utils.waitABit(500);
            }
        }
        if(data.containsKey("版本号屏蔽管理")){
            String value = data.get("版本号屏蔽管理").toString();
            if(value.contains("，")){
                String[] val=value.split("，");
                for(int i=0;i<val.length;i++){
                    if(val[i].contains("Ios")){
                        clickWithJS(waitFor(By.xpath("//*[contains(text(),'版本号屏蔽管理')]/../following-sibling::*//*[contains(text(),'Ios')]")));
                        Utils.waitABit(500);
                    }
                    else if(val[i].contains("Android")){
                        clickWithJS(waitFor(By.xpath("//*[contains(text(),'版本号屏蔽管理')]/../following-sibling::*//*[contains(text(),'Android')]")));
                        Utils.waitABit(500);
                    }
                    if(waitForVisibleOrNot(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"),1)){
                        List<WebElement> romove =findAll(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"));
                        for(WebElement element:romove){
                            element.click();
                            Utils.waitABit(500);
                        }
                    }
                    ///waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][2]//input[@class='ant-input']")).clear();
                    //waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][3]//input[@class='ant-input']")).clear();
                    if(val[i].contains("=")){
                        String[] text=val[i].split("=",2);
                            putInValue(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//input[@id='_equal']")),text[1]+",");
                            Utils.waitABit(500);
                            clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
                    }
                    else if(val[i].contains(">")){
                        String[] text=val[i].split(">",2);
                        putInValue(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][2]//input[@class='ant-input']"),text[1]);
                        Utils.waitABit(500);
                        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
                    }
                    else if(val[i].contains("<")){
                        String[] text=val[i].split("<",2);
                        putInValue(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][3]//input[@class='ant-input']"),text[1]);
                        Utils.waitABit(500);
                        clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
                    }
                }
            }else {
                if(value.contains("Ios")){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'版本号屏蔽管理')]/../following-sibling::*//*[contains(text(),'Ios')]")));
                    Utils.waitABit(500);
                }
                else if(value.contains("Android")){
                    clickWithJS(waitFor(By.xpath("//*[contains(text(),'版本号屏蔽管理')]/../following-sibling::*//*[contains(text(),'Android')]")));
                    Utils.waitABit(500);
                }
                if(waitForVisibleOrNot(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"),1)){
                    List<WebElement> romove =findAll(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"));
                    for(WebElement element:romove){
                        element.click();
                        Utils.waitABit(500);
                    }
                }
                if(value.contains("=")){
                    String[] text=value.split("=",2);
                    putInValue(waitFor(By.id("_equal")),text[1]+",");
                }
                else if(value.contains(">")){
                    String[] text=value.split(">",2);
                    putInValue(By.xpath("//div[@class='ant-modal-body']//div[@class='ant-row ant-form-item'][2]//input[@class='ant-input']"),text[1]);
                }
                else if(value.contains("<")){
                    String[] text=value.split("<",2);
                    putInValue(By.xpath("//div[@class='ant-modal-body']//div[@class='ant-row ant-form-item'][3]//input[@class='ant-input']"),text[1]);
                }
                Utils.waitABit(1000);
                clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
            }
        }
        if(data.containsKey("用户定向")){
            String value = data.get("用户定向").toString();
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'用户定向')]/../following-sibling::*//*[contains(text(),'设置')]")));
            Utils.waitABit(500);
            clickWithJS(waitFor(By.xpath("//*[contains(text(),'BI')]")));
            if(waitForVisibleOrNot(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"),1)){
                List<WebElement> romove =findAll(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//span[@class='ant-select-selection__choice__remove']"));
                for(WebElement element:romove){
                    element.click();
                    Utils.waitABit(500);
                }
            }
            putInValue(By.id("userLabels"),value+",");
            clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
            Utils.waitABit(500);
        }
    }

    public Map<String, String> getPlanInformation() throws Exception{

        //List<WebElement> checkedHeaders = findAll(By.xpath("//span[@class='ant-radio ant-radio-checked']/../../../../../..//label[@class='ant-form-item-required']"));
        //String checkedInformationPath = "//span[@class='ant-radio ant-radio-checked']/following-sibling::*";
        //LinkedHashMap<String, String> checkedInformation = getRowData(checkedInformationPath+"/../../../../../..//label[@class='ant-form-item-required']",checkedInformationPath,checkedHeaders.size(),driver);
        Utils.waitABit(3000);
        List<String> checkedValues = getListElementsText(findAll(By.xpath("//span[@class='ant-radio ant-radio-checked']/following-sibling::*")));
        List<String> inputValues = getListElementsText(findAll(By.cssSelector("input.ant-input")));


        String boothName =waitFor(By.xpath("//span[@class='ant-radio ant-radio-checked ant-radio-disabled']/following-sibling::*")).getAttribute("innerText");
        String startDate =waitFor(By.xpath("//span[@class='ant-calendar-picker']//input[@placeholder='开始日期']")).getAttribute("defaultValue");
        String endDate =waitFor(By.xpath("//span[@class='ant-calendar-picker']//input[@placeholder='结束日期']")).getAttribute("defaultValue");
        String targettingFlag =waitFor(By.xpath("//label[@title='开启定向']/../..//span[@class='ant-radio ant-radio-checked']/../span[2]")).getAttribute("innerText");
        String linkUrl =waitFor(By.xpath("//input[contains(@id,'link_')]")).getAttribute("defaultValue");


        Map<String,String> planInformation = new LinkedHashMap<String,String>();
        //planInformation.putAll();
        if(inputValues.size()>=4){
            planInformation.put("投放名称",inputValues.get(0));
            planInformation.put("售卖价格",inputValues.get(1));
            planInformation.put("售卖预算",inputValues.get(2));
        }
        else if(inputValues.size()==2||inputValues.size()==3){
            planInformation.put("投放名称",inputValues.get(0));
        }
        planInformation.put("广告位",boothName);
        planInformation.put("开始日期",startDate);
        planInformation.put("结束日期",endDate);
        planInformation.put("跳转链接",linkUrl);
        planInformation.put("定向",targettingFlag);
        if(checkedValues.size()>=6){
            planInformation.put("广告类型",checkedValues.get(0));
            planInformation.put("售卖模式",checkedValues.get(1));
            planInformation.put("流量控制",checkedValues.get(2));
            planInformation.put("帧位",checkedValues.get(3));
            String text =waitFor(By.xpath("//*[contains(text(),'是否显示广告标')]/../following-sibling::*//span[@class='ant-radio ant-radio-checked']/following-sibling::*")).getAttribute("innerText");
            planInformation.put("广告标",text);
            }
        else if(checkedValues.size()>=2&&checkedValues.size()<=5){
            planInformation.put("帧位",checkedValues.get(0));
        }
        if(planInformation.get("定向").equals("是")){
        //List<WebElement> targetList = findAll(By.xpath("//div[@class='ant-collapse']/div[3]//div/div[@class='ant-row ant-form-item']"));
            List<String> targetHeaders = getListElementsText(findAll(By.xpath("//div[@class='ant-collapse']/div[3]//div/div[@class='ant-row ant-form-item']/div[1]//label")));
            if(targetHeaders.contains("渠道定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'渠道定向')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']/following-sibling::*")));
                if(targetValue.size()==1){
                    planInformation.put("渠道定向",targetValue.get(0));
                }else {
                    String value = Joiner.on(",").join(targetValue);
                    planInformation.put("渠道定向",value);
                }
            }
            if(targetHeaders.contains("地域定向")){
                String targetValue = waitFor(By.xpath("//*[contains(text(),'地域定向')]/../following-sibling::*//span[@style]")).getAttribute("innerText");
                planInformation.put("地域定向",targetValue);
            }
            if(targetHeaders.contains("时间定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'时间定向')]/../following-sibling::*//input")));
                planInformation.put("时间定向",targetValue.get(0)+"至"+targetValue.get(1));
            }
            if(targetHeaders.contains("性别定向")){
                String targetValue = waitFor(By.xpath("//*[contains(text(),'性别定向')]/../following-sibling::*//div[@class='ant-select-selection-selected-value']")).getAttribute("innerText");
                planInformation.put("性别定向",targetValue);
            }
            if(targetHeaders.contains("频次定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'频次定向')]/../following-sibling::*//input")));
                planInformation.put("频次定向","单一用户"+targetValue.get(0)+"分钟"+targetValue.get(1)+"次广告");
            }
            if(targetHeaders.contains("APP平台定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'APP平台定向')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']/following-sibling::*")));
                if(targetValue.size()==1){
                    planInformation.put("APP平台定向",targetValue.get(0));
                }else {
                    String value = Joiner.on(",").join(targetValue);
                    planInformation.put("APP平台定向",value);
                }
            }
            if(targetHeaders.contains("版本号屏蔽管理")) {
                List<WebElement> elements =findAll(By.xpath("//*[contains(text(),'版本号屏蔽管理')]/../following-sibling::*//button[not(@disabled)]/span"));
                for(WebElement element:elements){
                    clickWithJS(element);
                    String text = element.getAttribute("innerText");
                    if(waitForVisibleOrNot(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//li[@class='ant-select-selection__choice']"),1)){
                        List<String> targetValue = getListElementsText(findAll(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//li[@class='ant-select-selection__choice']")));
                        String val = Joiner.on(",").join(targetValue);
                        planInformation.put("版本号屏蔽管理"+text+"=",val);
                        }
                    else {
                        String value =waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][2]//input[@class='ant-input']")).getAttribute("defaultValue");
                        String value2 =waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//div[@class='ant-row ant-form-item'][3]//input[@class='ant-input']")).getAttribute("defaultValue");
                        if(value.length()!=0){
                            planInformation.put("版本号屏蔽管理"+text+">",value);
                        }
                        if(value2.length()!=0){
                            planInformation.put("版本号屏蔽管理"+text+"<",value2);
                        }
                        if(value.length()==0&&value2.length()==0){
                            planInformation.put("版本号屏蔽管理","");
                        }
                    }
                }
                Utils.waitABit(1000);
                clickWithJS(waitFor(By.xpath("//div[@class='ant-modal-wrap '][not(@style='display: none;')]//*[contains(text(),'确 定')]")));
            }
            if(targetHeaders.contains("用户定向")){
                String targetValue =waitFor(By.xpath("//*[contains(text(),'用户定向')]/../following-sibling::*//span[@style]")).getAttribute("innerText");
                planInformation.put("用户定向",targetValue);
            }
            if(targetHeaders.contains("关键字定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']")));
                if(targetValue.size()!=0){
                    String value1 =waitFor(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//div[@class='ant-select-selection-selected-value']")).getAttribute("innerText");
                    String value2 =waitFor(By.xpath("//*[contains(text(),'关键字定向')]/../following-sibling::*//span[@class='ant-radio ant-radio-checked']/../span[2]")).getAttribute("innerText");
                    if(targetValue.size()==1){
                        planInformation.put("关键字定向",value1+value2+":"+targetValue.get(0));
                    }else {
                        String val = Joiner.on(",").join(targetValue);
                        planInformation.put("关键字定向",value1+value2+":"+val);
                    }
                }else {
                    planInformation.put("关键字定向","无定向");

                }
            }
            if(targetHeaders.contains("科室定向")){
                List<String> targetValue = getListElementsText(findAll(By.xpath("//*[contains(text(),'科室定向')]/../following-sibling::*//li[@class='ant-select-selection__choice']")));
                if(targetValue.size()!=0){
                    if(targetValue.size()==1){
                        planInformation.put("科室定向",targetValue.get(0));
                    }else {
                        String val = Joiner.on(",").join(targetValue);
                        planInformation.put("科室定向",val);
                    }
                }else {
                    planInformation.put("科室定向", "无定向");
                }
            }
        }
/*            List<String> targetHeaders = getListElementsText(findAll(By.xpath("//div[@class='ant-collapse']/div[3]//div/div[@class='ant-row ant-form-item']")));
           for(String value:targetHeaders){
               String[] target = value.split(" ",2);
               if(target.length==2){
                   planInformation.put(target[0].trim(),target[1].trim());
               }
           }
        }
          */
        Iterator it=planInformation.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String value = planInformation.get(key);
            if(value.contains(" ")){
                String val = value.replace(" ","");
                planInformation.put(key,val);
            }
            if(value.contains("，")){
                String val  = value.replace("，",",");
                planInformation.put(key,val);
            }
        }
        return planInformation;
    }

    public void clickUploadButton() throws Exception {
        clickWithActions(upButton);
    }

    public String getReminderSize() {
        String reminder = waitFor(By.xpath("//span[@class='materialUpload']/../div/span")).getAttribute("innerText");
        String size = reminder.substring(11,18).replace("*","x");
        return size;
    }

    public void selectAll() throws Exception{
        WebElement element = waitFor(By.xpath("//div[@class='ant-table-body']//thead/tr/th[@class='ant-table-selection-column']//input"));
        clickWithJS(element);
        Utils.waitABit(1000);
    }

    public void selectTopData() throws Exception{
        Utils.waitABit(2000);
        WebElement element = waitFor(By.xpath("//div[@class='ant-table-body']//tbody/tr[1]/td[@class='ant-table-selection-column']//span[@class='ant-checkbox']"));
        if(!element.isSelected()){
            element.click();
            Utils.waitABit(1000);
        }
    }

    public void checkPlanData(HashMap<String,String>checkData) throws Exception{
        Iterator it=checkData.keySet().iterator();
        while(it.hasNext()){
            String key = it.next().toString();
            String value = checkData.get(key);
            if(value.contains("，")){
                String val  = value.replace("，",",");
                checkData.put(key,val);
            }
        }
        if(checkData.containsKey("帧位")) {
            String frameNo = Utils.formatInteger(Integer.valueOf(checkData.get("帧位")));
            checkData.put("帧位","第"+frameNo+"帧");
        }
        if(checkData.containsKey("频次定向")) {
            String text =checkData.get("频次定向");
            String[]value=text.split(",",2);
            String checkText ="单一用户"+value[0]+"分钟"+value[1]+"次广告";
            checkData.put("频次定向",checkText);
        }
        HashMap<String,String> planData = (HashMap<String,String>)getPlanInformation();
        Iterator iterator=planData.keySet().iterator();
        List list =new ArrayList();
        while(iterator.hasNext()){
            String key = iterator.next().toString();
            String value = planData.get(key);
            if(key.contains("版本号屏蔽管理")){
                String text = key.replace("版本号屏蔽管理","");
                String val  = text.replace("版本号","");
                list.add(val+value);
            }
        }
        String val = Joiner.on(",").join(list);
        planData.put("版本号屏蔽管理",val);

        Assert.assertHashMapAValueContainsHashMapBValue(planData,checkData);
    }

    public void checkPlanListData(String activityName,String type) throws Exception{
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
