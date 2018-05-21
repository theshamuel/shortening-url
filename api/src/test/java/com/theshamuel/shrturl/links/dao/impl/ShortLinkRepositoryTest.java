package com.theshamuel.shrturl.links.dao.impl;

import com.theshamuel.shrturl.commons.base.dao.impl.BaseRepositoryImplTest;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.user.dao.impl.UserRepositoryImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * The ShortLink repository test. {@link UserRepositoryImpl}.
 *
 * @author Alex Gladkikh
 */
public class ShortLinkRepositoryTest extends BaseRepositoryImplTest {

    /**
     * The Short link repository.
     */
    ShortLinkRepositoryImpl shortLinkRepository = new ShortLinkRepositoryImpl();

    private ShortLink shortLink1Anonymous;
    private ShortLink shortLink2Anonymous;
    private ShortLink shortLink3Anonymous;

    private ShortLink shortLink1Admin;
    private ShortLink shortLink2Admin;


    /**
     * Test find by short url.
     */
    @Test
    public void testFindByShortUrl(){
        ShortLink actual = shortLinkRepository.findByShortUrl(shortLink1Admin.getShortUrl());
        assertThat(actual,is(shortLink1Admin));
    }

    /**
     * Test find by short url null.
     */
    @Test
    public void testFindByShortUrlNull(){
        ShortLink actual = shortLinkRepository.findByShortUrl("none");
        assertThat(actual,is(nullValue()));
    }

    /**
     * Test find short url by user.
     */
    @Test
    public void testFindShortUrlByUser(){
        List<ShortLink> actual = shortLinkRepository.findShortUrlsByUser(shortLink1Admin.getUserLogin(),null);
        assertThat(actual.size(),is(2));
        assertThat(actual,hasItems(shortLink1Admin,shortLink2Admin));

        actual = shortLinkRepository.findShortUrlsByUser(shortLink1Anonymous.getUserLogin(),null);
        assertThat(actual.size(),is(3));
        assertThat(actual,hasItems(shortLink1Anonymous,shortLink2Anonymous,shortLink3Anonymous));
    }

    /**
     * Test find short url by user null.
     */
    @Test
    public void testFindShortUrlByUserNull(){
        List<ShortLink> actual = shortLinkRepository.findShortUrlsByUser("none",null);
        assertThat(actual.size(),is(0));
    }

    @Before
    @Override
    public void createTestRecords() {
        initCollection("shortlinks");
        template.findAllAndRemove(Query.query(Criteria.where("id").exists(true)),ShortLink.class);
        shortLink1Anonymous = ShortLink.builder().id("0").shortUrl("abc").createdDate(new Date(1526763600000L)).longUrl("https://google.com").userLogin("anonymous").totalClicks(10L).build();
        shortLink2Anonymous = ShortLink.builder().id("1").shortUrl("dfg").createdDate(new Date(1526763600001L)).longUrl("https://yandex.com").userLogin("anonymous").totalClicks(11L).build();
        shortLink3Anonymous = ShortLink.builder().id("2").shortUrl("huj").createdDate(new Date(1526763600002L)).longUrl("https://reddit.com").userLogin("anonymous").totalClicks(12L).build();
        shortLink1Admin = ShortLink.builder().id("3").shortUrl("klm").createdDate(new Date(1526763600003L)).longUrl("https://google.com/123").userLogin("admin").totalClicks(20L).build();
        shortLink2Admin = ShortLink.builder().id("4").shortUrl("nop").createdDate(new Date(1526763600004L)).longUrl("https://yandex.com/4444").userLogin("admin").totalClicks(21L).build();
        template.save(shortLink1Anonymous);
        template.save(shortLink2Anonymous);
        template.save(shortLink3Anonymous);
        template.save(shortLink1Admin);
        template.save(shortLink2Admin);
    }

    @Override
    public void setMongo() {
        shortLinkRepository.setMongo(template);
    }
}
