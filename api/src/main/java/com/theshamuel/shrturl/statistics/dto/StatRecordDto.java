/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-URL )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.statistics.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * The data transaction object entity of statistic info by particular short URL.
 *
 * @author Alex Gladkikh
 */
public class StatRecordDto {

    private String shortUrl;

    private List<Country> country;

    private List<Browser> browser;

    private List<OperationSystem> operationSystem;

    private Long totalClicks;


    public List<Country> getCountry() {
        return country;
    }

    public void setCountry(List<Country> country) {
        this.country = country;
    }

    public List<Browser> getBrowser() {
        return browser;
    }

    public void setBrowser(List<Browser> browser) {
        this.browser = browser;
    }

    public List<OperationSystem> getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(List<OperationSystem> operationSystem) {
        this.operationSystem = operationSystem;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
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

        if (!(o instanceof StatRecordDto)) return false;

        StatRecordDto that = (StatRecordDto) o;

        return new EqualsBuilder()
                .append(shortUrl, that.shortUrl)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(shortUrl)
                .toHashCode();
    }
}
