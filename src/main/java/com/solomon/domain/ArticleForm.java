package com.solomon.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by xuehaipeng on 2017/6/22.
 */
public class ArticleForm extends FormData {
    @NotBlank
    private String content;
    private String content2;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "content='" + content + '\'' +
                ", content2='" + content2 + '\'' +
                '}';
    }
}
