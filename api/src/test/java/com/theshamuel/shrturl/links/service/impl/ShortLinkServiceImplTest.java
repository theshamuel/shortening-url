package com.theshamuel.shrturl.links.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.MockitoAnnotations.initMocks;

/**
 * The ShortLink service implementation tests. {@link ShortLinkServicesImpl}
 *
 * @author Alex Gladkikh
 */
public class ShortLinkServiceImplTest {
    @Mock
    private ShortLinkRepository shortLinkRepository;

    private ShortLinkService shortLinkService;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        initMocks(this);
        shortLinkService = new ShortLinkServicesImpl(shortLinkRepository);
    }

    /**
     * Test is short link service unique.
     */
    @Test
    public void testIsShortLinkServiceUnique(){}

    /**
     * Test find by short url.
     */
    @Test
    public void testFindByShortUrl(){}
}
