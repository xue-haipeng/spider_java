package com.solomon.service.impl;

import com.solomon.domain.Article;
import com.solomon.service.ArticleService;
import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class LoggingDataServiceImpl implements LoggingDataService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingDataServiceImpl.class);

    @Autowired
    ArticleService articleService;

    @Override
    public void insertSinaArticle(String url, Long menuId) {
        logger.info("Fetching %s... {}", url);

        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        Element title = doc.select("h1#artibodyTitle").first();
/*        Element publish_date = doc.select("span#pub_date").first();
        Element content = doc.select("div#artibody").first();*/
        Element publish_date = doc.select("span#pub_date").first();
        if (publish_date == null) {
            publish_date = doc.select("span.time-source").first();
        }
//        Element publish_date = doc.select("span.time-source").first();
        Element content = doc.select("div#artibody").first();

        try {
//            content.children().last().remove();
            content.getElementsByTag("blockquote").first().remove();
            content.select("div.ct_hqimg").first().remove();
            content.children().last().remove();
        } catch (Exception e) {}

        String dateStr = publish_date.text().substring(0,10).replaceAll("[年|月]", "-");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = java.sql.Date.valueOf(dateStr);

        logger.info(title.text());
/*        System.out.println(date);
        System.out.println(content.html());*/

/*        Assert.notNull(title, "标题为空");
        Assert.notNull(publish_date, "发布日期为空");
        Assert.notNull(content, "内容为空");*/
        if (title == null) {
            System.out.println("标题为空");
            System.exit(0);
        }
        if (publish_date == null) {
            System.out.println("发布日期为空");
            System.exit(0);
        }
        if (content == null) {
            System.out.println("内容为空");
            System.exit(0);
        }
        Article article = new Article();
        article.setTitle(title.text());
        article.setPublishedTime(date);
        article.setContent(content.html());
        article.setMenuId(menuId);
        try {
//            articleService.insertArticle(article);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }
}
