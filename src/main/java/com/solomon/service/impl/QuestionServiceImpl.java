package com.solomon.service.impl;

import com.solomon.common.Constant;
import com.solomon.domain.ArticleForPost;
import com.solomon.domain.MongoQuestion;
import com.solomon.domain.Question;
import com.solomon.domain.QuestionForPost;
import com.solomon.mapper.QuestionMapper;
import com.solomon.repository.QuestionRepo;
import com.solomon.repository.mongo.MongoQuestionRepo;
import com.solomon.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionServiceImpl.class);
    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);  // counter for the number of questions sent to prd
    private static final AtomicInteger total = new AtomicInteger(0);  // total questions sent to prd of all thread sessions

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    MongoQuestionRepo mongoQuestionRepo;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void insertQuestion(Question question) {
        question.setCreatedTime(new Date());
        question.setUpdatedTime(new Date());
        question.setStatus(1);
        question.setScore(0);
        try {
//            questionRepo.save(question);   // Spring Data JPA Solution
            questionMapper.insertByQuestion(question);    // Mybatis Solution
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    public void sentToPrd(Question question) {
        restTemplate.postForObject(Constant.QUESTION_SEND_URL, question, QuestionForPost.class);
    }

    @Override
    public MongoQuestion insertMongoQuestion(MongoQuestion mongoQuestion) {
        return mongoQuestionRepo.save(mongoQuestion);
    }

    @Override
    public Map<String, Integer> sentToPrd(int startPage, int endPage) {
        IntStream.rangeClosed(startPage, endPage).forEach(i -> {
            System.out.println("........................ 第 " + i + " 页 ..........................");
            Pageable pageable = new PageRequest(i,Constant.PAGE_SIZE_DB_QUERY, Constant.DB_ASC_SORT);
            List<Question> questions = questionRepo.findAll(pageable).getContent();
            questions.stream().filter(q -> isValidQuestion(q)).forEach(question -> {
                restTemplate.postForObject(Constant.QUESTION_SEND_URL, question, QuestionForPost.class);
                count.set(count.get() + 1);
                total.getAndIncrement();
                System.out.print(count.get() + ":" + question.getId() + " , ");
            });
            System.out.println();
        });
        Map<String, Integer> map = new ConcurrentHashMap<>();
        map.put("count", count.get());
        map.put("total", total.get());
        return map;
    }

    private boolean isValidQuestion(Question question) {
        return question.getMenuId() != null && StringUtils.isNotBlank(question.getTitle())
                && StringUtils.isNotBlank(question.getQuestion()) && StringUtils.isNotBlank(question.getAnswer());
    }
}
