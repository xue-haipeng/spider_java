package com.solomon.repository;

import com.solomon.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {

}
