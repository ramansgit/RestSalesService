package com.sales.service.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * starts jersey service and websockets service on the same endpoint.
 * @author ramans
 *
 */
public class ServerStarter  {
    public static void main( String[] args ) throws Exception {
        Server server = new Server(8080);
        
    	ResourceConfig config = new ResourceConfig();
		config.packages("com.sales.rest.resource");
		config.register(MultiPartFeature.class);
		ServletHolder servlet = new ServletHolder(new ServletContainer(config));    

        // Create the 'root' Spring application context
        final ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addEventListener(new ContextLoaderListener());
        context.setInitParameter("contextClass",AnnotationConfigWebApplicationContext.class.getName());
        context.setInitParameter("contextConfigLocation",AppConfig.class.getName());

        // Create default servlet (servlet api required)
        // The name of DefaultServlet should be set to 'defualt'.
        final ServletHolder defaultHolder = new ServletHolder( "default", DefaultServlet.class );
        defaultHolder.setInitParameter( "resourceBase", System.getProperty("user.dir") );
        context.addServlet( defaultHolder, "/" );
        context.addServlet(servlet, "/rest/*");
        
     
        
        server.setHandler(context);
        WebSocketServerContainerInitializer.configureContext(context);
       
        
        server.start();
        server.join();	
    }
}
