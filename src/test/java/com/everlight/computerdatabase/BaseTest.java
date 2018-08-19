package com.everlight.computerdatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class BaseTest {
	
	// properties common to all test cases
	public static Properties CONFIG;
	public static Properties DATA;
	public static WebDriver DRIVER;
	public static Logger log;
	
	public BaseTest() {
		// logging configuration in src/test/resources/log4j.xml
		log = Logger.getLogger(this.getClass().getName());
	}
	
	
	// Initializing static config and drivers
	static {
		initConfigAndData();
		loadDriver();
	}
	
	private static void initConfigAndData() {
		if(BaseTest.CONFIG==null) {
			CONFIG = new Properties();
			String cfgFile = "src/test/resources/config.properties";
			loadProperties(CONFIG, cfgFile);
		}
		if(BaseTest.DATA == null) {
			DATA = new Properties();
			String dataFile = "src/test/resources/data.properties";
			loadProperties(DATA, dataFile);
		}
	}
	
	/**
	 * Uility method to load properties from given file
	 * @param props
	 * @param fileName
	 */
	private static void loadProperties(Properties props, String fileName) {
		try {
			props.load(new FileInputStream(fileName));
			
		} catch (FileNotFoundException e) {
			log.error("Properties NOT FOUND - "+fileName);
			log.error(e.fillInStackTrace());
		} catch (IOException e) {
			log.error("Properties found, but not accessible - "+fileName);
			log.error(e.fillInStackTrace());
		}
		
	}


	/**
	 * Check for configured browser name and instantiate relevant driver provided by Selenium
	 */
	private static void loadDriver() {
		String driversDir = "src/test/resources/drivers";
		if(CONFIG.getProperty("browser").equalsIgnoreCase("Firefox") ||CONFIG.getProperty("browser").equalsIgnoreCase("FF") ){
			  System.setProperty("webdriver.gecko.driver", driversDir + "\\geckodriver.exe");
			  DRIVER = new FirefoxDriver();
		}
		else if(CONFIG.getProperty("browser").equals("EDGE")) {
			System.setProperty("webdriver.edge.driver", driversDir + "\\MicrosoftWebDriver.exe");
			 DRIVER = new ChromeDriver();
		}else if(CONFIG.getProperty("browser").equals("GoogleChrome")||CONFIG.getProperty("browser").equalsIgnoreCase("CHROME")) {
			 System.setProperty("webdriver.chrome.driver", driversDir + "\\chromedriver.exe");
			 DRIVER = new ChromeDriver();
		}
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void initialize() throws IOException {
		 log.info("WebDriver has been initialized successfully");
		 if(CONFIG != null && DRIVER != null) {
			 DRIVER.get(CONFIG.getProperty("ComputerDBUrl"));
			 DRIVER.manage().window().maximize();
			 DRIVER.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		 } else {
			 initConfigAndData();
			 loadDriver();
		 }
	}
	
	/**
	 * Closing browser after every test case
	 */
	public void tearDown() {
		log.info("Tearing down the session");
		DRIVER.close();
		log.info("Driver has been closed");
		DRIVER=null;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error("Sleep interrupted");
		}	
	}
}
