package com.xyh.vo.response.admin;

import com.xyh.vo.response.other.KeyValue;
import lombok.Data;

import java.util.List;

@Data
public class AdminIndexRespVO {
    private List<KeyValue> quesData;
    private List<KeyValue> personData;
    // X轴的数据
    private List<String> Xdata;
    // Y轴的数据
    private List<Integer> Ydata;
}
