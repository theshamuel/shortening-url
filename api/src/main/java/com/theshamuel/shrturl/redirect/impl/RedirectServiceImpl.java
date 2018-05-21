package com.theshamuel.shrturl.redirect.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.redirect.RedirectService;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.entity.StatRecord;
import eu.bitwalker.useragentutils.UserAgent;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * The Redirect service.
 *
 * @author Alex Gladkikh
 *
 */
@Service
public class RedirectServiceImpl implements RedirectService {

    private static Logger logger =  LoggerFactory.getLogger(RedirectServiceImpl.class);

    @Value("${country.ip.endpoint}")
    private String endpointCountryByIp;


    @Value("${context.front.end}")
    private String contextFrontEnd;

    @Autowired
    private Environment environment;

    /**
     * The ShortLink service.
     */
    ShortLinkService shortLinkServices;

    /**
     * The ShortLink repository.
     */
    ShortLinkRepository shortLinkRepository;

    /**
     * The StatRecord repository.
     */
    StatRecordRepository statRecordRepository;

    /**
     * Instantiates a new Redirect service.
     *
     * @param shortLinkServices    the short link services
     * @param shortLinkRepository  the short link reposiroty
     * @param statRecordRepository the stat record repository
     */
    @Autowired
    public RedirectServiceImpl(ShortLinkService shortLinkServices, ShortLinkRepository shortLinkRepository, StatRecordRepository statRecordRepository) {
        this.shortLinkServices = shortLinkServices;
        this.shortLinkRepository = shortLinkRepository;
        this.statRecordRepository = statRecordRepository;
    }

    /**
     * {@inheritDoc}
     */
    public String getRedirectUrl(String shortUrl, String strUserAgent, String remoteHost) {
        ShortLink shortLink = shortLinkRepository.findByShortUrl(shortUrl);
        String redirectUrl = shortUrl;
        if (shortLink != null) {
            String country = "unknown";
            if (endpointCountryByIp != null && endpointCountryByIp.trim().length() > 0) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseJSON = null;
                try {
                    responseJSON = restTemplate.getForEntity(endpointCountryByIp.concat("/").concat(remoteHost), String.class);
                    JSONObject jsonObject = new JSONObject(responseJSON.getBody());
                    if (jsonObject.get("status") != null && jsonObject.get("status").toString().toLowerCase().equals("success"))
                        country = (String) jsonObject.get("country");
                } catch (RestClientException e) {
                    logger.error("{} is not available", endpointCountryByIp);
                }
            }
            redirectUrl = shortLink.getLongUrl();
            String browser = "unknown";
            UserAgent userAgent = new UserAgent(strUserAgent);
            if (userAgent != null && userAgent.getBrowser() != null && userAgent.getBrowser().getName() != null)
                browser = userAgent.getBrowser().getName();
            String os = "unknown";
            if (userAgent != null && userAgent.getOperatingSystem() != null && userAgent.getOperatingSystem().getName() != null)
                os = userAgent.getOperatingSystem().getName();
            StatRecord statRecord = StatRecord.builder().shortUrl(shortLink.getShortUrl())
                    .createdDate(new Date())
                    .browser(browser)
                    .operationSystem(os)
                    .country(country)
                    .ipaddress(remoteHost)
                    .build();
            statRecordRepository.save(statRecord);
            shortLink.setTotalClicks(statRecordRepository.countByShortUrl(shortUrl));
            shortLinkRepository.save(shortLink);
            return redirectUrl;
        } else{
            if (contextFrontEnd!=null){
                StringBuilder sb = new StringBuilder("/").append(contextFrontEnd).append("/404.html");
                return sb.toString();
            }else
                return "/404.html";
        }
    }

}
