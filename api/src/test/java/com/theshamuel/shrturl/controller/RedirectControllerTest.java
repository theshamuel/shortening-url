package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.commons.TestUtils;
import com.theshamuel.shrturl.controllers.RedirectController;
import com.theshamuel.shrturl.redirect.RedirectService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The Redirect controller tests.  {@link RedirectController}
 *
 * @author Alex Gladkikh
 */
public class RedirectControllerTest {

    /**
     * The Redirect service.
     */
    @Mock
    RedirectService redirectService;

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
        mockMvc = MockMvcBuilders.standaloneSetup(new RedirectController(redirectService)).setHandlerExceptionResolvers(TestUtils.createExceptionResolver()).build();
    }


    /**
     * Test redirect to long url.
     *
     * @throws Exception the exceptions
     */
    @Test
    public void testRedirectToLongUrl() throws Exception {
        String shortUrl = "123";
        String strUserAgent = "ua";

        when(redirectService.getRedirectUrl(shortUrl,strUserAgent,"88.24.22.89")).thenReturn("http://google.com");
        mockMvc.perform(get("/123").header("User-Agent","ua").header("X-Forwarded-For","88.24.22.89"))
                .andExpect(status().isFound());

        verify(redirectService,times(1)).getRedirectUrl(shortUrl,strUserAgent,"88.24.22.89");
    }

}
