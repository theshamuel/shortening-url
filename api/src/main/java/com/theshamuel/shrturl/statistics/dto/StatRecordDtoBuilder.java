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
package com.theshamuel.shrturl.statistics.dto;

import java.util.List;

/**
 * The type Statistic record dto (data transaction object) builder.
 *
 * @author Alex Gladkikh
 */
public final class StatRecordDtoBuilder {

    private final StatRecordDto statRecordDto;

    /**
     * Instantiates a new Statistic record dto builder.
     */
    public StatRecordDtoBuilder() {
        this.statRecordDto = new StatRecordDto();
    }

    /**
     * Short url stat record dto builder.
     *
     * @param shortUrl the short url
     * @return the stat record dto builder
     */
    public StatRecordDtoBuilder shortUrl(String shortUrl) {
        StringBuilder pathUrl = new StringBuilder("http://localhost:82");
        if (   1==1 || System.getenv("DOMAIN")!=null && System.getenv("DOMAIN").trim().length()>0) {
            //pathUrl.append(System.getenv("DOMAIN"));
            pathUrl.append("/");
            pathUrl.append(shortUrl);
            statRecordDto.setShortUrl(pathUrl.toString());
        }else {
            throw new RuntimeException("Not found DOMAIN intro Environment");
        }
        return this;
    }

    /**
     * Countries stat record dto builder.
     *
     * @param country the countries
     * @return the stat record dto builder
     */
    public StatRecordDtoBuilder country (List<Country> country){
        statRecordDto.setCountry(country);
        return this;
    }

    public StatRecordDtoBuilder browser (List<Browser> browser){
        statRecordDto.setBrowser(browser);
        return this;
    }

    public StatRecordDtoBuilder operationSystem (List<OperationSystem> os){
        statRecordDto.setOperationSystem(os);
        return this;
    }
    /**
     * Total clicks stat record dto builder.
     *
     * @param totalClicks the total clicks
     * @return the stat record dto builder
     */
    public StatRecordDtoBuilder totalClicks(Long totalClicks) {
        statRecordDto.setTotalClicks(totalClicks);
        return this;
    }

    /**
     * Build stat record dto.
     *
     * @return the stat record dto
     */
    public StatRecordDto build() {
        return statRecordDto;
    }

}
