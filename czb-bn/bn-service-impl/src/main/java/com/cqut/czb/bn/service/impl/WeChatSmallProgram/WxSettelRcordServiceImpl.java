package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WxSettelRcordMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxSettleRcordDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxSettelRcordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxSettelRcordServiceImpl implements WxSettelRcordService{
    @Autowired
    WxSettelRcordMapperExtra wxSettelRcordMapperExtra;

    @Override
    public JSONResult getSettleRcord(WxSettleRcordDTO pageDTO) {
        PageHelper.startPage(pageDTO.getCurrentPage(), pageDTO.getPageSize(),true);
        List<WxSettleRcordDTO> withdrawList = wxSettelRcordMapperExtra.selectSettleRcord(pageDTO);
        PageInfo<WxSettleRcordDTO> pageInfo = new PageInfo<>(withdrawList);
        return new JSONResult("列表数据查询成功", 200, pageInfo);
    }

    @Override
    public JSONResult settleRecord(String recordId) {
        if(wxSettelRcordMapperExtra.settleRecord(recordId))
            return new JSONResult("结算成功", 200, true);
        else
            return new JSONResult("结算失败", 500, false);
    }

    @Override
    public JSONResult deleteSettleRecord(String recordId) {
        if(wxSettelRcordMapperExtra.deleteSettleRecord(recordId) && wxSettelRcordMapperExtra.updateSettleRecord(recordId))
            return new JSONResult("删除成功", 200, true);
        else
            return new JSONResult("删除失败", 500, false);
    }
}
