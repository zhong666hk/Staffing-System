package com.wbu.staff.position.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.exception.AppExceptionExample;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.position.domain.Position;
import com.wbu.staff.position.req.PositionQueryReq;
import com.wbu.staff.position.req.PositionSaveReq;
import com.wbu.staff.position.resp.PositionQueryResp;
import com.wbu.staff.position.service.PositionService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {
    public static final Logger LOG = LoggerFactory.getLogger(PositionController.class);

    @Autowired
    private PositionService positionService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody PositionSaveReq positionSaveReq) {
        if (ObjectUtil.isEmpty(positionSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try{
            if (positionService.savePosition(positionSaveReq)) {
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
    public CommonRespond<Page<PositionQueryResp>> query_list(@Valid PositionQueryReq positionQueryReq) {

        Page<PositionQueryResp> page = positionService.queryPositions(positionQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (positionService.deleteById(id)){
            return CommonRespond.succeed("删除成功",true);
        }
        return CommonRespond.error(30000,"删除失败");
    }

    @LogAnnotation
    @GetMapping("/query_all")
    public CommonRespond<List<PositionQueryResp>> queryAll() {
        List<PositionQueryResp> positionList = positionService.queryAll();
        return CommonRespond.succeed(positionList);
    }
}
