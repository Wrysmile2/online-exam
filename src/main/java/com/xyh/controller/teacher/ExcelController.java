package com.xyh.controller.teacher;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/excel/")
@RestController("ExcelController")
@Api(value = "导出的API",tags = "导出的API，待开发")
public class ExcelController {

}
