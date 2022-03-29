package testDerivation;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import DBConnection.SQLConnection;
import ExcelReader.InputDataSheet;

public class GetQueries {
	
	static SQLConnection connection;
	private static XSSFSheet inputSQLData = null;
	private static String sqlQueries = "SQLQueries";
	private static Map<String, String> allQueries = null;
	
	private static String InsertCaseTOcheckCaseProcessingTimes="InsertCaseTOcheckCaseProcessingTimes";
	private static String InsertResult="InsertResult";
	private static String InsertCaseTOcaseStatus="InsertCaseTOcaseStatus";
	private static String InsertcaseStatus="InsertcaseStatus";
	private static Logger log = LogManager.getLogger(GetQueries.class.getName());
	
	
	
	@SuppressWarnings("unchecked")
	public static void getData(){
		allQueries= new HashMap<String,String>();
		inputSQLData = InputDataSheet.getData().get(sqlQueries);
		Object[][] dataCaptured =  InputDataSheet.getSheetData(inputSQLData);
		allQueries=ArrayUtils.toMap(dataCaptured);
		log.info("SQL queries data is captured from Excel");
	}
	
	public static void checkCaseProcessingTimesExecuteQueries(String fieldofc, String formName, String formType, String receiptDate, String estimatedTime) {
		connection = new SQLConnection();
		getData();
		String myQuerytoInsertInput = allQueries.get(InsertCaseTOcheckCaseProcessingTimes);
		
		myQuerytoInsertInput=myQuerytoInsertInput.replace("$fieldofc", fieldofc);
		myQuerytoInsertInput=myQuerytoInsertInput.replace("$formName", formName);
		myQuerytoInsertInput=myQuerytoInsertInput.replace("$formType", formType);
		
		String myQuerytoInsertOutput = allQueries.get(InsertResult);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$fieldofc", fieldofc);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$formName", formName);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$formType", formType);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$receiptDate", receiptDate);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$estimatedTime", estimatedTime);
		
		connection.executeQuerynoRS(myQuerytoInsertInput);
		
		connection.executeQuerynoRS(myQuerytoInsertOutput);
		
	}
	

	public static void caseStatusExecuteQueries(String caseNumber, String status) {
		
		connection = new SQLConnection();
		getData();
		String myQuerytoInsertInput = allQueries.get(InsertCaseTOcaseStatus);
		
		myQuerytoInsertInput=myQuerytoInsertInput.replace("$caseNumber", caseNumber);
		
		String myQuerytoInsertOutput = allQueries.get(InsertcaseStatus);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$caseNumber", caseNumber);
		myQuerytoInsertOutput=myQuerytoInsertOutput.replace("$status", status);
		
		connection.executeQuerynoRS(myQuerytoInsertInput);
		connection.executeQuerynoRS(myQuerytoInsertOutput);
		
	}

}
