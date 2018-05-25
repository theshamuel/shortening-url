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

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.utils.Utils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * The ShortLink's controller class.
 *
 * @author Alex Gladkikh
 */
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ShortLinkController {
    private static Logger logger =  LoggerFactory.getLogger(ShortLinkController.class);

    /**
     * The ShortLink service.
     */
    ShortLinkService shortLinkServices;

    /**
     * The Short link repository.
     */
    ShortLinkRepository shortLinkRepository;

    /**
     * Gets claims.
     *
     * @param request the servlet request
     * @return the claims
     */
    @ModelAttribute("claims")
    public Claims getClaims(HttpServletRequest request) {
        return (Claims) request.getAttribute("claims");
    }

    /**
     * Instantiates a new Short link controller.
     *
     * @param shortLinkServices   the short link services
     * @param shortLinkRepository the short link repository
     */
    public ShortLinkController(ShortLinkService shortLinkServices, ShortLinkRepository shortLinkRepository) {
        this.shortLinkServices = shortLinkServices;
        this.shortLinkRepository = shortLinkRepository;
    }

    /**
     * Create short URL and response result.
     *
     * @param claims  the claims
     * @param linkDto the short link dto
     * @return the response entity
     */
    @PostMapping(value = "/links")
    public ResponseEntity<ShortLink> createLink(@ModelAttribute("claims") Claims claims, @RequestBody ShortLinkDto linkDto){
            if (linkDto !=null && linkDto.getLongUrl()!=null) {
                String url = null;
                if (linkDto.getLongUrl().toLowerCase().contains(".рф"))
                    url = Utils.getPunycodeForRfDomain(linkDto.getLongUrl());
                else
                    url = linkDto.getLongUrl();

                ShortLinkDto link = ShortLinkDto.builder().longUrl(url).totalClicks(0L).createdDate(new Date()).userLogin(claims!=null?claims.getSubject():"anonymous").build();

                return new ResponseEntity(shortLinkServices.save(link), HttpStatus.CREATED);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Gets all short links.
     *
     * @param claims the claims
     * @return the all short links
     */
    @GetMapping(value = "/links")
    public ResponseEntity<List> getAllShortLinks(@ModelAttribute("claims") Claims claims){
        if (claims.get("roles",String.class)!=null &&
                claims.get("roles",String.class).toLowerCase().equals("admin")) {
            return new ResponseEntity(shortLinkServices.findAll(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


    /**
     * Get short links by user response entity.
     *
     * @param userLogin the user login
     * @return the response entity
     */
    @GetMapping(value = "/links/user/{userLogin}")
    public ResponseEntity<List> getShortLinksByUser( @PathVariable String userLogin){
            return new ResponseEntity(shortLinkRepository.findShortUrlsByUser(userLogin,null), HttpStatus.OK);
    }


    /**
     * Delete short url response entity.
     *
     * @param claims   the claims
     * @param shortUrl the short url
     * @return the response entity with http status
     */
    @DeleteMapping(value = "/links/{shortUrl}")
    public ResponseEntity deleteShortUrl(@ModelAttribute("claims") Claims claims,
                                         @PathVariable String shortUrl){
        if (claims.get("roles",String.class)!=null &&
                claims.get("roles",String.class).toLowerCase().equals("admin")) {
            ShortLink shortLink = shortLinkRepository.findByShortUrl(shortUrl);
            if (shortLink != null) {
                shortLinkServices.delete(shortLink.getShortUrl());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


}
