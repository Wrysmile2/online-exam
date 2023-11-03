package com.xyh.service;

import com.xyh.vo.response.admin.AdminIndexRespVO;
import com.xyh.vo.response.teacher.IndexVO;

public interface IndexService {

    AdminIndexRespVO getAdminIndex();

    IndexVO getTeacherIndex();

}
