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
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * The interface ShortLink repository.
 *
 * @author Alex Gladkikh
 */
public interface ShortLinkRepository extends MongoRepository<ShortLink,String>, ShortLinkOperations {
}
