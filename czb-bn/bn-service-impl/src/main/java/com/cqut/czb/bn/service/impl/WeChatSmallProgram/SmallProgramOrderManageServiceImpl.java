package com.cqut.czb.bn.service.impl.WeChatSmallProgram;

import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.entity.dto.PageDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDTO;
import com.cqut.czb.bn.entity.dto.WeChatSmallProgram.WeChatCommodityOrderDetail;
import com.cqut.czb.bn.entity.global.JSONResult;
import com.cqut.czb.bn.service.weChatSmallProgram.SmallProgramOrderManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmallProgramOrderManageServiceImpl implements SmallProgramOrderManageService {

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;

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
        jsonResult.setMessage("订单作废成功");
        return jsonResult;
    }

    @Override
    public JSONResult<WeChatCommodityOrderDetail> getOrderDetail(String orderId) {
        JSONResult<WeChatCommodityOrderDetail> jsonResult = new JSONResult<>();
        WeChatCommodityOrderDetail result = weChatCommodityOrderMapper.getOrderDetail(orderId);
        jsonResult.setData(result);
        jsonResult.setCode(200);
        jsonResult.setMessage("数据获取成功");
        return null;
    }
}
