package com.wbu.staff.position.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wbu.staff.position.domain.Position;
import com.wbu.staff.position.req.PositionQueryReq;
import com.wbu.staff.position.req.PositionSaveReq;
import com.wbu.staff.position.resp.PositionQueryResp;

import java.util.List;

/**
* @author 钟正保
* @description 针对表【position(乘车人)】的数据库操作Service
* @createDate 2023-11-14 14:43:47
*/
public interface PositionService extends IService<Position> {
    public boolean savePosition(PositionSaveReq req);

    /**
     * 查询当前登录用户的购票
     * @param req
     * @return
     */
    public Page<PositionQueryResp> queryPositions(PositionQueryReq req);

    /**
     * 删除position 通过id
     * @param id
     */
    public boolean  deleteById(Long id);
}