package com.pajk.utils;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class ExtentUtils extends TestWatcher {

    private ExtentReports extent;

    public ExtentUtils(ExtentReports extent) {
        this.extent = extent;
    }

    @Override
    protected void succeeded(Description description) {
        ExtentTest test = extent.startTest(description.getDisplayName(), "-");

        // step log
        test.log(LogStatus.PASS, "-");
        flushReports(extent, test);
    }

    @Override
    protected void failed(Throwable e, Description description) {
        ExtentTest test = extent.startTest(description.getDisplayName(), "Test failed");

        // step log
        test.log(LogStatus.FAIL, e);
        flushReports(extent, test);
    }

    private void flushReports(ExtentReports extent, ExtentTest test){
        // ending test
        extent.endTest(test);
        // writing everything to document
        extent.flush();
    }
}
