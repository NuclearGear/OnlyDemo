package com.pajk.pages.adaisle;

import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static com.pajk.utils.WebDriverUtils.getRowData;

public class MerchantAccountPage extends WebDriverSteps {
    private static final Logger logger = LogManager.getLogger(MerchantAccountPage.class);
    public MerchantAccountPage() {
        PageFactory.initElements(WebDriverSteps.driver, this);
    }

    public void merchantListClickButton(String button){
        Utils.waitABit(1500);
        if (button.equals("充值")||button.equals("扣费")||button.equals("账户明细")||button.equals("编辑")){
            WebElement element = waitForShort(By.xpath("//a[contains(text(),'" + button + "')]"));
            clickWithJS(element);
        }
        else{
            WebElement element = waitForShort(By.xpath("//span[contains(text(),'" + button + "')]"));
            clickWithJS(element);
        }
        Utils.waitABit(1000);
    }

    public void addMerchant(String merchantid,String income) throws Exception {
        putInValue(By.id("MerchantId"),merchantid);
        putInValue(By.id("income"),income);
        merchantListClickButton("保 存");
    }

    public void searchMerchant(String merchantid,String merchantname) throws Exception {
        putInValue(By.id("merchantId"),merchantid);
        putInValue(By.id("merchantName"),merchantname);
        merchantListClickButton("查 询");
    }

    public void accountRecharge(String amount) throws Exception {
        putInValue(By.id("amount"),amount);
        Utils.waitABit(1000);
        merchantListClickButton("确 定");
    }

    public void accountDeductionAmount(String deductionAmount,String deduction) throws Exception {
        putInValue(By.id("deductionAmount"),deductionAmount);
        putInValue(By.id("deduction"),deduction);
        merchantListClickButton("确 定");
    }

    public List<String> getMerchantListData() throws Exception {
        Utils.waitABit(1000);
        List<String> merchantListData = this.getListElementsText(findAll(By.xpath("//div[@class='ant-table-content']//tr[@class='ant-table-row ant-table-row-level-0']//td")));
        return merchantListData;
    }

    public void checkMerchantList(String...args) throws Exception {
        Utils.waitABit(2000);
        List<String> merchantListData = getMerchantListData();
        for(String value:args){
            Assert.assertListContainsValue(merchantListData, value, "商户列表值与预期值不符");
        }
    }

    public void checkMerchantDetail(String...args) throws Exception {
        Utils.waitABit(2000);
        List<String> merchantDetailData = getListElementsText(findAll(By.xpath("//div[@class='ant-modal-content']//div[@class='ant-table-content']//tr[@class='ant-table-row ant-table-row-level-0'][last()]/td")));
        for(int i=0;i<args.length;i++){
            Assert.assertListContainsValue(merchantDetailData, args[i], "商户账户明细与预期值不符");
            if (args[i].equals("充值")){
                String balance = waitFor(By.xpath("//div[@class='ant-modal-content']//div[@class='ant-table-content']//tr[@class='ant-table-row ant-table-row-level-0'][last()-1]/td[5]")).getText().replace("¥ ","");
                String changeBalance = args[1].replace("+","");
                BigDecimal a = new BigDecimal(balance);
                BigDecimal b = new BigDecimal(changeBalance);
                BigDecimal atferChangeBalance = a.add(b);
                Assert.assertListContainsValue(merchantDetailData, "¥ "+atferChangeBalance.toString(), "商户账户明细充值金额不正确");
            }
            else if(args[i].equals("扣费")){
                String balance = waitFor(By.xpath("//div[@class='ant-modal-content']//div[@class='ant-table-content']//tr[@class='ant-table-row ant-table-row-level-0'][last()-1]/td[5]")).getText().replace("¥ ","");;
                String changeBalance = args[1].replace("-","");
                BigDecimal a = new BigDecimal(balance);
                BigDecimal b = new BigDecimal(changeBalance);
                BigDecimal atferChangeBalance = a.subtract(b);
                Assert.assertListContainsValue(merchantDetailData, "¥ "+atferChangeBalance.toString(), "商户账户明细扣费金额不正确");
            }
        }
    }


}
