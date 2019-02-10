package com.exilant.qutap.plugin;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.exilant.qutap.customException.SeleniumActionException;

public class SeleniumPluginHelper {
	private static WebDriver driver;
	private String winHandleBefore;
	public String result = null;
	JSONObject JSONRes;
	JSONObject respJSON = new JSONObject();
	StringWriter errors = new StringWriter();
	String res = "";
	String error = "";
	static String browserName;
	String xpathValue = "xpath";
	private static String parent;
	static String capture;
	
	//StoreScreenShot storeScreenShot= new StoreScreenShot();
	static String screenShot="";

	// ="A-315214" ="A-916270" ="A-691183"

	Logger log = LoggerFactory.getLogger(SeleniumPluginHelper.class);

	LoadConfiguration loadConfig = new LoadConfiguration();
	Properties PROPS1 = loadConfig.getProperties();

	String chromeDriverPath = PROPS1.getProperty("chromeDriverPath");
	String geckoDriverPath = PROPS1.getProperty("geckoDriverPath");
	String safariDriverPath = PROPS1.getProperty("safariDriverPath");
	String screenshotFolderPath = PROPS1.getProperty("screenshotFolderPath");

	public JSONObject launchBrowser(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("firefox")) {
				System.out.println("launchBrowser called" + browserName);
				System.setProperty("webdriver.gecko.driver", geckoDriverPath);

				driver = new FirefoxDriver();
				driver.manage().window().maximize();
				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) ("Navigeted to"));
				this.respJSON.put("error", (Object) "");
				this.browserName = browserName;
			}
			if (browserName.equalsIgnoreCase("chrome")) {
				System.out.println("launchBrowser called" + browserName);
				System.out.println("seleConfig:::::" + chromeDriverPath);
				System.setProperty("webdriver.chrome.driver", chromeDriverPath);
				driver = new ChromeDriver();
				driver.manage().window().maximize();

				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) ("Navigeted to"));
				this.respJSON.put("error", (Object) "");
				this.browserName = browserName;
			}
			if (browserName.equalsIgnoreCase("Safari")) {
				System.out.println("launchBrowser called" + browserName);
				System.setProperty("webdriver.safari.driver", safariDriverPath);
				driver = new SafariDriver();
				driver.manage().window().maximize();
				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) ("Navigeted to"));
				this.respJSON.put("error", (Object) "");
				this.browserName = browserName;
			}

		} catch (WebDriverException e) {
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("status", (Object) "FAIL");
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in launching browser", e);
			throw new SeleniumActionException("error in launching browser");
		}
		return this.respJSON;
	}

	public JSONObject launchApplication(String URL) {
		try {
			System.out.println("enter_URL called" + URL);
			driver.get(URL);
			// driver.navigate().to(URL);

			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Navigeted to" + URL));
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in launching Application", e);
			throw new SeleniumActionException("error in launching Application");

		}
		return this.respJSON;
	}

	public By locatorValue(String locatorType, String value) {
		try {
			By by;
			switch (locatorType) {
			case "id": {
				by = By.id((String) value);
				break;
			}
			case "name": {
				by = By.name((String) value);
				break;
			}
			case "xpath": {
				by = By.xpath((String) value);
				break;
			}
			case "css": {
				by = By.cssSelector((String) value);
				break;
			}
			case "linkText": {
				by = By.linkText((String) value);
				break;
			}
			case "partialLinkText": {
				by = By.partialLinkText((String) value);
				break;
			}
			default: {
				by = null;
			}
			}
			return by;
		} catch (Exception e) {
			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in getting locator value", e);
			throw new SeleniumActionException("error in getting locator Value");
		}
	}

	public JSONObject textBox(String locatorType, String value, String text) {
		try {
			System.out.println("TextBox called value .." + value + "text" + text);
			By locator = this.locatorValue("xpath", value);
			// WebElement element = driver.findElement(locator);
			WebElement element = explicit(driver, locator);
			element.clear();
			// element.sendKeys(new CharSequence[]{text});
			element.sendKeys(text);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) (text + " Has entered"));
			this.respJSON.put("error", (Object) "");
		}
		// catch (NoSuchElementException e)
		catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in textBox Action", e);
			throw new SeleniumActionException("error in textBox Action");
		}
		return this.respJSON;
	}

	public JSONObject reUseCapture(String locatorType, String value, String text) {
		try {
			System.out.println("TextBox called value .." + value + "text" + text);
			By locator = this.locatorValue("xpath", value);
			// WebElement element = driver.findElement(locator);
			WebElement element = explicit(driver, locator);
			element.clear();
			element.sendKeys(new CharSequence[] { capture });
			// A-621254
			// element.sendKeys("A-691183");
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) (text + " Has entered"));
			this.respJSON.put("error", (Object) "");
		} catch (NoSuchElementException e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in reUseCapture Action", e);
			throw new SeleniumActionException("error in reUseCapture Action");
		}
		return this.respJSON;
	}

	public JSONObject click(String locatorType, String value, String text) {
		try {

			System.out.println("click called " + value);
			By locator = this.locatorValue(xpathValue, value);
			if ((value.contains("%s")) && (!text.isEmpty())) {
				text = text.split("\"")[1];
				String pathValue = value.replace("%s", text);
				By locator1 = this.locatorValue(xpathValue, pathValue);
				// WebElement ele = driver.findElement(locator1);
				WebElement ele = explicit(driver, locator1);
				System.out.println("COOOOOOOOOOMMMMMMMMMMIIIIIIINNNNNNG");
				ele.click();
			} else {
				// WebElement element = driver.findElement(locator);
				WebElement element = explicit(driver, locator);
				element.click();
			}
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "click action done");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			System.out.println("COMMMMMMMMMMMMMMMMMMMMINGH IN CLICK EXCE");
			log.error("error in click Method", e);
			throw new SeleniumActionException("error in click Method");
		}
		return this.respJSON;
	}

	public JSONObject click_Action(String locatorType, String value, String text) {
		try {

			System.out.println("click called " + value);
			By locator = this.locatorValue(xpathValue, value);
			if ((value.contains("%s")) && (!text.isEmpty())) {
				String pathValue = value.replace("%s", capture);
				System.out.println("CLICK ACTION" + pathValue);
				System.out.println("Captured" + capture);

				System.out.println("SEEEEEEEEEEEEEEEEE" + pathValue);
				By locator1 = this.locatorValue(xpathValue, pathValue);
				// WebElement ele = driver.findElement(locator1);
				WebElement ele = explicit(driver, locator1);
				System.out.println("COOOOOOOOOOMMMMMMMMMMIIIIIIINNNNNNG");
				ele.click();
			} else {
				// WebElement element = driver.findElement(locator);
				WebElement element = explicit(driver, locator);
				element.click();
			}
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "click action done");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot("/Users/sandeepbabu.s/Desktop/workExcel/screenShotFileFolder");
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			System.out.println("COMMMMMMMMMMMMMMMMMMMMINGH IN CLICK EXCE");
			log.error("error in click_Action Method", e);
			throw new SeleniumActionException("error in click_Action Method");
		}
		return this.respJSON;
	}

	public JSONObject submit(String locatorType, String value) throws InterruptedException {
		try {
			By locator = this.locatorValue(xpathValue, value);
			// WebElement element = driver.findElement(locator);
			WebElement element = explicit(driver, locator);
			element.click();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "click action done");
			this.respJSON.put("error", (Object) "");
		} catch (NoSuchElementException e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			System.out.println("COMMMMMMMMMMMMMMMMMMMMINGH IN CLICK EXCE");
			log.error("error in submit Action", e);
			throw new SeleniumActionException("error in submit Action");
		}
		return this.respJSON;
	}

	public JSONObject switch_ToDefault() {
		try {
			driver.switchTo().defaultContent();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched  to the default content");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in switch_ToDefault Action", e);
			throw new SeleniumActionException("error in switch_ToDefault Action");
		}
		return this.respJSON;
	}

	public JSONObject switchToFrameByIndex(String index) {
		try {
			// Thread.sleep(45000);
			// waitForPageToLoad(20);
			System.out.println("switchToFrameByIndex called .." + index);
			int intValue = (int) Double.parseDouble(index.trim());
			System.out.println("switchToFrameByIndex b4 swithcing");
			driver.switchTo().frame(intValue);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched  to the specified frame");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in switchToFrameByIndex Action", e);
			throw new SeleniumActionException("error in switchToFrameByIndex Action");
		}
		return this.respJSON;
	}

	public JSONObject move_Cursor(String locatorType, String value) {
		try {
			Actions act = new Actions(driver);
			By locator = this.locatorValue(xpathValue, value);
			WebElement element = driver.findElement(locator);
			act.moveToElement(element).perform();
			Thread.sleep(1000);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Cursor moved to the specified element");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in move_Cursor Action", e);
			throw new SeleniumActionException("error in move_Cursor Action");
		}
		return this.respJSON;
	}

	public JSONObject closeBrowser() {
		try {
			System.out.println("closeBrowser called ..");
			driver.quit();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Firefox browser closed");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {
			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in closeBrowser Action", e);
			throw new SeleniumActionException("error in closeBrowser Action");
		}
		return this.respJSON;
	}

	public JSONObject refresh() {
		try {
			driver.navigate().refresh();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Page Refreshed");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in refresh Action", e);
			throw new SeleniumActionException("error in refresh Action");
		}
		return this.respJSON;
	}

	/*
	 * public JSONObject snooze(String time) { try {
	 * System.out.println("snooze()"+time +"called ........"); int
	 * intValue=(int)Double.parseDouble(time.trim());
	 * System.out.println("snooze()"+intValue +"called intValue........");
	 * 
	 * Thread.sleep(intValue* 100); this.respJSON.put("status", (Object)"PASS");
	 * this.respJSON.put("response", (Object)("Waiting..." + intValue +
	 * " Seconds")); this.respJSON.put("error", (Object)""); } catch (Exception e) {
	 * 
	 * this.respJSON.put("status", (Object)"FAIL"); this.respJSON.put("response",
	 * (Object)""); this.respJSON.put("error", (Object)this.errors.toString());
	 * log.error("error in snooze Action", e); throw new
	 * SeleniumAction("error in snooze Action"); } return this.respJSON; }
	 */

//	public JSONObject snooze(Integer time) {
//		try {
//			System.out.println("wait():::::called ........"+time);
//
//			int a = time;
//			System.out.println("wait():::::called ........"+a);
//			System.out.println("wait()"+a +"called ........");
//			Thread.sleep(a);
//			this.respJSON.put("status", (Object)"PASS");
//			this.respJSON.put("response", (Object)("Waiting..." + a + " Seconds"));
//			this.respJSON.put("error", (Object)"");
//		}
//		catch (Exception e) {
//			
//			this.respJSON.put("status", (Object)"FAIL");
//			String screenShot=snapShot(screenshotFolderPath);
//			this.respJSON.put("screenShot", screenShot);
//			this.respJSON.put("response", (Object)"");
//			this.respJSON.put("error", (Object)this.errors.toString());
	// log.error("error in snooze Action", e);
	// throw new SeleniumAction("error in snooze Action");

//		}
//		return this.respJSON;
//	}
	public JSONObject wait(Integer time) {
		try {
			System.out.println("wait():::::called ........" + time);

			int a = time;
			System.out.println("wait():::::called ........" + a);
			System.out.println("wait()" + a + "called ........");
			Thread.sleep(a);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Waiting..." + a + " Seconds"));
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in wait Action", e);
			throw new SeleniumActionException("error in wait Action");
		}
		return this.respJSON;
	}

	public JSONObject verify_Text(String locatorType, String value, String text) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			WebElement element = driver.findElement(locator);
			String displayedValue = element.getText();
			if (displayedValue.contains(text)) {
				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) displayedValue);
				this.respJSON.put("error", (Object) "");
			} else {
				this.respJSON.put("status", (Object) "FAIL");
				this.respJSON.put("response", (Object) displayedValue);
				this.respJSON.put("error", (Object) "Not valid text");
			}
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in verify_Text Action", e);
			throw new SeleniumActionException("error in verify_Text Action");
		}
		return this.respJSON;
	}

	public JSONObject clear_Text(String locatorType, String value) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			driver.findElement(locator).clear();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "TextBox cleared");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in clear_Text Action", e);
			throw new SeleniumActionException("error in clear_Text Action");
		}
		return this.respJSON;
	}

	public JSONObject select_Dropdown(String locatorType, String value, String selectValue) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			WebElement element = driver.findElement(locator);
			Select sdd = new Select(element);
			sdd.selectByVisibleText(selectValue);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Selected value :" + selectValue));
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in select_Dropdown Action", e);
			throw new SeleniumActionException("error in select_Dropdown Action");
		}
		return this.respJSON;
	}

	public JSONObject verify_Count(String locatorType, String value, String text) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			List elements = driver.findElements(locator);
			Integer count = elements.size();
			if (count.toString().equals(text)) {
				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) count);
				this.respJSON.put("error", (Object) "");
			} else {
				this.respJSON.put("status", (Object) "FAIL");
				this.respJSON.put("response", (Object) count);
				this.respJSON.put("error", (Object) "Not a valid count");
			}
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in verify_Count Action", e);
			throw new SeleniumActionException("error in verify_Count Action");
		}
		return this.respJSON;
	}

	public JSONObject right_Click(String locatorType, String value) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			WebElement contextElement = driver.findElement(locator);

			Actions element = new Actions(driver);
			element.contextClick(contextElement).build().perform();

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "context element clicked");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in right_Click Action", e);
			throw new SeleniumActionException("error in right_Click Action");
		}
		return this.respJSON;

	}

	public JSONObject switchTo_Popup(String locatorType, String value) {
		try {
			By locator = this.locatorValue(xpathValue, value);
			driver.findElement(locator).click();

			String myWindowHandle = driver.getWindowHandle();
			driver.switchTo().window(myWindowHandle);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched to popup");
			this.respJSON.put("error", (Object) "");

		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in switchTo_Popup Action", e);
			throw new SeleniumActionException("error in switchTo_Popup Action");
		}
		return this.respJSON;

	}

	public JSONObject file_Upload(String filePath) {
		try {
			StringSelection stringSelection = new StringSelection(filePath);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			Robot robot = new Robot();
			robot.keyPress(157);
			robot.keyPress(9);
			robot.keyRelease(157);
			robot.keyRelease(9);
			robot.delay(500);
			robot.keyPress(157);
			robot.keyPress(16);
			robot.keyPress(71);
			robot.keyRelease(157);
			robot.keyRelease(16);
			robot.keyRelease(71);
			robot.delay(500);
			robot.keyPress(157);
			robot.keyPress(86);
			robot.keyRelease(157);
			robot.keyRelease(86);
			robot.delay(500);
			robot.keyPress(10);
			robot.keyRelease(10);
			robot.delay(3000);
			robot.keyPress(10);
			robot.keyRelease(10);
			robot.delay(3000);
			robot.keyPress(10);
			robot.keyRelease(10);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "File Uploaded Successfully");
			this.respJSON.put("error", (Object) this.error);
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in file_Upload Action", e);
			throw new SeleniumActionException("error in file_Upload Action");
		}
		return this.respJSON;
	}

	/*******************************************************************************************************************************************/

	// newly added............
	public JSONObject closeWindow() {
		try {

			driver.close();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Firefox Window closed");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in closeWindow Action", e);
			throw new SeleniumActionException("error in closeWindow Action");

		}
		return this.respJSON;
	}

	public JSONObject switchToFrameByID(String value) {
		try {
			// Thread.sleep(9000);
			// waitForPageToLoad(20);
			WebElement wait = new WebDriverWait(driver, 60).ignoring(StaleElementReferenceException.class)
					.ignoring(WebDriverException.class)
					.until(ExpectedConditions.visibilityOf((driver.findElement(By.id(value)))));
			System.out.println("switchToFrameByID:::::::" + value);
			List<WebElement> frames = driver.findElements(By.xpath(".//iframe"));
			boolean switchFrame = false;

			for (Iterator<WebElement> iterator = frames.iterator(); iterator.hasNext();) {

				WebElement webElement = (WebElement) iterator.next();

				if (webElement.getAttribute("id").equals(value)) {

					switchFrame = true;
					driver.switchTo().frame(webElement);
					break;
				}

			}

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched to new frame by ID");
			this.respJSON.put("error", (Object) "");

		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in switchToFrameByID Action", e);
			throw new SeleniumActionException("error in switchToFrameByID Action");
		}
		return this.respJSON;
	}
//waitForElementToLoad

	public JSONObject waitForElementToLoad(String locatorType, String value) throws InterruptedException {
		try {
			System.out.println("waitForElementToLoad:::::::" + locatorType + ":::vlue::::" + value);
			By locator = this.locatorValue(xpathValue, value);
			// waitForPageToLoad(30);
			WebElement element = driver.findElement(locator);

			WebDriverWait wait = new WebDriverWait(driver, 100);
			// wait.until(ExpectedConditions.visibilityOf(ele));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "wait For Element To Load");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in waitForElementToLoad Action", e);
			throw new SeleniumActionException("error in waitForElementToLoad Action");
		}
		return this.respJSON;
	}

	public void waitForPageToLoad(int timesecs) {
		try {
			System.out.println("waitForPageToLoad:::::::" + timesecs);

			driver.manage().timeouts().implicitlyWait(timesecs, TimeUnit.SECONDS);
		} catch (Exception e) {
			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in waitForPageToLoad Action", e);
			throw new SeleniumActionException("error in waitForPageToLoad Action");
		}
	}

	public JSONObject switchToMainContent() {
		try {

			driver.switchTo().defaultContent();

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched to main content");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in switchToMainContent Action", e);
			throw new SeleniumActionException("error in switchToMainContent Action");

		}
		return this.respJSON;
	}

	public JSONObject switchToWindowByIndex(String value) {
		System.out.println("switchToWindowByIndex ..value" + value);
		try {

			Set<String> windows = driver.getWindowHandles();
			System.out.println("windowsssssssssssss......." + windows);
			System.out.println("b4 switching");
			driver.switchTo().window((String) windows.toArray()[(int) Double.parseDouble(value.trim())]);
			System.out.println("after switching");

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "switched to window by index");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.error);
			log.error("error in switchToWindowByIndex Action", e);
			throw new SeleniumActionException("error in switchToWindowByIndex Action");
		}
		return this.respJSON;
	}

	/*
	 * public JSONObject runAppleScriptForFileUpload(String locatorType, String
	 * value) { try {
	 * System.out.println("runAppleScriptForFileUpload ..value"+value);
	 * ///Users/ramana.k/Documents/ImportantDocs/uploadApple.scpt By locator =
	 * this.locatorValue(locatorType, value);
	 * 
	 * WebElement element=driver.findElement(locator); element.click();
	 * Thread.sleep(2000); Runtime.getRuntime().exec(
	 * "osascript"+" Users/ramana.k/Documents/ImportantDocs/uploadApple.scpt");
	 * this.respJSON.put("status", (Object)"PASS"); this.respJSON.put("response",
	 * (Object)"File Uploaded Successfully"); this.respJSON.put("error",
	 * (Object)this.error); } catch (Exception e) { this.error = e.getMessage();
	 * this.respJSON.put("status", (Object)"FAIL"); this.respJSON.put("response",
	 * (Object)""); this.respJSON.put("error", (Object)this.error);
	 * log.error("error in runAppleScriptForFileUpload Action", e); throw new
	 * SeleniumActionException("error in runAppleScriptForFileUpload Action"); }
	 * return this.respJSON; }
	 */

	// String locatorType,

	/*
	 * public JSONObject runAppleScriptForFileUpload(String value) { try {
	 * System.out.println("runAppleScriptForFileUpload ..value"+value);
	 * 
	 * By locator = this.locatorValue(xpathValue, value);
	 * 
	 * // WebElement element=driver.findElement(locator); WebElement
	 * element=explicit(driver, locator); element.click(); Thread.sleep(2000);
	 * 
	 * //Runtime.getRuntime().exec(
	 * "osascript"+" Users/ramana.k/Documents/ImportantDocs/uploadApple.scpt");
	 * 
	 * Runtime runtime=Runtime.getRuntime(); String
	 * applescriptCommand="tell app \"System Events\"\n"+
	 * "keystroke \"G\" using {command down,shift down}\n"+ "delay 2\n"+
	 * "keystroke \"" +value+"\"\n"+ "delay 1\n"+ "keystroke return\n"+ "delay 1\n"+
	 * "keystroke return\n"+ "end tell";
	 * 
	 * System.out.println("applescriptCommand:::"+applescriptCommand);
	 * 
	 * String[] args= {"osascript", "-e",applescriptCommand}; Process
	 * process=runtime.exec(args);
	 * 
	 * 
	 * this.respJSON.put("status", (Object)"PASS"); this.respJSON.put("response",
	 * (Object)"File Uploaded Successfully"); this.respJSON.put("error",
	 * (Object)this.error); } catch (Exception e) { this.error = e.getMessage();
	 * this.respJSON.put("status", (Object)"FAIL"); String
	 * screenShot=snapShot(screenshotFolderPath); this.respJSON.put("screenShot",
	 * screenShot); this.respJSON.put("response", (Object)"");
	 * this.respJSON.put("error", (Object)this.error);
	 * log.error("error in runAppleScriptForFileUpload Action", e); throw new
	 * SeleniumActionException("error in runAppleScriptForFileUpload Action"); }
	 * return this.respJSON; }
	 */

	/*******************************************************************************************************************************************/
	// kavita&devi
	/*******************************************************************************************************************************************/
	public JSONObject checkAttributeValue(String locatorType, String value, String stepParam, String paramData) {
		try {

			By locator = this.locatorValue(xpathValue, value);
			explicit(driver, locator);
			WebElement element = driver.findElement(locator);

			System.out.println("inside checkAttributeValue " + xpathValue);

			String actualValue = element.getAttribute(stepParam);
			System.out.println("Actual Value: " + actualValue);
			String data = paramData.substring(0, 1);
			System.out.println("param Value: " + data);
			if (actualValue.equalsIgnoreCase(data)) {
				System.out.println("Attribute value is correct...");
				this.respJSON.put("status", (Object) "PASS");
				this.respJSON.put("response", (Object) ("Checked Attribute value of the type " + xpathValue));
				this.respJSON.put("error", (Object) "");
			} else {
				System.out.println("Incorrect Attribute Value");
				String screenShot = snapShot(screenshotFolderPath);
				StoreScreenShot.setScreenshot(screenShot);
				this.respJSON.put("screenShot", screenShot);
				this.respJSON.put("status", (Object) "FAIL");
				this.respJSON.put("response", (Object) "Incorrect Attribute Value");
				this.respJSON.put("error", (Object) this.errors.toString());
			}

		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in checkAttributeValue Action", e);
			throw new SeleniumActionException("error in checkAttributeValue Action");

		}
		return this.respJSON;
	}

	public JSONObject generateRandomNumbers(String locatorType, String value) {
		try {

			System.out.println(locatorType + " " + value);
			Random random = new Random();
			String arr[] = String.format("A_%04d", random.nextInt(10000)).split("");

			By locator = this.locatorValue(locatorType, value);
			WebElement element = driver.findElement(locator);
			;
			element.sendKeys(arr);
			Thread.sleep(3000);
			driver.quit();

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Random Number generated"));
			this.respJSON.put("error", (Object) "");

		} catch (Exception e) {
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("status", (Object) "FAIL");
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());

			log.error("error in generateRandomNumbers Action", e);
			throw new SeleniumActionException("error in generateRandomNumbers Action");

		}
		return this.respJSON;
	}

	public JSONObject close_Window() {
		try {
			driver.close();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Firefox window closed");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in close_Window Action", e);
			throw new SeleniumActionException("error in close_Window Action");
		}
		return this.respJSON;
	}

	/*******************************************************************************************************************************************/
	// samas&tanu
	/*******************************************************************************************************************************************/
	public String getWinHandleBefore() {
		return winHandleBefore;
	}

	public void setWinHandleBefore(String winHandleBefore) {
		this.winHandleBefore = winHandleBefore;
	}

	/*
	 * public JSONObject storeParentBrowserWindow() { try {
	 * setWinHandleBefore(driver.getWindowHandle()); this.respJSON.put("status",
	 * (Object)"PASS"); this.respJSON.put("response", (Object)("Parent Url :"
	 * +driver.getCurrentUrl())); this.respJSON.put("error", (Object)"");
	 * }catch(Exception e) { e.printStackTrace(new PrintWriter(this.errors));
	 * this.respJSON.put("status", (Object)"FAIL"); String
	 * screenShot=snapShot(screenshotFolderPath); this.respJSON.put("screenShot",
	 * screenShot); this.respJSON.put("response", (Object)"");
	 * this.respJSON.put("error", (Object)this.errors.toString()); } return
	 * this.respJSON; }
	 */

	public JSONObject storeParentBrowserWindow() {
		try {
			// storing parent window in static variable
			parent = driver.getWindowHandle();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Parent Url :" + driver.getCurrentUrl()));
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in storeParentBrowserWindow Action", e);
			throw new SeleniumActionException("error in storeParentBrowserWindow Action");
		}
		return this.respJSON;
	}

	public JSONObject captureUIObject(String locatorType, String value) throws InterruptedException {
		try {

			By locator = this.locatorValue(xpathValue, value);
			// WebElement ele = driver.findElement(locator);
			WebElement element = explicit(driver, locator);
			// ele.sendKeys(value);
			// Thread.sleep(3000);
			capture = element.getAttribute("value");
			if (capture == null) {
				capture = element.getText();
			}
			System.out.println("CAPTURE VALUE" + capture);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Selected value :" + capture));
			this.respJSON.put("error", (Object) "");

		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in captureUIObject Action", e);
			throw new SeleniumActionException("error in captureUIObject Action");
		}
		return this.respJSON;
	}

//    	public JSONObject switchToWindow(String locatorType, String value) {
//            try {
//             
//         String parentGUID = driver.getWindowHandle();
//         By locator=this.locatorValue(locatorType, value);
//             WebElement element = driver.findElement(locator);
//             element.click();
//         Thread.sleep(5000);
//         Set<String> allGUID = driver.getWindowHandles();
//
//         
//         System.out.println("Page title before Switching : "+ driver.getTitle());
//         System.out.println("Total Windows : "+allGUID.size());
//         
//         for(String guid : allGUID){
//         
//         if(! guid.equals(parentGUID)){
//         
//         driver.switchTo().window(guid);
//         //driver.switchTo().frame(guid);
//         break;
//         }
//         }
//         
//              this.respJSON.put("status", (Object)"PASS");
//                 this.respJSON.put("response", (Object)"Switched to window");
//                 this.respJSON.put("error", (Object)"");
//            }
//            catch (Exception e) {
//                e.printStackTrace(new PrintWriter(this.errors));
//                this.respJSON.put("status", (Object)"FAIL");
//                this.respJSON.put("response", (Object)"");
//                this.respJSON.put("error", (Object)this.errors.toString());
//            }
//            return this.respJSON;
//        }
	public JSONObject switchToWindow(String value) {
		try {
			WebDriver driverNew = null;
			// driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "n");
			if (browserName.equalsIgnoreCase("firefox")) {
				driverNew = new FirefoxDriver();
				driver.get(value);

				driverNew.switchTo().window(driver.getWindowHandle());
				driverNew.get(value);
			} else if (browserName.equalsIgnoreCase("chrome")) {

				driverNew = new ChromeDriver();
				driver.get(value);

				driverNew.switchTo().window(driver.getWindowHandle());
				driverNew.get(value);
			}
//    	            Thread.sleep(3000);
//    	            for(String winHandle:driver.getWindowHandles()) {
//    	            	driverNew.switchTo().window(winHandle);
//    	            }
			// driverNew.get(value);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "Switched to window");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in switchToWindow Action", e);
			throw new SeleniumActionException("error in switchToWindow Action");
		}
		return this.respJSON;

	}

	/*
	 * public JSONObject switchToParentBrowserWindow() { try {
	 * driver.switchTo().window(getWinHandleBefore()); this.respJSON.put("status",
	 * (Object)"PASS"); this.respJSON.put("response",
	 * (Object)("Switch To Parent Browser :" +driver.getCurrentUrl()));
	 * this.respJSON.put("error", (Object)""); }catch(Exception e) {
	 * e.printStackTrace(new PrintWriter(this.errors)); this.respJSON.put("status",
	 * (Object)"FAIL"); String screenShot=snapShot(screenshotFolderPath);
	 * this.respJSON.put("screenShot", screenShot); this.respJSON.put("response",
	 * (Object)""); this.respJSON.put("error", (Object)this.errors.toString()); }
	 * return this.respJSON; }
	 */

	public JSONObject switchToParentBrowserWindow() {
		try {
			driver.switchTo().window(parent);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Switch To Parent Browser :" + driver.getCurrentUrl()));
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in switchToParentBrowserWindow Action", e);
			throw new SeleniumActionException("error in switchToParentBrowserWindow Action");
		}
		return this.respJSON;
	}

	public JSONObject generateRandomNumberAndEnterToTextField(String locatorType, String value, String format)   {
		try {

			// System.out.println(locatorType +" "+value);
			Random random = new Random();
			String arr[] = String.format(format + "%04d", random.nextInt(10000)).split("");

			By locator = this.locatorValue(xpathValue, value);
			// WebElement element = driver.findElement(locator);;
			WebElement element = explicit(driver, locator);
			element.clear();
			// element.sendKeys(new CharSequence[]{text});
			element.sendKeys(arr);
			// Thread.sleep(1000);
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("Random Number generated"));
			this.respJSON.put("error", (Object) "");

		} catch (Exception e) {
          
			this.respJSON.put("status", (Object) "FAIL");
			//---String screenShot = snapShot(screenshotFolderPath);
		    screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			//storeScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot); 
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			
			log.error("error in generateRandomNumberAndEnterToTextField Action", e);
			//----throw new SeleniumActionException("error in generateRandomNumberAndEnterToTextField Action");
			
			
			
			
		  throw new SeleniumActionException("error in generateRandomNumberAndEnterToTextField Action",respJSON);
		}
		return this.respJSON;
	}

	public WebElement explicit(WebDriver driver, By locator) {
		try {
			System.out.println("EXXXXXXXXXXPLICIT wWAAAAAAAAAAAAAITTTTTTTT CAAAAAAAAALLLLLLLEEEEED");
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.toString();
			WebElement webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return webElement;
		} catch (Exception e) {
			this.respJSON.put("status", (Object) "FAIL");
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in explicit Action", e);
			throw new SeleniumActionException("error in explicit Action");
		}
	}

	public String snapShot(String path) {
		String snaps = null;
		waitForPageToLoad(100);
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm");
		String snapName = "Snapshot";
		Date date = new Date();
		String name = String.format("%s.%s", snapName, date);
		snaps = name.replace(" ", "").replace(":", "_") + ".jpg";
		File fsnap = new File(path + "/" + snaps);
		String value = fsnap.getAbsolutePath();
		try {
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, fsnap);
			// logger.info("snapShot is captured for failed testcase");
		} catch (org.openqa.selenium.remote.UnreachableBrowserException e) {
			driver.getCurrentUrl();
			try {
				File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
				e.printStackTrace();
				// logger.log(Level.INFO, "snap src [" + scrFile + "]");
				FileUtils.copyFile(scrFile, fsnap);// TODO delete srcFile ?
				log.error("error in snapShot Action", e);
				throw new SeleniumActionException("error in snapShot Action");
			} catch (Exception e1) {
				// logger.error("snapShot is not captured for failed testcase :"
				// + e1, e1);
				e1.getMessage();
				log.error("error in snapShot Action", e1);
				throw new SeleniumActionException("error in snapShot Action");
			}
			// logger.error("snapShot is captured for failed testcase");
		} catch (Exception ex) {
			// logger.error("snapShot is not captured for failed testcase" + ex,
			// ex);
			ex.getMessage();
			log.error("error in snapShot Action", ex);
			throw new SeleniumActionException("error in snapShot Action");
		}
		// logger.log(Level.INFO, "snap src [" + snaps + "]" + " file [" + fsnap
		// + "] exists " + fsnap.exists());
		return value;
	}

	public JSONObject clearTextBoxValueAndAcceptAlert(String locatorType, String value) {
		try {

			By locator = this.locatorValue(xpathValue, value);
			explicit(driver, locator);
			WebElement element = driver.findElement(locator);
			element.clear();
			Thread.sleep(300);
			Alert alert = driver.switchTo().alert();
			alert.accept();
			// alert.accept();
			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) "TextBox cleared");
			this.respJSON.put("error", (Object) "");
		} catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in clearTextBoxValueAndAcceptAlert Action", e);
			throw new SeleniumActionException("error in clearTextBoxValueAndAcceptAlert Action");
		}
		return this.respJSON;
	}

	public JSONObject textBox1(String locatorType, String value, String text) {
		try {
			System.out.println("TextBox called value .." + value + "text" + text);
			By locator = this.locatorValue("xpath", value);
			// WebElement element = driver.findElement(locator);
			WebElement element = explicit(driver, locator);
			// String s=element.getText();
			String s = element.getAttribute("value");
			System.out.println("WWWWWWWWWWWWWWWW" + s);// .00
			// Thread.sleep(3000);

			if (s.contains(".00")) {

				for (int i = 0; i < s.length(); i++) {
					element.sendKeys(Keys.BACK_SPACE);
				}

				element.sendKeys(text);

			} else {
				element.sendKeys(text);
			}
//    				
			// element.getText().replaceAll("\\.00", text);

			// element.sendKeys(new CharSequence[]{text});
			// element.sendKeys(text);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) (text + " Has entered"));
			this.respJSON.put("error", (Object) "");
		}
		// catch (NoSuchElementException e)
		catch (Exception e) {

			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in textBox1 Action", e);
			throw new SeleniumActionException("error in textBox1 Action");
		}
		return this.respJSON;
	}

	public JSONObject runAppleScriptForFileUpload(String value) {
		try {
			// WebElement element=driver.findElement(locator);
			// element.click();

			System.out.println("runAppleScriptForFileUpload:::::::" + value);
			Runtime runtime = Runtime.getRuntime();
			String applescriptCommand = null;

			if (browserName.equalsIgnoreCase("firefox")) {
				applescriptCommand = "activate application \"Firefox\"\n"
						+ "tell application \"System Events\" to tell process \"Firefox\"\n"
						+ "keystroke \"G\" using {command down,shift down}\n" +

						"delay 2\n" + "keystroke \"" + value + "\"\n" + "delay 1\n" + "keystroke return\n" + "delay 1\n"
						+ "keystroke return\n" + "end tell";

			} else if (browserName.equalsIgnoreCase("Safari")) {

				applescriptCommand = "activate application \"Safari\"\n"
						+ "tell application \"System Events\" to tell process \"Safari\"\n"
						+ "keystroke \"G\" using {command down,shift down}\n" +

						"delay 2\n" + "keystroke \"" + value + "\"\n" + "delay 1\n" + "keystroke return\n" + "delay 1\n"
						+ "keystroke return\n" + "end tell";
			} else if (browserName.equalsIgnoreCase("chrome")) {
				System.out.println("chrome if part:123::::::::");

				applescriptCommand = "activate application \"Google Chrome\"\n"
						+ "tell application \"System Events\" to tell process \"Chrome\"\n"
						+ "keystroke \"G\" using {command down,shift down}\n" +

						"delay 2\n" + "keystroke \"" + value + "\"\n" + "delay 1\n" + "keystroke return\n" + "delay 1\n"
						+ "keystroke return\n" + "end tell";
			} else {

				System.out.println("chrome else part:::::::::");

				applescriptCommand = "activate application \"Google Chrome\"\n"
						+ "tell application \"System Events\" to tell process \"Chrome\"\n"
						+ "keystroke \"G\" using {command down,shift down}\n" +

						"delay 2\n" + "keystroke \"" + value + "\"\n" + "delay 1\n" + "keystroke return\n" + "delay 1\n"
						+ "keystroke return\n" + "end tell";

			}
			System.out.println("applescriptCommand:::" + applescriptCommand);

			String[] args = { "osascript", "-e", applescriptCommand };

			// Process process=runtime.exec("osascript "+applescriptCommand);

			Process process = runtime.exec(args);
			System.out.println("applescriptCommand123:::" + applescriptCommand);

			this.respJSON.put("status", (Object) "PASS");
			this.respJSON.put("response", (Object) ("runAppleScriptForFileUpload Success"));
			this.respJSON.put("error", (Object) "");
		}
		// catch (NoSuchElementException e)
		catch (Exception e) {
			this.respJSON.put("status", (Object) "FAIL");
			String screenShot = snapShot(screenshotFolderPath);
			StoreScreenShot.setScreenshot(screenShot);
			this.respJSON.put("screenShot", screenShot);
			this.respJSON.put("response", (Object) "");
			this.respJSON.put("error", (Object) this.errors.toString());
			log.error("error in runAppleScriptForFileUpload Action", e);
			throw new SeleniumActionException("error in runAppleScriptForFileUpload Action");
		}
		return this.respJSON;
	}

}
