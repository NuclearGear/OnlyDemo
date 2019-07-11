package com.pajk.config;

public class Config {
	private static final Config INSTANCE = new Config();

	private String _environment;
	private String _browser;
	private String lastTestStatus = "nothing";
	private boolean _extendedScreenshots;
	private boolean _enableScrolling = Const.SCROLLING;
	private boolean _takeScreenshot = Const.Take_SCREENSHOT;
	private boolean _updateTestLink = Const.UPDATE_TESTLINK;
	private String _screenshots_folder = Const.SCREENSHOTS_SUBFOLDER_PARAM;

	
	private Config() {
		super();
	}

	public static Config get() {
		return INSTANCE;
	}

	public void initialize() {
		_screenshots_folder = getSystemProperty(Const.SCREENSHOTS_SUBFOLDER_PARAM, _screenshots_folder);
		_browser = getSystemProperty(Const.BROWSER_PARAM, Const.DEFAULT_BROWSER);	
		_environment = getSystemProperty(Const.ENV_PARAM, Const.DEFAULT_ENVIRONMENT);
		_updateTestLink = Boolean.parseBoolean(getSystemProperty(Const.TESTLINK_PARAM, String.valueOf(_updateTestLink)));
	}

	private static String getSystemProperty(String key, String defaultValue) {
		return (System.getProperty(key) == null) ? defaultValue : System.getProperty(key);
	}
	
	public String getEnvironment() {
		return _environment;
	}

	public boolean IE() {
		return _browser.toLowerCase().equals("ie");
	}
	
	public boolean chrome() {
		return _browser.toLowerCase().equals("chrome");
	}
	
	public boolean firefox() {
		return _browser.toLowerCase().equals("firefox");
	}

	public void setLastTestStatus(String status) {
		this.lastTestStatus = status;
	}

	public String getLastTestStatus() {
		return lastTestStatus;
	}

	public boolean extendedScreenshots() {
		return _extendedScreenshots;
	}

	public boolean isScrollingEnabled() {
		return _enableScrolling;
	}

	public void enableScrolling(boolean b) {
		this._enableScrolling = b;
	}

	public boolean takeScreenshot() {
		return this._takeScreenshot;
	}

	public String getScreenshotPath() {
		return this._screenshots_folder;
	}

	public boolean updateTestLink() {
		return this._updateTestLink;
	}
 
}
