package com.awecommerce.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MongoDBValidationConfig {
    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener(
        LocalValidatorFactoryBean localValidatorFactoryBean
    ) {
        return new ValidatingMongoEventListener(localValidatorFactoryBean);
    }
}
