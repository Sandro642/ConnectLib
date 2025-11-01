package fr.sandro642.github.spring.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration class for controller beans.
 */

@Configuration
public class ControllerConfig {

    /**
     * Defines the primary DataController bean.
     * @return the singleton instance of DataController
     */
    @Bean
    @Primary
    public DataController dataController() {
        return DataController.getInstance();
    }
}
