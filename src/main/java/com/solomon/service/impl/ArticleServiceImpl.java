package com.solomon.service.impl;

import com.solomon.common.Constant;
import com.solomon.domain.Article;
import com.solomon.domain.ArticleForPost;
import com.solomon.domain.MongoArticle;
import com.solomon.mapper.ArticleMapper;
import com.solomon.repository.ArticleRepo;
import com.solomon.repository.mongo.MongoArticleRepo;
import com.solomon.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    MongoArticleRepo mongoArticleRepo;

    @Autowired
    ArticleMapper articleMapper;

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
    public MongoArticle insertMongoArticle(MongoArticle mongoArticle) {
        return mongoArticleRepo.save(mongoArticle);
    }
}
