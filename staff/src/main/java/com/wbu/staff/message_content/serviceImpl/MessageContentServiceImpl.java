package com.wbu.staff.message_content.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.message_content.domain.MessageContent;
import com.wbu.staff.message_content.mapper.MessageContentMapper;
import com.wbu.staff.message_content.req.MessageContentQueryReq;
import com.wbu.staff.message_content.req.MessageContentSaveReq;
import com.wbu.staff.message_content.resp.MessageContentQueryResp;
import com.wbu.staff.message_content.service.MessageContentService;
import org.springframework.stereotype.Service;

/**
 * @author 钟正保
 * @description 针对表【messageContent(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class MessageContentServiceImpl extends ServiceImpl<MessageContentMapper, MessageContent>
        implements MessageContentService {

    @Override
    public boolean saveMessageContent(MessageContentSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        MessageContent messageContent = BeanUtil.copyProperties(req, MessageContent.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(messageContent.getId())) {
            messageContent.setId(SnowUtil.getSnowflakeNextId());
            messageContent.setCreatedate(date);
            return this.save(messageContent);
        }// id不为空说明是修改的操作
        else {
            return this.updateById(messageContent);
        }
    }

    @Override
    public Page<MessageContentQueryResp> queryMessageContents(MessageContentQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<MessageContent> messageContentQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<MessageContent> page = this.page(new Page<>(req.getPage(), req.getSize()), messageContentQueryWrapper);
        Page<MessageContentQueryResp> messageContentQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page, messageContentQueryRespPage);
        return messageContentQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }
}




