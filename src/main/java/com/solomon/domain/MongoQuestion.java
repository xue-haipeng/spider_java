package com.solomon.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
@Document(collection = "spider_question")
public class MongoQuestion extends MongoZixun implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;  // id
    private String question;  // 问题
    private String answer;  // 答案

    public MongoQuestion() {}

    public MongoQuestion(MongoZixun mongoZixun) {
        super(mongoZixun);

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "MongoQuestion{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
