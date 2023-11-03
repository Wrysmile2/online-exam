package com.xyh.vo.response.admin;

import com.xyh.vo.request.other.PageVO;
import lombok.Data;

/**
 * 添加试卷题目的返回的VO
 */

@Data
public class AddQuestionPageRespVO extends PageVO {

    private Integer id;
    private String questionName;
    private Integer questionType;
    private Double questionScore;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

}
