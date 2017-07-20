package com.sales.service.util;

import java.io.FileWriter;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVWriter;

public class ExcelToCsvConvertor implements Callable<String> {

	private InputStream inputStream;
	private String contentType;

	public ExcelToCsvConvertor(InputStream inputStream, String contentType) {
		this.inputStream = inputStream;
		this.contentType = contentType;
	}

	public static String readExcelDataAndConvertToCsv(InputStream inputStream, String contentType) {

		try {
			// Create the input stream from the xlsx/xls file
			// FileInputStream fis = new FileInputStream(fileName);

			System.out.println("contentType" + contentType);
			if (!(contentType.equalsIgnoreCase("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
					|| contentType.equalsIgnoreCase("application/vnd.ms-excel"))) {
				throw new Exception("invalid file type -xlsx or xls allowed content types ");
			}

			// Create Workbook instance for xlsx/xls file input stream
			Workbook workbook = null;
			if (contentType != null && contentType.toLowerCase()
					.endsWith("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
				workbook = new XSSFWorkbook(inputStream);
			}
			if (contentType != null && contentType.toLowerCase().endsWith("application/vnd.ms-excel")) {
				workbook = new HSSFWorkbook(inputStream);
			}

			CSVWriter my_csv_output = new CSVWriter(new FileWriter("sales.csv", false), ',', '\0');

			// Get the nth sheet from the workbook
			Sheet sheet = workbook.getSheetAt(1);

			int rowStart = 4;
			int rowEnd = sheet.getLastRowNum();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			for (int rowIndex = rowStart; rowIndex <= rowEnd; rowIndex++) {
				Row row = sheet.getRow(rowIndex);

				if (row != null) {
					int cellStart = 0;
					int cellEnd = 9;
					String csvArr[] = new String[10];
					boolean isValid = true;
					for (int cellIndex = cellStart; cellIndex <= cellEnd; cellIndex++) {
						if (row.getCell(cellIndex) != null) {
							CellType type = row.getCell(cellIndex).getCellTypeEnum();
							Cell value = row.getCell(cellIndex);
							if (type != null && value != null) {
								if (type == CellType.STRING) {
									csvArr[cellIndex] = value.getStringCellValue();

								} else if (type == CellType.NUMERIC) {

									Double number = value.getNumericCellValue();
									csvArr[cellIndex] = String.valueOf(number.intValue());
								} else if (type == CellType.FORMULA) {
									CellValue eval = evaluator.evaluate(value);

									Double number = eval.getNumberValue() * 100;
									csvArr[cellIndex] = String.valueOf(new DecimalFormat("#.00").format(number) + "%");

								}
							}

						} else {
							isValid = false;
						}

					}
					if (isValid) {
						my_csv_output.writeNext(csvArr);
					}
				}
			}

			System.out.println("input xslx file converted succeffully to csv");

			my_csv_output.close();

		} catch (Exception e) {
			System.out.println("xlsx to csv conversion failed" + e.getMessage());
			e.printStackTrace();
			return "error";
		}
		return "success";

	}

	/**
	 * performing conversion in seperate thread
	 */
	public String call() throws Exception {

		String status = readExcelDataAndConvertToCsv(inputStream, contentType);
		if (status == null || status.equalsIgnoreCase("error")) {
			// post error msg saying file conversion failed msg on socket
		} else {

			// post success msg saying file conversion done on socket
		}
		// update status via websocket connection
		return status;
	}

}