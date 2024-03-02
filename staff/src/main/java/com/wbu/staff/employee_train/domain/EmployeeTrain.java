package com.wbu.staff.employee_train.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName employee_train
 */
@TableName(value ="employee_train")
@Data
public class EmployeeTrain implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 员工编号
     */
    private Long eid;

    /**
     * 培训开始日期
     */
    private Date trainStartDate;

    /**
     * 培训结束日期
     */
    private Date trainFinishDate;

    /**
     * 培训内容
     */
    private String trainContent;

    /**
     * 培训状态
     */
    private Integer trainStatus;

    /**
     * 备注
     */
    private String remark;

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
        EmployeeTrain other = (EmployeeTrain) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEid() == null ? other.getEid() == null : this.getEid().equals(other.getEid()))
            && (this.getTrainStartDate() == null ? other.getTrainStartDate() == null : this.getTrainStartDate().equals(other.getTrainStartDate()))
            && (this.getTrainFinishDate() == null ? other.getTrainFinishDate() == null : this.getTrainFinishDate().equals(other.getTrainFinishDate()))
            && (this.getTrainContent() == null ? other.getTrainContent() == null : this.getTrainContent().equals(other.getTrainContent()))
            && (this.getTrainStatus() == null ? other.getTrainStatus() == null : this.getTrainStatus().equals(other.getTrainStatus()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEid() == null) ? 0 : getEid().hashCode());
        result = prime * result + ((getTrainStartDate() == null) ? 0 : getTrainStartDate().hashCode());
        result = prime * result + ((getTrainFinishDate() == null) ? 0 : getTrainFinishDate().hashCode());
        result = prime * result + ((getTrainContent() == null) ? 0 : getTrainContent().hashCode());
        result = prime * result + ((getTrainStatus() == null) ? 0 : getTrainStatus().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", eid=").append(eid);
        sb.append(", trainStartDate=").append(trainStartDate);
        sb.append(", trainFinishDate=").append(trainFinishDate);
        sb.append(", trainContent=").append(trainContent);
        sb.append(", trainStatus=").append(trainStatus);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}