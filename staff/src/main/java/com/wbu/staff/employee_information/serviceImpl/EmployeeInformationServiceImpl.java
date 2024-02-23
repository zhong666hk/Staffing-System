package com.wbu.staff.employee_information.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.department.domain.Department;
import com.wbu.staff.department.service.DepartmentService;
import com.wbu.staff.employee.domain.Employee;
import com.wbu.staff.employee.service.EmployeeService;
import com.wbu.staff.employee_information.domain.EmployeeInformation;
import com.wbu.staff.employee_information.mapper.EmployeeInformationMapper;
import com.wbu.staff.employee_information.req.EmployeeInformationQueryReq;
import com.wbu.staff.employee_information.req.EmployeeInformationSaveReq;
import com.wbu.staff.employee_information.resp.EmployeeInformationQueryResp;
import com.wbu.staff.employee_information.service.EmployeeInformationService;
import com.wbu.staff.job_level.domain.JobLevel;
import com.wbu.staff.job_level.service.JobLevelService;
import com.wbu.staff.nation.domain.Nation;
import com.wbu.staff.nation.service.NationService;
import com.wbu.staff.position.domain.Position;
import com.wbu.staff.position.service.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【employeeInformation(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class EmployeeInformationServiceImpl extends ServiceImpl<EmployeeInformationMapper, EmployeeInformation>
        implements EmployeeInformationService {
    Logger LOG = LoggerFactory.getLogger(EmployeeInformationServiceImpl.class);
    @Autowired
    private NationService nationService;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JobLevelService jobLevelService;

    @Autowired
    private PositionService positionService;

    @Autowired
    private DepartmentService departmentService;

    @Override
    public boolean saveEmployeeInformation(EmployeeInformationSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        EmployeeInformation employeeInformation = BeanUtil.copyProperties(req, EmployeeInformation.class);
        // 获取信息
        Nation nation = nationService.queryNationByName(req.getNationName());
        employeeInformation.setNationId(nation.getId());
        Employee employee = employeeService.queryEmployeeByName(req.getName());
        employeeInformation.setEmployeeId(employee.getId());
        employeeInformation.setWorkId(employee.getWorkId());
        Position position = positionService.queryPositionByName(req.getPosName());
        employeeInformation.setPosId(position.getId());
        JobLevel jobLevel = jobLevelService.queryJobLevelByName(req.getJobLevelName());
        employeeInformation.setJobLevelId(jobLevel.getId());
        Department department = departmentService.queryDepartmentByName(req.getDepartmentName());
        employeeInformation.setDepartmentId(department.getId());
        String workState = req.getWorkState();
        if (workState.equals("0")){
            employeeInformation.setWorkState("在职");
        }else {
            employeeInformation.setWorkState("离职");
        }


        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(employeeInformation.getId())) {
            employeeInformation.setId(SnowUtil.getSnowflakeNextId());
            employeeInformation.setCreateTime(date);
            employeeInformation.setUpdateTime(date);
            return this.save(employeeInformation);
        }// id不为空说明是修改的操作
        else {
            employeeInformation.setUpdateTime(date);
            return this.updateById(employeeInformation);
        }
    }

    @Override
    public Page<EmployeeInformationQueryResp> queryEmployeeInformations(EmployeeInformationQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<EmployeeInformation> employeeInformationQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//      PageHelper.startPage( req.getPage(),req.getSize());
        Page<EmployeeInformation> page = this.page(new Page<>(req.getPage(), req.getSize()), employeeInformationQueryWrapper);
        Page<EmployeeInformationQueryResp> employeeInformationQueryRespPage = new Page<>();
        List<EmployeeInformation> employeeInformationList = page.getRecords();
        List<EmployeeInformationQueryResp> employeeInformationQueryRespList = BeanUtil.copyToList(employeeInformationList, EmployeeInformationQueryResp.class);
        for (int i = 0; i < employeeInformationList.size(); i++) {
            EmployeeInformationQueryResp employeeInformationQueryResp = employeeInformationQueryRespList.get(i);
            EmployeeInformation employeeInformation = employeeInformationList.get(i);
            // 设置信息
            Nation nation = nationService.getById(employeeInformation.getNationId());
            employeeInformationQueryResp.setNationName(nation.getName());

            Employee employee = employeeService.getById(employeeInformation.getEmployeeId());
            employeeInformationQueryResp.setName(employee.getName());

            Position position = positionService.getById(employeeInformation.getPosId());
            employeeInformationQueryResp.setPosName(position.getName());

            JobLevel jobLevel = jobLevelService.getById(employeeInformation.getJobLevelId());
            employeeInformationQueryResp.setJobLevelName(jobLevel.getName());

            Department department = departmentService.getById(employeeInformation.getDepartmentId());
            employeeInformationQueryResp.setDepartmentName(department.getName());

            if (employeeInformationQueryResp.getWorkState().equals("在职")) {
                employeeInformationQueryResp.setWorkState("0");
            }else {
                employeeInformationQueryResp.setWorkState("1");
            }
        }
        employeeInformationQueryRespPage.setRecords(employeeInformationQueryRespList);
        return employeeInformationQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }
}




