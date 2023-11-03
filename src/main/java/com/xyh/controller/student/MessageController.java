package com.xyh.controller.student;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.service.MessageService;
import com.xyh.service.MessageUserService;
import com.xyh.vo.request.other.PageVO;
import com.xyh.vo.response.student.StuMessageRespVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/student/message/")
@RestController("studentMessageController")
@Api(value = "学生对公告信息的处理", tags = "公告信息管理API")
public class MessageController {

    private final MessageService messageService;
    private final MessageUserService messageUserService;
    private final WebContext webContext;

    @Autowired
    public MessageController(MessageService messageService, MessageUserService messageUserService,WebContext webContext) {
        this.messageService = messageService;
        this.messageUserService = messageUserService;
        this.webContext = webContext;
    }

    @GetMapping("getUnread")
    @ApiModelProperty("查询未读的公告")
    public ResultAPI<Integer> getUnreadCount(){
        Integer userId = webContext.getCurrentUser().getId();
        Integer count = messageUserService.selectUnreadCount(userId);
        return  new ResultAPI<>(200,"查询成功",count);
    }

    @PostMapping("list")
    @ApiModelProperty("分页查询公告信息")
    public ResultAPI<IPage<StuMessageRespVO>> getMsgList(@RequestBody PageVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        IPage<StuMessageRespVO> page = messageService.selectStuList(vo, userId);
        return new ResultAPI<>(200,"查询成功",page);
    }

    /**
     *标记消息已读
     * @param id
     * @return
     */
    @GetMapping("read/{id}")
    @ApiOperation("阅读公告信息")
    public ResultAPI<String> readMessage(@PathVariable Integer id){
        messageService.readMessage(id);
        return new ResultAPI<>(200,"","");
    }


}
