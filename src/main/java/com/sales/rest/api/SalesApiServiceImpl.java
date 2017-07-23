package com.sales.rest.api;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sales.service.constants.SalesConstants;
import com.sales.service.model.Sales;
import com.sales.service.util.CSVToSalesMapper;
import com.sales.service.util.ExcelToCsvConvertor;
import com.sales.service.util.ResponseUtil;
import com.sales.websocket.service.SalesWSEndpoint;

/**
 * responsible for uploading sales file and converting to csv format sales
 * records fetched from csv file
 * 
 * @author ramans
 *
 */
public class SalesApiServiceImpl extends SalesApiService {

	/**
	 * uploads xlsx file into server and saves in .csv format
	 */
	@Override
	public Response uploadExcelFile(String clientId, HttpServletRequest request) {
		InputStream in = null;
		try {
			MultipartConfigElement multipartConfigElement = new MultipartConfigElement("data/tmp");
			request.setAttribute("org.eclipse.jetty.multipartConfig", multipartConfigElement);
			// String fName = request.getParameter("name");

			Part file = request.getPart("upload");

			String contentType = "";

			if (clientId == null || !SalesWSEndpoint.isClientFound(clientId)) {
				System.out.println("client id not provided or no session found for id" + clientId);
				return ResponseUtil.handleFailureResp(Status.BAD_REQUEST, SalesConstants.INVALID_SOCKET_ID_CLIENT,
						SalesConstants.INVALID_SOCKET_ID_CLIENT_MSG);
			}

			if (file == null) {
				return ResponseUtil.handleFailureResp(Status.BAD_REQUEST, SalesConstants.EMPTY_FILE_DATA,
						SalesConstants.EMPTY_FILE_DATA_MSG);

			}

			if (file.getSize() > 1024 * 1024 * 10) {
				return ResponseUtil.handleFailureResp(Status.BAD_REQUEST, SalesConstants.INVALID_FILE_SIZE,
						SalesConstants.INVALID_FILE_SIZE_MSG);
			}
			in = file.getInputStream();
			contentType = file.getContentType();
			if (in == null || contentType == null || !(contentType.equalsIgnoreCase(SalesConstants.FILE_MEDIA_TYPE_XLSX)
					|| contentType.equalsIgnoreCase(SalesConstants.FILE_MEDIA_TYPE_XLS))) {

				return ResponseUtil.handleFailureResp(Status.BAD_REQUEST, SalesConstants.INVALID_FILE_CONTENT_TYPE,
						SalesConstants.INVALID_FILE_CONTENT_TYPE_MSG);
			}

			System.out.println("sending file for conversion");
			Executors.newCachedThreadPool().submit(new ExcelToCsvConvertor(in, contentType, clientId));

			return ResponseUtil.handleSuccessResp(SalesConstants.FILE_RECEIVED_MSG);

		} catch (Exception e) {
			System.out.println("exception occured while doing file conversion" + e.getMessage());
			e.printStackTrace();

			return ResponseUtil.handleFailureResp(Status.INTERNAL_SERVER_ERROR, SalesConstants.FILE_UPLOAD_FAILED,
					SalesConstants.FILE_UPLOAD_FAILED_MSG);
		} finally {

		}
	}

	/**
	 * 
	 * reads csv file and sends sales data to client
	 */
	@Override
	public Response getSalesRecord(String clientId) {

		if (clientId == null || !SalesWSEndpoint.isClientFound(clientId)) {
			System.out.println("client id not provided or no session found for id" + clientId);
			return ResponseUtil.handleFailureResp(Status.BAD_REQUEST, SalesConstants.INVALID_SOCKET_ID_CLIENT,
					SalesConstants.INVALID_SOCKET_ID_CLIENT_MSG);
		}

		List<Sales> sales = new ArrayList<Sales>();
		sales.add(new Sales());
		try {
			sales = CSVToSalesMapper.convertCsvToSales(clientId);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseUtil.handleFailureResp(Status.NOT_FOUND, SalesConstants.FILE_NOT_FOUND,
					SalesConstants.FILE_NOT_FOUND_MSG);
		}

		return ResponseUtil.handleSuccessResp(sales);
	}

}
