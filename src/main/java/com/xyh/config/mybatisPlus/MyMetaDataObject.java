package com.xyh.config.mybatisPlus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * MybatisPlus对于公共字段的处理
 * @TODO 对于创建人后续再弄
 */
@Slf4j
@Component
public class MyMetaDataObject implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",new Date());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

    }
}
