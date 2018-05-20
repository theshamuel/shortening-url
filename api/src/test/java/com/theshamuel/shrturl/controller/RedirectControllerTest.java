package com.theshamuel.shrturl.controller;

import com.theshamuel.shrturl.buiders.TestUtils;
import com.theshamuel.shrturl.controllers.RedirectController;
import com.theshamuel.shrturl.model.statistics.service.RedirectService;
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
 * The type Redirect controller test.
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

        when(redirectService.getRedirectUrl(shortUrl,strUserAgent,"localhost")).thenReturn("http://google.com");
        mockMvc.perform(get("/123").header("User-Agent","ua"))
                .andExpect(status().isFound());

        verify(redirectService,times(1)).getRedirectUrl(shortUrl,strUserAgent,"localhost");
    }

}
