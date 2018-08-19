package com.everlight.computerdatabase;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ReadAndSearchTest extends BaseTest {
	private WebElement record = null;
	
	@BeforeTest
	private void openBrowser() {
		try {
			initialize();
		} catch(IOException e) {
			log.error("Could not initialize web driver: \n" + e.fillInStackTrace());
		}
	}

	// @Test
	public void readTest() {
		if(record == null) {
			// if specific record is not being viewed, read the first one available
			List<WebElement> rows = DRIVER.findElements(By.cssSelector("tbody > tr"));
			record = rows.get(1).findElement(By.tagName("a"));
		}
		try {
			record.click();
			Thread.sleep(5000);
			log.info("Waiting for read");
			WebElement label= DRIVER.findElement(By.xpath(".//*[@id='main']/h1"));
			Assert.assertEquals(label.getText(),DATA.getProperty("EditComputerHeading"));
		} catch (InterruptedException e) {
			log.error("sleep interrupted");
		}
	}
	
	//@Test
	public void searchTest(String name) {
		// if name is not given look for the our configured computer name
		String searchQuery = (name == null)?BaseTest.DATA.getProperty("ComputerName1"): name;
		BaseTest.DRIVER.findElement(By.id("searchbox")).sendKeys(String.valueOf(searchQuery));
		BaseTest.DRIVER.findElement(By.id("searchsubmit")).click();
		log.info("Search attempted");
		//BaseTest.DRIVER.findElements(By.)
		boolean isFound = false;
		// duplication is allowed. Get list of all returned computers
		 List<WebElement> rows = DRIVER.findElements(By.cssSelector("tbody > tr"));
		    for(WebElement row : rows){
		    	// match what we are looking for
		        if(row.findElement(By.tagName("a")).getText().equals(searchQuery)){
		        	log.info("getText() = "+  row.findElement(By.tagName("a")).getText() );
		             isFound = true;
		             // save the record for specific reading
		             record = row.findElement(By.tagName("a"));
		            break;
		        }
		    }
		    
		  // search test case fulfilled
		  Assert.assertEquals(isFound, true);
		  
	}	
	
	/**
	 * Find the specific computer
	 * Read details of that computer
	 * @param name
	 */
	@Test
	public void readSpecificComputer(String name) {
		searchTest(name);
		readTest();
	}
	
	@AfterTest
	public void tearDown() {
		super.tearDown();
	}
}
