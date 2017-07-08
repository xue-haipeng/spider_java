package com.solomon;

import com.solomon.repository.elasticsearch.ArticleFormRepo;
import com.solomon.vo.ArticleForm;
import com.solomon.vo.FormData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticSearchTests {

    @Autowired
    ArticleFormRepo articleFormRepo;

    @Test
    public void testEs() {
        FormData formData = new FormData();
        formData.setUrl("http://www.ofweek.com/CATList-8100-CHANGYIEXINWE-{}.html");
        formData.setMenuId(16L);
        formData.setExtractArea("div.list-left");
        formData.setStartIndex(1);
        formData.setEndIndex(31090);
        formData.setLinkPosition("a[title]");
        formData.setTitle("h1");
        formData.setPubDate1("span.sdate");
        formData.setType(0);
    }

    @Test
    public void testArticleEs() {
        ArticleForm formData = new ArticleForm();
        formData.setUrl("http://www.ofweek.com/abcdefg-{}.html");
        formData.setMenuId(16L);
        formData.setExtractArea("div.list-left");
        formData.setStartIndex(1);
        formData.setEndIndex(31090);
        formData.setLinkPosition("a[title]");
        formData.setTitle("h1");
        formData.setPubDate1("span.sdate");
        formData.setContent("div.content");
        formData.setType(0);
        articleFormRepo.save(formData);
        articleFormRepo.findAll().forEach(System.out::println);
//        System.out.println(formDataRepo.findOne("AV0XHo1gQuzSwRAkIXQd"));
    }

    @Test
    public void testQuery1() {
        List<ArticleForm> list = articleFormRepo.findByUrlStartingWith("http://www.ofweek.com");
        list.forEach(System.out :: println);
        System.out.println("-------------------------------------------------------");
        articleFormRepo.findAll().forEach(System.out :: println);
    }

}
