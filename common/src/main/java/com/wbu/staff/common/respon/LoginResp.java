package com.wbu.staff.common.respon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResp {
    private boolean loginState;
    private String token;
    private Long id;
    private String mobile;
    private String password;

    public LoginResp(boolean loginState, String token) {
        this.loginState = loginState;
        this.token = token;
    }
}
