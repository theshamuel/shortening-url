package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.StatRecordController;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.dto.StatRecordDtoBuilder;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The StatRecord controller test. {@link StatRecordController}
 *
 * @author Alex Gladkikh
 */
public class StatRecordControllerTest {

    /**
     * The Stat record repository.
     */
    @Mock
    StatRecordRepository statRecordRepository;

    /**
     * The Stat record service.
     */
    @Mock
    StatRecordService statRecordService;

    /**
     * The Mock mvc.
     */
    MockMvc mockMvc;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new StatRecordController(statRecordRepository,statRecordService)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }

    /**
     * Test get statistics by short url by period.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetStatisticsByShortUrlByPeriod() throws Exception {
        StatRecordDto expected = new StatRecordDtoBuilder().shortUrl("abc").totalClicks(10L).build();
        when(statRecordRepository.getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526849999000L))).thenReturn(expected);
        mockMvc.perform(get("/api/v1/statistics/abc/2018-05-20T00:00:00/2018-05-20T23:59:59").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(6)))
                .andExpect(jsonPath( "$.totalClicks",is(10)))
                .andExpect(jsonPath( "$.shortUrl",containsString("abc")));
        verify(statRecordRepository,times(1)).getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526849999000L));

    }

    /**
     * Test get statistics by user by period.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetStatisticsByUserByPeriod() throws Exception {
        List<StatRecordDto> expectedList = new ArrayList<>();
        expectedList.add(new StatRecordDtoBuilder().shortUrl("abc").totalClicks(10L).build());
        expectedList.add(new StatRecordDtoBuilder().shortUrl("hjk").totalClicks(40L).build());
        when(statRecordService.getStatsByUserByPeriod("anonymous",new Date(1526763600000L),new Date(1526849999000L))).thenReturn(expectedList);
        mockMvc.perform(get("/api/v1/statistics/user/anonymous/2018-05-20T00:00:00/2018-05-20T23:59:59").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath( "$[0].totalClicks",is(10)))
                .andExpect(jsonPath( "$[1].shortUrl",containsString("hjk")));
        verify(statRecordService,times(1)).getStatsByUserByPeriod("anonymous",new Date(1526763600000L),new Date(1526849999000L));
    }

    /**
     * Test get all statistics by period.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetAllStatisticsByPeriod() throws Exception {
        List<StatRecordDto> expectedList = new ArrayList<>();
        expectedList.add(new StatRecordDtoBuilder().shortUrl("abc").totalClicks(10L).build());
        expectedList.add(new StatRecordDtoBuilder().shortUrl("efg").totalClicks(20L).build());
        when(statRecordRepository.getAllStatisticsByPeriod(new Date(1526763600000L),new Date(1526849999000L))).thenReturn(expectedList);
        mockMvc.perform(get("/api/v1/statistics/2018-05-20T00:00:00/2018-05-20T23:59:59").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims", TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath( "$[0].totalClicks",is(10)))
                .andExpect(jsonPath( "$[0].shortUrl",containsString("abc")))
                .andExpect(jsonPath( "$[1].totalClicks",is(20)))
                .andExpect(jsonPath( "$[1].shortUrl",containsString("efg")));
        verify(statRecordRepository,times(1)).getAllStatisticsByPeriod(new Date(1526763600000L),new Date(1526849999000L));

    }
}
