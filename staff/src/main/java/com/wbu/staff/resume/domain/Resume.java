package com.wbu.staff.resume.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 简历
 * @TableName resume
 */
@TableName(value ="resume")
@Data
public class Resume implements Serializable {
    /**
     * id编号
     */
    @TableId
    private Long id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 性别 1为男 2为女
     */
    private String gender;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 民族
     */
    private Integer nationId;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 所属部门
     */
    private Long departmentId;

    /**
     * 职称ID
     */
    private Long jobLevelId;

    /**
     * 职位ID
     */
    private Long posId;

    /**
     * 最高学历
     */
    private Object tiptopDegree;

    /**
     * 所属专业
     */
    private String specialty;

    /**
     * 毕业院校
     */
    private String school;

    /**
     * 职业技能描述
     */
    private String vocationalSkills;

    /**
     * 自我描述
     */
    private String introduction;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    private int salary;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}