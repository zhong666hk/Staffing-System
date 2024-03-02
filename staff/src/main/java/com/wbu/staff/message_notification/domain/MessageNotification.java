package com.wbu.staff.message_notification.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName message_notification
 */
@TableName(value ="message_notification")
@Data
public class MessageNotification implements Serializable {
    /**
     * 
     */
    @TableId
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MessageNotification other = (MessageNotification) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMid() == null ? other.getMid() == null : this.getMid().equals(other.getMid()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getAcceptId() == null ? other.getAcceptId() == null : this.getAcceptId().equals(other.getAcceptId()))
            && (this.getSendId() == null ? other.getSendId() == null : this.getSendId().equals(other.getSendId()))
            && (this.getState() == null ? other.getState() == null : this.getState().equals(other.getState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMid() == null) ? 0 : getMid().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getAcceptId() == null) ? 0 : getAcceptId().hashCode());
        result = prime * result + ((getSendId() == null) ? 0 : getSendId().hashCode());
        result = prime * result + ((getState() == null) ? 0 : getState().hashCode());
        return result;
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}