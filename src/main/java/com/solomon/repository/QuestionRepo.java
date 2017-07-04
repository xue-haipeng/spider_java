package com.solomon.repository;

import com.solomon.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Repository
public interface QuestionRepo extends JpaRepository<Question, Long> {

}
