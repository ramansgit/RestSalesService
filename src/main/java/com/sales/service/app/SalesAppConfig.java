package com.sales.service.app;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.logging.LoggingFactory;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

public class SalesAppConfig extends Configuration {
	
	/**
	 * version
	 */
	@NotEmpty
	private String version;

	/**
	 * version 
	 * @return
	 */
	@JsonProperty
	public String getVersion() {
		return version;
	}

	/**
	 * sets the version
	 * @param version
	 */
	@JsonProperty
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * swagger configuration
	 */
	  @JsonProperty("swagger")
	    public SwaggerBundleConfiguration swaggerBundleConfiguration;
	  

}