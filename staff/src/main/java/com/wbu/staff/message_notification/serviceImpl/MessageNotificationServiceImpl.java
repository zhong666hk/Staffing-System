package com.wbu.staff.message_notification.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.context.LoginEmployeeContext;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.employee_information.domain.EmployeeInformation;
import com.wbu.staff.employee_information.service.EmployeeInformationService;
import com.wbu.staff.message_content.domain.MessageContent;
import com.wbu.staff.message_content.service.MessageContentService;
import com.wbu.staff.message_notification.domain.MessageNotification;
import com.wbu.staff.message_notification.mapper.MessageNotificationMapper;
import com.wbu.staff.message_notification.req.MessageNotificationQueryReq;
import com.wbu.staff.message_notification.req.MessageNotificationSaveReq;
import com.wbu.staff.message_notification.resp.MessageNotificationQueryResp;
import com.wbu.staff.message_notification.service.MessageNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 钟正保
 * @description 针对表【messageNotification(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */

@Service
public class MessageNotificationServiceImpl extends ServiceImpl<MessageNotificationMapper, MessageNotification>
        implements MessageNotificationService {

    @Autowired
    private MessageContentService messageContentService;
    @Autowired
    private EmployeeInformationService employeeInformationService;

    @Override
    public boolean saveMessageNotification(MessageNotificationSaveReq req) {

        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }

        // 拷贝类
        MessageNotification messageNotification = BeanUtil.copyProperties(req, MessageNotification.class);
        MessageContent messageContent = BeanUtil.copyProperties(req, MessageContent.class);
        //判断是否是群发消息还是个人消息 将接收者和发送者的姓名换成id
        if (req.getType()== 1){
            //个人消息
            EmployeeInformation accept = employeeInformationService.queryEmployeeInformationByName(req.getAcceptName());
            messageNotification.setAcceptId(accept.getId());
        }

        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(messageNotification.getId())) {
            messageNotification.setId(SnowUtil.getSnowflakeNextId());
            messageContent.setId(SnowUtil.getSnowflakeNextId());
            messageContent.setCreatedate(date);
            //设置消息id
            messageNotification.setMid(messageContent.getId());
            //设置发送方的id
            messageNotification.setSendId(LoginEmployeeContext.getId());
            //添加
            return this.save(messageNotification) && messageContentService.save(messageContent);

        }// id不为空说明是修改的操作
        else {
            //修改对应消息 ---消息的id还是通知的id
            messageContent.setId(req.getMid());
            return this.updateById(messageNotification) && messageContentService.save(messageContent);
        }
    }

    @Override
    public Page<MessageNotificationQueryResp> queryMessageNotifications(MessageNotificationQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<MessageNotification> messageNotificationQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<MessageNotification> page = this.page(new Page<>(req.getPage(), req.getSize()), messageNotificationQueryWrapper);
        Page<MessageNotificationQueryResp> messageNotificationQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page, messageNotificationQueryRespPage);
        return messageNotificationQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        //先获取消息的id 再删除消息 最后删除记录
        MessageNotification messageNotification = this.getById(id);
        return messageContentService.deleteById(messageNotification.getMid()) && this.removeById(id);
    }
}




