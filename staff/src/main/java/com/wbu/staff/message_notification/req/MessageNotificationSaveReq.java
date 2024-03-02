package com.wbu.staff.message_notification.req;


import lombok.Data;

@Data
public class MessageNotificationSaveReq {

    /**
     *
     */
    private Long id;

    /**
     * 消息id
     */
    private Long mid;

    /**
     * 0表示群发消息
     */
    private Integer type;

    /**
     * 接受者
     */
    private String acceptName;

    /**
     * 发送者
     */
    private String sendName;

    /**
     * 0 未读 1 已读
     */
    private Integer state;

    private String title;

    private String message;

}
