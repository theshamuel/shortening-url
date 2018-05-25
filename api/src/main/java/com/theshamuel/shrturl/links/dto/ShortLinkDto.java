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
package com.theshamuel.shrturl.links.dto;

import com.theshamuel.shrturl.baseclasses.entity.BaseEntity;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * The ShortLink dto (Data transaction object) class.
 *
 * @author Alex Gladkikh
 */
public class ShortLinkDto {

    private String userLogin;

    private String shortUrl;

    private String longUrl;

    private Long totalClicks;

    private Date createdDate;

    private Date modifyDate;

    private String author;
    /**
     * Instantiates a new ShortLink dto.
     */
    public ShortLinkDto() {
    }

    /**
     * Instantiates a new ShortLink dto.
     *
     * @param builder the builder
     */
    public ShortLinkDto(Builder builder) {

        setCreatedDate(builder.createdDate);

        setModifyDate(builder.modifyDate);

        setAuthor(builder.author);

        setUserLogin(builder.userLogin);

        setShortUrl(builder.shortUrl);

        setLongUrl(builder.longUrl);

        setTotalClicks(builder.totalClicks);
    }

    /**
     * Gets user login.
     *
     * @return the user login
     */
    public String getUserLogin() {
        return userLogin;
    }

    /**
     * Sets user login.
     *
     * @param userLogin the user login
     */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /**
     * Gets short url.
     *
     * @return the short url
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * Sets short url.
     *
     * @param shortUrl the short url
     */
    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    /**
     * Gets long url.
     *
     * @return the long url
     */
    public String getLongUrl() {
        return longUrl;
    }

    /**
     * Sets long url.
     *
     * @param longUrl the long url
     */
    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    /**
     * Gets total clicks.
     *
     * @return the total clicks
     */
    public Long getTotalClicks() {
        return totalClicks;
    }

    /**
     * Sets total clicks.
     *
     * @param totalClicks the total clicks
     */
    public void setTotalClicks(Long totalClicks) {
        this.totalClicks = totalClicks;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Builder builder.
     *
     * @return the builder
     */
    public static Builder builder(){
        return new Builder();
    }

    /**
     * The ShortLink dto (Data transaction object) builder.
     */
    public static final class Builder {

        private Date createdDate;

        private Date modifyDate;

        private String author;

        private String userLogin;

        private String shortUrl;

        private String longUrl;

        private Long totalClicks;

        /**
         * Instantiates a new Short link builder.
         */
        public Builder() {

        }


        /**
         * Created date short link builder.
         *
         * @param createdDate the created date
         * @return the builder
         */
        public Builder createdDate(Date createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        /**
         * Modify date short link builder.
         *
         * @param modifyDate the modify date
         * @return the builder
         */
        public Builder modifyDate(Date modifyDate) {
            this.modifyDate = modifyDate;
            return this;
        }

        /**
         * Author builder.
         *
         * @param author the author
         * @return the builder
         */
        public Builder author(String author) {
            this.author = author;
            return this;
        }

        /**
         * User login short link builder.
         *
         * @param userLogin the user login
         * @return the short link builder
         */
        public Builder userLogin(String userLogin) {
            this.userLogin = userLogin;
            return this;
        }

        /**
         * Short url short link builder.
         *
         * @param shortUrl the short url
         * @return the builder
         */
        public Builder shortUrl(String shortUrl) {
            this.shortUrl = shortUrl;
            return this;
        }

        /**
         * Long url short link builder.
         *
         * @param longUrl the long url
         * @return the builder
         */
        public Builder longUrl(String longUrl) {
            this.longUrl = longUrl;
            return this;
        }

        /**
         * Total clicks short link builder.
         *
         * @param totalClicks the total clicks
         * @return the builder
         */
        public Builder totalClicks(Long totalClicks) {
            this.totalClicks = totalClicks;
            return this;
        }

        /**
         * Build short link.
         *
         * @return the result ShortLink dto
         */
        public ShortLinkDto build() {
            return new ShortLinkDto(this);
        }
    }

}
