package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public WebDriver driver;

	public WebDriver WebDriverManager() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//test//resources//global.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String url = prop.getProperty("QAUrl");
		String browser_properties = prop.getProperty("browser");
		String browser_maven = System.getProperty("browser");
		// result = testCondition ? value1 : value2

		String browser = browser_maven != null ? browser_maven : browser_properties;

		if (driver == null) {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				// System.setProperty("webdriver.chrome.driver",
				// System.getProperty("user.dir") + "//src//test//resources//chromedriver");
				// driver = new ChromeDriver();// driver gets the life
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability("browserName", "chrome");
				driver = new RemoteWebDriver(new URL("http://ec2.com:4444/wd/hub"),
						cap);
			}
			if (browser.equalsIgnoreCase("edge")) {
				WebDriverManager.edgedriver().setup();
				// System.setProperty("webdriver.gecko.driver",
				// "//Users//rahulshetty//Downloads//geckodriver 5");
				driver = new EdgeDriver();
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability("browserName", "edge");
				driver = new RemoteWebDriver(new URL("http://:4444/wd/hub"),
						cap);
			}
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.get(url);
		}

		return driver;

	}

}
