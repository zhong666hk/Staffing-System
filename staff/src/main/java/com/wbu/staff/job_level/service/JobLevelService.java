package com.wbu.staff.job_level.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.job_level.domain.JobLevel;
import com.wbu.staff.job_level.req.JobLevelQueryReq;
import com.wbu.staff.job_level.req.JobLevelSaveReq;
import com.wbu.staff.job_level.resp.JobLevelQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【jobLevel(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface JobLevelService extends IService<JobLevel> {
    public boolean saveJobLevel(JobLevelSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<JobLevelQueryResp> queryJobLevels(JobLevelQueryReq req);

    /**
     * 删除jobLevel 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}