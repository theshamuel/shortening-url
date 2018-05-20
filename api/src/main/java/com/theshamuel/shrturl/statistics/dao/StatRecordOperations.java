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
package com.theshamuel.shrturl.statistics.dao;

import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Date;
import java.util.List;

/**
 * The interface Stat record operations.
 */
public interface StatRecordOperations {

    /**
     * Count by short url.
     *
     * @param shortUrl the short url
     * @return the long
     */
    long countByShortUrl(String shortUrl);


    /**
     * Gets all statistics for period by country.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the all statistics for period by country
     */
    List getAllStatisticsByPeriod(Date startDate, Date endDate);


    /**
     * Gets statistics by user by period.
     *
     * @param userUrls  the user urls
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics by user by period
     */
    List getStatisticsByUserByPeriod(List<String> userUrls, Date startDate, Date endDate);

    /**
     * Gets statistics by short url for period by country.
     *
     * @param shortUrl  the short url
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics by short url by period by country
     */
    StatRecordDto getStatisticsByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate);

    /**
     * Gets statistics country by period.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics country by period
     */
    List getStatisticsCountryByPeriod(Date startDate, Date endDate);

    /**
     * Gets statistics country by short url by period by.
     *
     * @param shortUrl  the short url
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics country by short url by period by
     */
    List getStatisticsCountryByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate);


    /**
     * Gets statistics country by user by period.
     *
     * @param shortUrls the short urls by user
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics country by user by period
     */
    List getStatisticsCountryByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate);

    /**
     * Gets statistics browser by period.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics browser by period
     */
    List getStatisticsBrowserByPeriod(Date startDate, Date endDate);

    /**
     * Gets statistics browser by short url by period by.
     *
     * @param shortUrl  the short url
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics browser by short url by period by
     */
    List getStatisticsBrowserByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate);


    /**
     * Gets statistics browser by user by period.
     *
     * @param shortUrls the short urls by user
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics browser by user by period
     */
    List getStatisticsBrowserByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate);

    /**
     * Gets statistics os by period.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics os by period
     */
    List getStatisticsOsByPeriod(Date startDate, Date endDate);


    /**
     * Gets statistics os by short url by period.
     *
     * @param shortUrl  the short url
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics os by short url by period
     */
    List getStatisticsOsByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate);


    /**
     * Gets statistics os by user by period.
     *
     * @param shortUrls the short urls by user
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics os by user by period
     */
    List getStatisticsOsByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate);

    /**
     * Sets mongo provider.
     *
     * @param mongo operation implementation
     */
    void setMongo(MongoOperations mongo);
}