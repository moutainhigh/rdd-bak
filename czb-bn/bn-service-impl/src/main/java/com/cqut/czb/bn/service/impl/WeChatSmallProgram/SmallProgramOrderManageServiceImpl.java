package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.AddressMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.DealCommodityInputDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderProcess;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SmallProgramOrderManageServiceImpl implements SmallProgramOrderManageService {

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;
    @Autowired
    AddressMapper addressMapper;

    @Override
    public JSONResult<PageInfo<WeChatCommodityOrderDTO>> getTableList(WeChatCommodityOrderDTO input, PageDTO page) {
        PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
        JSONResult<PageInfo<WeChatCommodityOrderDTO>> jsonResult = new JSONResult<>();
        List<WeChatCommodityOrderDTO> result = weChatCommodityOrderMapper.getTableList(input);
        PageInfo<WeChatCommodityOrderDTO> pageInfo = new PageInfo<>(result);
        jsonResult.setData(pageInfo);

        jsonResult.setCode(200);
        jsonResult.setMessage("列表数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<Boolean> obsoleteOrder(String orderId) {
        JSONResult<Boolean> jsonResult = new JSONResult<>();
        boolean result = weChatCommodityOrderMapper.obsoleteOrder(orderId) > 0;
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("成功作废该订单");
        return jsonResult;
    }

    @Override
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String orderId) {
        // 获取订单通用信息
        JSONResult<WeChatCommodityOrderDetail> jsonResult = new JSONResult<>();
        WeChatCommodityOrderDetail result = weChatCommodityOrderMapper.getOrderDetail(orderId);

        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<WeChatCommodityOrderProcess> getOrderProcessInfo(String orderId) {
        JSONResult<WeChatCommodityOrderProcess> jsonResult = new JSONResult<>();
        WeChatCommodityOrderProcess result = weChatCommodityOrderMapper.getOrderProcessInfo(orderId);

        // 如果为寄送(takeWay == 1)，获取addressInfo
        if (result.getTakeWay() == 1 && result.getAddressId() != null) {
            Address address = addressMapper.selectByPrimaryKey(result.getAddressId());
            result.setAddressInfo(address.getProvince() + address.getCity() + address.getArea() + address.getDetail());
        }

        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("数据获取成功");
        return jsonResult;
    }

    @Override
    public JSONResult<Boolean> dealOrder(WeChatCommodityOrderProcess input) {
        input.setProcessingTime(new Date().toString());
        JSONResult<Boolean> jsonResult = new JSONResult<>();
        Boolean result;
        if (input.getTakeWay() == 1) { // 寄送
            result = weChatCommodityOrderMapper.dealOrderSend(input) > 0;
        } else if (input.getTakeWay() == 2) { // 核销
            result = weChatCommodityOrderMapper.dealOrderEl(input) > 0;
        } else {
            jsonResult.setCode(200);
            jsonResult.setMessage("商品取件方式不明确！");
            return jsonResult;
        }
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("成功处理该订单");
        return jsonResult;
    }
}
