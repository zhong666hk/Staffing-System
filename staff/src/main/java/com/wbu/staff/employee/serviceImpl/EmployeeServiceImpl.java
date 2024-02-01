package com.wbu.staff.employee.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.exception.MyException;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.common.util.JwtUtil;
import com.wbu.staff.common.util.PasswordUtil;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.employee.domain.Employee;
import com.wbu.staff.employee.mapper.EmployeeMapper;
import com.wbu.staff.employee.req.EmployeeLoginReq;
import com.wbu.staff.employee.req.EmployeeQueryReq;
import com.wbu.staff.employee.req.EmployeeRegisterReq;
import com.wbu.staff.employee.req.EmployeeSaveReq;
import com.wbu.staff.employee.resp.EmployeeQueryResp;
import com.wbu.staff.common.respon.LoginResp;
import com.wbu.staff.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee>
        implements EmployeeService {

    @Value("${password.key}")
    private String key;

    @Override
    public boolean saveEmployee(EmployeeSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Employee employee = BeanUtil.copyProperties(req, Employee.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(employee.getId())){
            employee.setId(SnowUtil.getSnowflakeNextId());
            employee.setCreateTime(date);
            return this.save(employee);
        }// id不为空说明是修改的操作
        else {
            return this.updateById(employee);
        }
    }

    @Override
    public Page<EmployeeQueryResp> queryEmployees(EmployeeQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Employee> employeeQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<Employee> page = this.page(new Page<>(req.getPage(), req.getSize()), employeeQueryWrapper);
        Page<EmployeeQueryResp> employeeQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page,employeeQueryRespPage);
        return employeeQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)){
            return false;
        }
        return this.removeById(id);
    }

    @Override
    public CommonRespond<Long> register(EmployeeRegisterReq employeeRegisterReq) throws Exception {
        //为空返回
        if (ObjectUtil.isEmpty(employeeRegisterReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        String mobile = employeeRegisterReq.getMobile();
        String password = employeeRegisterReq.getPassword();
        //手机号已经注册过了
        QueryWrapper<Employee> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("mobile", mobile).eq("password",password);
        List<Employee> list = this.list(adminQueryWrapper);
        if (CollectionUtil.isNotEmpty(list)) {
            throw new MyException(200,"手机号已经注册过了");
        }
        Employee newEmployee = new Employee();
        newEmployee.setMobile(mobile);
        //密码加密存储
        String encryptAES = PasswordUtil.encryptAES(key, password);
        newEmployee.setPassword(encryptAES);
        newEmployee.setId(SnowUtil.getSnowflakeNextId());
        boolean saveResult = this.save(newEmployee);
        if (saveResult) {
            return CommonRespond.succeed("注册成功", newEmployee.getId());
        }
        return new CommonRespond<>(500, "注册失败");
    }

    @Override
    public CommonRespond<LoginResp> login(EmployeeLoginReq employeeLoginReq) throws Exception {
        String mobile = employeeLoginReq.getMobile();
        String password=employeeLoginReq.getPassword();
        //密码加密
        String encryptPassword = PasswordUtil.encryptAES(key, password);

        //生成token
        Employee employee = this.query().select("id").eq("mobile", mobile).eq("password",encryptPassword).one();
        if (ObjectUtil.isNull(employee)){
            return CommonRespond.error(3000,"账号或者密码错误");
        }
        String token = JwtUtil.createToken(employee.getId(), mobile,encryptPassword);
        return CommonRespond.succeed("登陆成功",new LoginResp(true,token));
    }
}




