package com.wbu.staff.position.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.util.SnowUtil;
import com.wbu.staff.position.domain.Position;
import com.wbu.staff.position.mapper.PositionMapper;
import com.wbu.staff.position.req.PositionQueryReq;
import com.wbu.staff.position.req.PositionSaveReq;
import com.wbu.staff.position.resp.PositionQueryResp;
import com.wbu.staff.position.service.PositionService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 钟正保
 * @description 针对表【position(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position>
        implements PositionService {

    @Override
    public boolean savePosition(PositionSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Position position = BeanUtil.copyProperties(req, Position.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(position.getId())){
            position.setId(SnowUtil.getSnowflakeNextId());
            position.setCreateTime(date);
            position.setUpdateTime(date);
            return this.save(position);
        }// id不为空说明是修改的操作
        else {
            position.setUpdateTime(date);
            return this.updateById(position);
        }
    }

    @Override
    public Page<PositionQueryResp> queryPositions(PositionQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Position> positionQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<Position> page = this.page(new Page<>(req.getPage(), req.getSize()), positionQueryWrapper);
        Page<PositionQueryResp> positionQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page,positionQueryRespPage);
        return positionQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)){
            return false;
        }
        return this.removeById(id);
    }
}




