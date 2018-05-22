package com.theshamuel.shrturl.statistics.dao.impl;

import com.theshamuel.shrturl.commons.base.dao.impl.BaseRepositoryImplTest;
import com.theshamuel.shrturl.statistics.entity.StatRecord;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;

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
