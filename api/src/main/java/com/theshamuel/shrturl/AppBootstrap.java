/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.theshamuel.shrturl.filter.JwtFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * The ShrtURL bootstrap class.
 *
 * @author Alex Gladkikh
 */
@SpringBootApplication
public class AppBootstrap {

    private static Logger logger = LoggerFactory.getLogger(AppBootstrap.class);

    /**
     * Register JWT filter bean.
     *
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean jwtFilter() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new JwtFilter());
        registrationBean.addUrlPatterns("/api/*");

        return registrationBean;
    }

    /**
     * Jackson builder jackson object mapper builder.
     *
     * @return the jackson object mapper builder
     */
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return builder;
    }

    /**
     * Add Java time module to Jackson.
     *
     * @return the module
     */
    @Bean
    public Module javaTimeModule() {
        return new JavaTimeModule();
    }

    /**
     * Add java 8 module to Jackson.
     *
     * @return the module
     */
    @Bean
    public Module java8Module() {
        return new Jdk8Module();
    }

    /**
     * Add parameter names module to Jackson.
     *
     * @return the module
     */
    @Bean
    public Module parameterNamesModule() {
        return new ParameterNamesModule(JsonCreator.Mode.PROPERTIES);
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exceptions
     */
    public static void main(String[] args) throws IOException {
        logger.info("Shrturl is starting...{}",LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss", Locale.getDefault())));
        SpringApplication app = new SpringApplication(AppBootstrap.class, args);

        app.run(AppBootstrap.class, args);
    }

}
