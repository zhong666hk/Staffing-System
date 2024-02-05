package com.wbu.staff.department.resp;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DepartmentQueryResp {

    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 部门名称
     */
    private String name;

    private String parentDepartment;

    /**
     * 
     */
    private Long parentid;

    /**
     * 
     */
    private Integer isparent;

    /**
     * 部门总人数
     */
    private Integer count;
}
