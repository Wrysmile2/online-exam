package com.xyh.vo.response.student;

import lombok.Data;

import java.util.List;

@Data
public class ChartVO {

    private List<String> Xdata;

    private List<Integer> Ydata;
}
