package com.wbu.staff.salary.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName salary
 */
@TableName(value ="salary")
@Data
public class Salary implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 基本工资
     */
    private int basicSalary;

    /**
     * 奖金
     */
    private int bonus;

    /**
     * 午餐补助
     */
    private int lunchSalary;

    /**
     * 交通补助
     */
    private int trafficSalary;

    /**
     * 应发工资
     */
    private int allSalary;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 员工id
     */
    private Long employeeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}