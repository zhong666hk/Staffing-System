package com.wbu.staff.salary.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.employee.domain.Employee;
import com.wbu.staff.employee.service.EmployeeService;
import com.wbu.staff.salary.domain.Salary;
import com.wbu.staff.salary.mapper.SalaryMapper;
import com.wbu.staff.salary.req.SalaryQueryReq;
import com.wbu.staff.salary.req.SalarySaveReq;
import com.wbu.staff.salary.resp.SalaryQueryResp;
import com.wbu.staff.salary.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【salary(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary>
        implements SalaryService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public boolean saveSalary(SalarySaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Salary salary = BeanUtil.copyProperties(req, Salary.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(salary.getId())){
            salary.setId(SnowUtil.getSnowflakeNextId());
            salary.setCreateTime(date);
            salary.setUpdateTime(date);
            return this.save(salary);
        }// id不为空说明是修改的操作
        else {
            salary.setUpdateTime(date);
            return this.updateById(salary);
        }
    }

    @Override
    public Page<SalaryQueryResp> querySalarys(SalaryQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Salary> salaryQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<Salary> page = this.page(new Page<>(req.getPage(), req.getSize()), salaryQueryWrapper);
        Page<SalaryQueryResp> salaryQueryRespPage = new Page<>();
        List<Salary> salaryList = page.getRecords();
        List<SalaryQueryResp> salaryQueryRespList = BeanUtil.copyToList(salaryList, SalaryQueryResp.class);
        for (int i = 0; i < salaryList.size(); i++) {
            SalaryQueryResp salaryQueryResp = salaryQueryRespList.get(i);
            Salary salary = salaryList.get(i);
            //信息的查询封装
            Employee employee = employeeService.getById(salary.getEmployeeId());
            salaryQueryResp.setEmployeeName(employee.getName());
        }
        salaryQueryRespPage.setRecords(salaryQueryRespList);
        return salaryQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)){
            return false;
        }
        return this.removeById(id);
    }
}




