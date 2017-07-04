package com.solomon.repository.mongo;

import com.solomon.domain.MongoQuestion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Repository
public interface MongoQuestionRepo extends MongoRepository<MongoQuestion, String> {
}
