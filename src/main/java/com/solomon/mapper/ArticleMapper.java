package com.solomon.mapper;

import com.solomon.domain.Article;
import com.solomon.domain.ArticleForPost;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/5.
 */
@Mapper
public interface ArticleMapper {

    @Insert("INSERT INTO spider_wx_article(title, content, keyword, menu_id, author_id, weight, status, score, created_time, updated_time," +
            " published_time) VALUES (#{title}, #{content}, #{keyword}, #{menuId}, #{authorId}, #{weight}, #{status}, #{score}, #{createdTime}, " +
            "#{updatedTime}, #{publishedTime})")
    int insertByArticle(Article article);

    @Select("SELECT * FROM spider_wx_article WHERE id = #{id}")
    Article findById(@Param("id") Long id);

    @Select("SELECT * FROM spider_wx_article")
    List<ArticleForPost> findAll();
}
