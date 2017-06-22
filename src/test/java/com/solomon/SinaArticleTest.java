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

    final String URL = "http://www.caijing.com.cn/dailynews/index-{}.html";
    int startIndex = 684;
    final int totalCount = 6200;    // 总量
    String extractArea = "ul.list";    // 析取区域
    String extractLinks = "div.wzbt > a[href]";    // 链接位置
    Long menuId = 8L;    // 二级类目

    @Test
    public void licaiTest() throws IOException, InterruptedException {
        for (int i = startIndex; i < totalCount; i++) {
            System.out.println("-------- 第 " + i + " 页 -------");
            String url = URL.replace("{}", Integer.toString(i));
            Document doc = null;
            try {
                doc = Jsoup.connect(url).timeout(3000).get();
            } catch (Exception e) {
                Thread.sleep(6000L);
                try {
                    doc = Jsoup.connect(url).timeout(20000).get();
                } catch (Exception ex) {
                    Thread.sleep(12000L);
                    doc = Jsoup.connect(url).timeout(30000).get();
                }
            }
            Element candidate = doc.select(extractArea).first();
            Elements links = candidate.select(extractLinks);
            links.forEach(link -> {
                try {
                    loggingDataService.insertSinaArticle(link.attr("href"), menuId);
                } catch (Exception e) {
                    System.out.println(link.attr("href"));
                    System.out.println(e.getMessage());
                }
//                System.out.println(link.attr("href"));
            });
        }
    }

}
