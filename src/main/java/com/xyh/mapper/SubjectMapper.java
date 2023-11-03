package com.xyh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.pojo.Subject;
import com.xyh.vo.response.other.KeyValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_subject】的数据库操作Mapper
* @createDate 2022-12-27 22:19:07
* @Entity com.xyh.pojo.Subject
*/
@Mapper
public interface SubjectMapper extends BaseMapper<Subject> {


    List<KeyValue> selectSubjectName(String subjectName);

    /**
     *
     * @param
     * @return
     */
    List<KeyValue> selectSubjects();
}
