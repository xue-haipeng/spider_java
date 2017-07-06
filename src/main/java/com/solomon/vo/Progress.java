package com.solomon.vo;

/**
 * Created by xuehaipeng on 2017/7/2.
 */
public class Progress {
    private Integer count;  // articles collected

    public Progress() {}

    public Progress(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
