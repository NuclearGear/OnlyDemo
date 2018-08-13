package com.winmax.driver;

import java.io.File;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.winmax.config.Config;
import com.winmax.config.Const;

public class DriverFactory {

	private static WebDriver _driver;

	public static WebDriver startDriver() throws Exception {
		Config.get().initialize();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		if (Config.get().IE()) {
			if (System.getProperty("webdriver.ie.driver") == null)
				System.setProperty("webdriver.ie.driver", Const.IEDRIVER_PATH);
			capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			capabilities.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
			_driver = new InternetExplorerDriver(capabilities);

		} else if (Config.get().chrome()) {
			if (System.getProperty("webdriver.chrome.driver") == null) {
				System.setProperty("webdriver.chrome.driver", Const.CHROMEDRIVER_PATH);
			}
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_content_settings.popups", 0);
			String downloadFilePath = System.getProperty("user.dir") + Const.DOWNLOAD_FOLDER;
		    chromePrefs.put("download.default_directory", downloadFilePath);
		    chromePrefs.put("download.", Const.DOWNLOAD_FOLDER);
		    chromePrefs.put("profile.default_content_setting_values.images",1);
		    chromePrefs.put("profile.managed_default_content_settings.images",1);
		    chromePrefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player",1);
		    chromePrefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player",1);
		    ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--disable-popup-blocking");
			options.addArguments("-start-maximized");
//			options.addArguments("–user-data-dir="+Const.CROMEUSERDATA_PATH);
			options.addArguments("–user-data-dir=C:/Users/only/AppData/Local/Google/Chrome/User Data/Default");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			_driver = new ChromeDriver(capabilities);
		} else {
			if (System.getProperty("webdriver.gecko.driver") == null) {
				System.setProperty("webdriver.gecko.driver", Const.FFDRIVER_PATH);
			}			
	        _driver = new FirefoxDriver();
		}
		_driver.manage().window().maximize();
		return _driver;
	}
}
