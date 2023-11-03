package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.admin.MessageReqVO;
import com.xyh.vo.request.admin.MessageSendReqVO;
import com.xyh.vo.request.other.PageVO;
import com.xyh.vo.response.admin.MessageRespVO;
import com.xyh.vo.response.student.StuMessageRespVO;

/**
* @author xyh
* @description 针对表【tbl_message】的数据库操作Service
* @createDate 2022-12-27 22:26:14
*/
public interface MessageService extends IService<Message> {
    IPage<MessageRespVO> getPageList(MessageReqVO vo);

    void sendMessage(MessageSendReqVO vo,Integer createUser);

    void readMessage(Integer id);


    IPage<StuMessageRespVO> selectStuList(PageVO vo, Integer userId);
}
