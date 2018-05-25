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
package com.theshamuel.shrturl.links.entity;

import com.theshamuel.shrturl.baseclasses.entity.BaseEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * The ShortLink entity class.
 *
 * @author Alex Gladkikh
 */
@Document(collection = "shortlinks")
public class ShortLink {


    @Field("userLogin")
    private String userLogin;

    @Id
    @Field("shortUrl")
    private String shortUrl;

    @Field("longUrl")
    private String longUrl;

    @Field("totalClicks")
    private Long totalClicks;

    @Field("createdDate")
    private Date createdDate;

    @Field("modifyDate")
    private Date modifyDate;

    @Field("author")
    private String author;


    /**
     * Instantiates a new ShortLink.
     */
    public ShortLink() {
    }

    /**
     * Instantiates a new ShortLink.
     *
     * @param builder the builder
     */
    public ShortLink(Builder builder) {

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
     * The ShortLink entity builder.
     */
    public static final class Builder {
        private String id;

        private Date createdDate;

        private Date modifyDate;

        private String author;

        private String userLogin;

        private String shortUrl;

        private String longUrl;

        private Long totalClicks;

        /**
         * Instantiates a new ShortLink builder.
         */
        public Builder() {

        }

        /**
         * Id short link builder.
         *
         * @param id the id
         * @return the builder
         */
        public Builder id(String id) {
            this.id = id;
            return this;
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
         * @return the result ShortLink entity
         */
        public ShortLink build() {
            return new ShortLink(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ShortLink)) return false;

        ShortLink that = (ShortLink) o;

        return new EqualsBuilder()
                .append(shortUrl, that.shortUrl)
                .append(longUrl, that.longUrl)
                .append(totalClicks, that.totalClicks)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shortUrl)
                .append(longUrl)
                .append(totalClicks)
                .toHashCode();
    }
}
