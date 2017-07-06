package com.solomon.repository;

import com.solomon.domain.ArticleForPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@Repository
public interface ArticleForPostRepo extends JpaRepository<ArticleForPost, Long> {
}
