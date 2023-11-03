package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.pojo.Subject;
import com.xyh.vo.request.admin.SubjectReqVO;
import com.xyh.vo.response.admin.SubjectRespVO;
import com.xyh.vo.response.other.KeyValue;

import java.util.List;

/**
* @author xyh
* @description 针对表【tbl_subject】的数据库操作Service
* @createDate 2022-12-27 22:19:07
*/
public interface SubjectService extends IService<Subject> {

    IPage<SubjectRespVO> getPageList(SubjectReqVO vo);

    void delByIds(Integer[] ids);

    boolean editSubject(Subject subject);

    List<KeyValue> getSubjectName(String subjectName);

    List<KeyValue> selectSubjects();

    Subject getSubjectById(Integer id);
}
