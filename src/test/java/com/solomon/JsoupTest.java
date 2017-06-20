package com.solomon;

import com.solomon.domain.Keyword;
import com.solomon.repository.KeywordRepo;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JsoupTest {


    private final String BAIDU_PREFIX = "https://www.baidu.com/s?wd=";
    private final String SO_PREFIX = "https://www.so.com/s?ie=utf-8&fr=none&src=360sou_newhome&q=";

    private final Executor executor = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    @Autowired
    KeywordRepo keywordRepo;

    Sort sort = new Sort(Sort.Direction.ASC, "id");
    Pageable pageable = new PageRequest(100, 10, sort);

    public List<Element> getDivRsList() {
//        List<String> keywords = Arrays.asList("人工智能", "大数据");
        List<Keyword> keywordList = keywordRepo.findAll(pageable).getContent();
        List<String> keywords = keywordRepo.findKeywordsById(pageable);

        List<CompletableFuture<Element>> secondaryKeywords = keywords.stream().map(kw -> CompletableFuture.supplyAsync(() -> {
            try {
                return Jsoup.connect(SO_PREFIX + kw).timeout(2000).get().select("div#rs").first();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, executor)).collect(Collectors.toList());

        return secondaryKeywords.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    @Test
    public void testJsoup() throws IOException {
        List<Element> divrsList = getDivRsList();
        List<Elements> elements = divrsList.stream().map(element -> element.select("a")).collect(Collectors.toList());
        List<String> duplicateKeywords = new ArrayList<>();
        elements.forEach(e -> e.forEach(ele -> {
            if (keywordRepo.findKeywordByName(ele.text()) != null && keywordRepo.findKeywordByName(ele.text()).size() > 0) {
                System.out.println("!!!!!!!!!! FIND DUPLICATE : " + ele.text());
                duplicateKeywords.add(ele.text());
            }
//            System.out.println("**" + ele.text());
        }));
        Path path = Paths.get("D:\\workspace\\git\\spider\\src\\main\\resources\\static\\data\\duplicate.txt");
        Files.write(path, duplicateKeywords, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
    }

    @Test
    public void test1() {
        List<String> keywords = keywordRepo.findKeywordsById(pageable);
        keywords.forEach(kw -> System.out.println(kw));
    }
}
