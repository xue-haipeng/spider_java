package com.solomon.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Entity
@Table(name = "spider_wx_question")
public class Question extends ZixunEntity {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;  // id
    private String question;  // 问题
    private String answer;  // 答案

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
