package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WxOrderWithdrawMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.GetWxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxOrderWithdrawService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxOrderWithdrawServiceImpl implements WxOrderWithdrawService{

    @Autowired
    WxOrderWithdrawMapperExtra wxOrderWithdrawMapperExtra;

    @Override
    public JSONResult toGetAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO) {
        PageHelper.startPage(wxOrderWithdrawDTO.getCurrentPage(), wxOrderWithdrawDTO.getPageSize(),true);
        List<WxOrderWithdrawDTO> list = new ArrayList<>();
        for (int i = 0; i < wxOrderWithdrawDTO.getShopIds().size(); i++) {
            GetWxOrderWithdrawDTO getWxOrderWithdrawDTO = new GetWxOrderWithdrawDTO();
            getWxOrderWithdrawDTO.setShopId(wxOrderWithdrawDTO.getShopIds().get(i));
            getWxOrderWithdrawDTO.setStartTime(wxOrderWithdrawDTO.getStartTime());
            getWxOrderWithdrawDTO.setEndTime(wxOrderWithdrawDTO.getEndTime());
            List<WxOrderWithdrawDTO> withdrawList = wxOrderWithdrawMapperExtra.toGetAllOrder(getWxOrderWithdrawDTO);
            System.out.println(wxOrderWithdrawDTO.getShopIds().size());
            list.addAll(withdrawList);
        }
        PageInfo<WxOrderWithdrawDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("查询成功",200,pageInfo);
    }

    @Override
    public JSONResult toWithDraw(WxOrderWithdrawDTO wxOrderWithdrawDTO) {
        for (int i = 0; i < wxOrderWithdrawDTO.getShopIds().size(); i++) {
//            List<WxOrderWithdrawDTO> withdrawList = wxOrderWithdrawMapperExtra.toWithDraw();
            System.out.println(wxOrderWithdrawDTO.getShopIds().size());
        }
        return new JSONResult("查询成功",200,"");
    }
}
