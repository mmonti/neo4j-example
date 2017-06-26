package com.dreamworks.example.config;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.config.DriverConfiguration;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by mmonti on 6/30/16.
 */
@Slf4j
@Configuration
@EnableNeo4jRepositories(basePackages = "com.dreamworks.example.repository")
@EnableTransactionManagement
public class Neo4jConfig {

    @Bean
    public Neo4jProperties neo4jProperties() {
        return new Neo4jProperties();
    }

    @Bean
    public Neo4jDefaultConfiguration configuration(final Neo4jProperties neo4jProperties){
        final Neo4jDefaultConfiguration neo4jDefaultConfiguration = new Neo4jDefaultConfiguration();
        final DriverConfiguration driverConfiguration = neo4jDefaultConfiguration
                .driverConfiguration()
                .setDriverClassName(neo4jProperties.getDriverClassname());

        if (!neo4jProperties.getUsername().isEmpty() && !neo4jProperties.getPassword().isEmpty()) {
            driverConfiguration.setCredentials(neo4jProperties.getUsername(), neo4jProperties.getPassword());
        }

        if (!neo4jProperties.getUri().isEmpty()) {
            driverConfiguration.setURI(neo4jProperties.getUri());
        }

        return neo4jDefaultConfiguration;
    }

    @Bean
    public SessionFactory sessionFactory(final Neo4jProperties neo4jProperties){
        return new SessionFactory(configuration(neo4jProperties), neo4jProperties.getDomainPackage());
    }

    @Bean
    public PlatformTransactionManager transactionManager(final Neo4jProperties neo4jProperties){
        return new Neo4jTransactionManager(sessionFactory(neo4jProperties));
    }
}
