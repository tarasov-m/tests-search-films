package ru.st.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.testng.*;
import org.testng.annotations.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class TestsForSearchFilms extends ru.st.selenium.pages.TestBase {
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @BeforeMethod
  public void LogIn() {
	driver.get(baseUrl + "/php4dvd/php4dvd/");
    driver.findElement(By.id("username")).clear();
	driver.findElement(By.id("username")).sendKeys("admin");
	driver.findElement(By.name("password")).clear();
	driver.findElement(By.name("password")).sendKeys("admin");
	driver.findElement(By.name("submit")).click();
	driver.findElement(By.linkText("Home")).click();
	driver.findElement(By.id("q")).clear();
	driver.findElement(By.id("q")).sendKeys(Keys.ENTER);

  }
  
  
  @Test
  public void testNoSearchResults() throws Exception {
	boolean isExpect = true;
	int quantityFinded =0;
	int quantity = driver.findElements(By.cssSelector("div.title")).size();
	  for (int i=quantity-1; i>=0;i--){
		if (driver.findElements(By.cssSelector("div.title")).get(i).getText().toLowerCase().startsWith("avatar") || driver.findElements(By.cssSelector("div.title")).get(i).getText().toLowerCase().endsWith("avatar")){
		  driver.findElements(By.cssSelector("div.title")).get(i).click();
		  driver.findElement(By.cssSelector("img[alt=\"Remove\"]")).click();
		  assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to remove this[\\s\\S]$"));
		}
		
    };
    driver.findElement(By.id("q")).clear();
    driver.findElement(By.id("q")).sendKeys("Avatar");
    driver.findElement(By.id("q")).sendKeys(Keys.ENTER);
    Thread.sleep(1000);
    assertEquals(driver.findElement(By.id("results")).findElement(By.cssSelector("div.content")).getText().matches("No movies where found."),true);
  }
  
  @Test
  public void testSearchResults() throws Exception {
	  driver.findElement(By.cssSelector("img[alt=\"Add movie\"]")).click();
      driver.findElement(By.name("name")).click();
	  driver.findElement(By.name("name")).clear();
	  driver.findElement(By.name("name")).sendKeys("Avatar");
	  driver.findElement(By.name("year")).click();
	  driver.findElement(By.name("year")).clear();
	  driver.findElement(By.name("year")).sendKeys("2000");
	  driver.findElement(By.id("submit")).click();
	  driver.findElement(By.linkText("Home")).click();
	  String nameFilm = driver.findElement(By.cssSelector("div.title")).getText();
	  driver.findElement(By.id("q")).clear();
	  driver.findElement(By.id("q")).sendKeys(nameFilm);
	  driver.findElement(By.id("q")).sendKeys(Keys.ENTER);
	  Thread.sleep(3000);
	  assertEquals(driver.findElements(By.cssSelector("div.title")).size()!=0,true);
  }
  
  
  @AfterMethod
  public void LogOut() {
	driver.findElement(By.linkText("Log out")).click();
	assertTrue(closeAlertAndGetItsText().matches("^Are you sure you want to log out[\\s\\S]$"));
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
