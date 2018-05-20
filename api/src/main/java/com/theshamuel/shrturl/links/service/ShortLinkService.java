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
package com.theshamuel.shrturl.links.service;

import com.theshamuel.shrturl.baseclasses.service.BaseService;
import com.theshamuel.shrturl.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.links.entity.ShortLink;

/**
 * The interface ShortLink service.
 *
 * @author Alex Gladkikh
 */
public interface ShortLinkService extends BaseService<ShortLinkDto,ShortLink> {

    /**
     * Is short URL in data store unique .
     *
     * @param shortUrl the short URL
     * @return the result (true - is unique, false - there is the same sequence into store)
     */
    boolean isShortLinkServiceUnique(String shortUrl);

    /**
     * Find by short url short link.
     *
     * @param shortUrl the short URL
     * @return the short link
     */
    ShortLink findByShortUrl(String shortUrl);
}
