package com.everlight.computerdatabase;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CreateUpdateComputerTest extends BaseTest {

	
	@BeforeTest
	private void openBrowser() {
		try {
			initialize();
		} catch(IOException e) {
			log.error("Could not initialize web driver: \n" + e.fillInStackTrace());
		}		
	}


	//@Test
	public void createComputerTest() {
		//Add a new computer
		DRIVER.findElement(By.id("add")).click();
		DRIVER.findElement(By.name("name")).sendKeys(String.valueOf(DATA.getProperty("ComputerName1")));
		DRIVER.findElement(By.name("introduced")).sendKeys(String.valueOf(DATA.getProperty("RequiredIntroducedDate1")));
		DRIVER.findElement(By.name("discontinued")).sendKeys(String.valueOf(DATA.getProperty("DiscontinuedDate1")));
		Select dropdown = new Select(DRIVER.findElement(By.name("company")));
		dropdown.selectByValue("1");
		DRIVER.findElement(By.cssSelector("input.btn.primary")).click();
	
	}

	/**
	 * Follows these steps:
	 *  1. create new computer
	 *  2. Read newly created computer
	 *  3. Update name of this computer
	 *  4. Verify success alert message is displayed 
	 */
	@Test
	public void updateComputerTest() {
		createComputerTest();
		ReadAndSearchTest rt = new ReadAndSearchTest();
		rt.readSpecificComputer(null);
		WebElement nameTextBox = DRIVER.findElement(By.id("name"));
		String newName = DATA.getProperty("ComputerName2")+"_UPDATED";
		nameTextBox.clear();
		nameTextBox.sendKeys(String.valueOf(newName));
		DRIVER.findElement(By.cssSelector("input.btn.primary")).click();
		//rt.readSpecificComputer(newName);
		//nameTextBox = DRIVER.findElement(By.id("name"));
		//Assert.assertEquals(nameTextBox.getText(), newName);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			log.error("sleep interrupted");
		}
		WebElement messageElement = DRIVER.findElement(By.xpath(".//*[@id='main']/div[1]"));
		String message = messageElement.getText();
		Assert.assertEquals(message,"Done! Computer "+ newName+" has been updated");
	}
	
	@AfterTest
	public void tearDown() {
		super.tearDown();
	}
}
