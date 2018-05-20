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


import com.theshamuel.shrturl.exceptions.NotFoundEntityException;

import java.util.Date;


/**
 * The ShortLink dto (Data transaction object) builder.
 *
 * @author Alex Gladkikh
 *
 */
public final class ShortLinkDtoBuilder {

    private final ShortLinkDto shortLinkDto;

    /**
     * Instantiates a new Short link dto builder.
     */
    public ShortLinkDtoBuilder() {
        this.shortLinkDto = new ShortLinkDto();
    }

    /**
     * Id short link dto builder.
     *
     * @param id the id
     * @return the dto builder
     */
    public ShortLinkDtoBuilder id(String id) {
        shortLinkDto.setId(id);
        return this;
    }

    /**
     * Created date short link dto builder.
     *
     * @param createdDate the created date
     * @return the dto builder
     */
    public ShortLinkDtoBuilder createdDate(Date createdDate) {
        shortLinkDto.setCreatedDate(createdDate);
        return this;
    }

    /**
     * User login short link dto builder.
     *
     * @param userLogin the user login
     * @return the short link builder
     */
    public ShortLinkDtoBuilder userLogin(String userLogin) {
        shortLinkDto.setUserLogin(userLogin);
        return this;
    }

    /**
     * Short url short link dto builder.
     *
     * @param shortUrl the short url
     * @return the dto builder
     */
    public ShortLinkDtoBuilder shortUrl(String shortUrl) {
        StringBuilder pathUrl = new StringBuilder("http://localhost:82/");
        if (1==1 || System.getenv("DOMAIN")!=null && System.getenv("DOMAIN")!=null && System.getenv("DOMAIN").trim().length()>0) {
            //pathUrl.append(System.getenv("DOMAIN"));
            pathUrl.append("/");
            pathUrl.append(shortUrl);
            shortLinkDto.setShortUrl(pathUrl.toString());
        }else {
            throw new NotFoundEntityException("Not found DOMAIN intro Environment");
        }
        return this;
    }

    /**
     * Long url short link dto builder.
     *
     * @param longUrl the long url
     * @return the dto builder
     */
    public ShortLinkDtoBuilder longUrl(String longUrl) {
        shortLinkDto.setLongUrl(longUrl);
        return this;
    }

    /**
     * Total clicks short link dto builder.
     *
     * @param totalClicks the total clicks
     * @return the dto builder
     */
    public ShortLinkDtoBuilder totalClicks(Long totalClicks) {
        shortLinkDto.setTotalClicks(totalClicks);
        return this;
    }

    /**
     * Build short link dto.
     *
     * @return the dto
     */
    public ShortLinkDto build() {
        return shortLinkDto;
    }
}
