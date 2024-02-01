package com.wbu.staff.common.interceptor;

import cn.hutool.json.JSONObject;
import com.wbu.staff.common.context.LoginEmployeeContext;
import com.wbu.staff.common.respon.LoginResp;
import com.wbu.staff.common.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 拦截信息 --->将登录的token信息解析放到ThreadLocal中
 */
@Component
public class EmployeeInterceptor implements HandlerInterceptor {
    private final Logger LOG = LoggerFactory.getLogger(EmployeeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        LOG.info("token为{}", token);
        JSONObject jsonObject = JwtUtil.getJSONObject(token);
        LoginResp loginResp = jsonObject.toBean(LoginResp.class);
        LOG.warn("当前登录会员{}", loginResp);
        // 设置当前会员的参数
        LoginEmployeeContext.setMember(loginResp);
        return true;
    }
}
