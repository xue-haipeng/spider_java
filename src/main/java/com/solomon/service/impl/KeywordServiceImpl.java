package com.solomon.service.impl;

import com.solomon.domain.Keyword;
import com.solomon.repository.KeywordRepo;
import com.solomon.service.KeywordService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Service
public class KeywordServiceImpl implements KeywordService {
    private static final Logger logger = LoggerFactory.getLogger(KeywordServiceImpl.class);
    private final String BAIDU_PREFIX = "https://www.baidu.com/s?wd=";
    private final String SO_PREFIX = "https://www.so.com/s?ie=utf-8&fr=none&src=360sou_newhome&q=";

    private final Executor executor = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    @Autowired
    KeywordRepo keywordRepo;

    @Override
    public List<String> getKeywordList(Pageable pageable) {
        return keywordRepo.findKeywordsById(pageable);
    }

    @Override
    public List<Keyword> findSecondaryKeywords(String keyword) {
        List<Keyword> secondaryKeywords = new ArrayList<>();
        Element baiduKeyword = getDivRsList(keyword, BAIDU_PREFIX);    // 百度搜索
        Element soKeyword = getDivRsList(keyword, SO_PREFIX);      // 360搜索
        Elements[] elements = new Elements[] {baiduKeyword.select("a"), soKeyword.select("a")};
        List<String> duplicateKeywords = new ArrayList<>();
        Arrays.stream(elements).forEach(e -> e.forEach(ele -> {
            if (keywordRepo.findKeywordByName(ele.text()) != null && keywordRepo.findKeywordByName(ele.text()).size() > 0) {
                System.out.println("!!!!!!!!!! FIND DUPLICATE : " + ele.text());
                duplicateKeywords.add(ele.text());
            }
            Keyword secondaryKw = new Keyword();
            secondaryKw.setKeyword(ele.text());
            secondaryKw.setLevel(2);
            secondaryKw.setParentId(keywordRepo.findKeywordByName(keyword).get(0));
            secondaryKw.setWeight(1);
            secondaryKw.setStatus(1);
            secondaryKeywords.add(secondaryKw);
        }));
        File file = null;
        try {
            file = new ClassPathResource("static/data/duplicate.txt").getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.write(file.toPath(), duplicateKeywords, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        return secondaryKeywords;
    }

    /**
     *通过百度/360搜索引擎查询二级关键词
     * @param keyword 一级关键词
     * @param url 可选值为百度/360网站前缀
     * @return
     */
    public Element getDivRsList(String keyword, String url) {

        try {
            return Jsoup.connect(url + keyword).timeout(2000).get().select("div#rs").first();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertSecondaryKeywords(List<Keyword> secondaryKeywords) {
        Set<String> kwSet = new HashSet<>();
        List<Keyword> filteredKw = new ArrayList<>();
        secondaryKeywords.forEach(kw -> {
            if (kwSet.size() == 0 || !kwSet.contains(kw.getKeyword())) {
                filteredKw.add(kw);
                kwSet.add(kw.getKeyword());
            }
        });
        keywordRepo.save(filteredKw);
    }

}
