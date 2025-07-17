package com.selenium.AssignmentL2_2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

/* Assignment 2: Create a maven based project. Write Webdriver script to perform the following actions and perform the 
 required validations using TestNG. Use Edge browser*/
public class AssignmentTwo {
	// POM for finding the elements by name, id, xpath
	By jsAlert = By.xpath("//button[text()='Click for JS Alert']");
	By result = By.id("result");
	By jsConfirm = By.xpath("//button[text()='Click for JS Confirm']");
	By jsPrompt = By.xpath("//button[text()='Click for JS Prompt']");

	WebDriver driver;
	String url = "https://the-internet.herokuapp.com/javascript_alerts";
	String jsAlertResult = "You successfully clicked an alert";
	String jsConfirmResult = "You clicked: Ok";
	String jsConfirmCancel = "You clicked: Cancel";

	@BeforeTest
	public void launchURL() {
		// Step1: Launch URL with Edge Browser
/*		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();*/
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get(url);
	}

	@Test
	public void shouldAnswerWithTrue() {
		// Step2: Click the JS Alert. Switch to alert and click on OK
		driver.manage().window().maximize();
		getClick(jsAlert);
		driver.switchTo().alert().accept();
		// Step3: Verify the result contains: You successfully clicked an alert
		getText(result).equals(jsAlertResult);
		Assert.assertEquals(jsAlertResult, getText(result));
		// Step 4: Click the JS Confirm. Switch to alert and click on OK
		getClick(jsConfirm);
		driver.switchTo().alert().accept();
		// Step 5: Verify the result contains: You clicked: OK
		getText(result).equals(jsConfirmResult);
		Assert.assertEquals(jsConfirmResult, getText(result));
		// Step 6: Click the JS Confirm. Switch to alert and click on Cancel
		getClick(jsConfirm);
		driver.switchTo().alert().dismiss();
		// Step 7: Verify the result contains: You clicked: Cancel
		getText(result).equals(jsConfirmCancel);
		Assert.assertEquals(jsConfirmCancel, getText(result));
		// Step 8: Repeat the same steps for JS Promt
		getClick(jsPrompt);
		// Step 9: Also verify the alert text for all 3
		(driver.switchTo().alert().getText().trim()).equals("I am a JS prompt");
		driver.switchTo().alert().sendKeys("Hello");
		driver.switchTo().alert().accept();
		getText(result).equals("You entered: Hello");
		Assert.assertEquals("You entered: Hello", getText(result));
		getClick(jsPrompt);
		driver.switchTo().alert().sendKeys("Bye");
		driver.switchTo().alert().dismiss();
		getText(result).equals("You entered: null");
		Assert.assertEquals("You entered: null", getText(result));
	}

	@AfterTest
	public void tearDownURL() {
		driver.quit();
	}

	// Function to click element
	public void getClick(By element) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getWait(element);
		if (driver.findElement(element).isEnabled()) {
			driver.findElement(element).click();
		}
	}

	// Explicit wait
	public void getWait(By element) {
		WebDriverWait driverWait = new WebDriverWait(driver, 20);
		driverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(element)));
	}

	// Function to Return Text
	public String getText(By element) {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		getWait(element);
		String text = driver.findElement(element).getText().trim();
		return text;
	}
}
