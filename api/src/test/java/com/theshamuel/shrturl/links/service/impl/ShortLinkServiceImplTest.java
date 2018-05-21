package com.theshamuel.shrturl.links.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
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
    public void testIsShortLinkServiceUnique(){
        when(shortLinkRepository.findByShortUrl("abc")).thenReturn(null);

        boolean actual = shortLinkService.isShortLinkServiceUnique("abc");
        assertThat(actual,is(true));

        verify(shortLinkRepository,times(1)).findByShortUrl("abc");


        when(shortLinkRepository.findByShortUrl("abc1")).thenReturn(ShortLink.builder().build());

        actual = shortLinkService.isShortLinkServiceUnique("abc1");
        assertThat(actual,is(false));

        verify(shortLinkRepository,times(1)).findByShortUrl("abc1");
    }
}
