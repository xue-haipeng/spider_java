package com.solomon.controller;

import com.solomon.repository.elasticsearch.ArticleFormRepo;
import com.solomon.repository.elasticsearch.QuestionFormRepo;
import com.solomon.vo.ArticleForm;
import com.solomon.vo.QuestionForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by xuehaipeng on 2017/7/6.
 */
@RestController
@RequestMapping("/es")
public class EsRestController {

    @Autowired
    ArticleFormRepo articleFormRepo;

    @Autowired
    QuestionFormRepo questionFormRepo;

    @GetMapping("/insertArticleForm")
    public String insertFormData(@Valid ArticleForm articleForm) {
        ArticleForm result = articleFormRepo.save(articleForm);
        return result != null && !StringUtils.isEmpty(result.getUrl()) ? "OK" : "Failed";
    }

    @GetMapping("/insertQuestionForm")
    public String insertFormData(@Valid QuestionForm questionForm) {
        QuestionForm result = questionFormRepo.save(questionForm);
        return result != null && !StringUtils.isEmpty(result.getUrl()) ? "OK" : "Failed";
    }

    @GetMapping("/deleteArticleForm")
    public void deleteArticleForm(@RequestParam String id) {
        articleFormRepo.delete(id);
    }

    @GetMapping("/deleteQuestionForm")
    public void deleteQuestionForm(@RequestParam String id) {
        questionFormRepo.delete(id);
    }

    @GetMapping("/queryArticleForm")
    public List<ArticleForm> queryArticleForm(@RequestParam String url) {
        return articleFormRepo.findByUrlStartingWith(url);
    }

    @GetMapping("/queryArticleFormById/{id}")
    public ArticleForm queryArticleFormById(@PathVariable String id) {
        return articleFormRepo.findOne(id);
    }

    @GetMapping("/queryQuestionForm")
    public List<QuestionForm> queryQeustionForm(@RequestParam String url) {
        return questionFormRepo.findByUrlStartingWith(url);
    }

    @GetMapping("/queryQuestionFormById/{id}")
    public QuestionForm queryQuestionFormById(@PathVariable String id) {
        return questionFormRepo.findOne(id);
    }

}
