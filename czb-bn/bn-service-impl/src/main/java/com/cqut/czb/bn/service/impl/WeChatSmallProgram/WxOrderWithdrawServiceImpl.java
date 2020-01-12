package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WxOrderWithdrawMapperExtra;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.GetWxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatSettleRecord;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WxOrderWithdrawDTO;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.WxOrderWithdrawService;
import com.cqut.czb.bn.util.string.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxOrderWithdrawServiceImpl implements WxOrderWithdrawService {

    @Autowired
    WxOrderWithdrawMapperExtra wxOrderWithdrawMapperExtra;

    @Override
    public JSONResult toGetAllOrder(WxOrderWithdrawDTO wxOrderWithdrawDTO) {
        List<WxOrderWithdrawDTO> list = new ArrayList<>();
        PageHelper.startPage(wxOrderWithdrawDTO.getCurrentPage(), wxOrderWithdrawDTO.getPageSize(),true);
        list = wxOrderWithdrawMapperExtra.toGetAllOrder(wxOrderWithdrawDTO);
        PageInfo<WxOrderWithdrawDTO> pageInfo = new PageInfo<>(list);
        return new JSONResult("查询成功",200,pageInfo);
    }

//    结算时数据库更新
    @Override
    public JSONResult toWithDraw(String id, WxOrderWithdrawDTO wxOrderWithdrawDTO) {
        WeChatSettleRecord weChatSettleRecord = new WeChatSettleRecord();
        List<WxOrderWithdrawDTO> list = new ArrayList<>();
        list = wxOrderWithdrawMapperExtra.toGetAllOrder(wxOrderWithdrawDTO);
        List<String> orderList = new ArrayList<>();
        String theId = "";
        theId = StringUtil.createId();
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
            orderList.add(list.get(i).getOrderId());
        }
        int bangding = wxOrderWithdrawMapperExtra.toBangding(orderList,theId);
        return new JSONResult("查询成功",200,"");
    }
}
