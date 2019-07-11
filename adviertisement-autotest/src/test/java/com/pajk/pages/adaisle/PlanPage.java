package com.pajk.pages.adaisle;

import org.openqa.selenium.support.PageFactory;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;

import static com.pajk.utils.WebDriverUtils.getRowData;
import static org.codehaus.plexus.util.StringUtils.split;

public class PlanPage extends WebDriverSteps{
    private static final Logger logger = LogManager.getLogger(PlanPage.class);
    public PlanPage() { PageFactory.initElements(WebDriverSteps.driver, this); }

    @FindBy(css = "button.ant-btn-primary[type='button']>span")
    public WebElement submit_btn;

    public void clickSubmitButon()throws Exception{
        Utils.waitABit(200);
        clickWithJS(submit_btn);
    }

    public List<List<String>> searchPlan(HashMap searchdata)throws Exception{
        //List<String> searchtypes = this.getListElementsText(findAll(By.cssSelector("form.searchForm>div>div>div>div>label")));
        Iterator iterator = searchdata.keySet().iterator();
        while(iterator.hasNext()){
            String key=iterator.next().toString();
            String value=searchdata.get(key).toString();
            if(key.equals("商品类目")){
                putInValue(waitFor(By.xpath("//div[@id='productCategoryId']//input")),value);
                waitFor(By.xpath("//label[contains(text(),'推广计划')]/../../div[2]//input")).click();
            }
            else if(key.equals("推广状态")){
                clickWithJS(waitFor(By.xpath("//div[@id='promotionStatus']/div/div")));;
                Utils.waitABit(1000);
                clickWithJS(waitFor(By.xpath("//li[contains(text(),'"+value+"')]")));
            }
            else{
                putInValue(waitFor(By.xpath("//label[contains(text(),'"+key+"')]/../../div[2]//input")),value);
            }
        }
        Utils.waitABit(1000);
        WebElement element = waitFor(By.xpath("//button[@type='submit']/span[contains(text(),' 查询 ')]"));
        clickWithJS(element);
        clickWithActions(element);
        Utils.waitABit(2000);
        return getPlanListData();
        }

    public void checksearchresult(HashMap searchdata)throws Exception{
        List<List<String>> planlistdata=getPlanListData();
        for (int i=0;i<planlistdata.size();i++){
            List<String> list= planlistdata.get(i);
            Iterator iterator = searchdata.keySet().iterator();
            while(iterator.hasNext()){
                String key=iterator.next().toString();
                String value=searchdata.get(key).toString();
                if(value.contains("/")){
                    String[] check = value.split("/",2);
                    if(list.contains(check[0])){
                        Assert.assertListContainsValue(list,check[0],"验证数据与预期不符:"+list);
                    }else {
                        Assert.assertListContainsValue(list,check[1],"验证数据与预期不符:"+list);
                    }
                }else {
                    Assert.assertListContainsValue(list,value,"验证数据与预期不符:"+list);
                }
            }
        }
    }

    public void planListClickButton(String plantype,String button)throws Exception{
        try {
            WebElement element = null;
            if (button.equals("编辑") || button.equals("添加")) {
                if (plantype.equals("直播推广")) {
                    element = waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[7][contains(text(),'" + button + "')]"));

                }
                if (plantype.equals("流量推广")) {
                    element = waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[12][contains(text(),'" + button + "')]"));
                }
            } else {
                element = waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'" + button + "')]"));
            }
            clickWithJS(element);
            Utils.waitABit(500);
        }catch (Exception e){
            throw new Exception("点击"+button+"按钮失败");
        }
/*            else if(button.equals("上架")){
                try{
                waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'上架')]")).click();
            }catch (Exception e){
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'下架')]")).click();
                    waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'上架')]")).click();
                }
            }
            else if(button.equals("下架")){
                try {
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'下架')]")).click();
                }catch (Exception e){
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'上架')]")).click();
                    waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'下架')]")).click();
                }
            }
            else if(button.equals("投放中")){
                try{
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'投放中')]")).click();
                }catch (Exception e){
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'暂停')]")).click();
                    Utils.waitABit(3500);
                    clickWithActions(waitFor(By.xpath("//button[@type='submit']")));
                    waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'投放中')]")).click();

                }
            }
            else if(button.equals("暂停")){
                try {
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'暂停')]")).click();
                }catch (Exception e){
                    waitForShort(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'投放中')]")).click();
                    Utils.waitABit(3500);
                    clickWithActions(waitFor(By.xpath("//button[@type='submit']")));
                    waitFor(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[contains(text(),'暂停')]")).click();

                }
            }*/
    }

    public void fillPlanInformation(String planname,String startdate,String enddate)throws Exception{
        if (planname!=null) {
            putInValue(By.xpath("//input[@id='promotionName']"), planname);
        }
/*        String js = "document.querySelector('input.ant-calendar-range-picker-input:nth-child(1)').removeAttribute('readonly')";
        executeJS(js);
        String js2 = "document.querySelector('input.ant-calendar-range-picker-input:nth-child(3)').removeAttribute('readonly')";
        executeJS(js2);*/
        //clickWithActions(waitFor(By.xpath("//i[@class='anticon anticon-calendar ant-calendar-picker-icon']/*[name()='svg']")));
/*        clickWithJS(waitFor(By.xpath("//input[@placeholder='开始日期']")));
        WebElement element =waitFor(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"));
        putInValue(element,startdate);
        clickWithJS(waitFor(By.className("ant-calendar-selected-start-date")));

        Utils.waitABit(500);
        clickWithJS(waitFor(By.xpath("//input[@placeholder='结束日期']")));
        putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='结束日期']"),enddate);
        clickWithJS(waitFor(By.className("ant-calendar-selected-end-date")));
        Utils.waitABit(500);*/
        //setValue(waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='开始日期']")),startdate);
/*        executeJS("",waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='开始日期']")));
        Utils.waitABit(500);
        setValue(waitFor(By.xpath("//input[@class='ant-calendar-range-picker-input'and @placeholder='结束日期']")),enddate);*/
        inputDate("//input[@placeholder='开始日期']",startdate,enddate);

        putInValue(waitFor(By.xpath("//*[contains(text(),'推广预算')]/../..//span[@class='ant-input-wrapper ant-input-group']//input")),"999999");
        //waitFor(By.xpath("//input[@id='promotionName']")).click();
    }

    public void fillPlanInformationBrokerage(String brokerage)throws Exception{
        putInValue(By.id("brokerageRatio"),brokerage);
    }

    public List planPromptText()throws Exception {
        try {
            List<String> PromptTexts = this.getListElementsText(findAll(By.xpath("//div[@class='ant-form-explain']")));
            return PromptTexts;
        }
        catch (Exception e){
            return null;
        }
    }

    public void assertplanPromptText(String prompttext)throws Exception {
        List prompttexts = planPromptText();
        Assert.assertListContainsValue(prompttexts,prompttext,"验证提示与预期不符");
    }

    public List<List<String>> getPlanListData() throws Exception{
        Utils.waitABit(500);
        List<String> goodslist = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[1]/span[2]")));
        List<String> sellerlist = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[2][@rowspan]")));
        //直播数据
        List<String> plancyclelist = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[3][@rowspan]")));
        List<String> status = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[6]")));
        //流量数据
        List<String> plancyclelist2 = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[8]")));
        List<String> adsenselist = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[10]")));
        List<String> status2 = this.getListElementsText(findAll(By.xpath("//tbody[@class='ant-table-tbody']/tr/td[11]")));
        List<List<String>> list = new LinkedList<List<String>>();
        for(int i=0;i<goodslist.size();i++){
            List<String> list1 =new ArrayList<String>();
            list1.add(goodslist.get(i));
            list1.add(sellerlist.get(i));
            list1.add(plancyclelist.get(i));
            list1.add(status.get(i));
            list1.add(plancyclelist2.get(i));
            list1.add(adsenselist.get(i));
            list1.add(status2.get(i));
            list.add(list1);
        }
        return list;
    }

    public LinkedHashMap<String, String> getPlanData() throws Exception{
        LinkedHashMap<String, String> plandata= getRowData("//span[contains(text(),'spuId')]/../../ancestor::th/..//th","//span[contains(text(),'不允许更改商品')]/../../td",4,driver);
        String startdate =waitFor(By.xpath("//span[@class='ant-calendar-picker']/span/input[1]")).getAttribute("value");
        String enddate =waitFor(By.xpath("//span[@class='ant-calendar-picker']/span/input[2]")).getAttribute("value");
        if (!startdate.isEmpty()&& !enddate.isEmpty()){
            plandata.put("开始日期",startdate);
            plandata.put("结束日期",enddate);
        }
        try{
            WebElement adsense=waitForVisibleShort(By.cssSelector("div.ant-select-selection__rendered>div.ant-select-selection-selected-value:nth-child(1)"));
            if (adsense!=null) {
                plandata.put("推广展位", adsense.getText());
                if (plandata.get("推广展位").equals("主搜商品广告位")) { }
             }
        }catch (Exception e){}
        return plandata;
    }

    public void clickExportButton() throws Exception {
        clickWithActions(waitFor(By.xpath("//button[@type='button']")));
    }

}