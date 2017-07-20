package com.sales.service.app;

import java.net.UnknownHostException;

import org.eclipse.jetty.server.session.SessionHandler;

import com.sales.service.resource.SaleServiceResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;

/**
 * holds dropwizard configuration
 * 
 * @author ramans
 *
 */
public class SalesApp extends Application<SalesAppConfig> {

	public static void main(String[] args) throws Exception {

		new SalesApp().run(args);
	}

	/**
	 * initalize the swagger configuration
	 */
	@Override
	public void initialize(Bootstrap<SalesAppConfig> bootstrap) {

		bootstrap.addBundle(new SwaggerBundle<SalesAppConfig>() {
			@Override
			protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(SalesAppConfig configuration) {
				// this would be the preferred way to set up swagger, you can
				// also construct the object here programtically if you want
				return configuration.swaggerBundleConfiguration;
			}
		});
	}

	/**
	 * run method cvcvc
	 */
	@Override
	public void run(SalesAppConfig config, Environment env) throws UnknownHostException {

		env.jersey().register(SaleServiceResource.class);
		env.servlets().setSessionHandler(new SessionHandler());
		final SalesAppHealthCheck healthCheck = new SalesAppHealthCheck(config.getVersion());
		env.healthChecks().register("template", healthCheck);
		env.jersey().register(healthCheck);


	}
}