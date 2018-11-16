package com.hibicode.beerstore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class ApiErrorConfig {

    public MessageSource apiErrorMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/api_erros");
        messageSource.setDefaultEncoding("UTF-8");

        return messageSource;
    }

}
