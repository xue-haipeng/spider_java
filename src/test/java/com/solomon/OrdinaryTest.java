package com.solomon;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by xuehaipeng on 2017/6/25.
 */
public class OrdinaryTest {

    @Test
    public void test1() {
//        Pattern pattern = Pattern.compile("[0-9]{4}[年][0-9]{1,2}[月]");
        Pattern pattern = Pattern.compile("\\d{4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?");
        Matcher matcher = pattern.matcher("兹定于2011年5月4日下午14：00在");

        String dateStr = matcher.find() ? matcher.group(0) : null;

        System.out.println(dateStr);

        String[] dateArr = dateStr.split("[-|\\/|年|月|日|号|\\.]");
        if (dateArr[1].length() == 1) {
            dateArr[1] = "0" + dateArr[1];
        }
        if (dateArr[2].length() == 1) {
            dateArr[2] = "0" + dateArr[2];
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date date = java.sql.Date.valueOf(org.apache.commons.lang3.StringUtils.join(dateArr, "-"));

        System.out.println("--------" + date);

    }

    @Test
    public void test2() {
        String url = "http://blog.csdn.net/xb12369";

        Pattern pattern = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+(com|cn|net|org|biz|info|cc|tv)");
        Matcher matcher = pattern.matcher(url);
        matcher.find();
        System.out.println("URL: " + matcher.group(0));
    }

    public String asyncTest() {
/*        List<Integer> ints = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Future<Double>> asyncCalc = ints.stream().map(integer -> CompletableFuture.supplyAsync(
                () -> slowCalc()
        )).collect(Collectors.toList());*/
        CompletableFuture.supplyAsync(
                () -> slowCalc()
        );
        return "Done";
    }

    private Double slowCalc() {
        try {
            Thread.sleep(3_000);
        } catch (InterruptedException e) {

        }
        Double randowm = Math.random();
        System.out.println(randowm);
        return randowm;
    }

    @Test
    public void testAsync() throws InterruptedException, NoSuchAlgorithmException {
//        System.out.println("------------- " + asyncTest() + "  ------------------------");
//        Thread.sleep(5_000);
        String str = "http://news.machine365.com/newslist-type-27-{}.shtml";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes());
        System.out.println("------------ " + new BigInteger(1, md5.digest()).toString(16));
    }

    @Test
    public void test3() {
        Map<String, Integer> map = new HashMap<>();
        map.put("a", 1);
        map.put("a", 2);
        System.out.println(map.get("a"));
    }

    @Test
    public void test4() {
        try {
            throw new RuntimeException("test");
        } catch (Exception e) {
            System.out.println("*********************** " + e.getMessage());
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^ " + e.toString());
        }
    }

    @Test
    public void test5() throws URISyntaxException, MalformedURLException {
        String url = "http://www.caiguu.com/shichang/ceping/22_3.html";
        URL url1 = new URL(url);
        String domain = url1.getHost();
        System.out.println(domain);
        System.out.println(url1.getRef());

    }

    @Test
    public void test6() {
        Date date = java.sql.Date.valueOf(LocalDate.parse("2017-07-23"));
        System.out.println(date);
    }

    public CompletableFuture<String> asyncTask() {
        System.out.println("asyncTask is invoked .............");
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync in asyncTask ...........");
            return "Hello World !!!!!!!!";
        });
    }

    public CompletableFuture<Integer> asyncCalc() {
        System.out.println("asyncCalc is invoked ...............");
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("supplyAsync in asyncCalc ..............");
            throw new RuntimeException("an exception");
//            return 10;
        });
    }

    @Test
    public void test7() {
        String result = asyncTask().thenCombine(asyncCalc(), (s, i) -> s + i).join();
        System.out.println("********* " + result);
    }

    @Test
    public void test8() {
        try {
            int num = asyncCalc().join();
            System.out.println(num);
        } catch (Exception e) {
            System.out.println("Catched ....................... " + e.getMessage());
        }
    }

    @Test
    public void test9() {
        String url = "www.caiguu.com//shichang/ceping/142945.html";
        if (url.replace("://", "").contains("//")) {
        }
    }

    @Test
    public void test10() {
        LocalDate localDate = LocalDate.parse("2017-07-30");
        LocalDateTime localDateTime = localDate.atTime(10, 45);
        System.out.println(localDateTime);
        LocalDateTime ldt = localDateTime.minusHours(12L);
        LocalDate ld = ldt.toLocalDate();
        System.out.println(ld);

    }

    @Test
    public void test11() {
        String abc = "The parade, long anticipated but only officially announced Saturday, was part of the celebrations" +
                " of the 90th anniversary of the People's Liberation Army (PLA). It was also viewed as a potent reminder of" +
                " Xi's firm grip on power ahead of a key Communist Party meeting this fall, where a major leadership reshuffle is expected.";
        String bcd = "Communist Party meeting this fall, ok?";

        String regex = "^Communist.*fall.*";
        System.out.println(bcd.matches(regex));;
        System.out.println(StringUtils.contains(bcd, regex));

        String url = "http://www.baidu.com//abcd/efg123.html";
        String reg = "(?<!(http:))(//)";
        System.out.println(url.replace(reg, "/"));
     }
}
