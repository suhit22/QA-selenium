package com.alg.test.config;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.alg.test.framework.BaseTest;
import com.alg.test.utility.ExcelUtils;

public abstract class BasePage {
	
	public WebDriver baseLaunchLocalBrowser(String sBrowser) throws Exception{
		WebDriver driver = null;
		
		switch (sBrowser){
		case "firefox":  // if 
			//driver = startFirefox(); 
			//break;
		case "chrome": // else if
			driver = startChrome();
			break;
		case "ie": // else if
			//driver = startIE();
			break;
		case "html": // else if 
			//driver = startHtml();
			break;	
		default: // else 
			//driver = startHtml();
			break;
		}		
		
		return driver;
	}
	
	public WebDriver baseLaunchRemoteBrowser(String sBrowser, ExcelUtils xUtils, int iTestStepRowNum) throws Exception{
		String sSauceLabsUserName = BaseTest.pParamPropFile.getProperty("SauceLabsUserName");
		String sSauceLabsAccessKey = BaseTest.pParamPropFile.getProperty("SauceLabsAccessKey");
		String sSauceLabsIpAddress = BaseTest.pParamPropFile.getProperty("SauceLabsIpAddress");
		String sSauceLabsPort = BaseTest.pParamPropFile.getProperty("SauceLabsPort");
		String sSauceLabsTunnelIdentifier = BaseTest.pParamPropFile.getProperty("SauceLabsTunnelIdentifier");
		String sAppUrl = "http://" + sSauceLabsUserName + ":" + sSauceLabsAccessKey + "@" + sSauceLabsIpAddress + ":" + sSauceLabsPort + "/wd/hub";
		
		WebDriver driver;
		DesiredCapabilities dCapBrowser;
		
		dCapBrowser = DesiredCapabilities.chrome();
		dCapBrowser.setCapability("platform", "Windows 8.1");
		dCapBrowser.setCapability("browserName", sBrowser.toLowerCase());
		dCapBrowser.setCapability("tunnel-identifier", sSauceLabsTunnelIdentifier);
		dCapBrowser.setCapability("name", "TestRun");
		dCapBrowser.setCapability("timeZone", "New_York");
		dCapBrowser.setCapability("screenResolution", "1280x1024");
		
		driver = new RemoteWebDriver(new URL(sAppUrl), dCapBrowser);
		
		return driver;
	}
	
	public String baseEnterData(WebDriver wDriver, String sData, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		element.clear();
		element.sendKeys(sData);
		element.sendKeys(Keys.TAB);
		sResult = "Success";
		
		return sResult;
	}
	
	public String baseClickElement(WebDriver wDriver, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		element.click();
		sResult = "Success";
		
		return sResult;
	}

	public String baseDropDown(WebDriver wDriver, String sData, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		//element.click();
		Select dd = new Select(element);
		dd.selectByVisibleText(sData);
		sResult = "Success";
		
		return sResult;
	}

	public String baseListDropDown(WebDriver wDriver, String sData, By locator, int iWaitInSeconds) throws Exception{
		String sResult = "Error";
		List<WebElement> list = findAndWaitWebElements(wDriver, locator, iWaitInSeconds);
		
		for(int i=0; i<list.size(); i++){
			if (list.get(i).getText().equalsIgnoreCase(sData)){
				list.get(i).click();
				break;
			}
		}
		sResult = "Success";
		
		return sResult;
	}

	public String baseCheckAvailable(WebDriver wDriver, String sData, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		if(element != null){
			sResult = "Success";
		}else{
			sResult = "Not Available";
		}
		
		return sResult;
	}
	
	public String baseCheckNotAvailable(WebDriver wDriver, String sData, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		if(element == null){
			sResult = "Success";
		}else{
			sResult = "Available";
		}
		
		return sResult;
	}
	
	public String baseRedirectBrowser(WebDriver wDriver, String sUrl) throws Exception{
		String sResult = "Error";
		
		wDriver.get(sUrl);
		sResult = "Success";
		
		return sResult;
	}
	
	public String baseGetData(WebDriver wDriver, By locator, int iWaitInSeconds) throws Exception{
		WebElement element = null;
		String sResult = "Error";
		
		element = findAndWait(wDriver, locator, iWaitInSeconds);
		sResult = element.getText().trim();
				
		return sResult;
	}
	
	public WebElement findAndWait(WebDriver wDriver, By locator, int iWaitInSeconds){
		try{
			FluentWait<WebDriver> wait = new WebDriverWait(wDriver, iWaitInSeconds);
			wait.pollingEvery(500, TimeUnit.MILLISECONDS);
			WebElement webElement = wait.until(new ExpectedCondition<WebElement>(){
	  				@Override
	  				public WebElement apply(WebDriver d) {
	  					try{
	  						if (d.findElement(locator).isDisplayed()){
	  							return d.findElement(locator);
	  						}else{
	  							return null;
	  						}
	  					}catch(Exception e){
	  						WebElement webElement = null;
							return webElement;
	  					}
	  				}
	  				}
	  			  );
			return webElement;
		} catch(Exception e) {
			WebElement webElement = null;
			return webElement;
		}
	}
	
	public List<WebElement> findAndWaitWebElements(WebDriver wDriver, By locator, int iWaitInSeconds) {
		List<WebElement> webElement = null;
		try{
			FluentWait<WebDriver> wait = new WebDriverWait(wDriver, iWaitInSeconds);
			wait.pollingEvery(500, TimeUnit.MILLISECONDS);
			webElement = wait.until(new ExpectedCondition<List<WebElement>>(){
	  				@Override
	  				public List<WebElement> apply(WebDriver d) {
	  					List<WebElement> elementList = null;
	  					try{
	  						elementList = d.findElements(locator);
	  						if (elementList.size()>0){
	  							return elementList;
	  						}else{
	  							return null;
	  						}
	  					}catch(Exception e){
	  						elementList = null;
							return elementList;
	  					}
	  				}
	  				}
	  			  );
			return webElement;
		} catch(Exception e) {
			return null;
		}
	}
	
	public void waitInternalSeconds(int iWaitInMillSeconds) throws Exception{
		Thread.sleep(iWaitInMillSeconds);
	}
	
	public void closeDriver(WebDriver wDriver){
		if(wDriver != null){
			wDriver.manage().deleteAllCookies();
			wDriver.close();
			wDriver.quit();
			wDriver = null;
		}
	}
	
	public boolean waitAjaxLoad(WebDriver wDriver, int iWaitInSeconds) throws Exception{
		int waitInMillSeconds = 0;
		long t0, t1;
		boolean ajaxCallsFinish = false;
		
		//Loop through to check if ajax calls are done
		try{
			waitInMillSeconds = iWaitInSeconds * 1000;
			
			t0 = System.currentTimeMillis();
			do{
				t1=System.currentTimeMillis();
				
				//Check if all pending ajax calls are done
				if(!(boolean)((JavascriptExecutor) wDriver).executeScript("return Ext.Ajax.isLoading();")){
					ajaxCallsFinish = true;
					break;
				}
			}while(t1 - t0 < waitInMillSeconds);
			
			waitInternalSeconds(50);
		}catch(Exception e){
			ajaxCallsFinish = false;
		}
		
		return ajaxCallsFinish;
	}
	
	public By getLocator(int iTestStepRow, ExcelUtils xUtils, String sSheetName) throws Exception{
		By locator = null;
		
		if(xUtils.getCellData(iTestStepRow, Constants.iLOCATORTYPECOLUMN, sSheetName).equalsIgnoreCase("XPATH")){
			locator = By.xpath(BaseTest.pParamPropFile.getProperty(xUtils.getCellData(iTestStepRow, Constants.iLOCATORCOLUMN, sSheetName)));
		}else if (xUtils.getCellData(iTestStepRow, Constants.iLOCATORTYPECOLUMN, sSheetName).equalsIgnoreCase("ID")){
			locator = By.id(BaseTest.pParamPropFile.getProperty(xUtils.getCellData(iTestStepRow, Constants.iLOCATORCOLUMN, sSheetName)));
		}else if (xUtils.getCellData(iTestStepRow, Constants.iLOCATORTYPECOLUMN, sSheetName).equalsIgnoreCase("CSS")){
			locator = By.cssSelector(BaseTest.pParamPropFile.getProperty(xUtils.getCellData(iTestStepRow, Constants.iLOCATORCOLUMN, sSheetName)));
		}else if (xUtils.getCellData(iTestStepRow, Constants.iLOCATORTYPECOLUMN, sSheetName).equalsIgnoreCase("LINKTEXT")){
			locator = By.linkText(BaseTest.pParamPropFile.getProperty(xUtils.getCellData(iTestStepRow, Constants.iLOCATORCOLUMN, sSheetName)));
		}
		
		return locator;
	}
	
	public WebDriver startChrome() throws Exception{
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--start-maximized");
        options.addArguments("-disable-cache");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
		capabilities.setCapability("chrome.switches", Arrays.asList("--incognito"));
		capabilities.setBrowserName("chrome");
		capabilities.setPlatform(Platform.WIN10);
		
		System.setProperty("webdriver.chrome.driver",
				"chromedriver.exe");

		return new ChromeDriver(capabilities);
	}
}
