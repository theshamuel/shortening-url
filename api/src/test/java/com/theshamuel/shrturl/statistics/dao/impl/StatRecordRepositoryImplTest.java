package com.theshamuel.shrturl.statistics.dao.impl;

import com.theshamuel.shrturl.commons.base.dao.impl.BaseRepositoryImplTest;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.entity.StatRecord;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * The StatRecord repository implementation test. {@link StatRecordRepositoryImpl}
 *
 * @author Alex Gladkikh
 */
public class StatRecordRepositoryImplTest extends BaseRepositoryImplTest {

    /**
     * The StatRecord repository.
     */
    StatRecordRepositoryImpl statRecordRepository = new StatRecordRepositoryImpl();

    private StatRecord statRecord1Anonymous;
    private StatRecord statRecord2Anonymous;
    private StatRecord statRecord3Anonymous;

    private StatRecord statRecord1Admin;
    private StatRecord statRecord2Admin;

    /**
     * Test count by short url.
     */
    @Test
    public void testCountByShortUrl(){
        long actual = statRecordRepository.countByShortUrl("abc");
        assertThat(actual,is(3L));

        actual = statRecordRepository.countByShortUrl("nop");
        assertThat(actual,is(1L));

        actual = statRecordRepository.countByShortUrl("---");
        assertThat(actual,is(0L));
    };


    /**
     * Get all statistics by period.
     */
    @Test
    public void getAllStatisticsByPeriod(){
        List<StatRecordDto> actual = statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.size(),is(3));
        assertThat(actual.stream().map(i->i.getShortUrl()).collect(Collectors.toList()),hasItems(StatRecordDto.builder().shortUrl("abc").build().getShortUrl(),StatRecordDto.builder().shortUrl("nop").build().getShortUrl()));

        actual = statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.size(),is(3));
        assertThat(actual.stream().map(i->i.getTotalClicks()).collect(Collectors.toList()),hasItems(1L,3L));


        actual = statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600003L),new Date(1526763600004L));
        assertThat(actual.size(),is(2));
        assertThat(actual.stream().flatMap(i->i.getBrowser().stream()).map(y->y.getLabel()).collect(Collectors.toList()),hasItems("Safari 11"));

        actual = statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600003L),new Date(1526763600004L));
        assertThat(actual.size(),is(2));
        assertThat(actual.stream().flatMap(i->i.getCountry().stream()).map(y->y.getLabel()).collect(Collectors.toList()),hasItems("Russia","Ireland"));

        actual = statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600150L),new Date(1526763600200L));
        assertThat(actual.size(),is(0));

    };

    /**
     * Test get statistics by user by period.
     */
    @Test
    public void testGetStatisticsByUserByPeriod(){
        List<String> userUrls = new ArrayList<>();
        userUrls.add("abc");
        userUrls.add("qwe");
        List<StatRecordDto> actual = statRecordRepository.getStatisticsByUserByPeriod(userUrls,new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.size(),is(2));

        actual = statRecordRepository.getStatisticsByUserByPeriod(userUrls,new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.stream().filter(k->k.getShortUrl().contains("abc")).flatMap(i->i.getBrowser().stream()).map(y->y.getLabel()).collect(Collectors.toList()),hasItems("Chrome","Safari 11"));
    }

    /**
     * Test get statistics by short url by period.
     */
    @Test
    public void testGetStatisticsByShortUrlByPeriod(){
        StatRecordDto actual = statRecordRepository.getStatisticsByShortUrlByPeriod("abc234",new Date(1526763600000L),new Date(152676360002L));
        assertThat(actual,is(nullValue()));

        actual = statRecordRepository.getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526763600003L));
        assertThat(actual,is(notNullValue()));

        actual = statRecordRepository.getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526763600002L));
        assertThat(actual.getTotalClicks(),is(2L));
    };


    @Before
    @Override
    public void createTestRecords() {
        initCollection("statistics");
        template.findAllAndRemove(Query.query(Criteria.where("id").exists(true)),StatRecord.class);
        statRecord1Anonymous = StatRecord.builder().id("0").shortUrl("abc").createdDate(new Date(1526763600000L)).browser("Chrome").operationSystem("Ubuntu").country("Ireland").build();
        statRecord2Anonymous = StatRecord.builder().id("1").shortUrl("abc").createdDate(new Date(1526763600001L)).browser("Chrome").operationSystem("Windows 10").country("Russia").build();
        statRecord3Anonymous = StatRecord.builder().id("2").shortUrl("nop").createdDate(new Date(1526763600002L)).browser("Safari 11").operationSystem("Mac OS").country("Russia").build();
        statRecord1Admin = StatRecord.builder().id("3").shortUrl("abc").createdDate(new Date(1526763600003L)).browser("Safari 11").operationSystem("Mac OS").country("Russia").build();
        statRecord2Admin = StatRecord.builder().id("4").shortUrl("qwe").createdDate(new Date(1526763600004L)).browser("Safari 11").operationSystem("Mac OS").country("Ireland").build();
        template.save(statRecord1Anonymous);
        template.save(statRecord2Anonymous);
        template.save(statRecord3Anonymous);
        template.save(statRecord1Admin);
        template.save(statRecord2Admin);
    }

    @Override
    public void setMongo() {
        statRecordRepository.setMongo(template);
    }
}
