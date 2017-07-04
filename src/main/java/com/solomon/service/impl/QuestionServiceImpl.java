package com.solomon.service.impl;

import com.solomon.domain.MongoQuestion;
import com.solomon.domain.Question;
import com.solomon.repository.QuestionRepo;
import com.solomon.repository.mongo.MongoQuestionRepo;
import com.solomon.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    MongoQuestionRepo mongoQuestionRepo;

    @Override
    public void insertQuestion(Question question) {
        question.setCreatedTime(new Date());
        question.setUpdatedTime(new Date());
        question.setStatus(1);
        question.setScore(0);
        try {
            questionRepo.save(question);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public MongoQuestion insertMongoQuestion(MongoQuestion mongoQuestion) {
        return mongoQuestionRepo.save(mongoQuestion);
    }
}
