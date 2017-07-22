package com.sales.service.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

public abstract class SalesApiService {

	/**
	 * allows client to upload excel file
	 */
	public abstract Response uploadExcelFile(String sessionId,HttpServletRequest request);

	/**
	 * allows client to get the sales record
	 * 
	 */
	public abstract Response getSalesRecord(String sessionId);
	

}
 