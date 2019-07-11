package com.pajk.driver;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.pajk.config.Config;
import com.pajk.config.Const;

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
            chromePrefs.put("download.prompt_for_download", "False");
		    chromePrefs.put("profile.default_content_setting_values.images",1);
		    chromePrefs.put("profile.managed_default_content_settings.images",1);
		    chromePrefs.put("profile.content_settings.plugin_whitelist.adobe-flash-player",1);
		    chromePrefs.put("profile.content_settings.exceptions.plugins.*,*.per_resource.adobe-flash-player",1);
			chromePrefs.put("Browser.setDownloadBehavior", "allow");
		    ChromeOptions options = new ChromeOptions();
		    options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--disable-popup-blocking");
			options.addArguments("-start-maximized");
			options.addArguments("–user-data-dir="+Const.CROMEUSERDATA_PATH);
            //无头浏览器模式
			if (Const.BROWSER_HEADLESS) {
                options.addArguments("--headless");
            }
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            //_driver = new ChromeDriver(driverService, capabilities);

            //无头浏览器下载文件
            ChromeDriverService driverService = ChromeDriverService.createDefaultService();
            ChromeDriver driver = new ChromeDriver(driverService,capabilities);
            Map<String, Object> commandParams = new HashMap<String, Object>();
            commandParams.put("cmd", "Page.setDownloadBehavior");
            Map<String, String> params = new HashMap<String, String>();
            params.put("behavior", "allow");
            params.put("downloadPath", downloadFilePath);
            commandParams.put("params", params);
            ObjectMapper objectMapper = new ObjectMapper();
            HttpClient httpClient = HttpClientBuilder.create().build();
            String command = objectMapper.writeValueAsString(commandParams);
            String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
            HttpPost request = new HttpPost(u);
            request.addHeader("content-type", "application/json");
            request.setEntity(new StringEntity(command));
            httpClient.execute(request);
            _driver = driver;
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
