package com.solomon.controller;

import com.solomon.repository.elasticsearch.ArticleFormEsRepo;
import com.solomon.repository.elasticsearch.QuestionFormEsRepo;
import com.solomon.vo.ArticleForm;
import com.solomon.vo.QuestionForm;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
    ArticleFormEsRepo articleFormEsRepo;

    @Autowired
    QuestionFormEsRepo questionFormEsRepo;

    @ApiOperation(value = "ArticleForm保存接口", notes = "保存ArticleForm到ES")
    @ApiImplicitParam(name = "articleForm", value = "ArticleForm", required = true, dataType = "ArticleForm")
    @GetMapping("/insertArticleForm")
    public String insertFormData(@Valid ArticleForm articleForm) {
        ArticleForm result = articleFormEsRepo.save(articleForm);
        return result != null && !StringUtils.isEmpty(result.getUrl()) ? "OK" : "Failed";
    }

    @ApiOperation(value = "QuestionForm保存接口", notes = "保存QuestionForm到Es")
    @ApiImplicitParam(name = "questionForm", value = "QuestionForm", required = true, dataType = "QuestionForm")
    @GetMapping("/insertQuestionForm")
    public String insertFormData(@Valid QuestionForm questionForm) {
        QuestionForm result = questionFormEsRepo.save(questionForm);
        return result != null && !StringUtils.isEmpty(result.getUrl()) ? "OK" : "Failed";
    }

    @ApiOperation(value = "ArticleForm删除接口", notes = "根据ID删除ES中的ArticleForm")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String")
    @GetMapping("/deleteArticleForm")
    public void deleteArticleForm(@RequestParam String id) {
        articleFormEsRepo.delete(id);
    }

    @ApiOperation(value = "QuestionForm删除接口", notes = "根据ID删除ES中的QuestionForm")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String")
    @GetMapping("/deleteQuestionForm")
    public void deleteQuestionForm(@RequestParam String id) {
        questionFormEsRepo.delete(id);
    }

    @ApiOperation(value = "ArticleForm查询接口", notes = "根据URL查询ES中的ArticleForm")
    @ApiImplicitParam(name = "url", value = "URL", required = true, dataType = "String")
    @GetMapping("/queryArticleForm")
    public List<ArticleForm> queryArticleForm(@RequestParam String url) {
        return articleFormEsRepo.findByUrlStartingWith(url);
    }

    @ApiOperation(value = "ArticleForm查询接口", notes = "根据ID查询ES中的ArticleForm")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String")
    @GetMapping("/queryArticleFormById/{id}")
    public ArticleForm queryArticleFormById(@PathVariable String id) {
        return articleFormEsRepo.findOne(id);
    }

    @ApiOperation(value = "QuestionForm查询接口", notes = "根据ID查询ES中的QuestionForm")
    @ApiImplicitParam(name = "url", value = "URL", required = true, dataType = "String")
    @GetMapping("/queryQuestionForm")
    public List<QuestionForm> queryQeustionForm(@RequestParam String url) {
        return questionFormEsRepo.findByUrlStartingWith(url);
    }

    @ApiOperation(value = "QuestionForm查询接口", notes = "根据URL查询ES中的QuestionForm")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "String")
    @GetMapping("/queryQuestionFormById/{id}")
    public QuestionForm queryQuestionFormById(@PathVariable String id) {
        return questionFormEsRepo.findOne(id);
    }

}
