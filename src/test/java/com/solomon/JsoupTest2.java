package com.solomon;

import com.solomon.domain.Article;
import com.solomon.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.IntStream;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
public class JsoupTest2 {
/*
    @Autowired
    ArticleService articleService;*/

    @Test
    public void test1() throws IOException {
        String url = "http://finance.sina.com.cn/money/forex/forexfxyc/2017-06-13/doc-ifyfzhac1846204.shtml";
        System.out.println("Fetching %s... " + url);

        Document doc = Jsoup.connect(url).get();
        Element title = doc.select("h1#artibodyTitle").first();
        Element publish_date = doc.select("span.time-source").first();
        Element content = doc.select("div#artibody").first();
        try {
//            content.children().first().remove();
            content.getElementsByTag("blockquote").first().remove();
            content.select("div.ct_hqimg").first().remove();
            content.children().last().remove();
        } catch (Exception e) {}

        String dateStr = publish_date.text().substring(0,10).replaceAll("[年|月]", "-");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = java.sql.Date.valueOf(dateStr);

        System.out.println(title.text());
        System.out.println(date);
        System.out.println(content.html());

        Article article = new Article();
        article.setTitle(title.text());
        article.setPublishedTime(date);
        article.setContent(content.html());
        article.setMenuId(11L);
//        articleService.insertArticle(article);
    }

    @Test
    public void test2() throws IOException {
        String url = "http://www.itjingyan.com/q_117915.html";
        Document doc = Jsoup.connect(url).get();
        Element answer = doc.select("div.mod-body.aw-feed-list").first();
        if (answer.children().size() > 4) {
            System.out.println("#########  " + answer.children().size() + "  *************");
            IntStream.range(4, answer.children().size()).forEach(i -> answer.children().last().remove());
        }
/*        for (int i = 5; i < answer.children().size(); i ++) {
            answer.children().last().remove();
            System.out.println("------------------ " + answer.children().size());
        }*/
        System.out.println("--------------  " + answer.children().size());
    }
}
