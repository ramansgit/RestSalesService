package com.sales.service.resource;

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

import com.sales.service.api.SalesApiService;
import com.sales.service.api.SalesApiServiceImpl;

@Path("/api/v1/sales")
public class SaleServiceResource {
	private final SalesApiService delegate = new SalesApiServiceImpl();

	@POST
	@Path("/pdf")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public Response uploadPdfFile(@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition fileMetaData) throws Exception {
		String UPLOAD_PATH = "/Users/ramans/git/RestSalesService/target/";
		try {
			int read = 0;
			byte[] bytes = new byte[1024];

			OutputStream out = new FileOutputStream(new File(UPLOAD_PATH + fileMetaData.getFileName()));
			while ((read = fileInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new WebApplicationException("Error while uploading file. Please try again !!");
		}
		return Response.ok("Data uploaded successfully !!").build();
	}

	@POST
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response uploadExcelFile(@HeaderParam("clientId") String clientId,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		System.out.println("clientId" + clientId);
		return delegate.uploadExcelFile(clientId, request);

	}

	@GET
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getSaleRecords(@HeaderParam("clientId") String clientId, @Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		System.out.println("clientId" + clientId);
		return delegate.getSalesRecord(clientId);
	}

}
