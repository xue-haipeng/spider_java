package com.solomon.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
public class FormData {
    @URL
    private String url;
    @Min(1)
    private int startIndex;
    @Min(1)
    private int endIndex;
    @NotBlank
    private String extractArea;
    @NotBlank
    private String linkPosition;
    @Min(8)
    private long menuId;
    @NotBlank
    private String title;
    private String title2;
    @NotBlank
    private String pubDate1;
    private String pubDate2;
    private String pubDate3;
    private String keyword;
    private Boolean exFirst;
    private Boolean exLast;
    private String excluded2;
    private Integer type;  // 0: ArticleForm, 1: QuestionForm

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public String getExtractArea() {
        return extractArea;
    }

    public void setExtractArea(String extractArea) {
        this.extractArea = extractArea;
    }

    public String getLinkPosition() {
        return linkPosition;
    }

    public void setLinkPosition(String linkPosition) {
        this.linkPosition = linkPosition;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getPubDate1() {
        return pubDate1;
    }

    public void setPubDate1(String pubDate1) {
        this.pubDate1 = pubDate1;
    }

    public String getPubDate2() {
        return pubDate2;
    }

    public void setPubDate2(String pubDate2) {
        this.pubDate2 = pubDate2;
    }

    public String getPubDate3() {
        return pubDate3;
    }

    public void setPubDate3(String pubDate3) {
        this.pubDate3 = pubDate3;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Boolean getExFirst() {
        return exFirst;
    }

    public void setExFirst(Boolean exFirst) {
        this.exFirst = exFirst;
    }

    public Boolean getExLast() {
        return exLast;
    }

    public void setExLast(Boolean exLast) {
        this.exLast = exLast;
    }

    public String getExcluded2() {
        return excluded2;
    }

    public void setExcluded2(String excluded2) {
        this.excluded2 = excluded2;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FormData{" +
                "url='" + url + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", extractArea='" + extractArea + '\'' +
                ", linkPosition='" + linkPosition + '\'' +
                ", menuId=" + menuId +
                ", title='" + title + '\'' +
                ", title2='" + title2 + '\'' +
                ", pubDate1='" + pubDate1 + '\'' +
                ", pubDate2='" + pubDate2 + '\'' +
                ", pubDate3='" + pubDate3 + '\'' +
                ", keyword='" + keyword + '\'' +
                ", exFirst=" + exFirst +
                ", exLast=" + exLast +
                ", excluded2='" + excluded2 + '\'' +
                ", type=" + type +
                '}';
    }
}
