package com.wbu.staff.employee_train.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.employee_train.domain.EmployeeTrain;
import com.wbu.staff.employee_train.req.EmployeeTrainQueryReq;
import com.wbu.staff.employee_train.req.EmployeeTrainSaveReq;
import com.wbu.staff.employee_train.resp.EmployeeTrainQueryResp;
import com.wbu.staff.employee_train.service.EmployeeTrainService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employeeTrain")
public class EmployeeTrainController {
    public static final Logger LOG = LoggerFactory.getLogger(EmployeeTrainController.class);

    @Autowired
    private EmployeeTrainService employeeTrainService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody EmployeeTrainSaveReq employeeTrainSaveReq) {
        if (ObjectUtil.isEmpty(employeeTrainSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (employeeTrainService.saveEmployeeTrain(employeeTrainSaveReq)) {
                return CommonRespond.succeed("添加或修改成功！！！",true);
            }
        }catch (Exception e){
            LOG.error(e.getMessage());
            return CommonRespond.error(30000,"添加或修改失败");
        }
        return CommonRespond.error(30000,"添加或修改失败");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<EmployeeTrainQueryResp>> query_list(@Valid EmployeeTrainQueryReq employeeTrainQueryReq) {

        Page<EmployeeTrainQueryResp> page = employeeTrainService.queryEmployeeTrains(employeeTrainQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (employeeTrainService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }
}
