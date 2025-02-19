package com.nmc.nmc_gateway;

import com.nmc.nmc_gateway.config.AsyncSyncConfiguration;
import com.nmc.nmc_gateway.config.EmbeddedElasticsearch;
import com.nmc.nmc_gateway.config.EmbeddedSQL;
import com.nmc.nmc_gateway.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { NmcApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedElasticsearch
@EmbeddedSQL
public @interface IntegrationTest {
}
