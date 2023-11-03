package com.xyh.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xyh.base.vo.ResultAPI;
import com.xyh.base.context.WebContext;
import com.xyh.service.MessageService;
import com.xyh.vo.request.admin.MessageReqVO;
import com.xyh.vo.request.admin.MessageSendReqVO;
import com.xyh.vo.response.admin.MessageRespVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/admin/message/")
@RestController("adminMessageController")
@Api(value = "管理人员进行公告的操作", tags = "公告信息管理API")
public class MessageController {

    private final MessageService messageService;
    private final WebContext webContext;

    @Autowired
    public MessageController(MessageService messageService, WebContext webContext) {
        this.messageService = messageService;
        this.webContext = webContext;
    }



    @PostMapping("page/list")
    public ResultAPI<IPage<MessageRespVO>> pageList(@RequestBody  MessageReqVO vo){
        return new ResultAPI<>(200,"查询成功", messageService.getPageList(vo));
    }

    @PostMapping("send")
    public ResultAPI<String> sendMessage(@RequestBody MessageSendReqVO vo){
        Integer userId = webContext.getCurrentUser().getId();
        messageService.sendMessage(vo,userId);
        return new ResultAPI<>(200,"发送成功","");
    }
}
