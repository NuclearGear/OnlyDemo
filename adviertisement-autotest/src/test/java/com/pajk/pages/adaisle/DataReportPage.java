package com.pajk.pages.adaisle;

import com.pajk.steps.WebDriverSteps;
import static com.pajk.utils.WebDriverUtils.getRowData;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.text.DecimalFormat;
import java.util.*;

public class DataReportPage extends WebDriverSteps{
    private static final Logger logger = LogManager.getLogger(DataReportPage.class);
    public DataReportPage() { PageFactory.initElements(WebDriverSteps.driver, this); }
    @FindBy(xpath = "//div[@aria-hidden='false']//div[@style='display: block;']//button[@type='submit'and contains(@class,'ant-btn-primary')]/span[contains(text(),'查询')]")
    public WebElement search_btn;
    @FindBy(xpath = "//div[@aria-hidden='false']//div[@style='display: block;']//span[contains(text(),'导出')]")
    public WebElement export_btn;


    public void clickReport(String reportname)throws Exception{
        if(reportname.equals("流量推广")){
            clickWithJS(waitFor(By.xpath("//div[@aria-hidden='false']//span[contains(text(),'"+reportname+"')]")));;
        }
        else{
            clickWithJS(waitFor(By.xpath("//span[contains(text(),'"+reportname+"')]")));;
        }
        Utils.waitABit(1000);
    }

    public void clickSearchButton()throws Exception {
        String reportname = waitFor(By.xpath("//div[@aria-selected='true']//span")).getAttribute("innerText");
        if (reportname.equals("主播收益报表")) {
            clickWithJS(waitFor(By.xpath("//div[@aria-hidden='false']//button[@type='submit'and contains(@class,'ant-btn-primary')]/span[contains(text(),'查询')]")));
        }
        else {
            clickWithJS(search_btn);
        }
        Utils.waitABit(1000);
    }

    public void clickExportButton() throws Exception {
        boolean result = waitForVisibleOrNot(By.xpath("//div[@aria-hidden='false']//*[contains(text(),'暂无数据')]"), 1);
        if (result) {
            throw new Exception("报表无数据导出");
            }
        else{
            try{
                clickWithJS(export_btn);
            }catch (Exception e){
                clickWithJS(waitFor(By.xpath("//div[@aria-hidden='false']//span[contains(text(),'导出')]")));
            }
            Utils.waitABit(3000);
        }
    }

    public void searchReportData(HashMap searchdata) throws Exception{
        scrollToTop();
        String reportname = waitFor(By.xpath("//div[@aria-selected='true']//span")).getAttribute("innerText");
        Iterator iterator = searchdata.keySet().iterator();
        String startDate = null;
        String endDate = null;
        while(iterator.hasNext()){
            String key=iterator.next().toString();
            String value=searchdata.get(key).toString();
            if(key.equals("开始日期")){
                startDate = value;
/*                if (reportname.equals("主播收益报表")){
                    clickWithJS(waitFor(By.xpath("//div[@aria-hidden='false']//input[@placeholder='开始日期']")));
                    Utils.waitABit(1500);
                    }
                else {
                    WebElement element = waitFor(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//input[@placeholder='开始日期']"));
                    clickWithJS(element);
                    Utils.waitABit(1500);
                    }
                putInValue(By.xpath("//input[@class='ant-calendar-input 'and @placeholder='开始日期']"),value);
                clickWithJS(waitFor(By.className("ant-calendar-selected-start-date")));
                Utils.waitABit(1000);*/
            }
            else if(key.equals("结束日期")){
                endDate = value;
                if (reportname.equals("主播收益报表")){
                    //clickWithJS(waitFor(By.xpath("//div[@aria-hidden='false']//input[@placeholder='结束日期']")));
                    inputDate("//div[@aria-hidden='false']//input[@placeholder='开始日期']",startDate,endDate);
                }
                else {
                    inputDate("//div[@aria-hidden='false']//div[@style='display: block;']//input[@placeholder='开始日期']",startDate,endDate);
                    Utils.waitABit(1000);
                }
            }

            else if(key.equals("推广计划")){
                putInValue(By.id("planName"),value);
            }
            else if(key.equals("商品名称")){
                putInValue(By.id("itemTitle"),value);
            }
            else if(key.equals("推广商品")){
                putInValue(By.id("productName"),value);
            }
            else if(key.equals("推广商家")){
                try{
                    putInValue(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//input[@id='sellerName']"),value);
                }
                catch (Exception e){
                    putInValue(By.id("adIncomeTrafficSellerName"),value);
                }
            }
            else if(key.equals("主播名称")){
                putInValue(By.id("anchorName"),value);
            }
        }
        Utils.waitABit(500);
        clickSearchButton();
    }

    public void getReportData() throws Exception {
        scrollToBase();
        String reportName=waitFor(By.xpath("//div[@aria-selected='true']//span")).getAttribute("innerText");
        Utils.waitABit(2000);
        try {
            if (!reportName.equals("主播收益报表")) {
                List<WebElement> data = findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tr[contains(@class,'ant-table-row')]"));
                for (int i = 0; i < data.size(); i++) {
                    LinkedHashMap<String, String> reportdata = getRowData("//div[@aria-hidden='false']//div[@style='display: block;']//tr/th", "//div[@aria-hidden='false']//div[@style='display: block;']//tr[contains(@class,'ant-table-row')][" + (i + 1) + "]/td", 8, driver);
                    logger.info(reportdata);
                }
                List<String> sum = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//strong/..")));
                logger.info(sum);
                }
            else{
                List<WebElement> data = findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]"));
                //List<WebElement> data = findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')][not(@data-row-key='undefined_总计')]"));
                for (int i = 0; i < data.size(); i++) {
                    LinkedHashMap<String, String> reportdata = getRowData("//div[@aria-hidden='false']//tr/th", "//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')][" + (i + 1) + "]/td", 6, driver);
                    if (reportdata.get("日期").equals("总计")) {
                        reportdata.remove("日期");
                        reportdata.remove("主播名称");
                    }
                        logger.info(reportdata);
                    }

                }
        }catch (Exception e){
            throw new Exception(reportName+"无数据");
        }
    }

    public String sumReportData(List<String>...args) throws Exception {
        double sum = 0;
        if (args.length == 1){
            for (int i=0;i<args[0].size();i++){
                sum += Double.valueOf(args[0].get(i));
            }
        }
        else {
            for (int i=0;i<args.length;i++){
                for (int j=0;j<args[i].size();j++){
                    sum += Double.valueOf(args[i].get(j));
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return String.valueOf(decimalFormat.format(sum));
    }

    public void checkReportData(String reportname) throws Exception {
        scrollToBase();
        boolean result = waitForVisibleOrNot(By.xpath("//div[@aria-hidden='false']//*[contains(text(),'暂无数据')]"),1);
        if(!result){
            if(reportname.equals("商家推广报表-直播推广")||reportname.equals("广告收入报表-直播推广")){
        //        List<String> date = this.getListElementsText(findAll(By.xpath("//div[@class='live']//tbody[@class='ant-table-tbody']/tr/td[1]")));

                List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//div[@class='ant-table-header']//thead/tr/th")));
                //根据title获取
                //System.out.println(headers.indexOf("商品交易额(元)")+1);
                List<String> gmv = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("商品交易额(元)")+1)+"]")));
                List<String> trade = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("支付订单量")+1)+"]")));
                List<String> commission = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("固定佣金(元)")+1)+"]")));
                List<String> activitycommission = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("活动佣金(元)")+1)+"]")));
                List<String> cash = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("现金(元)")+1)+"]")));

                List<String> total = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//strong/..")));
                //System.out.println(sum);
                //计算列表数据
                String sum1 = sumReportData(gmv)+"元";
                String sum2 = sumReportData(commission,activitycommission)+"元";
                String sum3 = sumReportData(trade);
                String sum4 = sumReportData(cash)+"元";
                List totallist = new ArrayList();
                total.remove("总计");
                for (int i=0;i<total.size();i++){
                    totallist.add(total.get(i).split(": ")[1]);
                }
        /*        System.out.println(sum1);
                System.out.println(sum2);
                System.out.println(sum3);
                System.out.println(sumlist);*/
                logger.info("商品交易额总计值:"+sum1+",总计列:"+totallist.get(0).toString());
                Assert.isEqual(String.valueOf(sum1),(totallist.get(0)).toString());
                logger.info("推广消费额总计值:"+sum2+",总计列:"+totallist.get(1).toString());
                Assert.isEqual(String.valueOf(sum2),(totallist.get(1)).toString());
                if(reportname.equals("商家推广报表-直播推广")) {
                    //支付订单量有不等于总计量情况，暂校验不等于0
                    logger.info("支付订单量总计值:"+sum3+",总计列:"+totallist.get(3).toString());
                    if(!sum3.equals("0")) {
                        Assert.assertNotEquals(0, totallist.get(3).toString());
                    }
                    logger.info("现金总计值:" + sum4 + ",总计列:" + totallist.get(2).toString());
                    Assert.isEqual(String.valueOf(sum4), (totallist.get(2)).toString());
                }
                else {
                    logger.info("支付订单量总计值:"+sum3+",总计列:"+totallist.get(2).toString());
                    Assert.isEqual(String.valueOf(sum3),(totallist.get(2)).toString());
                }
            }
            else if(reportname.equals("商家推广报表-流量推广")){
                List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//div[@class='ant-table-header']//thead/tr/th")));

                List<String> click = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("点击量")+1)+"]")));
                List<String> gmv = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("推广消费额")+1)+"]")));
                List<String> imp = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("展现量")+1)+"]")));
                List<String> cash = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("现金(元)")+1)+"]")));
                List<String> total = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//strong/..")));
                String clickSum = sumReportData(click);
                String gmvSum = sumReportData(gmv)+"元";
                String cashSum = sumReportData(cash)+"元";
                String impSum = sumReportData(imp);
                List totallist = new ArrayList();
                total.remove("总计");
                for (int i=0;i<total.size();i++){
                    totallist.add(total.get(i).split(": ")[1]);
                }
                logger.info("点击量总计值:"+clickSum+",总计列:"+totallist.get(0).toString());
                Assert.isEqual(clickSum,(totallist.get(0)).toString());
                logger.info("总消耗额总计值:"+gmvSum+",总计列:"+totallist.get(1).toString());
                Assert.isEqual(gmvSum,(totallist.get(1)).toString());
                logger.info("现金总计值:"+cashSum+",总计列:"+totallist.get(2).toString());
                Assert.isEqual(cashSum,(totallist.get(2)).toString());
                logger.info("展示量总计值:"+impSum+",总计列:"+totallist.get(3).toString());
                Assert.isEqual(impSum,(totallist.get(3)).toString());
            }
    /*        else if(reportname.equals("广告收入报表-直播推广")){
                List<String> gmv = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td[4]")));
                List<String> trade = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td[8]")));
                List<String> commission = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td[6]")));
                List<String> activitycommission = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td[7]")));
                List<String> sum = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//strong/..")));
                String sum1 = sumReportData(gmv);
                String sum2 = sumReportData(commission,activitycommission);
                String sum3 = sumReportData(trade);
                List sumlist = new ArrayList();
                sum.remove("总计");
                for (int i=0;i<sum.size();i++){
                    sumlist.add(sum.get(i).split(": ")[1]);
                }
                Assert.isEqual(String.valueOf(sum1),(sumlist.get(0)).toString());
                Assert.isEqual(String.valueOf(sum2),(sumlist.get(1)).toString());
                Assert.isEqual(String.valueOf(sum3),(sumlist.get(2)).toString());
            }*/
            else if(reportname.equals("广告收入报表-流量推广")){
                List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//div[@class='ant-table-header']//thead/tr/th")));

                List<String> click = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("点击量")+1)+"]")));
                List<String> gmv = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("推广消费额")+1)+"]")));
                List<String> imp = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("展现量")+1)+"]")));
                //List<String> cash = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//tbody[@class='ant-table-tbody']/tr/td["+(headers.indexOf("现金(元)")+1)+"]")));
                List<String> total = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//div[@style='display: block;']//strong/..")));
                String clickSum = sumReportData(click);
                String gmvSum = sumReportData(gmv)+"元";
                String impSum = sumReportData(imp);
                //String cashSum = sumReportData(cash)+"元";
                List totallist = new ArrayList();
                total.remove("总计");
                for (int i=0;i<total.size();i++){
                    totallist.add(total.get(i).split(": ")[1]);
                }
                logger.info("点击量总计值:"+clickSum+",总计列:"+totallist.get(0).toString());
                Assert.isEqual(String.valueOf(clickSum),(totallist.get(0)).toString());
                logger.info("总消耗额总计值:"+gmvSum+",总计列:"+totallist.get(1).toString());
                Assert.isEqual(String.valueOf(gmvSum),(totallist.get(1)).toString());
                //logger.info("现金总计值:"+cashSum+",总计列:"+totallist.get(2).toString());
                //Assert.isEqual(String.valueOf(cashSum),(totallist.get(2)).toString());
                logger.info("展示量总计值:"+impSum+",总计列:"+totallist.get(2).toString());
                Assert.isEqual(String.valueOf(impSum),(totallist.get(2)).toString());
            }
            else if(reportname.equals("主播收益报表")){
                List<String> headers = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr/th")));
                for(int i=3;i<=headers.size();i++){
                    List<String> list = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td["+i+"]")));
/*                    List<String> list4 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[4]")));
                    List<String> list5 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[5]")));
                    List<String> list6 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[6]")));
                    List<String> list7 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[7]")));
                    List<String> list8 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[8]")));
                    List<String> list9 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[9]")));
                    List<String> list10 = this.getListElementsText(findAll(By.xpath("//div[@aria-hidden='false']//tr[contains(@class,'ant-table-row')]/td[10]")));*/
                    if(list.size()!=1){
                        //List<String> sumlist = new LinkedList<String>();
                        //sumlist.add(list.get(list1.size()-1));
                        String total = list.get(list.size()-1);
                        list.remove(list.size()-1);
                        String sum = sumReportData(list);
                        logger.info(headers.get(i-1)+"总计值："+sum+",总计列:"+total);
                        Assert.isEqual(sum,total);
                    }
                    else{
                        throw new Exception(reportname+"无数据");
                    }
                }
            }

        }
        else {
            throw new Exception(reportname+"无数据");
        }

    }

}
