package com.solomon.controller;

import com.solomon.domain.ArticleForm;
import com.solomon.domain.Progress;
import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;
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

    @Autowired
    LoggingDataService loggingDataService;

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    @PostMapping("/article")
    public String articleForm(@Valid ArticleForm form) throws InterruptedException, IOException {

        System.out.println(form);
        for (int i = form.getStartIndex(); i < form.getEndIndex(); i++) {
            logger.info("-------- 第 {}/{} 页 --------", i, form.getEndIndex());
            String url = form.getUrl().replace("{}", Integer.toString(i));
            Document doc = null;
            try {
                doc = Jsoup.connect(url).timeout(3000).get();
            } catch (Exception e) {
                Thread.sleep(6000L);
                doc = Jsoup.connect(url).timeout(20_000).get();
            }
            if (form.getExtractArea().startsWith("<")) {

            }
            Element candidate = form.getExtractArea().startsWith("<") ? doc.getElementsByTag(form.getExtractArea().replace("<","")).first() : doc.select(form.getExtractArea()).first();
            Elements links = candidate.select(form.getLinkPosition());

            Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+(com|cn|net|org|biz|info|cc|tv)");
            Matcher matcher = pattern.matcher(url);
            matcher.find();

            links.forEach(link -> {
                try {
                    String href = link.attr("href").startsWith("http") ? link.attr("href") : matcher.group(0) + "/report/" + link.select("a").attr("href");
//                    String href = link.nextElementSibling().attr("href");   // <li>标签含两个<a>
                    if (!href.startsWith("http")) {
                        href = matcher.group(0) + href;
                    }
                    loggingDataService.insertWebArticle(form, href);
                } catch (Exception e) {
                    logger.error("文章解析出错：{}, baseURL: {}" , e.getMessage(), form.getUrl());
                    e.printStackTrace();
                }
            });
        }
        return "redirect:/article";
    }

    @PostMapping("/testFetch")
    @ResponseBody
    public Map<String, String> articleTest(ArticleForm form) {
        Map<String, String> resultMap = new HashMap<>();
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

        String href = links.first().attr("href").startsWith("http") ? links.first().attr("href") : matcher.group(0) + links.first().attr("href");
        if (href.endsWith("index.shtml") && links.first().nextElementSibling() != null) {
            href = links.first().nextElementSibling().attr("href");   // <li>标签含两个<a>
        }
        if (!href.startsWith("http")) {
            href = matcher.group(0) + "/" + href;
        }

        resultMap = loggingDataService.fetchArticle(form, href);
        return resultMap;
    }

/*    @MessageMapping("/hello")
    @SendTo("/topic/progress")
    public Progress progress(Integer count) {
        return new Progress(count);
    }*/

    @RequestMapping("/testsend")
    @ResponseBody
    public String testSend() {
        for (int i=0; i<100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            this.messagingTemplate.convertAndSend("/topic/progress", (1000+i) + ":" + (1000+i*5));
        }
        return "OK";
    }

}
