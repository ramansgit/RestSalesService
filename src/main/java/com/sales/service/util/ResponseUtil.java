package com.sales.service.util;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.sales.service.model.ApiError;
import com.sales.service.model.ApiResponseMessage;
import com.sales.service.model.MsgSuccess;

public class ResponseUtil {

	

	public static Response handleSuccessResp(Object data) {
		return Response.ok().status(Status.OK).entity(new ApiResponseMessage(data)).build();

	}

	public static Response handleSuccessResp(String message) {
		return Response.ok().status(Status.OK).entity(new MsgSuccess(message)).build();

	}
	
	public static Response handleFailureResp(Status status,String code, String msg) {
		return Response.ok().status(status).entity(new ApiResponseMessage(new ApiError(code, msg))).build();

	}
}
