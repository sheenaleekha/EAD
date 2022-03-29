package ExcelReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class InputDataSheet {

	private static XSSFWorkbook workbook;
	private static Map<String, XSSFSheet> inputSheetsMap;
	private static String inputdata = "CaseStatus";
	private static String casedata = "CaseData";
	private static String processingTimes = "ProcessingTimes";
	private static String caseStatusTracking = "CaseStatusTracking";
	private static String sqlQueries = "SQLQueries";

	private static Logger log = LogManager.getLogger(InputDataSheet.class.getName());

	public static Map<String, XSSFSheet> getData() {
		try {
			inputSheetsMap = new HashMap<String, XSSFSheet>();
			FileInputStream fis = new FileInputStream("/Users/sheena/Documents/OneDrive/InputData.xlsx");
			workbook = new XSSFWorkbook(fis);
			// noOfSheets=workbook.getNumberOfSheets();

			inputSheetsMap.put(inputdata, workbook.getSheet(inputdata));
			inputSheetsMap.put(processingTimes, workbook.getSheet(processingTimes));
			inputSheetsMap.put(sqlQueries, workbook.getSheet(sqlQueries));
			inputSheetsMap.put(caseStatusTracking, workbook.getSheet(caseStatusTracking));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return inputSheetsMap;

	}

	public static Object[][] getSheetData(XSSFSheet mySheet) {
		// Iterator<Row> rows=mySheet.rowIterator();
		Object[][] outputs = new Object[mySheet.getLastRowNum()][mySheet.getRow(0).getLastCellNum()];

		int firstRow = mySheet.getFirstRowNum();
		int lastRow = mySheet.getLastRowNum();
		for (int index = firstRow + 1; index <= lastRow; index++) {
			Row row = mySheet.getRow(index);
			// FormulaEvaluator objFormulaEvaluator = new
			// HSSFFormulaEvaluator((HSSFWorkbook) mySheet.get);

			for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
				Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
				// System.out.println(cell.getStringCellValue());

				outputs[index - 1][cellIndex] = cell.getStringCellValue();

			}
		}

		return outputs;

	}

	public static Object[][] getSheetData2(XSSFSheet mySheet) {
		// Iterator<Row> rows=mySheet.rowIterator();
		Object[][] outputs = new Object[mySheet.getLastRowNum()][mySheet.getRow(0).getLastCellNum()];
		DataFormatter formatter = new DataFormatter();
		int firstRow = mySheet.getFirstRowNum();
		int lastRow = mySheet.getLastRowNum();
		for (int index = firstRow + 1; index <= lastRow; index++) {
			Row row = mySheet.getRow(index);
			// FormulaEvaluator objFormulaEvaluator = new
			// HSSFFormulaEvaluator((HSSFWorkbook) mySheet.get);

			for (int cellIndex = row.getFirstCellNum(); cellIndex < row.getLastCellNum(); cellIndex++) {
				String cellVAlue = formatter
						.formatCellValue(row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
				// System.out.println(cell.getStringCellValue());

				outputs[index - 1][cellIndex] = cellVAlue;

			}
		}

		return outputs;

	}

	public static Map<String, XSSFSheet> getDatafromWorksheet(String fileLoc) {
		try {
			inputSheetsMap = new HashMap<String, XSSFSheet>();
			FileInputStream fis = new FileInputStream(fileLoc);
			workbook = new XSSFWorkbook(fis);
			int noOfSheets = workbook.getNumberOfSheets();

			while (noOfSheets > 0) {
				inputSheetsMap.put(workbook.getSheetAt(noOfSheets - 1).getSheetName(),
						workbook.getSheetAt(noOfSheets - 1));
				noOfSheets--;
			}

		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		return inputSheetsMap;

	}

	public static void getTableData(XSSFSheet mySheet) {

		List<XSSFTable> tables = mySheet.getTables();
		Object[][] inputs = new Object[5][];
		for (XSSFTable t : tables) {
			System.out.println(t.getDisplayName());
			System.out.println(t.getName());

			int startRow = t.getStartCellReference().getRow();
			int endRow = t.getEndCellReference().getRow();
			System.out.println("startRow = " + startRow);
			System.out.println("endRow = " + endRow);

			int startColumn = t.getStartCellReference().getCol();
			int endColumn = t.getEndCellReference().getCol();

			System.out.println("startColumn = " + startColumn);
			System.out.println("endColumn = " + endColumn);

			for (int i = startRow; i <= endRow; i++) {
				String cellVal = "";

				for (int j = startColumn; j <= endColumn; j++) {
					XSSFCell cell = mySheet.getRow(i).getCell(j);
					if (cell != null) {
						cellVal = cell.getStringCellValue();
						inputs[i][j] = cellVal;
					}
					System.out.print(cellVal + "\t");
				}
				System.out.println();
			}
		}

	}

	public static void writeData(Map<Integer, Object[]> caseData, XSSFWorkbook workbook, XSSFSheet spreadsheet,
			String location) throws IOException {

		int existingrowCount = spreadsheet.getLastRowNum();
		// creating a row object
		XSSFRow row;

		Set<Integer> keyid = caseData.keySet();

		// int rowid = 0;

		// writing the data into the sheets...

		for (int key : keyid) {

			row = spreadsheet.createRow(++existingrowCount);
			Object[] objectArr = caseData.get(key);
			int cellid = 0;

			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		// .xlsx is the format for Excel Sheets...
		// writing the workbook into the file...
		FileOutputStream out = new FileOutputStream(new File(location));

		workbook.write(out);
		out.close();

	}

	public static void writeRowAtTheEnd(Object[] objectArr, XSSFWorkbook workbook, XSSFSheet spreadsheet)
			throws IOException {
		int existingrowCount = spreadsheet.getLastRowNum();
		// creating a row object
		XSSFRow row;
		row = spreadsheet.createRow(existingrowCount++);

		// Object[] objectArr = caseData.get(key);
		int cellid = 0;

		for (Object obj : objectArr) {
			Cell cell = row.createCell(cellid++);
			cell.setCellValue((String) obj);
		}

		FileOutputStream out = new FileOutputStream(new File("/Users/sheena/Documents/OneDrive/TestResults.xlsx"));

		workbook.write(out);
		out.close();

	}

	public static void writeValueAtEndofSpecificRow(XSSFWorkbook workbook, XSSFSheet spreadsheet, String fileLoc, int rowno,
		 String value) throws IOException {
		XSSFRow row;

		CellStyle style = workbook.createCellStyle();
		// Setting Background color
		style.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
		style.setFillPattern(FillPatternType.BIG_SPOTS);

		row = spreadsheet.getRow(rowno);
		int cellno=row.getLastCellNum();
		
		XSSFCell cell = row.createCell(cellno);
		
		cell.setCellValue(value);
		cell.setCellStyle(style);

		FileOutputStream out = new FileOutputStream(new File(fileLoc));

		workbook.write(out);
		out.close();

	}

}
