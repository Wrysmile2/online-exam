package com.xyh.mapper;

import com.xyh.pojo.ExamClassesRel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 肖远华
* @description 针对表【tbl_exam_classes_rel】的数据库操作Mapper
* @createDate 2023-03-15 13:28:29
* @Entity com.xyh.pojo.ExamClassesRel
*/
@Mapper
public interface ExamClassesRelMapper extends BaseMapper<ExamClassesRel> {

    void saveBatch(List<ExamClassesRel> examClassesRels);

    Integer selectCountByCondition(@Param("examId") Integer examId,@Param("classesId") Integer classesId);

    //删除试卷的时候，需要将这里的联系删掉
    @Delete("delete from tbl_exam_classes_rel where exam_id = #{examId}")
    void delRel(Integer examId);
}




