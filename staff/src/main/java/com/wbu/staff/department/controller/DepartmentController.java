package com.wbu.staff.department.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.department.req.DepartmentQueryReq;
import com.wbu.staff.department.req.DepartmentSaveReq;
import com.wbu.staff.department.resp.DepartmentQueryResp;
import com.wbu.staff.department.service.DepartmentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    public static final Logger LOG = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody DepartmentSaveReq departmentSaveReq) {
        if (ObjectUtil.isEmpty(departmentSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try {
            if (departmentService.saveDepartment(departmentSaveReq)) {
                return CommonRespond.succeed("添加或修改成功！！！", true);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return CommonRespond.error(30000, "添加或修改失败");
        }
        return CommonRespond.error(30000, "添加或修改失败");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<DepartmentQueryResp>> query_list(@Valid DepartmentQueryReq departmentQueryReq) {

        Page<DepartmentQueryResp> page = departmentService.queryDepartments(departmentQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (departmentService.deleteById(id)) {
            return CommonRespond.succeed("删除成功", true);
        }
        return CommonRespond.error(30000, "删除失败");
    }
}
