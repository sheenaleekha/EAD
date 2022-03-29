package base;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext ;		
import org.testng.ITestListener ;		
import org.testng.ITestResult ;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import ExtentReports.ExtentManager;

public class ListenersNG implements ITestListener{
	
	ThreadLocal<ExtentTest> threadTest= new ThreadLocal<ExtentTest>();
	ExtentReports extent=ExtentManager.getExtentReports();;
	ExtentTest test;
	//ExtentReports extent;
	private static Logger log = LogManager.getLogger(ListenersNG.class.getName());

	public void onTestStart(ITestResult result) {
	
		test =  extent.createTest(result.getMethod().getMethodName(),result.getMethod().getDescription());
		threadTest.set(test);	
		
		log.info(result.getMethod().getMethodName()+ "Test Started");
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		threadTest.get().log(Status.PASS, result.getMethod().getMethodName()+" has been passed");

	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		threadTest.get().fail(result.getThrowable());
		log.info("Test Failed");
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		extent.flush();
	}

}
