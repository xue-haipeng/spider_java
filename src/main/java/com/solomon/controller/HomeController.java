package com.solomon.controller;

import com.solomon.vo.ArticleForm;
import com.solomon.vo.KeywordForm;
import com.solomon.vo.QuestionForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
