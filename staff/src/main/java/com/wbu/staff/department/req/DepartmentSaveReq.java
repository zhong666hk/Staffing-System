package com.wbu.staff.department.req;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class DepartmentSaveReq {

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

    /**
     * 
     */
    private String parentDepartment;

    /**
     * 
     */
    private Integer isparent;

    /**
     * 部门总人数
     */
    private Integer count;
}
