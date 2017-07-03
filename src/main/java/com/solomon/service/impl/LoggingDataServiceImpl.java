package com.solomon.service.impl;

import com.solomon.domain.Article;
import com.solomon.domain.ArticleForm;
import com.solomon.service.ArticleService;
import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class LoggingDataServiceImpl implements LoggingDataService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingDataServiceImpl.class);

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @Autowired
    ArticleService articleService;

    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    private static AtomicInteger total = new AtomicInteger(1);

    @Override
    public void insertWebArticle(ArticleForm form, String url, String random) {

        Map<String, String> resultMap = fetchArticle(form, url);

        Pattern pattern = Pattern.compile("\\d{4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?");
        Matcher matcher = pattern.matcher(resultMap.get("originDate"));
        String dateStr = matcher.find() ? matcher.group(0) : null;
        String[] dateArr = dateStr.split("[-|\\/|年|月|日|号|\\.]");
        if (dateArr[1].length() == 1) {
            dateArr[1] = "0" + dateArr[1];
        }
        if (dateArr[2].length() == 1) {
            dateArr[2] = "0" + dateArr[2];
        }
        java.sql.Date date = java.sql.Date.valueOf(org.apache.commons.lang3.StringUtils.join(dateArr, "-"));

        Article article = new Article();
        article.setTitle(resultMap.get("title"));
        article.setPublishedTime(date);
        article.setContent(resultMap.get("content"));
        article.setMenuId(form.getMenuId());
        article.setKeyword(resultMap.get("keyword"));
        try {
            articleService.insertArticle(article);
            count.set(count.get() + 1);
            messagingTemplate.convertAndSend("/topic/progress/" + random, count.get() + ":" + total.getAndIncrement());
            System.out.println(article);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }

    /**
     * 抓取文章元素原始值，返回<字符串形式的k,v>
     * @param form
     * @param url
     * @return
     */
    @Override
    public Map<String, String> fetchArticle(ArticleForm form, String url) {
        logger.info("Fetching %s... {}", url);
        Map<String, String> resultMap = new ConcurrentHashMap<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(3000).get();
        } catch (Exception e) {
            try {
                doc = Jsoup.connect(url).timeout(10_000).get();
            } catch (IOException e1) {
                logger.error("页面无法访问：{}", url, e1);
            }
        }
        Element title = doc.select(form.getTitle()) == null ? null : doc.select(form.getTitle()).first();
        if (title == null && !org.apache.commons.lang3.StringUtils.isEmpty(form.getTitle2())) {
            title = doc.select(form.getTitle2()).first();
        }
        if (title == null) {
            throw new RuntimeException("标题为空");
        }
        Element publish_date = doc.select(form.getPubDate1()) == null ? null : doc.select(form.getPubDate1()).first();
        if (publish_date == null && !StringUtils.isEmpty(form.getPubDate2())) {
            publish_date = doc.select(form.getPubDate2()) == null ? null : doc.select(form.getPubDate2()).first();
        }
        if (publish_date == null && !StringUtils.isEmpty(form.getPubDate3())) {
            publish_date = doc.select(form.getPubDate3()) == null ? null : doc.select(form.getPubDate3()).first();
        }
        if (publish_date == null) {
            throw new RuntimeException("发布日期为空");
        }
        Element content = doc.select(form.getContent()).first();
        if (content == null && org.apache.commons.lang3.StringUtils.isEmpty(form.getContent2())) {
            content = doc.select(form.getContent2()) == null ? null : doc.select(form.getContent2()).first();
        }
        if (content == null) {
            throw new RuntimeException("内容为空");
        }
        Elements keywords = StringUtils.isEmpty(form.getKeyword()) ? null : doc.select(form.getKeyword());
        StringBuilder keywordStr = new StringBuilder("");
        if (keywords != null) {
            keywords.forEach(kw -> {
                if (keywordStr.length() > 0) {
                    keywordStr.append("," + kw.text());
                } else {
                    keywordStr.append(kw.text());
                }
            });
        }
        if (form.getExFirst() != null && form.getExFirst()) {
            content.children().first().remove();
        }
        if (form.getExLast() != null && form.getExLast()) {
            content.children().last().remove();
        }
        if (!StringUtils.isEmpty(form.getExcluded2())) {
            content.select(form.getExcluded2()).first().remove();
        }
        String originDate = publish_date.text();
        logger.info(title.text());
        resultMap.put("title", title.text());
        resultMap.put("originDate", originDate);
        resultMap.put("keyword", keywordStr.toString());
        resultMap.put("content", content.html());
        return resultMap;
    }
}
