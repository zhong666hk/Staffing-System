package com.wbu.staff.salary.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SalaryQueryResp {

    /**
     *
     */
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 员工id
     */
    private String employeeName;

}
