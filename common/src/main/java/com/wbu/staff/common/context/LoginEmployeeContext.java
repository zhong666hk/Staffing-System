package com.wbu.staff.common.context;

import com.wbu.staff.common.respon.LoginResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginEmployeeContext {
    private static final Logger LOG= LoggerFactory.getLogger(LoginEmployeeContext.class);
    private static ThreadLocal<LoginResp> employee=new ThreadLocal<>();
    public static LoginResp getLoginResp(){return employee.get();}
    public static void setMember(LoginResp loginResp){
        LoginEmployeeContext.employee.set(loginResp);}

    public static Long getId(){
        try {
            return employee.get().getId();
        }catch (Exception e){
            LOG.error("获取登录会员信息失败",e.getMessage());
            throw e;
        }
    }
}
