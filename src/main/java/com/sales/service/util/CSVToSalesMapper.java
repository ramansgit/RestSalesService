package com.sales.service.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.sales.service.model.Sales;
import com.sales.service.model.WeeklySale;
import com.sales.websocket.service.SalesWSEndpoint;

/**
 * converts csv data to sales pojo
 * 
 * @author ramans
 *
 */
public class CSVToSalesMapper {

	/**
	 * converts to csv data to sales pojo
	 * 
	 * @return
	 * @throws Exception
	 */
	public static synchronized List<Sales> convertCsvToSales(String clientId) throws Exception {
		List<Sales> sales = new ArrayList<Sales>();
		FileReader fileReader = null;
		CSVReader reader = null;
		File file = null;
		try {
			String fileName = "sales"+SalesWSEndpoint.getClientFileName(clientId)+".csv";
			System.out.println("file name for reading"+fileName);
			file = new File(fileName);
			if (file.exists() && !file.isDirectory()) {
				System.out.println("file exist ");
				fileReader = new FileReader(fileName);

				reader = new CSVReader(fileReader, ',');
				reader.readNext(); // skipping as header
				reader.readNext();// skipping as header

				String[] record = null;

				while ((record = reader.readNext()) != null) {
					Sales sale = new Sales();
					List<WeeklySale> weeklySales = new ArrayList<WeeklySale>();

					sale.setProduct(record[0]);
					sale.setTarget(record[1]);

					WeeklySale weekSale1 = new WeeklySale();
					weekSale1.setWeekNo("Week1");
					weekSale1.setSales(record[2]);
					weekSale1.setAchievementPercentage(record[3]);
					weeklySales.add(weekSale1);

					WeeklySale weekSale2 = new WeeklySale();
					weekSale2.setWeekNo("Week2");
					weekSale2.setSales(record[4]);
					weekSale2.setAchievementPercentage(record[5]);
					weeklySales.add(weekSale2);

					WeeklySale weekSale3 = new WeeklySale();
					weekSale3.setWeekNo("Week3");
					weekSale3.setSales(record[6]);
					weekSale3.setAchievementPercentage(record[7]);
					weeklySales.add(weekSale3);

					WeeklySale weekSale4 = new WeeklySale();
					weekSale4.setWeekNo("Week4");
					weekSale4.setSales(record[8]);
					weekSale4.setAchievementPercentage(record[9]);
					weeklySales.add(weekSale4);
					sale.setWeeklySales(weeklySales);

					sales.add(sale);
				}
			} else {
				throw new Exception(
						"Sales csv file" + fileName + "not found in the directory , may be file upload not done yet");
			}

			System.out.println(sales);

		} finally {
			try {
				if (fileReader != null) {
					fileReader.close();
				}
				if (reader != null) {
					reader.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sales;

	}

}
