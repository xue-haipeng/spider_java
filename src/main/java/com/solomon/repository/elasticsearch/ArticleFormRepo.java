package com.solomon.repository.elasticsearch;

import com.solomon.vo.ArticleForm;
import com.solomon.vo.FormData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@Repository
public interface ArticleFormRepo extends ElasticsearchRepository<ArticleForm, String> {
    List<ArticleForm> findByUrl(String url);
    List<ArticleForm> findByUrlStartingWith(String urlPrefix);

}
