package com.wbu.staff.message_notification.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.message_notification.domain.MessageNotification;
import com.wbu.staff.message_notification.req.MessageNotificationQueryReq;
import com.wbu.staff.message_notification.req.MessageNotificationSaveReq;
import com.wbu.staff.message_notification.resp.MessageNotificationQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【messageNotification(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface MessageNotificationService extends IService<MessageNotification> {
    public boolean saveMessageNotification(MessageNotificationSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<MessageNotificationQueryResp> queryMessageNotifications(MessageNotificationQueryReq req);

    /**
     * 删除messageNotification 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}