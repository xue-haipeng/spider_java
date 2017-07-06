package com.solomon.controller;

import com.solomon.config.RabbitMqConfig;
import com.solomon.domain.Keyword;
import com.solomon.vo.KeywordForm;
import com.solomon.domain.PageEntity;
import com.solomon.service.KeywordService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Controller
public class KeywordController {

    private final Sort sort = new Sort(Sort.Direction.ASC, "id");

    private final String QUERY_URL = "http://man.wxlink.jd.com/dataCollect/getKeywordList?pageSize={1}&pageNow={2}";
    private final String INSERT_URL = "http://man.wxlink.jd.com/dataCollect/secondaryKeyword";

    @Autowired
    KeywordService keywordService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
        PageEntity<LinkedHashMap> keywordPageEntity = this.restTemplate.getForObject(QUERY_URL.replace("{1}", Integer.toString(pageSize))
                .replace("{2}", Integer.toString(pageNow)), PageEntity.class);
        List<LinkedHashMap> keywords = keywordPageEntity.getDatas();
        List<String> keywordList = keywords.stream().map(kw -> (String)kw.get("keyword")).collect(Collectors.toList());
        int count = 0;
        keywordList.stream().skip(pageOffset).forEach(kw -> {
//            restTemplate.postForObject(INSERT_URL, kw, String.class);
            try {
                Thread.sleep(400L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rabbitTemplate.convertAndSend(RabbitMqConfig.queueName, "KW: " + kw);
        });
    }

    @PostMapping("/secondaryKeyword")
    public String secondaryKwPost(@Valid KeywordForm keywordForm) {
        PageEntity<LinkedHashMap> keywordPageEntity = this.restTemplate.getForObject(QUERY_URL.replace("{1}", "20")
                .replace("{2}", Integer.toString(keywordForm.getStartPage())), PageEntity.class);
        List<LinkedHashMap> keywords = keywordPageEntity.getDatas();
        List<String> keywordList = keywords.stream().map(kw -> (String)kw.get("keyword")).collect(Collectors.toList());
        int count = 0;
        keywordList.stream().skip(keywordForm.getPageOffset()).forEach(kw -> {
//            restTemplate.postForObject(INSERT_URL, kw, String.class);
            try {
                Thread.sleep(400L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            rabbitTemplate.convertAndSend(RabbitMqConfig.queueName, "KW: " + kw);
        });
        return "redirect:/";
    }

}
