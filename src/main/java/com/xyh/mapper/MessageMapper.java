package com.xyh.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyh.vo.request.admin.MessageReqVO;
import com.xyh.vo.response.admin.MessageRespVO;
import com.xyh.vo.response.student.StuMessageRespVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author 肖远华
* @description 针对表【tbl_message】的数据库操作Mapper
* @createDate 2022-12-27 22:26:14
* @Entity com.xyh.pojo.Message
*/
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    IPage<MessageRespVO> getByPage(IPage<MessageRespVO> page, @Param("vo") MessageReqVO vo);

    void updateReadCount(Integer messageId);

    IPage<StuMessageRespVO> selectStuList(IPage<StuMessageRespVO> page,@Param("userId") Integer userId);
}




