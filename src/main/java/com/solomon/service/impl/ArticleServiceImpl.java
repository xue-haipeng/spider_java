package com.solomon.service.impl;

import com.solomon.common.Constant;
import com.solomon.domain.Article;
import com.solomon.domain.ArticleForPost;
import com.solomon.domain.MongoArticle;
import com.solomon.mapper.ArticleMapper;
import com.solomon.repository.ArticleForPostRepo;
import com.solomon.repository.ArticleRepo;
import com.solomon.repository.mongo.MongoArticleRepo;
import com.solomon.service.ArticleService;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);  // counter for the number of articles sent to prd
    private static final AtomicInteger total = new AtomicInteger(0);  // total articles sent to prd of all thread sessions

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    MongoArticleRepo mongoArticleRepo;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    ArticleForPostRepo articleForPostRepo;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void insertArticle(Article article) {
        article.setCreatedTime(new Date());
        article.setUpdatedTime(new Date());
        article.setStatus(1);
        article.setScore(0);
        try {
//            articleRepo.save(article);   // Spring Data JPA
            articleMapper.insertByArticle(article);    // Mybatis
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sentToPrd(Article article) {
        restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
    }

    @Override
    public Map<String, Integer> sentToPrd(int startPage, int endPage) {
        IntStream.rangeClosed(startPage, endPage).forEach(i -> {
            System.out.println("-------------- 第 " + i + " 页 ------------------");
            Pageable pageable = new PageRequest(i,Constant.PAGE_SIZE_DB_QUERY, Constant.DB_ASC_SORT);
            List<ArticleForPost> articles = null;
            try {
                articles = articleForPostRepo.findAll(pageable).getContent();
            } catch (PersistenceException | JDBCConnectionException e) {
                try {
                    Thread.sleep(60_000);
                    articles = articleForPostRepo.findAll(pageable).getContent();
                } catch (InterruptedException | PersistenceException | JDBCConnectionException ie) {
                    try {
                        Thread.sleep(600_000);
                        articles = articleForPostRepo.findAll(pageable).getContent();
                    } catch (InterruptedException | PersistenceException | JDBCConnectionException ee) {
                        logger.error("Connect to Database failed after 2 times retry");
                        throw new RuntimeException(e);
                    }
                }
            }
            articles.stream().filter(article -> !article.getContent().equals("<p></p>")).forEach(article -> {
                try {
                    restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
                } catch (ResourceAccessException e) {
                    try {
                        Thread.sleep(60_000);  // wait 1 min to see if network connection is available
                        restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
                    } catch (InterruptedException | ResourceAccessException ie) {
                        try {
                            Thread.sleep(600_000);  // wait 10 min to see if network connection is available
                            restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
                        } catch (InterruptedException | ResourceAccessException ee) {
                            try {
                                Thread.sleep(900_000);  // wait 10 min to see if network connection is available
                                restTemplate.postForObject(Constant.ARTICLE_SEND_URL, article, ArticleForPost.class);
                            } catch (InterruptedException | ResourceAccessException ex) {
                                logger.error("Connection to man.wxlink.jd.com failed after retry 3 times");
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
                count.set(count.get() + 1);
                total.getAndIncrement();
                System.out.print(count.get() + ":" + article.getId() + " , ");
            });
            System.out.println();
        });
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("count", count.get());
        map.put("total", total.get());
        return map;
    }

    @Override
    public MongoArticle insertMongoArticle(MongoArticle mongoArticle) {
        return mongoArticleRepo.save(mongoArticle);
    }
}
