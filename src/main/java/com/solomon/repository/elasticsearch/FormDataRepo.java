package com.solomon.repository.elasticsearch;

import com.solomon.vo.FormData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
public interface FormDataRepo extends ElasticsearchRepository<FormData, String> {
    List<FormData> findByUrl(String url);
}
