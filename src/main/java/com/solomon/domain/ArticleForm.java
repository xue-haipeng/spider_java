package com.solomon.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/22.
 */
public class ArticleForm {
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
    @NotBlank
    private String pubDate1;
    private String pubDate2;
    private String keyword;
    @NotBlank
    private String content;

    private List<String> excluded1;

    private String excluded2;

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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getExcluded1() {
        return excluded1;
    }

    public void setExcluded1(List<String> excluded1) {
        this.excluded1 = excluded1;
    }

    public String getExcluded2() {
        return excluded2;
    }

    public void setExcluded2(String excluded2) {
        this.excluded2 = excluded2;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "url='" + url + '\'' +
                ", startIndex=" + startIndex +
                ", endIndex=" + endIndex +
                ", extractArea='" + extractArea + '\'' +
                ", linkPosition='" + linkPosition + '\'' +
                ", menuId=" + menuId +
                ", title='" + title + '\'' +
                ", pubDate1='" + pubDate1 + '\'' +
                ", pubDate2='" + pubDate2 + '\'' +
                ", keyword='" + keyword + '\'' +
                ", content='" + content + '\'' +
                ", excluded1=" + excluded1 +
                ", excluded2='" + excluded2 + '\'' +
                '}';
    }
}
