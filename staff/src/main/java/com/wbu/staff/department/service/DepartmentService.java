package com.wbu.staff.department.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.department.domain.Department;
import com.wbu.staff.department.req.DepartmentQueryReq;
import com.wbu.staff.department.req.DepartmentSaveReq;
import com.wbu.staff.department.resp.DepartmentQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【department(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface DepartmentService extends IService<Department> {
    public boolean saveDepartment(DepartmentSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<DepartmentQueryResp> queryDepartments(DepartmentQueryReq req);

    /**
     * 删除department 通过id
     * @param id
     */
    public boolean  deleteById(Long id);

    List<DepartmentQueryResp> queryAll();
}