package com.wbu.staff.employee.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeQueryResp {

    /**
     * id
     */
    private Long id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "【密码】不能为空")
    private String password;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 是否删除(0：没有删除1：已经删除)
     */
    private Integer isDelete;

    private String name;

    private String workId;

}
