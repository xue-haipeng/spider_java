package com.solomon.controller;

import com.solomon.domain.Keyword;
import com.solomon.domain.PageEntity;
import com.solomon.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RestController
public class KeywordController {

    private final Sort sort = new Sort(Sort.Direction.ASC, "id");

    private final String URL = "http://man.wxlink.jd.com/dataCollect/getKeywordList?pageSize={1}&pageNow={2}";

    @Autowired
    KeywordService keywordService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/insertSecondaryKw")
    public void insertKeywords(int total) {
        final int totalPage = total/100 + 2;
        for (int i = 1; i < totalPage; i++) {
            Pageable pageable = new PageRequest(i,10, sort);
            List<String> keywords = keywordService.getKeywordList(pageable);
            keywords.forEach(kw -> {
                List<Keyword> keywordList = keywordService.findSecondaryKeywords(kw);
                keywordService.insertSecondaryKeywords(keywordList);
            });
        }
    }

    @GetMapping("/secondaryKeyword")
    public void secondaryKw(@RequestParam int pageSize, @RequestParam int pageNow, @RequestParam(value = "pageOffset", defaultValue = "0") int pageOffset) {
        PageEntity<LinkedHashMap> keywordPageEntity = this.restTemplate.getForObject(URL.replace("{1}", Integer.toString(pageSize))
                .replace("{2}", Integer.toString(pageNow)), PageEntity.class);
        List<LinkedHashMap> keywords = keywordPageEntity.getDatas();
        List<String> keywordList = keywords.stream().map(kw -> (String)kw.get("keyword")).collect(Collectors.toList());
        keywordList.stream().skip(pageOffset).forEach(System.out :: println);
    }

}
