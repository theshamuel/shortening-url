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
package com.theshamuel.shrturl.controllers;

import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * The Link's controller class.
 *
 * @author Alex Gladkikh
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class StatRecordController {

    /**
     * The StatRecord repository.
     */
    StatRecordRepository statRecordRepository;

    /**
     * Instantiates a new Stat record controller.
     *
     * @param statRecordRepository the stat record repository
     */
    @Autowired
    public StatRecordController(StatRecordRepository statRecordRepository) {
        this.statRecordRepository = statRecordRepository;
    }

    /**
     * Gets statistics by short url by period.
     *
     * @param shortUrl  the short url
     * @param startDate the start date
     * @param endDate   the end date
     * @return the statistics by short url by period
     * @throws ServletException the servlet exceptions
     */
    @GetMapping(value = "/statistics/{shortUrl}/{startDate}/{endDate}")
    public ResponseEntity<List> getStatisticsByShortUrlByPeriod(
            @PathVariable String shortUrl,
            @PathVariable String startDate,
            @PathVariable String endDate) throws ServletException {
        List res = statRecordRepository.getStatisticsByShortUrlByPeriod(shortUrl, Date.from(LocalDateTime.parse(startDate).atZone(ZoneId.systemDefault()).toInstant()),Date.from(LocalDateTime.parse(endDate).atZone(ZoneId.systemDefault()).toInstant()));

        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    /**
     * Gets all statistics by period.
     *
     * @param startDate the start date
     * @param endDate   the end date
     * @return the all statistics by period
     * @throws ServletException the servlet exceptions
     */
    @GetMapping(value = "/statistics/{startDate}/{endDate}")
    public ResponseEntity<List> getAllStatisticsByPeriod(
            @PathVariable String startDate,
            @PathVariable String endDate) throws ServletException {
        List res = statRecordRepository.getAllStatisticsByPeriod(Date.from(LocalDateTime.parse(startDate).atZone(ZoneId.systemDefault()).toInstant()),Date.from(LocalDateTime.parse(endDate).atZone(ZoneId.systemDefault()).toInstant()));

        return new ResponseEntity<>(res,HttpStatus.OK);
    }
}
