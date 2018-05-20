package com.theshamuel.shrturl.redirect.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.redirect.RedirectService;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * The type Redirect service implementation test.{@link RedirectServiceImpl}
 *
 * @author Alex Gladkikh
 */
public class RedirectServiceImplTest {

    /**
     * The Short link services.
     */
    @Mock
    ShortLinkService shortLinkServices;

    /**
     * The Short link reposiroty.
     */
    @Mock
    ShortLinkRepository shortLinkReposiroty;

    /**
     * The Stat record repository.
     */
    @Mock
    StatRecordRepository statRecordRepository;

    /**
     * The Redirect service.
     */
    RedirectService redirectService;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        initMocks(this);
        redirectService = new RedirectServiceImpl(shortLinkServices,shortLinkReposiroty,statRecordRepository);
    }

    /**
     * Test get redirect url.
     */
    @Test
    public void testGetRedirectUrl(){}


}
