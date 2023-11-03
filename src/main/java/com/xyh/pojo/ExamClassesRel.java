package com.xyh.pojo;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName tbl_exam_classes_rel
 */
@Data
public class ExamClassesRel implements Serializable {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 班级Id
     */
    private Integer classesId;

    /**
     * 试卷Id
     */
    private Integer examId;

    private static final long serialVersionUID = 1L;
}