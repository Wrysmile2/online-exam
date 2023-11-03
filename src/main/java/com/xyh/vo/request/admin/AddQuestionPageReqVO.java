package com.xyh.vo.request.admin;

import com.xyh.vo.request.other.PageVO;
import lombok.Data;

/**
 * 添加题目弹窗的分页
 */
@Data
public class AddQuestionPageReqVO extends PageVO {

    private Integer questionType;
    private Integer subjectId;
    private String  questionName;

}
