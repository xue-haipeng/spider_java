package com.solomon;

import com.solomon.domain.Keyword;
import com.solomon.domain.PageEntity;
import com.solomon.service.KeywordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KeywordTest {

    final int TOTAL_KEYWORDS = 2;
    Sort sort = new Sort(Sort.Direction.ASC, "id");

    @Autowired
    KeywordService keywordService;

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testKeywords() {
        final int totalPage = TOTAL_KEYWORDS/100 + 2;
        for (int i = 1; i < totalPage; i++) {
            Pageable pageable = new PageRequest(i,10, sort);
            List<String> keywords = keywordService.getKeywordList(pageable);
            keywords.forEach(kw -> {
                List<Keyword> keywordList = keywordService.findSecondaryKeywords(kw);
                keywordService.insertSecondaryKeywords(keywordList);
            });
        }

    }

    final String URL = "http://man.wxlink.jd.com/dataCollect/getKeywordList?pageSize=20&pageNow=46145";
    @Test
    public void test() {
        PageEntity<LinkedHashMap> keywordPageEntity = restTemplate.getForObject(URL, PageEntity.class);
        List<LinkedHashMap> keywords = keywordPageEntity.getDatas();
        keywords.forEach(keyword -> System.out.println(keyword.get("keyword")));
    }
}
