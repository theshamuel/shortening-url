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
package com.theshamuel.shrturl.baseclasses.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * The Base entity class.
 *
 * @author Alex Gladkikh
 */
public class BaseEntity implements Serializable {

    @Id
    private String id;

    @Field("createdDate")
    private Date createdDate;

    @Field("modifyDate")
    private Date modifyDate;

    @Field("author")
    private String author;


    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * Sets created date.
     *
     * @param createdDate the created date
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }


    /**
     * Gets modify date.
     *
     * @return the modify date
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * Sets modify date.
     *
     * @param modifyDate the modify date
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }


    /**
     * Gets author.
     *
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Sets author.
     *
     * @param author the author
     */
    public void setAuthor(String author) {
        this.author = author;
    }

}
