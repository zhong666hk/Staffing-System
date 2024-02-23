package com.wbu.staff.resume.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.resume.domain.Resume;
import com.wbu.staff.resume.req.ResumeApproveReq;
import com.wbu.staff.resume.req.ResumeQueryReq;
import com.wbu.staff.resume.req.ResumeSaveReq;
import com.wbu.staff.resume.resp.ResumeQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【resume(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface ResumeService extends IService<Resume> {
    public boolean saveResume(ResumeSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<ResumeQueryResp> queryResumes(ResumeQueryReq req);

    /**
     * 删除resume 通过id
     * @param id
     */
    public boolean  deleteById(Long id);

    public CommonRespond<Object> approveEmployee(ResumeApproveReq resumeApproveReq) throws Exception;
}