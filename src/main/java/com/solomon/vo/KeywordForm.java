package com.solomon.vo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by xuehaipeng on 2017/6/22.
 */
public class KeywordForm {
    @Min(1)
    private int startPage;
    @Min(1)
    private int endPage;
    @Min(0)
    private int pageOffset = 0;

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }

    @Override
    public String toString() {
        return "KeywordForm{" +
                "startPage=" + startPage +
                ", endPage=" + endPage +
                ", pageOffset=" + pageOffset +
                '}';
    }
}
