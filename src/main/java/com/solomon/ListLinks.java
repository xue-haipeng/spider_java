package com.solomon;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Example program to list links from a URL.
 */
public class ListLinks {
    public static void main(String[] args) throws IOException {

        String url = "http://roll.finance.sina.com.cn/finance/zq2/zsyj/index_2.shtml";
        System.out.println("Fetching %s... " + url);

        Document doc = Jsoup.connect(url).get();
        Element lin = doc.select("div.listBlk").first();
//        System.out.println(lin.text());
        Elements links = lin.select("li > a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        System.out.println("Links: " + links.size());
        for (Element link : links) {
            System.out.println(link.attr("abs:href"));
        }
    }


}