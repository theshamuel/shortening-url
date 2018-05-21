package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.ShortLinkController;
import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The ShortLink controller test. {@link ShortLinkController}
 *
 * @author Alex Gladkikh
 */
public class ShortLinkControllerTest {
    /**
     * The Short link service.
     */
    @Mock
    ShortLinkService shortLinkService;

    /**
     * The Short link repository.
     */
    @Mock
    ShortLinkRepository shortLinkRepository;
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
        mockMvc = MockMvcBuilders.standaloneSetup(new ShortLinkController(shortLinkService,shortLinkRepository)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }

    /**
     * Test create new short url.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testCreateNewShortUrl() throws Exception {
        final ShortLinkDto testShortLink = ShortLinkDto.builder().longUrl("http://google.com").build();

        ShortLinkDto resultShortLink = ShortLinkDto.builder().longUrl("http://google.com").build();

        when(shortLinkService.save(testShortLink)).thenReturn(resultShortLink);
        mockMvc.perform(post("/api/v1/links/").contentType(MediaType.APPLICATION_JSON_UTF8).content(TestUtils.convertObjectToJson(testShortLink)))
                .andExpect(status().isCreated());

        ArgumentCaptor<ShortLinkDto> linkCaptor = ArgumentCaptor.forClass(ShortLinkDto.class);
        verify(shortLinkService,times(1)).save(linkCaptor.capture());
    }

    /**
     * Test get all short url.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testGetAllShortUrl() throws Exception {
        List<ShortLinkDto> expectedList = new ArrayList<>();
        expectedList.add(ShortLinkDto.builder().shortUrl("abc").totalClicks(10L).build());
        when(shortLinkService.findAll()).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/links/").contentType(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(1)))
                .andExpect(jsonPath( "$[0].totalClicks",is(10)))
                .andExpect(jsonPath( "$[0].shortUrl",containsString("abc")));

        verify(shortLinkService,times(1)).findAll();
    }


    @Test
    public void testGetShortUrlByUser() throws Exception {
        List<ShortLinkDto> expectedList = new ArrayList<>();
        expectedList.add(ShortLinkDto.builder().shortUrl("abc").userLogin("anonymous").totalClicks(10L).build());
        expectedList.add(ShortLinkDto.builder().shortUrl("efg").userLogin("anonymous").totalClicks(20L).build());

        when(shortLinkRepository.findShortUrlsByUser("anonymous",null)).thenReturn(expectedList);

        mockMvc.perform(get("/api/v1/links/user/anonymous").contentType(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*",hasSize(2)))
                .andExpect(jsonPath( "$[0].totalClicks",is(10)))
                .andExpect(jsonPath( "$[0].shortUrl",containsString("abc")));

        verify(shortLinkRepository,times(1)).findShortUrlsByUser("anonymous",null);
    }

    /**
     * Test get all short url with invalid claims.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testGetAllShortUrlWithInvalidClaims() throws Exception {
        mockMvc.perform(get("/api/v1/links/").contentType(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getUserClaims()))
                .andExpect(status().isForbidden());
    }

    /**
     * Test delete short url not found.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteShortUrlNotFound() throws Exception {
        when(shortLinkRepository.findByShortUrl("12345678")).thenReturn(null);

        mockMvc.perform(delete("/api/v1/links/12345678").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isNotFound());

        verify(shortLinkRepository,never()).delete("12345678");
        verify(shortLinkRepository,times(1)).findByShortUrl("12345678");

    }

    /**
     * Test delete short url with invalid claims.
     *
     * @throws Exception the exception
     */
    @Test
    public void testDeleteShortUrlWithInvalidClaims() throws Exception {
        mockMvc.perform(delete("/api/v1/links/12345678").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getUserClaims()))
                .andExpect(status().isForbidden());

    }
}
