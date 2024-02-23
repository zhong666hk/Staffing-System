package com.wbu.staff.nation.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.nation.domain.Nation;
import com.wbu.staff.nation.req.NationQueryReq;
import com.wbu.staff.nation.req.NationSaveReq;
import com.wbu.staff.nation.resp.NationQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【nation(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface NationService extends IService<Nation> {
    public boolean saveNation(NationSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<NationQueryResp> queryNations(NationQueryReq req);

    /**
     * 删除nation 通过id
     * @param id
     */
    public boolean  deleteById(Long id);

    List<NationQueryResp> queryAll();

    Nation queryNationByName(String name);
}