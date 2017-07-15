package com.solomon;

import com.github.pagehelper.PageHelper;
import com.solomon.common.Constant;
import com.solomon.domain.ArticleForPost;
import com.solomon.mapper.ArticleMapper;
import com.solomon.repository.ArticleForPostRepo;
import com.solomon.repository.ArticleRepo;
import com.solomon.service.ArticleService;
import org.hibernate.exception.JDBCConnectionException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrdDataSendTest {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    ArticleForPostRepo articleForPostRepo;

    @Autowired
    ArticleService articleService;

    @Test
    public void testInsertPrd() {
        articleService.sentToPrd(2918, 10000);
    }

    @Test
    public void testInsertQat() {
        PageHelper.startPage(1, 10);
        Pageable pageable = new PageRequest(1,10, Constant.DB_ASC_SORT);

    }

}
