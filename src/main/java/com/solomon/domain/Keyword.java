package com.solomon.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xuehaipeng on 2017/6/12.
 */
@Entity
@Table(name = "wx_keyword")
public class Keyword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String keyword;
    private Integer level;
    private Long parentId;
    private Integer weight;
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", level=" + level +
                ", parentId=" + parentId +
                ", weight=" + weight +
                ", status=" + status +
                '}';
    }
}
