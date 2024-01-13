package com.wbu.staff.employee.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeRegisterReq {
    /**
     * @NotEmpty 用在集合类上面
     * @NotBlank 用在String上面
     * @NotNull 用在基本类型上
     */
    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$" ,message = "手机号不符合规范(1[3-9]XXX)")
    private String mobile;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^[a-zA-Z]\\w{6,15}$" ,message = "以字母开头，长度在6~15之间，只能包含字母、数字和下划线")
    private String password;
}
