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
package com.theshamuel.shrturl.links.dao.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkOperations;
import com.theshamuel.shrturl.links.entity.ShortLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * The ShortLink repository implementation class.
 *
 * @author Alex Gladkikh
 */
@Repository
public class ShortLinkRepositoryImpl implements ShortLinkOperations {
    private final static Logger logger =  LoggerFactory.getLogger(ShortLinkRepositoryImpl.class);

    @Autowired
    private MongoOperations mongo;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortLink findByShortUrl(String shortUrl) {
        Criteria where = Criteria.where("shortUrl").is(shortUrl);
        Query query = Query.query(where);
        return mongo.findOne(query,ShortLink.class);
    }
}
