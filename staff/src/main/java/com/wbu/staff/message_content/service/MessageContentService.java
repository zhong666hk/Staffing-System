package com.wbu.staff.message_content.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.message_content.domain.MessageContent;
import com.wbu.staff.message_content.req.MessageContentQueryReq;
import com.wbu.staff.message_content.req.MessageContentSaveReq;
import com.wbu.staff.message_content.resp.MessageContentQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【messageContent(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface MessageContentService extends IService<MessageContent> {
    public boolean saveMessageContent(MessageContentSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<MessageContentQueryResp> queryMessageContents(MessageContentQueryReq req);

    /**
     * 删除messageContent 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}