package com.solomon.controller;

import com.solomon.common.Constant;
import com.solomon.config.RabbitMqConfig;
import com.solomon.domain.ArticleForPost;
import com.solomon.domain.Keyword;
import com.solomon.repository.ArticleForPostRepo;
import com.solomon.vo.KeywordForm;
import com.solomon.domain.PageEntity;
import com.solomon.service.KeywordService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RestController
@RequestMapping("/kw")
public class KeywordController {

    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 1);

    @Autowired
    private KeywordService keywordService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ArticleForPostRepo articleForPostRepo;

    @GetMapping("/insertSecondaryKw")
    public void insertKeywords(int total) {
        final int totalPage = total/100 + 2;
        for (int i = 1; i < totalPage; i++) {
            Pageable pageable = new PageRequest(i,10, Constant.DB_ASC_SORT);
            List<String> keywords = keywordService.getKeywordList(pageable);
            keywords.forEach(kw -> {
                List<Keyword> keywordList = keywordService.findSecondaryKeywords(kw);
                keywordService.insertSecondaryKeywords(keywordList);
            });
        }
    }

    @ApiOperation(value = "二级关键词采集发送接口", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = true, dataType = "integer"),
            @ApiImplicitParam(name = "startPage", value = "起始页码", required = true, dataType = "integer")
    })
    @GetMapping("/secondaryKeyword")
    public void secondaryKw(@RequestParam int pageSize, @RequestParam int startPage) {
        PageEntity<LinkedHashMap> keywordPageEntity = this.restTemplate.getForObject(Constant.KEYWORD_QUERY_URL.replace("{1}", String.valueOf(pageSize))
                .replace("{2}", String.valueOf(startPage)), PageEntity.class);
        List<LinkedHashMap> keywords = keywordPageEntity.getDatas();
        List<String> keywordList = keywords.stream().map(kw -> (String)kw.get("keyword")).collect(Collectors.toList());
        keywordList.stream().forEach(kw -> {
            restTemplate.postForObject(Constant.SECONDARY_KW_INSERT_URL, kw, String.class);
//            rabbitTemplate.convertAndSend(RabbitMqConfig.queueName, "KW: " + kw);
        });
    }

    @ApiOperation(value = "二级关键词采集发送接口", notes = "根据Form采集发送二级关键词")
    @ApiImplicitParam(name = "keywordForm", value = "KeywordForm", required = true, dataType = "KeywordForm")
    @PostMapping("/secondaryKeyword")
    public String secondaryKwPost(@Valid KeywordForm keywordForm) {
        PageEntity<LinkedHashMap> keywordPageEntity = this.restTemplate.getForObject(Constant.KEYWORD_QUERY_URL.replace("{1}", "20")
                .replace("{2}", String.valueOf(keywordForm.getStartPage())), PageEntity.class);
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
