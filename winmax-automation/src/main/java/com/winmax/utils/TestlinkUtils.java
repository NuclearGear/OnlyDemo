package com.winmax.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.eti.kinoshita.testlinkjavaapi.TestLinkAPI;
import br.eti.kinoshita.testlinkjavaapi.constants.ExecutionStatus;
import br.eti.kinoshita.testlinkjavaapi.model.ReportTCResultResponse;
import br.eti.kinoshita.testlinkjavaapi.model.TestPlan;
import br.eti.kinoshita.testlinkjavaapi.util.TestLinkAPIException;

public class TestlinkUtils {
	private static final Logger logger = LogManager.getLogger(TestlinkUtils.class);
	
	private static String url = "http://192.168.1.113:8080/testlink/lib/api/xmlrpc/v1/xmlrpc.php";
    private static String devKey = "20f278f15ec6e0206f2a22dfd10956a0";
    private static String projectName = "EHR10";
    
    private static TestLinkAPI getCon() {
        TestLinkAPI api = null;
    	URL testlinkURL = null;
        try {
        	testlinkURL = new URL(url);
        } catch (MalformedURLException mue) {
            Assert.fail(mue.getMessage());
        }  
        try {
    	    api = new TestLinkAPI(testlinkURL, devKey);
    	} catch (TestLinkAPIException te) { 
    	    Assert.fail(te.getMessage());
    	}
        return api;
    }
 
    private static Integer getCurrentActivePlanId(TestLinkAPI api) {
    	Integer projectId = api.getTestProjectByName(projectName).getId();
    	TestPlan[] plans = api.getProjectTestPlans(projectId);
    	Integer id = null;
    	for (int i=0;i<plans.length;i++) {
    		if(plans[i].isActive()) {
    			id = plans[i].getId();
    		}
    	}
		return id;
    }
    
    public static void addTCResult(ArrayList<String> fullTestCaseExternalIdList, boolean result) {
    	ExecutionStatus status = ExecutionStatus.PASSED;
    	if (!result) {
    		status = ExecutionStatus.FAILED;
    	}
    	TestLinkAPI api = getCon();
		Integer testPlanId = getCurrentActivePlanId(api); 	
        String buildName = api.getLatestBuildForTestPlan(testPlanId).getName();         
		for (int i=0;i<fullTestCaseExternalIdList.size();i++) {
	        Integer externalID = api.getTestCaseByExternalId(fullTestCaseExternalIdList.get(i), null).getId();
	        ReportTCResultResponse response = api.reportTCResult(externalID, null, testPlanId, status, null, buildName, "自动化更新结果", null, null, null, null, null, null);
	        if (!response.getMessage().contains("Success")) {
	        	logger.error("更新TestLink测试用例： " + fullTestCaseExternalIdList.get(i) + "执行结果失败！！！");
	        }
		}
    }
}
