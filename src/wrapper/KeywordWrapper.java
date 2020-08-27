package wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class KeywordWrapper {
	static String a = "";
	static String b = "";
	static String getUrl= "";
	RemoteWebDriver driver;
	Properties prop;
	
	static Logger logger = Logger.getLogger("KeywordWrapper");
	
	
	public KeywordWrapper() throws FileNotFoundException, IOException  {

		PropertyConfigurator.configure(".\\Properties\\log4j.properties");
		prop = loadObjectRepository(".\\Properties\\Bulk.properties");	
		
	}

//Launch Browser
	public void launchBrowser(String url) throws IOException {
			String browser = prop.getProperty("Browser.Name");
			String path = "D:\\eclipse-committers-oxygen-M2-win32\\eclipse\\Blazedemo\\driver"; 
				//	String url = prop.getProperty("LoginPage.Url");
			try {

				if (browser.equalsIgnoreCase("firefox")) {
					System.setProperty("webdriver.gecko.driver", path + "\\geckodriver.exe");
					driver = new FirefoxDriver();
				} else if (browser.equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", path + "\\chromedriver.exe");
					driver = new ChromeDriver();
				} else if (browser.equalsIgnoreCase("ie")) {
					System.setProperty("webdriver.ie.driver", path + "\\IEDriverServer.exe");
					driver = new InternetExplorerDriver();
				} else if (browser.equals("Phantomjs")) {
					 Capabilities caps = new DesiredCapabilities();
					((DesiredCapabilities) caps).setJavascriptEnabled(true);
					((DesiredCapabilities) caps).setCapability("takesScreenshot", false);
					((DesiredCapabilities) caps).setCapability("locationContextEnabled", true);
					((DesiredCapabilities) caps).setCapability("acceptSslCerts", true);
					File file = new File(path + "\\phantomjs.exe");
					System.setProperty("phantomjs.binary.path", file.getAbsolutePath());
					driver = new PhantomJSDriver(caps);
							}

				driver.manage().window().maximize();
				driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
				driver.get(url);

				ATUReports.setWebDriver(driver);
				} catch (Exception e) {
				ATUReports.add("Browser opening failed", browser, LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		}	


	public void clickByXpath(String Xpath) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 40);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
			driver.findElement(By.xpath(Xpath)).click();
		} catch (NoSuchElementException e) {
			System.out.println("request submitted");
			} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	
	public void selectVisibleTextByXpath(String Xpath, String data) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
			Select i = new Select(driver.findElement(By.xpath(Xpath)));
			i.selectByVisibleText(data);
			} catch (NoSuchElementException e) {
			
		}
		catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}
	//Enter Text by Xpath
	public void enterTextByXpath(String Xpath, String data) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, 50);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
			driver.findElement(By.xpath(Xpath)).clear();
			driver.findElement(By.xpath(Xpath)).sendKeys(data);
			String value = driver.findElement(By.xpath(Xpath)).getAttribute("value");
			
		} catch (NoSuchElementException e) {
		
		} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}
	public Properties loadObjectRepository(String FileName) throws FileNotFoundException, IOException {
	
	Properties p = new Properties();
	
	p.load(new FileInputStream(new File(FileName)));
	return p;
}

public void propertyfile(String file) {
	try {
		prop = loadObjectRepository(file);
	} catch (FileNotFoundException e) {
	} catch (IOException e) {
	}
}

		public void respval(String Xpath) {
			try {
				
				WebDriverWait wait = new WebDriverWait(driver, 40);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Xpath)));
				String resp = driver.findElement(By.xpath(Xpath)).getText();
				if (resp.contains("Thank you for your purchase today!"))
				{
					System.out.println("pass");
					ATUReports.add("Testcase Passed",LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

					} 
					else
					{
						System.out.println("fail");
						ATUReports.add("TestCase Failed",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

					}
						
			} catch (NoSuchElementException e) {
				ATUReports.add("Button not clicked",LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			} catch (WebDriverException e1) {
				ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
			}
		}
}