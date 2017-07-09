package com.solomon;

import com.solomon.domain.Article;
import com.solomon.repository.ArticleRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xuehaipeng on 2017/7/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JpaTests {

    @Autowired
    ArticleRepo articleRepo;

    @Test
    public void test1() {
        Article article = articleRepo.findOne(10000L);
        System.out.println("-------------  " + article);
    }

}
