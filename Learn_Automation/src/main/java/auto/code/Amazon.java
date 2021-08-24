package auto.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

/*
 * Amazon Shopping website automation
 * 
 * Pre-requisite:
 * 1) Create Selenium Instance and launch browser
 * 
 * Testcase 1: LogIn to Amazon 
 * 1) Open amazon
 * 2) LogIn with existing username and password
 * 3) Verify the home page and take screenshot evidence
 * 
 * Testcase 2: LogOut 
 * 4) Mouse Hover on Accounts
 * 5) Click SignOut
 * 6) Verify the logout and take screenshot evidence
 * 
 */

public class Amazon {

	public static WebDriver driver;
	public Properties prop;
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		Amazon am = new Amazon();
		am.logInToAmazon();
		am.logOut();
		driver.close();
	}
	
	public void logInToAmazon() {
		try {
		String URL = "https://www.amazon.in";
		String mobileNo = "9094843125";
		String password = "Test@1234";
		String ExpectedTitle = "Amazon.in";
		driver.get(prop.getProperty(URL));
		maxiBrowser();
		addWait();
		driver.findElement(By.id("nav-signin-tooltip")).click();
		driver.findElement(By.id("ap_email")).sendKeys(prop.getProperty(mobileNo));
		driver.findElement(By.id("continue")).click();
		driver.findElement(By.id("ap_password")).sendKeys(prop.getProperty(password));
		driver.findElement(By.id("signInSubmit")).submit();
		Thread.sleep(5000);
		String actualTitle = driver.getTitle();
		if(actualTitle.contains(ExpectedTitle)) {
			takeSnap();
		}
		}
		catch(Exception e) {
			System.out.println("Error "+e);
		}
	}
	
	public void logOut() {
		try {
		String expectedTitle = "Sign In";
		Actions s = new Actions(driver);
		WebElement accountsTab = driver.findElement(By.id("nav-link-accountList"));
		s.moveToElement(accountsTab).perform();
		WebElement signOut = driver.findElement(By.id("nav-item-signout"));
		signOut.click();
		String actualTitle = driver.getTitle();
		if(actualTitle.contains(expectedTitle)) {
			takeSnap();
		}
		} catch(Exception e) {
			System.out.println("Error: "+e);
		}
		
		
	}
	public void takeSnap() {
		long number = (long) Math.floor(Math.random() * 900000000L) + 10000000L; 
		String path = System.getProperty("user.dir")+"/way2autoSnaps/"+number+".png";
		TakesScreenshot snap = (TakesScreenshot)driver;
		File sc = snap.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(sc, new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void maxiBrowser() {
		driver.manage().window().maximize();
	}
	
	public void addWait() {
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	public void loadProperties() {
		String propertiesProp = "./way2auto-config/AmazonPropertiesFile.properties";
		prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(propertiesProp)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
