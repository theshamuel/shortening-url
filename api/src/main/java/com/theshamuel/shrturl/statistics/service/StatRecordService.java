package com.theshamuel.shrturl.statistics.service;

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
}
