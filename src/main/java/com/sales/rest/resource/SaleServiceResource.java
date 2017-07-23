package com.sales.rest.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import com.sales.rest.api.SalesApiService;
import com.sales.rest.api.SalesApiServiceImpl;

@Path("/api/v1/sales")
public class SaleServiceResource {
	private final SalesApiService delegate = new SalesApiServiceImpl();

	/**
	 * allows rest client to upload sales excel file via post endpoint. 
	 * clientId is mandatory for upload. 
	 * @param clientId
	 * @param request
	 * @param response
	 * @return
	 */
	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response uploadExcelFile(@HeaderParam("clientId") String clientId,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		System.out.println("clientId" + clientId);
		return delegate.uploadExcelFile(clientId, request);

	}

	/**
	 * allows rest client to get sales data from this get endpoint. 
	 * client id is mandatory for retrieval
	 * @param clientId
	 * @param request
	 * @param response
	 * @return
	 */
	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSaleRecords(@HeaderParam("clientId") String clientId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		System.out.println("clientId" + clientId);
		return delegate.getSalesRecord(clientId);
	}

}
