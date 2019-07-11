package com.pajk.steps.test;

import com.pajk.utils.DataTableUtils;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pajk.dso.ImportFile;
import com.pajk.pages.test.TestPage;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.HashMap;
import java.util.Iterator;

public class testStep {
    String param;
	private static final Logger logger = LogManager.getLogger(testStep.class);
	private TestPage vp = new TestPage();
	private ImportFile file = null;

    @When("^获得列表数据$")
    public void gettable(DataTable data) throws Throwable {
        HashMap<String,String> valuehash= DataTableUtils.toHashMap(data);
        //Iterator iterator = valuehash.entrySet().iterator();
        Iterator iterator = valuehash.keySet().iterator();
        while(iterator.hasNext()){
            Object key=iterator.next();
            System.out.println(key+valuehash.get(key));
        }
    }

    @Given("^visit baidu in a browser$")
    public void visit_baidu_in_a_browser() throws Exception {
        vp.visitBaiduInBrowser();
    }

    @When("^I search (.+)$")
    public void I_search (String test) throws Throwable {
        param = test;
        vp.findInfomation(test);
    }
    @Then("^I check title$")
    public void I_check_title() throws Throwable {
    	vp.checktitle(param+"1");
        //vp.checktitle(param);
    }

}
