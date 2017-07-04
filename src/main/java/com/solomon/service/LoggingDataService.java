package com.solomon.service;

import com.solomon.domain.ArticleForm;
import com.solomon.domain.FormData;

import java.util.Map;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
public interface LoggingDataService {

    void insertArticleOrQuestion(FormData form, String url, String random);
    Map<String, String> fetchArticleOrQuestion(FormData form, String url);
}
