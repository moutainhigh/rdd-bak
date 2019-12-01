package com.cqut.czb.bn.service.impl.AppPaymentServiceImpl;

import com.alibaba.fastjson.JSONObject;
import com.cqut.czb.bn.dao.mapper.AddressMapperExtra;
import com.cqut.czb.bn.dao.mapper.ShopMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityMapper;
import com.cqut.czb.bn.dao.mapper.weChatSmallProgram.WeChatCommodityOrderMapper;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatParameterConfig;
import com.cqut.czb.bn.entity.dto.PayConfig.WeChatUtils;
import com.cqut.czb.bn.entity.dto.WeChatCommodity.PayInputDTO;
import com.cqut.czb.bn.entity.entity.Address;
import com.cqut.czb.bn.entity.entity.Shop;
import com.cqut.czb.bn.entity.entity.User;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodity;
import com.cqut.czb.bn.entity.entity.weChatSmallProgram.WeChatCommodityOrder;
import com.cqut.czb.bn.service.appPaymentService.WeChatAppletPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.SortedMap;
import java.util.UUID;

@Service
public class WeChatAppletPayServiceImpl implements WeChatAppletPayService {


    @Autowired
    WeChatCommodityMapper weChatCommodityMapper;

    @Autowired
    AddressMapperExtra addressMapperExtra;

    @Autowired
    ShopMapper shopMapper;

    @Autowired
    WeChatCommodityOrderMapper weChatCommodityOrderMapper;

    public Boolean inputOrder(String orgId,WeChatCommodity weChatCommodity,User user, PayInputDTO payInputDTO){
        //插入预支付订单
        WeChatCommodityOrder weChatCommodityOrder=new WeChatCommodityOrder();
        weChatCommodityOrder.setOrderId(orgId);
        weChatCommodityOrder.setUserId(user.getUserId());
        weChatCommodityOrder.setCommodityId(weChatCommodity.getCommodityId());
        weChatCommodityOrder.setShopId(weChatCommodity.getShopId());
        //可能涉及到vip
        weChatCommodityOrder.setActualPrice(weChatCommodity.getSalePrice());
        weChatCommodityOrder.setPayStatus(0);
        weChatCommodityOrder.setPayMethod(2);
        weChatCommodityOrder.setRemark(payInputDTO.getRemark());
        //需要生成电子码
        weChatCommodityOrder.setElectronicCode("");
        //0：待支付  1：支付完成待处理 2：订单完成
        weChatCommodityOrder.setOrderState(0);
        //前台给地址id
        weChatCommodityOrder.setAddressId(payInputDTO.getAddressId());
        //插入二维码
        weChatCommodityOrder.setQrcode("二维码");
        weChatCommodityOrder.setPhone(payInputDTO.getUserPhone());
        //来源
        weChatCommodityOrder.setCommoditySource("本地商家");
        //返佣金额
        weChatCommodityOrder.setFyMoney(weChatCommodity.getFyMoney()*Integer.valueOf(payInputDTO.getCommodityNum()));
        //成本价格
        weChatCommodityOrder.setCostPrice(weChatCommodity.getCostPrice()*Integer.valueOf(payInputDTO.getCommodityNum()));
        //商品类型——没确定
        weChatCommodityOrder.setCommodityType(1);
        //商品类目id——需要查出来
        weChatCommodityOrder.setCommmodityTypeId("sdfj");
        //商品类型
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        weChatCommodityOrder.setCreateAt(new Date());
        weChatCommodityOrder.setCommodityNum(Integer.valueOf(payInputDTO.getCommodityNum()));
        return weChatCommodityOrderMapper.insertSelective(weChatCommodityOrder)>0;
    }

    @Override
    public JSONObject WeChatAppletBuyCommodity(User user, PayInputDTO payInputDTO) {
        //查出商品信息
        WeChatCommodity weChatCommodity=weChatCommodityMapper.selectByPrimaryKey(payInputDTO.getCommodityId());
        if(weChatCommodity==null){
            return null;
        }
        /**
         * 生成起调参数串——返回给app（微信的支付订单）
         */
        String orgId = System.currentTimeMillis() + UUID.randomUUID().toString().substring(10, 15);
        String nonceStrTemp = WeChatUtils.getRandomStr();
        double money= BigDecimal.valueOf(weChatCommodity.getSalePrice()).multiply(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).doubleValue();

        //插入订单
        inputOrder( orgId, weChatCommodity, user,  payInputDTO);
        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersApplet(user.getUserAccount(),money,nonceStrTemp,orgId,user.getUserId(),weChatCommodity);
        return WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }
}
