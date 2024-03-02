package com.wbu.staff.salary.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.salary.domain.Salary;
import com.wbu.staff.salary.req.SalaryQueryReq;
import com.wbu.staff.salary.req.SalarySaveReq;
import com.wbu.staff.salary.resp.SalaryQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【salary(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface SalaryService extends IService<Salary> {
    public boolean saveSalary(SalarySaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<SalaryQueryResp> querySalarys(SalaryQueryReq req);

    /**
     * 删除salary 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}