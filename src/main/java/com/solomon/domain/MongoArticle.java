package com.solomon.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Document(collection = "spider_article")
public class MongoArticle extends MongoZixun implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String content;// 内容

    public MongoArticle() {}

    public MongoArticle(MongoZixun mongoZixun) {
        super(mongoZixun);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "MongoArticle{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
