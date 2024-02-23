package com.wbu.staff.nation.serviceImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wbu.staff.common.exception.MyException;
import com.wbu.staff.nation.domain.Nation;
import com.wbu.staff.nation.mapper.NationMapper;
import com.wbu.staff.nation.req.NationQueryReq;
import com.wbu.staff.nation.req.NationSaveReq;
import com.wbu.staff.nation.resp.NationQueryResp;
import com.wbu.staff.nation.service.NationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 钟正保
 * @description 针对表【nation(乘车人)】的数据库操作Service实现
 * @createDate 2023-11-14 14:43:47
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation>
        implements NationService {

    @Override
    public boolean saveNation(NationSaveReq req) {
        DateTime date = DateUtil.dateSecond(); // hutool的是已经格式化了的
        if (ObjectUtil.isNull(req)) {
            return false;
        }
        // 拷贝类
        Nation nation = BeanUtil.copyProperties(req, Nation.class);
        // 如果是id为空--->说明是添加的操作
        if (ObjectUtil.isNull(nation.getId())) {
            nation.setId(RandomUtil.randomInt(1, 56));
            return this.save(nation);
        }// id不为空说明是修改的操作
        else {
            return this.updateById(nation);
        }
    }

    @Override
    public Page<NationQueryResp> queryNations(NationQueryReq req) {
        // ObjectUtil.isNotNull(req)为空是管理员来查询所有的票
        QueryWrapper<Nation> nationQueryWrapper = new QueryWrapper<>();
        //原理会对第一个sql进行拦截 添加limit
//        PageHelper.startPage( req.getPage(),req.getSize());
        Page<Nation> page = this.page(new Page<>(req.getPage(), req.getSize()), nationQueryWrapper);
        Page<NationQueryResp> nationQueryRespPage = new Page<>();
        BeanUtil.copyProperties(page, nationQueryRespPage);
        return nationQueryRespPage;
    }

    @Override
    public boolean deleteById(Long id) {
        if (ObjectUtil.isNull(id)) {
            return false;
        }
        return this.removeById(id);
    }

    @Override
    public List<NationQueryResp> queryAll() {
        QueryWrapper<Nation> nationQueryWrapper = new QueryWrapper<>();
        nationQueryWrapper.orderByAsc("name");
        List<Nation> list = this.list(nationQueryWrapper);
        return BeanUtil.copyToList(list, NationQueryResp.class);
    }

    @Override
    public Nation queryNationByName(String name) {
        if (ObjectUtil.isNotNull(name)) {
            QueryWrapper<Nation> nationQueryWrapper = new QueryWrapper<>();
            nationQueryWrapper.eq("name",name);
            Nation nation = this.getOne(nationQueryWrapper);
            if (ObjectUtil.isNotNull(nation)) {
                return nation;
            } else {
                throw new MyException(30000, "该名族不存在");
            }
        } else {
            throw new MyException(30000, "name不能为空");
        }
    }
}




