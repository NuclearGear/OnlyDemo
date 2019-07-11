package com.pajk.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import static com.pajk.utils.FileUtil.getLatestDownloadFile;

public class Assert extends org.junit.Assert {

	public static void isTrue(boolean t) {
		try {
			assertTrue(t);
		} catch (AssertionError e) {
			Assert.fail("Values is incorrect" + "\n" + "Actual value is " + t + "\n" + "Expected value is " + "true");
		}		
	}
	
	public static void isTrue(boolean t, String msg) {
		try {
			assertTrue(t);
		} catch (AssertionError e) {
			Assert.fail(msg + "\n" +  " actual value is " + t + "\n" + "Expected value is " + "true");
		}		
	}
	
	public static void isEqual(String expect, String actual) {
		if (expect.equals(actual)) {
			assertTrue(true);
		} else {
			Assert.fail("Values is incorrect" + "\n" + "Actual value is " + actual + "\n" + "Expected value is " + expect);
		}		
	}
	
	public static void isEqual(int expect, int actual) {
		if (expect==actual) {
			assertTrue(true);
		} else {
			Assert.fail("Values is incorrect" + "\n" + "Actual value is " + actual + "\n" + "Expected value is " + expect);
		}
	}
	public static void isEqual(String expect, String actual, String msg) {
		if (expect.equals(actual)) {
			assertTrue(true);
		} else {
			Assert.fail(msg + "\n" + "Actual value is " + actual + "\n" + "Expected value is " + expect);
		}		
	}
	
	public static void isEqual(int expect, int actual, String msg) {
		if (expect==actual) {
			assertTrue(true);
		} else {
			Assert.fail(msg + "\n" + "Actual value is " + actual + "\n" + "Expected value is " + expect);
		}		
	}
	
	public static void isContains(String actual, String containedStr, String msg) {
		if (actual.contains(containedStr)) {
			assertTrue(true);
		} else {
			Assert.fail(msg + "\n" + "Actual value is " + actual + " does not contains " + containedStr);
		}		
	}

	public static void fileNameContains(String containedStr, String msg) throws Exception{
        String downloadFile = getLatestDownloadFile();
        if (downloadFile.contains(containedStr)){
			assertTrue(true);
		} else {
			Assert.fail(msg + "\n" + "Actual value is " + downloadFile + " does not contains " + containedStr);
		}
	}
	
	public static void present(By by, String failMsg, WebDriver driver) {
		try {
			WebDriverUtils.waitForShort(by, driver);
		} catch (Exception e) {			 
			Assert.fail(failMsg);
		}		
	}

	public static void notPresent(By by, String failMsg, WebDriver driver) {
		try {
			WebDriverUtils.waitForShort(by, driver);
			Assert.fail(failMsg);
		} catch (Exception e) {			 
			//pass
		}		
	}

	public static void visible(By by, String failMsg, WebDriver driver) {
		try {
			boolean flag = WebDriverUtils.waitFor(by, driver,5).isDisplayed();
			Assert.isTrue(flag);
		} catch (Exception e) {
			Assert.fail(failMsg);
		}
	}

	public static void visibleShort(By by, String failMsg, WebDriver driver) {
		try {
			boolean flag = WebDriverUtils.waitForShort(by, driver).isDisplayed();
			Assert.isTrue(flag);
		} catch (Exception e) {
			Assert.fail(failMsg);
		}
	}

	public static void notVisible(By by, String failMsg, WebDriver driver) {
		try {
			boolean flag = WebDriverUtils.waitFor(by, driver).isDisplayed();
			Assert.assertEquals(false, flag);
		} catch (Exception e) {

		}
	}

	public static void notVisibleShort(By by, String failMsg, WebDriver driver) {
		try {
			boolean flag = WebDriverUtils.waitForShort(by, driver).isDisplayed();
			Assert.assertEquals(false, flag);
		} catch (Exception e) {			 
	
		}		
	}

	public static void assertListContainsValue(List<String> l, String val, String failMsg) {
		if (!l.contains(val)) {
			Assert.fail(failMsg);
		}				
	}
	
	public static void assertListNotContainsValue(List<String> l, String val, String failMsg) {
		if (l.contains(val)) {
			Assert.fail(failMsg);
		}				
	}
	
	public static void assertHashMapAContainsHashMapB(HashMap<String, String> ha, HashMap<String, String> hb) {
		 Set <String> bKey = hb.keySet();	
		 for (String k : bKey) {
			 Assert.isEqual(hb.get(k), ha.get(k), k);
		 }
	}

	public static void assertHashMapAValueContainsHashMapBValue(HashMap<String, String> ha, HashMap<String, String> hb) {
		Set <String> bKey = hb.keySet();
		for (String k : bKey) {
			Assert.isContains(ha.get(k),hb.get(k), k);
		}
	}

	//验证 一个元素的attribute - value的值
	public static void assertElementAttributeValue(By by, String val, String failMsg, WebDriver driver) {
		String actual = WebDriverUtils.waitForShort(by, driver).getAttribute("value").trim();
		isEqual(actual, val, actual);
	}
	
	//验证 一个元素的text的值
	public static void assertElementText(By by, String val, String failMsg, WebDriver driver) {
		String actual = WebDriverUtils.waitForShort(by, driver).getText().trim();
		isEqual(actual, val, actual);
	}
	
	//验证 一个select的默认选中的值
	public static void assertDropDownDefaultSelected(By by, String val, String failMsg, WebDriver driver) {
		Select sel = new Select(driver.findElement(by));
		String actual = sel.getFirstSelectedOption().getText().trim();
		isEqual(actual, val, actual);
	}
	
	//验证hashmap相等
	public static void isHashMapEqual(HashMap<String, String> map1, HashMap<String, String> map2) {
		String message = "hashmap不相等： " + map1 + "不等于hashmap " + map2;
		if (map1.size() != map2.size()) {
			Assert.fail(message);
			return;
		}
		String tmp1;
		String tmp2;
		boolean b = false;
		for (Map.Entry<String, String> entry : map1.entrySet()) {
			if (map2.containsKey(entry.getKey())) {
				tmp1 = entry.getValue();
				tmp2 = map2.get(entry.getKey());
				if (null != tmp1 && null != tmp1) { // 都不为null
					if (tmp1.equals(tmp2)) {
						b = true;
						continue;
					} else {
						b = false;
						break;
					}
				} else if (null == tmp1 && null == tmp2) { // 都为null
					b = true;
					continue;
				} else {
					b = false;
					break;
				}
			} else {
				b = false;
				break;
			}
		}
		Assert.isTrue(b, message);
	}

	public static void isLinkedHashMapEqual(LinkedHashMap<String, String> map1, LinkedHashMap<String, String> map2) {
		isHashMapEqual(map1, map2);
	}
	
	public static void isListEqual(List<String> l1, List<String> l2) {
		String message = "List不相等： " + l1 + "不等于 " + l2;
		if (l1.size() != l2.size()) {
			Assert.fail(message);
			return;
		}
		for (int i=0;i<l1.size();i++) {
			Assert.isEqual(l1.get(i), l2.get(i), message);
		}
	}
	
	public static void isListWithLinkedHashMapEqual(List<LinkedHashMap<String, String>> l1, List<LinkedHashMap<String, String>> l2) {
		String message = "List LinkedHashMap 不相等： " + l1 + "不等于 " + l2;
		if (l1.size() != l2.size()) {
			Assert.fail(message);
			return;
		}
		for (int i=0;i<l1.size();i++) {
			Assert.isLinkedHashMapEqual(l1.get(i), l2.get(i));
		}
	}
}
