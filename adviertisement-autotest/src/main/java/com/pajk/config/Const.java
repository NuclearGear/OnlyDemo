package com.pajk.config;

public class Const {

	public static final long TIMEOUT = 10;
	public static final long SHORT_TIMEOUT = 1;
	public static final long POLL = 1;
	public static final long TEST_TIMEOUT = 420000;
	public static final long MAIL_TIMEOUT = 160;
	public static final String CHROMEDRIVER_PATH = ".\\src\\test\\resources\\browserDriver\\chromedriver.exe";
	public static final String IEDRIVER_PATH = "D:\\driver\\IEDriverServer.exe";
	public static final String FFDRIVER_PATH = "D:\\driver\\geckodriver.exe";
	public static final String CROMEUSERDATA_PATH = "C:\\Users\\WENGLI571\\AppData\\Local\\Google\\Chrome\\User Data";
	public static final String BROWSER_PARAM = "browser";
	public static final String ENV_PARAM = "env";
	public static boolean BROWSER_HEADLESS = true;

	public static final String DEFAULT_BROWSER = "Chrome";
    public static String DEFAULT_ENVIRONMENT = "http://www.test.pajkdc.com/tgt/#/admin";
	public static final String SCREENSHOT_PARAM = "screenshot";
	public static final boolean SCROLLING = true;
	public static final boolean UPDATE_TESTLINK = false;
	public static final String TESTLINK_PARAM = "updateTestLink";
	
	public static final boolean Take_SCREENSHOT = true;
	public static final String SCREENSHOTS_SUBFOLDER_PARAM = "screenshot";

	public static final String GLUE = "com.pajk.steps";
	public static final String REPORT_FOLDER = "target/cucumberRerun/";

	public static int RERUN_TIMES = 0;

	public static String USER_NAME;
	public static String PASSWORD;

	public static final String DB_URL = "192.168.1.11:1433";
	public static final String DB_DatabaseName = "XXXX";
	public static final String DB_UserName = "XXXX";
	public static final String DB_Password = "XXXX";

/*	public static final String yacht_url = "jdbc:mysql://testdb-m1.db.pajkdc.com:3306/";
	public static final String yacht_db = "yacht";
	public static final String yacht_user = "yacht";
	public static final String yacht_pass = "yacht";

	public static final String adoutput_url = "jdbc:mysql://testdbmb4-m1.db.pajkdc.com/";
	public static final String adoutput_db = "adoutput";
	public static final String adoutput_user = "adoutput";
	public static final String adoutput_pass = "adoutput";*/

	public static final String DOWNLOAD_FOLDER = "\\src\\test\\resources\\download\\";
	public static final String MATERITAL_FOLDER = "\\src\\test\\resources\\material\\";
	public static final String DEFAULT_ATTA = "upload.txt";

	public static boolean getBrowserHeadless() {
		return BROWSER_HEADLESS;
	}
	public static void setBrowserHeadless(boolean browserHeadless) {
		BROWSER_HEADLESS = browserHeadless;
	}

    public static void setDefaultEnvironment(String defaultEnvironment) {
        DEFAULT_ENVIRONMENT = defaultEnvironment;
    }

	public static void setUserName(String userName) {
		USER_NAME = userName;
	}

	public static void setPASSWORD(String PASSWORD) {
		Const.PASSWORD = PASSWORD;
	}

	public static void setRerunTimes(int rerunTimes) {
		RERUN_TIMES = rerunTimes;
	}

}
