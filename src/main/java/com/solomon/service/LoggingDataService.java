package com.solomon.service;

import com.solomon.domain.ArticleForm;

import java.util.Map;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
public interface LoggingDataService {

    void insertWebArticle(ArticleForm articleForm, String url, String random);
    Map<String, String> fetchArticle(ArticleForm form, String url);
}
