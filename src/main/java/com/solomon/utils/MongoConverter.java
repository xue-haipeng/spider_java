package com.solomon.utils;

import com.solomon.domain.*;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
public class MongoConverter {
    public static MongoZixun entityToMongo(ZixunEntity entity) {
        MongoZixun mongoZixun = new MongoZixun();
        mongoZixun.setTitle(entity.getTitle());
        mongoZixun.setKeyword(entity.getKeyword());
        mongoZixun.setMenuId(entity.getMenuId());
        mongoZixun.setAuthorId(entity.getAuthorId());
        mongoZixun.setWeight(entity.getWeight());
        mongoZixun.setStatus(entity.getStatus());
        mongoZixun.setScore(entity.getScore());
        mongoZixun.setCreatedTime(entity.getCreatedTime());
        mongoZixun.setUpdatedTime(entity.getUpdatedTime());
        mongoZixun.setPublishedTime(entity.getPublishedTime());
        if (entity instanceof Article) {
            Article article = (Article) entity;
            MongoArticle mongoArticle = (MongoArticle) mongoZixun;
            mongoArticle.setContent(article.getContent());
            return mongoArticle;
        } else if (entity instanceof Question) {
            Question question = (Question) entity;
            MongoQuestion mongoQuestion = (MongoQuestion) mongoZixun;
            mongoQuestion.setQuestion(question.getQuestion());
            mongoQuestion.setAnswer(question.getAnswer());
            return mongoQuestion;
        } else {
            throw new RuntimeException("Not A Legal Subclass Of ZixunEntity");
        }
    }

    public static ZixunEntity mongoToEntity(MongoZixun mongoZixun) {
        ZixunEntity entity = new ZixunEntity();
        entity.setTitle(mongoZixun.getTitle());
        entity.setKeyword(mongoZixun.getKeyword());
        entity.setMenuId(mongoZixun.getMenuId());
        entity.setAuthorId(mongoZixun.getAuthorId());
        entity.setWeight(mongoZixun.getWeight());
        entity.setStatus(mongoZixun.getStatus());
        entity.setScore(mongoZixun.getScore());
        entity.setCreatedTime(mongoZixun.getCreatedTime());
        entity.setUpdatedTime(mongoZixun.getUpdatedTime());
        entity.setPublishedTime(mongoZixun.getPublishedTime());
        if (mongoZixun instanceof MongoArticle) {
            MongoArticle mongoArticle = (MongoArticle) mongoZixun;
            Article article = (Article) entity;
            article.setContent(mongoArticle.getContent());
            return article;
        } else if (mongoZixun instanceof MongoQuestion) {
            MongoQuestion mongoQuestion = (MongoQuestion) mongoZixun;
            Question question = (Question) entity;
            question.setQuestion(mongoQuestion.getQuestion());
            question.setAnswer(mongoQuestion.getAnswer());
            return question;
        } else {
            throw new RuntimeException("Not A Legal Subclass Of MongoZixun");
        }
    }

}
