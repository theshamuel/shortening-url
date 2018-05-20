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

/**
 * The interface ShortLink operations.
 *
 * @author Alex Gladkikh
 */
public interface ShortLinkOperations {

    /**
     * Find by short URL link.
     *
     * @param shortUrl the short url
     * @return the short link entity
     */
    ShortLink findByShortUrl(String shortUrl);

}
