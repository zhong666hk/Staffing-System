package com.wbu.staff.employee.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.employee.domain.Employee;
import com.wbu.staff.employee.req.*;
import com.wbu.staff.employee.resp.EmployeeQueryResp;
import com.wbu.staff.common.respon.LoginResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【employee(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface EmployeeService extends IService<Employee> {
    public boolean saveEmployee(EmployeeSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<EmployeeQueryResp> queryEmployees(EmployeeQueryReq req);

    /**
     * 删除employee 通过id
     * @param id
     */
    public boolean  deleteById(Long id);

    /**
     * 注册账号
     * @param employeeRegisterReq
     * @return
     */
    CommonRespond<Long> register(EmployeeRegisterReq employeeRegisterReq) throws Exception;

    CommonRespond<LoginResp> login(EmployeeLoginReq employeeLoginReq) throws Exception;

    CommonRespond<Object> resetPassword(ResetEmployeePassword resetEmployeePassword) throws Exception;

    Employee queryEmployeeByName(String name);

    Employee registerEmployee(EmployeeResumeReq employeeResumeReq) throws Exception;

    List<EmployeeQueryResp> queryAll();
}