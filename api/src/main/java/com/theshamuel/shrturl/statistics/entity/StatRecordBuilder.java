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
package com.theshamuel.shrturl.statistics.entity;


import java.util.Date;

/**
 * The StatRecord entity builder.
 */
public class StatRecordBuilder {
    private final StatRecord statRecord;

    /**
     * Instantiates a new Statistic record builder.
     */
    public StatRecordBuilder() {
        this.statRecord = new StatRecord();
    }

    /**
     * Id statistic record builder.
     *
     * @param id the id
     * @return the statistic record builder
     */
    public StatRecordBuilder id(String id) {
        statRecord.setId(id);
        return this;
    }

    /**
     * Created date statistic record builder.
     *
     * @param createdDate the created date
     * @return the statistic record builder
     */
    public StatRecordBuilder createdDate(Date createdDate) {
        statRecord.setCreatedDate(createdDate);
        return this;
    }

    /**
     * Modify date statistic record builder.
     *
     * @param modifyDate the modify date
     * @return the statistic record builder
     */
    public StatRecordBuilder modifyDate(Date modifyDate) {
        statRecord.setModifyDate(modifyDate);
        return this;
    }

    /**
     * Ipaddress statistic record builder.
     *
     * @param ipaddress the ip address
     * @return the statistic record builder
     */
    public StatRecordBuilder ipaddress(String ipaddress) {
        statRecord.setIpaddress(ipaddress);
        return this;
    }

    /**
     * Operation system statistic record builder.
     *
     * @param os the operation system
     * @return the statistic record builder
     */
    public StatRecordBuilder operationSystem(String os) {
        statRecord.setOperationSystem(os);
        return this;
    }

    /**
     * Browser statistic record builder.
     *
     * @param browser the browser
     * @return the statistic record builder
     */
    public StatRecordBuilder browser(String browser) {
        statRecord.setBrowser(browser);
        return this;
    }

    /**
     * Short url statistic record builder.
     *
     * @param shortUrl the short URL
     * @return the statistic record builder
     */
    public StatRecordBuilder shortUrl(String shortUrl) {
        statRecord.setShortUrl(shortUrl);
        return this;
    }

    /**
     * Country statistic record builder.
     *
     * @param country the country
     * @return the statistic record builder
     */
    public StatRecordBuilder country(String country) {
        statRecord.setCountry(country);
        return this;
    }

    /**
     * Build statistic record.
     *
     * @return the statistic record
     */
    public StatRecord build() {
        return statRecord;
    }
}
