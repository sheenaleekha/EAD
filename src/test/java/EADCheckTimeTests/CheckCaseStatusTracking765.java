package EADCheckTimeTests;

import org.testng.annotations.Test;

import ExcelReader.InputDataSheet;
import base.GetDriver;
import pageObjects.CaseStatus;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITest;
import org.testng.annotations.AfterTest;

public class CheckCaseStatusTracking765 implements ITest {
	WebDriver driver = null;
	private static String fileloc765 = "/Users/sheena/Documents/OneDrive/TestInput765.xlsx";
	private static String caseStatusURL = null;
	private static XSSFSheet inputData = null;
	private static String caseDataSheet = "CaseData";
	private ThreadLocal<String> testName = new ThreadLocal<String>();
	private static Map<Integer, Object[]> caseData = new LinkedHashMap<Integer, Object[]>();
	private static int numb = 1;
	Logger log = LogManager.getLogger(CheckCaseStatusTracking765.class);
	XSSFWorkbook workbook;
	XSSFSheet spreadsheet;
	String resultFileLocation = "/Users/sheena/Documents/OneDrive/Test765Result.xlsx";

	@BeforeTest
	public void initialize() throws FileNotFoundException, IOException {

		driver = base.GetDriver.getURL();
		caseStatusURL = GetDriver.getProp().getProperty("urlCasestatus");
		driver.get(caseStatusURL);

	}

	@Test(dataProvider = "Cases", description = "To Check the status of USCIS Case")
	public void checkCases(String sno, String caseNumber, String caseStatus, String fullStatus, String receiptDate,
			String newStatus) {

		try {

			File xlsxFile = new File(resultFileLocation);
			if (xlsxFile.exists()) {
				FileInputStream inputStream = new FileInputStream(xlsxFile);
				workbook = new XSSFWorkbook(inputStream);
				spreadsheet = workbook.getSheet(caseDataSheet);
				if (sno.equalsIgnoreCase("1")) {
					InputDataSheet.writeValueAtEndofSpecificRow(workbook, spreadsheet, resultFileLocation, 0,
							LocalDate.now().toString());
				}
			} else {
				System.out.println("Result file does not exist");
				log.error("Result file does not exist");
			}
			int row = Integer.parseInt(sno);
			int lastcell= spreadsheet.getRow(row).getLastCellNum();
			
			if(spreadsheet.getRow(row).getCell(lastcell-1).getStringCellValue().equalsIgnoreCase("")) {
				
			}

			CaseStatus myCase = new CaseStatus(driver);
			myCase.enterReceiptNumber(caseNumber);
			log.info("case Number: " + caseNumber + " was successfully entered");
			myCase.clickchkstatusBtn();
			log.info("Clicked on check status button");
			String myResult;

			if (myCase.alertDisplayed()) {

				myResult = myCase.getMsgOnAlert();
				myCase.clickOkonAlertifPresent();

			} else {
				myResult = myCase.getResult();

			}

			if (myResult.equalsIgnoreCase(caseStatus)) {
				System.out.println("Case no " + sno + ": " + caseNumber + ": No Updates");
				log.info("No updates");

			} else {
				
				System.out.println("Case no " + sno + ": " + caseNumber + ": " + myResult);
				log.info("myResult");

				InputDataSheet.writeValueAtEndofSpecificRow(workbook, spreadsheet, resultFileLocation, row, myResult);

			}

		} catch (IOException e) {
			log.error(e);
		}

	}

	@DataProvider(name = "Cases")
	public Object[][] getCases() {
		inputData = InputDataSheet.getDatafromWorksheet(fileloc765).get(caseDataSheet);
		Object[][] dataCaptured = InputDataSheet.getSheetData2(inputData);
		return dataCaptured;
	}

	@AfterTest
	public void report() throws IOException {
		// InputDataSheet.writeData(caseData, spreadsheet);
		driver.close();

	}

	public String getTestName() {
		// TODO Auto-generated method stub
		return testName.get();
	}

}
