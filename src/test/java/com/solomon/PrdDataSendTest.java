package com.solomon;

import com.github.pagehelper.PageHelper;
import com.solomon.common.Constant;
import com.solomon.domain.ArticleForPost;
import com.solomon.mapper.ArticleMapper;
import com.solomon.repository.ArticleForPostRepo;
import com.solomon.repository.ArticleRepo;
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
    ArticleMapper articleMapper;

    @Autowired
    ArticleForPostRepo articleForPostRepo;

    @Test
    public void testInsertPrd() {
        int totalPage = 1_500_000/10 +1;
        for (int i = 2788; i < totalPage; i++) {   // int i = 1885;  2153
            System.out.println("-------------- 第 " + i + " 页 ------------------");
            Pageable pageable = new PageRequest(i,100, sort);
            List<ArticleForPost> articles = articleForPostRepo.findAll(pageable).getContent();
            articles.forEach(article -> restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class));

            articles.stream().filter(article -> !article.getContent().equals("<p></p>")).forEach(article -> {
                restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
                count.set(count.get() + 1);
                System.out.print(count.get() + ":" + article.getId() + " , ");
            });
            System.out.println();
        }

    }


    @Test
    public void testInsertQat() {
        PageHelper.startPage(1, 10);
        Pageable pageable = new PageRequest(1,10, sort);

    }


}
