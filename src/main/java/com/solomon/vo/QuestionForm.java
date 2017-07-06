package com.solomon.vo;

import com.solomon.vo.FormData;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by xuehaipeng on 2017/7/4.
 */
public class QuestionForm extends FormData {
    @NotBlank
    private String question;
    private String answer;

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
        return "QuestionForm{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }
}
