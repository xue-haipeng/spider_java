package com.solomon;

import com.github.pagehelper.PageHelper;
import com.solomon.domain.Article;
import com.solomon.domain.ArticleForPost;
import com.solomon.vo.FormData;
import com.solomon.mapper.ArticleMapper;
import com.solomon.repository.ArticleForPostRepo;
import com.solomon.repository.ArticleRepo;
import com.solomon.repository.elasticsearch.FormDataRepo;
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

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleForPostRepo articleForPostRepo;

    @Test
    public void testInsertPrd() {
        int totalPage = 20/10 +1;
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


    @Test
    public void testInsertQat() {
        PageHelper.startPage(1, 10);
        Pageable pageable = new PageRequest(1,10, sort);
        List<ArticleForPost> articles = articleForPostRepo.findAll(pageable).getContent();
        articles.forEach(article -> restTemplate.postForObject(URL, article, ArticleForPost.class));
    }

    @Autowired
    FormDataRepo formDataRepo;

    @Test
    public void testEs() {
/*        FormData formData = new FormData();
        formData.setUrl("http://www.ofweek.com/CATList-8100-CHANGYIEXINWE-{}.html");
        formData.setMenuId(16L);
        formData.setExtractArea("div.list-left");
        formData.setStartIndex(1);
        formData.setEndIndex(31090);
        formData.setLinkPosition("a[title]");
        formData.setTitle("h1");
        formData.setPubDate1("span.sdate");
        formData.setType(0);
        formDataRepo.save(formData);*/
//        formDataRepo.findAll().forEach(System.out::println);
        System.out.println(formDataRepo.findOne("AV0XHo1gQuzSwRAkIXQd"));
    }
}
