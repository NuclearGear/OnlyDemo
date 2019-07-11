package com.pajk.suites;

import com.pajk.db.DB_yacht;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import com.pajk.utils.ExtentUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import org.junit.*;
import com.pajk.config.Const;

import java.util.List;

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

public class AttendanceSuiteTest extends BaseSuiteTest {

    private static ExtentReports extent;
    private String outputPath = Const.REPORT_FOLDER;
//    String[] tags = {"@advertisement"};
    String[] tags = {"@adaisle"};
//    String[] tags = {"@adbackend"};
//    String[] tags = {"@plan"};
    //private String featureSource = "src/test/java/com/pajk/features";
    //private String featureSource = "src/test/java/com/pajk/features/adaisle";
    //private String featureSource = "src/test/java/com/pajk/features/adbackend";
    //private String featureSource = "src/test/java/com/pajk/features/test/test.feature";

    //if the value is null, then default run all feature files
    private String featureSource = null;
    
    @Test
    public void executeSuiteTest()throws Exception {
        defaultRun(outputPath, tags, featureSource);
        reRunTest(outputPath, Const.RERUN_TIMES);

        //清理自动化数据
        DB_yacht yacht = new DB_yacht();
        //清理推广活动
        yacht.deleteActivityByName("UI-自动化");
        //清理直播推广计划
        yacht.deleteLivePlanByName("UI-自动化");
        //清理广告后台数据
        yacht.deleteOwnerLikeName("UI-自动化");
        yacht.deleteAdByName("UI-自动化-运营活动");
        yacht.deleteAdByName("UI-自动化-商业活动");
        yacht.deletePlanByName("UI-自动化商业投放");
        yacht.deletePlanByName("UI-自动化运营投放");
        yacht.deleteTemplateByName("UI-自动化广告系统");
        yacht.deleteTemplateByName("UI-自动化业务系统");
        yacht.deleteBoothByCode("UI001");
    }

/*    @BeforeClass
    public static void beforeClass() {
        String reportPath = "target/extent/extent-report.html";
        extent = new ExtentReports(reportPath, true, NetworkMode.OFFLINE);
        extent.config().reportName("OnlyTest");
        extent.config().reportHeadline("Overview of testresults");
    }

    @AfterClass
    public static void afterClass() {
        extent.close();
    }

	@Rule
	public ExtentUtils eu = new ExtentUtils(extent);*/
}
