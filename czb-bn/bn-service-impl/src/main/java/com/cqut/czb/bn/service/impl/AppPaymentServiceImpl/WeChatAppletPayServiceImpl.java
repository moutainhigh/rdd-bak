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
        double money= BigDecimal.valueOf(weChatCommodity.getCostPrice()).subtract(BigDecimal.valueOf(Integer.valueOf(payInputDTO.getCommodityNum()))).doubleValue();

        //插入预支付订单
        WeChatCommodityOrder weChatCommodityOrder=new WeChatCommodityOrder();

        weChatCommodityOrder.setOrderId(orgId);
        weChatCommodityOrder.setUserId("254810436006489204");
        weChatCommodityOrder.setCommodityId(weChatCommodity.getCommodityId());
        weChatCommodityOrder.setShopId(weChatCommodity.getShopId());
        weChatCommodityOrder.setActualPrice(weChatCommodity.getSalePrice());
        weChatCommodityOrder.setPayStatus(0);
        weChatCommodityOrder.setPayMethod(2);
        weChatCommodityOrder.setElectronicCode("");//电子码
        //0：待支付  1：支付完成待处理 2：订单完成
        weChatCommodityOrder.setOrderState(0);
        //判断是否会邮寄
        if(weChatCommodity.getTakeWay()==1){
            Address address= addressMapperExtra.getDefaultAddress("254810436006489204");
            weChatCommodityOrder.setAddressId(address.getAddressId());
        }
//        //查出商店信息
//        Shop shop=shopMapper.selectByPrimaryKey(weChatCommodity.getShopId());
        weChatCommodityOrder.setPhone(payInputDTO.getUserPhone());
        //需要计算
        weChatCommodityOrder.setFyMoney(weChatCommodity.getFyMoney());
        weChatCommodityOrder.setCostPrice(weChatCommodity.getCostPrice()*Integer.valueOf(payInputDTO.getCommodityNum()));
        //商品类型
        //        weChatCommodityOrder.setCommodityType();
        weChatCommodityOrder.setCommmodityTypeId(weChatCommodity.getCommmodityTypeId());
        weChatCommodityOrder.setCreateAt(new Date());
        weChatCommodityOrder.setCommodityNum(Integer.valueOf(payInputDTO.getCommodityNum()));
        weChatCommodityOrderMapper.insertSelective(weChatCommodityOrder);

        // 设置参数
        SortedMap<String, Object> parameters = WeChatParameterConfig.getParametersApplet(user.getUserAccount(),money,nonceStrTemp,orgId,"254810436006489204",weChatCommodity);
        return WeChatParameterConfig.getSign( parameters, nonceStrTemp);
    }
}
