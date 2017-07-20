package com.sales.service.model;

@javax.xml.bind.annotation.XmlRootElement
@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-03-28T13:06:53.817Z")
public class ApiResponseMessage {

	ApiError error;
	Object data;

	public ApiResponseMessage() {
	}

	public ApiResponseMessage(Object data) {
		this.data = data;
	}

	public ApiResponseMessage(ApiError error) {
		this.error = error;
	}

	public Object getError() {
		return error;
	}


	public Object getData() {
		return data;
	}



}
