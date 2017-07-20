package com.solomon.controller;

import com.solomon.common.Constant;
import com.solomon.service.ArticleService;
import com.solomon.vo.ArticleForm;
import com.solomon.vo.FormData;
import com.solomon.vo.QuestionForm;
import com.solomon.service.LoggingDataService;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuehaipeng on 2017/6/23.
 */
@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    LoggingDataService loggingDataService;

    @Autowired
    ArticleService articleService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/article")
    @ResponseBody
    public Map<String, Integer> articleForm(@Valid ArticleForm articleForm) {
        try {
            return extractAndFetch(articleForm);
        } catch (InterruptedException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping("/article/sendToPrd")
    @ResponseBody
    public String sendToPrdDb(@RequestParam int startPage, @RequestParam int endPage) {
        CompletableFuture.supplyAsync(() -> articleService.sentToPrd(startPage, endPage));
        return "submitted";
    }

    @PostMapping("/question")
    @ResponseBody
    public Map<String, Integer> questionForm(@Valid QuestionForm form) {
        try {
            return extractAndFetch(form);
        } catch (InterruptedException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/testArticleFetch")
    @ResponseBody
    public Map<String, String> articleTest(ArticleForm articleForm) {
        Map<String, String> exception = new HashMap<>();
        try {
            return testFech(articleForm);
        } catch (Exception e) {
            exception.put("exceptMessage", e.toString());
            exception.put("exceptBody", ExceptionUtils.getStackTrace(e));
            return exception;
        }
    }

    @PostMapping("/testQuestionFetch")
    @ResponseBody
    public Map<String, String> questionTest(QuestionForm questionForm) throws MalformedURLException {
        return testFech(questionForm);
    }

    private Map<String, String> testFech(FormData form) throws MalformedURLException {
        String url = form.getUrl().replace("{}", Integer.toString(form.getStartIndex()));
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(3000).get();
        } catch (Exception e) {
            if (e instanceof SocketTimeoutException) {
                try {
                    doc = Jsoup.connect(url).timeout(3000).get();
                } catch (IOException e1) {
                    logger.error("无法连接到网址： {}", e.getMessage(), e);
                }
            }
        }
        Element candidate = doc.select(form.getExtractArea()).first();
        Elements links = candidate.select(form.getLinkPosition());
        Element hrefElement = links.first().toString().startsWith("<a ") ? links.first() : links.first().child(0);
        String href = hrefElement.attr("href");
//        Matcher matcher = Constant.URL_DOMAIN_PATTEN.matcher(url);
//        matcher.find();   hangs because of the "horrible backtrack"
        URL uri = new URL(url);
        if (href.endsWith("index.shtml") && links.first().nextElementSibling() != null) {
            href = links.first().nextElementSibling().attr("href");   // <li>标签含两个<a>
        }
        if (!href.startsWith("http")) {
            href = uri.getHost() + "/" + href;
        }
        Map<String, String> resultMap = loggingDataService.fetchArticleOrQuestion(form, href);
        return resultMap;
    }

    private Map<String, Integer> extractAndFetch(FormData form) throws InterruptedException, NoSuchAlgorithmException, IOException {
        System.out.println(form);
        Map<String, Integer> map = new HashMap<>();
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(form.getUrl().getBytes());
        String md5key = new BigInteger(1, md5.digest()).toString(16);
        int curPage = 0;
        for (int i = form.getStartIndex(); i < form.getEndIndex(); i++) {
            curPage = i;
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
/*            Matcher matcher = Constant.URL_DOMAIN_PATTEN.matcher(url);
            matcher.find();  // pool performance*/
            URL uri = new URL(url);
            links.forEach(link -> {
                try {
                    String href = link.attr("href").startsWith("http") ? link.attr("href") : uri.getHost() + link.select("a").attr("href");
//                    String href = link.nextElementSibling().attr("href");   // <li>标签含两个<a>
                    loggingDataService.insertArticleOrQuestion(form, href, md5key);
                } catch (Exception e) {
                    logger.error("文章解析出错：{}, baseURL: {}" , e.getMessage(), form.getUrl());
                    e.printStackTrace();
                }
            });
        }
        map.put("curPage", curPage);
        map.put("totalPage", form.getEndIndex());
        return map;
    }

}
