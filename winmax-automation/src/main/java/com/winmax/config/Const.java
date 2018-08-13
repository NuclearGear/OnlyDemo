package com.winmax.config;

public class Const {

	public static final long TIMEOUT = 10;
	public static final long SHORT_TIMEOUT = 5;
	public static final long POLL = 1;
	public static final long TEST_TIMEOUT = 420000;
	public static final long MAIL_TIMEOUT = 160;
	public static final String CHROMEDRIVER_PATH = "C:\\driver\\chromedriver.exe";
//	public static final String CHROMEDRIVER_PATH = "C:\\Users\\only\\AppData\\Local\\Google\\Chrome\\Application\\chromedriver.exe";
	public static final String IEDRIVER_PATH = "C:\\driver\\IEDriverServer.exe";
	public static final String FFDRIVER_PATH = "C:\\driver\\geckodriver.exe";	
	public static final String CROMEUSERDATA_PATH = "C:\\Users\\only\\AppData\\Local\\Google\\Chrome\\User Data";	
	public static final String BROWSER_PARAM = "browser";
	public static final String ENV_PARAM = "env";
	
	public static final String DEFAULT_BROWSER = "chrome";
	public static final String DEFAULT_ENVIRONMENT = "https://qawm.quantone.com/site/login";
	public static final String SCREENSHOT_PARAM = "screenshot";
	public static final boolean SCROLLING = true;
	public static final boolean UPDATE_TESTLINK = false;
	public static final String TESTLINK_PARAM = "updateTestLink";
	
	public static final boolean Take_SCREENSHOT = true;
	public static final String SCREENSHOTS_SUBFOLDER_PARAM = "screenshot";

	public static final String GLUE = "com.winmax.steps";
	public static final String REPORT_FOLDER = "target/cucumberRerun/";
	public static final int RERUN_TIMES = 0;
	
	public static final String USER_NAME = "root";
	public static final String PASSWORD = "QA2018tet";
	public static final String DEFAULT_ATTA = "upload.txt";
	public static final String DB_URL = "192.168.1.11:1433";
	public static final String DB_DatabaseName = "XXXX";
	public static final String DB_UserName = "XXXX";
	public static final String DB_Password = "XXXX";
	
	public static final String DOWNLOAD_FOLDER = "\\src\\test\\resources\\downloads\\";
	
}
