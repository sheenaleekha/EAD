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

public class CheckCaseStatusTracking implements ITest {
	WebDriver driver = null;
	private static String caseStatusURL = null;
	// private static Logger log =
	// LogManager.getLogger(CheckCaseStatusTracking.class.getName());
	private static XSSFSheet inputData = null;
	private static String caseStatusTracking = "CaseStatusTracking";
	private static String caseDataSheet = "CaseData";
	private ThreadLocal<String> testName = new ThreadLocal<String>();
	private static Map<Integer, Object[]> caseData = new LinkedHashMap<Integer, Object[]>();
	private static int numb = 1;
	Logger log = LogManager.getLogger(CheckCaseStatusTracking.class);
	XSSFWorkbook workbook;
	XSSFSheet spreadsheet;
	String resultFileLocation = "/Users/sheena/Documents/OneDrive/TestResults.xlsx";

	@BeforeTest
	public void initialize() throws FileNotFoundException, IOException {

		driver = base.GetDriver.getURL();
		caseStatusURL = GetDriver.getProp().getProperty("urlCasestatus");
		driver.get(caseStatusURL);

	}

	@Test(dataProvider = "Cases", description = "To Check the status of USCIS Case")
	public void checkCases(String sno, String caseNumber)  {

		System.out.println("Case no " + sno + ": " + caseNumber);

		try {
			CaseStatus myCase = new CaseStatus(driver);

			myCase.enterReceiptNumber(caseNumber);
			log.info("case Number: " + caseNumber + " was successfully entered");
			myCase.clickchkstatusBtn();
			log.info("Clicked on check status button");
			String myResult;

			if (myCase.alertDisplayed()) {

				myResult = myCase.getMsgOnAlert();
				myCase.clickOkonAlertifPresent();

				caseData.put(++numb, new Object[] { sno, caseNumber, myResult, "", "False" });
			} else {
				myResult = myCase.getResult();
				String fullResult = myCase.getFullResult();
				caseData.put(++numb, new Object[] { sno, caseNumber, myResult, fullResult, myCase.isI765Present() });
			}

			File xlsxFile = new File(resultFileLocation);
			if (xlsxFile.exists()) {
				FileInputStream inputStream = new FileInputStream(xlsxFile);
				workbook = new XSSFWorkbook(inputStream);
				spreadsheet = workbook.getSheet("Case Data");
			} else {
				workbook = new XSSFWorkbook();
				spreadsheet = workbook.createSheet("Case Data");
				caseData.put(numb,
						new Object[] { "S.No.", "Case Receipt", "Case Status", "Full Status", "Other Info" });
			}

			if (caseData.keySet().size() > 2) {
				InputDataSheet.writeData(caseData, workbook, spreadsheet, resultFileLocation);
				caseData.clear();

			}

		} catch (IOException e) {
			log.error(e);
		}

	}

	@DataProvider(name = "Cases")
	public Object[][] getCases() {
		inputData = InputDataSheet.getData().get(caseStatusTracking);
		Object[][] dataCaptured = InputDataSheet.getSheetData(inputData);

		Map<String, String> caseNumList = new LinkedHashMap<String, String>();

		String from = dataCaptured[0][1].toString(); //
		String to = dataCaptured[1][1].toString(); //

		int sno = 1;
		for (int i = Integer.parseInt(from.split("22900")[1]); i <= Integer.parseInt(to.split("22900")[1]); i++) {

			String valueofI = String.valueOf(i);

			while (valueofI.length() < 5) {
				valueofI = "0" + valueofI;
			}
			caseNumList.put(String.valueOf(sno), "EAC22900" + valueofI);
			sno++;
		}
		Object[][] dataCaptured1 = new Object[caseNumList.size()][2];
		int i = 0;
		for (Map.Entry mapElement : caseNumList.entrySet()) {
			String key = (String) mapElement.getKey();

			String value = (String) mapElement.getValue();
			dataCaptured1[i][0] = key;
			dataCaptured1[i][1] = value;
			i++;
		}
		return dataCaptured1;
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
