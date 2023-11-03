package com.xyh.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 题目练习表
 * @TableName tbl_question_practice
 */
public class QuestionPractice implements Serializable {
    /**
     * 主键编号
     */
    @TableId
    private Integer id;

    /**
     * 练习作答答案
     */
    private String practiceAnswer;

    /**
     * 学科Id
     */
    private Integer subjectId;

    /**
     * 题目编号
     */
    private Integer questionId;

    /**
     * 练习作答者
     */
    private Integer createUser;

    /**
     * 是否正确，0代表错误 1代表正确
     */
    private Boolean correct;

    /**
     * 作答时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 主键编号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 练习作答答案
     */
    public String getPracticeAnswer() {
        return practiceAnswer;
    }

    /**
     * 练习作答答案
     */
    public void setPracticeAnswer(String practiceAnswer) {
        this.practiceAnswer = practiceAnswer;
    }

    /**
     * 题目编号
     */
    public Integer getQuestionId() {
        return questionId;
    }

    /**
     * 题目编号
     */
    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    /**
     * 练习作答者
     */
    public Integer getCreateUser() {
        return createUser;
    }

    /**
     * 练习作答者
     */
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    /**
     * 是否正确，0代表错误 1代表正确
     */
    public Boolean getCorrect() {
        return correct;
    }

    /**
     * 是否正确，0代表错误 1代表正确
     */
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    /**
     * 作答时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 作答时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        QuestionPractice other = (QuestionPractice) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPracticeAnswer() == null ? other.getPracticeAnswer() == null : this.getPracticeAnswer().equals(other.getPracticeAnswer()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getCorrect() == null ? other.getCorrect() == null : this.getCorrect().equals(other.getCorrect()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPracticeAnswer() == null) ? 0 : getPracticeAnswer().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCorrect() == null) ? 0 : getCorrect().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", practiceAnswer=").append(practiceAnswer);
        sb.append(", questionId=").append(questionId);
        sb.append(", createUser=").append(createUser);
        sb.append(", correct=").append(correct);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}