package com.solomon.domain;

import java.util.Date;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
public class MongoZixun {

    private String title;  // 标题
    private String keyword;  // 关键词
    private Long menuId;  // 最低一级栏目的id
    private Long authorId;  // 作者id
    private Integer weight;  // 权重
    private Integer status;  // 状态
    private Integer score;  // 问答分值
    private Date createdTime;
    private Date updatedTime;
    private Date publishedTime;  // 发布日期

    public MongoZixun() {}

    public MongoZixun(MongoZixun mongoZixun) {
        this.title = mongoZixun.getTitle();
        this.keyword = mongoZixun.getKeyword();
        this.menuId = mongoZixun.getMenuId();
        this.authorId = mongoZixun.getAuthorId();
        this.weight = mongoZixun.getWeight();
        this.status = mongoZixun.getStatus();
        this.score = mongoZixun.getScore();
        this.createdTime = mongoZixun.getCreatedTime();
        this.updatedTime = mongoZixun.getUpdatedTime();
        this.publishedTime = mongoZixun.publishedTime;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    @Override
    public String toString() {
        return "MongoZixun{" +
                "title='" + title + '\'' +
                ", keyword='" + keyword + '\'' +
                ", menuId=" + menuId +
                ", authorId=" + authorId +
                ", weight=" + weight +
                ", status=" + status +
                ", score=" + score +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", publishedTime=" + publishedTime +
                '}';
    }
}
