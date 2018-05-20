package com.theshamuel.shrturl.links.entity;


import java.util.Date;

/**
 * The ShortLink entity builder.
 *
 * @author Alex Gladkikh
 */
public final class ShortLinkBuilder {
    private final ShortLink shortLink;

    /**
     * Instantiates a new Short link builder.
     */
    public ShortLinkBuilder() {
        this.shortLink = new ShortLink();
    }

    /**
     * Id short link builder.
     *
     * @param id the id
     * @return the builder
     */
    public ShortLinkBuilder id(String id) {
        shortLink.setId(id);
        return this;
    }

    /**
     * Created date short link builder.
     *
     * @param createdDate the created date
     * @return the builder
     */
    public ShortLinkBuilder createdDate(Date createdDate) {
        shortLink.setCreatedDate(createdDate);
        return this;
    }

    /**
     * Modify date short link builder.
     *
     * @param modifyDate the modify date
     * @return the builder
     */
    public ShortLinkBuilder modifyDate(Date modifyDate) {
        shortLink.setModifyDate(modifyDate);
        return this;
    }

    /**
     * User login short link builder.
     *
     * @param userLogin the user login
     * @return the short link builder
     */
    public ShortLinkBuilder userLogin(String userLogin) {
        shortLink.setUserLogin(userLogin);
        return this;
    }
    /**
     * Short url short link builder.
     *
     * @param shortUrl the short url
     * @return the builder
     */
    public ShortLinkBuilder shortUrl(String shortUrl) {
        shortLink.setShortUrl(shortUrl);
        return this;
    }

    /**
     * Long url short link builder.
     *
     * @param longUrl the long url
     * @return the  builder
     */
    public ShortLinkBuilder longUrl(String longUrl) {
        shortLink.setLongUrl(longUrl);
        return this;
    }

    /**
     * Total clicks short link builder.
     *
     * @param totalClicks the total clicks
     * @return the  builder
     */
    public ShortLinkBuilder totalClicks(Long totalClicks) {
        shortLink.setTotalClicks(totalClicks);
        return this;
    }

    /**
     * Build short link.
     *
     * @return the result ShortLink entity
     */
    public ShortLink build() {
        return shortLink;
    }
}
