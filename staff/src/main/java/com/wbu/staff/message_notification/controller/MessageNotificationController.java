package com.wbu.staff.message_notification.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.message_notification.domain.MessageNotification;
import com.wbu.staff.message_notification.req.MessageNotificationQueryReq;
import com.wbu.staff.message_notification.req.MessageNotificationSaveReq;
import com.wbu.staff.message_notification.resp.MessageNotificationQueryResp;
import com.wbu.staff.message_notification.service.MessageNotificationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageNotification")
public class MessageNotificationController {
    public static final Logger LOG = LoggerFactory.getLogger(MessageNotificationController.class);

    @Autowired
    private MessageNotificationService messageNotificationService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody MessageNotificationSaveReq messageNotificationSaveReq) {
        if (ObjectUtil.isEmpty(messageNotificationSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (messageNotificationService.saveMessageNotification(messageNotificationSaveReq)) {
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
    public CommonRespond<Page<MessageNotificationQueryResp>> query_list(@Valid MessageNotificationQueryReq messageNotificationQueryReq) {
        // todo 响应将消息内容返回出去
        Page<MessageNotificationQueryResp> page = messageNotificationService.queryMessageNotifications(messageNotificationQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (messageNotificationService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }
}
