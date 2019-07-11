package com.pajk.pages.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.pajk.config.Const;
import com.pajk.steps.WebDriverSteps;
import com.pajk.utils.Assert;
import com.pajk.utils.Excel;
import com.pajk.utils.FileUtil;
import com.pajk.utils.Utils;
import com.pajk.utils.WebDriverUtils;

public class TestPage extends WebDriverSteps {

   public void visitBaiduInBrowser(){
	   driver.get("https://www.baidu.com/");
   }

   public void findInfomation(String test){
        putInValue(By.id("kw"),test);
        waitFor(By.xpath("//input[@id='su']")).click();
   }
   
   public void checktitle(String test) throws InterruptedException{
	   Thread.sleep(2000);
	   Assert.isEqual(test+"_百度搜索",driver.getTitle() );
   }

}
