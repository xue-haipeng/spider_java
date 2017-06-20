package com.solomon.service;

import com.solomon.domain.Keyword;

import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
public interface KeywordService {

    List<String> getKeywordList(Pageable pageable);

    List<Keyword> findSecondaryKeywords(String keywords);

    void insertSecondaryKeywords(List<Keyword> secondaryKeywords);

}
