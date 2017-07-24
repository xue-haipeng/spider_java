package com.solomon.repository.elasticsearch;

import com.solomon.vo.QuestionForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@Repository
public interface QuestionFormEsRepo extends ElasticsearchRepository<QuestionForm, String> {
    List<QuestionForm> findByUrl(String url);

    List<QuestionForm> findByUrlStartingWith(String url);
}
