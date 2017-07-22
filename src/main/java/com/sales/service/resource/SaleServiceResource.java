package com.sales.service.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.sales.service.api.SalesApiService;
import com.sales.service.api.SalesApiServiceImpl;

@Path("/api/v1/sales")
public class SaleServiceResource {
	private final SalesApiService delegate = new SalesApiServiceImpl();

	@POST
	@Consumes({ "multipart/form-data" })
	@Produces({ "application/json" })
	public Response uploadExcelFile(@HeaderParam("sessionId") String sessionId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		System.out.println("sessionId" + sessionId);
		return delegate.uploadExcelFile(sessionId, request);

	}

	@GET
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	public Response getSaleRecords(@HeaderParam("sessionId") String sessionId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		return delegate.getSalesRecord(sessionId);
	}

}
