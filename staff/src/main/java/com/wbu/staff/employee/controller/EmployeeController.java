package com.wbu.staff.employee.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.LoginResp;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.employee.req.*;
import com.wbu.staff.employee.resp.EmployeeQueryResp;
import com.wbu.staff.employee.service.EmployeeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    public static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody EmployeeSaveReq employeeSaveReq) {
        if (ObjectUtil.isEmpty(employeeSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try {
            if (employeeService.saveEmployee(employeeSaveReq)) {
                return CommonRespond.succeed("员工添加或修改成功！！！", true);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return CommonRespond.error(3000, "操作异常");
        }
        return CommonRespond.error(3000, "操作异常");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<EmployeeQueryResp>> query_list(@Valid EmployeeQueryReq employeeQueryReq) {

        Page<EmployeeQueryResp> page = employeeService.queryEmployees(employeeQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (employeeService.deleteById(id)) {
            return CommonRespond.succeed("删除成功", true);
        }
        return CommonRespond.error(3000, "操作异常");
    }

    @LogAnnotation
    @PostMapping("/register")
    public CommonRespond<Long> register(@RequestBody @Valid EmployeeRegisterReq employeeRegisterReq) throws Exception {
        if (ObjectUtil.isEmpty(employeeRegisterReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        return employeeService.register(employeeRegisterReq);
    }

    @LogAnnotation
    @PostMapping("/login")
    public CommonRespond<LoginResp> Login(@Valid @RequestBody EmployeeLoginReq employeeLoginReq) throws Exception {
        if (ObjectUtil.isNull(employeeLoginReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        return employeeService.login(employeeLoginReq);

    }


    @LogAnnotation
    @PostMapping("/resetPassword")
    public CommonRespond<Object> resetPassword(@Valid @RequestBody ResetEmployeePassword resetEmployeePassword) throws Exception {
        if (ObjectUtil.isNull(resetEmployeePassword)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        return employeeService.resetPassword(resetEmployeePassword);
    }

    @LogAnnotation
    @GetMapping("/query_all")
    public CommonRespond<List<EmployeeQueryResp>> queryAll() {
        List<EmployeeQueryResp> employeeInformationList = employeeService.queryAll();
        return CommonRespond.succeed(employeeInformationList);
    }
}
