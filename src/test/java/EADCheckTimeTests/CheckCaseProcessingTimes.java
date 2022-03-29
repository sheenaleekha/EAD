package EADCheckTimeTests;

import org.testng.annotations.Test;
import ExcelReader.InputDataSheet;
import base.GetDriver;
import pageObjects.ProcessingTimes;
import testDerivation.GetQueries;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

public class CheckCaseProcessingTimes {

	WebDriver driver = null;
	private static String processingTimesurl = null;
	private static Logger log = LogManager.getLogger(CheckCaseProcessingTimes.class.getName());
	private static XSSFSheet inputData = null;
	private static String processingTimes = "ProcessingTimes";

	@BeforeTest
	public void initialize() {
		driver = base.GetDriver.getURL();
		processingTimesurl = GetDriver.getProp().getProperty("urlProcessingTimes");
		driver.get(processingTimesurl);

	}

	@Test(dataProvider = "Cases")
	public void checkMyCase(String fieldofc, String formName, String formType) throws InterruptedException {
		ProcessingTimes procTime = new ProcessingTimes(driver);
		procTime.selectForm(formName);
		log.info("Entered form name");
		procTime.selectFieldOffice(fieldofc);
		log.info("Entered service center");
		procTime.clickGetProcessingTimesBtn();
		log.info("Clicked on processing times button");
		
		procTime.clickreadLinesBtn();
		Map<String,String>  getResult=procTime.getResult(formType);
				
		String receiptDate=getResult.getOrDefault("ReceiptDate", "Receipt Date Not Found");
		String estTime=getResult.getOrDefault("EstimatedTime", "Estimated Time Not Found");
		
		
		
		GetQueries.checkCaseProcessingTimesExecuteQueries(fieldofc, formName, formType, receiptDate, estTime);
	}

	@DataProvider(name = "Cases")
	public Object[][] getCases() {
		inputData = InputDataSheet.getData().get(processingTimes);
		Object[][] dataCaptured = InputDataSheet.getSheetData(inputData);

		return dataCaptured;
	}
	
	

	@AfterTest
	public void report() {
		driver.close();

	}

}
