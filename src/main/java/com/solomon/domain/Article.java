package com.solomon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by xuehaipeng on 2017/6/13.
 */
@Entity
@Table(name = "spider_wx_article")
public class Article extends ZixunEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    private String content;  //  内容

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", content='" + content.substring(0, 100) + '\'' +
                "} " + super.toString();
    }
}
