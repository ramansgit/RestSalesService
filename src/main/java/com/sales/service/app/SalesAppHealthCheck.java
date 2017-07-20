package com.sales.service.app;

import com.codahale.metrics.health.HealthCheck;

/**
 * dropwizard health check configuration
 * @author ramans
 *
 */
public class SalesAppHealthCheck extends HealthCheck {
    private final String version;
 
    public SalesAppHealthCheck(String version) {
        this.version = version;
    }
 
    /**
     * check the health of application
     */
    @Override
    protected Result check() throws Exception {
 
        return Result.healthy();
    }
}