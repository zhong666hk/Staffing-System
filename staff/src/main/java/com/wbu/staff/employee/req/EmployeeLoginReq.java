package com.wbu.staff.employee.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployeeLoginReq {
    /**
     * 手机号
     */
    @NotBlank(message = "【手机】不能为空")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "【密码】不能为空")
    private String password;
}
