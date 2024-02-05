package com.wbu.staff.job_level.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.job_level.req.JobLevelQueryReq;
import com.wbu.staff.job_level.req.JobLevelSaveReq;
import com.wbu.staff.job_level.resp.JobLevelQueryResp;
import com.wbu.staff.job_level.service.JobLevelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobLevel")
public class JobLevelController {
    public static final Logger LOG = LoggerFactory.getLogger(JobLevelController.class);

    @Autowired
    private JobLevelService jobLevelService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody JobLevelSaveReq jobLevelSaveReq) {
        if (ObjectUtil.isEmpty(jobLevelSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try {
            if (jobLevelService.saveJobLevel(jobLevelSaveReq)) {
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
    public CommonRespond<Page<JobLevelQueryResp>> query_list(@Valid JobLevelQueryReq jobLevelQueryReq) {

        Page<JobLevelQueryResp> page = jobLevelService.queryJobLevels(jobLevelQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (jobLevelService.deleteById(id)) {
            return CommonRespond.succeed("删除成功", true);
        }
        return CommonRespond.error(30000, "删除失败");
    }

    @LogAnnotation
    @GetMapping("/query_all")
    public CommonRespond<List<JobLevelQueryResp>> queryAll() {
        List<JobLevelQueryResp> jobLevelList = jobLevelService.queryAll();
        return CommonRespond.succeed(jobLevelList);
    }
}
