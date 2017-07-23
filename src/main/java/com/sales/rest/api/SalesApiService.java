package com.sales.rest.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public abstract class SalesApiService {

	/**
	 * allows client to upload excel file
	 */
	public abstract Response uploadExcelFile(String clientId,HttpServletRequest request);

	/**
	 * allows client to get the sales record
	 * 
	 */
	public abstract Response getSalesRecord(String clientId);
	

}
 