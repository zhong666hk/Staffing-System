package com.wbu.staff.resume.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.exception.MyException;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.department.domain.Department;
import com.wbu.staff.department.service.DepartmentService;
import com.wbu.staff.employee.domain.Employee;
import com.wbu.staff.employee.req.EmployeeRegisterReq;
import com.wbu.staff.employee.req.EmployeeResumeReq;
import com.wbu.staff.employee.service.EmployeeService;
import com.wbu.staff.employee_information.domain.EmployeeInformation;
import com.wbu.staff.employee_information.service.EmployeeInformationService;
import com.wbu.staff.job_level.domain.JobLevel;
import com.wbu.staff.job_level.service.JobLevelService;
import com.wbu.staff.nation.domain.Nation;
import com.wbu.staff.nation.service.NationService;
import com.wbu.staff.position.domain.Position;
import com.wbu.staff.position.service.PositionService;
import com.wbu.staff.resume.domain.Resume;
import com.wbu.staff.resume.mapper.ResumeMapper;
import com.wbu.staff.resume.req.ResumeApproveReq;
import com.wbu.staff.resume.req.ResumeQueryReq;
import com.wbu.staff.resume.req.ResumeSaveReq;
import com.wbu.staff.resume.resp.ResumeQueryResp;
import com.wbu.staff.resume.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【resume(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume>
        implements ResumeService {
    @Autowired
    private NationService nationService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private JobLevelService jobLevelService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeInformationService employeeInformationService;

    @Override
    public boolean saveResume(ResumeSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Resume resume = BeanUtil.copyProperties(req, Resume.class);
        Nation nation = nationService.queryNationByName(req.getNationName());
        resume.setNationId(nation.getId());
        Position position = positionService.queryPositionByName(req.getPosName());
        resume.setPosId(position.getId());
        JobLevel jobLevel = jobLevelService.queryJobLevelByName(req.getJobLevelName());
        resume.setJobLevelId(jobLevel.getId());
        Department department = departmentService.queryDepartmentByName(req.getDepartmentName());
        resume.setDepartmentId(department.getId());
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(resume.getId())) {
            resume.setId(SnowUtil.getSnowflakeNextId());
            resume.setCreateTime(date);
            resume.setUpdateTime(date);
            return this.save(resume);
        }// id不为空说明是修改的操作
        else {
            resume.setUpdateTime(date);
            return this.updateById(resume);
        }
    }

    @Override
    public Page<ResumeQueryResp> queryResumes(ResumeQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Resume> resumeQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//      PageHelper.startPage( req.getPage(),req.getSize());
        Page<Resume> page = this.page(new Page<>(req.getPage(), req.getSize()), resumeQueryWrapper);
        Page<ResumeQueryResp> resumeQueryRespPage = new Page<>();
        List<Resume> resumeList = page.getRecords();
        List<ResumeQueryResp> resumeQueryRespList = BeanUtil.copyToList(resumeList, ResumeQueryResp.class);
        for (int i = 0; i < resumeList.size(); i++) {
            ResumeQueryResp resumeQueryResp = resumeQueryRespList.get(i);
            Resume resume = resumeList.get(i);
            // 设置信息
            Nation nation = nationService.getById(resume.getNationId());
            resumeQueryResp.setNationName(nation.getName());


            Position position = positionService.getById(resume.getPosId());
            resumeQueryResp.setPosName(position.getName());

            JobLevel jobLevel = jobLevelService.getById(resume.getJobLevelId());
            resumeQueryResp.setJobLevelName(jobLevel.getName());

            Department department = departmentService.getById(resume.getDepartmentId());
            resumeQueryResp.setDepartmentName(department.getName());

        }
        resumeQueryRespPage.setRecords(resumeQueryRespList);
        return resumeQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }

    @Override
    public CommonRespond<Object> approveEmployee(ResumeApproveReq req) throws Exception {
        DateTime date = DateUtil.dateSecond();
        //1.入职 -->默认的开始员工的初始密码位账号
        EmployeeResumeReq employeeResumeReq = new EmployeeResumeReq();
        String phone = req.getPhone();
        employeeResumeReq.setName(req.getName());
        employeeResumeReq.setPassword(phone);
        employeeResumeReq.setMobile(phone);
        Employee employee = employeeService.registerEmployee(employeeResumeReq);

        //2.注入详细信息
        EmployeeInformation employeeInformation = BeanUtil.copyProperties(req, EmployeeInformation.class);

        employeeInformation.setEmployeeId(employee.getId());

        Nation nation = nationService.queryNationByName(req.getNationName());
        employeeInformation.setNationId(nation.getId());

        Department department = departmentService.queryDepartmentByName(req.getDepartmentName());
        employeeInformation.setDepartmentId(department.getId());

        JobLevel jobLevel = jobLevelService.queryJobLevelByName(req.getJobLevelName());
        employeeInformation.setJobLevelId(jobLevel.getId());

        Position position = positionService.queryPositionByName(req.getPosName());
        employeeInformation.setPosId(position.getId());

        employeeInformation.setCreateTime(date);
        employeeInformation.setUpdateTime(date);

        employeeInformation.setWorkId(employee.getWorkId());

        employeeInformation.setWorkState("在职");

        employeeInformation.setBeginDate(date);

        employeeInformation.setBeginContract(date);

        employeeInformation.setWorkAge(0);

        employeeInformation.setContractTerm(3.0);

        employeeInformation.setConversionTime(date);

        DateTime endDate = DateUtil.offset(date, DateField.YEAR, 3);
        employeeInformation.setEndContract(endDate);

        if (employeeInformationService.save(employeeInformation)) {
            return CommonRespond.error(200,"入职办理成功");
        }else {
            throw new MyException(50000, "添加失败");
        }
    }
}




