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

import com.codahale.metrics.annotation.Timed;
import com.sales.service.api.SalesApiService;
import com.sales.service.api.SalesApiServiceImpl;
import com.sales.service.model.ApiResponseMessage;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("/api/v1/sales")
@Api(description = "the sales API")
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-04-10T14:43:58.709Z")
public class SaleServiceResource {
	private final SalesApiService delegate = new SalesApiServiceImpl();

	@POST
	@Consumes({ "multipart/form-data" })
	@Produces({ "application/json" })
	@ApiOperation(value = "uploads excel file", notes = "uploads excel file", response = ApiResponseMessage.class, tags = {
			"Sales" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = ApiResponseMessage.class) })
	public Response uploadExcelFile(@ApiParam(value = "socketId", required = true) @HeaderParam("socketId") String socketId,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		return delegate.uploadExcelFile(socketId, request);
		
	}
	
	

	@GET
	@Timed
	@Consumes({ "application/json" })
	@Produces({ "application/json" })
	@ApiOperation(value = "get all sales order records ", notes = "get all sales order records", response = ApiResponseMessage.class, tags = {
			"Sales", })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "", response = ApiResponseMessage.class) })
	public Response getSaleRecords(
			@ApiParam(value = "socketId", required = true) @HeaderParam("socketId") String socketId,
			@Context HttpServletRequest request, @Context HttpServletResponse response) {
		
		return delegate.getSalesRecord(socketId);
	}

}
