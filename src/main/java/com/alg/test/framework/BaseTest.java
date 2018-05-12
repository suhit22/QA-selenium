package com.alg.test.framework;

import java.sql.Connection;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.alg.test.utility.Log;
import com.alg.test.utility.Utils;

public abstract class BaseTest {

	public static String sMasterDirPath;
	public static Properties pParamPropFile = null;
	public static Connection connectionDb = null;
	
	@BeforeSuite
	public static void setUp(){
		//Initialize log configurator
		//DOMConfigurator.configure(System.getenv("ENV_LOG4J"));
		DOMConfigurator.configure("log4j.xml");
		
		//Log start of suite
		Log.startTestSuite();
		
		//Load properties file
		Log.info("Loading properties file");
		try {
			pParamPropFile = Utils.openProperties("Parameters.Properties");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Connect to DB
		
		//Create regression result folder
	}
	
	@AfterSuite
	public static void tearDown(){
		//Close DB connection
	}	
}
