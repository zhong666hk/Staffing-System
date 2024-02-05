package com.wbu.staff.department.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.department.domain.Department;
import com.wbu.staff.department.mapper.DepartmentMapper;
import com.wbu.staff.department.req.DepartmentQueryReq;
import com.wbu.staff.department.req.DepartmentSaveReq;
import com.wbu.staff.department.resp.DepartmentQueryResp;
import com.wbu.staff.department.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【department(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
        implements DepartmentService {

    @Override
    public boolean saveDepartment(DepartmentSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Department department = BeanUtil.copyProperties(req, Department.class);
        if(!ObjectUtil.isNull(req.getName())){
            //获取父id
            QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
            departmentQueryWrapper.eq("name",req.getName());
            Department parentDepartment = this.getOne(departmentQueryWrapper);
            //设置department的父id
            if (ObjectUtil.isNull(parentDepartment)){
                department.setParentid(null);
            }else {
                department.setParentid(parentDepartment.getId());
            }
        }
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(department.getId())){
            department.setId(SnowUtil.getSnowflakeNextId());
            department.setCreateTime(date);
            department.setUpdateTime(date);
            return this.save(department);
        }// id不为空说明是修改的操作
        else {
            department.setUpdateTime(date);
            return this.updateById(department);
        }
    }

    @Override
    public Page<DepartmentQueryResp> queryDepartments(DepartmentQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<Department> page = this.page(new Page<>(req.getPage(), req.getSize()), departmentQueryWrapper);
        Page<DepartmentQueryResp> departmentQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page,departmentQueryRespPage);
        return departmentQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)){
            return false;
        }
        return this.removeById(id);
    }

    @Override
    public List<DepartmentQueryResp> queryAll() {
        QueryWrapper<Department> departmentQueryWrapper = new QueryWrapper<>();
        departmentQueryWrapper.orderByAsc("name");
        List<Department> list = this.list(departmentQueryWrapper);
        return BeanUtil.copyToList(list, DepartmentQueryResp.class);
    }
}




