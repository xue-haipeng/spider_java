package com.solomon.controller;

import com.solomon.domain.Keyword;
import com.solomon.service.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RestController
public class KeywordController {

    private final Sort sort = new Sort(Sort.Direction.ASC, "id");
    @Autowired
    KeywordService keywordService;

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

}
