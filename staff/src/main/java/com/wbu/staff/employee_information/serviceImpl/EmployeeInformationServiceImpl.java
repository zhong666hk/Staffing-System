package com.wbu.staff.employee_information.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.employee_information.domain.EmployeeInformation;
import com.wbu.staff.employee_information.mapper.EmployeeInformationMapper;
import com.wbu.staff.employee_information.req.EmployeeInformationQueryReq;
import com.wbu.staff.employee_information.req.EmployeeInformationSaveReq;
import com.wbu.staff.employee_information.resp.EmployeeInformationQueryResp;
import com.wbu.staff.employee_information.service.EmployeeInformationService;
import org.springframework.stereotype.Service;

/**
 * @author 钟正保
 * @description 针对表【employeeInformation(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class EmployeeInformationServiceImpl extends ServiceImpl<EmployeeInformationMapper, EmployeeInformation>
        implements EmployeeInformationService {

    @Override
    public boolean saveEmployeeInformation(EmployeeInformationSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        EmployeeInformation employeeInformation = BeanUtil.copyProperties(req, EmployeeInformation.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(employeeInformation.getId())) {
            employeeInformation.setId(SnowUtil.getSnowflakeNextId());
            employeeInformation.setCreateTime(date);
            employeeInformation.setUpdateTime(date);
            return this.save(employeeInformation);
        }// id不为空说明是修改的操作
        else {
            employeeInformation.setUpdateTime(date);
            return this.updateById(employeeInformation);
        }
    }

    @Override
    public Page<EmployeeInformationQueryResp> queryEmployeeInformations(EmployeeInformationQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<EmployeeInformation> employeeInformationQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<EmployeeInformation> page = this.page(new Page<>(req.getPage(), req.getSize()), employeeInformationQueryWrapper);
        Page<EmployeeInformationQueryResp> employeeInformationQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page, employeeInformationQueryRespPage);
        return employeeInformationQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }
}




