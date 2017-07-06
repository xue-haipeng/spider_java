package com.solomon.mapper;

import com.solomon.domain.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@Mapper
public interface QuestionMapper {

    @Insert("INSERT INTO spider_wx_question(title, question, answer, keyword, menu_id, author_id, weight, status, score, created_time, updated_time," +
            " published_time) VALUES (#{title}, #{question}, #{answer}, #{keyword}, #{menuId}, #{authorId}, #{weight}, #{status}, #{score}, #{createdTime}, " +
            "#{updatedTime}, #{publishedTime})")
    int insertByQuestion(Question question);

    @Select("SELECT * FROM spider_wx_question WHERE id = #{id}")
    Question findById(@Param("id") Long id);
}
