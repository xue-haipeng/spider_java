package com.solomon;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
}
