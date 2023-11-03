package com.xyh.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来返回统一格式的类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultAPI<T> {

    private Integer code; // 响应码

    private String message; //响应信息

    private T data;   //响应的数据
}
