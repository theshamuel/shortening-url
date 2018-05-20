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
package com.theshamuel.shrturl.config;

import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import static java.util.Collections.singletonList;

/**
 * The Mongo config bean.
 *
 * @author Alex Gladkikh
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.theshamuel.shrturl")
public class MongoConfig extends AbstractMongoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(MongoConfig.class);

    /**
     * The Environment var.
     */
    @Autowired
    Environment environment;

    @Override
    protected String getDatabaseName() {
        if (environment.getProperty("MONGO_DB")==null){
            logger.warn("Didn't setup $MONGO_DB");
            return "shrturlDB";
        }
        return environment.getProperty("MONGO_DB");
    }


    @Override
    public Mongo mongo() throws Exception {
        String MONGO_SERVER = "localhost";
        Integer MONGO_PORT = 27017;
        String MONGO_DB = "shrturlDB";
        String MONGO_USER = null;
        String MONGO_PASSWORD = null;

        if (environment.getProperty("MONGO_SERVER")!=null && !environment.getProperty("MONGO_SERVER").trim().equals(""))
          MONGO_SERVER = environment.getProperty("MONGO_SERVER");
        else
            logger.warn("Didn't setup $MONGO_SERVER");

        if (environment.getProperty("MONGO_PORT")!=null && !environment.getProperty("MONGO_PORT").trim().equals(""))
            MONGO_PORT = Integer.valueOf(environment.getProperty("MONGO_PORT"));
        else
            logger.warn("Didn't setup $MONGO_PORT");

        if (environment.getProperty("MONGO_DB")!=null && !environment.getProperty("MONGO_DB").trim().equals(""))
            MONGO_DB = environment.getProperty("MONGO_DB");
        else
            logger.warn("Didn't setup $MONGO_DB");

        if (environment.getProperty("MONGO_USER")!=null && !environment.getProperty("MONGO_USER").trim().equals(""))
            MONGO_USER =  environment.getProperty("MONGO_USER");
        else
            logger.warn("Didn't setup $MONGO_USER");


        if (environment.getProperty("MONGO_USER_PASSWORD")!=null && !environment.getProperty("MONGO_USER_PASSWORD").trim().equals(""))
            MONGO_PASSWORD =  environment.getProperty("MONGO_USER_PASSWORD");
        else
            logger.warn("Didn't setup $MONGO_USER_PASSWORD");

        logger.warn("MONGO_CONNECTION has started in test mode");
        MongoClientOptions mgo = MongoClientOptions.builder().connectionsPerHost(65000).socketKeepAlive(true).socketTimeout(120 * 1000)
                .build();
        if (MONGO_USER !=null && MONGO_PASSWORD!=null)
            return   new MongoClient(singletonList(new ServerAddress(MONGO_SERVER, MONGO_PORT)),
                    singletonList(MongoCredential.createCredential(MONGO_USER, MONGO_DB, MONGO_PASSWORD.toCharArray())),mgo);
        else
           return new MongoClient(new ServerAddress(MONGO_SERVER, MONGO_PORT), mgo);


    }



}