package com.wbu.staff.employee.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class ResetEmployeePassword {
    /**
     * id
     */
    private Long id;

    /**
     * 新密码
     */
    @NotBlank(message = "【新密码】不能为空")
    @Pattern(regexp = "^[a-zA-Z]\\w{6,15}$" ,message = "以字母开头，长度在6~15之间，只能包含字母、数字和下划线")
    private String newPassword;

    /**
     * 确认新密码
     */
    @NotBlank(message = "【确认新密码】不能为空")
    @Pattern(regexp = "^[a-zA-Z]\\w{6,15}$" ,message = "以字母开头，长度在6~15之间，只能包含字母、数字和下划线")
    private String newPasswordAgain;
    /**
     *旧密码
     */
    @NotBlank(message = "【旧密码】不能为空")
    private String oldPassword;
}
