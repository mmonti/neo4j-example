package com.dreamworks.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by mmonti on 6/15/17.
 */
@Configuration
@ComponentScan(value = "com.dreamworks.example")
@EnableTransactionManagement
public class Neo4jEmbeddedConfig {

}
