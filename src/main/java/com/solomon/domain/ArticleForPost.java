package com.solomon.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Entity
@Table(name = "spider_wx_article_1")
public class ArticleForPost implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String title;// 标题
    private String content;// 内容
    private String keyword;// 爬取的关键词
    private Long menuId;// 最低一级栏目的id
    private Long authorId;// 作者id
    private Integer weight;// 权重
    private Integer status;// 状态
    private Integer score;// 文章分值，运营定
    private Date createdTime;// 万象入库创建时间
    private Date updatedTime;// 万象更新时间
    private Date publishedTime;// 文章本身发布时间

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getMenuId() {
        return this.menuId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getAuthorId() {
        return this.authorId;
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

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return this.createdTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public Date getPublishedTime() {
        return this.publishedTime;
    }

    @Override
    public String toString() {
        return "ArticleForPost{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
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
