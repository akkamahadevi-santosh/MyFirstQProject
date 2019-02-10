package com.exilant.qutap.plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;

import org.openqa.selenium.NoSuchElementException;

public class AppiumAndroidTesting {
	static WebDriver driver;
	//AndroidDriver<?>   driver;
	
	String res = null;
	static String runningEmulatorName;
	private static String sdkPath;
	private static String emulatorPath;
	
	public String launchEmulator(String emulatorName) {
		
		Properties prop = null;
	try {
				prop = getProperties();
			} catch (AppiumConfigException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		sdkPath = prop.getProperty("sdkPath");
		emulatorPath = sdkPath + "tools" + File.separator + "emulator";
		String[] aCommand = new String[] { emulatorPath, "-avd", emulatorName };
		try {
			Process process = new ProcessBuilder(aCommand).start();
			process.waitFor(210, TimeUnit.SECONDS);
			//System.out.println("Emulator launched successfully!");
			runningEmulatorName = emulatorName;
			res = "pass";
		} catch (Exception e) {
			res = e.getMessage();
		}
		return res;
	}

	public Properties getProperties() throws AppiumConfigException {
		Properties prop = new Properties();
		String propFileName = "Appium.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				throw new AppiumConfigException("Initialization ERROR: could not load " + propFileName + "", e);
			}
		} else
			throw new AppiumConfigException("Initialization ERROR: no Appium configuration was found. Please check if "
					+ propFileName + " file is in class path", null);
		return prop;
	}

	public String launchApp(String appName) throws ExecuteException, IOException, InterruptedException {
		
		Properties prop = null;
		try {
			prop = getProperties();
			
			
			CommandLine command = new CommandLine(prop.getProperty("nodePath"));
			command.addArgument(prop.getProperty("appiumPath"));
			command.addArgument("--address");
			command.addArgument(prop.getProperty("appiumServerAddress"));
			command.addArgument("--port");
			command.addArgument(prop.getProperty("appiumPort"));
			command.addArgument("--no-reset", false);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
			DefaultExecutor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.execute(command, resultHandler);
			Thread.sleep(20000);

			Set<Object> keys = prop.keySet();
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability("platformName", prop.getProperty("platformName"));
			capabilities.setCapability("platformVersion", prop.getProperty("platformVersion"));
			capabilities.setCapability("deviceName", runningEmulatorName);
			String str =prop.getProperty("appiumServerAddress");
			String str1 = prop.getProperty("appiumPort");
			
			
			if(appName.equalsIgnoreCase("flipkart")){
				
				capabilities.setCapability("app", prop.getProperty("flipkart.app"));
				capabilities.setCapability("appPackage", prop.getProperty("flipkart.appPackage"));
				capabilities.setCapability("appActivity", prop.getProperty("flipkart.appActivity"));
				driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		        //driver = new RemoteWebDriver(new URL("http://"+str+":"+str1+"/wd/hub"), capabilities);
				//driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
				
			}
			if(appName.equalsIgnoreCase("OLX")){
				
				capabilities.setCapability("app", prop.getProperty("OLX.app"));
				capabilities.setCapability("appPackage", prop.getProperty("OLX.appPackage"));
				capabilities.setCapability("appActivity", prop.getProperty("OLX.appActivity"));
				//driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
				
			}
			
			res = "pass";
		}catch (AppiumConfigException e) {
			
			e.printStackTrace();
			res = e.getMessage();
		}
		
		return res;
	}

	public String clickAction(String LocatorType, String LocatorValue) throws InterruptedException{
		//driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		Thread.sleep(15000);
		try {
			By locator;
			locator = locatorValue(LocatorType, LocatorValue);
			driver.findElement(locator).click();
		    res = "pass";
		} catch (NoSuchElementException e) {
			//System.err.print("There is no link to click" + e);
			res = e.getMessage();
		}
		return res;
	}

	public String enterText(String locatorType, String locatorValue, String text) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(240, TimeUnit.SECONDS);
		//Thread.sleep(10000);
		try {
			By locator;
			locator = locatorValue(locatorType, locatorValue);
			WebElement element = driver.findElement(locator);
			element.sendKeys(text);
			res = "pass";
		} catch (NoSuchElementException e) {
			//System.err.print("No Element Found to enter text" + e);
			res = e.getMessage();
		}
		return res;
	}

  public By locatorValue(String locatorType, String locatorValue) {
		By by = null;
		if (locatorType.equalsIgnoreCase("xpath")) {
			by = By.xpath(locatorValue);
		}
		return by;
	}

	public String close_Browser() throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
		try {
			driver.quit();
			res = "pass";
		} catch (Exception e) {
			res = e.getMessage();

		}
		return res;

	}

	
}
