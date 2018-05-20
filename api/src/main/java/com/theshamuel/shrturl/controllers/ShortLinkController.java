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

import com.theshamuel.shrturl.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.links.dto.ShortLinkDtoBuilder;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.utils.Utils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
     * Instantiates a new ShortLink controller.
     *
     * @param shortLinkServices the short link services
     */
    @Autowired
    public ShortLinkController(ShortLinkService shortLinkServices) {
        this.shortLinkServices = shortLinkServices;
    }


    /**
     * Create short URL and response result.
     *
     * @param linkDto the short link dto
     * @return the response entity
     */
    @PostMapping(value = "/links")
    public ResponseEntity<ShortLink> createLink(@RequestBody ShortLinkDto linkDto){
            if (linkDto !=null && linkDto.getLongUrl()!=null) {
                String url = null;
                if (linkDto.getLongUrl().toLowerCase().contains(".рф"))
                    url = Utils.getPunycodeForRfDomain(linkDto.getLongUrl());
                else
                    url = linkDto.getLongUrl();

                ShortLinkDto link = new ShortLinkDtoBuilder().longUrl(url).totalClicks(0L).createdDate(new Date()).build();

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
            return new ResponseEntity(shortLinkServices.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "createdDate"))), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
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
            ShortLink shortLink = shortLinkServices.findByShortUrl(shortUrl);
            if (shortLink != null) {
                shortLinkServices.delete(shortLink.getId());
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }


}
