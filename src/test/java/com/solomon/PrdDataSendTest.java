package com.solomon;

import com.solomon.domain.Article;
import com.solomon.domain.Keyword;
import com.solomon.repository.ArticleRepo;
import com.solomon.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrdDataSendTest {

    RestTemplate restTemplate = new RestTemplate();
    final String URL = "http://man.wxlink.jd.com/dataCollect";
    final Sort sort = new Sort(Sort.Direction.ASC, "id");
    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 1);

    @Autowired
    ArticleRepo articleRepo;

    @Test
    public void testInsertPrd() {
        int totalPage = 1_200_000/10 +1;
        for (int i = 1; i < totalPage; i++) {   // zixun: i = 15_000
            System.out.println("第 " + i + " 页");
            Pageable pageable = new PageRequest(i,10, sort);
            List<Article> articles = articleRepo.findAll(pageable).getContent();

            articles.stream().filter(article -> !article.getContent().equals("<p></p>")).forEach(article -> {
                Article posted = restTemplate.postForObject(URL, article, Article.class);
                if (posted != null && posted.getId() != null) {
                    count.set(count.get() + 1);
                    System.out.println(count.get());
                }
            });
        }

    }
}
