package com.solomon;

import com.solomon.domain.Question;
import com.solomon.repository.QuestionRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xuehaipeng on 2017/7/31.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionTests {

    @Autowired
    QuestionRepo questionRepo;

    @Test
    public void test1() {
        Question question = questionRepo.findOne(1L);
        System.out.println(question.getMenuId());
    }
}
