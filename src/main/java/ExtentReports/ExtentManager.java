package ExtentReports;

import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {
	// ExtentReports extent = null;
	//static ExtentSparkReporter reporter = null;
	
	static String path = System.getProperty("user.dir") + "\\reports\\index.html";
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();
	public static ExtentReports extentReports = new ExtentReports();
	
	 public static synchronized ExtentTest getTest() {
	        return extentTestMap.get((int) Thread.currentThread().getId());
	    }
	 
	 
	public static ExtentReports getExtentReports() {

		
		ExtentSparkReporter reporter  = new ExtentSparkReporter("./extent-reports/extent-report.html");
		reporter.config().setReportName("Test Results");
		reporter.config().setDocumentTitle("Test Reports");
		 extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", "Sheena");
		

		return extentReports;
	}

}
