package com.theshamuel.shrturl.statistics.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.dto.Browser;
import com.theshamuel.shrturl.statistics.dto.Country;
import com.theshamuel.shrturl.statistics.dto.OperationSystem;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * The StatRecord service implementation test. {@link StatRecordServiceImpl}
 *
 * @author Alex Gladkikh
 */
public class StatRecordServiceImplTest {
    @Mock
    private ShortLinkRepository shortLinkRepository;

    @Mock
    private StatRecordRepository statRecordRepository;

    private StatRecordService statRecordService;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        initMocks(this);
        statRecordService = new StatRecordServiceImpl(shortLinkRepository,statRecordRepository);
    }

    /**
     * Test get stats by user by period.
     */
    @Test
    public void testGetStatsByUserByPeriod(){
        List<Country> countries1 = new ArrayList<>();
        countries1.add(new Country("Russia",20L));
        List<Browser> browsers1 = new ArrayList<>();
        browsers1.add(new Browser("Chrome",10L));
        browsers1.add(new Browser("Safari 11",10L));
        List<OperationSystem> os1 = new ArrayList<>();
        os1.add(new OperationSystem("Mac OS X",20L));

        List<Country> countries2 = new ArrayList<>();
        countries2.add(new Country("Ireland",5L));
        countries2.add(new Country("Sweden",5L));
        countries2.add(new Country("Russia",15L));
        List<Browser> browsers2 = new ArrayList<>();
        browsers2.add(new Browser("Chrome",10L));
        browsers2.add(new Browser("Safari 11",10L));
        List<OperationSystem> os2 = new ArrayList<>();
        os2.add(new OperationSystem("Mac OS X",20L));

        List<ShortLink> userUrls = new ArrayList<>();
        userUrls.add(ShortLink.builder().shortUrl("abc").build());
        userUrls.add(ShortLink.builder().shortUrl("qwe").build());

        List<String> userUrlsStr = new ArrayList<>();
        userUrlsStr.add("abc");
        userUrlsStr.add("qwe");

        List<StatRecordDto> statRecordDtoList = new ArrayList<>();
        statRecordDtoList.add(StatRecordDto.builder().shortUrl("abc").browser(browsers1).operationSystem(os1).country(countries1).build());
        statRecordDtoList.add(StatRecordDto.builder().shortUrl("qwe").browser(browsers1).operationSystem(os1).country(countries1).build());

        when(shortLinkRepository.findShortUrlsByUser("anonymous",null)).thenReturn(userUrls);
        when(statRecordRepository.getStatisticsCountryByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);
        when(statRecordRepository.getStatisticsBrowserByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);
        when(statRecordRepository.getStatisticsOsByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);

        List<StatRecordDto> actual = statRecordService.getStatsByUserByPeriod("anonymous",new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.size(),is(2));

        assertThat(actual.stream().map(i->i.getShortUrl()).collect(Collectors.toList()),hasItems(StatRecordDto.builder().shortUrl("localhost/abc").build().getShortUrl(),StatRecordDto.builder().shortUrl("localhost/qwe").build().getShortUrl()));

        verify(shortLinkRepository,times(1)).findShortUrlsByUser("anonymous",null);
        verify(statRecordRepository,times(1)).getStatisticsCountryByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsBrowserByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsOsByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L));
    }
    /**
     * Get all statistics by period.
     */
    @Test
    public void getAllStatisticsByPeriod(){
        List<Country> countries2 = new ArrayList<>();
        countries2.add(new Country("Ireland",5L));
        countries2.add(new Country("Sweden",5L));
        countries2.add(new Country("Russia",15L));
        List<Browser> browsers2 = new ArrayList<>();
        browsers2.add(new Browser("Chrome",10L));
        browsers2.add(new Browser("Safari 11",10L));
        List<OperationSystem> os2 = new ArrayList<>();
        os2.add(new OperationSystem("Mac OS X",20L));

        List<ShortLink> userUrls = new ArrayList<>();
        userUrls.add(ShortLink.builder().shortUrl("abc").build());
        userUrls.add(ShortLink.builder().shortUrl("qwe").build());

        List<StatRecordDto> statRecordDtoList = new ArrayList<>();
        statRecordDtoList.add(StatRecordDto.builder().shortUrl("abc").browser(browsers2).operationSystem(os2).country(countries2).build());
        statRecordDtoList.add(StatRecordDto.builder().shortUrl("qwe").browser(browsers2).operationSystem(os2).country(countries2).build());

        when(statRecordRepository.getStatisticsCountryByPeriod(new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);
        when(statRecordRepository.getStatisticsBrowserByPeriod(new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);
        when(statRecordRepository.getStatisticsOsByPeriod(new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);


        List<StatRecordDto> actual = statRecordService.getAllStatisticsByPeriod(new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual.size(),is(2));
        assertThat(actual.stream().map(i->i.getShortUrl()).collect(Collectors.toList()),hasItems(StatRecordDto.builder().shortUrl("localhost/abc").build().getShortUrl(),StatRecordDto.builder().shortUrl("localhost/qwe").build().getShortUrl()));

        actual = statRecordService.getAllStatisticsByPeriod(new Date(1526763600150L),new Date(1526763600200L));
        assertThat(actual.size(),is(0));

        verify(statRecordRepository,times(1)).getStatisticsCountryByPeriod(new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsBrowserByPeriod(new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsOsByPeriod(new Date(1526763600000L),new Date(1526763600010L));

        verify(statRecordRepository,times(1)).getStatisticsCountryByPeriod(new Date(1526763600150L),new Date(1526763600200L));
        verify(statRecordRepository,times(1)).getStatisticsBrowserByPeriod(new Date(1526763600150L),new Date(1526763600200L));
        verify(statRecordRepository,times(1)).getStatisticsOsByPeriod(new Date(1526763600150L),new Date(1526763600200L));

    };

    /**
     * Test get statistics by short url by period.
     */
    @Test
    public void testGetStatisticsByShortUrlByPeriod(){
        List<Country> countries1 = new ArrayList<>();
        countries1.add(new Country("Russia",20L));
        List<Browser> browsers1 = new ArrayList<>();
        browsers1.add(new Browser("Chrome",10L));
        browsers1.add(new Browser("Safari 11",10L));
        List<OperationSystem> os1 = new ArrayList<>();
        os1.add(new OperationSystem("Mac OS X",20L));

        List<Country> countries2 = new ArrayList<>();
        countries2.add(new Country("Ireland",5L));
        countries2.add(new Country("Sweden",5L));
        countries2.add(new Country("Russia",15L));
        List<Browser> browsers2 = new ArrayList<>();
        browsers2.add(new Browser("Chrome",10L));
        browsers2.add(new Browser("Safari 11",10L));
        List<OperationSystem> os2 = new ArrayList<>();
        os2.add(new OperationSystem("Mac OS X",20L));

        List<ShortLink> userUrls = new ArrayList<>();
        userUrls.add(ShortLink.builder().shortUrl("abc").build());
        userUrls.add(ShortLink.builder().shortUrl("qwe").build());

        List<String> userUrlsStr = new ArrayList<>();
        userUrlsStr.add("abc");
        userUrlsStr.add("qwe");

        StatRecordDto statRecordDtoAbc = StatRecordDto.builder().shortUrl("abc").browser(browsers1).operationSystem(os1).country(countries1).build();
        StatRecordDto statRecordDtoQwe = StatRecordDto.builder().shortUrl("qwe").browser(browsers2).operationSystem(os2).country(countries2).build();

        List resAbc = new ArrayList();
        resAbc.add(statRecordDtoAbc);

        List resQwe = new ArrayList();
        resQwe.add(statRecordDtoQwe);

        when(statRecordRepository.getStatisticsCountryByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L))).thenReturn(resQwe);
        when(statRecordRepository.getStatisticsBrowserByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L))).thenReturn(resQwe);
        when(statRecordRepository.getStatisticsOsByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L))).thenReturn(resQwe);

        StatRecordDto actual = statRecordService.getStatisticsByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L));

        assertThat(actual.getTotalClicks(),is(25L));

        verify(statRecordRepository,times(1)).getStatisticsCountryByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsBrowserByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L));
        verify(statRecordRepository,times(1)).getStatisticsOsByShortUrlByPeriod("qwe",new Date(1526763600000L),new Date(1526763600010L));

    };


}
