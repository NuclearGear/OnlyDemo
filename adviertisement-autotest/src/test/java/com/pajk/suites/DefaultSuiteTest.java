package com.pajk.suites;

import org.springframework.test.context.ContextConfiguration;
import com.pajk.utils.ExtentUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import org.junit.*;
import org.junit.runner.RunWith;
import com.pajk.config.Const;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

/*
    tags = {"@st"},    表示只执行有@st标记的scenario
    tags = {"@st","@dt"},   表示只执行同时含有@st和@dt标记的scenario
    tags = {"@st","~@dt"},  表示执行有@st标记的同时排除标记有@dt标记的scenario
    tags = {"@st,@dt"},   表示执行有@st和@dt标记的scenario
adaisle:
@activity @plan @merchantAccount @merchantCommission
@anchorAccount @anchorCommission @dataReport

adbackend:
@advertiser @activity @plan @template @booth
*/

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/cucumber/cucumber.json", "html:target/cucumber", "pretty"},
        features = "src/test/java/com/pajk/features/",
        glue = Const.GLUE,
        tags = {
//        "@E-demo"
        "@adaisle"
//        "@anchorAccount"
//        ,"@plan"
//        ,"@E-addPlanData"
//        ,"@E-514"
        })

public class DefaultSuiteTest extends BaseSuiteTest {
}

/*
@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"json:target/cucumber/cucumber.json", "html:target/cucumber", "pretty"},
    //features = "src/test/java/com/pajk/features/adaisle",
    //features = "src/test/java/com/pajk/features/adbackend",
    //features = "src/test/java/com/pajk/features/adaisle/01.activity.feature",
    //features = "src/test/java/com/pajk/features/adaisle/02.plan.feature",
    //features = "src/test/java/com/pajk/features/adaisle/03.merchantAccount.feature",
	//features = "src/test/java/com/pajk/features/adaisle/04.merchantCommission.feature",
	//features = "src/test/java/com/pajk/features/adaisle/05.anchorAccount.feature",
	//features = "src/test/java/com/pajk/features/adaisle/07.dataReport.feature",

	//features = "src/test/java/com/pajk/features/adbackend/01.advertiser.feature",
    //features = "src/test/java/com/pajk/features/adbackend/02.activity.feature",
    //features = "src/test/java/com/pajk/features/adbackend/03.plan.feature",
	glue = Const.GLUE)
*/

/*@RunWith(Cucumber.class)
@ContextConfiguration("classpath:cucumber.xml")
@CucumberOptions(
        plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/extent-report/report.html"},
        format = {"json:target/cucumber/cucumber.json","pretty"},
        features = {"src/test/java/com/pajk/features/test"},
        glue = {Const.GLUE},
        tags = {"@E-demo"})*/

