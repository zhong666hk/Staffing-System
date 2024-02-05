package com.wbu.staff.job_level.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.job_level.domain.JobLevel;
import com.wbu.staff.job_level.mapper.JobLevelMapper;
import com.wbu.staff.job_level.req.JobLevelQueryReq;
import com.wbu.staff.job_level.req.JobLevelSaveReq;
import com.wbu.staff.job_level.resp.JobLevelQueryResp;
import com.wbu.staff.job_level.service.JobLevelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【jobLevel(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class JobLevelServiceImpl extends ServiceImpl<JobLevelMapper, JobLevel>
        implements JobLevelService {

    @Override
    public boolean saveJobLevel(JobLevelSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        JobLevel jobLevel = BeanUtil.copyProperties(req, JobLevel.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(jobLevel.getId())) {
            jobLevel.setId(SnowUtil.getSnowflakeNextId());
            jobLevel.setCreateTime(date);
            return this.save(jobLevel);
        }// id不为空说明是修改的操作
        else {
            return this.updateById(jobLevel);
        }
    }

    @Override
    public Page<JobLevelQueryResp> queryJobLevels(JobLevelQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<JobLevel> jobLevelQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<JobLevel> page = this.page(new Page<>(req.getPage(), req.getSize()), jobLevelQueryWrapper);
        Page<JobLevelQueryResp> jobLevelQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page, jobLevelQueryRespPage);
        return jobLevelQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }

    @Override
    public List<JobLevelQueryResp> queryAll() {
        QueryWrapper<JobLevel> jobLevelQueryWrapper = new QueryWrapper<>();
        jobLevelQueryWrapper.orderByAsc("name");
        List<JobLevel> list = this.list(jobLevelQueryWrapper);
        return BeanUtil.copyToList(list, JobLevelQueryResp.class);
    }
}




