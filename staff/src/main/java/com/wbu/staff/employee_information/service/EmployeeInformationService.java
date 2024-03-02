package com.wbu.staff.employee_information.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.employee_information.domain.EmployeeInformation;
import com.wbu.staff.employee_information.req.EmployeeInformationQueryReq;
import com.wbu.staff.employee_information.req.EmployeeInformationSaveReq;
import com.wbu.staff.employee_information.resp.EmployeeInformationQueryResp;

import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【employeeInformation(乘车人)】的数据库操作Service
 * @createDate 2023-11-14 14:43:47
 */
public interface EmployeeInformationService extends IService<EmployeeInformation> {
    public boolean saveEmployeeInformation(EmployeeInformationSaveReq req);

    /**
     * 查询当前登录用户的购票
     *
     * @param req
     * @return
     */
    public Page<EmployeeInformationQueryResp> queryEmployeeInformations(EmployeeInformationQueryReq req);

    /**
     * 删除employeeInformation 通过id
     *
     * @param id
     */
    public boolean deleteById(Long id);

    EmployeeInformation queryEmployeeInformationByName(String name);

    List<EmployeeInformationQueryResp> queryAll();
}