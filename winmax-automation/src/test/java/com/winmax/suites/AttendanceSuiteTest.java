package com.winmax.suites;

import org.junit.Test;

import com.winmax.config.Const;


public class AttendanceSuiteTest extends BaseSuiteTest {

    private String outputPath = Const.REPORT_FOLDER;
    String[] tags = {"@E-17"};
    //private String featureSource = "classpath:features/feature1.feature";
    //if the value is null, then default run all feature files
    private String featureSource = null;
    
    @Test
    public void executeSuiteTest() {
        defaultRun(outputPath, tags, featureSource);
        reRunTest(outputPath, Const.RERUN_TIMES);
    }
}
