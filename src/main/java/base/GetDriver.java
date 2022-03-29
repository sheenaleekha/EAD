package base;

import java.io.FileInputStream;

import java.util.HashMap;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import DBConnection.SQLConnection;
import io.github.bonigarcia.wdm.WebDriverManager;

public class GetDriver {

	private static Properties prop;
	private static WebDriver driver = null;
	private static String browser = null;
	private static final HashMap<String,String> propMap= new HashMap<String, String>();
	private static Logger log = LogManager.getLogger(GetDriver.class.getName());
	
	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		
		prop = new Properties();

		FileInputStream is = null;
		try {
			is = new FileInputStream("src/main/resources/data.properties");
			prop.load(is);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
		GetDriver.prop = prop;
	}
	
	



	public static WebDriver getURL() {
		
		//SQLConnection mycon= new SQLConnection();
		//mycon.getConnection();
		setProp(prop);		
		browser = getProp().getProperty("browser");

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().browserVersion("98").setup();
			ChromeOptions options = new ChromeOptions();
			// options.addArguments("start-maximized");
			driver = new ChromeDriver(options);
			return driver;

		} else if (browser.equals("firefox")) {

			WebDriverManager.firefoxdriver().browserVersion("").setup();
			driver = new FirefoxDriver();
			return driver;
		} else {
			return driver;
		}

	}

	

}
