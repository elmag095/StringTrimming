package org.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StringTrimmingConfiguration {
    @Bean
    TrimmedAnnotationBeanPostProcessor trimmedAnnotationBeanPostProcessor() {
        return new TrimmedAnnotationBeanPostProcessor();
    }
}
