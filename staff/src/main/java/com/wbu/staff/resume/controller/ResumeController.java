package com.wbu.staff.resume.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.resume.domain.Resume;
import com.wbu.staff.resume.req.ResumeApproveReq;
import com.wbu.staff.resume.req.ResumeQueryReq;
import com.wbu.staff.resume.req.ResumeSaveReq;
import com.wbu.staff.resume.resp.ResumeQueryResp;
import com.wbu.staff.resume.service.ResumeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
public class ResumeController {
    public static final Logger LOG = LoggerFactory.getLogger(ResumeController.class);

    @Autowired
    private ResumeService resumeService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody ResumeSaveReq resumeSaveReq) {
        if (ObjectUtil.isEmpty(resumeSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (resumeService.saveResume(resumeSaveReq)) {
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
    public CommonRespond<Page<ResumeQueryResp>> query_list(@Valid ResumeQueryReq resumeQueryReq) {

        Page<ResumeQueryResp> page = resumeService.queryResumes(resumeQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (resumeService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }

    /**
     * 审批入职
     * @param resumeApproveReq
     * @return
     */
    @LogAnnotation
    @PostMapping("/approve")
    public CommonRespond<Object> approve(@Valid @RequestBody ResumeApproveReq resumeApproveReq) throws Exception {
        if (ObjectUtil.isEmpty(resumeApproveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
         return resumeService.approveEmployee(resumeApproveReq);
    }
}
