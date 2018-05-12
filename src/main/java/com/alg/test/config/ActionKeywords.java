package com.alg.test.config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.alg.test.framework.BaseTest;
import com.alg.test.utility.ExcelUtils;
import com.alg.test.utility.Log;

public class ActionKeywords extends BasePage{
	
	public WebDriver launchBrowser(ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sBrowser = "";
		WebDriver wDriver = null;
		
		sBrowser = xUtils.getCellData(iTestCaseRowNum, Constants.iBROWSERCOLUMN, Constants.sTESTEXECUTORSHEET);
		
		try{
			if(BaseTest.pParamPropFile.getProperty("RemoteOrLocal").contentEquals("Remote")){
				Log.info("Launching browser in sauce labs");
				wDriver = baseLaunchRemoteBrowser(sBrowser, xUtils, iTestStepRowNum);
			}else{
				Log.info("Launching browser in local");
				wDriver = baseLaunchLocalBrowser(sBrowser);                         
			}
		}catch(Exception e){
			Log.error("Error launching browser | Exception Description - " + e.getMessage());
			wDriver = null;
		}
		
		return wDriver;
	}
	
	public String redirectBrowser(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sRedirectUrlResult = "Success";
		String sUrl = "";
		
		try{
			sUrl = xUtils.getCellData(iTestStepRowNum, Constants.iUIINPUTSTARTCOLUMN, sSheetName);
			Log.info("Redirecting to url - " + sUrl);
			baseRedirectBrowser(wDriver, sUrl);
		}catch(Exception e){
			sRedirectUrlResult = "Error";
			Log.error("Error redirecting browser | Exception Description - " + e.getMessage());
		}
		
		return sRedirectUrlResult;
	}
	
	public String closeBrowser(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		
		try{
			Log.info("Closing the browser");
			closeDriver(wDriver);
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error closing the browser");
		}
		
		return sResult;
	}
	
	public String enterData(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		String sData = "";
		
		try{
			sData = xUtils.getCellData(iTestStepRowNum, Constants.iUIINPUTSTARTCOLUMN, sSheetName);
			if(sData.length()>0){
				Log.info("Entering data - " + sData);
				baseEnterData(wDriver, sData, getLocator(iTestStepRowNum, xUtils, sSheetName), 5);
			}else{
				Log.info("Skipped entering data");
			}
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error entering data | Exception Description - " + e.getMessage());
		}
		
		return sResult;
	}
	
	public String clickElement(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		
		try{
			Log.info("Clicking on a webelement");
			baseClickElement(wDriver, getLocator(iTestStepRowNum, xUtils, sSheetName), 5);
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error while clicking on a webelement | Exception Description - " + e.getMessage());
		}
		
		return sResult;
	}
	
	public String dropDown(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		String sData = "";
		
		try{
			sData = xUtils.getCellData(iTestStepRowNum, Constants.iUIINPUTSTARTCOLUMN, sSheetName);
			if(sData.length()>0){
				Log.info("Clicking dropdown - " + sData);
				baseDropDown(wDriver, sData, getLocator(iTestStepRowNum, xUtils, sSheetName), 5);
			}else{
				Log.info("Skipped dropdown" +sData);
			}
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error clicking dropdown | Exception Description - " + e.getMessage());
		}
		
		return sResult;
	}
	
	public String listDropDown(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		String sData = "";
		
		try{
			sData = xUtils.getCellData(iTestStepRowNum, Constants.iUIINPUTSTARTCOLUMN, sSheetName);
			if(sData.length()>0){
				Log.info("Clicking listdropdown - " + sData);
				baseListDropDown(wDriver, sData, getLocator(iTestStepRowNum, xUtils, sSheetName), 5);
			}else{
				Log.info("Skipped listdropdown" +sData);
			}
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error clicking listdropdown | Exception Description - " + e.getMessage());
		}
		
		return sResult;
	}
	public String waitForSeconds(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		int iWaitSeconds = 0;
		
		try{
			iWaitSeconds = Integer.parseInt(xUtils.getCellData(iTestStepRowNum, Constants.iUIINPUTSTARTCOLUMN, sSheetName));
			Log.info("Force wait for " + iWaitSeconds + " seconds");
			waitInternalSeconds(iWaitSeconds*1000);
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error while force wait");
		}
		
		return sResult;
	}
	
	public String getData(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Init";
		
		try{
			Log.info("Reading data from a webelement");
			sResult = baseGetData(wDriver, getLocator(iTestStepRowNum, xUtils, sSheetName), 5);
			xUtils.setCellData(sResult, iTestStepRowNum, 8, sSheetName);
		}catch(Exception e){
			sResult = "Error";
			Log.error("Error while reading data from the webelement");
		}
		
		return sResult;
	}
	
	public String login(WebDriver wDriver, ExcelUtils xUtils, String sSheetName, int iTestCaseRowNum, int iTestStepRowNum){
		String sResult = "Success";
		
		try{
			//Click on My Account button
			Log.info("Clickin on My Account button");
			baseClickElement(wDriver, By.xpath(BaseTest.pParamPropFile.getProperty("btn_MyAccount")), 5);
			
			//Enter User Id
			
			//Enter Password
			
			try{
			//Enter Security OTP
				
			}catch(Exception e){
				
			}
			
			//Click on login button
			
		}catch(Exception e){
			Log.error("Error while login | " + e.getMessage());
			sResult = "Error";
		}
		
		return sResult;
	}
}
