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
package com.theshamuel.shrturl.statistics.dao.impl;

import com.mongodb.BasicDBObject;
import com.theshamuel.shrturl.statistics.dao.StatRecordOperations;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.dto.StatRecordDtoBuilder;
import com.theshamuel.shrturl.statistics.entity.StatRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * The StatRecord repository implementation class.
 *
 * @author Alex Gladkikh
 */
@Repository
public class StatRecordRepositoryImpl implements StatRecordOperations {
    private final static Logger logger =  LoggerFactory.getLogger(StatRecordRepositoryImpl.class);


    @Autowired
    private MongoOperations mongo;

    public long countByShortUrl(String shortUrl) {
        Query query = new Query();
        query.addCriteria(where("shortUrl").is(shortUrl));
        return mongo.count(query, StatRecord.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List getAllStatisticsByPeriod(Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap<>(getStatisticsCountryByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap<>(getStatisticsBrowserByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap<>(getStatisticsOsByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        return collectResult(resultByCountry,resultByBrowser,resultByOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List getStatisticsByUserByPeriod(List<String> userUrls, Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap<>(getStatisticsCountryByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap<>(getStatisticsBrowserByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap<>(getStatisticsOsByUserByPeriod(userUrls, startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        return collectResult(resultByCountry,resultByBrowser,resultByOS); }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatRecordDto getStatisticsByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap<>(getStatisticsCountryByShortUrlByPeriod(shortUrl,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap<>(getStatisticsBrowserByShortUrlByPeriod(shortUrl,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap<>(getStatisticsOsByShortUrlByPeriod(shortUrl, startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        List<StatRecordDto> tmpRes = collectResult(resultByCountry,resultByBrowser,resultByOS);
        return tmpRes.size()>0?tmpRes.get(0):null;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsCountryByPeriod(Date startDate, Date endDate) {
        Aggregation aggByCountry = newAggregation(match(new Criteria().andOperator(where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","country").count().as("totalClicks"),project("shortUrl","country","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$country")
                        .append("clicks", "$totalClicks" )
                ).as("country"),project("country").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByCountry, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsCountryByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate) {
        Aggregation aggByCountry = newAggregation(match(new Criteria().andOperator(where("shortUrl").is(shortUrl),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","country").count().as("totalClicks"),project("shortUrl","country","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$country")
                        .append("clicks", "$totalClicks" )
                ).as("country"),project("country").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByCountry, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsCountryByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate) {
        Aggregation aggByCountry = newAggregation(match(new Criteria().andOperator(where("shortUrl").in(shortUrls),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","country").count().as("totalClicks"),project("shortUrl","country","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$country")
                        .append("clicks", "$totalClicks" )
                ).as("country"),project("country").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByCountry, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsBrowserByPeriod(Date startDate, Date endDate) {
        Aggregation aggByBrowser = newAggregation(match(new Criteria().andOperator(where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","browser").count().as("totalClicks"),project("shortUrl","browser","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$browser")
                        .append("clicks", "$totalClicks" )
                ).as("browser"),project("browser").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByBrowser, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsBrowserByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate) {
        Aggregation aggByBrowser = newAggregation(match(new Criteria().andOperator(where("shortUrl").is(shortUrl),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","browser").count().as("totalClicks"),project("shortUrl","browser","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$browser")
                        .append("clicks", "$totalClicks" )
                ).as("browser"),project("browser").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByBrowser, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsBrowserByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate) {
        Aggregation aggByBrowser = newAggregation(match(new Criteria().andOperator(where("shortUrl").in(shortUrls),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","browser").count().as("totalClicks"),project("shortUrl","browser","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$browser")
                        .append("clicks", "$totalClicks" )
                ).as("browser"),project("browser").and("shortUrl").previousOperation());

        return mongo.aggregate(aggByBrowser, "statistics", StatRecordDto.class).getMappedResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsOsByPeriod(Date startDate, Date endDate) {
        Aggregation aggByOs = newAggregation(match(new Criteria().andOperator(where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","operationSystem").count().as("totalClicks"),project("shortUrl","operationSystem","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$operationSystem")
                        .append("clicks", "$totalClicks" )
                ).as("operationSystem"),project("operationSystem").and("shortUrl").previousOperation());
        return mongo.aggregate(aggByOs, "statistics", StatRecordDto.class).getMappedResults();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsOsByShortUrlByPeriod(String shortUrls, Date startDate, Date endDate) {
        Aggregation aggByOs = newAggregation(match(new Criteria().andOperator(where("shortUrl").is(shortUrls),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","operationSystem").count().as("totalClicks"),project("shortUrl","operationSystem","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$operationSystem")
                        .append("clicks", "$totalClicks" )
                ).as("operationSystem"),project("operationSystem").and("shortUrl").previousOperation());
        return mongo.aggregate(aggByOs, "statistics", StatRecordDto.class).getMappedResults();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StatRecordDto> getStatisticsOsByUserByPeriod(List<String> shortUrls, Date startDate, Date endDate) {
        Aggregation aggByOs = newAggregation(match(new Criteria().andOperator(where("shortUrl").in(shortUrls),where("createdDate").gte(startDate),where("createdDate").lte(endDate)))
                ,group("shortUrl","operationSystem").count().as("totalClicks"),project("shortUrl","operationSystem","totalClicks"),
                group("shortUrl").push(new BasicDBObject("label", "$operationSystem")
                        .append("clicks", "$totalClicks" )
                ).as("operationSystem"),project("operationSystem").and("shortUrl").previousOperation());
        return mongo.aggregate(aggByOs, "statistics", StatRecordDto.class).getMappedResults();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setMongo(MongoOperations mongo) {
        this.mongo = mongo;
    }

    private List <StatRecordDto> collectResult(Map<String,List> resultByCountry, Map<String,List>  resultByBrowser, Map<String,List>  resultByOS){
        List<StatRecordDto> result = new ArrayList<>();

        for (Map.Entry<String,List> entryCounty : resultByCountry.entrySet()){
            StatRecordDto tmp = new StatRecordDtoBuilder().shortUrl(entryCounty.getKey()).country(entryCounty.getValue()).build();
            if (resultByBrowser.containsKey(entryCounty.getKey())){
                tmp.setBrowser(resultByBrowser.get(entryCounty.getKey()));
                resultByBrowser.remove(entryCounty.getKey());
            }
            if (resultByOS.containsKey(entryCounty.getKey())){
                tmp.setOperationSystem(resultByOS.get(entryCounty.getKey()));
                resultByOS.remove(entryCounty.getKey());
            }
            tmp.setTotalClicks(tmp.getCountry().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }

        for (Map.Entry<String,List> entryBrowser : resultByBrowser.entrySet()){
            StatRecordDto tmp = new StatRecordDtoBuilder().shortUrl(entryBrowser.getKey()).browser(entryBrowser.getValue()).build();
            if (resultByCountry.containsKey(entryBrowser.getKey())){
                tmp.setCountry(resultByCountry.get(entryBrowser.getKey()));
                resultByCountry.remove(entryBrowser.getKey());
            }
            if (resultByOS.containsKey(entryBrowser.getKey())){
                tmp.setOperationSystem(resultByOS.get(entryBrowser.getKey()));
                resultByOS.remove(entryBrowser.getKey());
            }
            tmp.setTotalClicks(tmp.getBrowser().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }
        for (Map.Entry<String,List> entryOs : resultByOS.entrySet()){
            StatRecordDto tmp = new StatRecordDtoBuilder().shortUrl(entryOs.getKey()).operationSystem(entryOs.getValue()).build();
            if (resultByCountry.containsKey(entryOs.getKey())){
                tmp.setCountry(resultByCountry.get(entryOs.getKey()));
                resultByCountry.remove(entryOs.getKey());
            }
            if (resultByBrowser.containsKey(entryOs.getKey())){
                tmp.setBrowser(resultByBrowser.get(entryOs.getKey()));
                resultByBrowser.remove(entryOs.getKey());
            }
            tmp.setTotalClicks(tmp.getOperationSystem().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }
        return result;
    }


}
