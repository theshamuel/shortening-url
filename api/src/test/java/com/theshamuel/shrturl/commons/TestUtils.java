package com.theshamuel.shrturl.commons;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * The Utils class for configuring test's enviroment
 *
 * @author Alex Gladkikh
 */
public class TestUtils {

    private static final ObjectMapper objectMapper = TestUtils.configuredObjectMapper();

    private static ObjectMapper configuredObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.configure(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS,false);
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_ABSENT);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.JAVA_LANG_OBJECT);
        return objectMapper;
    }

    /**
     * Convert object to json bytes.
     *
     * @param object the object
     * @return the json as array of bytes
     * @throws IOException the io exceptions
     */
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException {
        return objectMapper.writeValueAsBytes(object);
    }

    /**
     * Convert object to json string.
     *
     * @param object the object
     * @return the string
     * @throws IOException the io exceptions
     */
    public static String convertObjectToJson(Object object) throws IOException {
        return objectMapper.writeValueAsString(object);
    }

    /**
     * Create exceptions resolver exceptions handler exceptions resolver.
     *
     * @return the exceptions handler exceptions resolver
     */
    public static ExceptionHandlerExceptionResolver createExceptionResolver() {
        ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(
                    HandlerMethod handlerMethod, Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(
                        ShortUrlExceptionHandler.class).resolveMethod(exception);
                return new ServletInvocableHandlerMethod(
                        new ShortUrlExceptionHandler(), method);
            }
        };
        exceptionResolver.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        exceptionResolver.getMessageConverters().add(new StringHttpMessageConverter());
        exceptionResolver.getMessageConverters().add(new FormHttpMessageConverter());
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

    public static Claims getAdminClaims() {
        Map mapClaims = new HashMap();
        mapClaims.put("roles", "ADMIN");
        Claims claims = Jwts.claims(mapClaims);
        return claims;
    }
    public static Claims getUserClaims() {
        Map mapClaims = new HashMap();
        mapClaims.put("roles", "USER");
        Claims claims = Jwts.claims(mapClaims);
        return claims;
    }
}
