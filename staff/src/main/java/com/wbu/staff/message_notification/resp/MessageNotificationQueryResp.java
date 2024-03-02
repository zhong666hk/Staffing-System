package com.wbu.staff.message_notification.resp;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MessageNotificationQueryResp {

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
    private Long acceptId;

    /**
     * 发送者
     */
    private Long sendId;

    /**
     * 0 未读 1 已读
     */
    private Integer state;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
        this.mid = mid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(Long acceptId) {
        this.acceptId = acceptId;
    }

    public Long getSendId() {
        return sendId;
    }

    public void setSendId(Long sendId) {
        this.sendId = sendId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", mid=").append(mid);
        sb.append(", type=").append(type);
        sb.append(", acceptId=").append(acceptId);
        sb.append(", sendId=").append(sendId);
        sb.append(", state=").append(state);
        sb.append("]");
        return sb.toString();
    }
}
