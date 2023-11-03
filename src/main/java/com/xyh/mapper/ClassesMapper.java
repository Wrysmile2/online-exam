package com.xyh.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Classes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.request.teacher.ClassesReqVO;
import com.xyh.vo.request.teacher.CountVO;
import com.xyh.vo.response.teacher.ClassesRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author 肖远华
* @description 针对表【tbl_classes】的数据库操作Mapper
* @createDate 2023-03-15 11:55:53
* @Entity com.xyh.pojo.Classes
*/
@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {

    List<String> selectByExamId(Integer examId);

    IPage<ClassesRespVO> selectPageList(IPage<ClassesRespVO> page, @Param("vo") ClassesReqVO vo);

    void delBatch(List<Integer> ids);

    List<CountVO> selectCountByClassId();

}




