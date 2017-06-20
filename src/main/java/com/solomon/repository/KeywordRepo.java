package com.solomon.repository;

import com.solomon.domain.Keyword;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
@Repository
public interface KeywordRepo extends JpaRepository<Keyword, Long> {

    @Query("select k.keyword from Keyword k")
    List<String> findKeywordsById(Pageable pageable);

    @Query("select k.id from Keyword k where k.keyword = ?1")
    List<Long> findKeywordByName(String keyword);

}
