package com.solomon.service.impl;

import com.solomon.domain.Article;
import com.solomon.repository.ArticleRepo;
import com.solomon.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Autowired
    ArticleRepo articleRepo;

    @Override
    public void insertArticle(Article article) {
        article.setCreatedTime(new Date());
        article.setUpdatedTime(new Date());
        article.setStatus(1);
        article.setScore(0);
        try {
            articleRepo.save(article);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}