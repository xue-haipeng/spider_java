package com.solomon.repository;

import com.solomon.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Repository
public interface ArticleRepo extends ZixunRepo<Article> {

/*    @Query(nativeQuery = true, value = "INSERT INTO spider_wx_article(title, content, keyword, menu_id, author_id, weight, status, score," +
            "created_time, updated_time, published_time) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8, ?9, ?10, ?11)")
    void insert(String title, String content, String keyword, Long menu_id, Long author_id, Integer weight, Integer status, Integer score,
                Date created_time, Date updated_time, Date published_time);*/
}
