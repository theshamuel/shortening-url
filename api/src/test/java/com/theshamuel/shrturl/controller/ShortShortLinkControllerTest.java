package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.buiders.TestUtils;
import com.theshamuel.shrturl.controllers.ShortLinkController;
import com.theshamuel.shrturl.model.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.model.links.dto.ShortLinkDtoBuilder;
import com.theshamuel.shrturl.model.links.service.ShortLinkService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Short short link controller test.
 */
public class ShortShortLinkControllerTest {
    /**
     * The Short link service.
     */
    @Mock
    ShortLinkService shortLinkService;

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
        mockMvc = MockMvcBuilders.standaloneSetup(new ShortLinkController(shortLinkService)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }

    /**
     * Test create new short url.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testCreateNewShortUrl() throws Exception {
        final ShortLinkDto testShortLink = new ShortLinkDtoBuilder().longUrl("http://google.com").build();

        ShortLinkDto resultShortLink = new ShortLinkDtoBuilder().longUrl("http://google.com").build();

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
        mockMvc.perform(get("/api/v1/links/").contentType(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isOk());
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

    @Test
    public void testDeleteShortUrlNotFound() throws Exception {
        when(shortLinkService.findByShortUrl("12345678")).thenReturn(null);

        mockMvc.perform(delete("/api/v1/links/12345678").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getAdminClaims()))
                .andExpect(status().isNotFound());

        verify(shortLinkService,never()).delete("12345678");
        verify(shortLinkService,times(1)).findByShortUrl("12345678");

    }
    @Test
    public void testDeleteShortUrlWithInvalidClaims() throws Exception {
        mockMvc.perform(delete("/api/v1/links/12345678").accept(MediaType.APPLICATION_JSON_UTF8).requestAttr("claims",TestUtils.getUserClaims()))
                .andExpect(status().isForbidden());

    }
}
