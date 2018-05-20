package com.theshamuel.shrturl.statistics.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatRecordServiceImpl implements StatRecordService{

    private ShortLinkRepository shortLinkRepository;

    private StatRecordRepository statRecordRepository;

    @Autowired
    public StatRecordServiceImpl(ShortLinkRepository shortLinkRepository, StatRecordRepository statRecordRepository) {
        this.shortLinkRepository = shortLinkRepository;
        this.statRecordRepository = statRecordRepository;
    }

    @Override
    public List getStatsByUserByPeriod(String userLogin, Date startDate, Date endDate) {
        List<String> userUrls = ((List<ShortLink>)shortLinkRepository.findShortUrlsByUser(userLogin, null)).stream().map(i->i.getShortUrl()).collect(Collectors.toList());
        return statRecordRepository.getStatisticsByUserByPeriod(userUrls,startDate,endDate);
    }
}
