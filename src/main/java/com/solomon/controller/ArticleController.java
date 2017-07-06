package com.solomon.controller;

import com.solomon.vo.ArticleForm;
import com.solomon.vo.FormData;
import com.solomon.vo.QuestionForm;
import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuehaipeng on 2017/6/23.
 */
@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
    private static Map<String, Integer> randomNums = new HashMap<>();   //  for storing random numbers of each browser

    @Autowired
    LoggingDataService loggingDataService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/article")
    public String articleForm(@Valid ArticleForm articleForm) {
        try {
            extractAndFetch(articleForm);
        } catch (InterruptedException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/article";
    }

    @PostMapping("/question")
    public String questionForm(@Valid QuestionForm form) {
        try {
            extractAndFetch(form);
        } catch (InterruptedException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return "redirect:/article";
    }

    @PostMapping("/testArticleFetch")
    @ResponseBody
    public Map<String, String> articleTest(ArticleForm articleForm) {
        return testFech(articleForm);
    }

    @PostMapping("/testQuestionFetch")
    @ResponseBody
    public Map<String, String> questionTest(QuestionForm questionForm) {
        return testFech(questionForm);
    }

    private Map<String, String> testFech(FormData form) {
        String url = form.getUrl().replace("{}", Integer.toString(form.getStartIndex()));
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(3000).get();
        } catch (Exception e) {
            logger.error("无法连接到网址： {}", e.getMessage(), e);
        }
        Element candidate = doc.select(form.getExtractArea()).first();
        Elements links = candidate.select(form.getLinkPosition());
        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+(com|cn|net|org|biz|info|cc|tv)");
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        Element hrefElement = links.first().toString().startsWith("<a ") ? links.first() : links.first().child(0);
        String href = hrefElement.attr("href").startsWith("http") ? hrefElement.attr("href") : matcher.group(0) + hrefElement.attr("href");
        if (href.endsWith("index.shtml") && links.first().nextElementSibling() != null) {
            href = links.first().nextElementSibling().attr("href");   // <li>标签含两个<a>
        }
        if (!href.startsWith("http")) {
            href = matcher.group(0) + "/" + href;
        }
        Map<String, String> resultMap = loggingDataService.fetchArticleOrQuestion(form, href);
        return resultMap;
    }

/*    @MessageMapping("/hello")
    @SendTo("/topic/progress")
    public Progress progress(Integer count) {
        return new Progress(count);
    }*/

    private void extractAndFetch(FormData form) throws InterruptedException, NoSuchAlgorithmException, IOException {
        System.out.println(form);
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(form.getUrl().getBytes());
        String md5key = new BigInteger(1, md5.digest()).toString(16);
        for (int i = form.getStartIndex(); i < form.getEndIndex(); i++) {
            logger.info("-------- 第 {}/{} 页 --------", i, form.getEndIndex());
            messagingTemplate.convertAndSend("/topic/pages/" + md5key, i + ":" + form.getEndIndex());
            String url = form.getUrl().replace("{}", Integer.toString(i));
            Document doc = null;
            try {
                doc = Jsoup.connect(url).timeout(3000).get();
            } catch (Exception e) {
                Thread.sleep(6000L);
                doc = Jsoup.connect(url).timeout(20_000).get();
            }
            Element candidate = form.getExtractArea().startsWith("<") ? doc.getElementsByTag(form.getExtractArea().replace("<","")).first() : doc.select(form.getExtractArea()).first();
            Elements links = candidate.select(form.getLinkPosition());
            Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+(com|cn|net|org|biz|info|cc|tv)");
            Matcher matcher = pattern.matcher(url);
            matcher.find();
            links.forEach(link -> {
                try {
                    String href = link.attr("href").startsWith("http") ? link.attr("href") : matcher.group(0) + link.select("a").attr("href");
//                    String href = link.nextElementSibling().attr("href");   // <li>标签含两个<a>
                    if (!href.startsWith("http")) {
                        href = matcher.group(0) + href;
                    }
                    loggingDataService.insertArticleOrQuestion(form, href, md5key);
                } catch (Exception e) {
                    logger.error("文章解析出错：{}, baseURL: {}" , e.getMessage(), form.getUrl());
                    e.printStackTrace();
                }
            });
        }
    }

}
