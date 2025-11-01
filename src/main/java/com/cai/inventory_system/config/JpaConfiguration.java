package com.cai.inventory_system.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class JpaConfiguration {

    private final SqlLoggingInterceptor sqlLoggingInterceptor;

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return (Map<String, Object> hibernateProperties) ->
            hibernateProperties.put("hibernate.session_factory.interceptor", sqlLoggingInterceptor);
    }
}
