package com.solomon.controller;

import com.solomon.domain.ArticleForm;
import com.solomon.domain.FormData;
import com.solomon.domain.KeywordForm;
import com.solomon.domain.QuestionForm;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String home(KeywordForm keywordForm) {
//        model.addAttribute("keywordForm", new KeywordForm());
        return "index";
    }

    @GetMapping("/article")
    public String articleSpider(ArticleForm articleForm) {
        return "article";
    }

    @GetMapping("/question")
    public String questionSpider(QuestionForm questionForm) { return "question"; };


}
