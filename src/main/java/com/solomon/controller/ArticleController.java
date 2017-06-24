package com.solomon.controller;

import com.solomon.domain.ArticleForm;
import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by xuehaipeng on 2017/6/23.
 */
@Controller
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    LoggingDataService loggingDataService;

    @PostMapping("/article")
    public String articleForm(@Valid ArticleForm form) throws InterruptedException, IOException {

        System.out.println(form);
        for (int i = form.getStartIndex(); i < form.getEndIndex(); i++) {
            logger.info("-------- 第 {} 页 --------", i);
            String url = form.getUrl().replace("{}", Integer.toString(i));
            Document doc = null;
            try {
                doc = Jsoup.connect(url).timeout(3000).get();
            } catch (Exception e) {
                Thread.sleep(6000L);
                doc = Jsoup.connect(url).timeout(20_000).get();
            }
            Element candidate = doc.select(form.getExtractArea()).first();
            Elements links = candidate.select(form.getLinkPosition());
            links.forEach(link -> {
                try {
                    loggingDataService.insertWebArticle(form, link.attr("href"));
                } catch (Exception e) {
                    logger.error("文章解析出错：{}" ,e.getMessage());
                }
            });
        }
        return "redirect:/article";
    }
}
