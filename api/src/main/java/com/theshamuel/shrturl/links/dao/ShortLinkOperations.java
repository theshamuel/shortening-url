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
package com.theshamuel.shrturl.links.dao;

import com.theshamuel.shrturl.links.entity.ShortLink;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;

/**
 * The interface ShortLink operations.
 *
 * @author Alex Gladkikh
 */
public interface ShortLinkOperations {

    /**
     * Find by short URL link.
     * If sort set as null, default sort will be ASC by created date
     *
     * @param shortUrl the short url
     * @return the short link entity
     */
    ShortLink findByShortUrl(String shortUrl);


    /**
     * Find short urls by user list.
     * If sort set as null, default sort will be ASC by created date
     *
     * @param userLogin the user login
     * @param sort      the sort
     * @return the list
     */
    List findShortUrlsByUser(String userLogin, Sort sort);

    /**
     * Sets mongo provider.
     *
     * @param mongo operation implementation
     */
    void setMongo(MongoOperations mongo);
}
