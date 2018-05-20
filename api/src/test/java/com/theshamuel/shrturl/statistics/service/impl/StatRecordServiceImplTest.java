package com.theshamuel.shrturl.statistics.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

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
        //public List getStatsByUserByPeriod(String userLogin, Date startDate, Date endDate)
    }

}
