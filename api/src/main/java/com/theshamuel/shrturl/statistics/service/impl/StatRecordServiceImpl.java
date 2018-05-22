package com.theshamuel.shrturl.statistics.service.impl;

import com.theshamuel.shrturl.links.dao.ShortLinkRepository;
import com.theshamuel.shrturl.links.entity.ShortLink;
import com.theshamuel.shrturl.statistics.dao.StatRecordRepository;
import com.theshamuel.shrturl.statistics.dto.StatRecordDto;
import com.theshamuel.shrturl.statistics.service.StatRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class StatRecordServiceImpl implements StatRecordService{

    private ShortLinkRepository shortLinkRepository;

    private StatRecordRepository statRecordRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List getAllStatisticsByPeriod(Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap((Map<String,List<StatRecordDto>>)statRecordRepository.getStatisticsCountryByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsBrowserByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap((Map<String,List<StatRecordDto>>)statRecordRepository.getStatisticsOsByPeriod(startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        return collectResult(resultByCountry,resultByBrowser,resultByOS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List getStatisticsByUserByPeriod(List<String> userUrls, Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsCountryByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsBrowserByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap((Map<String,List<StatRecordDto>>)statRecordRepository. getStatisticsOsByUserByPeriod(userUrls, startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        return collectResult(resultByCountry,resultByBrowser,resultByOS); }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatRecordDto getStatisticsByShortUrlByPeriod(String shortUrl, Date startDate, Date endDate) {

        Map<String,List> resultByCountry = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsCountryByShortUrlByPeriod(shortUrl,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsBrowserByShortUrlByPeriod(shortUrl,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap((Map<String,List<StatRecordDto>>)statRecordRepository.getStatisticsOsByShortUrlByPeriod(shortUrl, startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        List<StatRecordDto> tmpRes = collectResult(resultByCountry,resultByBrowser,resultByOS);
        return tmpRes.size()>0?tmpRes.get(0):null;

    }
    @Autowired
    public StatRecordServiceImpl(ShortLinkRepository shortLinkRepository, StatRecordRepository statRecordRepository) {
        this.shortLinkRepository = shortLinkRepository;
        this.statRecordRepository = statRecordRepository;
    }

    @Override
    public List getStatsByUserByPeriod(String userLogin, Date startDate, Date endDate) {
        List<String> userUrls = ((List<ShortLink>)shortLinkRepository.findShortUrlsByUser(userLogin, null)).stream().map(i->i.getShortUrl()).collect(Collectors.toList());

        Map<String,List> resultByCountry = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsCountryByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getCountry)));

        Map<String,List> resultByBrowser = new ConcurrentHashMap((Map<String,List<StatRecordDto>>) statRecordRepository.getStatisticsBrowserByUserByPeriod(userUrls,startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getBrowser)));

        Map<String,List> resultByOS = new ConcurrentHashMap((Map<String,List<StatRecordDto>>)statRecordRepository.getStatisticsOsByUserByPeriod(userUrls, startDate,endDate).stream().collect(Collectors.toMap(StatRecordDto::getShortUrl, StatRecordDto::getOperationSystem)));


        return collectResult(resultByCountry,resultByBrowser,resultByOS);
    }

    private List <StatRecordDto> collectResult(Map<String,List> resultByCountry, Map<String,List>  resultByBrowser, Map<String,List>  resultByOS){
        List<StatRecordDto> result = new ArrayList<>();

        for (Map.Entry<String,List> entryCounty : resultByCountry.entrySet()){
            StatRecordDto tmp = StatRecordDto.builder().shortUrl(entryCounty.getKey()).country(entryCounty.getValue()).build();
            if (resultByBrowser.containsKey(entryCounty.getKey())){
                tmp.setBrowser(resultByBrowser.get(entryCounty.getKey()));
                resultByBrowser.remove(entryCounty.getKey());
            }
            if (resultByOS.containsKey(entryCounty.getKey())){
                tmp.setOperationSystem(resultByOS.get(entryCounty.getKey()));
                resultByOS.remove(entryCounty.getKey());
            }
            tmp.setTotalClicks(tmp.getCountry().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }

        for (Map.Entry<String,List> entryBrowser : resultByBrowser.entrySet()){
            StatRecordDto tmp = StatRecordDto.builder().shortUrl(entryBrowser.getKey()).browser(entryBrowser.getValue()).build();
            if (resultByCountry.containsKey(entryBrowser.getKey())){
                tmp.setCountry(resultByCountry.get(entryBrowser.getKey()));
                resultByCountry.remove(entryBrowser.getKey());
            }
            if (resultByOS.containsKey(entryBrowser.getKey())){
                tmp.setOperationSystem(resultByOS.get(entryBrowser.getKey()));
                resultByOS.remove(entryBrowser.getKey());
            }
            tmp.setTotalClicks(tmp.getBrowser().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }
        for (Map.Entry<String,List> entryOs : resultByOS.entrySet()){
            StatRecordDto tmp = StatRecordDto.builder().shortUrl(entryOs.getKey()).operationSystem(entryOs.getValue()).build();
            if (resultByCountry.containsKey(entryOs.getKey())){
                tmp.setCountry(resultByCountry.get(entryOs.getKey()));
                resultByCountry.remove(entryOs.getKey());
            }
            if (resultByBrowser.containsKey(entryOs.getKey())){
                tmp.setBrowser(resultByBrowser.get(entryOs.getKey()));
                resultByBrowser.remove(entryOs.getKey());
            }
            tmp.setTotalClicks(tmp.getOperationSystem().stream().mapToLong(i->i.getClicks()).sum());
            result.add(tmp);
        }
        return result;
    }
}
