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
package com.theshamuel.shrturl.links.service.impl;

import com.theshamuel.shrturl.baseclasses.service.BaseServiceImpl;
import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.dto.ShortLinkDto;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.links.service.ShortLinkService;
import com.theshamuel.shrturl.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * The ShortLink services implementation class.\
 *
 * @author Alex Gladkikh
 */
@Service
public class ShortLinkServicesImpl extends BaseServiceImpl<ShortLinkDto,ShortLink> implements ShortLinkService {
    /**
     * The Environment variables.
     */
    @Autowired
    Environment environment;

    @Autowired
    private ShortLinkRepository shortLinkRepository;


    /**
     * Instantiates a new ShortLink service.
     *
     * @param shortLinkRepository the mongo repository
     */
    public ShortLinkServicesImpl(ShortLinkRepository shortLinkRepository) {
        super(shortLinkRepository);
        this.shortLinkRepository = shortLinkRepository;
    }

    @Override
    public ShortLinkDto save(ShortLinkDto dto) {
        if (dto.getShortUrl()==null){
            String newShortUrl = null;
            while (true) {
                newShortUrl = Utils.generateShortUrlSeq(8);
                if (isShortLinkServiceUnique(newShortUrl))
                    break;
            }
            dto.setShortUrl(newShortUrl);
        }
        return super.save(dto);
    }

    @Override
    public ShortLinkDto obj2dto(ShortLink obj) {
        StringBuilder domainName = new StringBuilder("http://localhost:82");
        if (1==1 || environment.getProperty("DOMAIN")!=null && environment.getProperty("DOMAIN").toString().trim().length()>0) {
            //domainName.append(environment.getProperty("DOMAIN"));
            domainName.append("/");
            domainName.append(obj.getShortUrl());
            ShortLinkDto linkDto = new ShortLinkDto();
            linkDto.setId(obj.getId());
            linkDto.setAuthor(obj.getAuthor());
            linkDto.setCreatedDate(obj.getCreatedDate());
            linkDto.setModifyDate(obj.getModifyDate());
            linkDto.setLongUrl(obj.getLongUrl());
            linkDto.setShortUrl(domainName.toString());
            linkDto.setTotalClicks(obj.getTotalClicks());
            return linkDto;
        }else {
            throw new RuntimeException("Not found DOMAIN intro Environment");
        }
    }

    @Override
    public ShortLink dto2obj(ShortLinkDto dto) {
        if (dto.getShortUrl()!=null)
            dto.setShortUrl(dto.getShortUrl().substring(dto.getShortUrl().lastIndexOf("/")+1,dto.getShortUrl().length()));
        return dto;
    }

    @Override
    public boolean isShortLinkServiceUnique(String shortUrl) {
        if (shortLinkRepository.findByShortUrl(shortUrl)!=null){
            return false;
        }
        return true;
    }

}
