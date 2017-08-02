package com.solomon.common;

import org.springframework.data.domain.Sort;

import java.util.regex.Pattern;

/**
 * Created by xuehaipeng on 2017/7/10.
 */
public interface Constant {
    String ARTICLE_SEND_URL = "http://man.wxlink.jd.com/dataCollect/article";
    String QUESTION_SEND_URL = "http://man.wxlink.jd.com/dataCollect/question";
//    String QUESTION_SEND_URL = "http://man.jcloud.com/dataCollect/question";
    Sort DB_ASC_SORT = new Sort(Sort.Direction.ASC, "id");
    String KEYWORD_QUERY_URL = "http://man.wxlink.jd.com/dataCollect/getKeywordList?pageSize={1}&pageNow={2}";
    String SECONDARY_KW_INSERT_URL = "http://man.wxlink.jd.com/dataCollect/secondaryKeyword";
    Pattern URL_DOMAIN_PATTEN = Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+(com|cn|net|org|biz|info|cc|tv)");
    Pattern DATE_PATTERN = Pattern.compile("\\d{4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?");
    String REGEX_SCRIPT_TAG = "<script[^>]*?>[\\s\\S]*?<\\/script>";
    int PAGE_SIZE_DB_QUERY = 100;
}
