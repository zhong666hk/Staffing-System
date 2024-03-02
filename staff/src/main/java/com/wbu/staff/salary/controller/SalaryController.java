package com.wbu.staff.salary.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.salary.domain.Salary;
import com.wbu.staff.salary.req.SalaryQueryReq;
import com.wbu.staff.salary.req.SalarySaveReq;
import com.wbu.staff.salary.resp.SalaryQueryResp;
import com.wbu.staff.salary.service.SalaryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salary")
public class SalaryController {
    public static final Logger LOG = LoggerFactory.getLogger(SalaryController.class);

    @Autowired
    private SalaryService salaryService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody SalarySaveReq salarySaveReq) {
        if (ObjectUtil.isEmpty(salarySaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (salaryService.saveSalary(salarySaveReq)) {
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
    public CommonRespond<Page<SalaryQueryResp>> query_list(@Valid SalaryQueryReq salaryQueryReq) {

        Page<SalaryQueryResp> page = salaryService.querySalarys(salaryQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (salaryService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }
}
