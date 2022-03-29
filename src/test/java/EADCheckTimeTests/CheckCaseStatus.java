package EADCheckTimeTests;
import org.testng.annotations.Test;

import ExcelReader.InputDataSheet;
import base.GetDriver;
import pageObjects.CaseStatus;
import testDerivation.GetQueries;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.annotations.AfterTest;

public class CheckCaseStatus implements ITest{
	WebDriver driver = null;
	private static String caseStatusURL = null;
	private static Logger log = LogManager.getLogger(CheckCaseProcessingTimes.class.getName());
	private static XSSFSheet inputData = null;
	private static String caseStatus = "CaseStatus";
	private ThreadLocal<String> testName = new ThreadLocal<String>();
	
	
	
	@BeforeTest
	public void initialize() {
		
		driver = base.GetDriver.getURL();
		caseStatusURL = GetDriver.getProp().getProperty("urlCasestatus");
		driver.get(caseStatusURL);
		
	}
	
	@Test(dataProvider="Cases", description="To Check the status of my USCIS Case")
	public void checkMyCase(String caseNumber) {
		
		CaseStatus myCase = new CaseStatus(driver);
		myCase.enterReceiptNumber(caseNumber);
		log.info("case Number was successfully entered");
		myCase.clickchkstatusBtn();
		log.info("Clicked on check status button");
		String myResult=myCase.getResult();
		
		GetQueries.caseStatusExecuteQueries(caseNumber, myResult);
	
		testName.set("_"+caseNumber+": "+ myResult);
		
		
	}
	
	@DataProvider(name = "Cases")
	public Object[][] getCases() {
		inputData = InputDataSheet.getData().get(caseStatus);
		Object[][] dataCaptured = InputDataSheet.getSheetData(inputData);

		return dataCaptured;
	}
	
	@AfterTest
	public void report() {
		driver.close();
		
	}

	public String getTestName() {
		// TODO Auto-generated method stub
		return testName.get();
	}

}
