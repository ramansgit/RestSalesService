package com.sales.service.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.concurrent.Callable;

import javax.websocket.EncodeException;
import javax.websocket.Session;

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
import com.sales.service.constants.SalesConstants;
import com.sales.service.model.Message;
import com.sales.websocket.service.SalesWSEndpoint;

/**
 * uploads excel file to server and converts to csv format
 * 
 * @author ramans
 *
 */
public class ExcelToCsvConvertor implements Callable<String> {

	private InputStream inputStream;
	private String contentType;
	private String clientId;

	public ExcelToCsvConvertor(InputStream inputStream, String contentType, String clientId) {
		this.inputStream = inputStream;
		this.contentType = contentType;
		this.clientId = clientId;
	}

	/**
	 * 
	 * @param inputStream
	 * @param contentType
	 * @param clientId
	 * @return
	 */
	public static String readExcelDataAndConvertToCsv(InputStream inputStream, String contentType, String clientId) {

		CSVWriter my_csv_output = null;
		Workbook workbook = null;

		try {
			// Create the input stream from the xlsx/xls file
			// FileInputStream fis = new FileInputStream(fileName);

			System.out.println("contentType" + contentType);
			if (!(contentType.equalsIgnoreCase(SalesConstants.FILE_MEDIA_TYPE_XLSX)
					|| contentType.equalsIgnoreCase(SalesConstants.FILE_MEDIA_TYPE_XLS))) {
				throw new Exception("invalid file type -xlsx or xls allowed content types ");
			}

			// Create Workbook instance for xlsx/xls file input stream

			if (contentType != null && contentType.toLowerCase().endsWith(SalesConstants.FILE_MEDIA_TYPE_XLSX)) {
				workbook = new XSSFWorkbook(inputStream);
			}
			if (contentType != null && contentType.toLowerCase().endsWith(SalesConstants.FILE_MEDIA_TYPE_XLS)) {
				workbook = new HSSFWorkbook(inputStream);
			}
			String fileName = "sales" + SalesWSEndpoint.getClientFileName(clientId) + ".csv";
			System.out.println("fileName" + fileName);

			my_csv_output = new CSVWriter(new FileWriter(fileName, false), ',', '\0');

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

		} catch (Exception e) {
			System.out.println("xlsx to csv conversion failed" + e.getMessage());
			e.printStackTrace();
			return "error";
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (my_csv_output != null) {
					my_csv_output.close();
				}
				if (workbook != null) {
					workbook.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "success";

	}

	/**
	 * performing conversion in seperate thread
	 */
	public String call() {
		String status = "";
		try {
			status = readExcelDataAndConvertToCsv(inputStream, contentType, clientId);
			System.out.println(status);
			if (status == null || status.equalsIgnoreCase("error")) {
				// post error msg saying file conversion failed msg on socket
				System.out.println("error while converting");

			} else {
				Session session = SalesWSEndpoint.getSession(clientId);
				if (session != null) {
					try {
						session.getBasicRemote().sendObject(new Message(session.getId(), "CSV_READY"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (EncodeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// post success msg saying file conversion done on socket
			}
			// update status via websocket connection
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return status;
	}

}