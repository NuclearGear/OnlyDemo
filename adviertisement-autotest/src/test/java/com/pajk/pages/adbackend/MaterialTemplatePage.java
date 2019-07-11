package com.pajk.pages.adbackend;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MaterialTemplatePage extends WebDriverSteps  {
    private static final Logger logger = LogManager.getLogger(MaterialTemplatePage.class);
    public MaterialTemplatePage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement submit_btn;

    public void materialTemplatePageClickButton(String button) throws Exception{
        WebElement element = waitFor(By.xpath("//*[contains(text(),'"+button+"')]"));
        clickWithActions(element);
    }

    public void searchTemplate(String templateName) throws Exception{
        putInValue(By.id("listname"),templateName);
        materialTemplatePageClickButton("查 询");
        putInValue(By.id("listname"),templateName);
        materialTemplatePageClickButton("查 询");
        Utils.waitABit(2000);
    }

    public void submit() throws Exception{
        clickWithJS(submit_btn);
    }

    public void fillTemplateInformation(HashMap data) throws Exception{
        if(data.containsKey("素材模版名称")){
            String value = data.get("素材模版名称").toString();
            WebElement element =waitFor(By.id("materialName"));
            putInValue(element,value);
        }
        if(data.containsKey("素材获取方式")){
            String value = data.get("素材获取方式").toString();
            WebElement element =waitFor(By.xpath("//*[contains(text(),'获取方式')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
            clickWithJS(element);
        }
        String type =waitFor(By.xpath("//*[contains(text(),'获取方式')]/../following-sibling::*//span[@class='ant-radio ant-radio-checked']/../span[2]")).getAttribute("innerText").trim();
        if(type.equals("广告系统")){
            if(data.containsKey("素材模版类型")){
                String value = data.get("素材模版类型").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'模版类型')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("素材内容格式")){
                String value = data.get("素材内容格式").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'内容格式')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("素材模版样式")){
                String value = data.get("素材模版样式").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'模版样式')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("是否可点击")){
                String value = data.get("是否可点击").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'是否可点击')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("素材模版元素")){
                if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"),1)){
                    List<WebElement> elements = findAll(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
                    for(WebElement element:elements){
                        clickWithJS(element);
                    }
                }
                String value = data.get("素材模版元素").toString();
                if(value.contains(",")){
                    String[] val=value.split(",");
                    for(int i =0;i<val.length;i++){
                        WebElement element =waitFor(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/../span[1]"));
                        clickWithJS(element);
                    }
                }else {
                    WebElement element =waitFor(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                    clickWithJS(element);
                }
            }
        }else if(type.equals("业务系统")){
            if(data.containsKey("获取内容")){
                String value = data.get("获取内容").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'获取内容')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("是否传递渠道参数")){
                String value = data.get("是否传递渠道参数").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'传递渠道参数')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            if(data.containsKey("是否允许替换素材属性")){
                String value = data.get("是否允许替换素材属性").toString();
                WebElement element =waitFor(By.xpath("//*[contains(text(),'是否允许替换素材属性')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                clickWithJS(element);
            }
            Utils.waitABit(1000);
            if(data.containsKey("素材模版元素")){
                if(waitForVisibleOrNot(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"),1)) {
                    List<WebElement> elements = findAll(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//span[@class='ant-checkbox ant-checkbox-checked']"));
                    for (WebElement element : elements) {
                        clickWithJS(element);
                    }
                }
                String value = data.get("素材模版元素").toString();
                if(value.contains(",")){
                    String[] val=value.split(",");
                    for(int i =0;i<val.length;i++){
                        WebElement element =waitFor(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//*[contains(text(),'"+val[i]+"')]/../span[1]"));
                        clickWithJS(element);
                    }
                }else {
                    WebElement element =waitFor(By.xpath("//*[contains(text(),'素材模版元素')]/../following-sibling::*//*[contains(text(),'"+value+"')]/../span[1]"));
                    clickWithJS(element);
                }
            }
        }

    }

    public void fillTemplateElementInformation(HashMap data) throws Exception{
        Iterator it = data.keySet().iterator();
        while (it.hasNext()){
            String key=it.next().toString();
            String value=data.get(key).toString();
            WebElement element = waitFor(By.xpath("//*[contains(text(),'"+key+"')]/../following-sibling::*//input"));
            if(key.equals("是否支持健康金")&&value.equals("是")){
                clickWithJS(element);
            }else {
                putInValue(element,value);
            }
        }
    }

}
