package com.solomon;

import com.solomon.service.LoggingDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SinaArticleTest {

    @Autowired
    LoggingDataService loggingDataService;

    final String URL = "http://money.sohu.com/hjdt/index_{}.shtml";
    int startIndex = 69;
    final int endIndex = 168;    // 总量
    String extractArea = "div.f14list>ul";    // 析取区域
    String extractLinks = "li > a[href]";    // 链接位置
    Long menuId = 12L;    // 二级类目

    @Test
    public void licaiTest() throws IOException, InterruptedException {

    }

}
