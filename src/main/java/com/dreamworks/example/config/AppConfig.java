package com.dreamworks.example.config;

import com.dreamworks.example.support.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mmonti on 6/23/17.
 */
@Slf4j
@Configuration
public class AppConfig {

    @Bean
    public IDGenerator idGenerator() {
        return new IDGenerator();
    }
}
