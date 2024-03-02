package com.wbu.staff.message_content.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.message_content.domain.MessageContent;
import com.wbu.staff.message_content.req.MessageContentQueryReq;
import com.wbu.staff.message_content.req.MessageContentSaveReq;
import com.wbu.staff.message_content.resp.MessageContentQueryResp;
import com.wbu.staff.message_content.service.MessageContentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageContent")
public class MessageContentController {
    public static final Logger LOG = LoggerFactory.getLogger(MessageContentController.class);

    @Autowired
    private MessageContentService messageContentService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody MessageContentSaveReq messageContentSaveReq) {
        if (ObjectUtil.isEmpty(messageContentSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (messageContentService.saveMessageContent(messageContentSaveReq)) {
                return CommonRespond.succeed("添加或修改成功！！！",true);
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
            return CommonRespond.error(30000,"添加或修改失败");
        }
        return CommonRespond.error(30000,"添加或修改失败");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<MessageContentQueryResp>> query_list(@Valid MessageContentQueryReq messageContentQueryReq) {

        Page<MessageContentQueryResp> page = messageContentService.queryMessageContents(messageContentQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (messageContentService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }
}
