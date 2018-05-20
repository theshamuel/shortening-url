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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The ShortLink repository implementation class.
 *
 * @author Alex Gladkikh
 */
@Repository
public class ShortLinkRepositoryImpl implements ShortLinkOperations {

    @Autowired
    private MongoOperations mongo;

    /**
     * {@inheritDoc}
     */
    @Override
    public ShortLink findByShortUrl(String shortUrl) {
        Criteria where = Criteria.where("shortUrl").is(shortUrl);
        Query query = query = Query.query(where);
        return mongo.findOne(query,ShortLink.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List findShortUrlsByUser(String userLogin,Sort sort) {
        Criteria where = Criteria.where("userLogin").is(userLogin);
        Query query = null;
        if (sort!=null)
            query = Query.query(where).with(sort);
        else
            query = Query.query(where).with(new Sort(new Sort.Order(Sort.Direction.ASC,"createdDate")));
        return mongo.find(query,ShortLink.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMongo(MongoOperations mongo) {
        this.mongo = mongo;
    }
}
