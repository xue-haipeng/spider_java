package com.solomon.vo;

import com.solomon.vo.FormData;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by xuehaipeng on 2017/6/22.
 */
@Document(indexName = "article_form")
public class ArticleForm extends FormData {
    @Id
    private String id;
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
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", content2='" + content2 + '\'' +
                "} " + super.toString();
    }
}
