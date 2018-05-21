package com.theshamuel.shrturl.statistics.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.dto.*;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        when(statRecordRepository.getStatisticsByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L))).thenReturn(statRecordDtoList);

        List<StatRecordDto> actual = statRecordService.getStatsByUserByPeriod("anonymous",new Date(1526763600000L),new Date(1526763600010L));
        assertThat(actual,is(statRecordDtoList));

        verify(shortLinkRepository,times(1)).findShortUrlsByUser("anonymous",null);
        verify(statRecordRepository,times(1)).getStatisticsByUserByPeriod(userUrlsStr,new Date(1526763600000L),new Date(1526763600010L));
    }

}
