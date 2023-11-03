package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Log;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.admin.LogReqVO;
import com.xyh.vo.response.admin.LogRespVO;

/**
* @author xyh
* @description 针对表【tbl_log】的数据库操作Service
* @createDate 2022-12-27 22:26:14
*/
public interface LogService extends IService<Log> {

    IPage<LogRespVO> getPageList(LogReqVO vo);

     IPage<Log> getUserLog(Integer userId) ;
}
