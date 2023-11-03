package com.xyh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Wrong;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.WrongDataItem;
import com.xyh.vo.response.student.WrongIndexVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author xyh
* @description 针对表【tbl_wrong】的数据库操作Mapper
* @createDate 2023-02-28 11:00:04
* @Entity com.xyh.pojo.Wrong
*/
@Mapper
public interface WrongMapper extends BaseMapper<Wrong> {

    Wrong selectByQuestionIdAndCreateUser(@Param("questionId")Integer questionId,@Param("createUser")Integer createUser);

    IPage<WrongIndexVO> selectWrongList(IPage<WrongIndexVO> page, @Param("vo") QueryReqVO vo);

    IPage<WrongDataItem> selectWrong(IPage<WrongDataItem> page, @Param("vo") QueryReqVO vo);
}




