package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WxOrderWithdrawMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.GetWxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatSettleRecord;
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
        List<WxOrderWithdrawDTO> list = new ArrayList<>();
        PageHelper.startPage(wxOrderWithdrawDTO.getCurrentPage(), wxOrderWithdrawDTO.getPageSize(),true);
        if (wxOrderWithdrawDTO.getShopIds().size() > 0) {
            for (int i = 0; i < wxOrderWithdrawDTO.getShopIds().size(); i++) {
                GetWxOrderWithdrawDTO getWxOrderWithdrawDTO = new GetWxOrderWithdrawDTO();
                getWxOrderWithdrawDTO.setShopId(wxOrderWithdrawDTO.getShopIds().get(i));
                getWxOrderWithdrawDTO.setStartTime(wxOrderWithdrawDTO.getStartTime());
                getWxOrderWithdrawDTO.setEndTime(wxOrderWithdrawDTO.getEndTime());
                List<WxOrderWithdrawDTO> withdrawList = wxOrderWithdrawMapperExtra.toGetAllOrder(getWxOrderWithdrawDTO);
                System.out.println(wxOrderWithdrawDTO.getShopIds().size());
                list.addAll(withdrawList);
            }
        } else {
            GetWxOrderWithdrawDTO getWxOrderWithdrawDTO = new GetWxOrderWithdrawDTO();
            getWxOrderWithdrawDTO.setRecordId(wxOrderWithdrawDTO.getRecordId());
            list = wxOrderWithdrawMapperExtra.toGetAllOrder(getWxOrderWithdrawDTO);
        }
        PageInfo<WxOrderWithdrawDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("查询成功",200,pageInfo);
    }

//    结算时数据库更新
    @Override
    public JSONResult toWithDraw(String id, WxOrderWithdrawDTO wxOrderWithdrawDTO) {
        WeChatSettleRecord weChatSettleRecord = new WeChatSettleRecord();
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
        String[] ids = {"0","1","2","3","4","5","6","7","8","9"};
        String theId = "";
        for (int i = 0; i < 11; i++) {
            theId += ids[(int)(Math.random()*10)];
        }
        weChatSettleRecord.setRecordId(theId);
        weChatSettleRecord.setSettleUserId(id);
        Double amount = 0.0;
        for (int i = 0; i < wxOrderWithdrawDTO.getShopIds().size(); i++) {
            weChatSettleRecord.setShopId(wxOrderWithdrawDTO.getShopIds().get(i));
            amount += wxOrderWithdrawMapperExtra.getTotalAmount(wxOrderWithdrawDTO.getShopIds().get(i));
        }
        weChatSettleRecord.setTotalAmount(amount);
        int insertNum =  wxOrderWithdrawMapperExtra.insertWithdraw(weChatSettleRecord);
        System.out.println(list.size());
        for (int i = 0; i < list.size(); i++) {
            int bangding = wxOrderWithdrawMapperExtra.toBangding(list.get(i).getOrderId(),theId);
        }
        return new JSONResult("查询成功",200,"");
    }
}
