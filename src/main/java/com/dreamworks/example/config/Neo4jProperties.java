package com.dreamworks.example.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by mmonti on 4/20/17.
 */
@Data
@ConfigurationProperties(prefix = "wfg.neo4j")
public class Neo4jProperties {

    private String domainPackage;
    private String driverClassname;
    private String uri;
    private String username;
    private String password;
    
}
