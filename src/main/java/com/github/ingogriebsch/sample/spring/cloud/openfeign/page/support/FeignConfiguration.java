package com.github.ingogriebsch.sample.spring.cloud.openfeign.page.support;

import com.fasterxml.jackson.databind.Module;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.support.PageJacksonModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients
public class FeignConfiguration {

    /**
     * Necessary atm because of https://github.com/spring-cloud/spring-cloud-openfeign/issues/205
     */
    @Bean
    public Module pageJacksonModule() {
        return new PageJacksonModule();
    }
}
