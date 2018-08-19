package com.everlight.computerdatabase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class DeleteComputerTest extends BaseTest {

	@BeforeTest
	private void openBrowser() {
		try {
			initialize();
		} catch(IOException e) {
			log.error("Could not initialize web driver: \n" + e.fillInStackTrace());
		}		
	}
	
	/**
	 * Read specific computer by configured name
	 * click on the delete button
	 * Verify alert message says that its deleted
	 */
	@Test
	public void deleteComputerTest() {
		ReadAndSearchTest rt = new ReadAndSearchTest();
		rt.readSpecificComputer(DATA.getProperty("ComputerName1"));
		
		DRIVER.findElement(By.cssSelector("input.btn.danger")).click();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error("sleep interrupted");
		}
		WebElement messageElement = DRIVER.findElement(By.xpath(".//*[@id='main']/div[1]"));
		Assert.assertEquals(messageElement.getText(),"Done! Computer has been deleted");
	}
	
	@AfterTest
	public void tearDown() {
		super.tearDown();
	}
}
