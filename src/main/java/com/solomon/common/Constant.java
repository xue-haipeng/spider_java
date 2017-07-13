package com.solomon.common;

import org.springframework.data.domain.Sort;

/**
 * Created by xuehaipeng on 2017/7/10.
 */
public interface Constant {
    String ARTICLE_SEND_URL = "http://man.wxlink.jd.com/dataCollect";  // "http://man.wxlink.jd.com/dataCollect/article";
    String QUESTION_SEND_URL = "http://man.wxlink.jd.com/dataCollect/question";
    Sort DB_ASC_SORT = new Sort(Sort.Direction.ASC, "id");
    String KEYWORD_QUERY_URL = "http://man.wxlink.jd.com/dataCollect/getKeywordList?pageSize={1}&pageNow={2}";
    String SECONDARY_KW_INSERT_URL = "http://man.wxlink.jd.com/dataCollect/secondaryKeyword";
}
