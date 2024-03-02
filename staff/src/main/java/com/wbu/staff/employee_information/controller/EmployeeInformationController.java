package com.wbu.staff.employee_information.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.employee_information.req.EmployeeInformationQueryReq;
import com.wbu.staff.employee_information.req.EmployeeInformationSaveReq;
import com.wbu.staff.employee_information.resp.EmployeeInformationQueryResp;
import com.wbu.staff.employee_information.service.EmployeeInformationService;
import com.wbu.staff.job_level.resp.JobLevelQueryResp;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeInformation")
public class EmployeeInformationController {
    public static final Logger LOG = LoggerFactory.getLogger(EmployeeInformationController.class);

    @Autowired
    private EmployeeInformationService employeeInformationService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody EmployeeInformationSaveReq employeeInformationSaveReq) {
        if (ObjectUtil.isEmpty(employeeInformationSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try {
            if (employeeInformationService.saveEmployeeInformation(employeeInformationSaveReq)) {
                return CommonRespond.succeed("员工信息添加或修改成功！！！", true);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return CommonRespond.error(30000, "员工信息添加或修改失败");
        }
        return CommonRespond.error(30000, "员工信息添加或修改失败");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<EmployeeInformationQueryResp>> query_list(@Valid EmployeeInformationQueryReq employeeInformationQueryReq) {

        Page<EmployeeInformationQueryResp> page = employeeInformationService.queryEmployeeInformations(employeeInformationQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (employeeInformationService.deleteById(id)) {
            return CommonRespond.succeed("删除成功", true);
        }
        return CommonRespond.error(30000, "员工信息删除失败");
    }

    @LogAnnotation
    @GetMapping("/query_all")
    public CommonRespond<List<EmployeeInformationQueryResp>> queryAll() {
        List<EmployeeInformationQueryResp> employeeInformationList = employeeInformationService.queryAll();
        return CommonRespond.succeed(employeeInformationList);
    }
}
