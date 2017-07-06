package com.solomon.service;

import com.solomon.domain.Article;
import com.solomon.domain.MongoArticle;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
public interface ArticleService {

    void insertArticle(Article article);

//    List<CompletableFuture<Article>> fetchArticleAsync(ArticleForm form);
    MongoArticle insertMongoArticle(MongoArticle mongoArticle);

}
