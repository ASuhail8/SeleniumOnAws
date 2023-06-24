package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public WebDriver driver;

	public WebDriver WebDriverManager() throws IOException {
		Properties prop = new Properties();
		InputStream inputstream = getClass().getClassLoader().getResourceAsStream("global.properties");
		prop.load(inputstream);
		String url = prop.getProperty("QAUrl");
		String browser_properties = prop.getProperty("browser");
		String browser_maven = System.getProperty("browser");
		// result = testCondition ? value1 : value2
		String host = "localhost";
		String browser = browser_maven != null ? browser_maven : browser_properties;

		if(System.getProperty("HUB_HOST") != null){
			host = System.getProperty("HUB_HOST");
		}

		String completeUrl = "http://" + host + ":4444/wd/hub";

		if (driver == null) {
			if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
				//WebDriverManager.chromedriver().setup();
				//ChromeOptions options = new ChromeOptions();
				//options.addArguments("--headless");
				// System.setProperty("webdriver.chrome.driver",
				// System.getProperty("user.dir") + "//src//test//resources//chromedriver");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--no-sandbox");
				options.addArguments("--headless");
				options.addArguments("--disable-dev-shm-usage");
				driver = new ChromeDriver(options);
				//driver = new RemoteWebDriver(new URL(completeUrl),options);
			}
			if (browser.equalsIgnoreCase("edge")) {
				//WebDriverManager.edgedriver().setup();
				// System.setProperty("webdriver.gecko.driver",
				// "//Users//rahulshetty//Downloads//geckodriver 5");
				//driver = new EdgeDriver();
				Capabilities cap = new EdgeOptions();
				driver = new RemoteWebDriver(new URL(completeUrl),cap);
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.get(url);
		}

		return driver;

	}

}
