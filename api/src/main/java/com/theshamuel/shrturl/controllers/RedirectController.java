/**
 * This project is a project which provide API for getting short url by long URI.
 *
 * Here is short description: ( for more detailed description please read README.md or
 * go to https://github.com/theshamuel/shortening-url )
 *
 * Back-end: Spring (Spring Boot, Spring IoC, Spring Data, Spring Test), JWT library, Java8
 * DB: MongoDB
 * Tools: git,maven,docker.
 *
 */
package com.theshamuel.shrturl.controllers;

import com.theshamuel.shrturl.redirect.RedirectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The Redirect controller.
 *
 * @author Alex Gladkikh
 */
@RestController
@RequestMapping(value = "/")
public class RedirectController {
    private static Logger logger =  LoggerFactory.getLogger(UserController.class);

    /**
     * The Redirect service.
     */
    @Autowired
    RedirectService redirectService;

    /**
     * Instantiates a new Redirect controller.
     *
     * @param redirectService the redirect service
     */
    @Autowired
    public RedirectController(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    /**
     * Redirect to long url.
     * The outcome is redirecting to new page or 404.html in case whe long URL has not found.
     *
     * @param shortUrl the short url
     * @param request  the request
     * @param response the response
     */
    @GetMapping(value = "/{shortUrl}")
    public void redirectToLongUrl(@PathVariable String shortUrl, HttpServletRequest request, HttpServletResponse response) {
        String remoteHost = request.getHeader("X-Forwarded-For");
        String redirectUrl = redirectService.getRedirectUrl(shortUrl,request.getHeader("User-Agent"),remoteHost);
        try {
            response.setCharacterEncoding("UTF-8");
            response.sendRedirect(redirectUrl);
        } catch (IOException e) {
            logger.error("The redirect was fail\n{}", redirectUrl, e);
        }
    }
}
