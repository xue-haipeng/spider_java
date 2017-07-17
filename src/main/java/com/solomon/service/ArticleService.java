package com.solomon.service;

import com.solomon.domain.Article;
import com.solomon.domain.MongoArticle;

import java.util.Map;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
public interface ArticleService {

    void insertArticle(Article article);

    void sentToPrd(Article article);

    /**
     * send articles fetched from local or qat db to prd db
     * @param startPage
     * @param endPage
     * @return article quantity of the current thread session and the quantity of articles sent by all sessions
     */
    Map<String, Integer> sentToPrd(int startPage, int endPage);
//    List<CompletableFuture<Article>> fetchArticleAsync(ArticleForm form);
    MongoArticle insertMongoArticle(MongoArticle mongoArticle);

}
