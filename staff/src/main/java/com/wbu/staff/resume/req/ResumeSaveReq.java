package com.wbu.staff.resume.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
@Data
public class ResumeSaveReq {

    /**
     * id编号
     */
    private Long id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 性别 1为男 2为女
     */
    @NotBlank(message = "【性别 1为男 2为女】不能为空")
    private String gender;

    /**
     * 出生日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 民族
     */
    private String nationName;

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
    private String departmentName;

    /**
     * 职称ID
     */
    private String jobLevelName;

    /**
     * 职位ID
     */
    private String posName;

    /**
     * 最高学历
     */
    private String tiptopDegree;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private int salary;

}
