package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.StatRecordController;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.dto.StatRecordDtoBuilder;
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

public class StatRecordControllerTest {

    @Mock
    StatRecordRepository statRecordRepository;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new StatRecordController(statRecordRepository)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }

    @Test
    public void testGetStatisticsByShortUrlByPeriod() throws Exception {
        ///statistics/{shortUrl}/{startDate}/{endDate}
        List<StatRecordDto> expectedList = new ArrayList<>();
        expectedList.add(new StatRecordDtoBuilder().shortUrl("abc").totalClicks(10L).build());
        when(statRecordRepository.getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526849999000L))).thenReturn(expectedList);
        mockMvc.perform(get("/api/v1/statistics/abc/2018-05-20T00:00:00/2018-05-20T23:59:59").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath( "$[0].totalClicks",is(10)))
                .andExpect(jsonPath( "$[0].shortUrl",containsString("abc")));
        verify(statRecordRepository,times(1)).getStatisticsByShortUrlByPeriod("abc",new Date(1526763600000L),new Date(1526849999000L));

    }
    @Test
    public void testGetAllStatisticsByPeriod() throws Exception {
        ///statistics/{shortUrl}/{startDate}/{endDate}
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
