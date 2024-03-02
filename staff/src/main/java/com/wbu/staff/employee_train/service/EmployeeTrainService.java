package com.wbu.staff.employee_train.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.employee_train.domain.EmployeeTrain;
import com.wbu.staff.employee_train.req.EmployeeTrainQueryReq;
import com.wbu.staff.employee_train.req.EmployeeTrainSaveReq;
import com.wbu.staff.employee_train.resp.EmployeeTrainQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【employeeTrain(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface EmployeeTrainService extends IService<EmployeeTrain> {
    public boolean saveEmployeeTrain(EmployeeTrainSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<EmployeeTrainQueryResp> queryEmployeeTrains(EmployeeTrainQueryReq req);

    /**
     * 删除employeeTrain 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}