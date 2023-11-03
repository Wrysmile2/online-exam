package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Wrong;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.student.QueryReqVO;
import com.xyh.vo.response.student.WrongIndexVO;
import com.xyh.vo.response.student.WrongRespVO;

/**
* @author xyh
* @description 针对表【tbl_wrong】的数据库操作Service
* @createDate 2023-02-28 11:00:04
*/
public interface WrongService extends IService<Wrong> {

    Wrong selectByQuestionIdAndUserId(Integer questionId,Integer userId);

    IPage<WrongIndexVO> pageList(QueryReqVO vo);

    WrongRespVO selectWrongsByPage(QueryReqVO vo);
}
