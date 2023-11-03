package com.xyh.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.pojo.Classes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyh.vo.request.teacher.ClassesReqVO;
import com.xyh.vo.response.teacher.ClassesRespVO;

import java.util.List;

/**
* @author 肖远华
* @description 针对表【tbl_classes】的数据库操作Service
* @createDate 2023-03-15 11:55:53
*/
public interface ClassesService extends IService<Classes> {

    List<Classes> selectByClassName(String classesName);

    IPage<ClassesRespVO> selectPage(ClassesReqVO vo);

    void edit(Classes vo, Integer id);

    void delBatch(List<Integer> ids);

    Classes getClassById(Integer id);
}
