package com.theshamuel.shrturl.statistics.service;

import com.theshamuel.shrturl.statistics.dto.StatRecordDto;

import java.util.Date;
import java.util.List;

/**
 * The interface Stat record service.
 *
 * @author Alex Gladkikh
 */
public interface StatRecordService {

    /**
     * Gets stats by user by period.
     *
     * @param userLogin the user login
     * @param startDate the start date
     * @param endDate   the end date
     * @return the stats by user by period
     */
    List getStatsByUserByPeriod(String userLogin, Date startDate, Date endDate);

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
}
