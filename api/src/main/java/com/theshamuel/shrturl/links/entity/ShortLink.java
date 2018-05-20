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
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * The ShortLink entity class.
 *
 * @author Alex Gladkikh
 */
@Document(collection = "shortlinks")
public class ShortLink extends BaseEntity {

    @Field("user")
    private String login;

    @Field("shortUrl")
    private String shortUrl;

    @Field("longUrl")
    private String longUrl;

    @Field("totalClicks")
    private Long totalClicks;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ShortLink)) return false;

        ShortLink that = (ShortLink) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(shortUrl, that.shortUrl)
                .append(longUrl, that.longUrl)
                .append(totalClicks, that.totalClicks)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(shortUrl)
                .append(longUrl)
                .append(totalClicks)
                .toHashCode();
    }
}
