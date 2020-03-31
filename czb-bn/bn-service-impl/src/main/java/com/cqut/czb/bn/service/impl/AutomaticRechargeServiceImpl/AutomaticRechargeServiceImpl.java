package com.cqut.czb.bn.service.impl.AutomaticRechargeServiceImpl;

import com.cqut.czb.bn.dao.mapper.AutomaticRechargeMapperExtra;
import com.cqut.czb.bn.entity.dto.automaticRecharge.AutomaticRechargeDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.automaticRechargeService.AutomaticRechargeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("AutomaticRechargeService")
public class AutomaticRechargeServiceImpl implements AutomaticRechargeService {

    @Autowired
    AutomaticRechargeMapperExtra automaticRechargeMapperExtra;

    @Override
    public JSONResult getAutoList(AutomaticRechargeDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        System.out.println(pageDTO.getStartTime());
        List<AutomaticRechargeDTO> withdrawList = automaticRechargeMapperExtra.getAutoList(pageDTO);
        PageInfo<AutomaticRechargeDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult deleteRecorder(String id) {
        int deleNum = automaticRechargeMapperExtra.deleteRecorder(id);
        if (deleNum != 0) {
            return new JSONResult("删除成功", 200);
        }
        return new JSONResult("删除失败", 400);
    }
}
