package com.solomon.service.impl;

import com.solomon.domain.*;
import com.solomon.service.ArticleService;
import com.solomon.service.LoggingDataService;
import com.solomon.service.QuestionService;
import com.solomon.vo.ArticleForm;
import com.solomon.vo.FormData;
import com.solomon.vo.QuestionForm;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

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

    @Autowired
    QuestionService questionService;

    private static ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);

    private static AtomicInteger total = new AtomicInteger(1);

    @Override
    public void insertArticleOrQuestion(FormData form, String url, String random) {

        Map<String, String> resultMap = fetchArticleOrQuestion(form, url);
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
        java.sql.Date date = java.sql.Date.valueOf(StringUtils.join(dateArr, "-"));

        if (form instanceof ArticleForm) {
            Article article = new Article();
            article.setTitle(resultMap.get("title"));
            article.setPublishedTime(date);
            article.setContent(resultMap.get("content"));
            article.setMenuId(form.getMenuId());
            article.setKeyword(resultMap.get("keyword"));
            try {
                articleService.insertArticle(article);
//                articleService.insertMongoArticle((MongoArticle) MongoConverter.entityToMongo(article));
                count.set(count.get() + 1);
                messagingTemplate.convertAndSend("/topic/progress/" + random, count.get() + ":" + total.getAndIncrement());
                System.out.println(article);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else if (form instanceof QuestionForm){
            Question question = new Question();
            question.setTitle(resultMap.get("title"));
            question.setPublishedTime(date);
            question.setQuestion(resultMap.get("question"));
            question.setAnswer(resultMap.get("answer"));
            question.setMenuId(form.getMenuId());
            question.setKeyword(resultMap.get("keyword"));
            try {
//                questionService.insertMongoQuestion((MongoQuestion) MongoConverter.entityToMongo(question));
                questionService.insertQuestion(question);
                count.set(count.get() + 1);
                messagingTemplate.convertAndSend("/topic/progress/" + random, count.get() + ":" + total.getAndIncrement());
                System.out.println(question);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    /**
     * 抓取文章元素原始值，返回<字符串形式的k,v>
     * @param form
     * @param url
     * @return
     */
    @Override
    public Map<String, String> fetchArticleOrQuestion(FormData form, String url) {
        logger.info("Fetching {} ... ", url);
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
        if (title == null && !StringUtils.isEmpty(form.getTitle2())) {
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
        Element content = null;
        Element question = null;
        Element answer = null;
        if (form instanceof ArticleForm) {
            ArticleForm articleForm = (ArticleForm) form;
            content = doc.select(articleForm.getContent()).first();
            if (content == null && StringUtils.isEmpty(articleForm.getContent2())) {
                content = doc.select(articleForm.getContent2()) == null ? null : doc.select(articleForm.getContent2()).first();
            }
            if (content == null) {
                throw new RuntimeException("内容为空");
            }
            if (articleForm.getExFirst() != null && articleForm.getExFirst()) {
                content.children().first().remove();
            }
            if (articleForm.getExLast() != null && articleForm.getExLast()) {
                content.children().last().remove();
            }
            if (!StringUtils.isEmpty(articleForm.getExcluded2())) {
                content.select(articleForm.getExcluded2()).first().remove();
            }
            resultMap.put("content", content.html());
        } else if (form instanceof QuestionForm) {
            QuestionForm questionForm = (QuestionForm) form;
            question = doc.select(questionForm.getQuestion()).first();
            if (question == null) {
                throw new RuntimeException("问题为空");
            }
            if (questionForm.getExFirst() != null && questionForm.getExFirst()) {
                question.children().first().remove();
            }
            if (questionForm.getExLast() != null && questionForm.getExLast()) {
                question.children().last().remove();
            }
            if (!StringUtils.isEmpty(questionForm.getExcluded2())) {
                question.select(questionForm.getExcluded2()).first().remove();
            }
            resultMap.put("question", question.html());
            answer = doc.select(questionForm.getAnswer()).first();
            if (answer != null) {
                if (answer.children().size() > 3) {
                    final int size = answer.children().size();
                    for (int i = 3; i < size; i ++) {
                        answer.children().last().remove();
                    }
//                    IntStream.range(4, answer.children().size()).forEach(i -> answer.children().last().remove());
                }
                resultMap.put("answer", answer.html());
            }
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
        String originDate = publish_date.text();
        logger.info(title.text());
        resultMap.put("title", title.text());
        resultMap.put("originDate", originDate);
        resultMap.put("keyword", keywordStr.toString());
        return resultMap;
    }

}
