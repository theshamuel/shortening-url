package com.theshamuel.shrturl.redirect.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.redirect.RedirectService;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
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
    ShortLinkRepository shortLinkRepository;

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
        redirectService = new RedirectServiceImpl(shortLinkServices,shortLinkRepository,statRecordRepository);
    }

    /**
     * Test get redirect url.
     */
    @Test
    public void testGetRedirectUrl(){

        ShortLink shortLink = ShortLink.builder().shortUrl("abc").longUrl("https://google.com").build();
        when(shortLinkRepository.findByShortUrl("abc")).thenReturn(shortLink);
        when(statRecordRepository.countByShortUrl("abc")).thenReturn(3L);
        when(shortLinkRepository.save(shortLink)).thenReturn(shortLink);
        String redirectUrl = redirectService.getRedirectUrl("abc","ua","locahost");
        assertThat(redirectUrl,is("https://google.com"));

        verify(shortLinkRepository,times(1)).findByShortUrl("abc");
        verify(statRecordRepository,times(1)).countByShortUrl("abc");
        verify(shortLinkRepository,times(1)).save(shortLink);


    }

    /**
     * Test get redirect url with 404 page.
     */
    @Test
    public void testGetRedirectUrl404(){
        ShortLink shortLink = ShortLink.builder().shortUrl("abc").longUrl("https://google.com").build();
        when(shortLinkRepository.findByShortUrl("abc1")).thenReturn(null);
        when(statRecordRepository.countByShortUrl("abc1")).thenReturn(3L);
        when(shortLinkRepository.save(shortLink)).thenReturn(shortLink);
        String redirectUrl = redirectService.getRedirectUrl("abc1","ua1","locahost");
        assertThat(redirectUrl,is("/404.html"));

        verify(shortLinkRepository,times(1)).findByShortUrl("abc1");
        verify(statRecordRepository,times(0)).countByShortUrl("abc1");
        verify(shortLinkRepository,times(0)).save(shortLink);
    }


}
