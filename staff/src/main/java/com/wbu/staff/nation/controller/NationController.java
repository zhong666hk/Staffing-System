package com.wbu.staff.nation.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wbu.staff.common.Aspect.annotation.LogAnnotation;
import com.wbu.staff.common.respon.CommonRespond;
import com.wbu.staff.common.respon.RespondExample;
import com.wbu.staff.nation.req.NationQueryReq;
import com.wbu.staff.nation.req.NationSaveReq;
import com.wbu.staff.nation.resp.NationQueryResp;
import com.wbu.staff.nation.service.NationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nation")
public class NationController {
    public static final Logger LOG = LoggerFactory.getLogger(NationController.class);

    @Autowired
    private NationService nationService;


    @LogAnnotation
    @PostMapping("/save")
    public CommonRespond<Boolean> register(@Valid @RequestBody NationSaveReq nationSaveReq) {
        if (ObjectUtil.isEmpty(nationSaveReq)) {
            return CommonRespond.error(RespondExample.REQUEST_PARAMETER_IS_ILLEGAL);
        }
        try {
            if (nationService.saveNation(nationSaveReq)) {
                return CommonRespond.succeed("添加或修改成功！！！", true);
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            return CommonRespond.error(30000, "添加或修改失败");
        }
        return CommonRespond.error(30000, "添加或修改失败");
    }


    @LogAnnotation
    @GetMapping("/query_list")
    public CommonRespond<Page<NationQueryResp>> query_list(@Valid NationQueryReq nationQueryReq) {

        Page<NationQueryResp> page = nationService.queryNations(nationQueryReq);
        return CommonRespond.succeed(page);
    }

    @LogAnnotation
    @DeleteMapping("/delete/{id}")
    public CommonRespond<Boolean> delete(@PathVariable Long id) {
        if (nationService.deleteById(id)) {
            return CommonRespond.succeed("删除成功", true);
        }
        return CommonRespond.error(30000, "删除失败");
    }

    @LogAnnotation
    @GetMapping("/query_all")
    public CommonRespond<List<NationQueryResp>> queryAll() {
        List<NationQueryResp> nationList = nationService.queryAll();
        return CommonRespond.succeed(nationList);
    }
}
