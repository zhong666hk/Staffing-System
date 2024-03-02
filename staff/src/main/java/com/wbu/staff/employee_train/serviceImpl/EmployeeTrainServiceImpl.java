package com.wbu.staff.employee_train.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.employee_train.domain.EmployeeTrain;
import com.wbu.staff.employee_train.mapper.EmployeeTrainMapper;
import com.wbu.staff.employee_train.req.EmployeeTrainQueryReq;
import com.wbu.staff.employee_train.req.EmployeeTrainSaveReq;
import com.wbu.staff.employee_train.resp.EmployeeTrainQueryResp;
import com.wbu.staff.employee_train.service.EmployeeTrainService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 钟正保
 * @description 针对表【employeeTrain(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class EmployeeTrainServiceImpl extends ServiceImpl<EmployeeTrainMapper, EmployeeTrain>
        implements EmployeeTrainService {

    @Override
    public boolean saveEmployeeTrain(EmployeeTrainSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        EmployeeTrain employeeTrain = BeanUtil.copyProperties(req, EmployeeTrain.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(employeeTrain.getId())){
            employeeTrain.setId(SnowUtil.getSnowflakeNextId());
            return this.save(employeeTrain);
        }// id不为空说明是修改的操作
        else {
            return this.updateById(employeeTrain);
        }
    }

    @Override
    public Page<EmployeeTrainQueryResp> queryEmployeeTrains(EmployeeTrainQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<EmployeeTrain> employeeTrainQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<EmployeeTrain> page = this.page(new Page<>(req.getPage(), req.getSize()), employeeTrainQueryWrapper);
        Page<EmployeeTrainQueryResp> employeeTrainQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page,employeeTrainQueryRespPage);
        return employeeTrainQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)){
            return false;
        }
        return this.removeById(id);
    }
}




