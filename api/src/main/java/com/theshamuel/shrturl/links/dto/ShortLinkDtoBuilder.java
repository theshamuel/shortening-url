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

    private final ShortLinkDto linkDto;

    /**
     * Instantiates a new Short link dto builder.
     */
    public ShortLinkDtoBuilder() {
        this.linkDto = new ShortLinkDto();
    }

    /**
     * Id short link dto builder.
     *
     * @param id the id
     * @return the dto builder
     */
    public ShortLinkDtoBuilder id(String id) {
        linkDto.setId(id);
        return this;
    }

    /**
     * Created date short link dto builder.
     *
     * @param createdDate the created date
     * @return the dto builder
     */
    public ShortLinkDtoBuilder createdDate(Date createdDate) {
        linkDto.setCreatedDate(createdDate);
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
            linkDto.setShortUrl(pathUrl.toString());
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
        linkDto.setLongUrl(longUrl);
        return this;
    }

    /**
     * Total clicks short link dto builder.
     *
     * @param totalClicks the total clicks
     * @return the dto builder
     */
    public ShortLinkDtoBuilder totalClicks(Long totalClicks) {
        linkDto.setTotalClicks(totalClicks);
        return this;
    }

    /**
     * Build short link dto.
     *
     * @return the dto
     */
    public ShortLinkDto build() {
        return linkDto;
    }
}
