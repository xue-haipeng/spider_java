package com.solomon.service;

import com.solomon.domain.MongoQuestion;
import com.solomon.domain.Question;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
public interface QuestionService {
    void insertQuestion(Question question);
    void sentToPrd(Question question);
    MongoQuestion insertMongoQuestion(MongoQuestion mongoQuestion);
}
